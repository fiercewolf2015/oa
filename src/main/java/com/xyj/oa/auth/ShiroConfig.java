package com.xyj.oa.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class ShiroConfig {

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// 访问的是后端url地址为 /login的接口
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
//		shiroFilterFactoryBean.setSuccessUrl("/staff");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/api/**", "anon");
		filterChainDefinitionMap.put("/workflow/getAllWorkflowTyp", "anon");
		filterChainDefinitionMap.put("/postchange/data", "anon");
		filterChainDefinitionMap.put("/register/**", "anon");
		filterChainDefinitionMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc", new LoginRecordFilter());
		System.out.println("Shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
	}

	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setCacheManager(ehCacheManager());
		return securityManager;
	}

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
		return em;
	}

	@Bean
	public OaShiroRealm myShiroRealm() {
		OaShiroRealm myShiroRealm = new OaShiroRealm();
		return myShiroRealm;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
