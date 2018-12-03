var sealType = function(){};
sealType.gridId = "#table_sealType";
sealType.pagerId = "#pager_sealType";
sealType.searchDivInput = "#searchsealTypeDiv :input";
sealType.searchBtn = "#searchBtn";
sealType.params = 'params';
$(function(){
	util.initSelectMenu('sealTypeLi');
	$(sealType.gridId).jqGrid(sealType.gridOption);
	$(window).resize(function(){
		$(sealType.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deletesealType('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var sealTypeIds = new Array();
sealType.gridOption = { 
		url: context+"/sealType/list",
		postData: {'param':{}},
		colNames : ['印章类型','备注','操作'],
		colModel : [
		            {name : 'name', width : 50, align : "center"},
		            {name : 'remark', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "印章类型",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: sealType.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(sealType.gridId, sealTypeIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(sealTypeIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(sealTypeIds, rowIds, status);
			else
				macIds = new Array();
		} 
};

sealType.addsealTypeOptions = 
{
		
		url		:	'/sealType/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addsealTypeModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "印章类型已存在",
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

sealType.editsealTypeOptions = 
{
		
		url		:	'/sealType/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editsealTypeModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
					title: "印章类型已存在",
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

sealType.delsealTypeOptions = 
{
		
		url		:	'/sealType/delete'					
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
	util.searchListWithParams(sealType.gridId,sealType.searchDivInput,sealType.params);
};
var showAddModal = function(){
	$('#addSealTypeName').val('');
	$('#remark').val('');
	util.showModal('addsealTypeModal');
};

var savesealType = function(){
	var canSave = util.validate("addsealTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addsealTypeDiv :input');
	sealType.addsealTypeOptions.postData = {
		params : params
	};
	util.commAjax(sealType.addsealTypeOptions);
};

var showEditModal = function(sealTypeId){
	var rowData = $(sealType.gridId).jqGrid('getRowData', sealTypeId);
	var sealTypeName = rowData["name"];
	var remark = rowData["remark"];
	$('#editsealTypeName').val(sealTypeName);
	$('#editRemark').val(remark);
	$('#selsealTypeId').val(sealTypeId);
	util.showModal('editsealTypeModal');
};

var saveEditsealType = function(){
	var canSave = util.validate("editSealTypeDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editSealTypeDiv :input');
	var selectId = $('#selsealTypeId').val();
	sealType.editsealTypeOptions.postData = {
		params : params,
		sealTypeId : selectId
	};
	util.commAjax(sealType.editsealTypeOptions);
};

var deletesealType = function(sealTypeId){
	var selsealTypeIds =  sealTypeId;
	if(sealTypeId == undefined){
		var sealTypeIds = $(sealType.gridId).jqGrid("getGridParam", "selarrrow");
		if(sealTypeIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selsealTypeIds = sealTypeIds.join(',');
	}
	sealType.delsealTypeOptions.postData = {
			sealTypeIds : selsealTypeIds
	};
	swal({
        title: "确定删除选择的印章类型?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(sealType.delsealTypeOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
