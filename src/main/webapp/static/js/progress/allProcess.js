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
	util.initSelectMenu('allProcesslistLi');
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
//'<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="processNextPoint('+cellvalue+','+type+')">流转下一节点</button>'+
var operationFormatter = function(cellvalue,rowObject){
	var type = rowObject.workFlow.workFlowType.id;
		return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showInstanceModal('+cellvalue+','+type+')">查看</button>'+
		'<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="revokeInstance('+cellvalue+')">撤销</button>';
}
var historylistIds = new Array();
historylist.gridOption = {
	url : context + "/workflowInstance/allProcess",
	postData : {
		'param' : {}
	},
	colNames : ['操作', '流程类别', '流程编号', '流程名称', '申请人', '申请人编号', '申请人部门','最后节点意见', '开始时间', '结束时间'],
	colModel : [ 
	             {name : 'id',width : 80,align : "center",formatter : function(cellvalue, options, rowObject) {return operationFormatter(cellvalue,rowObject);}}, 
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
var revokeInstance = function(instanceId){
	swal({
		title: "确定撤销该条流程?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "确定",
		cancelButtonText: "取消",
		closeOnConfirm: false,
		closeOnCancel: false },
		function (isConfirm) {
			if (isConfirm) {
				historylist.revokeWorkflowInstanceOptions.postData = {
						instanceId:instanceId
				}
				util.commAjax(historylist.revokeWorkflowInstanceOptions);
			} else {
				swal("已取消撤销", "", "error");
			}
		});
};
historylist.revokeWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/revoke'							
			,	mtype	:	'POST'						
				,	postData	:	{}							
,	success :	function(data,st, xhr){		
		if (data === 1) {
			swal({
				title: "操作成功!",
				type: "success"
			});
			searchPargerList();
		}else
			swal({
				title: "操作失败!",
				type: "error"
			});
	}	
,	error	:	function(xhr,st,err){		
		swal({
			title: "操作失败!",
			type: "error"
		});
	}		
}
var processNextPoint = function(instanceId,type){
	swal({
		title: "确定将该条流程流转到下一节点?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "确定",
		cancelButtonText: "取消",
		closeOnConfirm: false,
		closeOnCancel: false },
		function (isConfirm) {
			if (isConfirm) {
				historylist.processNextPointWorkflowInstanceOptions.postData = {
						instanceId:instanceId,
						type:type
				}
				util.commAjax(historylist.processNextPointWorkflowInstanceOptions);
			} else {
				swal("已取消撤销", "", "error");
			}
		});
};
historylist.processNextPointWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/process'							
			,	mtype	:	'POST'						
				,	postData	:	{}							
,	success :	function(data,st, xhr){		
		if (data === 1) {
			swal({
				title: "操作成功!",
				type: "success"
			});
			searchPargerList();
		}else if(data == -99){
			swal({
				title: "此功能不支持文件阅办与财务票据流程",
				type: "error"
			});
		}else if(data == -100){
			swal({
				title: "此功能不支持驳回流程",
				type: "error"
			});
		}else
			swal({
				title: "操作失败!",
				type: "error"
			});
	}	
,	error	:	function(xhr,st,err){		
		swal({
			title: "操作失败!",
			type: "error"
		});
	}		
}
var searchPargerList = function() {
	util.searchListWithParams(historylist.gridId, historylist.searchDivInput,
			historylist.params);
};

var showInstanceModal = function(instanceId,type) {
	lookOrOperateForUtil = 2;
	workflowInstanceIdForUtil = instanceId;
	workFlowTypeForUtil = type;
	hasPrintForUtil = false;
	getResultForInstanceLook();
};

var printFormInfo = function(){
	$('#instanceInfoModal .panel-body').printArea();
}
