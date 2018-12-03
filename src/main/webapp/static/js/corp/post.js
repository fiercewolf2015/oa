var post = function(){};
post.gridId = "#table_post";
post.pagerId = "#pager_post";
post.searchDivInput = "#searchPostDiv :input";
post.searchBtn = "#searchBtn";
post.params = 'params';
$(function(){
	util.initSelectMenu('postLi');
	$(post.gridId).jqGrid(post.gridOption);
	util.commAjax(post.loadDepartmentTreeNodesOptions);
	$(window).resize(function(){
		$(post.gridId).setGridWidth(util.getGridWith());
	});
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deletePost('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var postIds = new Array();
post.gridOption = { 
		url: context+"/post/list",
		postData: {'param':{}},
		colNames : ['岗位名称','岗位代码','岗位描述','备注','部门','操作','部门ids'],
		colModel : [
		            {name : 'postName', width : 50, align : "center"},
		            {name : 'postNo', width : 50, align : "center"},
		            {name : 'postDescribe', width : 90, align : "center"},
		            {name : 'remarks', width : 90, align : "center"},
		            {name : 'deptNames', width : 90, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}},
		            {name : 'deptIds', width : 90, align : "center",hidden:true}
		],
		caption : "岗位",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: post.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(post.gridId, postIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(postIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(postIds, rowIds, status);
			else
				macIds = new Array();
		}
};

post.addPostOptions = 
{
		
		url		:	'/post/add'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addPostModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
	                title: "岗位名称已存在",
	                type: "error"
			     });
			}else if(data === 3){
				swal({
	                title: "岗位代码已存在",
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

post.editPostOptions = 
{
		
		url		:	'/post/edit'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editPostModal'); 
		        });
				searchPargerList();
			}else if(data === 2){
				swal({
	                title: "岗位名称已存在",
	                type: "error"
			     });
			}else if(data === 3){
				swal({
	                title: "岗位代码已存在",
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

post.delPostOptions = 
{
		
		url		:	'/post/delete'							
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
	util.searchListWithParams(post.gridId,post.searchDivInput,post.params);
};
var showAddModal = function(){
	$('#addPostName').val('');
	$('#addPostNo').val('');
	$('#addPostDescribe').val('');
	$('#addRemarks').val('');
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	treeObj.checkAllNodes(false);
	util.showModal('addPostModal');
};

var savePost = function(){
	var canSave = util.validate("addPostDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addPostDiv :input');
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getCheckedNodes(true);
	var selDeptIds = new Array();
	if(selectNodes.length > 0){
		for(var i=0;i<selectNodes.length;i++){
			selDeptIds[i] = selectNodes[i].id;
		}
	}
	post.addPostOptions.postData = {
		params : params,
		selDeptIds : selDeptIds.join(',')
	};
	util.commAjax(post.addPostOptions);
};

var showEditModal = function(postId){
	var rowData = $(post.gridId).jqGrid('getRowData', postId);
	var postName = rowData["postName"];
	var remarks = rowData["remarks"];
	var postNo = rowData["postNo"];
	var postDescribe = rowData["postDescribe"];
	var deptIds = rowData["deptIds"];
	$('#editPostName').val(postName);
	$('#editPostNo').val(postNo);
	$('#editRemarks').val(remarks);
	$('#editPostDescribe').val(postDescribe);
	$('#selPostId').val(postId);
	var treeObj = $.fn.zTree.getZTreeObj("departmentTreeForEdit");
	treeObj.checkAllNodes(false);
	var deptIdArr = deptIds.split(',');
	if(deptIdArr != null && deptIdArr.length > 0){
		for (var i=0 ;i< deptIdArr.length;i++){
			var node = treeObj.getNodeByParam("id", deptIdArr[i], null);
			if (node)
				treeObj.checkNode(node,true,false);
		}
	}
	util.showModal('editPostModal');
};

var saveEditPost = function(){
	var canSave = util.validate("editPostDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editPostDiv :input');
	var selectId = $('#selPostId').val();
	var treeObj = $.fn.zTree.getZTreeObj("departmentTreeForEdit");
	var selectNodes = treeObj.getCheckedNodes(true);
	var selDeptIds = new Array();
	if(selectNodes.length > 0){
		for(var i=0;i<selectNodes.length;i++){
			selDeptIds[i] = selectNodes[i].id;
		}
	}
	post.editPostOptions.postData = {
		params : params,
		postId : selectId,
		selDeptIds : selDeptIds.join(',')
	};
	util.commAjax(post.editPostOptions);
};

var deletePost = function(postId){
	var selPostIds =  postId;
	if(postId == undefined){
		var postIds = $(post.gridId).jqGrid("getGridParam", "selarrrow");
		if(postIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selPostIds = postIds.join(',');
	}
	post.delPostOptions.postData = {
			postIds : selPostIds
	};
	
	 swal({
         title: "确定删除选择的岗位?",
         type: "warning",
         showCancelButton: true,
         confirmButtonColor: "#DD6B55",
         confirmButtonText: "确定",
         cancelButtonText: "取消",
         closeOnConfirm: false,
         closeOnCancel: false },
     function (isConfirm) {
         if (isConfirm) {
        	 util.commAjax(post.delPostOptions);
         } else {
             swal("已取消删除", "", "error");
         }
      });
}

post.departmentTreeSetting = {
		check: {
			enable: true,
			chkStyle:'checkbox',
			chkboxType:{"Y":"","N":""}
		},
		data: {
			simpleData: {
				enable: true
			}
		},
};

post.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTree"), post.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTree').expandAll(true);
				$.fn.zTree.init($("#departmentTreeForEdit"), post.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTreeForEdit').expandAll(true);
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
