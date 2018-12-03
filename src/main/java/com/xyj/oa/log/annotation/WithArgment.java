package com.xyj.oa.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithArgment {

	/**
	 * 属性显示名称
	 * @return
	 */
	String text() default LogConstants.TEMPSTR;
	
	/**
	 * 参数位置
	 * @return
	 */
	int argIndex() default LogConstants.ARGINDEX;
	
	/**
	 * 参数类型
	 * @return
	 */
	int argType() default LogConstants.JSONSTR;
	
	/**
	 * 对象属性表达式
	 * @return
	 */
	String argExpress() default LogConstants.TEMPSTR;
	
	/**
	 * 链接符
	 * @return
	 */
	String joinor() default LogConstants.JOINOR_COLON;
}
