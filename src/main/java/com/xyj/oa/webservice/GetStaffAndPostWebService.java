package com.xyj.oa.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;

@Component
@Transactional
public class GetStaffAndPostWebService {

	private static Logger logger = LoggerFactory.getLogger(GetStaffAndPostWebService.class);

	private static final String DRIVER = "oracle.jdbc.OracleDriver";

	private static final String URL = "jdbc:oracle:thin:@//59.110.67.70:1520/KDEAS";

	private static final String USER = "shr";

	private static final String PASSWORD = "123456";

	private static final String DEPT = "CV_ORG_BaseUnit";

	private static final String POST = "CV_ORG_Position";

	private static final String STAFF = "CV_BD_Person";

	private static final String POINT = "###";

	private static final List<String> SQLLIST = new ArrayList<>();

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private DepartMentDao departMentDao;

	@Autowired
	private PostDao postDao;

	static {
		SQLLIST.add("select * from " + DEPT);
		SQLLIST.add("select * from " + POST);
		SQLLIST.add("select * from " + STAFF);
	}

	public int saveOrUpdateDataFromGold() {
		Map<Integer, List<String>> dataInfo = getDataInfo();
		if (dataInfo == null || dataInfo.isEmpty())
			return 1;
		List<String> depts = dataInfo.get(0);
		if (CollectionUtils.isNotEmpty(depts)) {
			for (String item : depts) {
				String[] data = item.split(POINT);
				if (data == null || data.length == 0)
					continue;
				String oId = data[0];
				String deptNo = data[1];
				String deptName = data[2];
				Integer deptlevel = Integer.parseInt(data[3]);
				String parentDeptId = data[4];
				Department department = departMentDao.findByOldId(oId);
				if (department == null) {
					List<Department> list = departMentDao.findByNameOrNo(deptName, deptNo);
					if (!CollectionUtils.isEmpty(list))
						department = list.get(0);
				}
				Department parentDepartment = departMentDao.findByOldId(parentDeptId);
				if (department == null)
					department = new Department();
				department.setDepartmentNo(deptNo);
				department.setDepartmentLevel(deptlevel);
				department.setDepartmentName(deptName);
				department.setOldId(oId);
				if (parentDepartment != null)
					department.setParentDepartment(parentDepartment);
				departMentDao.save(department);
			}
			// for (String item : depts) {
			// String[] data = item.split(POINT);
			// if (data == null || data.length == 0)
			// continue;
			// String oId = data[0];
			// String parentDeptId = data[4];
			// Department department = departMentDao.findByOldId(oId);
			// Department parentDepartment = departMentDao.findByOldId(parentDeptId);
			// if (department == null)
			// continue;
			// if (parentDepartment != null)
			// department.setParentDepartment(parentDepartment);
			// departMentDao.save(department);
			// }
		}
		List<String> posts = dataInfo.get(1);
		if (CollectionUtils.isNotEmpty(posts)) {
			for (String item : posts) {
				String[] data = item.split(POINT);
				if (data == null || data.length == 0)
					continue;
				String oId = data[0];
				String postNo = data[1];
				String postName = data[2];
				String deptId = data[3];
				Department department = departMentDao.findByOldId(deptId);

				Post post = postDao.findByOldId(oId);
				if (post == null)
					post = postDao.findByPostName(postName);
				if (post == null)
					post = new Post();
				post.setOldId(oId);
				post.setPostName(postName);
				post.setPostNo(postNo);
				Set<Department> departments = post.getDepartments();
				if (department != null)
					departments.add(department);
				post.setDepartments(departments);
				postDao.save(post);
			}
		}
		List<String> staffs = dataInfo.get(2);
		if (CollectionUtils.isNotEmpty(staffs)) {
			for (String item : staffs) {
				String[] data = item.split(POINT);
				if (data == null || data.length == 0)
					continue;
				String oId = data[0];

				String staffNo = data[1];
				String staffName = data[2];
				Integer staffGender = Integer.parseInt(StringHelper.isEmpty(data[3]) ? "1" : data[3]);
				String postId = data[4];
				String deptId = data[5];
				if (postId == null || deptId == null)
					continue;
				Department department = departMentDao.findByOldId(deptId);
				Post post = postDao.findByOldId(postId);
				if (department == null || post == null)
					continue;
				Staff staff = staffDao.findByOldId(oId);

				if (staff == null) {
					staff = staffDao.findByNo(staffNo);
					if (staff == null)
						staff = new Staff();
				}
				staff.setGender(staffGender);
				staff.setStaffName(staffName);
				staff.setStaffNo(staffNo);
				if (department != null)
					staff.setDepartment(department);
				Set<Post> staffposts = staff.getPosts();
				staffposts.clear();
				if (post != null)
					staffposts.add(post);
				staff.setPosts(staffposts);
				staff.setOldId(oId);
				staffDao.save(staff);

			}
		}
		return 1;
	}

