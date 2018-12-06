package com.xyj.oa.system.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.api.exception.ApiException;
import com.xyj.oa.api.vo.APIErrorCode;
import com.xyj.oa.api.vo.ApiResult;
import com.xyj.oa.api.vo.NoticeVo;
import com.xyj.oa.api.vo.NoticeVoResult;
import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.department.entity.Department;
import com.xyj.oa.department.repository.DepartMentDao;
import com.xyj.oa.finance.entity.BudgetModel;
import com.xyj.oa.finance.repository.BudgetModelDao;
import com.xyj.oa.finance.repository.FinanceApprovalDao;
import com.xyj.oa.post.entity.Post;
import com.xyj.oa.post.repository.PostDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.statistic.entity.ContractWillForm;
import com.xyj.oa.statistic.repository.ContractWillFormDao;
import com.xyj.oa.system.entity.AssetType;
import com.xyj.oa.system.entity.BudgetCommissioner;
import com.xyj.oa.system.entity.FinanceLine;
import com.xyj.oa.system.entity.FinanceSubject;
import com.xyj.oa.system.entity.LeaveType;
import com.xyj.oa.system.entity.Notice;
import com.xyj.oa.system.entity.NoticeStaff;
import com.xyj.oa.system.entity.OverTimeType;
import com.xyj.oa.system.entity.SealType;
import com.xyj.oa.system.repository.AssetTypeDao;
import com.xyj.oa.system.repository.BudgetCommissionerDao;
import com.xyj.oa.system.repository.FinanceLineDao;
import com.xyj.oa.system.repository.FinanceSubjectDao;
import com.xyj.oa.system.repository.LeaveTypeDao;
import com.xyj.oa.system.repository.NoticeDao;
import com.xyj.oa.system.repository.NoticeStaffDao;
import com.xyj.oa.system.repository.OverTimeTypeDao;
import com.xyj.oa.system.repository.SealTypeDao;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.FileManager;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;
import com.xyj.oa.workflow.entity.WorkFlowInstance;

import net.sf.json.JSONObject;

@Component
@Transactional
public class SystemService {

	@Autowired
	private OverTimeTypeDao overTimeTypeDao;

	@Autowired
	private LeaveTypeDao leaveTypeDao;

	@Autowired
	private FinanceSubjectDao financeSubjectDao;

	@Autowired
	private SealTypeDao sealTypeDao;

	@Autowired
	private AssetTypeDao assetTypeDao;

	@Autowired
	private BudgetCommissionerDao budgetCommissionerDao;

	@Autowired
	private FinanceLineDao financeLineDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FinanceApprovalDao financeApprovalDao;

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private NoticeStaffDao noticeStaffDao;

	@Autowired
	private BudgetModelDao budgetModelDao;

	@Autowired
	private ContractWillFormDao contractWillFormDao;

	@Autowired
	private DepartMentDao departMentDao;

	public Long findOvertimeCountWithParams(String params) {
		return overTimeTypeDao.getCountWithParams(params);
	}

	public Long findLeaveTypeCountWithParams(String params) {
		return leaveTypeDao.getCountWithParams(params);
	}

	public Long findSealTypeCountWithParams(String params) {
		return sealTypeDao.getCountWithParams(params);
	}

	public Long findAssetTypeCountWithParams(String params) {
		return assetTypeDao.getCountWithParams(params);
	}

	public Long findNoticeCountWithParams(String params, Long staffId) {
		return noticeDao.getCountWithParams(params, staffId);
	}

	public Long findBudgetCommissionerCountWithParams(String params) {
		return budgetCommissionerDao.getCountWithParams(params);
	}

	public Long findFinanceLineCountWithParams(String params) {
		return financeLineDao.getCountWithParams(params);
	}

	public Long findContractWillCountWithParams(String params) {
		return contractWillFormDao.getCountWithParams(params);
	}

