package com.xyj.oa.occupation.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.occupation.entity.Occupation;

public interface OccupationDao extends PagingAndSortingRepository<Occupation, Long>,
		JpaSpecificationExecutor<Occupation>, OccupationDaoCustom {

}
