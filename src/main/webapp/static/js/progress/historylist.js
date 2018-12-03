var historylist = function() {
};
historylist.gridId = "#table_workflowInstance";
historylist.pagerId = "#pager_workflowInstance";
historylist.searchDivInput = "#searchWorkflowInstanceDiv :input";
historylist.searchBtn = "#searchBtn";
historylist.params = 'params';

historylist.searchWorkflowTypeInputSel = '#workflowType';
historylist.InputSelDefault = '请选择';

$(function() {
	util.initSelectMenu('instanceHistorylistLi');
	$(historylist.gridId).jqGrid(historylist.gridOption);
	$(window).resize(function() {
		$(historylist.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(historylist.workflowtypesOptions);
});

historylist.workflowtypesOptions = {
	url : '/workflow/getAllWorkflowType',
	mtype : 'POST',
	postData : {},
	success : function(data, st, xhr) {
		util.initSelectArray(historylist.searchWorkflowTypeInputSel, data,
				historylist.InputSelDefault);
	}
}

var operationFormatter = function(cellvalue,rowObject) {
	var type = rowObject.workFlow.workFlowType.id;
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showInstanceModal('+cellvalue+','+type+')">查看</button>';
}

var resultFormatter = function(cellvalue) {
	var result = "已审批";
	if (cellvalue == 2)
		result = "已撤销";
	return result;
}
var historylistIds = new Array();
historylist.gridOption = {
	url : context + "/workflowInstance/historylist",
	postData : {
		'param' : {}
	},
	colNames : [ '结果', '操作', '流程类别', '流程编号', '流程名称', '申请人', '申请人编号', '申请人部门','最后节点意见', '开始时间', '结束时间'],
	colModel : [ 
	             {name : 'isComplete',width : 50,align : "center",formatter : function(cellvalue, options, rowObject) {return resultFormatter(cellvalue);}}, 
	             {name : 'id',width : 50,align : "center",formatter : function(cellvalue, options, rowObject) {return operationFormatter(cellvalue,rowObject);}}, 
	             {name : 'workFlow.workFlowType.name',width : 100,align : "center"}, 
	             {name : 'instanceNum',width : 100,align : "center"}, 
	             {name : 'workFlow.name',width : 100,align : "center"}, 
	             {name : 'applyUser',width : 100,align : "center"}, 
	             {name : 'applyUserNo',width : 100,align : "center"},
	             {name : 'applyUserDeptName',width : 100,align : "center"}, 
	             {name : 'pointReason',width : 200,align : "center"}, 
	             {name : 'pointCreateTime',width : 100,align : "center"},
	             {name : 'pointApproveTime',width : 100,align : "center"}
	             ],
	caption : "历史流程列表",
	mtype : "POST",
	datatype : "json",
	emptyrecords : "无符合条件数据",
	rowNum : 20,
	height : '100%',
	pager : historylist.pagerId,
	autowidth : true,
	gridview : false,
	viewrecords : true,
	multiselect : true,
	multiselectWidth : 25,
	loadComplete: function(xhr){
		onGridLoadComplete(historylist.gridId, historylistIds);
	},
	onSelectRow: function(rowId, status){
		onGridSelectRow(historylistIds, rowId, status);
	},
	onSelectAll: function(rowIds, status){
		if(status)
			onGridSelectAll(historylistIds, rowIds, status);
		else
			macIds = new Array();
	}
};

var searchPargerList = function() {
	util.searchListWithParams(historylist.gridId, historylist.searchDivInput,
			historylist.params);
};

var showInstanceModal = function(instanceId,type) {
	lookOrOperateForUtil = 2;
	workflowInstanceIdForUtil = instanceId;
	workFlowTypeForUtil = type;
	hasPrintForUtil = true;
	getResultForInstanceLook();
};

var printFormInfo = function(){
	$('#instanceInfoModal .panel-body').printArea();
}
