package com.xyj.oa.api.web;

import org.slf4j.Logger;
import org.springframework.util.Base64Utils;

import com.xyj.oa.api.vo.APIErrorCode;
import com.xyj.oa.api.vo.ApiResult;
import com.xyj.oa.api.vo.ErrorResult;
import com.xyj.oa.api.vo.JsonMapper;

public abstract class BaseController {

	String processError(String userId, APIErrorCode error, Logger logger) {
		ErrorResult result = new ErrorResult(error);
		String json = JsonMapper.nonEmptyMapper().toJson(result);
		logger.info("手机用户：" + (userId == null ? "" : userId) + "###发生错误：" + (error == null ? "" : error.getError()));
		logger.info("原始返回数据：" + json);
		json = Base64Utils.encodeToString(json.getBytes());
		logger.info("加密处理后：" + json);
		return json;
	}

	String processResult(String userId, ApiResult result, Logger logger) {
		String json = JsonMapper.nonNullMapper().toJson(result);
		logger.info("----手机用户：" + userId);
		logger.info("原始返回数据：" + json);
		json = Base64Utils.encodeToString(json.getBytes());
		logger.info("加密处理后：" + json);
		return json;
	}

}
