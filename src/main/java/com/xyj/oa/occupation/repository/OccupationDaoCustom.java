package com.xyj.oa.occupation.repository;

import java.util.List;
import java.util.Map;

import com.xyj.oa.occupation.entity.Occupation;

public interface OccupationDaoCustom {

	Occupation findByOccupationName(String occupationName);

	Occupation findByOccupationNo(String occupationNo);

	List<Map<String, Object>> loadOccupationTree();
}
