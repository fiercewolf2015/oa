<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx }/static/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${ctx }/static/ztree/css/zTreeStyle.css" type="text/css">
	<title>权限管理</title>
	<style>
	    .btnWithoutLabel{
	    	margin-top:0px;
	      }
    </style>
</head>
<body>
  <div class="row" style="padding:45px 20px 20px 20px">
      <div class="col-lg-6">
          <div class="panel panel-default">
              <div class="panel-heading" style="height:50px;">
                  			角色管理
            			 <button type="button" class="btn btn-primary btn-sm btnWithoutLabel" onclick="showAddRoleModal();">新建角色</button>
            			 <button type="button" class="btn btn-primary btn-sm btnWithoutLabel" onclick="showEditRoleModal();">编辑角色</button>
            			 <button type="button" class="btn btn-primary btn-sm btnWithoutLabel" onclick="roleAndPermission.addPermissionToRole();">分配资源</button>
              </div>
              <div class="panel-body" id="leftPanel">
              		<div class="panel-body ztree" id="roleTree" style="height:500px;overflow: auto;"></div>
              </div>
          </div>
      </div>
      <div class="col-lg-6">
          <div class="panel panel-default">
              <div class="panel-heading" id="editDepartmentPanel" style="height:50px;">
             				 资源管理
           				 <shiro:hasPermission name="newPermission">
      				 		<button type="button" class="btn btn-primary btn-sm btnWithoutLabel"  onclick="showAddPermissionModal();">新建资源</button>
      				 	</shiro:hasPermission>
              </div>
              <div class="panel-body"  id="rightPanel">
              		<div class="panel-body ztree" id="permissionTree" style="height: 500px;overflow: auto;"></div>
              </div>
          </div>
      </div>
  </div>
  
  <div class="modal inmodal" id="addRoleModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增角色</h4>
				</div>
				<div class="modal-body" id="roleAddDiv">
						<div class="form-group"><label>角色名称</label> <input id="roleName" name="roleName" type="text" placeholder="角色名称" class="form-control" required></div>
						<div class="form-group"><label>角色描述</label> <input id="roleDescription" name="roleDescription" type="text" placeholder="角色描述" class="form-control" required></div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="roleAndPermission.addRole();">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="editRoleModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增角色</h4>
				</div>
				<div class="modal-body" id="roleEditDiv">
						<div class="form-group"><label>角色名称</label> <input id="editRoleName" name="editRoleName" type="text" placeholder="角色名称" class="form-control" required></div>
						<div class="form-group"><label>角色描述</label> <input id="editRoleDescription" name="editRoleDescription" type="text" placeholder="角色描述" class="form-control" required></div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="roleAndPermission.editRole();">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="addPermissionModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增资源</h4>
				</div>
				<div class="modal-body" id="permissionAddDiv">
						<div class="form-group"><label>资源名称</label> <input id="permissionName" name="permissionName" type="text" placeholder="资源名称" class="form-control" required></div>
						<div class="form-group"><label>代码名称</label> <input id="permissionCodeName" name="permissionCodeName" type="text" placeholder="代码名称" class="form-control" required></div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="roleAndPermission.addPermission();">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/js/account/roleAndPermission.js"></script>
</body>
</html>