var meeting = function(){};
meeting.gridId = "#table_meeting";
meeting.pagerId = "#pager_meeting";
meeting.searchDivInput = "#searchMeetingDiv :input";
meeting.searchBtn = "#searchBtn";
meeting.params = 'params';
meeting.staffgridId = "#table_staff";
meeting.staffpagerId = "#pager_staff";
meeting.staffsearchDivInput = "#searchStaffDiv :input";
$(function(){
	util.initSelectMenu('meetingLi');
	$(meeting.gridId).jqGrid(meeting.gridOption);
	$(meeting.staffgridId).jqGrid(meeting.staffgridOption);
	$(window).resize(function(){
		$(meeting.gridId).setGridWidth(util.getGridWith());
	});
});
meeting.staffgridOption = { 
		url: context+"/staff/list",
		postData: {'param':{}},
		colNames : ['姓名','员工编号','部门','职务','职称职级','岗位'],
		colModel : [
		            {name : 'staffName', width : 100, align : "center"},
		            {name : 'staffNo', width : 100, align : "center"},
		            {name : 'department.departmentName', width : 100, align : "center"},
		            {name : 'occupation.occupationName', width : 100, align : "center"},
		            {name : 'title.titleName', width : 100, align : "center"},
		            {name : 'postNames', width : 100, align : "center",sortable:false}
		],
		caption : "员工",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:50000, 
	   height:200,
	   width:560,
	   pager: meeting.staffpagerId,  
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		autoScroll: false,
};
var staffsearchPargerList = function(){
	util.searchListWithParams(meeting.staffgridId,meeting.staffsearchDivInput,meeting.params);
};
var addStaff = function(){
	var staffIdArr = $(meeting.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIdArr.length == 0){
		swal({
            title: "请选择员工",
            type: "error"
	    });
		return;
	}
	if(staffIdArr.length > 0){
		for(var i=0;i<staffIdArr.length;i++){
			var rowData = $(meeting.staffgridId).jqGrid('getRowData',staffIdArr[i]);
			var allHasSel = $("span[id^='selstaff_']");
			var canAdd = true;
			for(var j=0;j<allHasSel.length;j++){
				if(allHasSel[j].id == "selstaff_"+staffIdArr[i]){
					canAdd = false;
					break;
				}
			}
			if(canAdd)
				$('#selStaffDiv').append('<span id="selstaff_'+staffIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'+rowData.staffName+'</span>')
		}
	}
};
var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showUploadModal('+cellvalue+')"><i class="fa fa-paste"></i>新增会后文件</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteMeeting('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
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
		postData: {'params':'{"searchType":"0","meetingBeginTime":""}'},
		colNames : ['发起人','发起人部门','会议类型','会议开始时间','会议结束时间','会议地点','参会人员','会议主题','会议内容','会前附件','会后附件','操作'],
		colModel : [
		            {name : 'applyStaffName', width : 30, align : "center"},
		            {name : 'applyDeptName', width : 30, align : "center"},
		            {name : 'meetingTypeStr', width : 30, align : "center"},
		            {name : 'meetingBeginTime', width : 50, align : "center"},
		            {name : 'meetingEndTime', width : 50, align : "center"},
		            {name : 'meetingLocation', width : 50, align : "center"},
		            {name : 'meetingStaffNames', width : 50, align : "center"},
		            {name : 'meetingTitle', width : 60, align : "center"},
		            {name : 'meetingRemark', width : 100, align : "center"},
		            {name : 'meetingBeforeAttachmentsPath', width : 50, align : "center",formatter:function(cellvalue, options, rowObject){return attachmentsFormatter(cellvalue);}},
		            {name : 'meetingAfterAttachmentsPath', width : 50, align : "center",formatter:function(cellvalue, options, rowObject){return attachmentsFormatter(cellvalue);}},
		            {name : 'id', width : 60, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
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
meeting.addNoticeOptions = 
{
		
		url		:	'/meeting/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addMeetingModal'); 
		        });
				searchPargerList();
			}else
				swal({
					title: "保存失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "保存失败!",
	            type: "error"
		    });
		}		
}
meeting.delnoticeOptions = 
{
		
		url		:	'/meeting/delete'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "删除成功！",
					type: "success"
				});
			}else{
				swal({
					title: "删除失败！",
					type: "error"
				});
			}
				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "删除失败!",
	            type: "error"
		    });
		}		
}
var searchPargerList = function(){
	util.searchListWithParams(meeting.gridId,meeting.searchDivInput,meeting.params);
};
var showAddModal = function(){
	$('#addMeetingTheme').val('');
	$('#addMeetingType').val('-1');
	$('#addMeetingBeginDate').val('');
	$('#addMeetingEndDate').val('');
	$('#addMeetingLocation').val('');
	$('#addMeetingBriefly').val('');
	$('#file').val('');
	$('#selStaffDiv').empty().append('<span style="float:left;width:100%;">已选择的员工(双击删除):</span>');
	staffsearchPargerList();
	util.showModal('addMeetingModal');
};

