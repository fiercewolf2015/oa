<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>资料修改</title>
	
<style>
.formSpan{
	width:225px;
}
</style>
</head>
<body>
<div class="ibox-content border-bottom">
		<div class="row">
          <div class="col-lg-12" style="max-width:800px;">
              <div class="tabs-container">
                  <ul class="nav nav-tabs">
                      <li id="accountinfohref" class="active"><a data-toggle="tab" href="#accoutn-info">帐号设置</a></li>
                      <li id="personinfohref" class=""><a data-toggle="tab" href="#person-info">员工信息</a></li>
                  </ul>
                  <div class="tab-content">
                      <div id="accoutn-info" class="tab-pane active">
                          <div class="panel-body">
		                          <form id="profileDiv" method="POST" enctype="multipart/form-data" action="${ctx }/profile">
				                          <div class="oneRowDiv" style="height:100px;">
														<div class="form-group formDiv">
															<label class="formLabelForInput">昵称:</label>
															<input id="name" name="name" type="text" class="form-control formSpan">
														</div>
														<div class="form-group formDiv">
															<label class="formLabelForInput">头像:</label>
															<input id="file" name="file" type="file" size="30" class="form-control formSpan">
														</div>
												　</div>
												 <div class="oneRowDiv" style="height:100px;">
														<div class="form-group formDiv">
															<label class="formLabelForInput">新密码:</label>
															<input type="password" id="password" name="password" placeholder="如无需修改密码，保持该项为空" class="form-control formSpan">
														</div>
														<div class="form-group formDiv">
															<label class="formLabelForInput">确认密码:</label>
															 <input type="password" id="confirmPassword" name="confirmPassword" placeholder="如无需修改密码，保持该项为空" class="form-control formSpan">
														</div>
												　</div>
												 <div class="twoRowDiv" style="height:30px;">
												  	 <button type="button" class="btn btn-primary btn-sm" style="float:right;margin-right:10px;" onclick="save();">保存</button>
												　</div>
                        			</form>
                          </div>
                      </div>
                      <div id="person-info" class="tab-pane">
                          <input type="text" id="staffId" name="staffId" style="display:none;">
                          <div class="panel-body">
                          		<div class="oneRowDiv" style="height:710px;">
												<div class="form-group formDiv">
													<label class="formLabel">姓名:</label>
													<span id="staffName" name="staffName" class="formSpan"></span>
												</div>
												<div class="form-group formDiv">
													<label class="formLabel">员工编号:</label>
													<span id="staffNo" name="staffNo" class="formSpan"></span>
												</div>
												<div class="form-group formDiv">
													<label class="formLabel">职务:</label>
													<span id="occupation" name="occupation" class="formSpan"></span>
												</div>
												<div class="form-group formDiv">
													<label class="formLabel">部门:</label>
													<span id="department" name="department" class="formSpan"></span>
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">參加工作时间:</label>
													<input type="text"  id="jobTime" name="jobTime" class="form-control formSpan" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
												</div>
												<div class="form-group formDiv">
														<label class="formLabelForInput">民族:</label>
														<select id="nation" name="nation" class="form-control formSpan"></select>
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">生日:</label>
													<input type="text"  id="birthday" name="birthday" class="form-control formSpan" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">籍贯:</label>
													<input type="text"  id="nativePlace" name="nativePlace" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">血型:</label>
													<input type="text"  id="blood" name="blood" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">身份证号:</label>
													<input type="text"  id="personalId" name="personalId" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">婚姻状况:</label>
													<select id="marriage" name="marriage" class="form-control formSpan">
                     								<option value="">请选择</option>
                     								<option value="1">已婚</option>
                     								<option value="2">未婚</option>
                     							</select>
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">政治面貌:</label>
													<select id="political" name="political" class="form-control formSpan"></select>
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">健康状况:</label>
													<input type="text"  id="health" name="health" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">入党时间:</label>
													<input type="text"  id="partyDate" name="partyDate" class="form-control formSpan" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">家庭住址:</label>
													<input type="text"  id="homeAddress" name="homeAddress" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">家庭电话:</label>
													<input type="text"  id="homePhoneNum" name="homePhoneNum" class="form-control formSpan">
												</div>
												<div class="form-group formDiv">
													<label class="formLabelForInput">其他证件编号:</label>
													<input type="text" id="otherCertificateNumber" name="otherCertificateNumber"  class="form-control formSpan">
												</div>
									　  </div>
										<div class="oneRowDiv" style="height:710px;">
													<div class="form-group formDiv">
														<label class="formLabel">职称职级:</label>
														<span id="title" name="title" class="formSpan"></span>
													</div>
													<div class="form-group formDiv">
														<label class="formLabel">工作所属项目:</label>
														<span id="jobProject" name="jobProject" class="formSpan"></span>
													</div>
													<div class="form-group formDiv">
														<label class="formLabel">进入本企业时间:</label>
														<span id="enterpriseTime" name="enterpriseTime" class="formSpan"></span>
													</div>
													<div class="form-group formDiv">
														<label class="formLabel">岗位:</label>
														<span id="posts" name="posts" class="formSpan"></span>
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">性別:</label>
														<select id="gender" name="gender" class="form-control formSpan">
                        							   <option value="">请选择</option>
                        								<option value="1">男</option>
                        								<option value="2">女</option>
                        							</select>
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">户口所在地:</label>
														<input type="text"  id="accountAddress" name="accountAddress" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">档案所在地:</label>
														<input type="text"  id="archivesAddress" name="archivesAddress" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">原学历:</label>
														<select id="education" name="education" class="form-control formSpan"></select>
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">原所学专业:</label>
														<input type="text"  id="major" name="major" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">最高学历:</label>
														<select id="highestDegree" name="highestDegree" class="form-control formSpan"></select>
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">最高学历专业:</label>
														<input type="text"  id="highestDegreeMajor" name="highestDegreeMajor" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">学位:</label>
														<input type="text"  id="degree" name="degree" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">取证时间:</label>
														<input type="text"  id="forensicTime" name="forensicTime" class="form-control formSpan" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">证件编号:</label>
														<input type="text"  id="certificateNumber" name="certificateNumber" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">其他证书名称:</label>
														<input type="text"  id="otherCertificate" name="otherCertificate" class="form-control formSpan">
													</div>
													<div class="form-group formDiv">
														<label class="formLabelForInput">其他取证时间:</label>
														<input type="text"  id="otherForensicTime" name="otherForensicTime" class="form-control formSpan" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
													</div>
										　 </div>
										  <div class="twoRowDiv" style="height:30px;">
										  	 <button type="button" class="btn btn-primary btn-sm" style="float:right;margin-right:10px;" onclick="saveStaffInfo();">保存</button>
										 　</div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
    </div>
</div>
<script type="text/javascript" src="${ctx }/static/js/profile.js"></script>
<script type="text/javascript">
	var user_name = "<shiro:principal property='name'/>";
	$('#name').val(user_name);
</script>
</body>
</html>
