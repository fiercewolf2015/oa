<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>固定资产审批单</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:980px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:980px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">固定资产审批单<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:110px;">
									<div class="form-group formDiv">
										<label class="formLabel">表单编号:</label>
										<span id="formId" name="formId" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">申请人:</label>
										<span id="applyStaff" name="applyStaff" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv" style="float:left;">
										<label class="formLabel">所属分公司:</label>
										<select id="company" name="company" class="form-control transform formSpan" onchange="setParentId(this.value);"></select>
									</div>
							　</div>
							　<div class="oneRowDiv" style="height:120px;">
									<div class="form-group formDiv">
										<label class="formLabel">申请日期:</label>
										<span id="applyDate" name="applyDate" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">工号:</label>
										<span id="staffNo" name="staffNo" class="formSpan transform"></span>
									</div>
									<div class="form-group formDiv">
										<label class="formLabel">所属部门:</label>
										<select id="deptName" name="deptName" class="form-control transform formSpan" onchange="setAppStaff(this.value);"></select>
									</div>
							　</div>
							<div class="twoRowDiv" style="height:120px;">
								<div class="form-group formDiv" style="float:left;text-align: center;font-size: 20px;">
									申请明细
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">调出部门:</label>
									<input id="oldDept"  name="oldDept" readonly="readonly"  onClick="showTreeView('departmentTreeOld')" class="formSpan transform need"/>
           							<div class="row panel-body ztree" id="departmentTreeOld" style="overflow: auto;position:absolute;width:200px;display:none;padding:0;margin-left:100px;background: #fff;border: 1px solid #e7eaec;border-radius: 2px;margin-top:24px;"></div>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">调入部门:</label>
									<input id="newDept" name="newDept" readonly="readonly"  onClick="showTreeView('departmentTreeNew')" class="formSpan transform need"/>
                           			<div class="row panel-body ztree" id="departmentTreeNew" style="overflow: auto;position:absolute;width:200px;display:none;padding:0;margin-left:100px;background: #fff;border: 1px solid #e7eaec;border-radius: 2px;margin-top:24px;"></div>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">业务类型:</label>
									<select id="bussinessType" name="bussinessType" class="form-control transform need formSpan"></select>
								</div>
							</div>
							<div class="twoRowDiv" style="height:50px;">
									<div class="form-group formDiv">
										<label class="labelForInput" style="width:120px;">资产购置(处置)原因:</label>
										<textarea id="reason" name ="reason" style="width:685px;max-width:685px;max-height:50px;" class="form-control　formSpan need transform"></textarea>
									</div>
						　	</div>
							<div class="twoRowDiv" style="height:400px;">
									<div class="form-group formDiv" style="float:left;text-align: center;font-size: 20px;">
										新增资产信息
									</div>
									<button type="button" class="btn btn-primary btn-sm" style="float:right;" onclick="showSelectModal();">添加记录</button>
									<div class="form-group formDiv" style="height:240px;width:100%;">
										<div id="assetsInfo" style="border: 1px solid black;float: left;width: 87%;height:200px;overflow-y:auto;text-align: center;">
												<label style="float:left;width:10%;text-align: center;">资产编号</label>
												<label style="float:left;width:10%;text-align: center;">资产名称</label>
												<label style="float:left;width:10%;text-align: center;">资产规格</label>
												<label style="float:left;width:10%;text-align: center;">数量</label>
												<label style="float:left;width:10%;text-align: center;">单价</label>
												<label style="float:left;width:10%;text-align: center;">资产原值</label>
												<label style="float:left;width:10%;text-align: center;">累计折旧</label>
												<label style="float:left;width:10%;text-align: center;">资产净值</label>
												<label style="float:left;width:10%;text-align: center;">开始使用年限</label>
												<label style="float:left;width:10%;text-align: center;">操作</label>
										</div>
									</div>
									<div class="form-group formDiv" style="height:30px;width:87%;">
										<label style="float:left;width:30%;text-align: right;">合计:</label>
										<label class="transform" id="totalCnt" name="totalCnt" style="float:left;width:10%;text-align: center;">0</label>
										<label class="transform" id="totalPrice" name="totalPrice" style="float:left;width:10%;text-align: center;">0.00</label>
										<label class="transform" id="totalOldPrice" name="totalOldPrice" style="float:left;width:10%;text-align: center;">0.00</label>
										<label class="transform" id="totalReduce" name="totalReduce" style="float:left;width:10%;text-align: center;">0.00</label>
										<label class="transform" id="totalLeft" name="totalLeft" style="float:left;width:10%;text-align: center;">0.00</label>
										<label class="transform" id="totalYear" name="totalYear" style="float:left;width:10%;text-align: center;">0</label>
									</div>
									<div class="form-group formDiv" style="height:30px;width:87%;">
										<label style="float:left;width:30%;text-align: right;"></label>
										<label style="float:left;width:10%;text-align: center;">数量</label>
										<label style="float:left;width:10%;text-align: center;">单价</label>
										<label style="float:left;width:10%;text-align: center;">资产原值</label>
										<label style="float:left;width:10%;text-align: center;">累计折旧</label>
										<label style="float:left;width:10%;text-align: center;">资产净值</label>
										<label style="float:left;width:10%;text-align: center;">开始使用年限</label>
									</div>
									<div class="form-group formDiv" style="height:30px;width:87%;">
										<label style="float:left;width:10%;text-align: center;">大于5条明细见附件</label>
										<label style="float:left;width:20%;text-align: right;">附件合计数</label>
										<input class="transform" id="FiletotalCnt" name="FiletotalCnt" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
										<input class="transform" id="FiletotalPrice" name="FiletotalPrice" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
										<input class="transform" id="FiletotalOldPrice" name="FiletotalOldPrice" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
										<input class="transform" id="FiletotalReduce" name="FiletotalReduce" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
										<input class="transform" id="FiletotalLeft" name="FiletotalLeft" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
										<input class="transform" id="FiletotalYear" name="FiletotalYear" style="float:left;width:9%;text-align: center;margin-left:1%;"/>
									</div>
							</div>
							 <div class="twoRowDiv" style="height:40px;">
									<div class="form-group formDiv">
										<label class="labelForInput">备注:</label>
										<textarea id="remark" name ="remark" style="width:685px;max-width:685px;max-height:50px;" class="form-control　formSpan need transform"></textarea>
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
					        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(7);">提交</button>
									<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
				            	</div>
							</div>
							<input id="parentDeptId" name="parentDeptId" class="formSpan transform" type="text" style="display:none;">
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
				<div class="row">
            		<div class="form-group col-sm-6" style="margin-top:10px;float:left">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产编号:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产名称:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产规格:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">数量:</label>
		                   	<input id="newCnt" type="text" class="formSpan" onkeyup="checkOldPrice();"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">单价:</label>
		                   	<input id="newPrice" type="text" class="formSpan" onkeyup="checkOldPrice();"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产原值:</label>
		                   	<input id="newOldPrice" type="text" class="formSpan" disabled="disabled"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">累计折旧:</label>
		                   	<input id="newReduce" type="text" class="formSpan" onkeyup="checkOldPrice();"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产净值:</label>
		                   	<input id="newLeft" type="text" class="formSpan" disabled="disabled"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">开始使用年限:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="addInfo();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="EditselectModal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog" style="width:740px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">新建</h4>
			</div>
			<div class="modal-body">
				<div class="row">
            		<div class="form-group col-sm-6" style="margin-top:10px;float:left">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产编号:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产名称:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产规格:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">数量:</label>
		                   	<input id="oldCnt"  type="text" class="formSpan" onkeyup="editCheckOldPrice();"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">单价:</label>
		                   	<input id="oldPrice" type="text" class="formSpan" onkeyup="editCheckOldPrice();" />
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产原值:</label>
		                   	<input id="oldOldPrice" type="text" class="formSpan" disabled="disabled"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">累计折旧:</label>
		                   	<input id="oldReduce" type="text" class="formSpan" onkeyup="editCheckOldPrice();"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产净值:</label>
		                   	<input id="oldLeft" type="text" class="formSpan" disabled="disabled"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">开始使用年限:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="addEditInfo();">确定</button>
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
<script type="text/javascript" src="${ctx }/static/js/progress/fixedAssetsForm.js"></script>
</body>
</html>