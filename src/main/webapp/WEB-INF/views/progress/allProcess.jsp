<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>所有流程</title>
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
    <div class="modal inmodal" id="instanceInfoModal" tabindex="-1" role="dialog" aria-hidden="true">
	</div>
<script type="text/javascript" src="${ctx }/static/js/progress/allProcess.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery.PrintArea.js"></script>
</body>
</html>