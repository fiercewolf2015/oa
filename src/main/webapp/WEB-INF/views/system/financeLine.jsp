<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>超预算百分比设置</title>
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row">
            <div class="col-sm-12">
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deletefinanceLine()"><i class="fa fa-bitbucket"></i>删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_financeLine"></table>
                         <div id="pager_financeLine"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="addFinanceLineModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增超预算百分比</h4>
				</div>
				<div class="modal-body" id="addFinanceLineDiv">
						<div class="form-group"><label>全年预算总额＜截止到当月累计实际发生额＜全年预算总额的</label> <input id="firstLine" name="firstLine" type="text" placeholder="%" class="form-control" required></div>
						<div class="form-group"><label>全年预算总额＜截止到当月累计实际发生额＜全年预算总额的</label> <input id="secondLine" name="secondLine" type="text" placeholder="%" class="form-control" required></div>
						<div class="form-group"><label>截止到当月累计实际发生额＞全年预算总额的</label> <input id="threeLine" name="threeLine" type="text" placeholder="%" class="form-control" required></div>
				</div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveFinanceLine()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
    <script type="text/javascript" src="${ctx }/static/js/system/financeLine.js"></script>
</body>
</html>