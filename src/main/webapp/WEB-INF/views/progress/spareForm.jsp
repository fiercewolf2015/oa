<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>自定义名称</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div style="width:820px;margin:auto;">
                 <div class="panel panel-primary">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">自定义名称</div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:100px;">
									<div class="form-group formDiv">
										<label class="formLabel">表单编号:</label>
										<span id="formId" name="formId" class="formSpan">455613274812</span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">申请人:</label>
										<span id="applyStaff" name="applyStaff" class="formSpan">张三</span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">所属部门:</label>
										<span id="deptName" name="deptName" class="formSpan">研发部</span>
									</div>
							　</div>
							　<div class="oneRowDiv" style="height:100px;">
									<div class="form-group formDiv">
										<label class="formLabel">申请日期:</label>
										<span id="applyDate" name="applyDate" class="formSpan">2016-07-06</span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">工号:</label>
										<span id="staffNo" name="staffNo" class="formSpan">0102</span>
									</div>
							　</div>
							 <div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv">
										<label class="labelForInput">自定义名称:</label>
										<input id="companyName" name="companyName" type="text" class="formSpan" style="width:35%"/>
									</div>
							　</div>
							 <div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv" style="float:left;width:49%;">
										<label class="labelForInput">自定义名称:</label>
	                   					<input id="companyName" name="companyName" type="text" class="formSpan" style="width:35%"/>
	                   				</div>
	                   				<div class="form-group formDiv" style="float:left;width:49%;">
										<label class="labelForInput">自定义名称:</label>
	                   					<input id="companyName" name="companyName" type="text" class="formSpan" style="width:35%"/>
	                   				</div>
							　</div>
							 <div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv">
										<label class="labelForInput">自定义名称:</label>
										<textarea id="overtimeReason" name ="overtimeReason" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan"></textarea>
									</div>
							　</div>
							<div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv">
										<label class="labelForInput">自定义名称:</label>
										<textarea id="overtimeReason" name ="overtimeReason" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan"></textarea>
									</div>
							　</div>
							
							　<div class="oneRowDiv" style="height:100px;margin-top:30px;	">
									<div class="form-group formDiv">
										<label class="formLabel">附件上传:</label>
										<input id="file" name="file" type="file" size="30" style="float:left;width:270px;">
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">下一步骤:</label>
										<span id="nextApproval" name="nextApproval" class="formSpan">经理</span>
									</div>
									<div class="form-group formDiv">
										<label class="labelForInput">审批人员:</label>
										<select id="ApprovalStaff" name="ApprovalStaff" class="form-control formSpan"></select>
									</div>
							　</div>
							
							<div class="col-xs-12" style="float:left;">
								<div class="form-group" id="saveAndCancelDiv" style="float:right;">
					        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="save();">提交</button>
									<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
				            	</div>
							</div>
                  </div>
              </div>
         </div>
     </div>
<script type="text/javascript" src="${ctx }/static/js/progress/spareForm.js"></script>
</body>
</html>