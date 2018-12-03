<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>天孚公司供应商增加审批流程</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">天孚公司供应商增加审批流程<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
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
						 <div class="oneRowDiv" style="height:65px;">
								<div class="form-group formDiv">
									<label class="formLabel">申请日期:</label>
									<span id="applyDate" name="applyDate" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">工号:</label>
									<span id="staffNo" name="staffNo" class="formSpan transform"></span>
								</div>
						 </div>
                         <div class="twoRowDiv" style="height:435px;">
                         		<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">供方基本情况:</label>
									<input id="gongfang" name="gongfang" class="formSpan transform need" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">单位名称:</label>
									<input id="companyName" name="companyName" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">地址:</label>
									<input id="address" name="address" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">营业执照号:</label>
									<input id="number" name="number" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">营业执照有效期限:</label>
									<input id="validDate" name="validDate" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">公司法人:</label>
									<input id="companyPerson" name="companyPerson" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">联系电话:</label>
									<input id="telphone" name="telphone" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">公司联系人:</label>
									<input id="talPerson" name="talPerson" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">部门、职务:</label>
									<input id="companyDept" name="companyDept" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">电话/手机:</label>
									<input id="phone" name="phone" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">传真:</label>
									<input id="fax" name="fax" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">E-mail:</label>
									<input id="email" name="email" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">注册资本:</label>
									<input id="registeMoney" name="registeMoney" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:100%;text-align: center;">
									<strong>资质业绩情况审核</strong>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">服务供方证照情况:</label>
									<div style="width:18%;float:left;"><input name="situation" type="checkbox"  class="transform" value="营业执照"/><span>营业执照</span></div>
									<div style="width:18%;float:left;"><input name="situation" type="checkbox"  class="transform" value="资质证书"/><span>资质证书</span></div>
									<div style="width:18%;float:left;"><input name="situation" type="checkbox"  class="transform" value="税务登记证"/><span>税务登记证</span></div>
									<div style="width:18%;float:left;"><input name="situation" type="checkbox"  class="transform" value="其他证件"/><span>其他证件(勾选)</span></div>
								</div>
								<div class="form-group formDiv" style="float:left;height: 25px;">
									<label class="formLabel">主要营业范围:</label>
									<input id="business" name="business" class="formSpan transform need" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;height: 30px;">
									<label class="formLabel" style="height:100%;">服务类别:</label>
									<div style="width:18%;float:left;"><input name="serviceType" type="checkbox" class="transform" value="商品类"/><span>商品类</span></div>
									<div style="width:18%;float:left;"><input name="serviceType" type="checkbox" class="transform" value="服务类"/><span>服务类（勾选）</span></div>
								</div>
						　</div>
						 <div class="twoRowDiv" style="height:70px;">
								<div class="form-group formDiv">
									<label class="formLabel">企业主要业绩(包括为天孚物业服务的情况）:</label>
									<textarea id="score" name ="score" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
						　</div>
						 <div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv">
									<label class="formLabel">审核情况/证照是否过期</label>
									<input name="outDate" type="radio" class="formSpan transform" value="是" checked="checked" style="width:5%"/>
									<label class="formLabel">是</label>
									<input name="outDate" type="radio" class="formSpan transform" value="否" style="width:5%"/>
									<label class="formLabel">否</label>
								</div>
						　</div>
						 <div class="twoRowDiv" style="height:70px;">
								<div class="form-group formDiv">
									<label class="formLabel">业绩保障情况审核</label>
									<textarea id="review " name ="review" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:70px;">
								<div class="form-group formDiv">
									<label class="formLabel" style="width:100%;">1、服务能力展示情况（针对企业拥有的设备设施、人员及作业能力）</label>
									<textarea id="show" name ="show" style="width:714px;max-width:714px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv">
									<label class="formLabel" style="width:100%;">2、是否能够提供明确有效的服务实现方案，并作出书面承诺：</label>
									<input name="provide" type="radio" class="formSpan transform" value="能" checked="checked" style="width:5%"/>
									<label class="formLabel">能</label>
									<input name="provide" type="radio" class="formSpan transform" value="否" style="width:5%"/>
									<label class="formLabel">否</label>
								</div>
						　</div>
						
						<div class="twoRowDiv" style="height:70px;">
								<div class="form-group formDiv">
									<label class="formLabel" style="width:100%;">3、从业人员资格证书及从业所需的安全、健康等证件情况</label>
									<textarea id="personPic" name ="personPic" style="width:714px;max-width:714px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:70px;">
								<div class="form-group formDiv">
									<label class="formLabel" style="width:100%;">4、ISO质量认证等资料</label>
									<textarea id="ISO" name ="ISO" style="width:714px;max-width:714px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
						　</div>
						<div class="twoRowDiv" style="height:20px;">
								<div class="form-group formDiv">
									<label class="formLabel" style="width:60px;">备注：在</label>
									<input id="serviceDate" name="serviceDate" type="text" class="form-control transform need" style="float:left;width:102px;height:25px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" >
								   <label class="formLabel" style="width:210px;">之前，我公司现有该类服务商共计</label>
								   <input id="serviceCnt" name="serviceCnt" class="formSpan transform need" style="width: 6%;height:18px;"/>
								    <label class="formLabel" style="width:190px;">家，该服务商在我公司可提供(</label>
								    <input name="ableType" type="radio" class="formSpan transform" value="高" checked="checked" style="width:2%;"/>
									<label class="formLabel" style="width:16px;">高</label>
									<input name="ableType" type="radio" class="formSpan transform" value="中" style="width:2%;"/>
									<label class="formLabel" style="width:16px;">中</label>
									<input name="ableType" type="radio" class="formSpan transform" value="低" style="width:2%;"/>
									<label class="formLabel" style="width:75px;">低)档服务。</label>
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(13);">提交</button>
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