package com.xyj.oa.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xyj.oa.entity.IdEntity;

@Entity
@Table(name = "t_financelines")
public class FinanceLine extends IdEntity {

	private Integer firstLine;// 超预算第一限度

	private Integer secondLine;// 超预算第二限度

	private Integer threeLine;// 超预算第三限度

	private Integer fourLine;// 超预算第四限度，暂时预留

	public Integer getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(Integer firstLine) {
		this.firstLine = firstLine;
	}

	public Integer getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(Integer secondLine) {
		this.secondLine = secondLine;
	}

	public Integer getThreeLine() {
		return threeLine;
	}

	public void setThreeLine(Integer threeLine) {
		this.threeLine = threeLine;
	}

	public Integer getFourLine() {
		return fourLine;
	}

	public void setFourLine(Integer fourLine) {
		this.fourLine = fourLine;
	}

}
