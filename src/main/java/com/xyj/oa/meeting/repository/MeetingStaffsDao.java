package com.xyj.oa.meeting.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.meeting.entity.MeetingStaffs;

public interface MeetingStaffsDao extends PagingAndSortingRepository<MeetingStaffs, Long>, MeetingStaffsDaoCustom {

}
