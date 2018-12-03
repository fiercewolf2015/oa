package com.xyj.oa.log.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

/**
 * 展示业务实体:[{业务信息}]
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithTables {
	
	
	/**
	 * Columns : select
	 * @return
	 */
	WithTable[] withWithTables() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default LogConstants.SPLITOR_COMMA;
	
}
