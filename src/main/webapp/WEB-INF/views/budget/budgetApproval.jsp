<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>预算审批</title>
</head>
<body>
	<div class="ibox-content m-b-sm border-bottom">
	    <div class="row">
	        <div class="col-lg-12">
	        		<div class="tabs-container">
	                   <ul class="nav nav-tabs">
	                       <li class="active"><a data-toggle="tab" href="#budgetApproval-info">预算编制审批</a></li>
	                       <li class=""><a data-toggle="tab" href="#simpleTable-info">简表</a></li>
	                   </ul>
	                   <div class="tab-content">
	                       <div id="budgetApproval-info" class="tab-pane active">
	                           <div class="panel-body">
			                           <div class="row" id="searchBudgetApprovalDiv">
									            <div class="col-sm-2">
									                <div class="form-group">
									                    <label class="control-label" for="financeApprovalName">预算编制名称</label>
									                    <input type="text" id="financeApprovalName" name="financeApprovalName" placeholder="预算编制名称" class="form-control">
									                </div>
									            </div>
									            <div class="col-sm-4">
									                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
									            </div>
									            <div class="col-sm-6">
									                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="reject();">驳回</button>
									                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" onclick="agree()">同意</button>
									            </div>
								          </div>
	                     				<div class="ibox">
							                <div class="ibox-content">
														<div class="jqGrid_wrapper">
							                         <table id="table_budgetApproval"></table>
							                         <div id="pager_budgetApproval"></div>
							                     </div>
							                </div>
							            </div>
	                           </div>
	                       </div>
	                       <div id="simpleTable-info" class="tab-pane">
	                           <div class="panel-body">
	                              	<!-- <div class="row" id="searchBudgetSimpleDiv">
									            <div class="col-sm-2">
									                <div class="form-group">
									                    <label class="control-label" for="departmentName">部门名称</label>
									                    <input type="text" id="departmentName" name="departmentName" placeholder="部门名称" class="form-control">
									                </div>
									            </div>
									            <div class="col-sm-4">
									                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerListForSimple()"><i class="fa fa-search"></i>搜索</button>
									            </div>
								          </div> -->
                         				<div class="ibox">
							                <div class="ibox-content">
														<div class="jqGrid_wrapper">
							                         <table id="table_budgetSimple"></table>
							                         <div id="pager_budgetSimple"></div>
							                     </div>
							                </div>
							            </div>
	                           </div>
	                       </div>
	                   </div>
	               </div>
	        </div>
	    </div>
    </div>
    
    <div class="modal inmodal" id="rejectModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">驳回意见</h4>
				</div>
				<div class="modal-body">
						<div class="form-group"><label>意见</label> 
							<textarea id="rejectReason" name ="rejectReason" placeholder="意见" style="max-width:100%;" class="form-control"></textarea>
						</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="realReject()">确定</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
    
<script type="text/javascript" src="${ctx }/static/js/budget/budgetApproval.js"></script>
</body>
</html>