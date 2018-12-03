package com.xyj.oa.backup;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.api.web.BaseController;
import com.xyj.oa.util.LogUtil;

@Controller
@RequestMapping(value = "/backup")
public class BackUpController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BackUpController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/backup";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		//		try {
		//			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findAssetTypeCountWithParams(params),
		//					systemService.findAssetTypeListWithParams(params, pageNo, pageSize, sidx, sord));
		//		} catch (Exception e) {
		//			logger.error(LogUtil.stackTraceToString(e));
		//			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		//		}
		return null;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addAssetType(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			//			result = systemService.saveAssetType(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
