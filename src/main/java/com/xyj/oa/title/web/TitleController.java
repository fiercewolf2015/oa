package com.xyj.oa.title.web;

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

import com.xyj.oa.title.service.TitleService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "title")
public class TitleController {
	
	private static Logger logger = LoggerFactory.getLogger(TitleController.class);
	
	@Autowired
	private TitleService titleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "corp/title";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, titleService.findCountWithParams(params),
					titleService.findListWithParams(params, pageNo, pageSize, sidx, sord));

		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public int addTitle(@RequestParam(value = "params") String params) {
		int result = 0;
		try {
			result = titleService.saveTitle(params);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editTitle(@RequestParam(value = "params") String params,@RequestParam(value = "titleId") Long titleId) {
		int result = 0;
		try {
			result = titleService.editTitle(params,titleId);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteTitle(@RequestParam(value = "titleIds") String titleIds) {
		String result = "删除失败";
		try {
			result = titleService.deleteTitles(titleIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllTitles")
	@ResponseBody
	public List<Map<String, Object>> getAllTitles() {
		return titleService.getAllTitles();

	}

}
