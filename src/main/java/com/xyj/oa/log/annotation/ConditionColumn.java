package com.xyj.oa.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionColumn {

	/**
	 * 参数位置
	 * @return
	 */
	int argIndex() default LogConstants.ARGINDEX;

	/**
	 * 对象属性表达式
	 * @return
	 */
	String argExpress() default LogConstants.TEMPSTR;

	/**
	 * 目标方法参数类型：object or json string
	 * @return
	 */
	int argType() default LogConstants.OBJECT;

	/**
	 * 数据列字段名称: Column Name - entity.property
	 * @return
	 */
	String name() default LogConstants.TEMPSTR;

	/**
	 * where 语句操作符
	 * @return
	 */
	String operator() default LogConstants.EQUALS;
}
