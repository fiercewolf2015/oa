var department = function(){};
department.departmentTreeId = '#departmentTree';
department.post_gridId = "#table_post";
department.post_pagerId = "#pager_post";
department.post_searchDivInput = "#searchPostDiv :input";
department.post_searchBtn = "#searchBtn";
department.post_params = 'params';
$(function(){
	var leftHeight = $('#leftPanel').height();
	var rightHeight = $('#rightPanel').height();
	if(leftHeight > rightHeight)
		$('#rightPanel').height(leftHeight);
	else
		$('#leftPanel').height(rightHeight);
	util.initSelectMenu('departmentLi');
	util.commAjax(department.loadDepartmentTreeNodesOptions);
	$(department.post_gridId).jqGrid(department.gridOption);
});

department.gridOption = { 
		url: context+"/post/list",
		postData: {'param':{}},
		colNames : ['岗位名称','岗位代码','岗位描述','备注'],
		colModel : [
		            {name : 'postName', width : 100, align : "center"},
		            {name : 'postNo', width : 100, align : "center"},
		            {name : 'postDescribe', width : 150, align : "center"},
		            {name : 'remarks', width : 150, align : "center"}
		],
		caption : "岗位",
		mtype:"POST",
	    datatype: "json",
	    emptyrecords:"无符合条件数据",
	    rowNum:10000, 
	    height:300,
	    pager: department.post_pagerId,  
	    autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(department.post_gridId, $('#post_ids').val().split(','));
		}
};
var searchPostList = function(){
	util.searchListWithParams(department.post_gridId,department.post_searchDivInput ,department.post_params);
};

var showPostModal = function(){
	$('#postName').val('');
	$('#postNo').val('');
	$(department.post_gridId).resetSelection();
	onGridLoadComplete(department.post_gridId, $('#post_ids').val().split(','));
	util.showModal('chosePostModal');
};
var saveChosePost = function(){
	var postIds = $(department.post_gridId).jqGrid("getGridParam", "selarrrow");
	var names = '';
	for (var i = 0; i < postIds.length; i++) {
		var rowData = $(department.post_gridId).jqGrid('getRowData', postIds[i]);
		names +=rowData['postName'];
		if(i != postIds.length-1)
			names+="，";
	}
	var selPostIds = postIds.join(',');
	$('#post_ids').val(selPostIds);
	$('#post_name').val(names);
	util.hideModal('chosePostModal');
}

department.departmentTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: departmentNodeBeforeclick,
			onClick: departmentNodeOnclick
		}
		
};
function departmentNodeBeforeclick(treeId, treeNode, clickFlag){
	clearInput();
};
function departmentNodeOnclick(event,treeId, treeNode){
	var deptId = treeNode.id;
	$('#editDepartmentPanel').text(treeNode.name+"属性");
	$('#departmentName').val(treeNode.name);
	$('#departmentNo').val(treeNode.department_no);
	$('#departmentLevel').val(treeNode.department_level);
	$('#departmentDescribe').val(treeNode.department_describe);
	$('#remarks').val(treeNode.remarks);
	$('#post_name').val(treeNode.post_name);
	$('#post_ids').val(treeNode.post_ids);
	
};
department.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(department.departmentTreeId), department.departmentTreeSetting, data);
//				$.fn.zTree.getZTreeObj('departmentTree').expandAll(false);
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

department.addDepartmentOptions = {
		url		:	'/department/add'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "保存成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(department.loadDepartmentTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "部门名称或部门代码已存在",
	                type: "error"
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

department.delDepartmentOptions = {
		url		:	'/department/delete'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "删除成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(department.loadDepartmentTreeNodesOptions);
			        });
			}else if(data === 2){
				swal({
	                title: "删除失败!",
	                text:"此部门（或下级部门）包含员工，不能删除",
	                type: "error"
		        });
			}else if(data === 3){
				swal({
	                title: "删除失败!",
	                text:"此部门（或下级部门）包含岗位，不能删除",
	                type: "error"
		        });
			}else
				swal({
	                title: "删除失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			swal({
	            title: "删除失败!",
	            type: "error"
		    });
		}		
};

