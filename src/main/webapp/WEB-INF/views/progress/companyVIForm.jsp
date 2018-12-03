<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>天孚公司VI审批流程</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">天孚公司VI审批流程<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:105px;">
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
						 <div class="oneRowDiv" style="height:65px;">
						 		<div class="form-group formDiv">
									<label class="formLabel">VI名称:</label>
									<input id="VIname" name="VIname" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">申请日期:</label>
									<span id="applyDate" name="applyDate" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">工号:</label>
									<span id="staffNo" name="staffNo" class="formSpan transform"></span>
								</div>
						 </div>
						 <div class="twoRowDiv" style="height:80px;text-align: center;">
								<div class="form-group formDiv" style="width:20%;float:left;">
									<label class="labelForInput" style="width:100%;float:left;">标识内容</label>
									<form id="fileForm" action="${ctx}/workflow/uploadFile" method="post">
										<input id="fileName" name="fileName" type="text" hidden='true'>
										<input id="file" name="file" type="file" size="30" style="width:95%;float:left;">
									</form>
								</div>
								<div class="form-group formDiv" style="width:20%;float:left;">
									<label class="labelForInput">英文翻译</label>
									<input id="enName" name="enName" type="text" class="formSpan transform need" style="width:80%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:20%;float:left;">
									<label class="labelForInput">尺寸</label>
									<input id="size" name="size" class="formSpan transform need" style="width:80%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:20%;float:left;">
									<label class="labelForInput">拟用材料</label>
									<input id="material" name="material" class="formSpan transform need" style="width:80%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:20%;float:left;">
									<label class="labelForInput">安装方法及位置</label>
									<input id="position" name="position" class="formSpan transform need" style="width:80%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv" style="text-align: center;">
									<label class="labelForInput">预计制作费用:</label>
									<input id="price" name="price" class="formSpan transform need" style="width:100px;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
									<label class="labelForInput">元(预算内/外)</label>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:80px;text-align: center;">
								<div class="form-group formDiv" style="width:6%;float:left;">
									<label class="labelForInput">序号</label>
									<input id="no" name="no" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:12%;float:left;">
									<label class="labelForInput">名称</label>
									<input id="strName" name="strName" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:12%;float:left;">
									<label class="labelForInput">材质</label>
									<input id="strmaterial" name="strmaterial" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">长</label>
									<input id="strLength" name="strLength" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">宽</label>
									<input id="strWide" name="strWide" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">厚</label>
									<input id="strHeight" name="strHeight" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">数量</label>
									<input id="strCnt" name="strCnt" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">安装位置</label>
									<input id="strPosition" name="strPosition" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">安装方式</label>
									<input id="strWay" name="strWay" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								<div class="form-group formDiv" style="width:10%;float:left;">
									<label class="labelForInput">其他</label>
									<input id="other" name="other" class="formSpan transform need" style="width:98%;float:left;border-left: none;border-right: none;border-top: none;border-bottom: 1px solid;text-align:center;"/>
								</div>
								
						　</div>
						　<div class="oneRowDiv" style="height:100px;margin-top:30px;">
								<div class="form-group formDiv">
									<label class="formLabel">下一步骤:</label>
									<span id="nextApproval" name="nextApproval" class="formSpan transform">经理</span>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">审批人员:</label>
									<select id="ApprovalStaff" name="ApprovalStaff" class="form-control formSpan transform need"></select>
								</div>
						　</div>
						 <div class="col-xs-12" style="float:left;">
							<div class="form-group" id="saveAndCancelDiv" style="float:right;">
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(14);">提交</button>
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