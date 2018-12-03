package com.xyj.oa.meeting.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyj.oa.meeting.entity.MeetingAttachments;

public interface MeetingAttachmentsDao extends PagingAndSortingRepository<MeetingAttachments, Long>, MeetingAttachmentsDaoCustom {

}