department.editDepartmentOptions = {
		url		:	'/department/edit'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "编辑成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(department.loadDepartmentTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "部门名称或部门代码已存在",
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
};


var add = function(){
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门节点!",
            type: "error"
	    });
		return false;
	}
	$('#departmentName').val('').removeAttr('disabled');
	$('#departmentNo').val('').removeAttr('disabled');
	$('#departmentLevel').val('');
	$('#departmentDescribe').val('').removeAttr('disabled');
	$('#remarks').val('').removeAttr('disabled');
	$('#post_name').val('');
	$('#post_ids').val('');
	
	$('#chosePostbtn').removeAttr('disabled');
	$('#buttons button').attr('disabled','true');
	$('#saveAndCancelDiv').show();
};

var edit = function(){
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门节点!",
            type: "error"
	    });
		return false;
	}
	departmentNodeOnclick(null,'', selectNodes[0]);
	$('#departmentName').removeAttr('disabled');
	$('#departmentNo').removeAttr('disabled');
	$('#departmentDescribe').removeAttr('disabled');
	$('#remarks').removeAttr('disabled');
	$('#chosePostbtn').removeAttr('disabled');
	$('#buttons button').attr('disabled','true');
	$('#saveAndCancelDivForEdit').show();
};

var saveForEdit = function(){
	var canSave = util.validate("departmentInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门节点!",
            type: "error"
	    });
		return false;
	}
	var id = selectNodes[0].id;
	var params = util.findFormData('#departmentInfoDiv :input');
	department.editDepartmentOptions.postData = {
		params : params,
		id	   : id
	};
	util.commAjax(department.editDepartmentOptions);
	loadingAjax();
};

var save = function(){
	var canSave = util.validate("departmentInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门节点!",
            type: "error"
	    });
		return false;
	}
	var pId = selectNodes[0].id;
	var params = util.findFormData('#departmentInfoDiv :input');
	department.addDepartmentOptions.postData = {
		params : params,
		pId	   : pId
	};
	util.commAjax(department.addDepartmentOptions);
	loadingAjax();
};

var clearInput = function(){
	$('#departmentName').val('').attr('disabled','true');
	$('#departmentNo').val('').attr('disabled','true');
	$('#departmentLevel').val('');
	$('#departmentDescribe').val('').attr('disabled','true');
	$('#remarks').val('').attr('disabled','true');
	$('#post_name').val('').attr('disabled','true');
	$('#chosePostbtn').attr('disabled','true');
	$('#post_ids').val('');
	$('#buttons button').removeAttr('disabled');
	$('#saveAndCancelDiv').hide();
	$('#saveAndCancelDivForEdit').hide();
};

var del = function(){
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门节点!",
            type: "error"
	    });
		return false;
	}
	if(selectNodes[0].level == 0){
		swal( {title: "错误", text: "根节点不能删除",type:'error'});
		return false;
	}
	var isParent = selectNodes[0].isParent;
	var id = selectNodes[0].id;
	if(isParent){
		swal({
	        title: "是否确认删除该部门节点?",
	        text: "您将要删除一个父级节点，系统将会自动删除其下全部子部门节点",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消",
	        closeOnConfirm: false,
	        closeOnCancel: false 
	        },
	        function (isConfirm) {
		        if (isConfirm) {
		        	loadingAjax();
		        	department.delDepartmentOptions.postData = {id:id};
	        		util.commAjax(department.delDepartmentOptions);
		        } else
		            swal( {title: "提示", text: "您已取消删除"});
	        });
	}else{
		swal({
	        title: "是否确认删除该部门节点?",
	        text: "",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消",
	        closeOnConfirm: false,
	        closeOnCancel: false 
	        },
	        function (isConfirm) {
		        if (isConfirm) {
		        	loadingAjax();
		        	department.delDepartmentOptions.postData = {id:id};
	        		util.commAjax(department.delDepartmentOptions);
		        } else
		        	swal( {title: "提示", text: "您已取消删除"});
	        });
	}
}