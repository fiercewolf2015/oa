var user = function(){};
user.departmentTreeId = '#departmentTree';
user.gridId = "#table_user";
user.pagerId = "#pager_user";
user.searchDivInput = "#searchUserDiv :input";
user.params = 'params';
user.roleTreeId = '#roleTree';
user.staffgridId = "#table_staff";
user.staffpagerId = "#pager_staff";
user.staffsearchDivInput = "#searchStaffDiv :input";
user.staffparams = 'params';
user.roleTreeSetting = {
		check: {
			enable: true,
			chkStyle: "radio",
			chkboxType: { "Y": "s", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeCheck: roleBeforeCheck
		}
		
};
function roleBeforeCheck(treeId, treeNode){
	if(treeNode.id == 'parent')
		return false;
	return true;
};
user.loadRoleTreeNodesOptions = 
{
		url		:	'/role/loadRoleTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(user.roleTreeId), user.roleTreeSetting, data);
				$.fn.zTree.getZTreeObj('roleTree').expandAll(true);
			} else
				swal({
		            title: "操作失败：未加载数据!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "操作失败：未加载数据!",
	            type: "error"
		    });
		}		
};
user.editRoleTreeId = '#editRoleTree';
user.editRoleTreeSetting = {
		check: {
			enable: true,
			chkStyle: "radio",
			chkboxType: { "Y": "s", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeCheck: roleBeforeCheck
		}
		
};
user.loadeditRoleTreeNodesOptions = 
{
		url		:	'/role/loadRoleTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				util.showModal('editUserRoleModal');
				$.fn.zTree.init($(user.editRoleTreeId), user.editRoleTreeSetting, data);
				var treeObj = $.fn.zTree.getZTreeObj('editRoleTree');
				treeObj.expandAll(true);
				treeObj.checkAllNodes(false);
				var userId = $(user.gridId).jqGrid("getGridParam", "selarrrow");
				var rowData = $(user.gridId).jqGrid('getRowData', userId[0]);
					var node = treeObj.getNodeByParam("id", rowData['role.id'], null);
					if (node)
						treeObj.checkNode(node,true,false);
			} else
				swal({
		            title: "操作失败：未加载数据!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "操作失败：未加载数据!",
	            type: "error"
		    });
		}		
};
$(function(){
	var leftHeight = $('#leftPanel').height();
	var rightHeight = $('#rightPanel').height();
	if(leftHeight > rightHeight)
		$('#rightPanel').height(leftHeight);
	else
		$('#leftPanel').height(rightHeight);
	util.initSelectMenu('userLi');
	$(user.gridId).jqGrid(user.gridOption);
	$(user.staffgridId).jqGrid(user.staffgridOption);
	$(window).resize(function(){
		$(user.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(user.loadRoleTreeNodesOptions);
});

var userIds = new Array();
user.gridOption = { 
		url: context+"/user/list",
		postData: {'param':{}},
		colNames : ['id','登录名','姓名','密码', '角色',''],
		colModel : [
	            	{name : 'id',hidden:true},
		            {name : 'loginName', width : 100, align : "center"},
		            {name : 'staff.staffName', width : 100, align : "center"},
		            {name : 'plainPassword', width : 100, align : "center"},
		            {name : 'role.name', width : 150, align : "center",sortable:false},
		            {name : 'role.id',hidden:true}
		],
		caption : "用户",
		mtype:"POST",
	    datatype: "json",
	    emptyrecords:"无符合条件数据",
	    rowNum:25, 
	    height:'100%',
	    pager: user.pagerId,  
	    autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(user.gridId, userIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(userIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(userIds, rowIds, status);
			else
				macIds = new Array();
		}
};

user.staffgridOption = { 
		url: context+"/staff/list",
		postData: {'param':{}},
		colNames : ['姓名','员工编号','部门','职务','职称职级','岗位'],
		colModel : [
		            {name : 'staffName', width : 100, align : "center"},
		            {name : 'staffNo', width : 100, align : "center"},
		            {name : 'department.departmentName', width : 100, align : "center"},
		            {name : 'occupation.occupationName', width : 100, align : "center"},
		            {name : 'title.titleName', width : 100, align : "center"},
		            {name : 'postNames', width : 100, align : "center",sortable:false}
		],
		caption : "员工",
		mtype:"POST",
	    datatype: "json",
	    emptyrecords:"无符合条件数据",
	    rowNum:50000, 
	    height:280,
	    pager: user.staffpagerId,  
	    autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		shrinkToFit:false,
		autoScroll: false  
};
var searchPargerList = function(){
	util.searchListWithParams(user.gridId,user.searchDivInput,user.params);
};
var staffsearchPargerList = function(){
	util.searchListWithParams(user.staffgridId,user.staffsearchDivInput,user.staffparams);
};
user.registerUserOptions = {
		url		:	'/user/register'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) { 
				swal({
		                title: "创建成功!",
		                type: "success"
			        },function(){
			        	util.hideModal('registerUserModal');
			        	searchPargerList();
			        });
			}else if(data === 2){
				swal({
	                title: "创建失败!",
	                text:"登录名已存在",
	                type: "error"
		        });
			}else
				swal({
	                title: "创建失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "创建失败!",
	            type: "error"
		    });
		}		
};
user.registerUser = function(){
	$('#searchStaffNo').val('');
	$('#searchStaffName').val('');
	staffsearchPargerList();
	util.showModal('registerUserModal');
}
user.saveUser = function(){
	var staffIds = $(user.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIds.length < 1){
		swal({
			title: "至少选择一个员工"
		});
		return;
	}
	var selStaffIds = staffIds.join(',');
	var roleTreeObj = $.fn.zTree.getZTreeObj("roleTree");
	var role_nodes = roleTreeObj.getCheckedNodes(true);
	if(role_nodes == '' || role_nodes == null || role_nodes.length == 0) {
		swal({
            title: "请选择一个角色!",
            type: "error"
	    });
		return false;
	}
	user.registerUserOptions.postData = {
			'staffIds':selStaffIds,
			'roleId':role_nodes[0].id
	};
	loadingAjax();
	util.commAjax(user.registerUserOptions);
}
var clearInput = function(){
	$('#editplainPassword').val('');
	$('#editconfirmPassword').val('');
};
user.delUserOptions = {
		url		:	'/user/delete'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) { 
				swal({
		                title: "删除成功!",
		                type: "success"
			        },function(){
			        	searchPargerList();
			        });
			}else if(data === 2){
				swal({
	                title: "删除失败!",
	                text:"不能删除自己",
	                type: "error"
		        });
			}else
				swal({
	                title: "删除失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "删除失败!",
	            type: "error"
		    });
		}		
};
var deleteUser = function() {
	var selectedId = $(user.gridId).jqGrid("getGridParam", "selarrrow");
	if (selectedId == '' || selectedId == null || selectedId == undefined || selectedId.length < 1) {
		swal({
            title: "请选择至少一个用户!",
            type: "error"
	    });
		return false;
	}
	var userIds = selectedId.join(",");
	swal({
        title: "是否确认删除用户?",
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
	        	user.delUserOptions.postData = {'userIds':userIds};
        		util.commAjax(user.delUserOptions);
	        } else
	            swal( {title: "提示", text: "您已取消删除"});
        });
};
user.editUserRoleModal = function(){
	var userId = $(user.gridId).jqGrid("getGridParam", "selarrrow");
	if(userId == null || userId == "" || userId.length == 0 || userId.length > 1 ){
		swal({
			title: "请选择一条记录"
		});
		return false;
	}
	util.commAjax(user.loadeditRoleTreeNodesOptions);
};
user.editUserRoleOptions = {
		url		:	'/user/editUserRole'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) { 
				swal({
		                title: "修改成功!",
		                type: "success"
			        },function(){
			        	util.hideModal('editUserRoleModal');
			        	searchPargerList();
			        });
			}else
				swal({
	                title: "修改失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "修改失败!",
	            type: "error"
		    });
		}		
};
user.editUserRoleSave = function(){
	loadingAjax();
	var roleTreeObj = $.fn.zTree.getZTreeObj("editRoleTree");
	var role_nodes = roleTreeObj.getCheckedNodes(true);
	if(role_nodes == '' || role_nodes == null || role_nodes.length == 0) {
		swal({
            title: "请选择一个角色!",
            type: "error"
	    });
		return false;
	}
	var userId = $(user.gridId).jqGrid("getGridParam", "selarrrow");
	user.editUserRoleOptions.postData = {
			'userId':userId[0],
			'roleId':role_nodes[0].id
	};
	util.commAjax(user.editUserRoleOptions);
};
user.editPassword = function(){
	var userId = $(user.gridId).jqGrid("getGridParam", "selarrrow");
	if(userId == null || userId == "" || userId.length == 0 ){
		swal({
			title: "请至少选择一条记录"
		});
		return false;
	}
	clearInput();
	util.showModal('editUserPasswordModal');
};

user.editUserPasswordOptions = {
		url		:	'/user/editUserPassword'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) { 
				swal({
		                title: "修改成功!",
		                type: "success"
			        },function(){
			        	util.hideModal('editUserPasswordModal');
			        	searchPargerList();
			        });
			}else
				swal({
	                title: "修改失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "修改失败!",
	            type: "error"
		    });
		}		
};
user.editUserPasswordSave = function(){
	var canSave = util.validate("editpasswordDiv");
	if(canSave == false)
		return false;
	var userIdArr = $(user.gridId).jqGrid("getGridParam", "selarrrow");
	var userIds = userIdArr.join(',');
	
	var plainPassword = $('#editplainPassword').val();
	var confirmPassword = $('#editconfirmPassword').val();
	if(plainPassword != confirmPassword){
		swal({title: "两次输入的密码不一致，请检查",type: "error"});
		return false;
	}
	user.editUserPasswordOptions.postData = {
			'userIds':userIds,
			'plainPassword':plainPassword
	};
	loadingAjax();
	util.commAjax(user.editUserPasswordOptions);
};

