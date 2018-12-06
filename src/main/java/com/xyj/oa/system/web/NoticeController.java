package com.xyj.oa.system.web;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "notice")
public class NoticeController {

	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/notice";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			Long userd = shiroUser.getId();
			Staff staff = accountService.getStaffInfo(userd);
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findNoticeCountWithParams(params, staff.getId()),
					systemService.findNoticeListWithParams(params, staff.getId(), pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addNotice(@RequestParam(value = "addNoticeName") String addNoticeName, @RequestParam(value = "remark") String remark,
			@RequestParam(value = "staffIds") String staffIds, @RequestParam(value = "fileNameForTwo") String fileName, HttpServletRequest request) {
		int result = 0;
		String[] path = new String[2];
		try {
			String filePath = "";
			if (StringHelper.isNotEmpty(fileName)) {
				String projectRealPath = request.getRealPath("/");
				filePath = projectRealPath + "static" + File.separator + "file_temp" + File.separator + fileName.substring(2).trim();
				fileName = "/oa/static/file_temp/" + fileName.substring(2).trim();
			}
			path[0] = filePath;
			path[1] = fileName.trim();
			result = systemService.addNotice(addNoticeName, remark, staffIds, path);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editNotice(@RequestParam(value = "params") String params, @RequestParam(value = "noticeId") Long noticeId) {
		int result = 0;
		try {
			result = systemService.editNotice(params, noticeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteNotice(@RequestParam(value = "noticeIds") String noticeIds) {
		int result = 0;
		try {
			result = systemService.deleteNotice(noticeIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
