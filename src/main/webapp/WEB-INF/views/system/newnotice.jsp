<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>通知管理</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchnoticeDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="noticeTitle">通知标题</label>
                    <input type="text" id="noticeTitle" name="noticeTitle" placeholder="通知标题" class="form-control">
                </div>
            </div>
            <div class="col-sm-4">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-6">
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" onclick="changeReadState()"><i class="fa fa-plus"></i>标为已读</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_notice"></table>
                         <div id="pager_notice"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx }/static/js/system/newnotice.js"></script>
</body>
</html>