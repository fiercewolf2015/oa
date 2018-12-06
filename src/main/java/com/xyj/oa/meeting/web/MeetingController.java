package com.xyj.oa.meeting.web;

import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.meeting.service.MeetingService;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.FileManager;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "meeting")
public class MeetingController {

	private static Logger logger = LoggerFactory.getLogger(MeetingController.class);

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "meet/meeting";
	}

	@RequestMapping(value = "/history")
	public String history() {
		return "meet/meetinghistory";
	}

	@RequestMapping(value = "/draft")
	public String draft() {
		return "meet/meetingdraft";
	}

	@RequestMapping(value = "/remind")
	public String remind() {
		return "meet/meetingremind";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			ShiroUser shiroUser = shiroManager.getCurrentUser();
			if (shiroUser == null)
				return null;
			Long userd = shiroUser.getId();
			User user = accountService.getUser(userd);
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, meetingService.findMeetingCountWithParams(params, user.getStaff().getId()),
					meetingService.findMeetingListWithParams(params, pageNo, pageSize, sidx, sord, user.getStaff().getId()));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addMeeting(@RequestParam(value = "addMeetingTheme") String addMeetingTheme, @RequestParam(value = "addMeetingType") String addMeetingType,
			@RequestParam(value = "addMeetingBeginDate") String addMeetingBeginDate, @RequestParam(value = "addMeetingEndDate") String addMeetingEndDate,
			@RequestParam(value = "addMeetingLocation") String addMeetingLocation, @RequestParam(value = "addMeetingBriefly") String addMeetingBriefly,
			@RequestParam(value = "staffIds") String staffIds, @RequestParam(required = false, value = "file") MultipartFile bfile, HttpServletRequest request) {
		int result = 0;
		String[] path = null;
		try {
			if (bfile != null && !bfile.isEmpty()) {
				InputStream inputStream = bfile.getInputStream();
				String fileName = bfile.getOriginalFilename();
				if (fileName.lastIndexOf("\\") >= 0)
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				else if (fileName.lastIndexOf("/") >= 0)
					fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				int available = inputStream.available();
				if (available > 10485760)
					return -100;
				path = FileManager.saveFile(inputStream, fileName, request);
			}
			result = meetingService.addMeeting(addMeetingTheme, addMeetingType, addMeetingBeginDate, addMeetingEndDate, addMeetingLocation, addMeetingBriefly,
					staffIds, path);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteNotice(@RequestParam(value = "meetingIds") String meetingIds) {
		int result = 0;
		try {
			result = meetingService.deleteMeeting(meetingIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/uploadAtt")
	@ResponseBody
	public int uploadAtt(@RequestParam(value = "meegtingId") String meegtingId, @RequestParam(required = false, value = "afile") MultipartFile afile,
			HttpServletRequest request) {
		int result = 0;
		String[] path = null;
		try {
			if (afile != null && !afile.isEmpty()) {
				InputStream inputStream = afile.getInputStream();
				String fileName = afile.getOriginalFilename();
				if (fileName.lastIndexOf("\\") >= 0)
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				else if (fileName.lastIndexOf("/") >= 0)
					fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				int available = inputStream.available();
				if (available > 10485760)
					return -100;
				path = FileManager.saveFile(inputStream, fileName, request);
			}
			result = meetingService.uploadAtt(meegtingId, path);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/remindMeeting")
	@ResponseBody
	public int remindMeeting(@RequestParam(value = "meetingIds") String meetingIds, HttpServletRequest request) {
		int result = 0;
		try {
			result = meetingService.remindMeeting(meetingIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
