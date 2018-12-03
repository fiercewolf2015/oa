var donelist = function(){};
donelist.gridId = "#table_workflowInstance";
donelist.pagerId = "#pager_workflowInstance";
donelist.searchDivInput = "#searchWorkflowInstanceDiv :input";
donelist.searchBtn = "#searchBtn";
donelist.params = 'params';

donelist.searchWorkflowTypeInputSel = '#workflowType';
donelist.InputSelDefault = '请选择';

$(function(){
	util.initSelectMenu('instanceDonelistLi');
	$(donelist.gridId).jqGrid(donelist.gridOption);
	$(window).resize(function(){
		$(donelist.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(donelist.workflowtypesOptions);
});

donelist.workflowtypesOptions = 
{
		url		:	'/workflow/getAllWorkflowType'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		    util.initSelectArray(donelist.searchWorkflowTypeInputSel,data,donelist.InputSelDefault);
		}
}
var operationFormatter = function(cellvalue,rowObject){
	var type = rowObject.workFlow.workFlowType.id;
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showInstanceModal('+cellvalue+','+type+')">查看</button>';
}
var donelistIds = new Array();
donelist.gridOption = { 
		url: context+"/workflowInstance/donelist",
		postData: {'param':{}},
		colNames : ['操作','流程类别','流程编号','流程名称','申请人','申请人编号','申请人部门','当前节点','当前审批人','当前节点序号','接收时间'],
		colModel : [
		            {name : 'id', width : 50, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue,rowObject);}},
		            {name : 'workFlow.workFlowType.name', width : 100, align : "center"},
		            {name : 'instanceNum', width : 100, align : "center"},
		            {name : 'workFlow.name', width : 100, align : "center"},
		            {name : 'applyUser', width : 100, align : "center"},
		            {name : 'applyUserNo', width : 100, align : "center"},
		            {name : 'applyUserDeptName', width : 100, align : "center"},
		            {name : 'pointName', width : 100, align : "center"},
		            {name : 'pointAssignee', width : 100, align : "center"},
		            {name : 'pointNum', width : 100, align : "center"},
		            {name : 'pointCreateTime', width : 100, align : "center"}
		],
		caption : "已办流程列表",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: donelist.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(donelist.gridId, donelistIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(donelistIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(donelistIds, rowIds, status);
			else
				macIds = new Array();
		}
};

var searchPargerList = function(){
	util.searchListWithParams(donelist.gridId,donelist.searchDivInput,donelist.params);
};

var showInstanceModal = function(instanceId,type){
	lookOrOperateForUtil = 2;
	workflowInstanceIdForUtil = instanceId;
	workFlowTypeForUtil = type;
	hasPrintForUtil = false;
	getResultForInstanceLook();
};