var deleteMeeting = function(meetingId){
	var selnoticeIds =  meetingId;
	if(meetingId == undefined){
		var meetingIds = $(meeting.gridId).jqGrid("getGridParam", "selarrrow");
		if(meetingIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selnoticeIds = meetingIds.join(',');
	}
	meeting.delnoticeOptions.postData = {
			meetingIds : selnoticeIds
	};
	swal({
        title: "确定删除选择的会议吗?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(meeting.delnoticeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
};
var uploadFile = function(){
	var canSave = util.validate("addMeetingDiv");
	if(canSave == false)
		return false;
	var allHasSelStaff = $("span[id^='selstaff_']");
	if(allHasSelStaff.length == 0){
		swal({
            title: "请选择通知人员",
            type: "error"
	    });
		return;
	}
	if($('#addMeetingEndDate').val() < $('#addMeetingBeginDate').val()){
		swal({
            title: "会议结束时间不能早于会议开始时间",
            type: "error"
	    });
		return;
	}
	var selStaffIdArr = new Array();
	if(allHasSelStaff.length > 0){
		for(var i=0;i<allHasSelStaff.length;i++){
			selStaffIdArr.push(allHasSelStaff[i].id.split('_')[1]);
		}
	}
	$('#staffIds').val(selStaffIdArr.join(','));
	$('#fileDiv').ajaxSubmit(function(result){
		if(result > 0){
			swal({
				title: "保存成功",
				type: "success"
			},function(){
				util.hideModal('addMeetingModal'); 
				searchPargerList();
			});
		}else if(result == -100){
			swal({
				title: "文件大小不能超过10M",
				type: "error"
			});
		}else{
			swal({
				title: "保存失败！",
				type: "error"
			});
		}
	});
};
var showUploadModal = function(meetingId){
	$('#afile').val('');
	$('#meegtingId').val(meetingId);
	util.showModal('uploadAttModal');
};

var uploadFileAtt = function(){
	var file = $('#afile').val();
	if(file != null && file != ""){
		if(!/.(xls|xlsx|csv|doc|docx|zip|rar)$/.test(file)){
			swal({title: "请上传正确格式的文件",type: "error"});
			return false;
		}
	}
	loadingAjax();
	$('#aFileDiv').ajaxSubmit(function(result){
		hideAjax();
		if(result == 1){
			swal({
				title: "上传成功"
			},function(){
				$('#afile').val('');
				util.hideModal('uploadAttModal'); 
				searchPargerList();
			});
		}else{
			swal({title: "上传失败",type: "error"});
			return false;
		}
	});
};
var remindMeeting = function(){
	var meetingIds = $(meeting.gridId).jqGrid("getGridParam", "selarrrow");
	if(meetingIds.length < 1){
		swal({
			title: "至少选择一条记录提醒"
		});
		return;
	}
	selnoticeIds = meetingIds.join(',');
	meeting.remindMeetingOptions.postData = {
			meetingIds : selnoticeIds
	};
	swal({
        title: "确定通知所选会议的参会人员吗?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(meeting.remindMeetingOptions);
        } else {
            swal("已取消通知", "", "error");
        }
     });
};
meeting.remindMeetingOptions = 
{
		url		:	'/meeting/remindMeeting'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "通知成功！",
					type: "success"
				});
			}else{
				swal({
					title: "通知失败！",
					type: "error"
				});
			}
				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "通知失败!",
	            type: "error"
		    });
		}
}