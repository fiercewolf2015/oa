<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>员工管理</title>
    <style>
    	.tableInput{
    		width:200px;
    	}
    	.labelTd{
    		width:100px;
    		text-align:right;
    	}
    	.tabTableLeft{
    		border-collapse:separate;  
    	   border-spacing:10px;
    	   width:285px;
    	   float:left;
    	   margin-left:15px;
    	}
    	.tabTableRight{
    		border-collapse:separate;  
    	   border-spacing:10px;
    	   width:325px;
    	   float:left;
    	   margin-left:10px;
    	}
    </style>
	<link href="${ctx }/static/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body>
 	<div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchStaffDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="searchStaffName">员工姓名</label>
                    <input type="text" id="searchStaffName" name="searchStaffName" placeholder="员工姓名" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="searchStaffNo">员工编号</label>
                    <input type="text" id="searchStaffNo" name="searchStaffNo" placeholder="员工编号" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            </div>
            <div class="col-sm-6">
           		 <button type="button" class="btn btn-default btn-sm btnWithoutLabel" onclick="syncData();">同步数据</button>
           		 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel" onclick="showImportModal()"><i class="fa fa-plus"></i>导入员工</button>
                 <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deleteStaff()"><i class="fa fa-bitbucket"></i>删除</button>
                 <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_staff"></table>
                         <div id="pager_staff"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
  	<div class="modal inmodal" id="addStaffModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:690px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="spanText">新增员工</h4>
				</div>
				<div class="modal-body" id="addStaffDiv" style="padding:0">
					<input type="text" id="selStaffId" name="selStaffId" style="display:none;">
					 <div class="row">
	                <div class="col-lg-12">
	                    <div class="tabs-container">
	                        <ul class="nav nav-tabs">
	                            <li id="workinfohref" class="active"><a data-toggle="tab" href="#work-info">工作信息</a></li>
	                            <li id="personinfohref" class=""><a data-toggle="tab" href="#person-info">个人信息</a></li>
	                        </ul>
	                        <div class="tab-content">
	                            <div id="work-info" class="tab-pane active">
	                                <div class="panel-body">
                               			<table class="tabTableLeft" style="background-color:rgba(7, 11, 13, 0.16);">
                       						<tr>
                          						<td class="labelTd">姓名</td>
                          						<td>
                          							<input type="text" id="staffName" name="staffName" class="form-control tableInput" required>
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">员工编号</td>
                          						<td>
                          							<input type="text" id="staffNo" name="staffNo" class="form-control tableInput">
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">职务</td>
                          						<td>
	                           						<input id="occupation"  readonly="readonly" onBlur="hideTreeView('occupationTree')" onClick="showTreeView('occupationTree')"  class="form-control"  name="occupation" required/>
	                           						<div class="row panel-body ztree" id="occupationTree" style="overflow: auto;position:fixed;width:200px;display:none;padding:0;height:200px;"></div>
															<input type="hidden" name="occupationIdHidden" id="occupationIdHidden" value = "-1"/>
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">部门</td>
                          						<td>
                          						   <input id="department"  readonly="readonly"  onBlur="hideTreeView('departmentTree')" onClick="showTreeView('departmentTree')"  class="form-control"  name="department" required/>
                           							<div class="row panel-body ztree" id="departmentTree" style="overflow: auto;position:absolute;width:200px;display:none;padding:0;height:200px;"></div>
															<input type="hidden" name="departmentIdHidden" id="departmentIdHidden" value = "-1"/>
												</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">岗位</td>
                          						<td>
                            					 <select id="posts" name="posts" class="chosen-select tableInput" multiple required></select>
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">手机号</td>
                          						<td>
                          							<input type="text" id="mobilePhone" name="mobilePhone" class="form-control tableInput">
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">固定电话</td>
                          						<td>
                          							<input type="text" id="fixedPhone" name="fixedPhone" class="form-control tableInput">
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">邮箱</td>
                          						<td>
                          							<input type="text" id="email" name="email" class="form-control tableInput">
                          						</td>
                       						</tr>
                               			</table>
                               			<table class="tabTableRight" style="background-color:rgba(7, 11, 13, 0.16);">
                       						<tr>
                        						<td class="labelTd">职称职级</td>
                        						<td>
                        							<select id="title" name="title" class="form-control tableInput" required></select>
                        						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">工作所属项目</td>
                          						<td><input type="text"  id="jobProject" name="jobProject" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">參加工作时间</td>
                          						<td><input type="text"  id="jobTime" name="jobTime" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">进入本企业时间</td>
                          						<td><input type="text"  id="enterpriseTime" name="enterpriseTime" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                       						</tr>
                               			</table>
	                                </div>
	                            </div>
	                            <div id="person-info" class="tab-pane">
	                                <div class="panel-body">
                                      <table class="tabTableLeft" style="background-color:rgba(7, 11, 13, 0.16);">
                        						<tr>
	                           						<td class="labelTd">性別</td>
	                           						<td>
	                           							<select id="gender" name="gender" class="form-control tableInput">
	                           							   <option value="">请选择</option>
	                           								<option value="1">男</option>
	                           								<option value="2">女</option>
	                           							</select>
	                           						</td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">民族</td>
	                           						<td>
                       									<select id="nation" name="nation" class="form-control tableInput"></select>
	                           						</td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">生日</td>
	                           						<td><input type="text"  id="birthday" name="birthday" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">籍贯</td>
	                           						<td><input type="text"  id="nativePlace" name="nativePlace" class="form-control tableInput"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">血型</td>
	                           						<td>
	                           							<select id="blood" name="blood" class="form-control tableInput">
	                           							   <option value="">请选择</option>
	                           								<option value="A型">A型</option>
	                           								<option value="B型">B型</option>
	                           								<option value="AB型">AB型</option>
	                           								<option value="O型">O型</option>
	                           							</select>
	                           						</td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">身份证号</td>
	                           						<td><input type="text"  id="personalId" name="personalId" class="form-control tableInput"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">婚姻状况</td>
	                           						<td>
                           								<select id="marriage" name="marriage" class="form-control tableInput">
	                           								<option value="">请选择</option>
	                           								<option value="1">已婚</option>
	                           								<option value="2">未婚</option>
	                           								<option value="3">离异</option>
	                           							</select>
                           							</td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">政治面貌</td>
	                           						<td>
	                           							<select id="political" name="political" class="form-control tableInput"></select>
	                           						</td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">健康状况</td>
	                           						<td><input type="text"  id="health" name="health" class="form-control tableInput"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">入党时间</td>
	                           						<td><input type="text"  id="partyDate" name="partyDate" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">家庭住址</td>
	                           						<td><input type="text"  id="homeAddress" name="homeAddress" class="form-control tableInput"></td>
                        						</tr>
                        						<tr>
	                           						<td class="labelTd">家庭电话</td>
	                           						<td><input type="text"  id="homePhoneNum" name="homePhoneNum" class="form-control tableInput"></td>
                        						</tr>
                           				</table>
                               			<table class="tabTableRight" style="background-color:rgba(7, 11, 13, 0.16);">
                       						<tr>
                          						<td class="labelTd">户口所在地</td>
                          						<td><input type="text"  id="accountAddress" name="accountAddress" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">档案所在地</td>
                          						<td><input type="text"  id="archivesAddress" name="archivesAddress" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">原学历</td>
                          						<td>
                          							<select id="education" name="education" class="form-control tableInput"></select>
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">原所学专业</td>
                          						<td><input type="text"  id="major" name="major" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">最高学历</td>
                          						<td>
                          							<select id="highestDegree" name="highestDegree" class="form-control tableInput"></select>
                          						</td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">最高学历专业</td>
                          						<td><input type="text"  id="highestDegreeMajor" name="highestDegreeMajor" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">学位</td>
                          						<td><input type="text"  id="degree" name="degree" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">取证时间</td>
                          						<td><input type="text"  id="forensicTime" name="forensicTime" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">证件编号</td>
                          						<td><input type="text"  id="certificateNumber" name="certificateNumber" class="form-control tableInput"></td>
                       						</tr>
                       						
                       						<tr>
                          						<td class="labelTd">其他证书名称</td>
                          						<td><input type="text"  id="otherCertificate" name="otherCertificate" class="form-control tableInput"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">其他取证时间</td>
                          						<td><input type="text"  id="otherForensicTime" name="otherForensicTime" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})"></td>
                       						</tr>
                       						<tr>
                          						<td class="labelTd">其他证件编号</td>
                          						<td><input type="text" id="otherCertificateNumber" name="otherCertificateNumber"  class="form-control tableInput"></td>
                       						</tr>
                               			</table>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveStaff()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="editOccupationModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">编辑职务</h4>
				</div>
				<div class="modal-body" id="editOccupationDiv">
						<div class="form-group"><label>职务名称</label> <input id="editOccupationName" name="editOccupationName" type="text" placeholder="职务名称" class="form-control" required></div>
						<div class="form-group"><label>职务代码</label> <input id="editOccupationNo" name="editOccupationNo" type="text" placeholder="职务代码" class="form-control" required></div>
						<div class="form-group"><label>备注</label> 
							<textarea id="editRemark" name ="editRemark" placeholder="备注" style="max-width:100%;" class="form-control"></textarea>
						</div>
			   </div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveEditOccupation()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal inmodal" id="importStaffModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量导入员工</h4>
				</div>
				<div class="modal-body" style="height:150px;">
					<div class="form-group">
						<a href="${ctx}/static/template.xls" style="cursor: pointer;">点击下载导入文件模板 template.xls</a>
					</div>
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
	
<script type="text/javascript" src="${ctx }/static/js/jquery-form.js"></script>
<script type="text/javascript" src="${ctx }/static/js/plugins/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/staff/staff.js"></script>
</body>
</html>