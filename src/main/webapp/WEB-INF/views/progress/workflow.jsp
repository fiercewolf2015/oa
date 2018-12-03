<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>自定义流程</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchWorkflowDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="workflowName">流程名称</label>
                    <input type="text" id="workflowName" name="workflowName" placeholder="流程名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="workflowType">流程类型</label>
                     <select id="workflowType" name="workflowType" class="form-control"></select>
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-6">
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deleteWorkflow()"><i class="fa fa-bitbucket"></i>删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
                  <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showEditStartModal();"><i class="fa fa-paste"></i>编辑申请人</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_workflow"></table>
                         <div id="pager_workflow"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="addWorkflowModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:600px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增流程</h4>
				</div>
				<input type="text" id="selWorkflowId" style="display:none;">
				<div class="modal-body" style="padding:10">
				     <div class="panel panel-default">
                     <div class="panel-heading"  >流程信息</div>
                     <div class="panel-body">
	                      <div class="col-sm-6">
				                <div class="form-group">
				                    <label class="control-label" for="addWorkflowName">流程名称</label>
				                    <input type="text" id="addWorkflowName" name="addWorkflowName" placeholder="流程名称" class="form-control">
				                </div>
				            </div>
				            <div class="col-sm-6">
				                <div class="form-group">
				                    <label class="control-label" for="addWorkflowType">流程类型</label>
				                    <select id="addWorkflowType" name="addWorkflowType" class="form-control"></select>
				                </div>
				            </div>
                 	 </div>
              	</div>
           	    <div class="panel panel-default">
                     <div class="panel-heading"  >节点信息
                     		 <button type="button" class="btn btn-sm btn-primary" style="float:right;margin-top:-5px;" onclick="addNode();"><i class="fa fa-plus"></i>增加节点</button>
                     </div>
                     <div class="panel-body">
	                         <div class="ibox-content">
			                        <ol id="allNodeOl">
			                        </ol>
			                   </div>
                 	 </div>
              	</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveWorkflow()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal inmodal" id="addNodeModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 id="addNodeTitle" class="modal-title">新增审批节点</h4>
				</div>
				<input type="text" id="addOrEdit" style="display:none;">
				<div class="modal-body" style="padding:10;height:550px;overflow:auto;">
					 <div id="nodeInfo" class="col-lg-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-title">
		                        <h5>审批节点信息</h5>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height:100px;">
		                       <div class="col-sm-6">
					                <div class="form-group">
					                    <label class="control-label" for="addNodeName">审批节点名称</label>
					                    <input type="text" id="addNodeName" name="addNodeName" placeholder="审批节点名称" class="form-control">
					                </div>
					            	　</div>
		                    </div>
		                </div>
	            </div>
	            <div class="col-lg-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-title">
		                        <h5>选择职务</h5>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height:170px;overflow:auto;">
		                       <div class="panel-body ztree" id="occupationTree" style="overflow: auto;"></div>
		                    </div>
		                </div>
	            </div>
	            <div class="col-lg-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-title">
		                        <h5>选择岗位</h5>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
                          <div class="ibox-content" style="height:410px;">
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
								                    <input type="text" id="postNo" name="postNo" placeholder="岗位代码" class="form-control">
								                </div>
								            </div>
								            <div class="col-sm-2">
								                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="postsearchPargerList()"><i class="fa fa-search"></i>搜索</button>
								            </div>
								            <div class="col-sm-2">
								                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="addPost()"><i class="fa fa-search"></i>增加岗位</button>
								            </div>
								        </div>
								       <div class="row" style="width:635px;float:left;">
								            <div class="ibox">
								                <div class="ibox-content">
													<div class="jqGrid_wrapper">
								                         <table id="table_post"></table>
								                         <div id="pager_post"></div>
							                     	</div>
								                </div>
								            </div>
						        		 </div>
						        		 <div id="selPostDiv" style="width:200px;float:left;height:290px;margin-top:15px;margin-left:20px;overflow:auto;">
						        		    <span style="float:left;width:100%;">已选择的岗位(双击删除):</span>
						        		 </div>
		                    </div>
		                </div>
	            </div>
	            <div class="col-lg-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-title">
		                        <h5>选择人员</h5>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height:420px;">
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
								             <div class="col-sm-2">
								                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="addStaff()"><i class="fa fa-search"></i>增加员工</button>
								            </div>
								        </div>
								       <div class="row" style="width:635px;float:left;">
								            <div class="ibox">
								                <div class="ibox-content">
													<div class="jqGrid_wrapper">
								                         <table id="table_staff"></table>
								                         <div id="pager_staff"></div>
							                     	</div>
								                </div>
								            </div>
						        		 </div>
						        		  <div id="selStaffDiv" style="width:200px;float:left;height:290px;margin-top:15px;margin-left:20px;overflow:auto;">
						        		    <span style="float:left;width:100%;">已选择的员工(双击删除):</span>
						        		 </div>
		                    </div>
		                </div>
	            </div>
			   </div>
				<div class="modal-footer">
				    <button id="saveNodeBtn" type="button" class="btn btn-primary btn-sm" onclick="saveNode()">保存</button>
				    <button id="saveStartPersonBtn" type="button" class="btn btn-primary btn-sm" onclick="saveStartPerson()">保存</button>
					 <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
<input type="text" id="selWorkflowIdForStartPer" name="selWorkflowIdForStartPer" style="display:none;">	
<script type="text/javascript" src="${ctx }/static/js/progress/workflow.js"></script>
</body>
</html>