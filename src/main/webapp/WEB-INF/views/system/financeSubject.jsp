<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>会计科目设置</title>
    <style>
	    .btnWithoutLabel{
	    	margin-top:0px;
	      }
    </style>
    <link href="${ctx }/static/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body>
     <div class="row" style="padding:45px 20px 20px 20px">
         <div class="col-lg-12">
              <div class="row">
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" style="height:50px;">
                              			已有会计科目
                        			 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="del();"><i class="fa fa-bitbucket"></i>删除</button>
					                 <button class="btn btn-sm btn-primary btnWithoutLabel" type="button" onclick="edit();"><i class="fa fa-paste"></i>编辑</button>
					                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="add();"><i class="fa fa-plus"></i>新增下级会计科目</button>
                          </div>
                          <div class="panel-body" id="leftPanel"  style="height:600px;overflow-y: auto;">
                          		<div class="panel-body ztree" id="financeSubjectTree" style="overflow: auto;"></div>
                          </div>
                      </div>
                  </div>
                  <div class="col-lg-6">
                      <div class="panel panel-default">
                          <div class="panel-heading" id="editFinanceSubjectPanel" style="height:50px;">
                          </div>
                          <div class="panel-body"  id="rightPanel">
                             	<div id="financeSubjectInfoDiv" class="col-xs-6" style="float:left;margin-top:10px;">
											<div class="form-group">
												<label>会计科目名称</label>
												<input id="financeSubjectName" style="width:50%;" name="financeSubjectName" type="text" placeholder="会计科目名称" class="form-control" disabled='true' required>
											</div>
											<div class="form-group">
												<label>层级</label>
												<input id="financeSubjectLevel" style="width:50%;" name="financeSubjectLevel" type="text" placeholder="层级" class="form-control" disabled='true'>
											</div>
											<div class="form-group">
												<label>类型</label>
												<select id="type" name="type" class="form-control" disabled='true' style="width:50%;">
													<option value="1">收入</option>
													<option value="2">成本</option>
													<option value="3">利润</option>
												</select>
											</div>
											<div class="form-group">
												<label style="dispaly:block;width:100%;">岗位</label>
												<select id="posts" name="posts" class="chosen-select form-control" multiple style="width:50%;" disabled='true' required></select>
											</div>
											<div class="form-group">
												<label>备注</label> 
												<textarea id="remarks" name ="remarks" placeholder="备注" style="max-width:100%;" class="form-control" disabled='true'></textarea>
											</div>
										</div>
										<div class="col-xs-6" style="float:left;">
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
<script type="text/javascript" src="${ctx }/static/js/jquery-form.js"></script>
<script type="text/javascript" src="${ctx }/static/js/plugins/chosen/chosen.jquery.js"></script>     
<script type="text/javascript" src="${ctx }/static/js/system/financeSubject.js"></script>
</body>
</html>