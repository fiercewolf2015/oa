var budgetModel = function(){};
budgetModel.financeSubjectTreeId = '#financeSubjectTree';
budgetModel.gridId = "#table_budgetModel";
budgetModel.pagerId = "#pager_budgetModel";
budgetModel.searchDivInput = "#searchBudgeModelDiv :input";
budgetModel.params = 'params';
$(function(){
	util.initSelectMenu('budgetModelLi');
	$(budgetModel.gridId).jqGrid(budgetModel.gridOption);
	$(window).resize(function(){
		$(budgetModel.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(budgetModel.loadFinanceSubjectTreeNodesOptions);
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="delModel('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
};
var budgetModelIds = new Array();
budgetModel.gridOption = {
		url : context + "/budgetModel/list",
		caption : "预算编制模板",
		postData : {
			'param' : {}
		},
		colNames : ['名称','备注','操作','subIds'],
		colModel : [ 
		    {name: 'name', width:100,align:"center",sorttype:"string"},
		    {name: 'remark', width:400,align:"center"},
		    {name: 'id', width:100,align:"center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}},
		    {name: 'subIds', hidden:true}
		],
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: budgetModel.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(budgetModel.gridId, budgetModelIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(budgetModelIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(budgetModelIds, rowIds, status);
			else
				macIds = new Array();
		}
};

var searchPargerList = function(){
	util.searchListWithParams(budgetModel.gridId,budgetModel.searchDivInput,budgetModel.params);
};

budgetModel.financeSubjectTreeSetting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "N": "s", "Y": "p" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

budgetModel.loadFinanceSubjectTreeNodesOptions = {
		url		:	'/financeSubject/loadFinanceSubjectTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(budgetModel.financeSubjectTreeId ), budgetModel.financeSubjectTreeSetting, data);
			} else
				swal({
		            title: "加载数据失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "加载数据失败!",
	            type: "error"
		    });
		}		
};

var showAddModal = function(){
	$('#addName').val('');
	$('#addRemark').val('');
	$('#selModelId').val('');
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	treeObj.checkAllNodes(false);
	util.showModal('addBudgetingModal');
};

budgetModel.addBudgetModelOptions = {
		url		:	'/budgetModel/add'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			if (data === 1) {
				swal({
		                title: "保存成功!",
		                type: "success"
			        },
			        function(){
			        	util.hideModal('addBudgetingModal');
			        	searchPargerList();
			        });
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
};
var saveModel = function(){
	var addName = $('#addName').val();
	if(isEmpty(addName)){
		swal({
            title: "请填写模板名称!",
            type: "error"
	    });
		return;
	}
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var nodes = treeObj.getCheckedNodes(true);
	if(nodes.length ==0){
		swal({
            title: "请选择科目!",
            type: "error"
	    });
		return;
	}
	var hasChild = false;
	for(var i=0;i<nodes.length;i++){
		if(!nodes[i].isParent){
			hasChild = true;
			break;
		}
	}
	if(!hasChild){
		swal({
            title: "请选择最底层节点!",
            type: "error"
	    });
		return;
	}
	var nodeIdArr = new Array();
	for(var i=1;i<nodes.length;i++){
		nodeIdArr.push(nodes[i].id);
	}
	budgetModel.addBudgetModelOptions.postData={
			name:addName,
			remark:$('#addRemark').val(),
			subIds:nodeIdArr.join(","),
			id:$('#selModelId').val()
	}
	util.commAjax(budgetModel.addBudgetModelOptions);
};

var showEditModal = function(id){
	$('#selModelId').val(id);
	var rowData = $(budgetModel.gridId).jqGrid('getRowData', id);
	$('#addName').val(rowData["name"]);
	$('#addRemark').val(rowData["remark"]);
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	treeObj.expandAll(true);
	treeObj.checkAllNodes(false);
	var subIdsArr = rowData["subIds"].split(',');
	for (var i=0 ;i< subIdsArr.length;i++){
		var node = treeObj.getNodeByParam("id", subIdsArr[i], null);
		if (node)
			treeObj.checkNode(node,true,true);
	}
	util.showModal('addBudgetingModal');
};

var delModel = function(id){
	budgetModel.delModelOptions.postData = {
			id : id
	};
	swal({
        title: "确认删除该预算编制模板?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(budgetModel.delModelOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });	
};

budgetModel.delModelOptions = 
{
		
		url		:	'/budgetModel/delete'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "删除成功！",
					type: "success"
				});
			}
			else if(data == -1){
				swal({
					title: "有预算编制应用了此模板,不能删除！",
					type: "error"
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
};
