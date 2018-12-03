var title = function(){};
title.gridId = "#table_title";
title.pagerId = "#pager_title";
title.searchDivInput = "#searchTitleDiv :input";
title.searchBtn = "#searchBtn";
title.params = 'params';
$(function(){
	util.initSelectMenu('titleLi');
	$(title.gridId).jqGrid(title.gridOption);
	$(window).resize(function(){
		$(title.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteTitle('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var titleIds = new Array();
title.gridOption = { 
		url: context+"/title/list",
		postData: {'param':{}},
		colNames : ['职称职级名称','职称职级代码','备注','操作'],
		colModel : [
		            {name : 'titleName', width : 50, align : "center"},
		            {name : 'titleNo', width : 50, align : "center"},
		            {name : 'remark', width : 100, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "职称职级",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: title.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(title.gridId, titleIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(titleIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(titleIds, rowIds, status);
			else
				macIds = new Array();
		}
};

title.addTitleOptions = 
{
		
		url		:	'/title/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addTitleModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
	                title: "职称职级名称已存在",
	                type: "error"
			     });
			}else if(data === 3){
				swal({
	                title: "职称职级代码已存在",
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

title.editTitleOptions = 
{
		
		url		:	'/title/edit'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editTitleModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
	                title: "职称职级名称已存在",
	                type: "error"
			     });
			}else if(data === 3){
				swal({
	                title: "职称职级代码已存在",
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

title.delTitleOptions = 
{
		
		url		:	'/title/delete'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data.indexOf('删除成功') >=0){
				swal({
					title: data,
					type: "success"
				});
			}else{
				swal({
					title: data,
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
	util.searchListWithParams(title.gridId,title.searchDivInput,title.params);
};
var showAddModal = function(){
	$('#addTitleName').val('');
	$('#addTitleNo').val('');
	$('#remark').val('');
	util.showModal('addTitleModal');
};

var saveTitle = function(){
	var canSave = util.validate("addTitleDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addTitleDiv :input');
	title.addTitleOptions.postData = {
		params : params
	};
	util.commAjax(title.addTitleOptions);
};

var showEditModal = function(titleId){
	var rowData = $(title.gridId).jqGrid('getRowData', titleId);
	var titleName = rowData["titleName"];
	var titleNo = rowData["titleNo"];
	var remark = rowData["remark"];
	$('#editTitleName').val(titleName);
	$('#editTitleNo').val(titleNo);
	$('#editRemark').val(remark);
	$('#selTitleId').val(titleId);
	util.showModal('editTitleModal');
};

var saveEditTitle = function(){
	var canSave = util.validate("EditTitleDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#EditTitleDiv :input');
	var selectId = $('#selTitleId').val();
	title.editTitleOptions.postData = {
		params : params,
		titleId : selectId
	};
	util.commAjax(title.editTitleOptions);
};

var deleteTitle = function(titleId){
	var selTitleIds =  titleId;
	if(titleId == undefined){
		var titleIds = $(title.gridId).jqGrid("getGridParam", "selarrrow");
		if(titleIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selTitleIds = titleIds.join(',');
	}
	title.delTitleOptions.postData = {
			titleIds : selTitleIds
	};
	swal({
        title: "确定删除选择的职称职级?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(title.delTitleOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
