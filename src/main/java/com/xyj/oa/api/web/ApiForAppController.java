package com.xyj.oa.api.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.api.exception.ApiException;
import com.xyj.oa.api.vo.APIErrorCode;
import com.xyj.oa.api.vo.ApiResult;
import com.xyj.oa.api.vo.JsonMapper;
import com.xyj.oa.api.vo.UserVo;
import com.xyj.oa.redis.Redis;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.service.AccountService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.workflow.service.WorkFlowInstanceService;
import com.xyj.oa.workflow.service.WorkFlowService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api")
public class ApiForAppController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ApiForAppController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private WorkFlowService workFlowService;

	@Autowired
	private WorkFlowInstanceService workFlowInstanceService;

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			User user = accountService.getUserForApi(params);
			return processResult(String.valueOf(user.getId()), new UserVo(user), logger);
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	@ResponseBody
	public String userlist(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			ApiResult result = accountService.getUserListForApi(params);
			return processResult("", result, logger);
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/todoList", method = RequestMethod.GET)
	@ResponseBody
	public String todoList(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			String result = workFlowInstanceService.findListForApi(params, 1);
			logger.info("原始返回数据：" + result);
			result = Base64Utils.encodeToString(result.getBytes());
			logger.info("加密处理后：" + result);
			return result;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/doneList", method = RequestMethod.GET)
	@ResponseBody
	public String doneList(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			String result = workFlowInstanceService.findListForApi(params, 2);
			logger.info("原始返回数据：" + result);
			result = Base64Utils.encodeToString(result.getBytes());
			logger.info("加密处理后：" + result);
			return result;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/hisList", method = RequestMethod.GET)
	@ResponseBody
	public String hisList(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			String result = workFlowInstanceService.findListForApi(params, 3);
			logger.info("原始返回数据：" + result);
			result = Base64Utils.encodeToString(result.getBytes());
			logger.info("加密处理后：" + result);
			return result;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/getWorkflowInstanceInfo")
	@ResponseBody
	public String getWorkflowInstanceInfo(@RequestParam(value = "instanceId") Long instanceId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		Map<String, Object> result = null;
		try {
			result = workFlowInstanceService.getWorkflowInstanceInfo(instanceId);
			String json = JsonMapper.nonNullMapper().toJson(result);
			logger.info("原始返回数据：" + json);
			json = Base64Utils.encodeToString(json.getBytes());
			logger.info("加密处理后：" + json);
			return json;
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/agree")
	@ResponseBody
	public String agreeWorkflowInstanceInfo(@RequestParam(value = "params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		int result = 0;
		try {
			if (StringHelper.isEmpty(params))
				throw new ApiException(APIErrorCode.ParamError);
			params = new String(Base64Utils.decodeFromString(params));
			JSONObject object = JSONObject.fromObject(params);
			String crossReason = object.getString("crossReason");
			Long managerStaffId = object.getLong("managerStaffId");
			Long instanceId = object.getLong("instanceId");
			String array = object.getString("nextDeptStaffIds");
			Long[] nextDeptStaffIds = new Long[] {};
			nextDeptStaffIds = (Long[]) (StringHelper.isEmpty(array) ? nextDeptStaffIds : array.split(","));
			if (nextDeptStaffIds == null || nextDeptStaffIds.length <= 0)
				result = workFlowService.crossInstance(crossReason, managerStaffId, instanceId, null, null, null);
			else
				result = workFlowService.crossFinancialNotesInstance(crossReason, managerStaffId, instanceId, nextDeptStaffIds, null, null);
			ApiResult apiResult = new ApiResult();
			apiResult.setResult(result);
			String json = JsonMapper.nonNullMapper().toJson(apiResult);
			logger.info("原始返回数据：" + json);
			json = Base64Utils.encodeToString(json.getBytes());
			logger.info("加密处理后：" + json);
			return json;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/reject")
	@ResponseBody
	public String rejectWorkflowInstanceInfo(@RequestParam(value = "params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		int result = 0;
		try {
			if (StringHelper.isEmpty(params))
				throw new ApiException(APIErrorCode.ParamError);
			params = new String(Base64Utils.decodeFromString(params));
			JSONObject object = JSONObject.fromObject(params);
			String rejectReason = object.getString("rejectReason");
			Long instanceId = object.getLong("instanceId");
			result = workFlowService.rejectInstance(rejectReason, instanceId);
			ApiResult apiResult = new ApiResult();
			apiResult.setResult(result);
			String json = JsonMapper.nonNullMapper().toJson(apiResult);
			logger.info("原始返回数据：" + json);
			json = Base64Utils.encodeToString(json.getBytes());
			logger.info("加密处理后：" + json);
			return json;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/noticeList", method = RequestMethod.GET)
	@ResponseBody
	public String noticeList(@RequestParam("params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			if (StringHelper.isEmpty(params))
				throw new ApiException(APIErrorCode.ParamError);
			params = new String(Base64Utils.decodeFromString(params));
			JSONObject object = JSONObject.fromObject(params);
			String name = object.getString("name");
			Long userId = object.getLong("userId");
			int unread = object.getInt("unRead");
			int pageNo = object.getInt("pageNo");
			int pageSize = object.getInt("pageSize");
			ApiResult result = systemService.getNoticeList(name, userId, unread, pageNo, pageSize);
			return processResult("", result, logger);
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

	@RequestMapping(value = "/read")
	@ResponseBody
	public String readMessage(@RequestParam(value = "params") String params, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			if (StringHelper.isEmpty(params))
				throw new ApiException(APIErrorCode.ParamError);
			params = new String(Base64Utils.decodeFromString(params));
			JSONObject object = JSONObject.fromObject(params);
			String ids = object.getString("ids");
			Redis redis = new Redis();
			boolean result = redis.add(ids, "1");
			ApiResult apiResult = new ApiResult();
			apiResult.setResult(result ? 1 : 0);
			String json = JsonMapper.nonNullMapper().toJson(apiResult);
			logger.info("原始返回数据：" + json);
			json = Base64Utils.encodeToString(json.getBytes());
			logger.info("加密处理后：" + json);
			return json;
		} catch (ApiException e) {
			return processError(null, e.getError(), logger);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return processError(null, APIErrorCode.ServerError, logger);
		}
	}

}