	public List<OverTimeType> findOvertimeListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return overTimeTypeDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<LeaveType> findLeaveTypeListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return leaveTypeDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<SealType> findSealTypeListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return sealTypeDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<AssetType> findAssetTypeListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return assetTypeDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<BudgetCommissioner> findBudgetCommissionerListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return budgetCommissionerDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<FinanceLine> findFinanceLinesListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return financeLineDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<ContractWillForm> findContractWillsListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return contractWillFormDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public List<Notice> findNoticeListWithParams(String params, Long staffId, int pageNo, int pageSize, String sidx, String sord) {
		List<Notice> listWithParams = noticeDao.getListWithParams(params, staffId, pageNo, pageSize, sidx, sord);
		for (Notice notice : listWithParams)
			notice.setCreateTimeStr(DateUtil.formatTimestampToStringByFmt(notice.getCreateTime(), DateUtil.NORMAL_TIME_FORMAT));
		return listWithParams;
	}

	public Long findNoticeStaffCountWithParams(String params, Long staffId) {
		return noticeStaffDao.getCountWithParams(params, staffId);
	}

	public List<NoticeStaff> findNoticeStaffListWithParams(String params, Long staffId, int pageNo, int pageSize, String sidx, String sord) {
		List<NoticeStaff> listWithParams = noticeStaffDao.getListWithParams(params, staffId, pageNo, pageSize, sidx, sord);
		for (NoticeStaff noticeStaff : listWithParams) {
			Notice notice = noticeStaff.getNotice();
			notice.setCreateTimeStr(DateUtil.formatTimestampToStringByFmt(notice.getCreateTime(), DateUtil.NORMAL_TIME_FORMAT));
		}
		return listWithParams;
	}

