var budgetCommissioner = function(){};
budgetCommissioner.gridId = "#table_budgetCommissioner";
budgetCommissioner.pagerId = "#pager_budgetCommissioner";
budgetCommissioner.searchDivInput = "#searchbudgetCommissionerDiv :input";
budgetCommissioner.searchBtn = "#searchBtn";
budgetCommissioner.params = 'params';
$(function(){
	util.initSelectMenu('budgetCommissionerLi');
	$(budgetCommissioner.gridId).jqGrid(budgetCommissioner.gridOption);
	$(window).resize(function(){
		$(budgetCommissioner.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deletebudgetCommissioner('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var budgetCommissionerIds = new Array();
budgetCommissioner.gridOption = { 
		url: context+"/budgetCommissioner/list",
		postData: {'param':{}},
		colNames : ['预算专员','备注','操作'],
		colModel : [
		            {name : 'name', width : 50, align : "center"},
		            {name : 'remark', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "预算专员",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: budgetCommissioner.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(budgetCommissioner.gridId, budgetCommissionerIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(budgetCommissionerIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(budgetCommissionerIds, rowIds, status);
			else
				macIds = new Array();
		}
};

budgetCommissioner.addbudgetCommissionerOptions = 
{
		
		url		:	'/budgetCommissioner/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addbudgetCommissionerModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "预算专员已存在",
	                type: "error"
			     });
			}
			else
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

budgetCommissioner.editbudgetCommissionerOptions = 
{
		
		url		:	'/budgetCommissioner/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editbudgetCommissionerModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
					title: "预算专员已存在",
	                type: "error"
			     });
			}
			else
				swal({
					title: "编辑失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "编辑失败!",
	            type: "error"
		    });
		}		
}

budgetCommissioner.delbudgetCommissionerOptions = 
{
		
		url		:	'/budgetCommissioner/delete'					
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
	util.searchListWithParams(budgetCommissioner.gridId,budgetCommissioner.searchDivInput,budgetCommissioner.params);
};
var showAddModal = function(){
	$('#addbudgetCommissionerName').val('');
	$('#remark').val('');
	util.showModal('addbudgetCommissionerModal');
};

var savebudgetCommissioner = function(){
	var canSave = util.validate("addbudgetCommissionerDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addbudgetCommissionerDiv :input');
	budgetCommissioner.addbudgetCommissionerOptions.postData = {
		params : params
	};
	util.commAjax(budgetCommissioner.addbudgetCommissionerOptions);
};

var showEditModal = function(budgetCommissionerId){
	var rowData = $(budgetCommissioner.gridId).jqGrid('getRowData', budgetCommissionerId);
	var budgetCommissionerName = rowData["name"];
	var remark = rowData["remark"];
	$('#editbudgetCommissionerName').val(budgetCommissionerName);
	$('#editRemark').val(remark);
	$('#selbudgetCommissionerId').val(budgetCommissionerId);
	util.showModal('editbudgetCommissionerModal');
};

var saveEditbudgetCommissioner = function(){
	var canSave = util.validate("editbudgetCommissionerDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editbudgetCommissionerDiv :input');
	var selectId = $('#selbudgetCommissionerId').val();
	budgetCommissioner.editbudgetCommissionerOptions.postData = {
		params : params,
		budgetCommissionerId : selectId
	};
	util.commAjax(budgetCommissioner.editbudgetCommissionerOptions);
};

var deletebudgetCommissioner = function(budgetCommissionerId){
	var selbudgetCommissionerIds =  budgetCommissionerId;
	if(budgetCommissionerId == undefined){
		var budgetCommissionerIds = $(budgetCommissioner.gridId).jqGrid("getGridParam", "selarrrow");
		if(budgetCommissionerIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selbudgetCommissionerIds = budgetCommissionerIds.join(',');
	}
	budgetCommissioner.delbudgetCommissionerOptions.postData = {
			budgetCommissionerIds : selbudgetCommissionerIds
	};
	swal({
        title: "确定删除选择的预算专员?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(budgetCommissioner.delbudgetCommissionerOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
