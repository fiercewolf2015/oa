var notice = function(){};
notice.gridId = "#table_notice";
notice.pagerId = "#pager_notice";
notice.searchDivInput = "#searchnoticeDiv :input";
notice.searchBtn = "#searchBtn";
notice.params = 'params';
$(function(){
	$(notice.gridId).jqGrid(notice.gridOption);
	$(window).resize(function(){
		$(notice.gridId).setGridWidth(util.getGridWith());
	});
});
var attachmentPathFormatter = function(cellvalue, options, rowObject){
	if(cellvalue == '' || cellvalue == undefined)
		return '';
	return '<a style="cursor:pointer;" target="_blank" href="'+cellvalue+'" >附件</a>';
}
var readFormatter = function(cellvalue, options, rowObject){
	return cellvalue == 0 ? "未读" : "已读";
}
var noticeIds = new Array();
notice.gridOption = { 
		url: context+"/newnotice/list",
		postData: {'param':{}},
		colNames : ['状态','通知标题','通知内容','附件','发送时间'],
		colModel : [
		            {name : 'unread', width : 30, align : "center",formatter:readFormatter},
		            {name : 'notice.title', width : 30, align : "center"},
		            {name : 'notice.message', width : 50, align : "center"},
		            {name : 'notice.attachmentName', width : 50, align : "center",formatter:attachmentPathFormatter},
		            {name : 'notice.createTimeStr', width : 50, align : "center"}
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
var searchPargerList = function(){
	util.searchListWithParams(notice.gridId,notice.searchDivInput,notice.params);
};
var changeReadState = function(){
	var noticeIds = $(notice.gridId).jqGrid("getGridParam", "selarrrow");
	if(noticeIds.length < 1){
		swal({
			title: "至少选择一条记录删除"
		});
		return;
	}
	selnoticeIds = noticeIds.join(',');
	notice.delnoticeOptions.postData = {
			noticeIds : selnoticeIds
	};
	util.commAjax(notice.delnoticeOptions);
}
notice.delnoticeOptions = 
{
		url		:	'/newnotice/changeReadState'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "标记成功！",
					type: "success"
				});
				searchPargerList();
			}else{
				swal({
					title: "标记失败！",
					type: "error"
				});
			}
				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "标记失败!",
	            type: "error"
		    });
		}
}