	public int saveOvertimeType(String params) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("addOvertimeTypeName");
		String remark = jsonObject.getString("remark");
		OverTimeType overTimeType = overTimeTypeDao.findByName(name);
		if (overTimeType != null)
			return -100;
		overTimeType = new OverTimeType();
		overTimeType.setName(name);
		overTimeType.setRemark(remark);
		overTimeType.setUpdateTime(TfOaUtil.getCurTime());
		overTimeType.setCreateTime(TfOaUtil.getCurTime());
		overTimeTypeDao.save(overTimeType);
		return 1;
	}

	public int editOvertimeType(String params, Long overtimeTypeId) {
		if (StringHelper.isEmpty(params) || overtimeTypeId == null)
			return 0;
		OverTimeType findOne = overTimeTypeDao.findById(overtimeTypeId).orElse(null);
		if (findOne == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editovertimeTypeName = jsonObject.getString("editovertimeTypeName");
		OverTimeType findByName = overTimeTypeDao.findByName(editovertimeTypeName);
		if (findByName != null && findByName.getId().longValue() != overtimeTypeId.longValue())
			return 2;
		findOne.setUpdateTime(TfOaUtil.getCurTime());
		findOne.setName(editovertimeTypeName);
		findOne.setRemark(jsonObject.getString("editRemark"));
		overTimeTypeDao.save(findOne);
		return 1;
	}

	public int deleteOverTimeType(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] staffIdArr = ids.split(",");
		for (String staffId : staffIdArr) {
			OverTimeType overTimeType = overTimeTypeDao.findById(Long.valueOf(staffId)).orElse(null);
			if (overTimeType == null)
				continue;
			overTimeTypeDao.delete(overTimeType);
		}
		return 1;
	}

	public int saveLeaveType(String params) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("addLeaveTypeName");
		String remark = jsonObject.getString("remark");
		LeaveType leaveType = leaveTypeDao.findByName(name);
		if (leaveType != null)
			return -100;
		leaveType = new LeaveType();
		leaveType.setName(name);
		leaveType.setRemark(remark);
		leaveType.setUpdateTime(TfOaUtil.getCurTime());
		leaveType.setCreateTime(TfOaUtil.getCurTime());
		leaveTypeDao.save(leaveType);
		return 1;
	}

	public int addContractWill(String params, int flag, String instanceNum, String createTime) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String contractCount = null;
		String contractNo1 = null;
		String contractNo2 = null;
		String contractNo3 = null;
		String contractNo4 = null;
		String contractBeginDate = null;
		String contractEndDate = null;
		String contractName = null;
		String contractPrice = null;
		String contractMonthPrice = null;
		String contractPriceTotal = null;
		String paymentDate = null;
		String paymentMoney = null;
		String invoiceDate = null;
		String paymentTimeSlot = null;
		String jiaName = null;
		String yiName = null;
		if (jsonObject.has("contractNo1"))
			contractNo1 = jsonObject.getString("contractNo1");
		if (jsonObject.has("contractNo2"))
			contractNo2 = jsonObject.getString("contractNo2");
		if (jsonObject.has("contractNo3"))
			contractNo3 = jsonObject.getString("contractNo3");
		if (jsonObject.has("contractNo4"))
			contractNo4 = jsonObject.getString("contractNo4");
		if (jsonObject.has("contractCount"))
			contractCount = jsonObject.getString("contractCount");
		if (jsonObject.has("contractBeginDate"))
			contractBeginDate = jsonObject.getString("contractBeginDate");
		if (jsonObject.has("contractEndDate"))
			contractEndDate = jsonObject.getString("contractEndDate");
		if (jsonObject.has("contractName"))
			contractName = jsonObject.getString("contractName");
		if (jsonObject.has("contractPrice"))
			contractPrice = jsonObject.getString("contractPrice");
		if (jsonObject.has("contractMonthPrice"))
			contractMonthPrice = jsonObject.getString("contractMonthPrice");
		if (jsonObject.has("contractPriceTotal"))
			contractPriceTotal = jsonObject.getString("contractPriceTotal");
		if (jsonObject.has("paymentDate"))
			paymentDate = jsonObject.getString("paymentDate");
		if (jsonObject.has("paymentMoney"))
			paymentMoney = jsonObject.getString("paymentMoney");
		if (jsonObject.has("invoiceDate"))
			invoiceDate = jsonObject.getString("invoiceDate");
		if (jsonObject.has("paymentTimeSlot"))
			paymentTimeSlot = jsonObject.getString("paymentTimeSlot");
		if (jsonObject.has("jiaName"))
			jiaName = jsonObject.getString("jiaName");
		if (jsonObject.has("yiName"))
			yiName = jsonObject.getString("yiName");
		String bingName = null;
		if (flag == 1) {
			if (jsonObject.has("sanName"))
				bingName = jsonObject.getString("sanName");
		} else {
			if (jsonObject.has("bingName"))
				bingName = jsonObject.getString("bingName");
		}
		String contractType = null;
		String reason = null;
		String projectOperationType = null;
		String areaStatistics = null;
		String projectAddr = null;
		String projectJIaName = null;
		String companyId = null;
		String companyName = null;
		String deptNameId = null;
		String deptName = null;
		if (jsonObject.has("contractType"))
			contractType = jsonObject.getString("contractType");
		if (jsonObject.has("reason"))
			reason = jsonObject.getString("reason");
		if (jsonObject.has("projectOperationType"))
			projectOperationType = jsonObject.getString("projectOperationType");
		if (jsonObject.has("areaStatistics"))
			areaStatistics = jsonObject.getString("areaStatistics");
		if (jsonObject.has("projectAddr"))
			projectAddr = jsonObject.getString("projectAddr");
		if (jsonObject.has("projectJIaName"))
			projectJIaName = jsonObject.getString("projectJIaName");
		if (jsonObject.has("company"))
			companyId = jsonObject.getString("company");
		if (jsonObject.has("deptName"))
			deptNameId = jsonObject.getString("deptName");
		String contractNo = contractNo1 + "-" + contractNo2 + "-" + contractNo3 + "-" + contractNo4;
		//		ContractWillForm contractWillForm = contractWillFormDao.findByContractNo(contractNo);
		//		if (contractWillForm != null)
		//			return -100;
		ContractWillForm contractWillForm = new ContractWillForm();
		if (!StringHelper.isEmpty(companyId)) {
			Department department = departMentDao.findById(Long.valueOf(companyId)).orElse(null);
			if (department != null)
				companyName = department.getDepartmentName();
		}
		if (!StringHelper.isEmpty(deptNameId)) {
			Department department = departMentDao.findById(Long.valueOf(deptNameId)).orElse(null);
			if (department != null)
				deptName = department.getDepartmentName();
		}
		contractWillForm = new ContractWillForm();
		contractWillForm.setAreaStatistics(areaStatistics);
		contractWillForm.setBingName(bingName);
		contractWillForm.setContractBeginDate(contractBeginDate);
		contractWillForm.setContractEndDate(contractEndDate);
		contractWillForm.setContractMonthPrice(contractMonthPrice);
		contractWillForm.setContractName(contractName);
		contractWillForm.setContractNo(contractNo);
		contractWillForm.setApplyDate(createTime);
		contractWillForm.setCompanyName(companyName);
		contractWillForm.setProjectName(deptName);
		contractWillForm.setInstanceNum(instanceNum);
		try {
			contractWillForm.setContractTime(String.valueOf(DateUtil.getDaySpace(contractBeginDate, contractEndDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		contractWillForm.setContractPrice(contractPrice);
		contractWillForm.setContractPriceTotal(contractPriceTotal);
		contractWillForm.setContractType(contractType);
		contractWillForm.setInvoiceDate(invoiceDate);
		contractWillForm.setJiaName(jiaName);
		contractWillForm.setPaymentDate(paymentDate);
		contractWillForm.setPaymentMoney(paymentMoney);
		contractWillForm.setPaymentTimeSlot(paymentTimeSlot);
		contractWillForm.setProjectAddr(projectAddr);
		contractWillForm.setProjectJiaName(projectJIaName);
		contractWillForm.setProjectOperationType(projectOperationType);
		contractWillForm.setReason(reason);
		contractWillForm.setYiName(yiName);
		contractWillForm.setContractCount(contractCount);
		contractWillForm.setFlag(flag);
		contractWillFormDao.save(contractWillForm);
		return 1;
	}

	public int saveSealType(String params) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("addSealTypeName");
		String remark = jsonObject.getString("remark");
		SealType sealType = sealTypeDao.findByName(name);
		if (sealType != null)
			return -100;
		sealType = new SealType();
		sealType.setName(name);
		sealType.setRemark(remark);
		sealType.setUpdateTime(DateUtil.getCurDateStr());
		sealType.setCreateTime(DateUtil.getCurDateStr());
		sealTypeDao.save(sealType);
		return 1;
	}

	public int saveAssetType(String params) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("addassetTypeName");
		String remark = jsonObject.getString("remark");
		AssetType assetType = assetTypeDao.findByName(name);
		if (assetType != null)
			return -100;
		assetType = new AssetType();
		assetType.setName(name);
		assetType.setRemark(remark);
		assetType.setUpdateTime(DateUtil.getCurDateStr());
		assetType.setCreateTime(DateUtil.getCurDateStr());
		assetTypeDao.save(assetType);
		return 1;
	}

	public int addNotice(String name, String remark, String staffIds, String[] path) {
		Notice notice = new Notice();
		notice.setTitle(name);
		notice.setMessage(remark);
		if (path != null && path.length > 0) {
			notice.setAttachmentPath(path[0]);
			notice.setAttachmentName(path[1]);
		}
		notice.setCreateTime(DateUtil.getNowTimestamp());
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		notice.setPromotStaff(user.getStaff());
		noticeDao.save(notice);
		Set<Staff> acceptStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, null, null, null);
		for (Staff staff : acceptStaffs) {
			NoticeStaff noticeStaff = new NoticeStaff();
			noticeStaff.setNotice(notice);
			noticeStaff.setStaff(staff);
			noticeStaffDao.save(noticeStaff);
		}
		return 1;
	}

	public int addNoticeForInstance(String name, String remark, String staffIds, String[] path, WorkFlowInstance instance) {
		Notice notice = new Notice();
		notice.setTitle(name);
		notice.setMessage(remark);
		if (path != null && path.length > 0) {
			notice.setAttachmentPath(path[0]);
			notice.setAttachmentName(path[1]);
		}
		notice.setCreateTime(DateUtil.getNowTimestamp());
		//		ShiroUser shiroUser = shiroManager.getCurrentUser();
		//		if (shiroUser == null)
		//			return 0;
		//		Long userd = shiroUser.getId();
		//		User user = userDao.findOne(userd);
		Staff promotStaff = staffDao.findById(instance.getStaffId()).get();
		notice.setPromotStaff(promotStaff);
		noticeDao.save(notice);
		Set<Staff> acceptStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, null, null, null);
		for (Staff staff : acceptStaffs) {
			NoticeStaff noticeStaff = new NoticeStaff();
			noticeStaff.setNotice(notice);
			noticeStaff.setStaff(staff);
			noticeStaffDao.save(noticeStaff);
		}
		return 1;
	}

	public int saveBudgetCommissioner(String params) {
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("addbudgetCommissionerName");
		String remark = jsonObject.getString("remark");
		BudgetCommissioner budgetCommissioner = budgetCommissionerDao.findByName(name);
		if (budgetCommissioner != null)
			return -100;
		budgetCommissioner = new BudgetCommissioner();
		budgetCommissioner.setName(name);
		budgetCommissioner.setRemark(remark);
		budgetCommissioner.setUpdateTime(DateUtil.getCurDateStr());
		budgetCommissioner.setCreateTime(DateUtil.getCurDateStr());
		budgetCommissionerDao.save(budgetCommissioner);
		return 1;
	}

	public int editLeaveType(String params, Long leaveTypeId) {
		if (StringHelper.isEmpty(params) || leaveTypeId == null)
			return 0;
		LeaveType findOne = leaveTypeDao.findById(leaveTypeId).orElse(null);
		if (findOne == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editleaveTypeName = jsonObject.getString("editleaveTypeName");
		LeaveType findByName = leaveTypeDao.findByName(editleaveTypeName);
		if (findByName != null && findByName.getId().longValue() != leaveTypeId.longValue())
			return 2;
		findOne.setUpdateTime(TfOaUtil.getCurTime());
		findOne.setName(editleaveTypeName);
		findOne.setRemark(jsonObject.getString("editRemark"));
		leaveTypeDao.save(findOne);
		return 1;
	}

	public int editSealType(String params, Long sealTypeId) {
		if (StringHelper.isEmpty(params) || sealTypeId == null)
			return 0;
		SealType findOne = sealTypeDao.findById(sealTypeId).orElse(null);
		if (findOne == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editsealTypeName = jsonObject.getString("editsealTypeName");
		SealType findByName = sealTypeDao.findByName(editsealTypeName);
		if (findByName != null && findByName.getId().longValue() != sealTypeId.longValue())
			return 2;
		findOne.setUpdateTime(DateUtil.getCurDateStr());
		findOne.setName(editsealTypeName);
		findOne.setRemark(jsonObject.getString("editRemark"));
		sealTypeDao.save(findOne);
		return 1;
	}

	public int editAssetType(String params, Long assetTypeId) {
		if (StringHelper.isEmpty(params) || assetTypeId == null)
			return 0;
		AssetType findOne = assetTypeDao.findById(assetTypeId).orElse(null);
		if (findOne == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editAssetTypeName = jsonObject.getString("editassetTypeName");
		AssetType findByName = assetTypeDao.findByName(editAssetTypeName);
		if (findByName != null && findByName.getId().longValue() != assetTypeId.longValue())
			return 2;
		findOne.setUpdateTime(DateUtil.getCurDateStr());
		findOne.setName(editAssetTypeName);
		findOne.setRemark(jsonObject.getString("editRemark"));
		assetTypeDao.save(findOne);
		return 1;
	}

	public int editNotice(String params, Long noticeId) {
		if (StringHelper.isEmpty(params) || noticeId == null)
			return 0;
		Notice notice = noticeDao.findById(noticeId).orElse(null);
		if (notice == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editNoticeName = jsonObject.getString("editNoticeName");
		String editNoticeContent = jsonObject.getString("editNoticeContent");
		notice.setTitle(editNoticeName);
		notice.setMessage(editNoticeContent);
		noticeDao.save(notice);
		return 1;
	}

	public int editBudgetCommissioner(String params, Long budgetCommissionerId) {
		if (StringHelper.isEmpty(params) || budgetCommissionerId == null)
			return 0;
		BudgetCommissioner findOne = budgetCommissionerDao.findById(budgetCommissionerId).orElse(null);
		if (findOne == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editBudgetCommissionerName = jsonObject.getString("editbudgetCommissionerName");
		BudgetCommissioner findByName = budgetCommissionerDao.findByName(editBudgetCommissionerName);
		if (findByName != null && findByName.getId().longValue() != budgetCommissionerId.longValue())
			return 2;
		findOne.setUpdateTime(DateUtil.getCurDateStr());
		findOne.setName(editBudgetCommissionerName);
		findOne.setRemark(jsonObject.getString("editRemark"));
		budgetCommissionerDao.save(findOne);
		return 1;
	}

	public int deleteLeaveType(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] leaveTypeIdArr = ids.split(",");
		for (String leaveTypeId : leaveTypeIdArr) {
			LeaveType leaveType = leaveTypeDao.findById(Long.valueOf(leaveTypeId)).orElse(null);
			if (leaveType == null)
				continue;
			leaveTypeDao.delete(leaveType);
		}
		return 1;
	}

	public int deleteContractWill(String contractIds) {
		if (StringHelper.isEmpty(contractIds))
			return 0;
		String[] contractWillIdArr = contractIds.split(",");
		for (String cwId : contractWillIdArr) {
			ContractWillForm contractWillForm = contractWillFormDao.findById(Long.valueOf(cwId)).orElse(null);
			if (contractWillForm == null)
				continue;
			contractWillFormDao.delete(contractWillForm);
		}
		return 1;
	}

	public int deleteSealType(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] sealTypeIdArr = ids.split(",");
		for (String sealTypeId : sealTypeIdArr) {
			SealType sealType = sealTypeDao.findById(Long.valueOf(sealTypeId)).orElse(null);
			if (sealType == null)
				continue;
			sealTypeDao.delete(sealType);
		}
		return 1;
	}

	public int deleteAssetType(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] assetTypeIdArr = ids.split(",");
		for (String assetTypeId : assetTypeIdArr) {
			AssetType assetType = assetTypeDao.findById(Long.valueOf(assetTypeId)).orElse(null);
			if (assetType == null)
				continue;
			assetTypeDao.delete(assetType);
		}
		return 1;
	}

	public int deleteNotice(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] noticeIdsArr = ids.split(",");
		for (String noticeId : noticeIdsArr) {
			Notice notice = noticeDao.findById(Long.valueOf(noticeId)).orElse(null);
			if (notice == null)
				continue;
			Set<NoticeStaff> noticeStaffs = notice.getNoticeStaffs();
			for (NoticeStaff noticeStaff : noticeStaffs)
				noticeStaffDao.delete(noticeStaff);
			FileManager.delFile(notice.getAttachmentPath());
			noticeDao.delete(notice);
		}
		return 1;
	}

	public int deleteFinanceLine(Long id) {
		if (id == null || id <= 0)
			return 0;
		FinanceLine findOne = financeLineDao.findById(id).orElse(null);
		if (findOne == null)
			return 0;
		financeLineDao.delete(findOne);
		return 1;
	}

	public int deleteBudgetCommissioner(String ids) {
		if (StringHelper.isEmpty(ids))
			return 0;
		String[] budgetCommisionerIdArr = ids.split(",");
		for (String budgetCommissionerId : budgetCommisionerIdArr) {
			BudgetCommissioner budgetCommissioner = budgetCommissionerDao.findById(Long.valueOf(budgetCommissionerId)).orElse(null);
			if (budgetCommissioner == null)
				continue;
			budgetCommissionerDao.delete(budgetCommissioner);
		}
		return 1;
	}

	public int saveFinanceSubject(String params, Long pId, String postsIds) {
		if (StringHelper.isEmpty(params) || pId <= 0)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String name = jsonObject.getString("financeSubjectName");
		String remark = jsonObject.getString("remarks");
		FinanceSubject financeSubject = financeSubjectDao.findByName(name);
		if (financeSubject != null)
			return 2;
		financeSubject = new FinanceSubject();
		financeSubject.setName(name);
		financeSubject.setRemark(remark);
		financeSubject.setCreateTime(TfOaUtil.getCurTime());
		FinanceSubject parent = null;
		if (pId != null)
			parent = financeSubjectDao.findById(pId).orElse(null);
		Integer level = 1;
		if (parent != null) {
			level = parent.getLevel() + 1;
			financeSubject.setParentFinanceSubject(parent);
		}
		financeSubject.setLevel(level);
		financeSubject.setType(Integer.valueOf(jsonObject.getString("type")));
		if (StringHelper.isNotEmpty(postsIds)) {
			Set<Post> findByPostIds = postDao.findByPostIds(postsIds);
			financeSubject.getPosts().clear();
			financeSubject.getPosts().addAll(findByPostIds);
		}
		financeSubjectDao.save(financeSubject);
		return 1;
	}

	public int editFinanceSubject(String params, Long financeSubjectId, String postsIds) {
		if (StringHelper.isEmpty(params) || financeSubjectId == null)
			return 0;
		FinanceSubject financeSubject = financeSubjectDao.findById(financeSubjectId).orElse(null);
		if (financeSubject == null)
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String editFinanceSubjectName = jsonObject.getString("financeSubjectName");
		FinanceSubject financeSubject2 = financeSubjectDao.findByName(editFinanceSubjectName);
		if (financeSubject2 != null && financeSubject2.getId().longValue() != financeSubjectId.longValue())
			return 2;
		financeSubject.setName(editFinanceSubjectName);
		financeSubject.setRemark(jsonObject.getString("remarks"));
		financeSubject.setType(Integer.valueOf(jsonObject.getString("type")));
		if (StringHelper.isNotEmpty(postsIds)) {
			Set<Post> findByPostIds = postDao.findByPostIds(postsIds);
			financeSubject.getPosts().clear();
			financeSubject.getPosts().addAll(findByPostIds);
		}
		financeSubjectDao.save(financeSubject);
		return 1;
	}

	public int deleteFinanceSubject(Long id) {
		if (id == null || id <= 0)
			return 0;
		BudgetModel budgetModel = budgetModelDao.findBySubId(id);
		if (budgetModel != null)
			return -100;
		List<FinanceSubject> list = financeSubjectDao.findByParentId(id);
		if (CollectionUtils.isNotEmpty(list))
			for (FinanceSubject financeSubject : list)
				financeSubjectDao.delete(financeSubject);
		financeSubjectDao.deleteById(id);
		return 1;
	}

	public List<Map<String, Object>> getAllOvertimeTypes() {
		return overTimeTypeDao.findAllOverTimeTypes();
	}

	public List<Map<String, Object>> getAllLeaveTypes() {
		return leaveTypeDao.findAllLeaveTypes();
	}

	public List<Map<String, Object>> getAllSealTypes() {
		return sealTypeDao.findAllSealTypes();
	}

	public List<Map<String, Object>> getAllAssetTypes() {
		return assetTypeDao.findAllAssetTypes();
	}

	public List<Map<String, Object>> getAllBudgetCommissioners() {
		return budgetCommissionerDao.findAllBudgetCommissioners();
	}

	public List<Map<String, Object>> loadFinanceSubjectTree() {
		List<Map<String, Object>> loadFinanceSubjectTree = financeSubjectDao.loadFinanceSubjectTree();
		List<Long> postIds = null;
		for (Map<String, Object> map : loadFinanceSubjectTree) {
			Long id = ((BigInteger) map.get("id")).longValue();
			FinanceSubject findOne = financeSubjectDao.findById(id).get();
			Set<Post> posts = findOne.getPosts();
			postIds = new ArrayList<>();
			for (Post post : posts) {
				postIds.add(post.getId());
			}
			map.put("postIds", StringHelper.join(postIds, ","));
		}
		return loadFinanceSubjectTree;
	}

	public List<Map<String, Object>> loadSecondFinanceSubjectTree() {
		List<Map<String, Object>> loadFinanceSubjectTree = financeSubjectDao.loadLevelFinanceSubjectTree(null, 2);
		return loadFinanceSubjectTree;
	}

	public List<Map<String, Object>> loadThreeFinanceSubjectTree(Long pId) {
		List<Map<String, Object>> loadFinanceSubjectTree = financeSubjectDao.loadLevelFinanceSubjectTree(pId, 3);
		return loadFinanceSubjectTree;
	}

	public String getOverTimeTypeNameById(Long overTimeTypeId) {
		if (overTimeTypeId == null || overTimeTypeId <= 0)
			return "";
		OverTimeType overTimeType = overTimeTypeDao.findById(overTimeTypeId).orElse(null);
		if (overTimeType == null)
			return "";
		return overTimeType.getName();
	}

	public String getCommissionerNameById(Long id) {
		if (id == null || id <= 0)
			return "";
		BudgetCommissioner findOne = budgetCommissionerDao.findById(id).orElse(null);
		if (findOne == null)
			return "";
		return findOne.getName();
	}

	public String getAssetTypeNameById(Long assetTypeId) {
		if (assetTypeId == null || assetTypeId <= 0)
			return "";
		AssetType findOne = assetTypeDao.findById(assetTypeId).orElse(null);
		if (findOne == null)
			return "";
		return findOne.getName();
	}

	public String getSealTypeNameById(Long sealTypeId) {
		if (sealTypeId == null || sealTypeId <= 0)
			return "";
		SealType sealType = sealTypeDao.findById(sealTypeId).orElse(null);
		if (sealType == null)
			return "";
		return sealType.getName();
	}

	public Integer[] getFinanceLine() {
		FinanceLine financeLine = financeLineDao.getFinanceLine();
		Integer[] returnValue = new Integer[3];
		returnValue[0] = financeLine.getFirstLine();
		returnValue[1] = financeLine.getSecondLine();
		returnValue[2] = financeLine.getThreeLine();
		return returnValue;
	}

	public int addFinanceLine(String params) {
		FinanceLine financeLine = financeLineDao.getFinanceLine();
		if (financeLine == null)
			financeLine = new FinanceLine();
		else
			return 100;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String firstLine = jsonObject.getString("firstLine");
		String secondLine = jsonObject.getString("secondLine");
		String threeLine = jsonObject.getString("threeLine");
		financeLine.setFirstLine(Integer.valueOf(firstLine));
		financeLine.setSecondLine(Integer.valueOf(secondLine));
		financeLine.setThreeLine(Integer.valueOf(threeLine));
		financeLineDao.save(financeLine);
		return 1;
	}

	public int getBalanceById(Long subjectId) {
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		Department department = user.getStaff().getDepartment();
		return financeApprovalDao.findFinanceSubjectBalance(department.getId(), subjectId);
	}

	public ApiResult getNoticeList(String name, Long userId, int unread, int pageNo, int pageSize) throws ApiException {
		User user = userDao.findById(userId).orElse(null);
		if (user == null)
			throw new ApiException(APIErrorCode.UserNoExist);
		Long staffId = user.getStaff().getId();
		StringBuilder params = new StringBuilder();
		params.append("{" + "\"noticeName\"" + ":\"").append(name).append("\"," + "\"staffId\"" + ":").append(staffId).append("," + "\"unRead\"" + ":")
				.append(unread).append("}");
		List<Notice> list = findNoticeListWithParams(params.toString(), null, pageNo, pageSize, null, null);
		ApiResult result = null;
		List<NoticeVo> noticeVos = new ArrayList<NoticeVo>();
		for (int i = 0; i < list.size(); i++) {
			Notice notice = list.get(i);
			NoticeVo noticeVo = new NoticeVo(notice);
			noticeVos.add(noticeVo);
		}
		result = new NoticeVoResult(noticeVos);
		return result;
	}

	public List<ContractWillForm> findAllCwByParams(String params) {
		return contractWillFormDao.findAllCwByParams(params);
	}

	public Long getUnreadNum(Long staffId) {
		return noticeDao.getUnreadNum(staffId);
	}

	public int changeReadState(String noticeIds) {
		if (StringHelper.isEmpty(noticeIds))
			return 0;
		String[] noticeIdsArr = noticeIds.split(",");
		for (String noticeId : noticeIdsArr) {
			NoticeStaff noticeStaff = noticeStaffDao.findById(Long.valueOf(noticeId)).get();
			noticeStaff.setUnread(1);
			noticeStaffDao.save(noticeStaff);
		}
		return 1;
	}

}
