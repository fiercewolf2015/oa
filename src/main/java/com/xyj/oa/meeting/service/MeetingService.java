package com.xyj.oa.meeting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.auth.ShiroRealm.ShiroUser;
import com.xyj.oa.auth.ShiroManager;
import com.xyj.oa.meeting.entity.Meeting;
import com.xyj.oa.meeting.entity.MeetingAttachments;
import com.xyj.oa.meeting.repository.MeetingAttachmentsDao;
import com.xyj.oa.meeting.repository.MeetingDao;
import com.xyj.oa.staff.entity.Staff;
import com.xyj.oa.staff.repository.StaffDao;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.user.entity.User;
import com.xyj.oa.user.repository.UserDao;
import com.xyj.oa.util.DateUtil;
import com.xyj.oa.util.FileManager;
import com.xyj.oa.util.StringHelper;

@Component
public class MeetingService {

	private static final Map<Integer, String> meetingTypeStr = new HashMap<Integer, String>();

	private static final String TEXT1 = "您好：";

	private static final String TEXT2 = "特此通知";

	private static final String TEXT3 = "兹定于";

	private static final String TEXT4 = "会议";

	private static final String TEXT5 = "在";

	private static final String TEXT6 = "召开";

	private static final String POINT = "，";

	private static final String POINT2 = "。";

	private static final String MEETINGTEXT = "会议通知";

	@Autowired
	private MeetingDao meetingDao;

	@Autowired
	private MeetingAttachmentsDao meetingAttachmentsDao;

	@Autowired
	private ShiroManager shiroManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private SystemService systemService;

	static {
		meetingTypeStr.put(1, "类型1");
		meetingTypeStr.put(2, "类型2");
	}

	public Long findMeetingCountWithParams(String params, Long staffId) {
		return meetingDao.getCountWithParams(params, staffId);
	}

