<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>预算编制模板</title>
</head>
<body>
	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchBudgeModelDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="modelName">模板名称</label>
                    <input type="text" id="modelName" name="modelName" placeholder="模板名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-4">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-6">
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
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
    
  	<div class="modal inmodal" id="addBudgetingModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增模板</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>模板名称</label>
						<input id="addName" name="addName" type="text" placeholder="模板名称" class="form-control">
					</div>
					<div class="form-group">
						<label>备注</label> 
						<textarea id="addRemark" name ="addRemark" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
					</div>
					<div class="panel-body ztree" id="financeSubjectTree" style="overflow: auto;"></div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveModel()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<input id="selModelId" name="selModelId" type="text" style="display:none;">
<script type="text/javascript" src="${ctx }/static/js/budget/budgetModel.js"></script>
</body>
</html>