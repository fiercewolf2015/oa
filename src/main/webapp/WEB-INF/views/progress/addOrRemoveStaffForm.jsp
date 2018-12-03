<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>天孚公司增减人员申报审批单</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">天孚公司增减人员申报审批单<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
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
									<span id="applyDeptName" name="applyDeptName" class="formSpan transform"></span>
								</div>
						 </div>
						 <div class="oneRowDiv" style="height:110px;">
								<div class="form-group formDiv">
									<label class="formLabel">申请日期:</label>
									<span id="applyDate" name="applyDate" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">工号:</label>
									<span id="staffNo" name="staffNo" class="formSpan transform"></span>
								</div>
						 </div>
                         <div class="twoRowDiv" style="height:30px;">
                         		<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">所属分公司:</label>
									<input id="company" name="company" type="text" class="formSpan transform need" style="width:35%"/>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:30px;">
                        	<div class="form-group formDiv" style="float:left;width:49%;">
								<label class="formLabel">所属项目</label>
								<input id="deptName" name="deptName" type="text" class="formSpan transform need" style="width:35%"/>
							</div>
					　 </div>
						 <div class="twoRowDiv" style="height:25px;">
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">增加人数:</label>
									<input id="addCount" name="addCount" type="text" class="formSpan transform" style="width:35%"/>
								</div>
						 </div>
						 <div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv">
									<label class="labelForInput">增加人员:</label>
									<textarea id="addStaff" name ="addStaff" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform"></textarea>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv">
									<label class="labelForInput">所属岗位:</label>
									<input id="addPost" name="addPost" type="text" class="formSpan transform" style="width:25%"/>
								</div>
						　</div>
						<!-- 
						<div class="twoRowDiv" style="height:25px;">
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">减少人数:</label>
									<input id="removeCount" name="removeCount" type="text" class="formSpan transform" style="width:35%"/>
								</div>
						 </div>
						 <div class="twoRowDiv" style="height:40px;">
								<div class="form-group formDiv">
									<label class="labelForInput">减少人员:</label>
									<textarea id="removeStaff" name ="removeStaff" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform"></textarea>
								</div>
						　</div>
						 -->
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(11);">提交</button>
								<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
			            	</div>
						 </div>
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
</body>
</html>