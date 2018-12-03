var notice = function(){};
notice.gridId = "#table_notice";
notice.pagerId = "#pager_notice";
notice.searchDivInput = "#searchnoticeDiv :input";
notice.searchBtn = "#searchBtn";
notice.params = 'params';
notice.staffgridId = "#table_staff";
notice.staffpagerId = "#pager_staff";
notice.staffsearchDivInput = "#searchStaffDiv :input";
$(function(){
	util.initSelectMenu('noticeLi');
	$(notice.gridId).jqGrid(notice.gridOption);
	$(notice.staffgridId).jqGrid(notice.staffgridOption);
	$(window).resize(function(){
		$(notice.gridId).setGridWidth(util.getGridWith());
	});
});
notice.staffgridOption = { 
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
	   pager: notice.staffpagerId,  
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		autoScroll: false  
};
var staffsearchPargerList = function(){
	util.searchListWithParams(notice.staffgridId,notice.staffsearchDivInput,notice.params);
};
var addStaff = function(){
	var staffIdArr = $(notice.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIdArr.length == 0){
		swal({
            title: "请选择员工",
            type: "error"
	    });
		return;
	}
	if(staffIdArr.length > 0){
		for(var i=0;i<staffIdArr.length;i++){
			var rowData = $(notice.staffgridId).jqGrid('getRowData',staffIdArr[i]);
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
	return '<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteNotice('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}

var attachmentPathFormatter = function(cellvalue, options, rowObject){
	if(cellvalue == '' || cellvalue == undefined)
		return '';
	return '<a style="cursor:pointer;" target="_blank" href="'+cellvalue+'" >附件</a>';
}
var noticeIds = new Array();
notice.gridOption = { 
		url: context+"/notice/list",
		postData: {'param':{}},
		colNames : ['通知标题','通知内容','附件','发送时间','操作'],
		colModel : [
		            {name : 'title', width : 30, align : "center"},
		            {name : 'message', width : 50, align : "center"},
		            {name : 'attachmentName', width : 50, align : "center",formatter:attachmentPathFormatter},
		            {name : 'createTimeStr', width : 50, align : "center"},
		            {name : 'id', width : 20, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "通知",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: notice.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(notice.gridId, noticeIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(noticeIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(noticeIds, rowIds, status);
			else
				macIds = new Array();
		}
};
notice.addNoticeOptions = 
{
		
		url		:	'/notice/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addNoticeModal'); 
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
notice.delnoticeOptions = 
{
		
		url		:	'/notice/delete'					
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
	util.searchListWithParams(notice.gridId,notice.searchDivInput,notice.params);
};
var showAddModal = function(){
	$('#addNoticeNameForLook').val('');
	$('#remarkForLook').val('');
	$('#file').val('');
	$('#selStaffDiv').empty().append('<span style="float:left;width:100%;">已选择的员工(双击删除):</span>');
	staffsearchPargerList();
	util.showModal('addNoticeModal');
};
var saveNotice = function(){
	var canSave = util.validate("addNoticeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addNoticeDiv :input');
	notice.addNoticeOptions.postData = {
		params : params
	};
	util.commAjax(notice.addNoticeOptions);
};
var deleteNotice = function(noticeId){
	var selnoticeIds =  noticeId;
	if(noticeId == undefined){
		var noticeIds = $(notice.gridId).jqGrid("getGridParam", "selarrrow");
		if(noticeIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selnoticeIds = noticeIds.join(',');
	}
	notice.delnoticeOptions.postData = {
			noticeIds : selnoticeIds
	};
	swal({
        title: "确定删除选择的消息吗?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(notice.delnoticeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
};
var uploadFileTwo = function(){
	var allHasSelStaff = $("span[id^='selstaff_']");
	if(allHasSelStaff.length == 0){
		swal({
            title: "请选择通知人员",
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
	$('#addNoticeName').val($('#addNoticeNameForLook').val());
	$('#remark').val($('#remarkForLook').val());
	$('#fileNameForTwo').val($('#fileName').val());
	$('#fileDiv').ajaxSubmit(function(result){
		if(result > 0){
			swal({
				title: "保存成功",
				type: "success"
			},function(){
				util.hideModal('addNoticeModal'); 
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
