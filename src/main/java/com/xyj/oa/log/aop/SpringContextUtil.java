package com.xyj.oa.log.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 工具类：SpringContext
 */
@Component
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware{
	
	private static Logger log = LoggerFactory.getLogger(SpringContextUtil.class);
	
	private static ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
		checkApplicationContext();
		log.info("init this bean success ! ");
	}
	
	 /** 
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getBean(String name) {  
        checkApplicationContext();  
        return (T) ctx.getBean(name);  
    }  
  
    /** 
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 
     * 如果有多个Bean符合Class, 取出第一个. 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getBean(Class<T> requiredType) {  
        checkApplicationContext();  
        return (T) ctx.getBeansOfType(requiredType);  
    }  
      
    /** 
     * 清除applicationContext静态变量. 
     */  
    public static void cleanApplicationContext(){  
    	ctx = null;  
    }  
  
    public static void checkApplicationContext() {  
        if (ctx == null) {  
        	log.error("applicaitonContext 未注入.");
            throw new IllegalStateException("applicaitonContext 未注入.");  
        }  
    }
    
    public static ApplicationContext getCtx() {  
        checkApplicationContext();  
        return ctx;
    }  
    
}
