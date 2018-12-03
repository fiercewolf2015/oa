var meeting = function(){};
meeting.gridId = "#table_meeting";
meeting.pagerId = "#pager_meeting";
meeting.searchDivInput = "#searchMeetingDiv :input";
meeting.searchBtn = "#searchBtn";
meeting.params = 'params';
$(function(){
	util.initSelectMenu('meetingRemindLi');
	$(meeting.gridId).jqGrid(meeting.gridOption);
	$(window).resize(function(){
		$(meeting.gridId).setGridWidth(util.getGridWith());
	});
});
var operationFormatter = function(cellvalue){
//	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showUploadModal('+cellvalue+')"><i class="fa fa-paste"></i>新增会后文件</button>'
//	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteMeeting('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
	return '';
};
var attachmentsFormatter = function(cellvalue){
	var html = '';
	if(cellvalue == '' || cellvalue == null || cellvalue == 'null')
		return html;
	var attArr = cellvalue.split(",");
	for (var i = 0; i < attArr.length; i++) {
		html += '<a href="'+attArr[i]+'">附件'+(i+1)+'</a>';
	}
	return html;
};
var meetingIds = new Array();
meeting.gridOption = { 
		url: context+"/meeting/list",
		postData: {'params':'{"searchType":"2"}'},
		colNames : ['发起人','发起人部门','会议类型','会议开始时间','会议结束时间','会议地点','会议主题','会议内容','会前附件','会后附件','操作'],
		colModel : [
		            {name : 'applyStaffName', width : 30, align : "center"},
		            {name : 'applyDeptName', width : 30, align : "center"},
		            {name : 'meetingTypeStr', width : 30, align : "center"},
		            {name : 'meetingBeginTime', width : 40, align : "center"},
		            {name : 'meetingEndTime', width : 40, align : "center"},
		            {name : 'meetingLocation', width : 50, align : "center"},
		            {name : 'meetingTitle', width : 100, align : "center"},
		            {name : 'meetingRemark', width : 100, align : "center"},
		            {name : 'meetingBeforeAttachmentsPath', width : 50, align : "center",formatter:function(cellvalue, options, rowObject){return attachmentsFormatter(cellvalue);}},
		            {name : 'meetingAfterAttachmentsPath', width : 50, align : "center",formatter:function(cellvalue, options, rowObject){return attachmentsFormatter(cellvalue);}},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "通知",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: meeting.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(meeting.gridId, meetingIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(meetingIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(meetingIds, rowIds, status);
			else
				macIds = new Array();
		}
};
var searchPargerList = function(){
	util.searchListWithParams(meeting.gridId,meeting.searchDivInput,meeting.params);
};