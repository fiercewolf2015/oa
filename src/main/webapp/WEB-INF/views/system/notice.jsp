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
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deleteNotice()"><i class="fa fa-bitbucket"></i>批量删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
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
    
  	<div class="modal inmodal" id="addNoticeModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增通知</h4>
				</div>
				<div class="modal-body" id="addNoticeDiv">
						<div class="form-group"><label>通知标题</label> <input id="addNoticeNameForLook" name="addNoticeNameForLook" type="text" placeholder="通知标题" class="form-control" required></div>
						<div class="form-group"><label>通知内容</label> 
							<textarea id="remarkForLook" name ="remarkForLook" placeholder="通知内容" style="max-width:100%;" class="form-control"></textarea>
						</div>
						<div class="form-group" style="height:550px;">
						<form id="fileForm" action="${ctx}/workflow/uploadFile" method="post">
			                <input id="file" name="file" type="file" size="30" style="float:left;width:270px;">
			                <input id="fileName" name="fileName" type="text" hidden='true'>
			             </form>
			        	 	<button type="button" class="btn btn-primary btn-sm" style="margin-right:10px;" onclick="uploadFile();">上传附件</button>
							<button type="button" class="btn btn-default btn-sm" onclick="delFile();">删除附件</button>
			                 <div class="col-lg-12" style="margin-top: 5px;">
			                <div class="ibox float-e-margins">
			                    <div class="ibox-title">
			                        <h5>选择人员</h5 >
			                        <div class="ibox-tools">
			                            <a class="collapse-link">
			                                <i class="fa fa-chevron-up"></i>
			                            </a>
			                        </div>
			                    </div>
			                    <div class="ibox-content" style="height:420px;">
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
									             <div class="col-sm-2">
									                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="addStaff()"><i class="fa fa-search"></i>增加员工</button>
									            </div>
									        </div>
									       <div class="row" style="width:635px;float:left;">
									            <div class="ibox">
									                <div class="ibox-content">
														<div class="jqGrid_wrapper">
									                         <table id="table_staff"></table>
									                         <div id="pager_staff"></div>
								                     	</div>
									                </div>
									            </div>
							        		 </div>
							        		  <div id="selStaffDiv" style="width:200px;float:left;height:290px;margin-top:15px;margin-left:20px;overflow:auto;">
							        		    <span style="float:left;width:100%;">已选择的员工(双击删除):</span>
							        		 </div>
			                    </div>
			                </div>
			                </div>
		       			</div>
		       			<form id="fileDiv" method="POST" enctype="multipart/form-data" action="${ctx}/notice/add">
		            		<input type="hidden" id="staffIds" name="staffIds">
		            		<input type="hidden" id="addNoticeName" name="addNoticeName">
		            		<input type="hidden" id="remark" name="remark">
		            		<input type="hidden" id="fileNameForTwo" name="fileNameForTwo">
						</form> 
					</div>
					<div class="modal-footer">
					    <button type="button" class="btn btn-primary btn-sm" onclick="uploadFileTwo()">保存</button>
						<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
					</div>
			   </div>
			</div>
		</div>
	</div>
    <script type="text/javascript" src="${ctx }/static/js/system/notice.js"></script>
</body>
</html>