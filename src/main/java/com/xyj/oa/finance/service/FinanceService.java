package com.xyj.oa.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.finance.entity.FinanceApproval;
import com.xyj.oa.finance.entity.FinanceOrganization;
import com.xyj.oa.finance.repository.FinanceApprovalDao;
import com.xyj.oa.finance.repository.FinanceOrganizationDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.system.entity.FinanceSubject;
import com.xyj.oa.system.repository.FinanceLineDao;
import com.xyj.oa.system.repository.FinanceSubjectDao;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

@Component
@Transactional
public class FinanceService {

	@Autowired
	private FinanceLineDao financeLineDao;

	@Autowired
	private FinanceApprovalDao financeApprovalDao;

	@Autowired
	private FinanceOrganizationDao financeOrganizationDao;

	@Autowired
	private FinanceSubjectDao financeSubjectDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private DepartMentDao departMentDao;

	private static final List<String> POSTLIST = new ArrayList<>();

	static {
		POSTLIST.add("收入专员");
		POSTLIST.add("工程专员");
		POSTLIST.add("物业专员");
		POSTLIST.add("财务专员");
		POSTLIST.add("采购专员");
		POSTLIST.add("车管专员");
		POSTLIST.add("人力资源部");
	}

	public Long findBudgetingCountWithParams(String params, Integer history) {
		return financeApprovalDao.getCountWithParams(params, 0, history);
	}

