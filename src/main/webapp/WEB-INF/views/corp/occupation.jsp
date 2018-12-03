<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${ctx }/static/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${ctx }/static/ztree/css/zTreeStyle.css" type="text/css">
    <title>职务管理</title>
    <style>
    .btnWithoutLabel{
    	margin-top:0px;
      }
    </style>
</head>
<body>
    <div class="row" style="padding:45px 20px 20px 20px">
         <div class="col-lg-12">
              <div class="row">
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" style="height:50px;">
                              			已有职务
                        			 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="del();"><i class="fa fa-bitbucket"></i>删除</button>
					                 <button class="btn btn-sm btn-primary btnWithoutLabel" type="button" onclick="edit();"><i class="fa fa-paste"></i>编辑</button>
					                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="add();"><i class="fa fa-plus"></i>新增下级职务</button>
                          </div>
                          <div class="panel-body" id="leftPanel">
                          		<div class="panel-body ztree" id="occupationTree" style="overflow: auto;height: 330px;"></div>
                          </div>
                      </div>
                  </div>
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" id="editOccupationPanel" style="height:50px;">
                          </div>
                          <div class="panel-body"  id="rightPanel">
                             <div id="occupationInfoDiv" class="col-xs-6" style="float:left;margin-top:10px;">
											<div class="form-group">
												<label>职务名称</label>
												<input id="occupationName" style="width:50%;" name="occupationName" type="text" placeholder="职务名称" class="form-control" disabled='true' required>
											</div>
											<div class="form-group">
												<label>职务代码</label>
												<input id="occupationNo" style="width:50%;" name="occupationNo" type="text" placeholder="职务代码" class="form-control" disabled='true' required>
											</div>
											<div class="form-group">
												<label>职务层级</label>
												<input id="occupationLevel" style="width:50%;" name="occupationLevel" type="text" placeholder="职务层级" class="form-control" disabled='true'>
											</div>
											<div class="form-group">
												<label>备注</label> 
												<textarea id="remark" name ="remark" placeholder="备注" style="max-width:100%;" class="form-control" disabled='true'></textarea>
											</div>
										</div>
										<div class="col-xs-6" style="float:left;margin-top:270px;">
											<div class="form-group" id="saveAndCancelDiv" style="float:right;" hidden="true">
								        	 	<button type="button" class="btn btn-primary btn-sm" onclick="save();">保存</button>
												<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">取消</button>
							            	</div>
							            	<div class="form-group" id="saveAndCancelDivForEdit" style="float:right;" hidden="true">
								        	 	<button type="button" class="btn btn-primary btn-sm" onclick="saveForEdit();">保存</button>
												<button type="button" class="btn btn-default btn-sm" onclick="clearInput();">取消</button>
							            	</div>
										</div>
                          </div>
                      </div>
                  </div>
              </div>
         </div>
     </div>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/js/corp/occupation.js"></script>
</body>
</html>