package com.xyj.oa.log.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

/**
 * 展示业务实体：{字段信息}
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithTable {
	
	/**
	 * table name : Entity name
	 * @return
	 */
	String name() default LogConstants.TEMPSTR;
	
	/**
	 * tabe name alias
	 * @return
	 */
	String alias() default LogConstants.ALIAS_TBL;
	
	/**
	 * Columns : select
	 * @return
	 */
	WithColumn[] withColumns() default {};
	
	/**
	 * Columns : where condition
	 * @return
	 */
	ConditionColumn[] conditionColumns() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default LogConstants.SPLITOR_SPOT;
	
}
