<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link href="${ctx }/static/css/plugins/chosen/chosen.css" rel="stylesheet">
   <title>待办流程</title>
    <style>
    	.inputWithoutBorder{
    		border:none;
    		width:60px;
    	}
    </style>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchWorkflowInstanceDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="workflowType">流程类型</label>
                     <select id="workflowType" name="workflowType" class="form-control"></select>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="companyForSearch">分公司</label>
                    <input type="text" id="companyForSearch" name="companyForSearch" placeholder="分公司" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="project">项目名称</label>
                    <input type="text" id="project" name="project" placeholder="项目名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="applyStaff">申请人</label>
                    <input type="text" id="applyStaff" name="applyStaff" placeholder="申请人" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="applyStaffDept">申请人部门</label>
                    <input type="text" id="applyStaffDept" name="applyStaffDept" placeholder="申请人部门" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="applyDateStart">申请日期开始</label>
                    <input type="text"  id="applyDateStart" name="applyDateStart" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="applyDateEnd">申请日期结束</label>
                    <input type="text"  id="applyDateEnd" name="applyDateEnd" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_workflowInstance"></table>
                         <div id="pager_workflowInstance"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="instanceInfoModal" tabindex="-1" role="dialog" aria-hidden="true"></div>
	
	<div class="modal inmodal" id="instanceInfoForResaveModal" tabindex="-1" role="dialog" aria-hidden="true"></div>
	
	<div class="modal inmodal" id="SelectModal" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog" style="width:740px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="hideAddAssetsModel('SelectModal');" aria-hidden="true">&times;</button>
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
					<button type="button" class="btn btn-default btn-sm" onclick="hideAddAssetsModel('SelectModal');">取消</button>
				</div>
	   		</div>
		</div>
	</div>
	
	
  <div class="modal inmodal" id="SelectModalForAssets" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog" style="width:740px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="hideAddAssetsModel('SelectModalForAssets');" aria-hidden="true">&times;</button>
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
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">单价:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产原值:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">累计折旧:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产净值:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">开始使用年限:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="addInfo();">确定</button>
				<button type="button" class="btn btn-default btn-sm" onclick="hideAddAssetsModel('SelectModalForAssets');">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="EditselectModal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog" style="width:740px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="hideAddAssetsModel('EditselectModal');" aria-hidden="true">&times;</button>
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
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">单价:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产原值:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">累计折旧:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">资产净值:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
	                <div class="form-group col-sm-6" style="margin-top:10px;float:left;height: 30px;">
		                   	<label for="modalNo" class="control-label" style="float:left;width:85px;">开始使用年限:</label>
		                   	<input type="text" class="formSpan"/>
	                </div>
        		</div>
           	</div>
           	<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="addEditInfo();">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="hideAddAssetsModel('EditselectModal');">取消</button>
			</div>
   		</div>
	</div>
</div>
<div class="modal inmodal" id="financialRejectModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择驳回人员</h4>
			</div>
			<div class="modal-body" id="editLeaveTypeDiv">
					<div class="form-group formDiv">
						<label class="labelForInput">驳回人员:</label>
						<select id="financialRejectStaff" name="financialRejectStaff" class="form-control formSpan"></select>
					</div>
		   </div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-primary btn-sm" onclick="realFinancialReject()">确定</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
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
<input type="text" id="selInstanceId" style="display:none;">
<script type="text/javascript" src="${ctx }/static/js/plugins/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/progress/todolist.js"></script>
</body>
</html>