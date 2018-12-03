package com.xyj.oa.system.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.system.entity.AssetType;

public interface AssetTypeDao extends PagingAndSortingRepository<AssetType, Long>, AssetTypeDaoCustom {

}
