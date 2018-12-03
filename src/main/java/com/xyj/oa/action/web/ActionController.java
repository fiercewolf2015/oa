package com.xyj.oa.action.web;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.action.service.ActionService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "/permission")
public class ActionController {
	public static final Logger logger = LoggerFactory.getLogger(ActionController.class);

	@Autowired
	private ActionService actionService;

	@RequestMapping(value = "/loadPermissionTree")
	@ResponseBody
	public List<Map<String, Object>> loadPermissionTree() {
		return actionService.loadPermissionTree();
	}

	@RequestMapping(value = "/searchPermission")
	@ResponseBody
	public List<BigInteger> searchPermissionByRoleId(@RequestParam(value = "id") long roleId) {
		try {
			return actionService.searchPermissionByRoleId(roleId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return null;
		}
	}

	@RequestMapping(value = "/addPermission")
	@ResponseBody
	public int addPermission(@RequestParam(value = "name") String name, @RequestParam(value = "codeName") String codeName) {
		try {
			return actionService.addPermission(name, codeName);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
	}
}
