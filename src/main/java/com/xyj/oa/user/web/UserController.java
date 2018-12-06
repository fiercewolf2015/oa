package com.xyj.oa.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.PicUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private ShiroManager shiroManager;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String inedx() {
		return "account/user";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile() {
		return "account/profile";
	}

	@RequestMapping(value = "/user/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, accountService.findCountWithParams(params),
					accountService.findListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public int register(@RequestParam(value = "staffIds") String staffIds, @RequestParam(value = "roleId") Long roleId) {
		int result = 0;
		try {
			if (StringHelper.isEmpty(staffIds) || roleId == null || roleId <= 0)
				return 0;
			result = accountService.registerUser(staffIds, roleId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@SuppressWarnings({ "deprecation" })
	@RequestMapping(value = "/profile")
	@ResponseBody
	public int uploadPhoto(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name, HttpServletRequest request) {
		int result = 0;
		File tempFile = null;
		InputStream is = null;
		String tempPath = null;
		try {
			Long userId = shiroManager.getCurrentUserId();
			if (userId == null || userId <= 0)
				return result;
			String pic = "";
			if (file != null && !file.isEmpty()) {
				String projectRealPath = request.getRealPath("/");
				tempPath = projectRealPath + File.separator + "WEB-INF" + File.separator + "photo_temp";
				tempFile = new File(tempPath + File.separator + file.getOriginalFilename());
				is = file.getInputStream();
				byte[] bytes = new byte[is.available()];
				PicUtil.doResize(is, tempPath, 48, 48);
				try {
					tempFile = new File(tempPath);
					is = new FileInputStream(tempFile);
					bytes = new byte[is.available()];
				} catch (Exception e) {
					logger.info("FileNotFoundException:文件不存在，文件路径为：" + tempPath + File.separator + file.getOriginalFilename());
					return result;
				}
				is.read(bytes);
				pic = Base64.encodeBase64String(bytes);
			}
			result = accountService.profile(pic, name, password);
			return result;
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		return result;
	}

	@RequestMapping(value = "/getPhoto")
	@ResponseBody
	public void getPhoto(HttpServletRequest request, HttpServletResponse response) {
		ShiroUser currentUser = shiroManager.getCurrentUser();
		if (currentUser == null)
			return;
		String photo = currentUser.getPhoto();
		byte[] data = null;
		if (StringHelper.isNotEmpty(photo))
			data = Base64.decodeBase64(photo.getBytes());
		ServletOutputStream outputStream = null;
		try {
			response.setContentType("image/jpg");//image/jpg
			outputStream = response.getOutputStream();
			if (data == null)
				outputStream.write(new byte[0]);
			else
				outputStream.write(data);
		} catch (IOException e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			if (outputStream != null)
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.error(LogUtil.stackTraceToString(e));
				}
		}
	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	@ResponseBody
	public int deleteUser(@RequestParam(value = "userIds") String userIds) {
		int result = 0;
		try {
			result = accountService.deleteUser(userIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/user/editUserRole", method = RequestMethod.POST)
	@ResponseBody
	public int editUserRole(@RequestParam(value = "userId") Long userId, @RequestParam(value = "roleId") Long roleId) {
		int result = 0;
		try {
			result = accountService.editUserRole(userId, roleId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/user/editUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public int editUserPassword(@RequestParam(value = "userIds") String userIds, @RequestParam(value = "plainPassword") String plainPassword) {
		int result = 0;
		try {
			result = accountService.editUserPassword(userIds, plainPassword);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/profile/staffInfo", method = RequestMethod.POST)
	@ResponseBody
	public Staff getStaffInfo() {
		Staff result = null;
		try {
			ShiroUser currentUser = shiroManager.getCurrentUser();
			if (currentUser == null)
				return result;
			Long userId = currentUser.getId();
			if (userId == null || userId <= 0)
				return result;
			result = accountService.getStaffInfo(userId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

	@RequestMapping(value = "/profile/editStaff", method = RequestMethod.POST)
	@ResponseBody
	public int editStaff(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = accountService.editStaff(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
