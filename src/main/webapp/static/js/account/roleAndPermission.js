var roleAndPermission = function(){};
$(function(){
	var leftHeight = $('#leftPanel').height();
	var rightHeight = $('#rightPanel').height();
	if(leftHeight > rightHeight)
		$('#rightPanel').height(leftHeight);
	else
		$('#leftPanel').height(rightHeight);
	util.initSelectMenu('roleLi');
	util.commAjax(roleAndPermission.loadRoleTreeNodesOptions);
	util.commAjax(roleAndPermission.loadPermissionTreeNodesOptions);
});
roleAndPermission.addPermissionToRole = function(){
	var roleTreeObj = $.fn.zTree.getZTreeObj("roleTree");
	var role_nodes = roleTreeObj.getCheckedNodes(true);
	if(role_nodes == '' || role_nodes == null || role_nodes.length == 0) {
		swal({
            title: "请选择一个角色!",
            type: "error"
	    });
		return false;
	}
	var permissionTreeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var permission_nodes = permissionTreeObj.getCheckedNodes(true);
	if(permission_nodes == ''|| permission_nodes.length == 0) {
		swal({
	        title: "是否确认给该角色关闭所有资源?",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确认",
	        cancelButtonText: "取消",
	        closeOnConfirm: false,
	        closeOnCancel: false 
	        },
	        function (isConfirm) {
		        if (isConfirm) {
		        	loadingAjax();
		        	var roleId = role_nodes[0].id;
		        	var permissionIds = new Array();
		        	for (var i = 0; i < permission_nodes.length; i++)
		        		permissionIds.push(permission_nodes[i].id);
		        	roleAndPermission.addPermissionToRoleOptions.postData={
		        			'roleId'	:	roleId,
		        			'permissionIds'	:	permissionIds.join(',')
		        	};
		        	util.commAjax(roleAndPermission.addPermissionToRoleOptions);
		        } else
		            swal( {title: "提示", text: "您已取消操作"});
	        });
	}else{
		loadingAjax();
    	var roleId = role_nodes[0].id;
    	var permissionIds = new Array();
    	for (var i = 0; i < permission_nodes.length; i++)
    		permissionIds.push(permission_nodes[i].id);
    	roleAndPermission.addPermissionToRoleOptions.postData={
    			'roleId'	:	roleId,
    			'permissionIds'	:	permissionIds.join(',')
    	};
    	util.commAjax(roleAndPermission.addPermissionToRoleOptions);
	}
}

