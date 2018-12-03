package com.xyj.oa.api.attendance.quartz.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyj.oa.meeting.service.MeetingService;
import com.xyj.oa.util.LogUtil;

@Component
public class SynDataJobRunner {

	private static Logger log = LoggerFactory.getLogger(SynDataJobRunner.class);

	private static final String LOG_SCAN_MEETING_FLAG_START = "------------------------------------扫面更新会议状态开始！---------------------------------";

	private static final String LOG_SCAN_MEETING_FLAG_END = "------------------------------------扫面更新会议状态结束！---------------------------------";

	@Autowired
	private MeetingService meetingService;

	public void scanMeetingFlag() {
		log.info(LOG_SCAN_MEETING_FLAG_START);
		try {
			meetingService.scanMeetingFlag();
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
		}
		log.info(LOG_SCAN_MEETING_FLAG_END);
	}

}
