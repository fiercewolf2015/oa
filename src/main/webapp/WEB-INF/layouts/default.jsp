<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx }/static/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx }/static/css/animate.css" rel="stylesheet">
<link href="${ctx }/static/css/style.css" rel="stylesheet">
<link href="${ctx }/static/css/font.css" rel="stylesheet">
<link href="${ctx }/static/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx }/static/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx }/static/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx }/static/css/webuploader.css" type="text/css">
<link rel="stylesheet" href="${ctx }/static/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${ctx }/static/ztree/css/zTreeStyle.css" type="text/css">
<!-- Sweet Alert -->
<link href="${ctx }/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="${ctx }/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<script src="${ctx }/static/js/jquery-2.1.1.js"></script>
<script src="${ctx }/static/js/bootstrap.min.js"></script>
<script src="${ctx }/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx }/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx }/static/js/inspinia.js"></script>
<script src="${ctx }/static/js/plugins/pace/pace.min.js"></script>
<script src="${ctx }/static/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${ctx }/static/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx }/static/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${ctx }/static/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery-form.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx }/static/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="${ctx}/static/js/cal_new/WdatePicker.js" type="text/javascript"></script>
<!-- DROPZONE -->
<script src="${ctx }/static/js/plugins/dropzone/dropzone.js"></script>
<script src="${ctx }/static/js/util.js"></script>
<script type="text/javascript">
	function loadingAjax(){
		$('.ajaxModal').css('display', 'block');
		$('.spiner-example').css('padding-top', $('.ajaxModal').height()/2);
		if(!(navigator.userAgent.indexOf('MSIE 9.0') > 0 || navigator.userAgent.indexOf('MSIE 10.0') > 0)) {
			if(document.all){
				$('.ajaxModal').css('top', '40%');
				$('.ajaxModal').css('left', '47%');
			}
		}
	};
	function hideAjax(){
		$('.ajaxModal').css('display','none');
	};
	function onGridLoadComplete(gridId, idArray){
		if(idArray == '' || idArray == undefined || idArray == null ||idArray.length == 0)
			return;
		for (var i = 0; i < idArray.length; i++)
			$(gridId).setSelection(idArray[i], true);
	};
	function arrayIndexOf(array, value){
		for (var i = 0; i < array.length; i++) {
			if(value == array[i])
				return i;
		}
		return -1;
	};
	function onGridSelectRow(idArray, id, checkStatus){
		if(checkStatus) {
			var pos = arrayIndexOf(idArray, id);
			if(pos == -1)
				idArray.push(id);
		}
		else {
			var pos = arrayIndexOf(idArray, id);
			idArray.splice(pos, 1);
		}
	};
	function onGridSelectAll(idArray, ids, checkStatus){
		if(checkStatus) {
			for (var i = 0; i < ids.length; i++) {
				var pos = arrayIndexOf(idArray, ids[i]);
				if(pos == -1)
					idArray.push(ids[i]);
			}
		}
	};
	var util = new util();
	var context = '${ctx}';
	var $ = jQuery.noConflict();
	
	function checkSaveDiv(){
		var result = true;
		$('#saveDiv .need').each(function(){
			if($(this).val() == ''||$(this).val() == -1){
				$(this).css('border','1px solid red');
				result = false;			
			}else
				$(this).css('border','');
		});
		return result;
	};
	var startInstanceOptions = 
	{
			url		:	'/workflow/startInstance'							
		,	mtype	:	'POST'						
		,	postData	:	{}						
		,	success :	function(data,st, xhr){		
			hideAjax();
			if (data === 1) {
				swal({
	                title: "保存成功!",
	                type: "success"
		        },function(){
					window.location.reload();
		        });
			}
			else
				swal({
	                title: "保存失败!",
	                type: "error"
			    });
			}
		,	error	:	function(xhr,st,err){		
			swal({
	            title: "保存失败!",
	            type: "error"
		    });
		}
	}
	var startFileInstanceOptions = 
	{
			url		:	'/workflow/startFileInstance'							
		,	mtype	:	'POST'						
		,	postData	:	{}						
		,	success :	function(data,st, xhr){		
			hideAjax();
			if (data === 1) {
				swal({
	                title: "保存成功!",
	                type: "success"
		        },function(){
					window.location.reload();
		        });
			}
			else
				swal({
	                title: "保存失败!",
	                type: "error"
			    });
			}
		,	error	:	function(xhr,st,err){		
			swal({
	            title: "保存失败!",
	            type: "error"
		    });
		}
	}
	function uploadFile(){
		//var fileArr = $('#file').val().split('\\');
		var fileName = '';
		var objArr = $("input[name='file']");
		for (var i = 0; i < objArr.length; i++) {
			if(objArr[i].value != ''){
				var nameArr = objArr[i].value.split('\\');
				fileName += "##" + new Date().getTime()+'_'+nameArr[nameArr.length-1];
			}
		}
		//if(!(fileArr == '' || fileArr.length == 0 || fileArr == undefined))
		//	fileName = new Date().getTime()+'_'+fileArr[fileArr.length-1];
		$('#fileName').val(fileName);
		if(fileName != ''){
			$('#fileForm').ajaxSubmit(function(data){
				if(data == 1){
					swal({
		                title: "保存成功!",
		                type: "success"
			        });
				}else if(data == - 99){
					swal({
		                title: "附件大小超过30M请重新上传!",
		                type: "error"
				    });
				}else{
					swal({
		                title: "保存失败!",
		                type: "error"
				    });
					$('#fileName').val('');
				}
			});
		}else{
			swal({
                title: "请选择附件!",
                type: "error"
		    });
		}
	}
	function delFile(){
		$('#file').val('');
		$('#fileName').val('');
	}
	function uploadFileForContract(){
		var fileArr = $('#newfile').val().split('\\');
		var fileName = "";
		if(!(fileArr == '' || fileArr.length == 0 || fileArr == undefined))
			fileName = new Date().getTime()+'_'+fileArr[fileArr.length-1];
		$('#newfileName').val(fileName);
		if(fileName != ''){
			$('#fileForm').ajaxSubmit(function(data){
				if(data == 1){
					swal({
		                title: "保存成功!",
		                type: "success"
			        });
				}else if(data == - 99){
					swal({
		                title: "附件大小超过30M请重新上传!",
		                type: "error"
				    });
				}else{
					swal({
		                title: "保存失败!",
		                type: "error"
				    });
					$('#newfileName').val('');
				}
			});
		}else{
			swal({
                title: "请选择附件!",
                type: "error"
		    });
		}
	}
	var fileHtmlObj = null;
	function delFileForContract(htmlId,wfiId,fileName){
		fileHtmlObj = $('#'+htmlId);
		deleteFileForContractOptions.postData = {
				wfiId	:	wfiId,
				fileName		:	fileName
		};
	    util.commAjax(deleteFileForContractOptions);
	}
	var deleteFileForContractOptions = 
	{
			url		:	'/workflow/deleteFileForContract'							
		,	mtype	:	'POST'						
		,	postData	:	{}						
		,	success :	function(data,st, xhr){		
			hideAjax();
			if (data === 1) {
				$(fileHtmlObj).prev().remove();
				$(fileHtmlObj).remove();
			}
			else
				swal({
	                title: "删除失败!",
	                type: "error"
			    });
			}
		,	error	:	function(xhr,st,err){		
			swal({
	            title: "删除失败!",
	            type: "error"
		    });
		}
	}
	
	function saveDiv(flag){
		var result = checkSaveDiv();
		if(!result){
			swal({
	            title: "有必填项为空",
	            type: "error"
		    });
			return false;
		}
//	var formId = $('#formId').text();
	//	if(formId == ''){
	//	swal({
	   //         title: "暂无流程,不能提交，请联系管理员",
	    //        type: "error"
		//    });
		//	return false;
		//}
		if(flag==2){
			if($('.leaveInfo').length==0){
				swal({
		            title: "请假信息为空",
		            type: "error"
			    });
				$('#leaveInfo').css('border','1px solid red');
				return false;
			}
		}else if(flag==15){
			if($('.postChangeInfo').length==0){
				swal({
		            title: "异动信息为空",
		            type: "error"
			    });
				$('#postChangeInfo').css('border','1px solid red');
				return false;
			}
		}else if(flag == 6){
			var objArr = $("input[name*='useType']");
			var k = 0;
			for (var i = 0; i < objArr.length; i++) {
				if(objArr[i].checked == true)
					k++;
			}
			if(k ==0){
				swal({
		            title: "请选择印章类型",
		            type: "error"
			    });
				return false;
			}
		}
		var obj = util.findFormDataForNew('#saveDiv');
		/* var fileArr = $('#file').val().split('\\');
		var fileName = "";
		if(!(fileArr == '' || fileArr.length == 0 || fileArr == undefined))
			fileName = new Date().getTime()+'_'+fileArr[fileArr.length-1]; */
		var fileArr = $('#file').val().split('\\');
		var fileName = $('#fileName').val();
		if(!(fileArr == '' || fileArr.length == 0 || fileArr == undefined) && (fileName == '' || fileName == null)){
			swal({
	            title: "附件未上传，请先上传附件",
	            type: "error"
		    });
			return false;
		}
		if(flag==2){
			$('.leaveInfo').each(function(){
				var formData = {};
				$(this).children('label').each(function(){
					formData[$(this).attr('name')] = $(this).text();
				});
				if(obj['leaveInfo']==undefined || obj['leaveInfo'] == '')
					obj['leaveInfo'] = JSON.stringify(formData)
				else
					obj['leaveInfo']+="#####"+JSON.stringify(formData);
			});
		}else if(flag==15){
			$('.postChangeInfo').each(function(){
				var formData = {};
				$(this).children('label').each(function(){
					formData[$(this).attr('name')] = $(this).text();
				});
				if(obj['postChangeInfo']==undefined || obj['postChangeInfo'] == '')
					obj['postChangeInfo'] = JSON.stringify(formData)
				else
					obj['postChangeInfo']+="#####"+JSON.stringify(formData);
			});
		}else if(flag==7){
			$('.assetsInfo').each(function(){
				var formData = {};
				$(this).children('label').each(function(){
					formData[$(this).attr('name')] = $(this).text();
				});
				if(obj['assetsInfo']==undefined || obj['assetsInfo'] == '')
					obj['assetsInfo'] = JSON.stringify(formData)
				else
					obj['assetsInfo']+="#####"+JSON.stringify(formData);
			});
		}
		var workflowId = $('#processLiIdForHidden').val().split('_')[1];
		if(flag == 3){
			startFileInstanceOptions.postData = {
					params 			: 	JSON.stringify(obj),
					workflowId	:	workflowId,
					subStaffId		:	null,
					fileName		:	fileName,
					managerStaffId	:	$('#ApprovalStaff').val()
			};
			loadingAjax();
			/* if(fileName != '')
				$('#fileForm').ajaxSubmit(); */
		    util.commAjax(startFileInstanceOptions);
		}else{
		startInstanceOptions.postData = {
				params 			: 	JSON.stringify(obj),
				workflowId	:	workflowId,
				subStaffId		:	null,
				fileName		:	fileName,
				managerStaffId	:	$('#ApprovalStaff').val()
		};
		loadingAjax();
		/* if(fileName != '')
			$('#fileForm').ajaxSubmit(); */
	    util.commAjax(startInstanceOptions);
		}
	};
	function clearInput(){
		window.location.reload();
	}
