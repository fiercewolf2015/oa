<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx }/static/ztree/css/demo.css" type="text/css">
	 <link rel="stylesheet" href="${ctx }/static/ztree/css/zTreeStyle.css" type="text/css">
    <title>部门管理</title>
    <style>
	    .btnWithoutLabel{
	    	margin-top:0px;
	      }
    </style>
</head>
<body>
     <div class="row" style="padding:45px 20px 20px 20px">
         <div class="col-lg-12">
              <div class="row">
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" style="height:50px;">
                              			已有部门
                        			 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="del();"><i class="fa fa-bitbucket"></i>删除</button>
					                 <button class="btn btn-sm btn-primary btnWithoutLabel" type="button" onclick="edit();"><i class="fa fa-paste"></i>编辑</button>
					                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="add();"><i class="fa fa-plus"></i>新增下级部门</button>
                          </div>
                          <div class="panel-body" id="leftPanel" style="overflow-y: auto;">
                          		<div class="panel-body ztree" id="departmentTree" style="overflow: auto;"></div>
                          </div>
                      </div>
                  </div>
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" id="editDepartmentPanel" style="height:50px;">
                          </div>
                          <div class="panel-body"  id="rightPanel">
                             <div id="departmentInfoDiv" class="col-xs-12" style="float:left;margin-top:10px;">
											<div class="form-group">
												<label>部门名称</label>
												<input id="departmentName" style="width:50%;" name="departmentName" type="text" placeholder="部门名称" class="form-control" disabled='true' required>
											</div>
											<div class="form-group">
												<label>部门代码</label>
												<input id="departmentNo" style="width:50%;" name="departmentNo" type="text" placeholder="部门代码" class="form-control" disabled='true' required>
											</div>
											<div class="form-group">
												<label>部门层级</label>
												<input id="departmentLevel" style="width:50%;" name="departmentLevel" type="text" placeholder="部门层级" class="form-control" disabled='true'>
											</div>
											<div class="form-group">
												<label>部门描述</label>
												<input id="departmentDescribe" style="width:50%;" name="departmentDescribe" type="text" placeholder="部门描述" class="form-control" disabled='true'>
											</div>
											<div class="form-group">
												<label style="width:100%;float:left;">岗位名称</label>
												<div style="width:100%;float:left;height: 52px;">
													<input id="post_name" style="width:50%;float:left;" name="post_name" type="text" placeholder="岗位名称" class="form-control" disabled='true'>
													<button type="button" id="chosePostbtn" style="float:left;margin-left:20px;" class="btn btn-primary btn-sm" onclick="showPostModal();" disabled='true'>选择岗位</button>
													<input id="post_ids" name="post_ids" type="text" disabled='true' hidden="true">
												</div>
											</div>
											<div class="form-group">
												<label>备注</label> 
												<textarea id="remarks" name ="remarks" placeholder="备注" style="max-width:100%;" class="form-control" disabled='true'></textarea>
											</div>
								</div>
										<div class="col-xs-12" style="float:left;">
											<div class="form-group" id="saveAndCancelDiv" style="float:right;" hidden="true">
								        	 	<button type="button" class="btn btn-primary btn-sm" onclick="save();">保存</button>
												<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">取消</button>
							            	</div>
							            	<div class="form-group" id="saveAndCancelDivForEdit" style="float:right;" hidden="true">
								        	 	<button type="button" class="btn btn-primary btn-sm" onclick="saveForEdit();">保存</button>
												<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">取消</button>
							            	</div>
										</div>
                          </div>
                      </div>
                  </div>
              </div>
         </div>
     </div>
    <div class="modal inmodal" id="chosePostModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">选择岗位</h4>
				</div>
				<div class="modal-body">
					<div class="row" id="searchPostDiv">
			            <div class="col-sm-4">
			                <div class="form-group">
			                    <label class="control-label" for="postName">岗位名称</label>
			                    <input type="text" id="postName" name="postName" placeholder="岗位名称" class="form-control">
			                </div>
			            </div>
			            <div class="col-sm-4">
			                <div class="form-group">
			                    <label class="control-label" for="postNo">岗位代码</label>
			                    <input type="text" id="postNo" name="postNo"placeholder="岗位代码" class="form-control">
			                </div>
	            		</div>
			            <div class="col-sm-2">
			                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPostList()"><i class="fa fa-search"></i>搜索</button>
			            </div>
	        		</div>
					<table id="table_post"></table>
                    <div id="pager_post"></div>
               	</div>
               	<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveChosePost();">确定</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
	   		</div>
			</div>
		</div>
	
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/js/corp/department.js"></script>
</body>
</html>