package com.xyj.oa.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.util.LogUtil;
import com.xyj.oa.workflow.service.WorkFlowService;

@Controller
@RequestMapping(value = "/postchange")
public class GetPostChangeDateController {

	private static Logger logger = LoggerFactory.getLogger(GetPostChangeDateController.class);

	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping(value = "/data", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String doGet(@RequestParam("applystaffid") String applystaffid, @RequestParam("staffid") String staffId,
			@RequestParam("oldpostid") String oldpostid, @RequestParam("olddeptid") String olddeptid,
			@RequestParam("newpostid") String newpostid, @RequestParam("newdeptid") String newdeptid,
			@RequestParam("formid") String formid) {
		try {
			return workFlowService.doPostChange(applystaffid, staffId, oldpostid, olddeptid, newpostid, newdeptid,
					formid);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return "未知错误";
		}
	}
}
