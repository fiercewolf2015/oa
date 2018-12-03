package com.xyj.oa.staff.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.occupation.entity.Occupation;
import com.xyj.oa.occupation.repository.OccupationDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.title.entity.Title;
import com.xyj.oa.title.repository.TitleDao;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.entity.WorkFlow;
import com.xyj.oa.workflow.repository.WorkFlowDao;

import net.sf.json.JSONObject;

@Component
@Transactional
public class StaffService {

	private static final String SEPRATOR = ",";

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private OccupationDao occupationDao;

	@Autowired
	private TitleDao titleDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private DepartMentDao departMentDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	WorkFlowDao workFlowDao;

	private static final String[] nation = { "汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族",
			"傣族", "黎族", "傈僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族",
			"阿昌族", "普米族", "塔吉克族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族" };

	private static final String[] political = { "中共党员", "中共预备党员", "共青团员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "无党派民主人士", "群众" };

	private static final String[] education = { "小学", "初中", "中职", "中技", "中专", "高中", "职高", "高职", "大专", "本科", "硕士", "MBA", "EMBA", "博士", "其他" };

	public long findCountWithParams(String params) {
		return staffDao.getCountWithParams(params);
	}

	public List<Staff> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		List<Staff> listWithParams = staffDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
		for (Staff staff : listWithParams) {
			Set<Post> posts = staff.getPosts();
			List<String> postNames = new ArrayList<>();
			List<Long> postIds = new ArrayList<>();
			for (Post post : posts) {
				if (post == null)
					continue;
				postNames.add(post.getPostName());
				postIds.add(post.getId());
			}
			staff.setPostNames(StringHelper.join(postNames, SEPRATOR));
			staff.setPostIds(StringHelper.join(postIds, SEPRATOR));
		}
		return listWithParams;
	}

	public List<Map<String, Object>> findAllWorkflowTypeByUserId(Long userId) {
		List<Map<String, Object>> result = new ArrayList<>();
		if (userId == null || userId <= 0)
			return result;
		Optional<User> optional = userDao.findById(userId);
		if(!optional.isPresent())
			return result;
		User user = optional.get();
		
		Staff staff = user.getStaff();
		if (staff == null)
			return result;
		List<WorkFlow> workFlows = workFlowDao.findWorkFlowByStaffId(staff.getId());
		if (CollectionUtils.isEmpty(workFlows))
			return result;
		Map<String, Object> map = null;
		for (WorkFlow workFlow : workFlows) {
			map = new HashMap<String, Object>();
			map.put("id", workFlow.getId());
			map.put("workflowType", workFlow.getWorkFlowType().getId());
			map.put("name", workFlow.getName());
			result.add(map);
		}
		return result;
	}

