package com.xyj.oa.statistic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_contract_will_forms")
public class ContractWillForm extends IdEntity {

	private String applyDate;// 申请时间'

	private String companyName;

	private String projectName;

	private String instanceNum;

	private String contractNo;// 合同编号

	private String contractBeginDate;// 合同开始时间

	private String contractEndDate;// 合同结束时间

	private String contractTime;// 合同时限

	private String contractName;// 合同名称

	private String contractPrice;// 合同单价

	private String contractMonthPrice;// 合同月金额

	private String contractPriceTotal;// 合同总价

	private String paymentDate;// 收/付款时间

	private String paymentMoney;// 收/付款金额

	private String caiwuType;// 财务类型

	private String contractCount;// 合同数量

	private String invoiceDate;// 开具发票时间

	private String paymentTimeSlot;// 收付款时间段

	private String jiaName;// 甲方名称

	private String yiName;// 乙方名称

	private String bingName;// 丙方名称

	private String disifangName;// 第四方 手填

	private String ifFanHuan;// 是否返还

	private String yeShu;// 页数

	private String contractType;// 合同类型

	private String contractConten;// 合同内容

	private String reason;// 备注

	private String projectOperationType;// 项目业态类型

	private String areaStatistics;// 面积统计

	private String projectAddr;// 项目地址

	private String projectJiaName;// 项目甲方名称

	private String zhiChu;// 支出;

	private int flag = 0;// 合同来源 0为：流程 1为 历史

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractBeginDate() {
		return contractBeginDate;
	}

	public void setContractBeginDate(String contractBeginDate) {
		this.contractBeginDate = contractBeginDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPaymentTimeSlot() {
		return paymentTimeSlot;
	}

	public void setPaymentTimeSlot(String paymentTimeSlot) {
		this.paymentTimeSlot = paymentTimeSlot;
	}

	public String getJiaName() {
		return jiaName;
	}

	public void setJiaName(String jiaName) {
		this.jiaName = jiaName;
	}

	public String getYiName() {
		return yiName;
	}

	public void setYiName(String yiName) {
		this.yiName = yiName;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProjectOperationType() {
		return projectOperationType;
	}

	public void setProjectOperationType(String projectOperationType) {
		this.projectOperationType = projectOperationType;
	}

	public String getAreaStatistics() {
		return areaStatistics;
	}

	public void setAreaStatistics(String areaStatistics) {
		this.areaStatistics = areaStatistics;
	}

	public String getProjectAddr() {
		return projectAddr;
	}

	public void setProjectAddr(String projectAddr) {
		this.projectAddr = projectAddr;
	}

	public String getCaiwuType() {
		return caiwuType;
	}

	public void setCaiwuType(String caiwuType) {
		this.caiwuType = caiwuType;
	}

	public String getBingName() {
		return bingName;
	}

	public void setBingName(String bingName) {
		this.bingName = bingName;
	}

	public String getDisifangName() {
		return disifangName;
	}

	public void setDisifangName(String disifangName) {
		this.disifangName = disifangName;
	}

	public String getIfFanHuan() {
		return ifFanHuan;
	}

	public void setIfFanHuan(String ifFanHuan) {
		this.ifFanHuan = ifFanHuan;
	}

	public String getContractConten() {
		return contractConten;
	}

	public void setContractConten(String contractConten) {
		this.contractConten = contractConten;
	}

	public String getZhiChu() {
		return zhiChu;
	}

	public void setZhiChu(String zhiChu) {
		this.zhiChu = zhiChu;
	}

	public String getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(String contractPrice) {
		this.contractPrice = contractPrice;
	}

	public String getContractMonthPrice() {
		return contractMonthPrice;
	}

	public void setContractMonthPrice(String contractMonthPrice) {
		this.contractMonthPrice = contractMonthPrice;
	}

	public String getContractPriceTotal() {
		return contractPriceTotal;
	}

	public void setContractPriceTotal(String contractPriceTotal) {
		this.contractPriceTotal = contractPriceTotal;
	}

	public String getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(String paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}

	public String getYeShu() {
		return yeShu;
	}

	public void setYeShu(String yeShu) {
		this.yeShu = yeShu;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getContractTime() {
		return contractTime;
	}

	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}

	public String getProjectJiaName() {
		return projectJiaName;
	}

	public void setProjectJiaName(String projectJiaName) {
		this.projectJiaName = projectJiaName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(String instanceNum) {
		this.instanceNum = instanceNum;
	}

}
