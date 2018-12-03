package com.xyj.oa.occupation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xyj.oa.occupation.entity.Occupation;
import com.xyj.oa.occupation.repository.OccupationDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.util.StringHelper;
import com.xyj.oa.util.TfOaUtil;

import net.sf.json.JSONObject;

@Component
@Transactional
public class OccupationService {

	@Autowired
	private OccupationDao occupationDao;

	public int addOccupation(String params, Long pId) {
		if (StringHelper.isEmpty(params) || pId == null || pId <= 0)
			return 0;
		JSONObject jsonObject = JSONObject.fromObject(params);
		String occupationName = jsonObject.getString("occupationName");
		String occupationNo = jsonObject.getString("occupationNo");
		String remark = jsonObject.getString("remark");
		if (StringHelper.isEmpty(occupationName) || StringHelper.isEmpty(occupationNo))
			return 0;
		Integer level = 1;
		Occupation parent = null;
		if (pId != null) {
			Optional<Occupation> optional = occupationDao.findById(pId);
			if (optional.isPresent())
				parent = optional.get();
		}
		Occupation findByOccupationName = occupationDao.findByOccupationName(occupationName);
		if (findByOccupationName != null)
			return 2;
		Occupation findByOccupationNo = occupationDao.findByOccupationNo(occupationNo);
		if (findByOccupationNo != null)
			return 3;
		Occupation occupation = new Occupation();
		occupation.setOccupationName(occupationName);
		occupation.setOccupationNo(occupationNo);
		occupation.setRemark(remark);
		if (parent != null) {
			level = parent.getOccupationLevel() + 1;
			occupation.setParentOccupation(parent);
		}
		occupation.setOccupationLevel(level);
		occupationDao.save(occupation);
		return 1;
	}

	public int editOccupation(String params, Long occupationId) {
		if (StringHelper.isEmpty(params) || occupationId == null)
			return 0;
		Optional<Occupation> optional = occupationDao.findById(occupationId);
		if (!optional.isPresent())
			return 0;
		Occupation findOne = optional.get();
		JSONObject jsonObject = TfOaUtil.fromObject(params);
		String occupationName = jsonObject.getString("occupationName");
		String occupationNo = jsonObject.getString("occupationNo");
		Occupation findByOccupationName = occupationDao.findByOccupationName(occupationName);
		if (findByOccupationName != null && findByOccupationName.getId().longValue() != occupationId.longValue())
			return 2;
		Occupation findByOccupationNo = occupationDao.findByOccupationNo(occupationNo);
		if (findByOccupationNo != null && findByOccupationNo.getId().longValue() != occupationId.longValue())
			return 3;
		findOne.setOccupationName(occupationName);
		findOne.setOccupationNo(occupationNo);
		findOne.setRemark(jsonObject.getString("remark"));
		occupationDao.save(findOne);
		return 1;
	}

	public int deleteOccupation(Long id) {
		if (id == null || id <= 0)
			return 0;
		Optional<Occupation> optional = occupationDao.findById(id);
		if (!optional.isPresent())
			return 0;
		Occupation parent = optional.get();
		List<Occupation> list = new ArrayList<Occupation>();
		iterationDepartmentForChild(parent, list);
		for (Occupation occupation : list) {
			Set<Staff> staffs = occupation.getStaffs();
			if (CollectionUtils.isNotEmpty(staffs))
				return 2;
		}
		for (Occupation occupation : list) {
			occupationDao.delete(occupation);
		}
		return 1;
	}

	public void iterationDepartmentForChild(Occupation occupation, List<Occupation> list) {
		list.add(occupation);
		Set<Occupation> children = occupation.getChildren();
		for (Occupation child : children)
			iterationDepartmentForChild(child, list);
	}

	public List<Map<String, Object>> loadOccupationTree() {
		return occupationDao.loadOccupationTree();
	}

}
