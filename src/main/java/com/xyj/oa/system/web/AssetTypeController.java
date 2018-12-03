package com.xyj.oa.system.web;

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

import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "assetType")
public class AssetTypeController {
	
	private static Logger logger = LoggerFactory.getLogger(AssetTypeController.class);

	@Autowired
	private SystemService systemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "system/assettype";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findAssetTypeCountWithParams(params),
					systemService.findAssetTypeListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addAssetType(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = systemService.saveAssetType(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editAssetType(@RequestParam(value = "params") String params,@RequestParam(value = "assetTypeId") Long assetTypeId) {
		int result = 0;
		try {
			result = systemService.editAssetType(params, assetTypeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public int deleteAssetType(@RequestParam(value = "assetTypeIds") String assetTypeIds) {
		int result = 0;
		try {
			result = systemService.deleteAssetType(assetTypeIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllAssetTypes")
	@ResponseBody
	public List<Map<String, Object>> getAllAssetTypes() {
		return systemService.getAllAssetTypes();
	}
	
	@RequestMapping(value="/getName")
	@ResponseBody
	public String getAssetTypeNameById(@RequestParam(value="typeId") Long typeId){
		String result = "";
		try {
			result = systemService.getAssetTypeNameById(typeId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		return result;
	}

}
