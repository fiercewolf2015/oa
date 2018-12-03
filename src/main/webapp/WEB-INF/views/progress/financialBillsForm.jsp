<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>财务票据审批单</title>
    <style>
    	.inputWithoutBorder{
    		border:none;
    		width:60px;
    	}
    </style>
</head>
<body>
	 <div class="row" style="padding:45px 20px 20px 20px">
         <div id="saveDiv" style="max-width:820px;margin:auto;overflow-x:auto;">
                 <div class="panel panel-primary" style="width:820px;">
                     <div class="panel-heading"  style="text-align:center;font-size:18px;">财务票据审批单<span style="float: right;margin-right: 10px;cursor: pointer;" onclick="showWorkFlow('${liId}')" >查看流程</span></div>
                     <div class="panel-body">
                        <div class="oneRowDiv" style="height:115px;">
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
						 <div class="oneRowDiv" style="height:115px;">
								<div class="form-group formDiv">
									<label class="formLabel">申请日期:</label>
									<span id="applyDate" name="applyDate" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv">
									<label class="formLabel">工号:</label>
									<span id="staffNo" name="staffNo" class="formSpan transform"></span>
								</div>
								<div class="form-group formDiv" style="float:left;">
									<label class="formLabel">所属部门:</label>
									<select id="deptName" name="deptName" class="form-control transform formSpan" onchange="setAppStaff(this.value);"></select>
								</div>
						 </div>
                    <div class="twoRowDiv" style="height:115px;">
                         		<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">经办人:</label>
									<input id="manager" name="manager" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">预算专员:</label>
									<select id="budgetPerson" name="budgetPerson" class="formSpan need transform"></select>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<input name="financeType" type="radio" value="现金流" class="formSpan transform" style="width:5%" checked/>
									<label class="formLabel">现金流</label>
									<input name="financeType" type="radio" value="非现金流" class="formSpan transform" style="width:5%"/>
									<label class="formLabel">非现金流</label>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">业务类型:</label>
									<input id="bussinessType" name="bussinessType" type="text" class="formSpan transform" onclick="selSecondFinanceSubject(this.id);"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;"></div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="formLabel">大写金额:</label>
									<span id="bussinessBig" name="bussinessBig" class="formSpan transform"></span>
								</div>
						　</div>
						<div class="oneRowDiv" style="height:150px;">
								<div class="form-group formDiv">
									<label class="labelForInput">报销类型:</label>
									<select id="reimbursementType" name="reimbursementType" class="form-control transform" style="width: 143px;">
										<option value="有发票报销">有发票报销</option>
										<option value="无发票请款">无发票请款</option>
										<option value="银行相关手续费">银行相关手续费</option>
										<option value="往来款">往来款</option>
										<option value="借备用金">借备用金</option>
									</select>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">结算方式:</label>
									<select id="statements" name="statements" class="form-control transform" style="width: 143px;">
										<option value="现金">现金</option>
										<option value="转账支票">转账支票</option>
										<option value="银行汇款">银行汇款</option>
										<option value="银行本票">银行本票</option>
										<option value="商兑汇票">商兑汇票</option>
										<option value="同城委托收款">同城委托收款</option>
										<option value="银行电汇">银行电汇</option>
										<option value="银行信汇">银行信汇</option>
										<option value="网上报税">网上报税</option>
										<option value="网上汇款">网上汇款</option>
										<option value="其他">其他</option>
									</select>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">对方单位全称:</label>
									<input id="otherCompanyName" name="otherCompanyName" class="formSpan transform "/>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">收款人账号:</label>
									<input id="receiveAccount" name="receiveAccount" class="formSpan transform "/>
								</div>
								<div class="form-group formDiv">
									<label class="labelForInput">收款人开户行:</label>
									<input id="receiveBank" name="receiveBank" class="formSpan transform "/>
								</div>
						　</div>
						<div class="oneRowDiv" style="height:185px;">
								<div class="form-group formDiv">
									<label class="labelForInput" style="height:170px;float:left;line-height: 23px;">科目明细:</label>
									<input id="accountantItem1" name="accountantItem1" class="formSpan transform" onclick="selThreeFinanceSubject(this.id);"/>
									<input id="accountantItem2" name="accountantItem2" class="formSpan transform" style="margin-top:15px;" onclick="selThreeFinanceSubject(this.id);"/>
									<input id="accountantItem3" name="accountantItem3" class="formSpan transform" style="margin-top:15px;" onclick="selThreeFinanceSubject(this.id);"/>
									<input id="accountantItem4" name="accountantItem4" class="formSpan transform" style="margin-top:15px;" onclick="selThreeFinanceSubject(this.id);"/>
									<input id="accountantItem5" name="accountantItem5" class="formSpan transform" style="margin-top:15px;" onclick="selThreeFinanceSubject(this.id);"/>
								</div>
						　</div>
						 <div class="twoRowDiv" style="height:50px;">
								<div class="form-group formDiv">
									<label class="labelForInput">用途说明:</label>
									<textarea id="reason" name ="reason" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform"></textarea>
								</div>
						　</div>
								<!-- 
						<div class="twoRowDiv" style="height:30px;">
							<textarea id="explainContent" name ="explainContent" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								<div class="form-group formDiv" style="float:left;width:55%;">
									<label class="formLabel" style="float:left;width:16%;">相关部门:</label>
									<div style="width:19%;float:left;"><input name="selDept" type="checkbox" class="transform" value="综合管理部"/><span>综合管理部</span></div>
									<div style="width:19%;float:left;"><input name="selDept" type="checkbox" class="transform" value="计划财务部"/><span>计划财务部</span></div>
									<div style="width:19%;float:left;"><input name="selDept" type="checkbox" class="transform" value="技术支持部"/><span>技术支持部</span></div>
									<div style="width:19%;float:left;"><input name="selDept" type="checkbox" class="transform" value="党群工作部"/><span>党群工作部</span></div>
								</div>
						　</div>
								 -->
						<!-- 
						<div class="twoRowDiv" style="height:20px;margin-top: 30px;">
							<input name="contractFlag" type="radio" value="有合同报销" style="margin-left:10px;float: left;" checked onclick="changeFormDiv(this.value);"/>
							<span style="float:left;margin-left:5px;font-weight:700">有合同报销</span>
							<input name="contractFlag" type="radio" value="无合同报销" style="margin-left:10px;float: left;" onclick="changeFormDiv(this.value)"/>
							<span style="float:left;margin-left:5px;font-weight:700">无合同报销</span>
					　</div>
						<div id="contractDiv" class="twoRowDiv" style="height:280px;">
								<div class="form-group formDiv" style="float:left;text-align: center;font-size: 25px;">
									合同信息
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">合同编号:</label>
									<select id="contractNo1" name="contractNo1"class="formSpan transform need" style="float:left;width:15%" onchange="changeNo();">
										<option value="TFBB">TFBB</option>
										<option value="TFON">TFON</option>
										<option value="TFTW">TFTW</option>
										<option value="TFTH">TFTH</option>
										<option value="TFFO">TFFO</option>
										<option value="TFFI">TFFI</option>
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
									<input id="contractNo4" name="contractNo4" type="text" class="formSpan transform need" onblur="getContractForm();" style="float:left;width:15%"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">合同期间:</label>
									<input id="beginDate" name="beginDate" type="text" class="form-control transform need" style="float:left;width:110px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" >
									<label class="formLabel" style="width: 14px;">-</label>
									<input id="endDate" name="endDate" type="text" class="form-control transform need" style="float:left;width:110px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" >
								</div>
								<div class="form-group formDiv" style="float:left;height: 45px;">
									<label class="labelForInput">合同内容:</label>
									<textarea id="contractContent" name ="contractContent" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform need"></textarea>
								</div>
								<div class="form-group formDiv" style="float:left;height: 45px;">
									<label class="labelForInput">无合同说明:</label>
									<textarea id="noContractDescription" name ="noContractDescription" style="width:600px;max-width:600px;max-height:50px;" class="form-control　formSpan transform"></textarea>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">合同价格:</label>
									<input id="contractPrice" name="contractPrice" class="formSpan transform need"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput" style="float:left;width:26%;">合同税率:</label>
									<input id="contractRate" name="contractRate" class="formSpan transform need" style="float:left;width:14%;"/>
									<label class="labelForInput" style="float:left;width:49%;">%</label>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">价税合计:</label>
									<input id="priceRate" name="priceRate" class="formSpan transform need" disabled="disabled"/>
								</div>
								<div class="form-group formDiv" style="float:left;width:49%;">
									<label class="labelForInput">合同税金:</label>
									<input id="contractMoney" name="contractMoney" class="formSpan transform need" disabled="disabled"/>
								</div>
						　</div>
						 -->
						<div class="twoRowDiv" style="height:340px;">
								<div class="form-group formDiv" style="float:left;text-align: center;font-size: 25px;">
									本次票据
								</div>
								<div class="form-group formDiv" style="float:left;font-size: 25px;">
									<li style="color: red">注：开票需携带资料：合同、一般纳税人认定表。分公司总经理、项目经理或部门经理为责任人，责任人承诺：此次票据数据准确，业务真实。</li>
								</div>
								<table  border="1" bordercolor="#E0E0E0" style="border-collapse:collapse;width: 675px;margin-left: 20px;">
									<tr>
										<td width="13%">发票类型</td>
										<td width="87%">实际价格</td>
									</tr>
									<tr>
										<td>
											<input name="invoiceType" type="radio" value="专票" class="formSpan transform" style="width:15%;margin-left:10px;" checked onclick="changeInvoiceType(1)"/>
											<span style="float:left;margin-left:5px;">专票</span>
										</td>
										<td>价款:<input id="zMoney" name="zMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="zpChange();" style="width: 40px;">元;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;税款:<input id="zTax" name="zTax" type="text" class="inputWithoutBorder transform" onkeyup="zpChange();" style="width: 40px;">元;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价税合计:<input id="zTotal" name="zTotal" type="text" class="inputWithoutBorder transform" onkeyup="smalltoBIG(this.value,'zBig')" style="width: 40px;">元&nbsp;&nbsp;大写金额:<span class="transform" id="zBig" name="zBig" style="margin-left:5px;">零元整</span></td>
									</tr>
									<tr>
										<td>
											<input name="invoiceType" type="radio" value="非专票" class="formSpan transform" style="width:15%;margin-left:10px;" onclick="changeInvoiceType(2)"/>
											<span style="float:left;margin-left:5px;">非专票</span>
										</td>
										<td>价款:<input id="nzMoney" name="nzMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="smalltoBIG(this.value,'nzBig')" disabled>元&nbsp;&nbsp;大写金额:<span class="transform" id="nzBig"  style="margin-left:5px;">零元整</span></td>
									</tr>
									<tr>
										<td>
											<input name="invoiceType" type="radio" value="退往来款" class="formSpan transform" style="width:15%;margin-left:10px;" onclick="changeInvoiceType(3)"/>
											<span style="float:left;margin-left:5px;">退往来款</span>
										</td>
										<td>价款:<input id="twMoney" name="twMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="smalltoBIG(this.value,'twBig')" disabled>元&nbsp;&nbsp;大写金额:<span class="transform" id="twBig" style="margin-left:5px;">零元整</span></td>
									</tr>
									</table>
									<table  border="1" bordercolor="#E0E0E0" style="border-collapse:collapse;width: 675px;margin-left: 20px;">
										<tr>
											<td width="40%">
												<div style="float;left;width:100%;text-align: center; text-indent: 0px;">
													<p style="width:44%;height:30px;margin:0px;padding:0px;text-align:center;line-height:30px;float: left;border-right:#eeeeee 1px solid;"><strong>含税价格差异</strong></p>
													<input id="priceDifference" name="priceDifference" type="text" class="inputWithoutBorder transform" style="width: 55%;float: left;height:30px;">
												</div>
												<div style="float;left;width:100%;text-align: center; text-indent: 0px;border-top:#eeeeee 1px solid;float: left;">
													<p style="width:44%;height:30px;margin:0px;padding:0px;text-align:center;line-height:30px;float: left;border-right:#eeeeee 1px solid;"><strong>期间</strong></p>	
													<input id="period" name="period" type="text" class="inputWithoutBorder transform" style="width: 55%;float: left;height:30px;">
												</div>
											</td>
											<td width="60%">
												<div style="float;left;width:20%;text-align: center; text-indent: 0px;border-right:#eeeeee 1px solid;float: left;height: 61px;">
													<span style="float:left;margin-left:10px;line-height:60px;height:25px;"><strong>差异说明</strong></span>
												</div>
												<textarea id="differenceExplain" name="differenceExplain" class="inputWithoutBorder transform"  style="width: 320px;height: 60px;max-width: 320px;max-height: 60px;min-height: 60px;min-width: 320px;"></textarea>
											</td>
									</tr>
									</table>
									<table  border="1" bordercolor="#E0E0E0" style="border-collapse:collapse;width: 675px;margin-left: 20px;">
									<tr>
										<td width="13%">发票类型</td>
										<td width="87%"></td>
									</tr>
									<tr>
										<td>
											<input name="realityinvoiceType" type="radio" value="专票" class="formSpan transform" style="width:15%;margin-left:10px;" checked onclick="changerealityInvoiceType(1)"/>
											<span style="float:left;margin-left:5px;">专票</span>
										</td>
										<td>价款:<input id="realityzMoney" name="realityzMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="zpRealityChange();" style="width: 40px;">元;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;税款:<input id="realityzTax" name="realityzTax" type="text" class="inputWithoutBorder transform" onkeyup="zpRealityChange();" style="width: 40px;">元;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价税合计:<input id="realityzTotal" name="realityzTotal" type="text" class="inputWithoutBorder transform" onkeyup="smalltoBIG(this.value,'realityzBig')" style="width: 40px;">元&nbsp;&nbsp;大写金额:<span class="transform" id="realityzBig" style="margin-left:5px;">零元整</span></td>
									</tr>
									<tr>
										<td>
											<input name="realityinvoiceType" type="radio" value="非专票" class="formSpan transform" style="width:15%;margin-left:10px;" onclick="changerealityInvoiceType(2)"/>
											<span style="float:left;margin-left:5px;">非专票</span>
										</td>
										<td>价款:<input id="realitynzMoney" name="realitynzMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="smalltoBIG(this.value,'realitynzBig')" disabled>元&nbsp;&nbsp;大写金额:<span  class="transform" id="realitynzBig" style="margin-left:5px;">零元整</span></td>
									</tr>
									<tr>
										<td>
											<input name="realityinvoiceType" type="radio" value="退往来款" class="formSpan transform" style="width:15%;margin-left:10px;" onclick="changerealityInvoiceType(3)"/>
											<span style="float:left;margin-left:5px;">退往来款</span>
										</td>
										<td>价款:<input id="realitytwMoney" name="realitytwMoney" type="text" class="inputWithoutBorder transform" onblur="changePrice()" onkeyup="smalltoBIG(this.value,'realitytwBig')" disabled>元&nbsp;&nbsp;大写金额:<span class="transform" id="realitytwBig" style="margin-left:5px;">零元整</span></td>
									</tr>
									</table>
								<div class="form-group formDiv" style="float:left;margin-top:15px;">
									<label class="labelForInput">所属期间:</label>
									<input id="pDate" name="pDate" class="formSpan transform " style="width: 77%;"/>
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
				        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="saveDiv(4);">提交</button>
								<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">返回</button>
			            	</div>
						 </div>
						 <input id="parentFSId" name="parentFSId" class="formSpan transform" type="text" style="display:none;">
						 <input id="parentDeptId" name="parentDeptId" class="formSpan transform" type="text" style="display:none;">
                  </div>
              </div>
         </div>
     </div>
     
    <div class="modal inmodal" id="selFinanceSubjectModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">选择项目</h4>
				</div>
				<div class="modal-body">
					<div class="panel-body ztree" id="financeSubjectTree" style="overflow: auto;"></div>
					<div class="form-group" style="margin-top:20px;">
						<label>报销金额</label>
						<input id="reimbursementAmount" name=""reimbursementAmount"" type="text" placeholder="报销金额" class="form-control" onfocus="getBalance();">
					</div>
					<div class="form-group" style="margin-top:20px;">
						<span id="reimbursementInfo"></span>
					</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="addFinanceSubject()">确定</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<input id="selOneSubId" name="selOneSubId" type="text" style="display:none;">
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
<script type="text/javascript" src="${ctx }/static/js/progress/financialBillsForm.js"></script>
</body>
</html>