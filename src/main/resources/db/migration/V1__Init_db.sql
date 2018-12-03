/*
Navicat MySQL Data Transfer

Source Server         : 天孚（新）
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : tf

Target Server Type    : MYSQL
File Encoding         : 65001

Date: 2018-11-29 14:19:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_assettype
-- ----------------------------
DROP TABLE IF EXISTS `t_assettypes`;
CREATE TABLE `t_assettypes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `update_time` varchar(25) DEFAULT NULL,
  `create_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_bizaction_log
-- ----------------------------
DROP TABLE IF EXISTS `t_bizaction_logs`;
CREATE TABLE `t_bizaction_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(250) DEFAULT NULL,
  `biz_type` varchar(250) DEFAULT NULL,
  `biz_info` text,
  `biz_content` text,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(250) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81318 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_budget_commissioner
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_commissioners`;
CREATE TABLE `t_budget_commissioners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `update_time` varchar(25) DEFAULT NULL,
  `create_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_budgetmodel
-- ----------------------------
DROP TABLE IF EXISTS `t_budgetmodels`;
CREATE TABLE `t_budgetmodels` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_budgetmodel_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_budgetmodel_subjects`;
CREATE TABLE `t_budgetmodel_subjects` (
  `model_id` bigint(20) NOT NULL,
  `sub_id` bigint(20) NOT NULL,
  PRIMARY KEY (`model_id`,`sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_businessdata
-- ----------------------------
DROP TABLE IF EXISTS `t_businessdatas`;
CREATE TABLE `t_businessdatas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_info` text,
  `work_flow_instance_id` bigint(20) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18515 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_contract_will_form
-- ----------------------------
DROP TABLE IF EXISTS `t_contract_will_forms`;
CREATE TABLE `t_contract_will_forms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_date` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `instance_num` varchar(255) DEFAULT NULL,
  `contract_no` varchar(255) DEFAULT NULL,
  `contract_begin_date` varchar(255) DEFAULT NULL,
  `contract_end_date` varchar(255) DEFAULT NULL,
  `contract_time` varchar(255) DEFAULT NULL,
  `contract_name` varchar(255) DEFAULT NULL,
  `contract_price` varchar(255) DEFAULT NULL,
  `contract_month_price` varchar(255) DEFAULT NULL,
  `contract_price_total` varchar(255) DEFAULT NULL,
  `payment_date` varchar(255) DEFAULT NULL,
  `payment_money` varchar(255) DEFAULT NULL,
  `caiwu_type` varchar(255) DEFAULT NULL,
  `contract_count` varchar(255) DEFAULT NULL,
  `invoice_date` varchar(255) DEFAULT NULL,
  `payment_time_slot` varchar(255) DEFAULT NULL,
  `jia_name` varchar(255) DEFAULT NULL,
  `yi_name` varchar(255) DEFAULT NULL,
  `bing_name` varchar(255) DEFAULT NULL,
  `disifang_name` varchar(255) DEFAULT NULL,
  `if_fan_huan` varchar(255) DEFAULT NULL,
  `ye_shu` varchar(255) DEFAULT NULL,
  `contract_type` varchar(255) DEFAULT NULL,
  `contract_conten` varchar(255) DEFAULT NULL,
  `reason` varchar(5000) DEFAULT NULL,
  `project_operation_type` varchar(255) DEFAULT NULL,
  `area_statistics` varchar(255) DEFAULT NULL,
  `project_addr` varchar(255) DEFAULT NULL,
  `project_jia_name` varchar(255) DEFAULT NULL,
  `zhi_chu` varchar(255) DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1535 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_departments`;
CREATE TABLE `t_departments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  `department_no` varchar(255) DEFAULT NULL,
  `department_describe` varchar(255) DEFAULT NULL,
  `department_level` int(20) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=462 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_department_copy
-- ----------------------------
DROP TABLE IF EXISTS `t_department_copys`;
CREATE TABLE `t_department_copys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  `department_no` varchar(255) DEFAULT NULL,
  `department_describe` varchar(255) DEFAULT NULL,
  `department_level` int(20) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_finance_actually_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_finance_actually_subjects`;
CREATE TABLE `t_finance_actually_subjects` (
  `fa_id` bigint(20) NOT NULL,
  `fac_id` bigint(20) NOT NULL,
  PRIMARY KEY (`fa_id`,`fac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_finance_organization_approval
-- ----------------------------
DROP TABLE IF EXISTS `t_finance_organization_approvals`;
CREATE TABLE `t_finance_organization_approvals` (
  `fapp_id` bigint(20) NOT NULL,
  `for_id` bigint(20) NOT NULL,
  PRIMARY KEY (`fapp_id`,`for_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financeactually
-- ----------------------------
DROP TABLE IF EXISTS `t_financeactuallys`;
CREATE TABLE `t_financeactuallys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_id` bigint(20) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `actually_month` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financeactuallysubject
-- ----------------------------
DROP TABLE IF EXISTS `t_financeactuallysubjects`;
CREATE TABLE `t_financeactuallysubjects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fac_id` bigint(20) NOT NULL,
  `actually_money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financeapproval
-- ----------------------------
DROP TABLE IF EXISTS `t_financeapprovals`;
CREATE TABLE `t_financeapprovals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `finance_approval_name` varchar(255) NOT NULL,
  `finance_approval_staff_ids` varchar(255) DEFAULT NULL,
  `approvalstaff_id` bigint(20) DEFAULT NULL,
  `approvaldept_id` bigint(20) DEFAULT NULL,
  `applystaff_id` bigint(20) NOT NULL,
  `applydept_id` bigint(20) NOT NULL,
  `finance_year` varchar(255) DEFAULT NULL,
  `all_january` int(11) DEFAULT NULL,
  `all_february` int(11) DEFAULT NULL,
  `all_march` int(11) DEFAULT NULL,
  `all_april` int(11) DEFAULT NULL,
  `all_may` int(11) DEFAULT NULL,
  `all_june` int(11) DEFAULT NULL,
  `all_july` int(11) DEFAULT NULL,
  `all_august` int(11) DEFAULT NULL,
  `all_september` int(11) DEFAULT NULL,
  `all_october` int(11) DEFAULT NULL,
  `all_november` int(11) DEFAULT NULL,
  `all_december` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `approval_flag` int(11) DEFAULT NULL,
  `reject_reason` varchar(5000) DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  `history_finance_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financeapproval_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_financeapproval_staffs`;
CREATE TABLE `t_financeapproval_staffs` (
  `fapp_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  PRIMARY KEY (`fapp_id`,`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for t_financeline
-- ----------------------------
DROP TABLE IF EXISTS `t_financelines`;
CREATE TABLE `t_financelines` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_line` int(11) DEFAULT NULL,
  `second_line` int(11) DEFAULT NULL,
  `three_line` int(11) DEFAULT NULL,
  `four_line` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financeorganization
-- ----------------------------
DROP TABLE IF EXISTS `t_financeorganizations`;
CREATE TABLE `t_financeorganizations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_id` bigint(20) NOT NULL,
  `subject_finance_january` int(11) NOT NULL,
  `subject_finance_february` int(11) NOT NULL,
  `subject_finance_march` int(11) NOT NULL,
  `subject_finance_april` int(11) NOT NULL,
  `subject_finance_may` int(11) NOT NULL,
  `subject_finance_june` int(11) NOT NULL,
  `subject_finance_july` int(11) NOT NULL,
  `subject_finance_august` int(11) NOT NULL,
  `subject_finance_september` int(11) NOT NULL,
  `subject_finance_october` int(11) NOT NULL,
  `subject_finance_november` int(11) DEFAULT NULL,
  `subject_finance_december` int(11) DEFAULT NULL,
  `subject_finance_all` int(11) NOT NULL,
  `app_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `if_child` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_financesubject
-- ----------------------------
DROP TABLE IF EXISTS `t_financesubjects`;
CREATE TABLE `t_financesubjects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `level` tinyint(4) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_leavetype
-- ----------------------------
DROP TABLE IF EXISTS `t_leavetypes`;
CREATE TABLE `t_leavetypes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting
-- ----------------------------
DROP TABLE IF EXISTS `t_meetings`;
CREATE TABLE `t_meetings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meeting_title` varchar(1000) DEFAULT NULL,
  `meeting_remark` varchar(5000) DEFAULT NULL,
  `meeting_location` varchar(255) DEFAULT NULL,
  `meeting_type` tinyint(4) DEFAULT NULL,
  `meeting_begin_time` varchar(255) DEFAULT NULL,
  `meeting_end_time` varchar(255) DEFAULT NULL,
  `meeting_flag` tinyint(4) DEFAULT NULL,
  `s_id` bigint(20) DEFAULT NULL,
  `d_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting_attachments
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting_attachments`;
CREATE TABLE `t_meeting_attachments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_id` bigint(20) NOT NULL,
  `before_meeting_attachment_path` varchar(255) DEFAULT NULL,
  `before_meeting_attachment_name` varchar(255) DEFAULT NULL,
  `after_meeting_attachment_path` varchar(255) DEFAULT NULL,
  `after_meeting_attachment_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting_plan_staffs
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting_plan_staffs`;
CREATE TABLE `t_meeting_plan_staffs` (
  `m_id` bigint(20) NOT NULL,
  `s_id` bigint(20) NOT NULL,
  PRIMARY KEY (`m_id`,`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting_staffs`;
CREATE TABLE `t_meeting_staffs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_id` bigint(20) NOT NULL,
  `s_id` bigint(20) NOT NULL,
  `meeting_login_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notices`;
CREATE TABLE `t_notices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `s_id` bigint(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `message` varchar(5000) NOT NULL,
  `attachment_path` varchar(255) DEFAULT NULL,
  `attachment_name` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13343 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_notice_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_staffs`;
CREATE TABLE `t_notice_staffs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notice_id` bigint(20) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `unread` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32104 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for t_occupation
-- ----------------------------
DROP TABLE IF EXISTS `t_occupations`;
CREATE TABLE `t_occupations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `occupation_name` varchar(255) DEFAULT NULL,
  `occupation_no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `occupation_level` int(11) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_overtimetype
-- ----------------------------
DROP TABLE IF EXISTS `t_overtimetypes`;
CREATE TABLE `t_overtimetypes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_actions
-- ----------------------------
DROP TABLE IF EXISTS `t_actions`;
CREATE TABLE `t_actions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_post
-- ----------------------------
DROP TABLE IF EXISTS `t_posts`;
CREATE TABLE `t_posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_name` varchar(255) DEFAULT NULL,
  `post_no` varchar(255) DEFAULT NULL,
  `post_describe` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2558 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_post_copy
-- ----------------------------
DROP TABLE IF EXISTS `t_post_copys`;
CREATE TABLE `t_post_copys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_name` varchar(255) DEFAULT NULL,
  `post_no` varchar(255) DEFAULT NULL,
  `post_describe` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2459 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_post_department
-- ----------------------------
DROP TABLE IF EXISTS `t_post_departments`;
CREATE TABLE `t_post_departments` (
  `post_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  PRIMARY KEY (`post_id`,`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_roles
-- ----------------------------
DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_actions
-- ----------------------------
DROP TABLE IF EXISTS `t_role_actions`;
CREATE TABLE `t_role_actions` (
  `role_id` bigint(20) NOT NULL,
  `action_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sealtype
-- ----------------------------
DROP TABLE IF EXISTS `t_sealtypes`;
CREATE TABLE `t_sealtypes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `update_time` varchar(25) DEFAULT NULL,
  `create_time` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_staffs`;
CREATE TABLE `t_staffs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_no` varchar(50) DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  `personal_id` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `native_place` varchar(255) DEFAULT NULL,
  `marriage` tinyint(4) DEFAULT NULL,
  `political` varchar(20) DEFAULT NULL,
  `blood` varchar(50) DEFAULT NULL,
  `health` varchar(255) DEFAULT NULL,
  `party_date` varchar(50) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `highest_degree` varchar(255) DEFAULT NULL,
  `highest_degree_major` varchar(255) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `forensic_time` varchar(50) DEFAULT NULL,
  `certificate_number` varchar(255) DEFAULT NULL,
  `other_certificate` varchar(255) DEFAULT NULL,
  `other_forensic_time` varchar(50) DEFAULT NULL,
  `other_certificate_number` varchar(255) DEFAULT NULL,
  `job_time` varchar(50) DEFAULT NULL,
  `enterprise_time` varchar(50) DEFAULT NULL,
  `home_address` varchar(255) DEFAULT NULL,
  `home_phone_num` varchar(50) DEFAULT NULL,
  `account_address` varchar(255) DEFAULT NULL,
  `archives_address` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `fixed_phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job_project` varchar(255) DEFAULT NULL,
  `occupation_id` bigint(20) DEFAULT NULL,
  `title_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1579 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_staff_copy
-- ----------------------------
DROP TABLE IF EXISTS `t_staff_copys`;
CREATE TABLE `t_staff_copys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_no` varchar(50) DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  `personal_id` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `native_place` varchar(255) DEFAULT NULL,
  `marriage` tinyint(4) DEFAULT NULL,
  `political` varchar(20) DEFAULT NULL,
  `blood` varchar(50) DEFAULT NULL,
  `health` varchar(255) DEFAULT NULL,
  `party_date` varchar(50) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `highest_degree` varchar(255) DEFAULT NULL,
  `highest_degree_major` varchar(255) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `forensic_time` varchar(50) DEFAULT NULL,
  `certificate_number` varchar(255) DEFAULT NULL,
  `other_certificate` varchar(255) DEFAULT NULL,
  `other_forensic_time` varchar(50) DEFAULT NULL,
  `other_certificate_number` varchar(255) DEFAULT NULL,
  `job_time` varchar(50) DEFAULT NULL,
  `enterprise_time` varchar(50) DEFAULT NULL,
  `home_address` varchar(255) DEFAULT NULL,
  `home_phone_num` varchar(50) DEFAULT NULL,
  `account_address` varchar(255) DEFAULT NULL,
  `archives_address` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `fixed_phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job_project` varchar(255) DEFAULT NULL,
  `occupation_id` bigint(20) DEFAULT NULL,
  `title_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `old_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1498 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_staff_post
-- ----------------------------
DROP TABLE IF EXISTS `t_staff_posts`;
CREATE TABLE `t_staff_posts` (
  `staff_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`staff_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_subject_post
-- ----------------------------
DROP TABLE IF EXISTS `t_subject_posts`;
CREATE TABLE `t_subject_posts` (
  `su_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`su_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_titles
-- ----------------------------
DROP TABLE IF EXISTS `t_titles`;
CREATE TABLE `t_titles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title_name` varchar(255) DEFAULT NULL,
  `title_no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `plain_password` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `register_date` date DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `photo` text,
  `s_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=925 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflow
-- ----------------------------
DROP TABLE IF EXISTS `t_workflows`;
CREATE TABLE `t_workflows` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `point` tinyint(4) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `staff_ids` varchar(5000) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `staff_names` varchar(5000) DEFAULT NULL,
  `occupation_ids` varchar(1000) DEFAULT NULL,
  `occupation_names` varchar(1000) DEFAULT NULL,
  `post_ids` varchar(1000) DEFAULT NULL,
  `post_names` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=368 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflow_post
-- ----------------------------
DROP TABLE IF EXISTS `t_workflow_posts`;
CREATE TABLE `t_workflow_posts` (
  `wf_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`wf_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------
-- Table structure for t_workflow_staffs
-- -------------------------------------
DROP TABLE IF EXISTS `t_workflow_staffs`;
CREATE TABLE `t_workflow_staffs` (
  `wf_id` bigint(20) NOT NULL,
  `s_id` bigint(20) NOT NULL,
  PRIMARY KEY (`wf_id`,`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflowinstance
-- ----------------------------
DROP TABLE IF EXISTS `t_workflowinstances`;
CREATE TABLE `t_workflowinstances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wf_id` bigint(20) DEFAULT NULL,
  `instance_num` varchar(255) DEFAULT NULL,
  `point_assignee` varchar(255) DEFAULT NULL,
  `point_approve_time` varchar(255) DEFAULT NULL,
  `point_receive_time` varchar(255) DEFAULT NULL,
  `point_create_time` varchar(255) DEFAULT NULL,
  `apply_user` varchar(255) DEFAULT NULL,
  `apply_user_no` varchar(255) DEFAULT NULL,
  `apply_user_dept_name` varchar(255) DEFAULT NULL,
  `point_reason` varchar(5000) DEFAULT NULL,
  `point_num` tinyint(4) DEFAULT NULL,
  `point_name` varchar(255) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `sub_staff_id` bigint(20) DEFAULT NULL,
  `manager_staff_id` bigint(20) DEFAULT NULL,
  `buss_id` bigint(20) DEFAULT NULL,
  `ifreject` tinyint(4) DEFAULT NULL,
  `is_complete` tinyint(4) DEFAULT NULL,
  `attachment_path` varchar(255) DEFAULT NULL,
  `attachment_name` varchar(255) DEFAULT NULL,
  `reserve1` varchar(255) DEFAULT NULL,
  `reserve2` varchar(255) DEFAULT NULL,
  `reserve3` varchar(255) DEFAULT NULL,
  `reserve4` varchar(255) DEFAULT NULL,
  `reserve5` varchar(255) DEFAULT NULL,
  `reserve6` varchar(255) DEFAULT NULL,
  `reserve7` varchar(255) DEFAULT NULL,
  `reserve8` varchar(255) DEFAULT NULL,
  `reserve9` varchar(255) DEFAULT NULL,
  `reserve10` varchar(255) DEFAULT NULL,
  `reserve11` varchar(255) DEFAULT NULL,
  `reserve12` varchar(255) DEFAULT NULL,
  `reserve13` varchar(255) DEFAULT NULL,
  `reserve14` varchar(255) DEFAULT NULL,
  `reserve15` varchar(255) DEFAULT NULL,
  `reserve16` varchar(255) DEFAULT NULL,
  `reserve17` varchar(255) DEFAULT NULL,
  `reserve18` varchar(255) DEFAULT NULL,
  `reserve19` varchar(255) DEFAULT NULL,
  `reserve20` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18149 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflowpointinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_workflowpointinfos`;
CREATE TABLE `t_workflowpointinfos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wfi_id` bigint(20) DEFAULT NULL,
  `point_num` tinyint(4) DEFAULT NULL,
  `point_name` varchar(255) DEFAULT NULL,
  `approval_staff_id` bigint(20) DEFAULT NULL,
  `approval_state` tinyint(4) DEFAULT NULL,
  `reason` varchar(5000) DEFAULT NULL,
  `task_receive_time` varchar(255) DEFAULT NULL,
  `task_cross_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126189 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflowrule
-- ----------------------------
DROP TABLE IF EXISTS `t_workflowrules`;
CREATE TABLE `t_workflowrules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `occupation_ids` varchar(255) DEFAULT NULL,
  `post_ids` varchar(255) DEFAULT NULL,
  `staff_ids` varchar(255) DEFAULT NULL,
  `occupation_names` varchar(255) DEFAULT NULL,
  `post_names` varchar(255) DEFAULT NULL,
  `staff_names` varchar(255) DEFAULT NULL,
  `work_flow_id` bigint(20) DEFAULT NULL,
  `point_name` varchar(255) DEFAULT NULL,
  `order_number` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15688 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_workflowtype
-- ----------------------------
DROP TABLE IF EXISTS `t_workflowtypes`;
CREATE TABLE `t_workflowtypes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
