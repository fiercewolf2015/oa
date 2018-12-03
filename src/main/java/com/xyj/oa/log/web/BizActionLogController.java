package com.xyj.oa.log.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.log.entity.BizActionLog;
import com.xyj.oa.log.service.BizActionLogService;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping("/bizActionLog")
public class BizActionLogController {

	@Autowired
	private ShiroManager shiroManager;
	
	@Autowired
	private BizActionLogService bizActionLogService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/bizactionlog";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam(required = false, value = "params") String params,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserId = shiroManager.getCurrentUserId();
			List<BizActionLog> list = bizActionLogService.findListWithParams(params, pageNo, pageSize,currentUserId);
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, bizActionLogService.findCountWithParams(params,currentUserId), list);
		} catch (Exception e) {
			e.printStackTrace();
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
}