	public List<Meeting> findMeetingListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, Long staffId) {
		List<Meeting> listWithParams = meetingDao.getListWithParams(params, pageNo, pageSize, sidx, sord, staffId);
		for (Meeting meeting : listWithParams) {
			meeting.setApplyDeptName(meeting.getApplyDepartment().getDepartmentName());
			meeting.setApplyStaffName(meeting.getApplyStaff().getStaffName());
			meeting.setMeetingTypeStr(meetingTypeStr.get(meeting.getMeetingType()));
			Set<MeetingAttachments> meetingAttachments = meeting.getMeetingAttachments();
			StringBuilder beforeSb = new StringBuilder();
			StringBuilder afterSb = new StringBuilder();
			for (MeetingAttachments ma : meetingAttachments) {
				if (ma.getAfterMeetingAttachmentName() != null)
					afterSb.append(",").append(ma.getAfterMeetingAttachmentName());
				if (ma.getBeforeMeetingAttachmentName() != null)
					beforeSb.append(",").append(ma.getBeforeMeetingAttachmentName());
			}
			StringBuilder meetingStaffNames = new StringBuilder();
			Set<Staff> staffs = meeting.getStaffs();
			for (Staff s : staffs)
				meetingStaffNames.append(",").append(s.getStaffName());
			meeting.setMeetingBeforeAttachmentsPath(StringHelper.isEmpty(beforeSb.toString()) ? "" : beforeSb.substring(1).toString());
			meeting.setMeetingAfterAttachmentsPath(StringHelper.isEmpty(afterSb.toString()) ? "" : afterSb.substring(1).toString());
			meeting.setMeetingStaffNames(meetingStaffNames.substring(1).toString());
		}
		return listWithParams;
	}

	public int addMeeting(String addMeetingTheme, String addMeetingType, String addMeetingBeginDate, String addMeetingEndDate, String addMeetingLocation,
			String addMeetingBriefly, String staffIds, String[] path) {
		Meeting meeting = new Meeting();
		meeting.setMeetingBeginTime(addMeetingBeginDate);
		meeting.setMeetingEndTime(addMeetingEndDate);
		meeting.setMeetingTitle(addMeetingTheme);
		meeting.setMeetingRemark(addMeetingBriefly);
		meeting.setMeetingLocation(addMeetingLocation);
		meeting.setMeetingType(Integer.valueOf(addMeetingType));
		ShiroUser shiroUser = shiroManager.getCurrentUser();
		if (shiroUser == null)
			return 0;
		Long userd = shiroUser.getId();
		User user = userDao.findById(userd).get();
		meeting.setApplyStaff(user.getStaff());
		meeting.setApplyDepartment(user.getStaff().getDepartment());
		meeting.setCreateTime(DateUtil.getNowTimestamp());
		Set<Staff> acceptStaffs = staffDao.findStaffByIdsOrPostIdsOrOccupationIds(staffIds, null, null, null);
		meeting.getStaffs().addAll(acceptStaffs);
		meetingDao.save(meeting);
		if (path != null && path.length > 0) {
			MeetingAttachments meetingAttachments = new MeetingAttachments();
			meetingAttachments.setMeeting(meeting);
			meetingAttachments.setBeforeMeetingAttachmentName(path[1]);
			meetingAttachments.setBeforeMeetingAttachmentPath(path[0]);
			meetingAttachmentsDao.save(meetingAttachments);
		}
		return 1;
	}

	public int deleteMeeting(String meetingIds) {
		if (StringHelper.isEmpty(meetingIds))
			return 0;
		String[] meetingIdsArr = meetingIds.split(",");
		for (String meetingId : meetingIdsArr) {
			Meeting meeting = meetingDao.findById(Long.valueOf(meetingId)).orElse(null);
			if (meeting == null)
				continue;
			Set<MeetingAttachments> meetingAttachments = meeting.getMeetingAttachments();
			for (MeetingAttachments mas : meetingAttachments) {
				FileManager.delFile(mas.getAfterMeetingAttachmentPath());
				FileManager.delFile(mas.getBeforeMeetingAttachmentPath());
			}
			meetingDao.delete(meeting);
		}
		return 1;
	}

	public int uploadAtt(String meegtingId, String[] path) {
		Meeting meeting = meetingDao.findById(Long.valueOf(meegtingId)).get();
		if (path != null && path.length > 0) {
			MeetingAttachments meetingAttachments = new MeetingAttachments();
			meetingAttachments.setMeeting(meeting);
			meetingAttachments.setAfterMeetingAttachmentName(path[1]);
			meetingAttachments.setAfterMeetingAttachmentPath(path[0]);
			meetingAttachmentsDao.save(meetingAttachments);
		}
		return 1;
	}

	public int remindMeeting(String meegtingIds) {
		List<Meeting> meetings = meetingDao.findAllMeetingByIds(meegtingIds);
		if (CollectionUtils.isEmpty(meetings))
			return 0;
		for (Meeting meeting : meetings) {
			Set<Staff> staffs = meeting.getStaffs();
			for (Staff staff : staffs) {
				StringBuilder sb = new StringBuilder();
				sb.append(staff.getStaffName()).append(TEXT1).append(POINT).append(TEXT3).append(meeting.getMeetingBeginTime()).append(TEXT5)
						.append(meeting.getMeetingLocation()).append(TEXT6).append(meeting.getMeetingTitle()).append(TEXT4).append(POINT).append(TEXT2)
						.append(POINT2);
				systemService.addNotice(MEETINGTEXT, sb.toString(), staff.getId().toString(), null);
			}
		}
		return 1;
	}

	public void scanMeetingFlag() {
		List<Meeting> meetings = meetingDao.findAllEndMeeting();
		if (CollectionUtils.isEmpty(meetings))
			return;
		for (Meeting meeting : meetings) {
			meeting.setMeetingFlag(1);
			meetingDao.save(meeting);
		}
	}

}
