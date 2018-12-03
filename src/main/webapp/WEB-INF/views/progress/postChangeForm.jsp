<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx }/static/css/plugins/chosen/chosen.css" rel="stylesheet">
    <title>岗位异动审批单</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">岗位异动审批单<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:100px;">
									<div class="form-group formDiv">
										<label class="formLabel">表单编号:</label>
										<span id="formId" name="formId" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">申请人:</label>
										<span id="applyStaff" name="applyStaff" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">所属部门:</label>
										<span id="deptName" name="deptName" class="formSpan transform"></span>
									</div>
							　</div>
							　<div class="oneRowDiv" style="height:100px;">
									<div class="form-group formDiv">
										<label class="formLabel">申请日期:</label>
										<span id="applyDate" name="applyDate" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">工号:</label>
										<span id="staffNo" name="staffNo" class="formSpan transform"></span>
									</div>
							　</div>
							<div class="twoRowDiv" style="height:40px;">
                   				<button type="button" class="btn btn-primary btn-sm" style="float:right;" onclick="showSelectModal();">添加记录</button>
						　	</div>
						 	 <div class="twoRowDiv" style="height:150px;">
	                          	<div class="form-group formDiv" style="height:100%;">
										<label class="labelForInput">岗位变动明细:</label>
										<div id="postChangeInfo" style="border: 1px solid black;float: left;width: 87%;height:100%;overflow-y:auto;">
												<label style="float:left;width:10%;text-align: center;">姓名</label>
												<label style="float:left;width:10%;text-align: center;">工号</label>
												<label style="float:left;width:15%;text-align: center;">异动前部门</label>
												<label style="float:left;width:20%;text-align: center;">异动前岗位</label>
												<label style="float:left;width:15%;text-align: center;">异动后部门</label>
												<label style="float:left;width:20%;text-align: center;">异动后岗位</label>
												<label style="float:left;width:10%;text-align: center;">操作</label>
										</div>
								</div>
							　</div>
							 <div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv">
										<label class="labelForInput">备注:</label>
										<textarea id="reason" name ="reason" style="width:685px;max-width:685px;max-height:50px;" class="form-control　formSpan need transform"></textarea>
									</div>
							　</div>
							
							　<div class="twoRowDiv" style="height:auto;margin-top:30px;">
									<div class="form-group formDiv" style="height: auto;">
										<label class="formLabel"><i class="fa fa-exclamation-triangle" aria-hidden="true" title="上传所有文件大小最大为30M"></i>附件上传:</label>
										<form id="fileForm" action="${ctx}/workflow/uploadFile" method="post">
											<input id="fileName" name="fileName" type="text" hidden='true'>
											<input id="file" name="file" type="file" size="30" style="float:left;width:236px;">
											<i class="fa fa-times" style="float:left;margin-top:5px;cursor: pointer;" title="删除" onclick="deleteFile(this)"></i>
										</form>
										<div class="form-group" style="float: right;margin-top: 5px;">
							        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="uploadFile();">上传附件</button>
											<!-- <button type="button" class="btn btn-default btn-sm" style="margin-right:10px;" onclick="delFile();">删除附件</button> -->
						            		<button type="button" class="btn btn-primary btn-sm" onclick="addFile('fileForm');">添加附件</button>
						            	</div>
									</div>
									<div class="form-group formDiv" style="float: left;width: 100%;">
										<label class="formLabel">下一步骤:</label>
										<span id="nextApproval" name="nextApproval" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv" style="float: left;width: 100%;">
										<label class="labelForInput">审批人员:</label>
										<select id="ApprovalStaff" name="ApprovalStaff" class="form-control formSpan need transform" ></select>
									</div>
							　</div>
							
							<div class="col-xs-12" style="float:left;">
								<div class="form-group" id="saveAndCancelDiv" style="float:right;">
					        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(15);">提交</button>
									<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
				            	</div>
							</div>
                  </div>
              </div>
         </div>
     </div>
     <div class="modal inmodal" id="SelectModal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog" style="width:740px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新建</h4>
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
				<div class="row">
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
            		<div class="form-group col-sm-6">
		                   	<label for="departmentTree" class="control-label">选择异动后部门:</label>
		                   	<div class="panel-body ztree" id="departmentTree" style="height: 300px;overflow: auto;"></div>
	                </div>
	                <div class="form-group col-sm-6">
		                   	<label for="departmentTree" class="control-label">选择异动后岗位:</label>
		                   	<select id="posts" name="posts" class="chosen-select tableInput" multiple></select>
	                </div>
	                
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="addPostChangeInfo();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="workflowDataModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">流程信息</h4>
				</div>
				<div class="modal-body" id="workflowDataDiv">
					
			   </div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx }/static/js/plugins/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/progress/postChangeForm.js"></script>
</body>
</html>