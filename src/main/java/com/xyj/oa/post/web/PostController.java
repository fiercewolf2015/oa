package com.xyj.oa.post.web;

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

import com.xyj.oa.post.service.PostService;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "post")
public class PostController {

	private static Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "corp/post";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo,
			@RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx,
			@RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, postService.findCountWithParams(params),
					postService.findListWithParams(params, pageNo, pageSize, sidx, sord));

		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public int addOccupation(@RequestParam(value = "params") String params,
			@RequestParam(value = "selDeptIds") String selDeptIds) {
		int result = 0;
		try {
			result = postService.savePost(params, selDeptIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public int editOccupation(@RequestParam(value = "params") String params,
			@RequestParam(value = "postId") Long postId, @RequestParam(value = "selDeptIds") String selDeptIds) {
		int result = 0;
		try {
			result = postService.editPost(params, postId, selDeptIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deletePost(@RequestParam(value = "postIds") String postIds) {
		String result = "删除失败";
		try {
			result = postService.deletePosts(postIds);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/getAllPosts")
	@ResponseBody
	public List<Map<String, Object>> getAllPosts(@RequestParam(value = "departmentId") Long departmentId) {
		return postService.getAllPosts(departmentId);

	}
}
