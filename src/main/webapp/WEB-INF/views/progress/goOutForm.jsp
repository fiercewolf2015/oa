<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>天孚公司外出活动审批表</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">天孚公司外出活动审批表<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:150px;">
                        		<div class="form-group formDiv">
									<label class="formLabel">表单编号:</label>
									<span id="formId" name="formId" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">申请单位(部门):</label>
									<span id="deptName" name="deptName" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">活动主题:</label>
									<input id="activityName" name="activityName" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">活动主办单位:</label>
									<input id="activityCompany" name="activityCompany" class="formSpan transform need" style="width:60%"/>
								</div>
						 </div>
						 <div class="oneRowDiv" style="height:110px;">
								<div class="form-group formDiv">
									<label class="formLabel">申请日期:</label>
									<span id="applyDate" name="applyDate" class="formSpan transform">2016-07-06</span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">申请人:</label>
									<span id="applyStaff" name="applyStaff" class="formSpan transform"></span>
								</div>
						 </div>
                         <div class="twoRowDiv" style="height:190px;">
                         		<div class="form-group formDiv" style="float:left;width:49%">
									<label class="formLabel">外出目的地:</label>
									<input id="address" name="address" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%">
									<label class="formLabel">活动人数:</label>
									<input id="personCount" name="personCount" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">交通安排:</label>
									<input id="car" name="car" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel" style="width: 85px;">起止时间:</label>
									<input id="beginDate" name="beginDate" type="text" class="form-control transform need" style="float:left;width:142px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" >
									<label class="formLabel" style="width: 14px;">至</label>
									<input id="endDate" name="endDate" type="text" class="form-control transform need" style="float:left;width:142px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})" >
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">带队负责人:</label>
									<input id=captain name="captain" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">联系电话:</label>
									<input id="phone" name="phone" class="formSpan transform need" style="width:60%"/>
								</div>
								<div class="form-group formDiv" style="float:left">
									<label class="formLabel">外出行程:</label>
									<input id="trip" name="trip" class="formSpan transform need" style="width:85%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">活动经费:</label>
									<select id="giveMoney" name="giveMoney" class="form-control tableInput" style="width:60%">
           								<option value="">请选择</option>
           								<option value="预算内">预算内</option>
           								<option value="预算外">预算外</option>
           							</select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">活动总费用:</label>
									<input id="useMoney" name="useMoney" class="formSpan transform need" style="width:44%"/>
									<label class="formLabel" style="width:115px;">元(测算依据附件)</label>
								</div>
								
						　</div>
						 <div class="twoRowDiv" style="height:40px;">
								<div class="form-group formDiv">
									<label class="labelForInput">外出事由:</label>
									<textarea id="reason" name ="reason" style="width:670px;max-width:670px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(9);">提交</button>
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