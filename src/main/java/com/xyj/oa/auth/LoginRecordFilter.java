package com.xyj.oa.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xyj.oa.log.aop.SpringContextUtil;
import com.xyj.oa.log.entity.BizActionLog;
import com.xyj.oa.log.event.BizActionLogAddEventPublisher;
import com.xyj.oa.log.service.BizActionLogService;

//@Component
public class LoginRecordFilter extends FormAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(LoginRecordFilter.class);

	public LoginRecordFilter() {
	}

	private static final String TEXT[] = { "登陆", "用户", "远程地址：", "远程主机：", "远程用户：", "浏览器" };

	@Override
	protected UsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password != null ? password.toCharArray() : null, rememberMe, host);
		return token;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		UsernamePasswordToken token = createToken(request, response);
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);// 正常验证
			subject.isPermitted("login");
			LOG.info(token.getUsername() + "  登录成功");
			saveLoginRecord(request);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			LOG.info(token.getUsername() + "  登录失败--" + e);
			return onLoginFailure(token, e, request, response);
		}
	}

	private void saveLoginRecord(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		StringBuilder bizContent = new StringBuilder();
		bizContent.append(TEXT[2]).append(request.getRemoteAddr()).append(TEXT[3]).append(request.getRemoteHost()).append(TEXT[4]).append(req.getRemoteUser())
				.append(TEXT[5]).append(req.getHeader("USER-AGENT"));
		BizActionLog bizActionLog = new BizActionLog();
		bizActionLog.setActionType(TEXT[0]);
		bizActionLog.setBizContent(bizContent.toString());
		bizActionLog.setBizType(TEXT[1]);

		BizActionLogService bizActionLogService = (BizActionLogService) SpringContextUtil.getBean("bizActionLogService");
		BizActionLogAddEventPublisher publisher = (BizActionLogAddEventPublisher) SpringContextUtil.getBean("bizActionLogAddEventPublisher");

		bizActionLogService.setEntityUserInfo(bizActionLog);
		publisher.publishEvent(bizActionLog);
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

}
