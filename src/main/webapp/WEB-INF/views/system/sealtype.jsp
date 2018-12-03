<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>印章类型设置</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchsealTypeDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="sealTypeName">印章类型</label>
                    <input type="text" id="sealTypeName" name="sealTypeName" placeholder="印章类型" class="form-control">
                </div>
            </div>
            <div class="col-sm-4">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-6">
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deletesealType()"><i class="fa fa-bitbucket"></i>批量删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_sealType"></table>
                         <div id="pager_sealType"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="addsealTypeModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增印章类型</h4>
				</div>
				<div class="modal-body" id="addsealTypeDiv">
						<div class="form-group"><label>印章类型名称</label> <input id="addSealTypeName" name="addSealTypeName" type="text" placeholder="印章类型名称" class="form-control" required></div>
						<div class="form-group"><label>备注</label> 
							<textarea id="remark" name ="remark" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
						</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="savesealType()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="editsealTypeModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">编辑印章类型</h4>
				</div>
				<div class="modal-body" id="editSealTypeDiv">
						<div class="form-group"><label>印章类型名称</label> <input id="editsealTypeName" name="editsealTypeName" type="text" placeholder="印章类型名称" class="form-control" required></div>
						<div class="form-group"><label>备注</label> 
							<textarea id="editRemark" name ="editRemark" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
						</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveEditsealType()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<input id="selsealTypeId" name="selsealTypeId" type="text" style="display:none;">
    <script type="text/javascript" src="${ctx }/static/js/system/sealtype.js"></script>
</body>
</html>