	public int saveStaff(String params, String selPostIds) {
		if (StringHelper.isEmpty(params))
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String staffName = jsonObject.getString("staffName");
		String occupationId = jsonObject.getString("occupationIdHidden");
		Occupation occupation = null;
		if (StringHelper.isNotEmpty(occupationId))
			occupation = occupationDao.findById(Long.valueOf(occupationId)).get();
		String departmentId = jsonObject.getString("departmentIdHidden");
		Department department = null;
		if (StringHelper.isNotEmpty(departmentId))
			department = departMentDao.findById(Long.valueOf(departmentId)).get();
		Set<Post> posts = new HashSet<>();
		if (StringHelper.isNotEmpty(selPostIds)) {
			String[] postIdArr = selPostIds.split(",");
			for (String postId : postIdArr) {
				Post findOne = postDao.findById(Long.valueOf(postId)).get();
				if (findOne != null)
					posts.add(findOne);
			}
		}
		String titleId = jsonObject.getString("title");
		Title title = null;
		if (StringHelper.isNotEmpty(titleId))
			title = titleDao.findById(Long.valueOf(titleId)).get();
		String jobProject = jsonObject.getString("jobProject");
		String jobTime = jsonObject.getString("jobTime");
		String enterpriseTime = jsonObject.getString("enterpriseTime");
		String gender = jsonObject.getString("gender");
		String nation = jsonObject.getString("nation");
		String birthday = jsonObject.getString("birthday");
		String nativePlace = jsonObject.getString("nativePlace");
		String blood = jsonObject.getString("blood");
		String personalId = jsonObject.getString("personalId");
		String marriage = jsonObject.getString("marriage");
		String political = jsonObject.getString("political");
		String health = jsonObject.getString("health");
		String partyDate = jsonObject.getString("partyDate");
		String homeAddress = jsonObject.getString("homeAddress");
		String homePhoneNum = jsonObject.getString("homePhoneNum");
		String accountAddress = jsonObject.getString("accountAddress");
		String archivesAddress = jsonObject.getString("archivesAddress");
		String education = jsonObject.getString("education");
		String major = jsonObject.getString("major");
		String highestDegree = jsonObject.getString("highestDegree");
		String highestDegreeMajor = jsonObject.getString("highestDegreeMajor");
		String degree = jsonObject.getString("degree");
		String forensicTime = jsonObject.getString("forensicTime");
		String certificateNumber = jsonObject.getString("certificateNumber");
		String otherCertificate = jsonObject.getString("otherCertificate");
		String otherForensicTime = jsonObject.getString("otherForensicTime");
		String otherCertificateNumber = jsonObject.getString("otherCertificateNumber");
		String mobilePhone = jsonObject.getString("mobilePhone");
		String fixedPhone = jsonObject.getString("fixedPhone");
		String email = jsonObject.getString("email");
		Staff staff = null;
		String staffId = jsonObject.getString("selStaffId");
		if (StringHelper.isEmpty(staffId))
			staff = new Staff();
		else
			staff = staffDao.findById(Long.valueOf(staffId)).orElse(null);
		if (staff == null)
			return 0;
		staff.setAccountAddress(accountAddress);
		staff.setArchivesAddress(archivesAddress);
		staff.setBirthday(birthday);
		staff.setBlood(blood);
		staff.setCertificateNumber(certificateNumber);
		staff.setDegree(degree);
		staff.setDepartment(department);
		staff.setEducation(education);
		staff.setEnterpriseTime(enterpriseTime);
		staff.setForensicTime(forensicTime);
		if (StringHelper.isNotEmpty(gender))
			staff.setGender(Integer.valueOf(gender));
		staff.setHealth(health);
		staff.setHighestDegree(highestDegree);
		staff.setHighestDegreeMajor(highestDegreeMajor);
		staff.setHomeAddress(homeAddress);
		staff.setHomePhoneNum(homePhoneNum);
		staff.setJobProject(jobProject);
		staff.setJobTime(jobTime);
		staff.setMajor(major);
		if (StringHelper.isNotEmpty(marriage))
			staff.setMarriage(Integer.valueOf(marriage));
		staff.setNation(nation);
		staff.setNativePlace(nativePlace);
		staff.setOccupation(occupation);
		staff.setOtherCertificate(otherCertificate);
		staff.setOtherCertificateNumber(otherCertificateNumber);
		staff.setTitle(title);
		staff.setStaffName(staffName);
		staff.setPosts(posts);
		staff.setPolitical(political);
		staff.setPersonalId(personalId);
		staff.setPartyDate(partyDate);
		staff.setOtherForensicTime(otherForensicTime);
		staff.setMobilePhone(mobilePhone);
		staff.setFixedPhone(fixedPhone);
		staff.setEmail(email);
		String staffNo = jsonObject.getString("staffNo");
		//设置员工编号,当前月份加三个数字
		if (StringHelper.isNotEmpty(staffNo)) {
			Staff findByNo = staffDao.findByNo(staffNo);
			if (findByNo != null) {
				if (StringHelper.isEmpty(staffId))
					return 2;
				else {
					if (findByNo.getId().longValue() != Long.valueOf(staffId))
						return 2;
				}
			}
			staff.setStaffNo(staffNo);
			User user = userDao.findByStaffId(staff.getId());
			if (user != null) {
				user.setLoginName(staffNo);
				userDao.save(user);
			}
		} else {
			String curMonthStr = DateUtil.getCurMonthStr();
			staff.setStaffNo(String.valueOf(staffDao.findCurMonthMaxStaffNo(curMonthStr)));
		}
		staffDao.save(staff);
		return 1;
	}

