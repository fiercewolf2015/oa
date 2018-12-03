package com.xyj.oa.title.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.title.entity.Title;
import com.xyj.oa.title.repository.TitleDao;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

@Component
@Transactional
public class TitleService {

	@Autowired
	private TitleDao titleDao;

	public Long findCountWithParams(String params) {
		return titleDao.getCountWithParams(params);
	}

	public List<Title> findListWithParams(String params, int pageNo, int pageSize, String sidx, String sord) {
		return titleDao.getListWithParams(params, pageNo, pageSize, sidx, sord);
	}

	public int saveTitle(String params) {
		if (StringHelper.isEmpty(params))
			return 0;
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String titleName = jsonObject.getString("addTitleName");
		String titleNo = jsonObject.getString("addTitleNo");
		Title findByTitleName = titleDao.findByTitleName(titleName);
		if (findByTitleName != null)
			return 2;
		Title findByTitleNo = titleDao.findByTitleNo(titleNo);
		if (findByTitleNo != null)
			return 3;
		Title title = new Title();
		title.setRemark(jsonObject.getString("remark"));
		title.setTitleName(titleName);
		title.setTitleNo(titleNo);
		titleDao.save(title);
		return 1;
	}

	public int editTitle(String params, Long titleId) {
		if (StringHelper.isEmpty(params) || titleId == null)
			return 0;
		Optional<Title> titles = titleDao.findById(titleId);
		if (!titles.isPresent())
			return 0;
		Title findOne = titles.get();
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String titleName = jsonObject.getString("editTitleName");
		String titleNo = jsonObject.getString("editTitleNo");
		Title findByTitleName = titleDao.findByTitleName(titleName);
		if (findByTitleName != null && findByTitleName.getId().longValue() != titleId.longValue())
			return 2;
		Title findByTitleNo = titleDao.findByTitleNo(titleNo);
		if (findByTitleNo != null && findByTitleNo.getId().longValue() != titleId.longValue())
			return 3;
		findOne.setTitleName(titleName);
		findOne.setTitleNo(titleNo);
		findOne.setRemark(jsonObject.getString("editRemark"));
		titleDao.save(findOne);
		return 1;
	}

	public String deleteTitles(String titleIds) {
		String result = "删除失败";
		String[] titleIdArr = titleIds.split(",");
		StringBuilder notDel = new StringBuilder();
		int delCnt = 0;
		for (String titleId : titleIdArr) {
			Optional<Title> tOptional = titleDao.findById(Long.valueOf(titleId));
			if(!tOptional.isPresent()) 
				continue;
			Title title = tOptional.get();
			if (CollectionUtils.isNotEmpty(title.getStaffs())) {
				notDel.append(title.getTitleName()).append(",");
				continue;
			}
			titleDao.delete(title);
			delCnt++;
		}
		if (notDel.toString().length() > 0) {
			result = notDel.toString().substring(0, notDel.toString().length() - 1) + "已绑定员工，不能删除;";
			if (delCnt > 0)
				result += "其余职称职级删除成功";
		} else
			result = "删除成功";
		return result;
	}

	public List<Map<String, Object>> getAllTitles() {
		return titleDao.findAllTitles();
	}

}