roleAndPermission.addPermission = function(){
	var canSave = util.validate("permissionAddDiv");
	if(canSave == false)
		return false;
	var permissionName = $('#permissionName').val();
	var codeName = $('#permissionCodeName').val();
	roleAndPermission.addPermissionOptions.postData={
			'name'		:	permissionName,
			'codeName'	:	codeName
	};
	loadingAjax();
	util.commAjax(roleAndPermission.addPermissionOptions);
};
roleAndPermission.addRoleOptions = {
		url		:	'/role/addRole'
	,	mtype	:	'POST'
	,	postData	:	{}	
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data == 1) {
				swal({
		            title: "操作成功!",
		            type: "success"
			    },function(){
			    	util.commAjax(roleAndPermission.loadRoleTreeNodesOptions);
			    	util.hideModal('addRoleModal');
			    	util.hideModal('editRoleModal');
			    });
			} else if (data == 2) {
				swal({
		            title: "名称已存在，请重新检查!",
		            type: "error"
			    });
			}else
				swal({
		            title: "操作失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
};
roleAndPermission.addRole = function(){
	var canSave = util.validate("roleAddDiv");
	if(canSave == false)
		return false;
	var roleName = $('#roleName').val();
	var roleDescription = $('#roleDescription').val();
	roleAndPermission.addRoleOptions.postData={
			'roleId':null,
			'roleName'		:	roleName,
			'roleDescription'	:	roleDescription
	};
	loadingAjax();
	util.commAjax(roleAndPermission.addRoleOptions);
};
function roleOnCheck(event, treeId, treeNode) {
	if(treeNode.checked){
		roleAndPermission.loadPermissionByRoleIdOptions.postData={
				'id':treeNode.id
		};
		util.commAjax(roleAndPermission.loadPermissionByRoleIdOptions);
	}else {
		var treeObj = $.fn.zTree.getZTreeObj('permissionTree');
		treeObj.checkAllNodes(false);
	}
};
function roleBeforeCheck(treeId, treeNode){
	if(treeNode.id == 'parent')
		return false;
	return true;
};

function roleBeforeRemove(treeId, treeNode){
	if(treeNode.id == 'parent')
		return false;
	var roleId = treeNode.id;
	if(roleId == null ||roleId==undefined|| roleId <=0)
		return false;
	roleAndPermission.deleteRoleOptions.postData = {
			'id':roleId
	};
	loadingAjax();
	util.commAjax(roleAndPermission.deleteRoleOptions);
	return false;
};

roleAndPermission.deleteRoleOptions = {
		url		:	'/role/deleteRole'
	,	mtype	:	'POST'
	,	postData	:	{}	
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data == 1) {
				swal({
		            title: "删除成功!",
		            type: "success"
			    },function(){
			    	util.commAjax(roleAndPermission.loadRoleTreeNodesOptions);
			    });
			} else if (data == 2) {
				swal({
		            title: "已有用户使用该角色，请先解除绑定!",
		            type: "error"
			    });
			}else
				swal({
		            title: "操作失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
};

roleAndPermission.roleTreeId = '#roleTree';
roleAndPermission.permissionTreeId = '#permissionTree';

roleAndPermission.permissionTreeSetting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "s", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};
roleAndPermission.roleTreeSetting = {
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
		edit: {
			enable: true,
			showRemoveBtn: true,
			showRenameBtn: false
		},
		callback: {
			onCheck: roleOnCheck,
			beforeCheck: roleBeforeCheck,
			beforeRemove: roleBeforeRemove
		}
		
};
roleAndPermission.loadRoleTreeNodesOptions = 
{
		url		:	'/role/loadRoleTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(roleAndPermission.roleTreeId), roleAndPermission.roleTreeSetting, data);
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
roleAndPermission.loadPermissionTreeNodesOptions = 
{
		url		:	'/permission/loadPermissionTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(roleAndPermission.permissionTreeId), roleAndPermission.permissionTreeSetting, data);
				$.fn.zTree.getZTreeObj('permissionTree').expandAll(true);
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
roleAndPermission.loadPermissionByRoleIdOptions = 
{
		url		:	'/permission/searchPermission'
	,	mtype	:	'POST'
	,	postData	:	{}	
	,	success :	function(data,st, xhr){
			if (data) { 
				var treeObj = $.fn.zTree.getZTreeObj('permissionTree');
				treeObj.expandAll(true);
				treeObj.checkAllNodes(false);
				for (var i=0 ;i< data.length;i++){
					var node = treeObj.getNodeByParam("id", data[i], null);
					if (node)
						treeObj.checkNode(node,true,false);
				}
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
roleAndPermission.addPermissionOptions = 
{
		url		:	'/permission/addPermission'
	,	mtype	:	'POST'
	,	postData	:	{}	
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data == 1) {
				swal({
		            title: "操作成功!",
		            type: "success"
			    },function(){
			    	util.commAjax(roleAndPermission.loadPermissionTreeNodesOptions);
			    	util.hideModal('addPermissionModal');
			    });
			} else if (data == 2) {
				swal({
		            title: "名称已存在，请重新检查!",
		            type: "error"
			    });
			}else
				swal({
		            title: "操作失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
};
roleAndPermission.addPermissionToRoleOptions = 
{
		url		:	'/role/addPermissionToRole'
	,	mtype	:	'POST'
	,	postData	:	{}	
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data == 1) {
				swal({
		            title: "分配成功!",
		            type: "success"
			    },function(){
			    	util.commAjax(roleAndPermission.loadPermissionTreeNodesOptions);
					util.commAjax(roleAndPermission.loadRoleTreeNodesOptions);
			    });
				
			}else
				swal({
		            title: "操作失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			hideAjax();
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
};

var showAddRoleModal = function(){
	$('#addRoleModal :input').val('');
	util.showModal('addRoleModal');
}
var showEditRoleModal = function(){
	var roleTreeObj = $.fn.zTree.getZTreeObj("roleTree");
	var role_nodes = roleTreeObj.getCheckedNodes(true);
	if(role_nodes == '' || role_nodes == null || role_nodes.length == 0) {
		swal({
            title: "请选择一个角色!",
            type: "error"
	    });
		return false;
	}
	$('#editRoleName').val(role_nodes[0].name);
	$('#editRoleDescription').val(role_nodes[0].description);
	util.showModal('editRoleModal');
}
roleAndPermission.editRole = function(){
	var canSave = util.validate("roleEditDiv");
	if(canSave == false)
		return false;
	var roleTreeObj = $.fn.zTree.getZTreeObj("roleTree");
	var role_nodes = roleTreeObj.getCheckedNodes(true);
	var roleName = $('#editRoleName').val();
	var roleDescription = $('#editRoleDescription').val();
	roleAndPermission.addRoleOptions.postData={
			'roleId'  : role_nodes[0].id,
			'roleName'		:	roleName,
			'roleDescription'	:	roleDescription
	};
	loadingAjax();
	util.commAjax(roleAndPermission.addRoleOptions);
};
var showAddPermissionModal = function(){
	$('#addPermissionModal :input').val('');
	util.showModal('addPermissionModal');
}