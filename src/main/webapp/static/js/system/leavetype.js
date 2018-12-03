var leaveType = function(){};
leaveType.gridId = "#table_leaveType";
leaveType.pagerId = "#pager_leaveType";
leaveType.searchDivInput = "#searchLeaveTypeDiv :input";
leaveType.searchBtn = "#searchBtn";
leaveType.params = 'params';
$(function(){
	util.initSelectMenu('leaveTypeLi');
	$(leaveType.gridId).jqGrid(leaveType.gridOption);
	$(window).resize(function(){
		$(leaveType.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteleaveType('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var leaveTypeIds = new Array();
leaveType.gridOption = { 
		url: context+"/leaveType/list",
		postData: {'param':{}},
		colNames : ['请假类型','备注','操作'],
		colModel : [
		            {name : 'name', width : 50, align : "center"},
		            {name : 'remark', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "请假类型",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: leaveType.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(leaveType.gridId, leaveTypeIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(leaveTypeIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(leaveTypeIds, rowIds, status);
			else
				macIds = new Array();
		} 
};

leaveType.addleaveTypeOptions = 
{
		
		url		:	'/leaveType/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addleaveTypeModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "请假类型已存在",
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

leaveType.editleaveTypeOptions = 
{
		
		url		:	'/leaveType/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editleaveTypeModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
					title: "请假类型已存在",
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

leaveType.delleaveTypeOptions = 
{
		
		url		:	'/leaveType/delete'					
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
	util.searchListWithParams(leaveType.gridId,leaveType.searchDivInput,leaveType.params);
};
var showAddModal = function(){
	$('#addLeaveTypeName').val('');
	$('#remark').val('');
	util.showModal('addleaveTypeModal');
};

var saveLeaveType = function(){
	var canSave = util.validate("addLeaveTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addLeaveTypeDiv :input');
	leaveType.addleaveTypeOptions.postData = {
		params : params
	};
	util.commAjax(leaveType.addleaveTypeOptions);
};

var showEditModal = function(leaveTypeId){
	var rowData = $(leaveType.gridId).jqGrid('getRowData', leaveTypeId);
	var leaveTypeName = rowData["name"];
	var remark = rowData["remark"];
	$('#editleaveTypeName').val(leaveTypeName);
	$('#editRemark').val(remark);
	$('#selleaveTypeId').val(leaveTypeId);
	util.showModal('editleaveTypeModal');
};

var saveEditLeaveType = function(){
	var canSave = util.validate("editLeaveTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editLeaveTypeDiv :input');
	var selectId = $('#selleaveTypeId').val();
	leaveType.editleaveTypeOptions.postData = {
		params : params,
		leaveTypeId : selectId
	};
	util.commAjax(leaveType.editleaveTypeOptions);
};

var deleteleaveType = function(leaveTypeId){
	var selleaveTypeIds =  leaveTypeId;
	if(leaveTypeId == undefined){
		var leaveTypeIds = $(leaveType.gridId).jqGrid("getGridParam", "selarrrow");
		if(leaveTypeIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selleaveTypeIds = leaveTypeIds.join(',');
	}
	leaveType.delleaveTypeOptions.postData = {
			leaveTypeIds : selleaveTypeIds
	};
	swal({
        title: "确定删除选择的请假类型?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(leaveType.delleaveTypeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
