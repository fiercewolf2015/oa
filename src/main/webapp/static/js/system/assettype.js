var assetType = function(){};
assetType.gridId = "#table_assetType";
assetType.pagerId = "#pager_assetType";
assetType.searchDivInput = "#searchassetTypeDiv :input";
assetType.searchBtn = "#searchBtn";
assetType.params = 'params';
$(function(){
	util.initSelectMenu('assetTypeLi');
	$(assetType.gridId).jqGrid(assetType.gridOption);
	$(window).resize(function(){
		$(assetType.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteassetType('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var assetTypeIds = new Array();
assetType.gridOption = { 
		url: context+"/assetType/list",
		postData: {'param':{}},
		colNames : ['资产业务类型','备注','操作'],
		colModel : [
		            {name : 'name', width : 50, align : "center"},
		            {name : 'remark', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "资产业务类型",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: assetType.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(assetType.gridId, assetTypeIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(assetTypeIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(assetTypeIds, rowIds, status);
			else
				macIds = new Array();
		}
};

assetType.addassetTypeOptions = 
{
		
		url		:	'/assetType/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addassetTypeModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "资产业务类型已存在",
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

assetType.editassetTypeOptions = 
{
		
		url		:	'/assetType/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editassetTypeModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
					title: "资产业务类型已存在",
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

assetType.delassetTypeOptions = 
{
		
		url		:	'/assetType/delete'					
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
	util.searchListWithParams(assetType.gridId,assetType.searchDivInput,assetType.params);
};
var showAddModal = function(){
	$('#addassetTypeName').val('');
	$('#remark').val('');
	util.showModal('addassetTypeModal');
};

var saveassetType = function(){
	var canSave = util.validate("addassetTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addassetTypeDiv :input');
	assetType.addassetTypeOptions.postData = {
		params : params
	};
	util.commAjax(assetType.addassetTypeOptions);
};

var showEditModal = function(assetTypeId){
	var rowData = $(assetType.gridId).jqGrid('getRowData', assetTypeId);
	var assetTypeName = rowData["name"];
	var remark = rowData["remark"];
	$('#editassetTypeName').val(assetTypeName);
	$('#editRemark').val(remark);
	$('#selassetTypeId').val(assetTypeId);
	util.showModal('editassetTypeModal');
};

var saveEditassetType = function(){
	var canSave = util.validate("editassetTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editassetTypeDiv :input');
	var selectId = $('#selassetTypeId').val();
	assetType.editassetTypeOptions.postData = {
		params : params,
		assetTypeId : selectId
	};
	util.commAjax(assetType.editassetTypeOptions);
};

var deleteassetType = function(assetTypeId){
	var selassetTypeIds =  assetTypeId;
	if(assetTypeId == undefined){
		var assetTypeIds = $(assetType.gridId).jqGrid("getGridParam", "selarrrow");
		if(assetTypeIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selassetTypeIds = assetTypeIds.join(',');
	}
	assetType.delassetTypeOptions.postData = {
			assetTypeIds : selassetTypeIds
	};
	swal({
        title: "确定删除选择的资产业务类型?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(assetType.delassetTypeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
