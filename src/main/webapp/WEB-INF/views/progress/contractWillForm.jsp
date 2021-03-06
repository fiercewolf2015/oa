<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>合同会审单</title>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">合同会审单<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:75px;">
                        		<div class="form-group formDiv">
									<label class="formLabel">表单编号:</label>
									<span id="formId" name="formId" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">申请人:</label>
									<span id="applyStaff" name="applyStaff" class="formSpan transform"></span>
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
                         <div class="twoRowDiv" style="height:780px;">
                         		<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">所属分公司:</label>
									<select id="company" name="company" class="form-control transform formSpan need" onchange="setParentId(this.value);"></select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">所属部门:</label>
									<select id="deptName" name="deptName" class="form-control transform formSpan need" onchange="setAppStaff(this.value);"></select>
								</div>
								<!-- 
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">相关部门:</label>
									<div style="width:18%;float:left;"><input id="selDept1" name="selDept" type="checkbox" class="transform" value="综合管理部"/><span>综合管理部</span></div>
									<div style="width:18%;float:left;"><input id="selDept2" name="selDept" type="checkbox" class="transform" value="计划财务部"/><span>计划财务部</span></div>
									<div style="width:18%;float:left;"><input id="selDept3" name="selDept" type="checkbox" class="transform" value="技术支持部"/><span>技术支持部</span></div>
									<div style="width:18%;float:left;"><input id="selDept4" name="selDept" type="checkbox" class="transform" value="党群工作部"/><span>党群工作部</span></div>
								</div>
								 -->
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">合同编号:</label>
									<select id="contractNo1" name="contractNo1"class="formSpan transform need" style="float:left;width:15%" onchange="changeNo();">
										<option value="TFBB">TFBB</option>
										<option value="TFON">TFON</option>
										<option value="TFTW">TFTW</option>
										<option value="TFTH">TFTH</option>
										<option value="TFFO">TFFO</option>
										<option value="TFFI">TFFI</option>
										<option value="TFTF">TFTF</option>
									</select>
									<label style="float:left;">—</label>
									<select id="contractNo2" name="contractNo2"class="formSpan transform need" style="float:left;width:15%"></select>
									<label style="float:left;">—</label>
									<select id="contractNo3" name="contractNo3"class="formSpan transform need" style="float:left;width:15%">
										<option value="SRWY">SRWY</option>
										<option value="SRYS">SRYS</option>
										<option value="SRTY">SRTY</option>
										<option value="SRCZ">SRCZ</option>
										<option value="SRQT">SRQT</option>
										<option value="ZCWW">ZCWW</option>
										<option value="ZCGX">ZCGX</option>
										<option value="ZCSG">ZCSG</option>
										<option value="CZGY">CZGY</option>
										<option value="ZCQT">ZCQT</option>
									</select>
									<label style="float:left;">—</label>
									<input id="contractNo4" name="contractNo4" type="text" class="formSpan transform need" style="float:left;width:15%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">合同份数:</label>
									<input id="contractCount" name="contractCount" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">合同期限:</label>
									<input id="contractBeginDate" name="contractBeginDate" type="text" class="form-span transform need" style="float:left;width:142px;height:26px;"> <!-- onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" -->
									<label style="float:left;">——</label>
									<input id="contractEndDate" name="contractEndDate" type="text" class="form-span
									 transform need" style="float:left;width:142px;height:26px;"  ><!-- onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" -->
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">合同名称:</label>
									<input id="contractName" name="contractName" class="formSpan transform need" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">合同单价:</label>
									<input id="contractPrice" name="contractPrice" type="text" class="formSpan transform need" style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">合同月金额:</label>
									<input id="contractMonthPrice" name="contractMonthPrice" type="text" class="formSpan transform " style="float:left;"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:100%;">
									<label class="formLabel">合同总价:</label>
									<input id="contractPriceTotal" name="contractPriceTotal" class="formSpa transform need" onchange="changeConPrice();"/> <!-- onkeyup='this.value=this.value.replace(/[^\d.]/g,"")' -->
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">付款时间:</label>
									<input id="paymentDate" name="paymentDate" class="formSpa transform "/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">收款时间:</label>
									<input id="ReceivablesDate" name="ReceivablesDate" class="formSpa transform "/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">付款金额:</label>
									<input id="paymentMoney" name="paymentMoney" class="formSpa transform "/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">收款金额:</label>
									<input id="ReceivablesMoney" name="ReceivablesMoney" class="formSpa transform "/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">付款时间段:</label>
									<select id="paymentTimeSlot" name="paymentTimeSlot"class="formSpan transform " style="float:left;"onchange="showOtherDiv(this.value);">
										<option value="按月支付">按月支付</option>
										<option value="按季度支付">按季度支付</option>
										<option value="按半年支付">按半年支付</option>
										<option value="按年支付">按年支付</option>
										<option value="其他">其他</option>
									</select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">收款时间段:</label>
									<select id="ReceivablesTimeSlot" name="ReceivablesTimeSlot"class="formSpan transform " style="float:left;"onchange="showOtherDiv(this.value);">
										<option value="按月收款">按月收款</option>
										<option value="按季度收款">按季度收款</option>
										<option value="按半年收款">按半年收款</option>
										<option value="按年收款">按年收款</option>
										<option value="其他">其他</option>
									</select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">票据类型:</label>
									<select id="billType" name="billType"class="formSpan transform need" style="float:left;width:15%" onchange="changeConPrice();">
										<option value="专票">专票</option>
										<option value="普票">普票</option>
										<option value="其他">其他</option>
									</select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">税率:</label>
									<select id="taxRate" name="taxRate"class="formSpan transform need" style="float:left;width:15%" onchange="changeConPrice();">
										<option value="免税">免税</option>
										<option value="0%">0%</option>
										<option value="3%">3%</option>
										<option value="6%">6%</option>
										<option value="11%">11%</option>
										<option value="13%">13%</option>
										<option value="17%">17%</option>
									</select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">价款:</label>
									<input id="price" name="price" class="formSpa transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">税款:</label>
									<input id="tax" name="tax" class="formSpa transform need "/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">开具发票时间:</label>
									<input id="invoiceDate" name="invoiceDate" class="formSpa transform need"/>
								</div>
								<div id="paymentOtherReasonDiv" class="form-group formDiv" style="float:left;display: none">
									<input id="paymentOtherReason" name="paymentOtherReason" class="formSpan transform" style="width: 90%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">甲方名称:</label>
									<input id="jiaName" name="jiaName" class="formSpan transform need" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">乙方名称:</label>
									<input id="yiName" name="yiName" class="formSpan transform need" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">第三方名称:</label>
									<input id="sanName" name="sanName" class="formSpan transform" style="width: 75%;"/>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">合同版本:</label>
									<div style="width:18%;float:left;"><input name="contractVersion" type="checkbox" class="transform" value="标准格式合同" /><span>标准格式合同</span></div>
									<div style="width:18%;float:left;"><input name="contractVersion" type="checkbox"  class="transform" value="甲方格式合同"/><span>甲方格式合同</span></div>
									<div style="width:18%;float:left;"><input name="contractVersion" type="checkbox"  class="transform" value="经律师审核合同"/><span>经律师审核合同</span></div>
									<div style="width:18%;float:left;"><input name="contractVersion" type="checkbox"  class="transform" value="未经律师审核合同"/><span>未经律师审核合同</span></div>
								</div>
								<div class="form-group formDiv" style="float:left;height: 50px;">
									<label class="formLabel">合同类别:</label>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="物业合同"/><span>物业合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="延伸合同"/><span>延伸合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="特约合同"/><span>特约合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="场租合同"/><span>场租合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="其他收入"/><span>其他收入</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox" class="transform" value="外委合同" /><span>外委合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="购销合同"/><span>购销合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="施工合同"/><span>施工合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="公共事业合同"/><span>公共事业合同</span></div>
									<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="其他支出"/><span>其他支出</span></div>
								</div>
								<div class="form-group formDiv" style="float:left;height: 70px;">
									<label class="formLabel" style="height:100%;">附件类型:</label>
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="营业执照副本复印件"/><span>营业执照副本复印件</span></div>
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="税务登记证复印件"/><span>税务登记证复印件</span></div>
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="资质证书复印件"/><span>资质证书复印件</span></div>
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="安全协议" /><span>安全协议</span></div>
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="授权委托书"/><span>授权委托书</span></div>
									
									<div style="width:18%;float:left;"><input name="nearType" type="checkbox" class="transform" value="律师审核意见涵"/><span>律师审核意见涵</span></div>
									<div style="width:40%;float:left;"><input name="nearType" type="checkbox" class="transform" value="报送单位合同说明"/><span>报送单位合同说明</span></div>
									<div style="width:40%;float:left;"><input name="nearType" type="checkbox" class="transform" style="float:left;" value="其他"/><span style="float:left;">其他</span><input id="othernearType" name="othernearType" class="formSpan transform" style="float: left;height: 18px;margin-top: 2px;margin-left: 2px;"/></div>
								</div>
						　</div>
						 	<div class="twoRowDiv" style="height:190px;">
								<div class="form-group formDiv" style="float:left;height: 70px;">
									<label class="labelForInput">备注:</label>
									<textarea id="reason" name ="reason" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
							<div class="form-group formDiv" style="float:left;width:49%;">
								<label class="formLabel">项目业态类型:</label>
								<select id="projectOperationType" name="projectOperationType"class="formSpan transform " style="float:left;"onchange="showProjectOtherDiv(this.value);">
									<option value="住宅类">住宅类</option>
									<option value="公寓类">公寓类</option>
									<option value="商业类">商业类</option>
									<option value="写字楼">写字楼</option>
									<option value="学校后勤">学校后勤</option>
									<option value="工业园区厂房">工业园区厂房</option>
									<option value="交通设施">交通设施</option>
									<option value="其他">其他</option>
								</select>
							</div>
							<div class="form-group formDiv" style="float:left;width:49%;">
								<label class="formLabel">面积统计:</label>
								<input id="areaStatistics" name="areaStatistics" class="formSpan transform "/>
							</div>
							<div id="projectOperationTypeReasonDiv" class="form-group formDiv" style="float:left;display: none">
								<input id="projectOperationTypeReason" name="projectOperationTypeReason" class="formSpan transform" style="width: 90%;"/>
							</div>
							<div class="form-group formDiv" style="float:left;width: 49%">
								<label class="formLabel">项目地址:</label>
								<input id="projectAddr" name="projectAddr" class="formSpan transform "/>
							</div>
							<div class="form-group formDiv" style="float:left;width: 49%">
								<label class="formLabel">甲方名称:</label>
								<input id="projectJIaName" name="projectJIaName" class="formSpan transform "/>
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(8);">提交</button>
								<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
			            	</div>
						 </div>
						 <input id="parentDeptId" name="parentDeptId" class="formSpan transform" type="text" style="display:none;">
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
<script type="text/javascript">
	changeNo();
	util.commAjax(companyNameOptions);
</script>
</body>
</html>