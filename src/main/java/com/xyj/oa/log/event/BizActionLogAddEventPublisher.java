package com.xyj.oa.log.event;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.xyj.oa.log.aop.SpringContextUtil;
import com.xyj.oa.log.entity.BizActionLog;

/**
 * 事件发布:新增业务操作日志
 */
@Component
public class BizActionLogAddEventPublisher {

	public void publishEvent(BizActionLog bizActionLog){
		ApplicationContext applicationContext = SpringContextUtil.getCtx();
		BizActionLogAddEvent event = new BizActionLogAddEvent(applicationContext, bizActionLog);
		applicationContext.publishEvent(event);
	}
	
}
