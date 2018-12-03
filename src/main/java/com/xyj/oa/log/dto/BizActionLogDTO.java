package com.xyj.oa.log.dto;

import com.xyj.oa.log.annotation.WithColumn;


public class BizActionLogDTO {

	private WithColumn[] withColumns;
	
	private String tableSplitor;
	
	private StringBuilder  findBizInfoHql;

	public WithColumn[] getWithColumns() {
		return withColumns;
	}

	public StringBuilder getFindBizInfoHql() {
		return findBizInfoHql;
	}

	public void setWithColumns(WithColumn[] withColumns) {
		this.withColumns = withColumns;
	}

	public void setFindBizInfoHql(StringBuilder findBizInfoHql) {
		this.findBizInfoHql = findBizInfoHql;
	}

	public String getTableSplitor() {
		return tableSplitor;
	}

	public void setTableSplitor(String tableSplitor) {
		this.tableSplitor = tableSplitor;
	}
	
	
}