	public int deleteStaffs(String staffIds) {
		if (StringHelper.isEmpty(staffIds))
			return 0;
		String[] staffIdArr = staffIds.split(",");
		for (String staffId : staffIdArr) {
			Staff findOne = staffDao.findById(Long.valueOf(staffId)).orElse(null);
			if (findOne == null)
				continue;
			User user = userDao.findByStaffId(Long.parseLong(staffId));
			if (user != null)
				userDao.delete(user);
			staffDao.delete(findOne);
		}
		return 1;
	}

	public String importStaff(InputStream inputStream, String fileName) throws Exception {
		String result = importForExcel(inputStream, fileName);
		return result;
	}

	private String importForExcel(InputStream inputStream, String fileName) throws Exception {
		boolean isE2007 = false; //判断是否是excel2007格式  
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		Workbook wb = null;
		if (isE2007)
			wb = new XSSFWorkbook(inputStream);
		else
			wb = new HSSFWorkbook(inputStream);
		Sheet sheet = wb.getSheetAt(0);
		Row row;
		String cell;
		for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null)
				continue;
			if (StringHelper.isEmpty(getCellFormatValue(row.getCell(0))) || StringHelper.isEmpty(getCellFormatValue(row.getCell(1)))
					|| StringHelper.isEmpty(getCellFormatValue(row.getCell(2))) || StringHelper.isEmpty(getCellFormatValue(row.getCell(3)))
					|| StringHelper.isEmpty(getCellFormatValue(row.getCell(4)))) {
				return "第" + (i + 1) + "行有错误，前五项必填";
			}
			Staff staff = new Staff();
			int physicalNumberOfCells = row.getPhysicalNumberOfCells();
			if (physicalNumberOfCells < 6)
				physicalNumberOfCells = physicalNumberOfCells + 1;
			for (int j = row.getFirstCellNum(); j < physicalNumberOfCells; j++) {
				cell = StringHelper.removeBlank(getCellFormatValue(row.getCell(j)));
				if (StringHelper.isEmpty(cell) && j != 5)
					continue;
				switch (j) {
				case 0://姓名
					staff.setStaffName(cell);
					break;
				case 1://部门
					List<Department> list = departMentDao.findByNameOrNo(cell, cell);
					if (CollectionUtils.isEmpty(list))
						return "第" + (i + 1) + "行有错误，部门未找到";
					staff.setDepartment(list.get(0));
					break;
				case 2://职务
					Occupation occupation = occupationDao.findByOccupationName(cell);
					if (occupation == null)
						return "第" + (i + 1) + "行有错误，职务未找到";
					staff.setOccupation(occupation);
					break;
				case 3://职称职级
					Title title = titleDao.findByTitleName(cell);
					if (title == null)
						return "第" + (i + 1) + "行有错误，职称职级未找到";
					staff.setTitle(title);
					break;
				case 4://岗位
					Post post = postDao.findByPostName(cell);
					if (post == null)
						return "第" + (i + 1) + "行有错误，岗位未找到";
					else {
						if (post.getDepartments().contains(staff.getDepartment()))
							staff.getPosts().add(post);
						else
							return "第" + (i + 1) + "行有错误，岗位和部门不匹配";
					}
					break;
				case 5://考勤编号
					if (StringHelper.isNotEmpty(cell)) {
						if (cell.indexOf("E") > 0)
							return "第" + (i + 1) + "行有错误！将员工编号改为文本格式";
						Staff findByNo = staffDao.findByNo(cell);
						if (findByNo != null)
							return "第" + (i + 1) + "行有错误，员工编号已存在";
						staff.setStaffNo(cell);
					} else {
						String curMonthStr = DateUtil.getCurMonthStr();
						staff.setStaffNo(String.valueOf(staffDao.findCurMonthMaxStaffNo(curMonthStr)));
					}
					break;
				case 6://手机号
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将手机号改为文本格式";
					else if (cell.length() != 11)
						return "第" + (i + 1) + "行有错误！身份证号长度有误";
					staff.setMobilePhone(cell);
					break;
				case 7://固定电话
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将固定电话改为文本格式";
					staff.setFixedPhone(cell);
					break;
				case 8://电子邮箱
					if (cell.indexOf("@") < 0)
						return "第" + (i + 1) + "行有错误！邮箱格式有误";
					staff.setEmail(cell);
					break;
				case 9://身份证号
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将身份证号改为文本格式";
					else if (cell.length() != 18)
						return "第" + (i + 1) + "行有错误！身份证号长度有误";
					staff.setPersonalId(cell);
					break;
				case 10://性别
					if (!cell.equals("男") && !cell.equals("女"))
						return "第" + (i + 1) + "行有错误！性别请填写“男”或“女”";
					staff.setGender(cell.equals("男") ? 1 : 2);
					break;
				case 11://民族
					if (!cell.contains("族"))
						cell = cell + "族";
					boolean flag = false;
					for (int k = 0; k < nation.length; k++) {
						if (nation[k].equals(cell)) {
							staff.setNation(k + "");
							flag = true;
							break;
						}
					}
					if (!flag)
						return "第" + (i + 1) + "行有错误！民族不正确";
					break;
				case 12://生日
					staff.setBirthday(cell);
					break;
				case 13://籍贯
					staff.setNativePlace(cell);
					break;
				case 14://婚姻状况
					staff.setMarriage(cell.equals("已婚") ? 1 : 0);
					break;
				case 15://政治面貌
					flag = false;
					for (int k = 0; k < political.length; k++) {
						if (political[k].equals(cell)) {
							staff.setPolitical(k + "");
							flag = true;
							break;
						}
					}
					if (!flag)
						return "第" + (i + 1) + "行有错误！政治面貌不正确";
					break;
				case 16://血型
					staff.setBlood(cell);
					break;
				case 17://健康状况
					staff.setHealth(cell);
					break;
				case 18://入党时间
					staff.setPartyDate(cell);
					break;
				case 19://原学历
					flag = false;
					for (int k = 0; k < education.length; k++) {
						if (education[k].equals(cell)) {
							staff.setEducation(k + "");
							flag = true;
							break;
						}
					}
					if (!flag)
						return "第" + (i + 1) + "行有错误！学历不正确";
					break;
				case 20://原所学专业
					staff.setMajor(cell);
					break;
				case 21://最高学历
					flag = false;
					for (int k = 0; k < education.length; k++) {
						if (education[k].equals(cell)) {
							staff.setHighestDegree(k + "");
							flag = true;
							break;
						}
					}
					if (!flag)
						return "第" + (i + 1) + "行有错误！最高学历不正确";
					break;
				case 22://最高学历专业
					staff.setHighestDegreeMajor(cell);
					break;
				case 23://学位
					staff.setDegree(cell);
					break;
				case 24://取证时间
					staff.setForensicTime(cell);
					break;
				case 25://证件编号
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将证件编号改为文本格式";
					staff.setCertificateNumber(cell);
					break;
				case 26://其他证书名称
					staff.setOtherCertificate(cell);
					break;
				case 27://其他取证时间
					staff.setOtherForensicTime(cell);
					break;
				case 28://其他证件编号	
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将其他证件编号改为文本格式";
					staff.setOtherCertificateNumber(cell);
					break;
				case 29://参加工作时间
					staff.setJobTime(cell);
					break;
				case 30://进入本企业时间
					staff.setEnterpriseTime(cell);
					break;
				case 31://家庭住址
					staff.setHomeAddress(cell);
					break;
				case 32://家庭电话
					if (cell.indexOf("E") > 0)
						return "第" + (i + 1) + "行有错误！将家庭电话改为文本格式";
					staff.setHomePhoneNum(cell);
					break;
				case 33://户口所在地
					staff.setAccountAddress(cell);
					break;
				case 34://档案所在地
					staff.setArchivesAddress(cell);
					break;
				case 35://工作所属项目
					staff.setJobProject(cell);
					break;
				default:
					break;
				}
			}
			staffDao.save(staff);
		}
		return "";
	}

	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case XSSFCell.CELL_TYPE_NUMERIC:
			case XSSFCell.CELL_TYPE_FORMULA:
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else { // 如果是纯数字；取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
					if (cellvalue.endsWith(".0")) {
						cellvalue = cellvalue.replace(".0", "");
					}
				}
				break;
			// 如果当前Cell的Type为STRING，取得当前的Cell字符串
			case XSSFCell.CELL_TYPE_STRING:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				boolean comment = cell.getBooleanCellValue();
				cellvalue = comment ? "Y" : "N";
				break;
			// 默认的Cell值
			default:
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

}