</script>
<sitemesh:head/>
<style type="text/css">
	.ajaxModal {
	    display:    none;
	    position:   fixed;
	    z-index:    100000000;
	    top:        0;
	    left:       0;
	    height:     100%;
	    width:      100%;
	    background: rgba( 255, 255, 255, .5 ) 50% 50%;
	}
</style>
</head>
<body>
	<div class="row">
		<%@ include file="/WEB-INF/layouts/leftbar.jsp"%>
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div id="wrapper">
        <div id="page-wrapper" class="gray-bg">
				<sitemesh:body/>
        <%--  <%@ include file="/WEB-INF/layouts/footer.jsp"%> --%>
        </div>
      </div>
		
	</div>
	
	<div class="ibox-content ajaxModal">
     <div class="spiner-example">
      <div class="sk-spinner sk-spinner-chasing-dots">
          <div class="sk-dot1"></div>
          <div class="sk-dot2"></div>
      </div>
     </div>
	</div>
	<input type="text" id="processLiIdForHidden" value="${liId}" style="display:none;">
<script type="text/javascript">
var progressUrlObj = {
		1:'overtimeForm',
		2:'leaveForm',
		3:'fileAccessForm',
		4:'financialBillsForm',
		5:'partyFileForm',
		6:'useSealForm',
		7:'fixedAssetsForm',
		8:'contractWillForm',
		9:'goOutForm',
		10:'salaryForm',
		11:'addOrRemoveStaffForm',
		12:'expandReward',
		13:'supplierForm',
		14:'companyVIForm',
		15:'postChangeForm',
		16:'financialBillsFormOut'
}
var userGetAllWorkFlowOptions = 
{
		url		:	'/staff/getAllWorkflow'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data != null && data.length > 0) {
				var li = $('<li onclick="expandOrNot(this);"><a href="#">-发起流程<span class="fa arrow"></span></a></li>');
				$('#instanceTodolistLi').before(li);
				var ifForm= window.location.href.indexOf("allProcessLi_");
				if(ifForm > 0)
					var childUl = $('<ul class="nav nav-third-level collapse in"></ul>');
				else
					var childUl = $('<ul class="nav nav-third-level collapse"></ul>');
				for(var i=0;i<data.length;i++){
					var oneWorkFlow = data[i];
					childUl.append('<li id="allProcessLi_'+oneWorkFlow.id+'"><a href="${ctx }/workflow/progress/'+oneWorkFlow.id+'/'+progressUrlObj[oneWorkFlow.workflowType]+'/allProcessLi_'+oneWorkFlow.id+'">-'+oneWorkFlow.name+'</a></li>')
				}
				li.append(childUl);
				var tempArr = window.location.href.split('/');
				var processLiId =tempArr[tempArr.length-1];
				if(ifForm > 0)
					util.initSelectMenu(processLiId);
			}
		}
	,	error	:	function(xhr,st,err){		
		swal({
            title: "服务器当前遇到问题!",
            type: "error"
	    });
	}
};
var getUnreadNum = 
{
		url		:	'/newnotice/getUnreadNum'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data  > 0) {
				document.getElementById('nonewNotice').style.display = 'none';
				document.getElementById('newNotice').style.display = '';
				document.getElementById('noticeNum').innerHTML = data;
			}else{
				document.getElementById('nonewNotice').style.display = '';
				document.getElementById('newNotice').style.display = 'none';
			}
		}
	,	error	:	function(xhr,st,err){		
		swal({
            title: "服务器当前遇到问题!",
            type: "error"
	    });
	}
};
var expandOrNot = function(obj){
	var childUl = $(obj).find('ul');
	if(childUl.hasClass('in'))
		childUl.removeClass('in');
	else
		childUl.addClass('in');
};

$(function(){
	util.commAjax(userGetAllWorkFlowOptions);
	util.commAjax(getUnreadNum);
})
var addFile = function(){
	var obj  = $('#fileForm');
	if(obj[0].childElementCount == 1)
		$('#fileForm').append('<input id="file" name="file" type="file" size="30" style="float:left;width:30%;"><i class="fa fa-times" style="float:left;margin-top:5px;cursor: pointer;" title="删除" onclick="deleteFile(this)"></i>');
	else
		$('#fileForm').append('<input id="file" name="file" type="file" size="30" style="float:left;width:30%;margin-left:100px;"><i class="fa fa-times" style="float:left;margin-top:5px;cursor: pointer;" title="删除" onclick="deleteFile(this)"></i>');
}
var deleteFile = function(obj){
	$(obj).prev().remove();
	$(obj).remove();
}
var deleteFileForReject = function(obj){
	$(obj).prev().remove();
	$(obj).remove();
}
</script>
</body>
</html>