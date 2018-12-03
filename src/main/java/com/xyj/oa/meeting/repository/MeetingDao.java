package com.xyj.oa.meeting.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.meeting.entity.Meeting;

public interface MeetingDao extends PagingAndSortingRepository<Meeting, Long>, MeetingDaoCustom {

}