	public Map<Integer, List<String>> getDataInfo() {
		Map<Integer, List<String>> returnMap = new HashMap<Integer, List<String>>();
		try {
			Connection con = openOracleCon();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			for (int i = 0; i < SQLLIST.size(); i++) {
				pstm = con.prepareStatement(SQLLIST.get(i));
				rs = pstm.executeQuery();
				if (i == 0) {
					List<String> processDeptData = processDeptData(rs);
					returnMap.put(i, processDeptData);
				} else if (i == 1) {
					List<String> processPostData = processPostData(rs);
					returnMap.put(i, processPostData);
				} else {
					List<String> processStaffData = processStaffData(rs);
					returnMap.put(i, processStaffData);
				}
			}
			closeOracleCon(con, pstm, rs);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return returnMap;
	}

	private List<String> processDeptData(ResultSet rs) {
		List<String> deptList = new ArrayList<>();
		try {
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				String deptId = rs.getString("FID");
				String deptNo = rs.getString("FNUMBER");
				String deptName = rs.getString("FNAME");
				int deptlevel = rs.getInt("FLEVEL");
				String parentDeptId = rs.getString(("FPARENTID"));
				int ifSon = rs.getInt(("FISLEAF"));
				sb.append(deptId).append(POINT).append(deptNo).append(POINT).append(deptName).append(POINT)
						.append(deptlevel).append(POINT).append(parentDeptId).append(POINT).append(ifSon);
				deptList.add(sb.toString());
			}
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return deptList;
	}

	private List<String> processPostData(ResultSet rs) {
		List<String> postList = new ArrayList<>();
		try {
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				String postId = rs.getString("FID");
				String postNo = rs.getString("FNUMBER");
				String postName = rs.getString("FNAME");
				String deptId = rs.getString("FORGUNITID");
				String parentPostId = rs.getString("FPARENTID");
				String postNum = rs.getString("FINDEX");
				sb.append(postId).append(POINT).append(postNo).append(POINT).append(postName).append(POINT)
						.append(deptId).append(POINT).append(parentPostId).append(POINT).append(postNum);
				postList.add(sb.toString());
			}
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return postList;
	}

	private List<String> processStaffData(ResultSet rs) {
		List<String> staffList = new ArrayList<>();
		try {
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				String staffId = rs.getString("FID");
				String staffNo = rs.getString("FNUMBER");
				String staffName = rs.getString("FNAME");
				String staffGender = rs.getString("FGENDER");
				String postId = rs.getString(("FPOSITIONID"));
				String deptId = rs.getString("FORGUNITID");
				sb.append(staffId).append(POINT).append(staffNo).append(POINT).append(staffName).append(POINT)
						.append(staffGender).append(POINT).append(postId).append(POINT).append(deptId);
				staffList.add(sb.toString());
			}
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return staffList;
	}

	private Connection openOracleCon() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return con;
	}

	private void closeOracleCon(Connection con, PreparedStatement pstm, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		try {
			if (con != null && (!con.isClosed())) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.error(LogUtil.stackTraceToString(e));
				}
			}
		} catch (SQLException e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
	}

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@//59.110.67.70:1520/KDEAS";
		String user = "shr";
		String password = "123456";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from " + DEPT;
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			// while (rs.next()) {
			// StringBuilder sb = new StringBuilder();
			// String staffId = rs.getString("FID");
			// String staffNo = rs.getString("FNUMBER");
			// String staffName = rs.getString("FNAME");
			// String staffGender = rs.getString("FGENDER");
			// String postId = rs.getString(("FPOSITIONID"));
			// String deptId = rs.getString("FORGUNITID");
			// sb.append(staffId).append(POINT).append(staffNo).append(POINT).append(staffName).append(POINT).append(staffGender).append(POINT).append(postId)
			// .append(POINT).append(deptId);
			// System.out.println(sb.toString());
			// }
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				String postId = rs.getString("FID");
				String postNo = rs.getString("FNUMBER");
				String postName = rs.getString("FNAME");
				String deptId = rs.getString("FORGUNITID");
				String parentPostId = rs.getString("FPARENTID");
				String postNum = rs.getString("FINDEX");
				sb.append(postId).append(POINT).append(postNo).append(POINT).append(postName).append(POINT)
						.append(deptId).append(POINT).append(parentPostId).append(POINT).append(postNum);
				System.out.println(sb.toString());
			}
			flag = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				if (con != null && (!con.isClosed())) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (flag) {
			System.out.println("执行成功！");
		} else {
			System.out.println("执行失败！");
		}
	}
}
