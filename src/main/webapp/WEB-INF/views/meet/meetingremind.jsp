<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>会议提醒</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchMeetingDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="meetingTitle">会议主题</label>
                    <input type="text" id="meetingTitle" name="meetingTitle" placeholder="会议主题" class="form-control">
                </div>
            </div>	
            <div class="col-sm-1">
                <div class="form-group">
                	<label class="control-label" for="applyStaffName">发起人</label>
                    <input type="text" id="applyStaffName" name="applyStaffName" placeholder="发起人" class="form-control">
                </div>
            </div>
            <div class="col-sm-1">
                <div class="form-group">
                	<label class="control-label" for="applyDeptName">部门</label>
                    <input type="text" id="applyDeptName" name="applyDeptName" placeholder="部门" class="form-control">
                </div>
            </div>
            <div class="col-sm-1">
                <div class="form-group">
                	 <label class="control-label" for="meetingType">会议类型</label>
                     <select id="meetingType" name="meetingType" class="form-control tableInput">
					    <option value="-1">请选择</option>
						<option value="1">类型1</option>
						<option value="2">类型2</option>
					</select>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                	<label class="control-label" for="meetingDate">会议开始日期</label>
					<input type="text"  id="meetingBeginTime" name="meetingBeginTime" class="form-control tableInput"  placeholder="会议开始时间" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                	<label class="control-label" for="meetingDate">会议开始时间</label>
					<input type="text"  id="meetingEndTime" name="meetingEndTime" class="form-control tableInput"  placeholder="会议结束时间" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})">
                </div>
            </div>
            <div class="col-sm-2" style="float: right;">
            	 <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <input type="hidden" id="searchType" name="searchType" value = "2">
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_meeting"></table>
                         <div id="pager_meeting"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx }/static/js/meet/meetingremind.js"></script>
</body>
</html>