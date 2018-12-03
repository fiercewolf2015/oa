package com.xyj.oa.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

/**
 * 业务日志
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog {
	

	/**
	 * 操作类型（更细一些）
	 * @return
	 */
	String actionType() default LogConstants.TEMPSTR;
	
	/**
	 * 业务类型(大项)
	 * @return
	 */
	String bizType() default LogConstants.TEMPSTR;
	
	/**
	 * 期望返回值表达式
	 * @return
	 */
	String expectedExp() default LogConstants.TEMPSTR;
	
	/**
	 * 业务标识信息表达式
	 * @return
	 */
	String bizInfoExp() default LogConstants.TEMPSTR;
}
