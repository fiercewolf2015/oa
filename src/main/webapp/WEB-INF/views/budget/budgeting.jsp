<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>预算编制</title>
    <style>
	     .noBorderInput{
	      	width:100px;
	      	border:none;
	      }
	     .budgetTh{
	      text-align:center;
	      border:solid 2px #add9c0;
	      }
	     .tdForInput{
	      	border:solid 2px #add9c0;
	      }
    </style>
</head>
<body>
    <div class="ibox-content m-b-sm border-bottom">
	    <div class="row">
	        <div class="col-lg-12">
	        		<div class="tabs-container">
	                   <ul class="nav nav-tabs">
	                       <li class="active"><a data-toggle="tab" href="#budgeting-info">预算编制</a></li>
	                       <li class=""><a id="historyId" data-toggle="tab" href="#budgetingHistory-info" onclick="returnHistoryWidth(this.id);">预算编制历史</a></li>
	                   </ul>
	                   <div class="tab-content">
	                       <div id="budgeting-info" class="tab-pane active">
	                           <div class="panel-body">
			                           <div class="row" id="searchBudgetingDiv">
									            <!-- <div class="col-sm-2">
									                <div class="form-group">
									                    <label class="control-label" for="financeApprovalName">预算编制名称</label>
									                    <input type="text" id="financeApprovalName" name="financeApprovalName" placeholder="预算编制名称" class="form-control">
									                </div>
									            </div>
									            <div class="col-sm-4">
									                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
									            </div> -->
									            <div class="col-sm-12">
									                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" style="margin-top:0px;" onclick="deleteBudgeting()"><i class="fa fa-bitbucket"></i>删除</button>
									                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" style="margin-top:0px;"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
									                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" style="margin-top:0px;" onclick="startWorkFlow();">提交申请</button>
									            </div>
									        </div>
	                     				   <div class="ibox" style="margin-top:20px;">
								                <div class="ibox-content">
															<div class="jqGrid_wrapper">
								                         <table id="table_budgeting"></table>
								                         <div id="pager_budgeting"></div>
								                     </div>
								                </div>
								            </div>
	                           </div>
	                       </div>
	                      <div id="budgetingHistory-info" class="tab-pane">
	                           <div class="panel-body">
			                           <!-- <div class="row" id="searchBudgetingHistoyDiv">
									            <div class="col-sm-2">
									                <div class="form-group">
									                    <label class="control-label" for="financeApprovalNameHistory">预算编制名称</label>
									                    <input type="text" id="financeApprovalNameHistory" name="financeApprovalNameHistory" placeholder="预算编制名称" class="form-control">
									                </div>
									            </div>
									            <div class="col-sm-4">
									                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerHistoryList()"><i class="fa fa-search"></i>搜索</button>
									            </div>
									        </div> -->
	                     				   <div class="ibox">
								                <div class="ibox-content">
															<div class="jqGrid_wrapper">
								                         <table id="table_budgeting_history"></table>
								                         <div id="pager_budgeting_history"></div>
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
    
  	<div class="modal inmodal" id="addBudgetingModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div id="firstStep" class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增预算编制</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>名称</label>
						<input id="addName" name="addName" type="text" placeholder="名称" class="form-control">
					</div>
					<div class="form-group">
						<label>年份</label>
						<input id="addYear" name="addYear" type="text" placeholder="年份" class="form-control" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy'})">
					</div>
					<div class="ibox-content m-b-sm border-bottom">
						<div class="panel-body ztree" id="departmentTree" style="overflow: auto;"></div>
					</div>
					<div class="ibox-content m-b-sm border-bottom">
				        <div class="row" id="searchBudgeModelDiv">
				            <div class="col-sm-6">
				                <div class="form-group">
				                    <label class="control-label" for="modelName">模板名称</label>
				                    <input type="text" id="modelName" name="modelName" placeholder="模板名称" class="form-control">
				                </div>
				            </div>
				            <div class="col-sm-4">
				                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerListForModel()"><i class="fa fa-search"></i>搜索</button>
				            </div>
				            <div class="col-lg-12">
					            <div class="ibox">
					                <div class="ibox-content">
												<div class="jqGrid_wrapper">
					                         <table id="table_budgetModel"></table>
					                         <div id="pager_budgetModel"></div>
					                     </div>
					                </div>
					            </div>
					        </div>
				        </div>
				    </div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="nextStep()">下一步</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
		
		<div id="secondStep" class="modal-dialog" style="display:none;width:1535px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 id="nameAndYear" class="modal-title"></h4>
				</div>
				<div class="modal-body">
                     <table>
                         <thead>
                         <tr>
                             <th class="budgetTh">项目</th>
                             <th class="budgetTh">1月</th>
                             <th class="budgetTh">2月</th>
                             <th class="budgetTh">3月</th>
                             <th class="budgetTh">4月</th>
                             <th class="budgetTh">5月</th>
                             <th class="budgetTh">6月</th>
                             <th class="budgetTh">7月</th>
                             <th class="budgetTh">8月</th>
                             <th class="budgetTh">9月</th>
                             <th class="budgetTh">10月</th>
                             <th class="budgetTh">11月</th>
                             <th class="budgetTh">12月</th>
                             <th class="budgetTh">合计</th>
                         </tr>
                         </thead>
                         <tbody id="subjectContent">
                         </tbody>
                     </table>
			   </div>
				<div class="modal-footer">
				   <button type="button" class="btn btn-primary btn-sm" onclick="saveBudgeting()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="editOneSubjectModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:1535px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">编辑预算编制科目</h4>
				</div>
				<div class="modal-body">
						 <table>
                         <thead>
                         <tr>
                             <th class="budgetTh">项目</th>
                             <th class="budgetTh">1月</th>
                             <th class="budgetTh">2月</th>
                             <th class="budgetTh">3月</th>
                             <th class="budgetTh">4月</th>
                             <th class="budgetTh">5月</th>
                             <th class="budgetTh">6月</th>
                             <th class="budgetTh">7月</th>
                             <th class="budgetTh">8月</th>
                             <th class="budgetTh">9月</th>
                             <th class="budgetTh">10月</th>
                             <th class="budgetTh">11月</th>
                             <th class="budgetTh">12月</th>
                             <th class="budgetTh">合计</th>
                         </tr>
                         </thead>
                         <tbody id="editOneSubjectDiv">
                        		 <tr>
                        		 	<td class="tdForInput" style="width:140px;">
                        		 		<div id="subjectName"></div>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceJanuary" name="subjectFinanceJanuary" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceFebruary" name="subjectFinanceFebruary" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceMarch" name="subjectFinanceMarch" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceApril" name="subjectFinanceApril" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceMay" name="subjectFinanceMay" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceJune" name="subjectFinanceJune" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceJuly" name="subjectFinanceJuly" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceAugust" name="subjectFinanceAugust" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceSeptember" name="subjectFinanceSeptember" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceOctober" name="subjectFinanceOctober" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceNovember" name="subjectFinanceNovember" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input id="subjectFinanceDecember" name="subjectFinanceDecember" type="text" class="noBorderInput oneForEdit" onchange="changeSubjectForEdit();" required>
                        		 	</td>
                        		 	<td class="tdForInput">
                        		 		<input type="text" id="totalForEdit" name="totalForEdit" class="noBorderInput" style="background-color:#C4C4C4;" value="0" readonly="readonly">
                        		 	</td>
                        		 </tr>
                         </tbody>
                     </table>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveEditOneSubject()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<input id="selOneSubId" name="selOneSubId" type="text" style="display:none;">
<script type="text/javascript" src="${ctx }/static/js/budget/budgeting.js"></script>
</body>
</html>