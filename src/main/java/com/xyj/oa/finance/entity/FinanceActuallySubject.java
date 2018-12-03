package com.xyj.oa.finance.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;
import com.xyj.oa.system.entity.FinanceSubject;

/**
 * 实际报销单对应科目明细
 */
@Entity
@Table(name = "t_financeactuallysubjects")
public class FinanceActuallySubject extends IdEntity {

	private FinanceSubject financeSubject;

	private Integer actuallyMoney;

	@ManyToOne
	@JoinColumn(name = "fac_id")
	public FinanceSubject getFinanceSubject() {
		return financeSubject;
	}

	public void setFinanceSubject(FinanceSubject financeSubject) {
		this.financeSubject = financeSubject;
	}

	public Integer getActuallyMoney() {
		return actuallyMoney;
	}

	public void setActuallyMoney(Integer actuallyMoney) {
		this.actuallyMoney = actuallyMoney;
	}

}
