<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理</title>
</head>
<body>
	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchForm">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="actionType">按操作类型查询</label>
                    <input type="text" id="actionType" name="actionType" class="form-control" placeholder="按操作类型查询" value="" >
                </div>
            </div>
             <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="bizType">按业务类型查询</label>
                    <input type="text" id="bizType" name="bizType" class="form-control" placeholder="按业务类型查询" value="" >
                </div>
            </div>
             <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="bizContent">按业务信息查询</label>
                    <input type="text" id="bizContent" name="bizContent" class="form-control" placeholder="按业务信息查询" value="" >
                </div>
            </div>
             <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="beginCreatetime">操作开始时间</label>
                    <input placeholder="开始时间" class="form-control" id="beginCreatetime" name="beginCreatetime" 
								onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})" type="text" readonly="readonly"/> 
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="beginCreatetime">操作结束时间</label>
                    <input placeholder="结束时间" class="form-control" id="endCreatetime" name="endCreatetime" 
								onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})" type="text" readonly="readonly"/>
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary btnSearch" id="search_btn"><i class="fa fa-search"></i>搜索</button>
            </div>
        </div>
   </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="list"></table>
								<div id="pager"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>

	<script type="text/javascript" src="${ctx}/static/js/system/bizactionlog.js"></script>
</body>
</html>