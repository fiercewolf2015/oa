<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>数据备份</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchnoticeDiv" style="text-align: center;">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="noticeTitle">数据备份</label>
                </div>
            </div>
            <button type="button" class="btn btn-lg btn-danger btnWithoutLabel" onclick=""><i class="fa fa-database"></i>一键备份</button>
            <button type="button" class="btn btn-lg btn-primary btnWithoutLabel"  onclick=""><i class="fa fa-refresh"></i>一键恢复</button>
        </div>
    </div>
    <div class="modal inmodal" id="importSqlModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">一键恢复</h4>
				</div>
				<div class="modal-body" style="height:150px;">
					<div class="form-group" style="height:50px;">
						<form id="fileDiv" method="POST" enctype="multipart/form-data" action="${ctx }/staff/importStaff">
			              <div class="form-group">
			                  <label class="control-label">上传文件</label>
			                  <div><input id="file" name="file" type="file" size="30" style="float:left;width:270px;"></div>
		       			  </div>
						</form>
					</div>
					<div class="form-group">
					<span id="errorMse" style="color:red;"></span>
					</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="uploadFile();">确定</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
    <script type="text/javascript" src="${ctx }/static/js/system/backup.js"></script>
</body>
</html>