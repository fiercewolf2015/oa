package com.xyj.oa.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

/**
 * 展示字段：字段显示名称:字段数据内容
 * select filed info
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithColumn {

	/**
	 * 数据列显示名称
	 * @return
	 */
	String text() default LogConstants.TEMPSTR;
	
	/**
	 * 数据列字段名称: Column Name - entity.property
	 * @return
	 */
	String name() default LogConstants.TEMPSTR;
	
	/**
	 * 链接符
	 * @return
	 */
	String joinor() default LogConstants.JOINOR_COLON;
	
	/**
	 * 分割符
	 * @return
	 */
	String splitor() default LogConstants.SPLITOR_SEMICOLON;
}
