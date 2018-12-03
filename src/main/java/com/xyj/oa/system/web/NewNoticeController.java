package com.xyj.oa.system.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.auth.OaShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "newnotice")
public class NewNoticeController {

	private static Logger logger = LoggerFactory.getLogger(NewNoticeController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/newnotice";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		Map<String, Object> createPageInfoMap = null;
		try {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			Staff staff = accountService.getStaffInfo(userd);
			createPageInfoMap = TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findNoticeStaffCountWithParams(params, staff.getId()),
					systemService.findNoticeStaffListWithParams(params, staff.getId(), pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
		return createPageInfoMap;
	}

	@RequestMapping(value = "/getUnreadNum")
	@ResponseBody
	public Long getUnreadNum() {
		Long result = 0L;
		try {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			Staff staff = accountService.getStaffInfo(userd);
			result = systemService.getUnreadNum(staff.getId());
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/changeReadState")
	@ResponseBody
	public int changeReadState(@RequestParam(value = "noticeIds") String noticeIds) {
		int result = 0;
		try {
			result = systemService.changeReadState(noticeIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
