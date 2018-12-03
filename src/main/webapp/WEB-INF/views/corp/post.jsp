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
    <title>岗位管理</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchPostDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="postName">岗位名称</label>
                    <input type="text" id="postName" name="postName" placeholder="岗位名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="postNo">岗位代码</label>
                    <input type="text" id="postNo" name="postNo"placeholder="岗位代码" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="deptName">部门名称</label>
                    <input type="text" id="deptName" name="deptName"placeholder="部门名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-4">
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deletePost()"><i class="fa fa-bitbucket"></i>批量删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_post"></table>
                         <div id="pager_post"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="addPostModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增岗位</h4>
				</div>
				<div class="modal-body">
						<div  id="addPostDiv">
							<div class="form-group"><label>职务名称</label> <input id="addPostName" name="addPostName" type="text" placeholder="岗位名称" class="form-control" required></div>
							<div class="form-group"><label>岗位代码</label> <input id="addPostNo" name="addPostNo" type="text" placeholder="岗位代码" class="form-control" required></div>
							<div class="form-group"><label>岗位描述</label> 
								<textarea id="addPostDescribe" name ="addPostDescribe" placeholder="岗位描述" style="max-width:100%;" class="form-control"></textarea>
							</div>
							<div class="form-group"><label>备注</label> 
								<textarea id="addRemarks" name ="addRemarks" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
							</div>
						</div>
						 <div class="form-group">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                		选择部门
                            </div>
                            <div class="panel-body">
                               <div class="panel-body ztree" id="departmentTree" style="overflow: auto;"></div>
                            </div>
                        </div>
                    </div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="savePost()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="editPostModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">编辑岗位</h4>
				</div>
				<div class="modal-body">
						<div id="editPostDiv">
							<div class="form-group"><label>岗位名称</label> <input id="editPostName" name="editPostName" type="text" placeholder="岗位名称" class="form-control" required></div>
							<div class="form-group"><label>岗位代码</label> <input id="editPostNo" name="editPostNo" type="text" placeholder="岗位代码" class="form-control" required></div>
							<div class="form-group"><label>岗位描述</label> 
									<textarea id="editPostDescribe" name ="editPostDescribe" placeholder="岗位描述" style="max-width:100%;" class="form-control"></textarea>
								</div>
							<div class="form-group"><label>备注</label> 
								<textarea id="editRemarks" name ="editRemarks" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
							</div>
						</div>
						 <div class="form-group">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                		选择部门
                            </div>
                            <div class="panel-body">
                               <div class="panel-body ztree" id="departmentTreeForEdit" style="overflow: auto;"></div>
                            </div>
                        </div>
                    </div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveEditPost()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<input id="selPostId" name="selPostId" type="text" style="display:none;">
<script type="text/javascript" src="${ctx }/static/js/corp/post.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
</body>
</html>