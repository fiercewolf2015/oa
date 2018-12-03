package com.xyj.oa.role.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.role.service.RoleService;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String inedx() {
		return "account/roleAndPermission";
	}
	
	@RequestMapping(value = "/loadRoleTree")
	@ResponseBody
	public List<Map<String, Object>> loadRoleTree() {
		return roleService.loadRoleTree();
	}
	
	@RequestMapping(value = "/addPermissionToRole")
	@ResponseBody
	public int addActionToRole(@RequestParam(value = "roleId") Long roleId, @RequestParam(value = "permissionIds") String permissionIds) {
		try {
			return roleService.addPermissionToRole(roleId, permissionIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
	}
	
	@RequestMapping(value = "/addRole")
	@ResponseBody
	public int addRole(@RequestParam(value = "roleId") Long roleId,@RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleDescription") String roleDescription) {
		try {
			return roleService.addRole(roleId,roleName, roleDescription);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
	}
	
	@RequestMapping(value = "/deleteRole")
	@ResponseBody
	public int deleteRole(@RequestParam(value = "id") Long roleId) {
		try {
			return roleService.deleteRole(roleId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return 0;
		}
	}
	
}
