var overtimeType = function(){};
overtimeType.gridId = "#table_overtimeType";
overtimeType.pagerId = "#pager_overtimeType";
overtimeType.searchDivInput = "#searchOvertimeDiv :input";
overtimeType.searchBtn = "#searchBtn";
overtimeType.params = 'params';
$(function(){
	util.initSelectMenu('overtiemTypeLi');
	$(overtimeType.gridId).jqGrid(overtimeType.gridOption);
	$(window).resize(function(){
		$(overtimeType.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteovertimeType('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var overtimeTypeIds = new Array();
overtimeType.gridOption = { 
		url: context+"/overtimeType/list",
		postData: {'param':{}},
		colNames : ['加班类型','备注','操作'],
		colModel : [
		            {name : 'name', width : 50, align : "center"},
		            {name : 'remark', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "加班类型",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: overtimeType.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(overtimeType.gridId, overtimeTypeIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(overtimeTypeIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(overtimeTypeIds, rowIds, status);
			else
				macIds = new Array();
		} 
};

overtimeType.addovertimeTypeOptions = 
{
		
		url		:	'/overtimeType/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addovertimeTypeModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "加班类型已存在",
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

overtimeType.editovertimeTypeOptions = 
{
		
		url		:	'/overtimeType/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editovertimeTypeModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
					title: "加班类型已存在",
	                type: "error"
			     });
			}else
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

overtimeType.delovertimeTypeOptions = 
{
		
		url		:	'/overtimeType/delete'					
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
	util.searchListWithParams(overtimeType.gridId,overtimeType.searchDivInput,overtimeType.params);
};
var showAddModal = function(){
	$('#addOvertimeTypeName').val('');
	$('#remark').val('');
	util.showModal('addovertimeTypeModal');
};

var saveOvertimeType = function(){
	var canSave = util.validate("addOvertimeTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addOvertimeTypeDiv :input');
	overtimeType.addovertimeTypeOptions.postData = {
		params : params
	};
	util.commAjax(overtimeType.addovertimeTypeOptions);
};

var showEditModal = function(overtimeTypeId){
	var rowData = $(overtimeType.gridId).jqGrid('getRowData', overtimeTypeId);
	var overtimeTypeName = rowData["name"];
	var remark = rowData["remark"];
	$('#editovertimeTypeName').val(overtimeTypeName);
	$('#editRemark').val(remark);
	$('#selovertimeTypeId').val(overtimeTypeId);
	util.showModal('editovertimeTypeModal');
};

var saveEditOvertimeType = function(){
	var canSave = util.validate("editOvertimeTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editOvertimeTypeDiv :input');
	var selectId = $('#selovertimeTypeId').val();
	overtimeType.editovertimeTypeOptions.postData = {
		params : params,
		overtimeTypeId : selectId
	};
	util.commAjax(overtimeType.editovertimeTypeOptions);
};

var deleteovertimeType = function(overtimeTypeId){
	var selovertimeTypeIds =  overtimeTypeId;
	if(overtimeTypeId == undefined){
		var overtimeTypeIds = $(overtimeType.gridId).jqGrid("getGridParam", "selarrrow");
		if(overtimeTypeIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selovertimeTypeIds = overtimeTypeIds.join(',');
	}
	overtimeType.delovertimeTypeOptions.postData = {
			overtimeTypeIds : selovertimeTypeIds
	};
	swal({
        title: "确定删除选择的加班类型?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(overtimeType.delovertimeTypeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
