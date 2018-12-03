package com.xyj.oa.title.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.title.entity.Title;

public interface TitleDaoCustom {

	List<Title> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord);

	Long getCountWithParams(String params);

	Title findByTitleName(String titleName);

	Title findByTitleNo(String titleNo);

	List<Map<String, Object>> findAllTitles();

}
