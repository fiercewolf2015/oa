<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用印申请</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv">
                 <div class="panel panel-primary">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">用印申请<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
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
                         		<div class="form-group formDiv" style="float:left;width:58%;">
									<label class="formLabel">紧急程度</label>
									<input id="level1" name="level" type="radio" value="一般" class="formSpan transform" style="width:5%" checked="checked"/>
									<label class="formLabel">一般</label>
									<input id="level2" name="level" type="radio" value="急" class="formSpan transform" style="width:5%"/>
									<label class="formLabel">急</label>
								</div>
								<div class="form-group formDiv" style="float:left;width:42%;">
									<label class="formLabel">用印日期:</label>
									<input id="useDate" name="useDate" type="text" class="form-control transform need" style="float:left;width:142px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" >
								</div>
						　</div>
						 <div class="twoRowDiv" style="height:auto;">
								<div class="form-group formDiv" id="useTypeDiv">
									<label class="formLabel">印章类型:</label>
								</div>
						 </div>
						 <div class="twoRowDiv" style="height:40px;">
								<div class="form-group formDiv">
									<label class="labelForInput">用印事由:</label>
									<textarea id="reason" name ="reason" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(6);">提交</button>
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
<script type="text/javascript" src="${ctx }/static/js/progress/useSealForm.js"></script>
</body>
</html>