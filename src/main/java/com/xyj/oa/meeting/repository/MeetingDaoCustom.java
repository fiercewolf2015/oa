package com.xyj.oa.meeting.repository;

import java.util.List;

import com.xyj.oa.meeting.entity.Meeting;

public interface MeetingDaoCustom {

	List<Meeting> getListWithParams(String params, int pageNo, int pageSize, String sidx, String sord, Long staffId);

	Long getCountWithParams(String params, Long staffId);

	List<Meeting> findAllMeetingByIds(String meetingIds);

	List<Meeting> findAllEndMeeting();

}