	public List<FinanceApproval> findBudgetingListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, Integer history) {
		return financeApprovalDao.getListWithParams(params, pageNo, pageSize, sidx, sord, 0, history);
	}

	public int saveNewOrganization(String[] params, String financeName, String year, Long departmentId) {
		if (params == null || financeName == null || year == null)
			return 0;
		FinanceApproval approval = new FinanceApproval();
		approval.setApprovalFlag(0);
		approval.setCreateTime(DateUtil.getNowTimestamp());
		approval.setFinanceYear(year);
		approval.setFinanceApprovalName(financeName);
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).orElse(null);
		if (user == null)
			return 0;
		approval.setFinanceApplyStaff(user.getStaff());
		Department department = departMentDao.findById(departmentId).orElse(null);
		approval.setFinanceApplyDepartment(department);
		financeApprovalDao.save(approval);
		Set<FinanceOrganization> allOr = new HashSet<FinanceOrganization>();
		Map<Long, FinanceOrganization> parentMap = new HashMap<Long, FinanceOrganization>();
		for (String obj : params) {
			String[] objArr = obj.split("#");
			FinanceSubject financeSubject = financeSubjectDao.findById(Long.valueOf(objArr[0])).orElse(null);
			FinanceOrganization financeOrganization = new FinanceOrganization();
			FinanceSubject parentFinanceSubject = financeSubject.getParentFinanceSubject();
			if (parentFinanceSubject != null) {
				FinanceOrganization financeOrganizationParent = parentMap.get(parentFinanceSubject.getId());
				if (financeOrganizationParent != null)
					financeOrganization.setParentFinanceOrganization(financeOrganizationParent);
			}
			financeOrganization.setFinanceSubjects(financeSubject);
			financeOrganization.setFinanceApproval(approval);
			setMoney(financeOrganization, Integer.valueOf(objArr[1]), Integer.valueOf(objArr[2]), Integer.valueOf(objArr[3]), Integer.valueOf(objArr[4]),
					Integer.valueOf(objArr[5]), Integer.valueOf(objArr[6]), Integer.valueOf(objArr[7]), Integer.valueOf(objArr[8]), Integer.valueOf(objArr[9]),
					Integer.valueOf(objArr[10]), Integer.valueOf(objArr[11]), Integer.valueOf(objArr[12]), Integer.valueOf(objArr[13]));
			financeOrganization.setIfChild(Integer.valueOf(objArr[14]));
			financeOrganizationDao.save(financeOrganization);
			parentMap.put(financeSubject.getId(), financeOrganization);
			allOr.add(financeOrganization);
		}
		approval.setFinanceOrganizations(allOr);
		//		approval.setAllJanuary(allMonth.get(0));
		//		approval.setAllFebruary(allMonth.get(1));
		//		approval.setAllMarch(allMonth.get(2));
		//		approval.setAllApril(allMonth.get(3));
		//		approval.setAllMay(allMonth.get(4));
		//		approval.setAllJune(allMonth.get(5));
		//		approval.setAllJuly(allMonth.get(6));
		//		approval.setAllAugust(allMonth.get(7));
		//		approval.setAllSeptember(allMonth.get(8));
		//		approval.setAllOctober(allMonth.get(9));
		//		approval.setAllNovember(allMonth.get(10));
		//		approval.setAllDecember(allMonth.get(11));
		financeApprovalDao.save(approval);
		return 1;
	}

	public int deleteFinanceApproval(Long id) {
		FinanceApproval financeApproval = financeApprovalDao.findById(id).orElse(null);
		if (financeApproval == null)
			return 0;
		if (financeApproval.getApprovalFlag() != 0)
			return -1;//审批过程中不得删除
		Set<FinanceOrganization> organizations = financeApproval.getFinanceOrganizations();
		for (FinanceOrganization financeOrganization : organizations)
			financeOrganizationDao.delete(financeOrganization);
		financeApprovalDao.delete(financeApproval);
		return 1;
	}

	public List<FinanceOrganization> getAllSubjectsInfo(Long appId) {
		List<FinanceOrganization> result = new ArrayList<>();
		if (appId == null || appId <= 0)
			return result;
		FinanceApproval findOne = financeApprovalDao.findById(appId).orElse(null);
		if (findOne == null)
			return result;
		StringBuilder subjectIds = new StringBuilder();
		if (findOne.getApprovalFlag() == 2 || findOne.getApprovalFlag() == 7) {
			StringBuilder postIds = new StringBuilder();
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			if (shiroUser == null)
				return result;
			Long userd = shiroUser.getId();
			User user = userDao.findById(userd).get();
			Set<Post> posts = user.getStaff().getPosts();
			if (CollectionUtils.isEmpty(posts))
				return result;
			for (Post post : posts) {
				if (!POSTLIST.contains(post.getPostName()))
					continue;
				postIds.append(",").append(post.getId());
			}
			if (postIds.length() > 0) {
				List<FinanceSubject> list = financeSubjectDao.findByPostIds(postIds.substring(1).toString());
				if (CollectionUtils.isEmpty(list))
					return result;
				for (FinanceSubject financeSubject : list)
					subjectIds.append(",").append(financeSubject.getId());
			}
		}
		List<FinanceOrganization> findFinaceOrGanizationP = financeOrganizationDao.findFinaceOrGanizationP(appId, subjectIds.length() > 0 ? subjectIds
				.substring(1).toString() : null);
		if (CollectionUtils.isNotEmpty(findFinaceOrGanizationP)) {
			for (FinanceOrganization fo : findFinaceOrGanizationP) {
				iterationFinanceOrganizationForChild(fo, result, appId);
			}
		}
		return result;
	}

	public void iterationFinanceOrganizationForChild(FinanceOrganization fo, List<FinanceOrganization> list, Long appId) {
		if (!list.contains(fo))
			list.add(fo);
		List<FinanceOrganization> findFinaceOrGanizationChild = financeOrganizationDao.findFinaceOrGanizationChild(fo.getId(), appId);
		for (FinanceOrganization child : findFinaceOrGanizationChild)
			iterationFinanceOrganizationForChild(child, list, appId);
	}

	/**
	 * 编辑一个子科目
	 * @param params
	 * @param subjectId
	 * @return
	 */
	public int editOneSubject(String params, Long subjectId) {
		int result = 0;
		if (StringHelper.isEmpty(params) || subjectId == null || subjectId <= 0)
			return result;
		FinanceOrganization entity = financeOrganizationDao.findById(subjectId).orElse(null);
		if (entity == null)
			return result;
		if (entity.getIfChild() == 0 || entity.getFinanceApproval().getApprovalFlag() == 100)
			return -2;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		Integer ja = jsonObject.getInt("subjectFinanceJanuary");
		Integer fe = jsonObject.getInt("subjectFinanceFebruary");
		Integer mar = jsonObject.getInt("subjectFinanceMarch");
		Integer ap = jsonObject.getInt("subjectFinanceApril");
		Integer may = jsonObject.getInt("subjectFinanceMay");
		Integer jun = jsonObject.getInt("subjectFinanceJune");
		Integer jul = jsonObject.getInt("subjectFinanceJuly");
		Integer au = jsonObject.getInt("subjectFinanceAugust");
		Integer se = jsonObject.getInt("subjectFinanceSeptember");
		Integer oc = jsonObject.getInt("subjectFinanceOctober");
		Integer no = jsonObject.getInt("subjectFinanceNovember");
		Integer de = jsonObject.getInt("subjectFinanceDecember");
		Integer all = jsonObject.getInt("totalForEdit");
		//修改自己
		setMoney(entity, ja, fe, mar, ap, may, jun, jul, au, se, oc, no, de, all);
		financeOrganizationDao.save(entity);
		//修改父
		FinanceOrganization parent = entity.getParentFinanceOrganization();
		while (parent != null) {
			ja = 0;
			fe = 0;
			mar = 0;
			ap = 0;
			may = 0;
			jun = 0;
			jul = 0;
			au = 0;
			se = 0;
			oc = 0;
			no = 0;
			de = 0;
			all = 0;
			List<FinanceOrganization> findFinaceOrGanizationChild = financeOrganizationDao.findFinaceOrGanizationChild(parent.getId(), null);
			for (FinanceOrganization fo : findFinaceOrGanizationChild) {
				ja += fo.getSubjectFinanceJanuary();
				fe += fo.getSubjectFinanceFebruary();
				mar += fo.getSubjectFinanceMarch();
				ap += fo.getSubjectFinanceApril();
				may += fo.getSubjectFinanceMay();
				jun += fo.getSubjectFinanceJune();
				jul += fo.getSubjectFinanceJuly();
				au += fo.getSubjectFinanceAugust();
				se += fo.getSubjectFinanceSeptember();
				oc += fo.getSubjectFinanceOctober();
				no += fo.getSubjectFinanceNovember();
				de += fo.getSubjectFinanceDecember();
			}
			all = ja + fe + ap + mar + may + jun + jul + au + se + oc + no + de;
			setMoney(parent, ja, fe, mar, ap, may, jun, jul, au, se, oc, no, de, all);
			financeOrganizationDao.save(parent);
			parent = parent.getParentFinanceOrganization();
		}
		return 1;
	}

	private void setMoney(FinanceOrganization entity, Integer ja, Integer fe, Integer mar, Integer ap, Integer may, Integer jun, Integer jul, Integer au,
			Integer se, Integer oc, Integer no, Integer de, Integer all) {
		entity.setSubjectFinanceJanuary(ja);
		entity.setSubjectFinanceFebruary(fe);
		entity.setSubjectFinanceMarch(mar);
		entity.setSubjectFinanceApril(ap);
		entity.setSubjectFinanceMay(may);
		entity.setSubjectFinanceJune(jun);
		entity.setSubjectFinanceJuly(jul);
		entity.setSubjectFinanceAugust(au);
		entity.setSubjectFinanceSeptember(se);
		entity.setSubjectFinanceOctober(oc);
		entity.setSubjectFinanceNovember(no);
		entity.setSubjectFinanceDecember(de);
		entity.setSubjectFinanceAll(all);
	}

	/**
	 * 删除一个子科目
	 * @param id
	 * @return
	 */
	public int deleteOneSebject(Long id) {
		int result = 0;
		if (id == null || id <= 0)
			return result;
		FinanceOrganization entity = financeOrganizationDao.findById(id).orElse(null);
		if (entity == null)
			return result;
		FinanceOrganization parent = entity.getParentFinanceOrganization();
		financeOrganizationDao.delete(entity);
		Integer ja = 0, fe = 0, mar = 0, ap = 0, may = 0, jun = 0, jul = 0, au = 0, se = 0, oc = 0, no = 0, de = 0, all = 0;
		while (parent != null) {
			List<FinanceOrganization> findFinaceOrGanizationChild = financeOrganizationDao.findFinaceOrGanizationChild(parent.getId(), null);
			if (CollectionUtils.isEmpty(findFinaceOrGanizationChild)) {
				financeOrganizationDao.delete(parent);
				parent = parent.getParentFinanceOrganization();
			} else {
				ja = 0;
				fe = 0;
				mar = 0;
				ap = 0;
				may = 0;
				jun = 0;
				jul = 0;
				au = 0;
				se = 0;
				oc = 0;
				no = 0;
				de = 0;
				all = 0;
				for (FinanceOrganization fo : findFinaceOrGanizationChild) {
					ja += fo.getSubjectFinanceJanuary();
					fe += fo.getSubjectFinanceFebruary();
					mar += fo.getSubjectFinanceMarch();
					ap += fo.getSubjectFinanceApril();
					may += fo.getSubjectFinanceMay();
					jun += fo.getSubjectFinanceJune();
					jul += fo.getSubjectFinanceJuly();
					au += fo.getSubjectFinanceAugust();
					se += fo.getSubjectFinanceSeptember();
					oc += fo.getSubjectFinanceOctober();
					no += fo.getSubjectFinanceNovember();
					de += fo.getSubjectFinanceDecember();
				}
				all = ja + fe + ap + mar + may + jun + jul + au + se + oc + no + de;
				setMoney(parent, ja, fe, mar, ap, may, jun, jul, au, se, oc, no, de, all);
				financeOrganizationDao.save(parent);
				parent = parent.getParentFinanceOrganization();
			}
		}
		return 1;
	}

}
