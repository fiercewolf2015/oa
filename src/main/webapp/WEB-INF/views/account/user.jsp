<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx }/static/ztree/css/demo.css" type="text/css">
	 <link rel="stylesheet" href="${ctx }/static/ztree/css/zTreeStyle.css" type="text/css">
	<title>用户管理</title>
</head>
<body>
<div class="ibox-content m-b-sm border-bottom">
   <div class="row" id="searchUserDiv">
       <div class="col-sm-2">
           <div class="form-group">
               <label class="control-label" for="searchLoginName">登录名</label>
               <input type="text" id="searchLoginName" name="searchLoginName" placeholder="登录名" class="form-control">
           </div>
       </div>
       <div class="col-sm-2">
           <div class="form-group">
               <label class="control-label" for="searchName">姓名</label>
               <input type="text" id="searchName" name="searchName" placeholder="姓名" class="form-control">
           </div>
       </div>
       <div class="col-sm-2">
           <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
       </div>
       <div class="col-sm-6">
            <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deleteUser();"><i class="fa fa-bitbucket"></i>删除</button>
            <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" onclick="user.editUserRoleModal();"><i class="fa fa-paste"></i>修改角色</button>
            <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" onclick="user.editPassword();"><i class="fa fa-paste"></i>修改密码</button>
            <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="user.registerUser();"><i class="fa fa-plus"></i>新增</button>
       </div>
   </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-content">
				<div class="jqGrid_wrapper">
	                 <table id="table_user"></table>
	                 <div id="pager_user"></div>
	            </div>
            </div>
        </div>
    </div>
</div>

<div class="modal inmodal" id="registerUserModal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog" style="width:740px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">创建用户</h4>
			</div>
			<div class="modal-body">
				<div class="ibox-content m-b-sm border-bottom">
			        <div class="row" id="searchStaffDiv">
			            <div class="col-sm-4">
			                <div class="form-group">
			                    <label class="control-label" for="searchStaffName">员工姓名</label>
			                    <input type="text" id="searchStaffName" name="searchStaffName" placeholder="员工姓名" class="form-control">
			                </div>
			            </div>
			            <div class="col-sm-4">
			                <div class="form-group">
			                    <label class="control-label" for="searchStaffNo">员工编号</label>
			                    <input type="text" id="searchStaffNo" name="searchStaffNo" placeholder="员工编号" class="form-control">
			                </div>
			            </div>
			            <div class="col-sm-2">
			                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="staffsearchPargerList()"><i class="fa fa-search"></i>搜索</button>
			            </div>
			        </div>
			    </div>
				<div class="row" id="registerUserDiv">
			        <div class="col-sm-12">
			            <div class="ibox">
			                <div class="ibox-content">
								<div class="jqGrid_wrapper">
			                         <table id="table_staff"></table>
			                         <div id="pager_staff"></div>
		                     	</div>
			                </div>
			            </div>
			        </div>
            		<div class="col-sm-12">
		                <div class="form-group col-sm-6">
		                   	<label for="roleTree" class="control-label">选择角色:</label>
		                   	<div class="panel-body ztree" id="roleTree" style="height: 300px;overflow: auto;"></div>
		                </div>
            		</div>
            		
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="user.saveUser();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="editUserRoleModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">修改角色</h4>
			</div>
			<div class="modal-body">
				<div class="row">
            		<div class="col-sm-12">
		                <div class="form-group col-sm-6">
		                   	<label for="editRoleTree" class="control-label">选择角色:</label>
		                   	<div class="panel-body ztree" id="editRoleTree" style="height: 300px;overflow: auto;"></div>
		                </div>
            		</div>
            		
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="user.editUserRoleSave();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="editUserPasswordModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">修改密码</h4>
			</div>
			<div class="modal-body">
				<div class="row" id="editpasswordDiv">
            		<div class="col-sm-12">
		                <div class="form-group col-sm-6">
		                    <label for="editplainPassword" class="control-label">密码:</label>
		                    <input type="password" id="editplainPassword" name="editplainPassword" placeholder="密码" class="form-control" required>
		                </div>
            		</div>
            		<div class="col-sm-12">
		                <div class="form-group col-sm-6">
		                   	<label for="editconfirmPassword" class="control-label">确认密码:</label>
		                   	<input type="password" id="editconfirmPassword" name="editconfirmPassword" placeholder="确认密码" class="form-control" required>
		                </div>
            		</div>
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="user.editUserPasswordSave();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
			</div>
   		</div>
	</div>
</div>
	<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/account/user.js"></script>
</body>
</html>
