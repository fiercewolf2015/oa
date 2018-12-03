package com.xyj.oa.login.web;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		return "account/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password, Model model) {
		try {
			User user = accountService.findUserByLoginName(userName);
			if (user == null) {
				model.addAttribute("error", "用户名错误");
				logger.info("用户名错误:" + userName);
			} else {
				boolean result = accountService.checkUserPassword(userName, password);
				if (!result) {
					model.addAttribute("error", "密码错误");
					logger.info("密码错误");
				}
			}
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}

		return "account/login";
	}

}
