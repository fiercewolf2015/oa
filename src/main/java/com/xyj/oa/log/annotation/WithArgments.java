package com.xyj.oa.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xyj.oa.log.constants.LogConstants;

/**
 * 业务信息
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithArgments {
	
	/**
	 * Columns : select
	 * @return
	 */
	WithArgment[] withArgments() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default LogConstants.SPLITOR_COMMA;

}
