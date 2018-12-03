package com.xyj.oa.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;

@Configuration
public class WebConfig {

	@Bean
	public FilterRegistrationBean<SiteMeshFilter> filterRegistrationBean() {
		FilterRegistrationBean<SiteMeshFilter> filterRegistrationBean = new FilterRegistrationBean<SiteMeshFilter>();
		filterRegistrationBean.setFilter(new SiteMeshFilter());
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

}
