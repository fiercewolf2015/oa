package com.xyj.oa.log.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.log.annotation.WithColumn;
import com.xyj.oa.log.dto.BizActionLogDTO;
import com.xyj.oa.log.entity.BizActionLog;
import com.xyj.oa.log.repository.BizActionLogDao;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.StringHelper;

import ognl.Ognl;
import ognl.OgnlException;

@Component
@Transactional(readOnly=true)
public class BizActionLogService {

	private static Logger log = LoggerFactory.getLogger(BizActionLogService.class);
	
	@Autowired
	private BizActionLogDao bizActionLogDao;
	
	@Autowired
	private ShiroManager shiroManager;
	
	@Transactional(readOnly=false)
	public void save(BizActionLog entity) {
		entity.setCreateTime(DateUtil.getNowTimeStr());
		log.info("saving bizActionLong info ... " + entity.toString());
		bizActionLogDao.save(entity);
	}
	
	/**
	 * 设置当前登陆者的信息及权限信息
	 * @param entity
	 */
	public void setEntityUserInfo(BizActionLog entity){
		entity.setCreatorId(shiroManager.getCurrentUserId());
		entity.setCreatorName(shiroManager.getCurrentUserName());
	}
	
	public String findFieldByHql(String hql, Object[] params){
		List<String> list = bizActionLogDao.findFieldByHql(hql, params); 
		return  StringHelper.join(list, ",");
	}

	public String formatBizContent(BizActionLogDTO bizActionLogDTO) throws OgnlException{
		StringBuilder bizInfo = new StringBuilder();
		StringBuilder bizInfoHql = bizActionLogDTO.getFindBizInfoHql();
		WithColumn[] withColumns = bizActionLogDTO.getWithColumns();
		List<Object> list = bizActionLogDao.findFieldByHql(bizInfoHql.toString());
		for (Object entity : list){
			for (int i = 0; i < withColumns.length; i++){
				WithColumn wc = withColumns[i];
				Object value = Ognl.getValue(wc.name(), entity);
				bizInfo.append(wc.text()).append(wc.joinor()).append(value);
				if (i < withColumns.length -1)
					bizInfo.append(wc.splitor());
				else
					bizInfo.append(bizActionLogDTO.getTableSplitor());
			}
		}
		return bizInfo.toString();
	}
	
	public List<BizActionLog> findListWithParams(String params, int pageNo, int pageSize,Long userId){
		return bizActionLogDao.getListWithParams(params, pageNo, pageSize, userId);
	}
	
	public Long	findCountWithParams(String params, Long userId){
		return bizActionLogDao.getCountWithParams(params, userId);
	}
	
}
