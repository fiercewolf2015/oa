var todolist = function(){};
todolist.gridId = "#table_workflowInstance";
todolist.pagerId = "#pager_workflowInstance";
todolist.searchDivInput = "#searchWorkflowInstanceDiv :input";
todolist.searchBtn = "#searchBtn";
todolist.params = 'params';

todolist.searchWorkflowTypeInputSel = '#workflowType';
todolist.InputSelDefault = '请选择';

todolist.financialRejectStaffInputSel = '#financialRejectStaff';

$(function(){
	util.initSelectMenu('instanceTodolistLi');
	$(todolist.gridId).jqGrid(todolist.gridOption);
	$(window).resize(function(){
		$(todolist.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(todolist.workflowtypesOptions);
});

todolist.workflowtypesOptions = 
{
		url		:	'/workflow/getAllWorkflowType'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		    util.initSelectArray(todolist.searchWorkflowTypeInputSel,data,todolist.InputSelDefault);
		}
}

var operationFormatter = function(cellvalue,rowObject){
	var ifreject = rowObject.ifreject;
	var type = rowObject.workFlow.workFlowType.id;
	if(ifreject == 0)
		return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showInstanceModal('+cellvalue+','+type+')">办理</button>';
	else
		return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showRevokeInstanceModal('+cellvalue+','+type+')">查看</button>'+
			'<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="revokeInstance('+cellvalue+')">撤销</button>';
}
var todolistIds = new Array();
todolist.gridOption = { 
		url: context+"/workflowInstance/todolist",
		postData: {'param':{}},
		colNames : ['操作','流程编号','流程名称','流程类别','申请人','申请人编号','申请人部门','申请日期','接受时间','创建时间','流程类别id'],
		colModel : [
		            {name : 'id', width : 70, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue,rowObject);}},
		            {name : 'instanceNum', width : 100, align : "center"},
		            {name : 'workFlow.name', width : 100, align : "center"},
		            {name : 'workFlow.workFlowType.name', width : 100, align : "center"},
		            {name : 'applyUser', width : 100, align : "center"},
		            {name : 'applyUserNo', width : 100, align : "center"},
		            {name : 'applyUserDeptName', width : 100, align : "center"},
		            {name : 'reserve20', width : 100, align : "center"},
		            {name : 'pointReceiveTime', width : 100, align : "center"},
		            {name : 'pointCreateTime', width : 100, align : "center"},
		            {name : 'workFlow.workFlowType.id', width : 100, align : "center",hidden:true}
		],
		caption : "待办流程列表",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: todolist.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(todolist.gridId, todolistIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(todolistIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(todolistIds, rowIds, status);
			else
				macIds = new Array();
		}
};

var searchPargerList = function(){
	util.searchListWithParams(todolist.gridId,todolist.searchDivInput,todolist.params);
};

var showInstanceModal = function(instanceId,type){
	$('#selInstanceId').val(instanceId);
	lookOrOperateForUtil = 1;
	workflowInstanceIdForUtil = instanceId;
	workFlowTypeForUtil = type;
	hasPrintForUtil = false;
	getResultForInstanceLook();
};

todolist.agreeWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/agree'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "操作成功!",
                type: "success"
		        },function(){
		        	util.hideModal('instanceInfoModal'); 
		        });
				searchPargerList();
			}else
				swal({
	                title: "操作失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
}

var agreeWorkflowInstance = function(){
	var ApprovalStaff = $('#ApprovalStaff').val();
	if(isEmpty(ApprovalStaff) && !$("#nextApprovalDiv").is(":hidden")){
		swal({
            title: "请选择审批人员",
            type: "error"
	    });
		return;
	}
	var approvalReason = $('#approvalReason').val();
	if(isEmpty(approvalReason)){
		swal({
            title: "请填写审批意见",
            type: "error"
	    });
		return;
	}
	var nextDeptStaffIdsArr = new Array();
	$('.nextDeptStaff').each(function(){
		nextDeptStaffIdsArr.push(this.id.split('_')[1]);
	})
	var fileName = "";
	if(workFlowTypeForUtil == 8){
		var fileName = $('#fileName').val();
		var fileObj = document.getElementById('file');
		if(fileObj != null && fileName == ''){
			swal({
	            title: "附件未上传，请先上传附件",
	            type: "error"
		    });
			return false;
		}
	}
	console.log(nextDeptStaffIdsArr);
	todolist.agreeWorkflowInstanceOptions.postData = {
		crossReason:approvalReason,
		managerStaffId:ApprovalStaff,
		instanceId:$('#selInstanceId').val(),
		nextDeptStaffIds:nextDeptStaffIdsArr.length == 0?null:nextDeptStaffIdsArr,
		fileName:fileName
	}
	util.commAjax(todolist.agreeWorkflowInstanceOptions);
};
var agreeFileWorkflowInstance = function(){
	var appNext = $("#nextApprovalSelect").val();
	var ApprovalStaff = $('#ApprovalStaff').val();
	var deptNames = '';
	var endInstance = null;
	if(appNext == '本部各部门经理'){
		for (var i = 1; i < 8; i++) {
			if(document.getElementById('selDept'+i).checked == true)
				deptNames += (',' + document.getElementById('selDept'+i).value);
		}
		if(deptNames == ''){
			swal({
				title: "请选择审批人员",
				type: "error"
			});
			return;
		}
	}else if(appNext == '流程结束'){
		endInstance = "流程结束";
	}else{
		if(isEmpty(ApprovalStaff) && !$("#nextApprovalDiv").is(":hidden")){
			swal({
				title: "请选择审批人员",
				type: "error"
			});
			return;
		}
	}
	var approvalReason = $('#approvalReason').val();
	if(isEmpty(approvalReason)){
		swal({
            title: "请填写审批意见",
            type: "error"
	    });
		return;
	}
	todolist.agreeFileWorkflowInstanceOptions.postData = {
		crossReason:approvalReason,
		managerStaffId:ApprovalStaff,
		deptNames:deptNames,
		instanceId:$('#selInstanceId').val(),
		endInstance:endInstance
	}
	util.commAjax(todolist.agreeFileWorkflowInstanceOptions);
};
todolist.agreeFileWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/agreeFile'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "操作成功!",
                type: "success"
		        },function(){
		        	util.hideModal('instanceInfoModal'); 
		        });
				searchPargerList();
			}else
				swal({
	                title: "操作失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
}
todolist.rejectWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/reject'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "操作成功!",
                type: "success"
		        },function(){
		        	util.hideModal('instanceInfoModal'); 
		        });
				searchPargerList();
			}else
				swal({
	                title: "操作失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
}

todolist.getAllRejectStaffFinancialOptions = 
{
		
		url		:	'/workflow/getAllrejectStaffForFinancialNotes'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data !=null) {
				 util.initSelectArray(todolist.financialRejectStaffInputSel,data,todolist.InputSelDefault);
				 util.showModal('financialRejectModal');
			}else
				swal({
	                title: "操作失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
}

var rejectWorkflowInstance = function(){
	var approvalReason = $('#approvalReason').val();
	if(isEmpty(approvalReason)){
		swal({
            title: "请填写审批意见",
            type: "error"
	    });
		return;
	}
	todolist.rejectWorkflowInstanceOptions.postData = {
			rejectReason:approvalReason,
			instanceId:$('#selInstanceId').val()
	}
	util.commAjax(todolist.rejectWorkflowInstanceOptions);
}

todolist.rejectWorkflowInstanceFinancialOptions = 
{
		
		url		:	'/workflowInstance/reject'							
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "操作成功!",
                type: "success"
		        },function(){
		        	util.hideModal('instanceInfoModal'); 
		        	util.hideModal('financialRejectModal'); 
		        });
				searchPargerList();
			}else
				swal({
	                title: "操作失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "操作失败!",
	            type: "error"
		    });
		}		
}

var rejectWorkflowInstanceForFinancial = function(){
	var approvalReason = $('#approvalReason').val();
	if(isEmpty(approvalReason)){
		swal({
            title: "请填写审批意见",
            type: "error"
	    });
		return;
	}
	todolist.getAllRejectStaffFinancialOptions.postData={
			wfId:$('#selInstanceId').val()
	}
	util.commAjax(todolist.getAllRejectStaffFinancialOptions);
}

var realFinancialReject = function(){
	var financialRejectStaff = $('#financialRejectStaff').val();
	if(financialRejectStaff <=0){
		swal({
            title: "请选择驳回人员",
            type: "error"
	    });
		return;
	}
	var approvalReason = $('#approvalReason').val();
	todolist.rejectWorkflowInstanceFinancialOptions.postData = {
			rejectReason:approvalReason,
			instanceId:$('#selInstanceId').val()
//			managerStaffId:financialRejectStaff
	}
	util.commAjax(todolist.rejectWorkflowInstanceFinancialOptions);
}

todolist.revokeWorkflowInstanceOptions = 
{
		
		url		:	'/workflowInstance/revoke'							
			,	mtype	:	'POST'						
				,	postData	:	{}							
,	success :	function(data,st, xhr){		
		if (data === 1) {
			swal({
				title: "操作成功!",
				type: "success"
			});
			searchPargerList();
		}else
			swal({
				title: "操作失败!",
				type: "error"
			});
	}	
,	error	:	function(xhr,st,err){		
		swal({
			title: "操作失败!",
			type: "error"
		});
	}		
}

var revokeInstance = function(instanceId){
	swal({
		title: "确定撤销该条流程?",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: "确定",
		cancelButtonText: "取消",
		closeOnConfirm: false,
		closeOnCancel: false },
		function (isConfirm) {
			if (isConfirm) {
				todolist.revokeWorkflowInstanceOptions.postData = {
						instanceId:instanceId
				}
				util.commAjax(todolist.revokeWorkflowInstanceOptions);
			} else {
				swal("已取消撤销", "", "error");
			}
		});
};

var modelObjForEdit = {
		1:"/overtimeFormModelForEdit.html",
		2:"/leaveFormModelForEdit.html",
		3:"/fileAccessFormModelForEdit.html",
		4:"/financialBillsFormModelForEdit.html",
		5:"/partyFileFormModelForEdit.html",
		6:"/useSealFormModelForEdit.html",
		7:"/fixedAssetsFormModelForEdit.html",
		8:"/contractWillFormModelForEdit.html",
		9:"/goOutFormModelForEdit.html",
		10:"/salaryFormModelForEdit.html",
		11:"/addOrRemoveStaffFormModelForEdit.html",
		12:"/expandRewardFormModelForEdit.html",
		13:"/supplierFormModelForEdit.html",
		14:"/companyVIFormModelForEdit.html",
		15:"/postChangeFormModelForEdit.html"
}

var showRevokeInstanceModal = function(instanceId,type){
	$('#selInstanceId').val(instanceId);
	$('#instanceInfoForResaveModal').empty();
	var url = "/oa/static/progressModelForEdit" + modelObjForEdit[type];
	$.ajax({ 
		async:false, 
		url : url, 
		success : function(result){ 
			$('#instanceInfoForResaveModal').append(result);
			if(type == 1){//加班
				util.commAjax(todolist.overtimetypesOptions);
			}else if(type == 2){
				util.commAjax(todolist.leaveTypesOptions);
			}else if(type==4){
				util.commAjax(todolist.budgetCommissionerOptions);
				util.commAjax(todolist.companyNameOptions);
			}else if(type == 6){
				util.commAjax(todolist.sealTypesOptions);
			}else if(type == 7 || type == 8){
				util.commAjax(todolist.companyNameOptions);
			}else{
				todolist.reSavelookInstanceInfoOptions.postData = {
						instanceId:$('#selInstanceId').val()
				}
				util.commAjax(todolist.reSavelookInstanceInfoOptions);
			}
		} 
	}); 
};
todolist.companyNameOptions = 
{
		url		:	'/department/loadSecondDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray("#company" ,data,InputSelDefaultForUtil);
          todolist.reSavelookInstanceInfoOptions.postData = {
					instanceId:$('#selInstanceId').val()
			 }
			 util.commAjax(todolist.reSavelookInstanceInfoOptions);
		}
}
todolist.deptNameOptions = 
{
		url		:	'/department/loadThreeDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray("#deptName" ,data,InputSelDefaultForUtil);
          $('#deptName').val($('#editDeptName').val());
		}
}
todolist.overtimetypesOptions = 
{
		url		:	'/overtimeType/getAllOvertimeTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(overtimeTypesInputSelForUtil,data,InputSelDefaultForUtil);
          todolist.reSavelookInstanceInfoOptions.postData = {
					instanceId:$('#selInstanceId').val()
			 }
			 util.commAjax(todolist.reSavelookInstanceInfoOptions);
		}
}

todolist.leaveTypesInputSel = '#leaveType';
todolist.leaveTypesOptions = 
{
		url		:	'/leaveType/getAllLeaveTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(todolist.leaveTypesInputSel ,data,InputSelDefaultForUtil);
          todolist.reSavelookInstanceInfoOptions.postData = {
					instanceId:$('#selInstanceId').val()
			 }
			 util.commAjax(todolist.reSavelookInstanceInfoOptions);
		}
}

todolist.budgetCommissionerInputSel = '#budgetPerson';
todolist.budgetCommissionerOptions = 
{
		url		:	'/budgetCommissioner/getAllBudgetCommissioners'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(todolist.budgetCommissionerInputSel ,data,InputSelDefaultForUtil);
          todolist.reSavelookInstanceInfoOptions.postData = {
					instanceId:$('#selInstanceId').val()
			 }
			 util.commAjax(todolist.reSavelookInstanceInfoOptions);
		}
}

todolist.sealTypesOptions = 
{
		url		:	'/sealType/getAllSealTypes'							
			,	mtype	:	'POST'						
			,	postData	:	{}						
			,	success :	function(data,st, xhr){		
				var div = $('#useTypeDiv');
				for (var i = 0; i < data.length; i++){
					if(i == 0)
						div.append('<div id="'+data[i].value+'div"  class="form-group formDiv" style="width:50%;"><input id="'+data[i].value+'" name="useType" type="checkbox" value="'+data[i].text+'" style="float:left;" class ="transform"/><label class="formLabel" style="width:50px;margin-left:3px;">'+data[i].text+'</label><label class="formLabel" style="width:50px;margin-left:3px;">次数:</label><input id="'+data[i].text+'"  name="'+data[i].text+'" type="text" class="transform" style="float:left;" ></div>')
					else
						div.append('<div id="'+data[i].value+'div"  class="form-group formDiv" style="width:50%;margin-left:100px;"><input id="'+data[i].value+'" name="useType" type="checkbox" value="'+data[i].text+'" style="float:left;" class ="transform"/><label class="formLabel" style="width:50px;margin-left:3px;">'+data[i].text+'</label><label class="formLabel" style="width:50px;margin-left:3px;">次数:</label><input id="'+data[i].text+'"  name="'+data[i].text+'" type="text" class="transform" style="float:left;" ></div>')
				}
					todolist.reSavelookInstanceInfoOptions.postData = {
							instanceId:$('#selInstanceId').val()
					 }
					 util.commAjax(todolist.reSavelookInstanceInfoOptions);
				}
}

todolist.reSavelookInstanceInfoOptions = 
{
		url		:	'/workflowInstance/getWorkflowInstanceInfo'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		    if(data == null){
		    	swal({
		            title: "服务器当前遇到问题!",
		            type: "error"
			    });
		    }else{
		    	var instanceInfo = data.dataInfo;
		    	var workflowType = data.workflowTypeId;
		    	var filePath = data.attachmentPath;
		    	var fileName = data.attachmentName;
		    	$('#fileOriginal').empty();
		    	if(isEmpty(filePath))
		    		$('#fileOriginal').text('无附件');
		    	else{
		    		var fileNameArr = fileName.split('##');
		    		var insId = $('#selInstanceId').val();
		    		for (var i = 0; i < fileNameArr.length; i++) {
	    				$('#fileOriginal').append('<a style="float:left;margin-left:5px;"href="'+context+"/static/file_temp/"+fileNameArr[i]+'">附件'+(i+1)+'</a> <i class="fa fa-times" id="fileI'+i+'" style="float:left;margin-top:2px;cursor: pointer;" title="删除" onclick="delFileForContract(this.id,'+insId+',\''+fileNameArr[i]+'\')"></i>');
					}
		    	}
		    	var instanceInfoObj = JSON.parse(instanceInfo);
		    	if(workflowType == 2){
		    		var leaveInfoArr = instanceInfoObj.leaveInfo.split('#####');
		    		for(var i=0;i<leaveInfoArr.length;i++){
		    			var oneObj = JSON.parse(leaveInfoArr[i]);
		    			var str = '<div class="leaveInfo"><label name="leaveSectionFrom" style="float:left;width:30%;">'+oneObj.leaveSectionFrom
						+'</label><label name="leaveSectionTo" style="float:left;width:30%;">'+oneObj.leaveSectionTo
						+'</label><label name="leaveTypeName" style="float:left;width:25%;">'+oneObj.leaveTypeName
						+'</label><button type="button" class="btn btn-primary btn-sm" style=width:9%;text-align: center;" onclick="$(this).parent().remove();">删除</button></div>';
		    			$('#leaveInfo').append(str);
		    		}
		    	}
		    	if(workflowType == 4){
		    		changeFormDiv('有合同报销');
		    	}
		    	if(workflowType == 4 || workflowType == 7 || workflowType == 8){
		    		$("#editWfId").val(data.wfId);
		    		setEditAppStaff(instanceInfoObj.deptName);
//		    		changeNo();
//		    		$("#contractNo2").val(instanceInfoObj['contractNo2']);
		    		$('#editDeptName').val(instanceInfoObj.deptName);
		    		todolist.deptNameOptions.postData = {
							pId:instanceInfoObj.company
					 }
		          util.commAjax(todolist.deptNameOptions);
		    	}
		    	if(workflowType == 7){
		    		util.commAjax(todolist.loadDepartmentTreeNodesForAssetsOptions);
		    		if(instanceInfoObj.assetsInfo != undefined && instanceInfoObj.assetsInfo != null){
		    			var assetsInfoArr = instanceInfoObj.assetsInfo.split('#####');
		    			for(var i=0;i<assetsInfoArr.length;i++){
		    				var oneObj = JSON.parse(assetsInfoArr[i]);
		    				var str = '<div class="assetsInfo" style="margin-top:5px;">'
		    					+'<label name="oneNo" style="width:10%;text-align: center;">'+oneObj.oneNo
		    					+'</label><label name="oneName" style="width:10%;text-align: center;">'+oneObj.oneName
		    					+'</label><label name="oneType" style="width:10%;text-align: center;">'+oneObj.oneType
		    					+'</label><label name="oneCnt" style="width:10%;text-align: center;">'+oneObj.oneCnt
		    					+'</label><label name="onePrice" style="width:10%;text-align: center;">'+oneObj.onePrice
		    					+'</label><label name="oneOldPrice" style="width:10%;text-align: center;">'+oneObj.oneOldPrice
		    					+'</label><label name="oneReduce" style="width:10%;text-align: center;">'+oneObj.oneReduce
		    					+'</label><label name="oneLeft" style="width:10%;text-align: center;">'+oneObj.oneLeft
		    					+'</label><label name="oneYear" style="width:10%;text-align: center;">'+oneObj.oneYear
		    					+'</label><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="editInfo(this);">编辑</button><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="cancelInfo(this);">删除</button></div>';
		    				$('#assetsInfo').append(str);
		    			}
		    		}
		    	}
		    	if(workflowType == 15){
		    		var postChangeInfo = instanceInfoObj.postChangeInfo.split('#####');
		    		for(var i=0;i<postChangeInfo.length;i++){
		    			var oneObj = JSON.parse(postChangeInfo[i]);
		    			var str = '<div class="postChangeInfo" style="margin-top:5px;">'
		    				+'<label name="changeStaffName" style="width:10%;text-align: center;">'+oneObj.changeStaffName
		    				+'</label><label name="changeStaffNo" style="width:10%;text-align: center;">'+oneObj['changeStaffNo']
		    				+'</label><label name="oldDepartmentName" style="width:15%;text-align: center;">'+oneObj['oldDepartmentName']
		    				+'</label><label name="oldPostNames" style="width:20%;text-align: center;">'+oneObj['oldPostNames']
		    				+'</label><label name="newDepartmentName" style="width:16%;text-align: center;">'+oneObj['newDepartmentName']
		    				+'</label><label name="newPostNames" style="width:19%;text-align: center;">'+oneObj['newPostNames']
		    				+'</label><button type="button" class="btn btn-primary btn-sm" style=width:10%;text-align: center;" onclick="$(this).parent().remove();">删除</button></div>';
		    			$('#postChangeInfo').append(str);
		    		}
		    	}
		    	$('.transform').each(function(){
		    		if($(this).is('span') || $(this).is('label'))
		    			$(this).text(instanceInfoObj[this.id]);
		    		else{
		    			if(this.type == 'checkbox' || this.type == 'radio'){
		    				if(instanceInfoObj[this.name] != undefined && instanceInfoObj[this.name] != null){
		    					var checkboxArr = instanceInfoObj[this.name].split(',');
		    					for(var i=0;i<checkboxArr.length;i++){
		    						if(this.value == checkboxArr[i])
		    							this.checked=true;
		    					}
		    				}
		    			}else{
		    				$(this).val(instanceInfoObj[this.name]);
		    			}
		    		}
		    	})
		    	$('#formId').text(data.formId);
		    	if(workflowType == 8){
		    		showOtherDiv(instanceInfoObj['paymentTimeSlot']);
		    		showProjectOtherDiv(instanceInfoObj['projectOperationType']);
		    		changeNo();
		    		$("#contractNo2").val(instanceInfoObj['contractNo2']);
		    	}
//		    	if(workflowType == 6){
//		    		var useTypeArr = instanceInfoObj.useType.split(',');
//		    		var div = $('#useTypeDiv');
//					for (var i = 0; i < useTypeArr.length; i++){
//						var num = instanceInfoObj[useTypeArr[i]] == undefined ? 0 : instanceInfoObj[useTypeArr[i]];
//						div.append('<span id="useType" name="useType" class="formSpan transform">'+useTypeArr[i]+': '+num+'次</span>')
//					}
//		    	}
		    	var allPointInfo = data.allPointInfo;
		    	$('#taskTbodyShow').empty();
		    	if(allPointInfo !=null && allPointInfo.length >0){
		    		for(var i=0;i<allPointInfo.length;i++){
		    			var state = '审批通过';
		    			if(allPointInfo[i].approvalState == 1)
		    				state = '驳回';
		    			else if(allPointInfo[i].approvalState == -1)
		    				state = '申请';
		    			$('#taskTbodyShow').append('<tr><td title="'+allPointInfo[i].pointName+'" style="height: 30px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">'+allPointInfo[i].pointName
		    					+'</td><td title="'+allPointInfo[i].staffName+'" style="height: 30px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">'+allPointInfo[i].staffName
		    					+'</td><td title="'+allPointInfo[i].taskReceiveTime+'" style="height: 30px; white-space: normal; overflow: hidden; text-overflow: ellipsis;">'+allPointInfo[i].taskReceiveTime
		    					+'</td><td title="'+allPointInfo[i].taskCrossTime+'" style="height: 30px; white-space: normal; overflow: hidden; text-overflow: ellipsis;">'+allPointInfo[i].taskCrossTime
		    					+'</td><td title="'+state+'" style="height: 30px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">'+state
		    					+'</td><td title="'+allPointInfo[i].reason+'" style="height: 30px; white-space: normal; overflow: hidden; text-overflow: ellipsis;">'+allPointInfo[i].reason+'</td></tr>');
		    		}
		    	}
		    	if(workflowType == 3){
		    		var array = new Array();
		    		var InputSelDefaultArray = '请选择';
		    		if(data.pointName.indexOf("#") >= 0){
		    			var objArr = data.pointName.split('#');
		    			for (var i = 0; i < objArr.length; i++) {
							var map = {
									"text" : objArr[i],
			    					"value" : objArr[i]
								};
							array.push(map);
						}
		    		}else{
		    			var map = {
		    					"text" : data.pointName,
		    					"value" : data.pointName
		    			};
		    			array.push(map);
		    		}
					util.initSelectArray('#nextApprovalSelect',array,InputSelDefaultArray);
		    	}else
		    		$('#nextApproval').text(data.pointName);
		    	var  nextApproval = data.nextApproval;
		    	if(nextApproval != null)
		    		util.initSelectArray(ApprovalStaffInputSelForUtil,nextApproval,InputSelDefaultForUtil);
		    	if(workflowType == 16){
		    		var pointNum = data.pointNum;
		    		if(pointNum && pointNum == 1){
		    			$("#financialDeptDiv").show();
		    			$('input[name="selDept"]').removeAttr('disabled');
		    			$('#nextApproval').text('相关部门经理');
		    			$('#ApprovalStaff').hide();
		    		}else if(pointNum && pointNum > 1){
		    			$('#ApprovalStaff').show();
		    			$("#financialDeptDiv").show();
		    			$('input[name="selDept"]').attr('disabled',true);
		    		}else{
		    			$('#financialDeptDiv').hide();
		    		}
		    	}
		    	util.showModal('instanceInfoForResaveModal');
		    }
		}
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "服务器当前遇到问题!",
	            type: "error"
		    });
		}		
}

function changeFormDiv(value){
	if(value == '有合同报销'){
		$('#contractDiv').find(":input").attr('disabled',false);
		$('#contractDiv').find(":input").addClass('need');
		$('#contractContent').attr("disabled",false);
		$('#noContractDescription').attr("disabled",true);
		$('#contractContent').addClass("need")
//		$('#noContractDescription').removeClass("need")
	}else{
		$('#contractDiv').find(":input").attr('disabled',true);
		$('#contractDiv').find(":input").removeClass('need');
		$('#contractContent').attr("disabled",true);
		$('#noContractDescription').attr("disabled",false);
//		$('#noContractDescription').addClass("need");
		$('#contractContent').removeClass("need");
	}
}
var changeFileManagerStaff = function(value){
	if(value == '本部各部门经理'){
		document.getElementById('nextApprovalDiv').style.display = 'none';
		document.getElementById('nextApprovalDiv2').style.display = '';
	}else if(value == '公司领导'){
		document.getElementById('nextApprovalDiv').style.display = '';
		document.getElementById('nextApprovalDiv2').style.display = 'none';
		getFileManagerStaff.postData={
				postName:value
		};
		util.commAjax(getFileManagerStaff);
	}else if(value == '流程结束'){
		document.getElementById('nextApprovalDiv').style.display = 'none';
		document.getElementById('nextApprovalDiv2').style.display = 'none';
	}else{
		if(value == '您已经是最后的审批人员')
			document.getElementById('nextApprovalDiv').style.display = 'none';
		else{
			document.getElementById('nextApprovalDiv').style.display = '';
			document.getElementById('nextApprovalDiv2').style.display = 'none';
			getFileManagerStaff2.postData={
					postName:value
			};
			util.commAjax(getFileManagerStaff2);
		}
	}
};
var getFileManagerStaff = 
{
		url		:	'/workflowInstance/getFileManagerStaff'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			util.initSelectArray(ApprovalStaffInputSelForUtil,data,InputSelDefaultForUtil);
		}
};
var getFileManagerStaff2 = 
{
		url		:	'/workflowInstance/getFileManagerStaff2'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			util.initSelectArray(ApprovalStaffInputSelForUtil,data,InputSelDefaultForUtil);
		}
};
var changeFile = function(){
	$('#fileOriginal').remove();
	$('#fileForm').show();
};

todolist.reStartInstanceOptions = 
{
		url		:	'/workflow/reloadInstance'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		hideAjax();
		if (data === 1) {
			swal({
                title: "申请成功!",
                type: "success"
	        },function(){
	        	searchPargerList();
	        	util.hideModal('instanceInfoForResaveModal');
	        });
		}
		else
			swal({
                title: "申请失败!",
                type: "error"
		    });
		}
	,	error	:	function(xhr,st,err){		
		swal({
            title: "申请失败!",
            type: "error"
	    });
	}
};
todolist.reStartFileInstanceOptions = 
{
		url		:	'/workflow/reloadFileInstance'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		hideAjax();
		if (data === 1) {
			swal({
                title: "申请成功!",
                type: "success"
	        },function(){
	        	searchPargerList();
	        	util.hideModal('instanceInfoForResaveModal');
	        });
		}
		else
			swal({
                title: "申请失败!",
                type: "error"
		    });
		}
	,	error	:	function(xhr,st,err){		
		swal({
            title: "申请失败!",
            type: "error"
	    });
	}
};
function reSaveDiv(flag){
	var result = checkSaveDiv();
	if(!result){
		swal({
            title: "有必填项为空",
            type: "error"
	    });
		return false;
	}
	//var formId = $('#formId').text();
	//if(formId == ''){
	//	swal({
   //         title: "暂无流程,不能提交，请联系管理员",
   //         type: "error"
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
	}
	var obj = util.findFormDataForNew('#saveDiv');
	var fileName = $('#fileName').val();
	var fileObj = document.getElementById('file');
	if(fileObj != null && fileName == ''){
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
	if(flag == 3){
		todolist.reStartFileInstanceOptions.postData = {
				params 			: 	JSON.stringify(obj),
				wfiId	:	$('#selInstanceId').val(),
				subStaffId		:	null,
				fileName		:	fileName,
				managerStaffId	:	$('#ApprovalStaff').val()
		};
		loadingAjax();
		if(fileName != '')
			$('#fileForm').ajaxSubmit();
		util.commAjax(todolist.reStartFileInstanceOptions);
	}else{
		todolist.reStartInstanceOptions.postData = {
				params 			: 	JSON.stringify(obj),
				wfiId	:	$('#selInstanceId').val(),
				subStaffId		:	null,
				fileName		:	fileName,
				managerStaffId	:	$('#ApprovalStaff').val()
		};
		loadingAjax();
		if(fileName != '')
			$('#fileForm').ajaxSubmit();
		util.commAjax(todolist.reStartInstanceOptions);
	}
};
/*****************岗位异动使用开始***************************/
todolist.staffgridId = "#table_staff";
todolist.staffpagerId = "#pager_staff";
todolist.staffsearchDivInput = "#searchStaffDiv :input";
todolist.staffparams = 'params';
todolist.staffgridOption = { 
		url: context+"/staff/list",
		postData: {'param':{}},
		colNames : ['姓名','员工编号','部门','职务','职称职级','岗位'],
		colModel : [
		            {name : 'staffName', width : 100, align : "center"},
		            {name : 'staffNo', width : 100, align : "center"},
		            {name : 'department.departmentName', width : 100, align : "center"},
		            {name : 'occupation.occupationName', width : 100, align : "center"},
		            {name : 'title.titleName', width : 100, align : "center"},
		            {name : 'postNames', width : 100, align : "center",sortable:false}
		],
		caption : "员工",
		mtype:"POST",
	    datatype: "json",
	    emptyrecords:"无符合条件数据",
	    rowNum:50000, 
	    height:280,
	    pager: todolist.staffpagerId,  
	    autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		shrinkToFit:false,
		autoScroll: false  
};
var staffsearchPargerList = function(){
	util.searchListWithParams(todolist.staffgridId,todolist.staffsearchDivInput,todolist.staffparams);
};

todolist.postInputSel = '#posts';

$(function(){
	$(todolist.staffgridId).jqGrid(todolist.staffgridOption);
	util.commAjax(todolist.postOptions);
});

var allPostData;
todolist.postOptions = 
{
		url		:	'/post/getAllPosts'							
	,	mtype	:	'POST'						
	,	postData	:	{departmentId:null}						
	,	success :	function(data,st, xhr){	
			allPostData = data;
		}
}

todolist.departmentTreeSetting = {
		check: {
			enable: true,
			chkStyle: "radio",
			chkboxType: { "Y": "s", "N": "s" },
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};
todolist.loadDepartmentTreeNodesForPostsOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTree"), todolist.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTree').expandAll(true);
			} else
				swal({
		            title: "加载数据失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "加载数据失败!",
	            type: "error"
		    });
		}		
};

function showSelectModal(){
	$('#searchStaffNo').val('');
	$('#searchStaffName').val('');
	util.commAjax(todolist.loadDepartmentTreeNodesForPostsOptions);
	staffsearchPargerList();
	util.initSelectArray(todolist.postInputSel,allPostData,null);
	 var config = {
	          '.chosen-select' : {}
	   }
 	 for (var selector in config) {
 	     $(selector).chosen(config[selector]);
	  }
	 $('.chosen-container-multi').width(200);
	util.showModal('SelectModal');
}

function addPostChangeInfo(){
	var staffIdArr = $(todolist.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIdArr.length < 1){
		swal({
			title: "至少选择一个员工"
		});
		return false;
	}
	var departmentTreeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var department_nodes = departmentTreeObj.getCheckedNodes(true);
	var departmentNewName = "";
	if(department_nodes == '' || department_nodes == null || department_nodes.length == 0) {
	}else
		departmentNewName = department_nodes[0].name;
	var postNames = "";
	$("#posts option:selected").each(function(){
		if(postNames == '')
			postNames = this.text;
		else
			postNames+=","+this.text;
	});
	
	for (var i = 0; i < staffIdArr.length; i++) {
		var rowData = $(todolist.staffgridId).jqGrid('getRowData', staffIdArr[i]);
		var str = '<div class="postChangeInfo" style="margin-top:5px;">'
		+'<label name="changeStaffName" style="width:10%;text-align: center;">'+rowData['staffName']
		+'</label><label name="changeStaffNo" style="width:10%;text-align: center;">'+rowData['staffNo']
		+'</label><label name="oldDepartmentName" style="width:15%;text-align: center;">'+rowData['department.departmentName']
		+'</label><label name="oldPostNames" style="width:20%;text-align: center;">'+rowData['postNames']
		+'</label><label name="newDepartmentName" style="width:16%;text-align: center;">'+(departmentNewName==""?rowData['department.departmentName']:departmentNewName)
		+'</label><label name="newPostNames" style="width:19%;text-align: center;">'+(postNames==""?rowData['postNames']:postNames)
		+'</label><button type="button" class="btn btn-primary btn-sm" style=width:10%;text-align: center;" onclick="$(this).parent().remove();">删除</button></div>';
		$('#postChangeInfo').append(str);
	}
	hideAddAssetsModel('SelectModal');
};
/*****************岗位异动使用结束***************************/
/**********固定资产使用开始*************/

function checkSelectModalSaveDiv(){
	var result = true;
	$('#SelectModalForAssets input').each(function(){
		if($(this).val() == ''){
			$(this).css('border','1px solid red');
			result = false;			
		}else
			$(this).css('border','');
	});
	return result;
};
function checkEditSelectModalSaveDiv(){
	var result = true;
	$('#EditselectModal input').each(function(){
		if($(this).val() == ''){
			$(this).css('border','1px solid red');
			result = false;			
		}else
			$(this).css('border','');
	});
	return result;
};
function showSelectModalForFixedAssets(){
	if($('.assetsInfo').length==5){
		swal({
            title: "超过5条请添加到附件中!",
            type: "error"
	    });
		return false;
	}
	$('#SelectModalForAssets input').val('');
	$('#SelectModalForAssets input').css('border','');
	util.showModal('SelectModalForAssets');
}

function addInfo(){
	var result = checkSelectModalSaveDiv();
	if(!result){
		return false;
	}
	var valObj = {};
	$('#SelectModalForAssets input').each(function(index){
		valObj[index] = $(this).val();
	});
	var str = '<div class="assetsInfo" style="margin-top:5px;">'
	+'<label name="oneNo" style="width:10%;text-align: center;">'+valObj['0']
	+'</label><label name="oneName" style="width:10%;text-align: center;">'+valObj['1']
	+'</label><label name="oneType" style="width:10%;text-align: center;">'+valObj['2']
	+'</label><label name="oneCnt" style="width:10%;text-align: center;">'+valObj['3']
	+'</label><label name="onePrice" style="width:10%;text-align: center;">'+valObj['4']
	+'</label><label name="oneOldPrice" style="width:10%;text-align: center;">'+valObj['5']
	+'</label><label name="oneReduce" style="width:10%;text-align: center;">'+valObj['6']
	+'</label><label name="oneLeft" style="width:10%;text-align: center;">'+valObj['7']
	+'</label><label name="oneYear" style="width:10%;text-align: center;">'+valObj['8']
	+'</label><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="editInfo(this);">编辑</button><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="cancelInfo(this);tongji();">删除</button></div>';
	$('#assetsInfo').append(str);
	hideAddAssetsModel('SelectModalForAssets');
	tongji();
};
function editInfo(obj){
	var objArr = $(obj).parent().find('label');
	var valObj = {};
	for (var i = 0; i < objArr.length; i++) 
		valObj[i] = objArr[i].innerText;
	$('#EditselectModal input').each(function(index){
		$(this).val(valObj[index]);
	});
	util.showModal('EditselectModal');
	infoObj = $(obj).parent();
};
function addEditInfo(){
	var result = checkEditSelectModalSaveDiv();
	if(!result){
		return false;
	}
	var valObj = {};
	$('#EditselectModal input').each(function(index){
		valObj[index] = $(this).val();
	});
	var str = '<div class="assetsInfo" style="margin-top:5px;">'
	+'<label name="oneNo" style="width:10%;text-align: center;">'+valObj['0']
	+'</label><label name="oneName" style="width:10%;text-align: center;">'+valObj['1']
	+'</label><label name="oneType" style="width:10%;text-align: center;">'+valObj['2']
	+'</label><label name="oneCnt" style="width:10%;text-align: center;">'+valObj['3']
	+'</label><label name="onePrice" style="width:10%;text-align: center;">'+valObj['4']
	+'</label><label name="oneOldPrice" style="width:10%;text-align: center;">'+valObj['5']
	+'</label><label name="oneReduce" style="width:10%;text-align: center;">'+valObj['6']
	+'</label><label name="oneLeft" style="width:10%;text-align: center;">'+valObj['7']
	+'</label><label name="oneYear" style="width:10%;text-align: center;">'+valObj['8']
	+'</label><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="editInfo(this);">编辑</button><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="cancelInfo(this);tongji();">删除</button></div>';
	$('#assetsInfo').append(str);
	infoObj.remove();
	util.hideModal('EditselectModal');
	tongji();
};
function cancelInfo(obj){
	$(obj).parent().remove();
	tongji();
};
function tongji(){
	var totalCnt = 0;
	var totalPrice = 0;
	var totalOldPrice = 0;
	var totalReduce = 0;
	var totalLeft = 0;
	var totalYear = 0;
	$('label[name="oneCnt"]').each(function(){
		totalCnt+=parseInt($(this).text());
	});
	$('label[name="onePrice"]').each(function(){
		totalPrice+=parseFloat($(this).text());
	});
	$('label[name="oneOldPrice"]').each(function(){
		totalOldPrice+=parseFloat($(this).text());
	});
	$('label[name="oneReduce"]').each(function(){
		totalReduce+=parseFloat($(this).text());
	});
	$('label[name="oneLeft"]').each(function(){
		totalLeft+=parseFloat($(this).text());
	});
	$('label[name="oneYear"]').each(function(){
		totalYear+=parseInt($(this).text());
	});
	$('#totalCnt').text(totalCnt);
	$('#totalPrice').text(totalPrice);
	$('#totalOldPrice').text(totalOldPrice);
	$('#totalReduce').text(totalReduce);
	$('#totalLeft').text(totalLeft);
	$('#totalYear').text(totalYear);
};
todolist.departmentTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: departmentNodeOnclick
		}
		
};

function departmentNodeOnclick(event,treeId, treeNode){
	if(treeId =='departmentTreeOld'){
		$('#oldDept').val(treeNode.name);
	}else if(treeId =='departmentTreeNew')
		$('#newDept').val(treeNode.name);
};

todolist.loadDepartmentTreeNodesForAssetsOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTreeOld"), todolist.departmentTreeSetting, data);
				$.fn.zTree.init($("#departmentTreeNew"), todolist.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTreeOld').expandAll(true);
				$.fn.zTree.getZTreeObj('departmentTreeNew').expandAll(true);
			} else
				swal({
		            title: "加载数据失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "加载数据失败!",
	            type: "error"
		    });
		}		
};

var hideAddAssetsModel=function(modalId){
	util.hideModal(modalId);
	$('body').addClass('modal-open');
}
/**********固定资产使用结束*************/
todolist.getFirstManagerStaffOptions = 
{
		url		:	'/workflow/getFirstManagerStaff'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){	
		   if(data != null){
		    	var ifChecked = data.ifChecked;
		    	var value = data.value;
		    	var text = data.text;
		    	if(ifChecked){
		    		$('#nextApprovalDiv').append('<span id="next_'+value+'" name="next_'+value+'" class="formSpan nextDeptStaff">'+text+'</span>');
		    	}else{
		    		$('next_'+value).remove();
		    	}
		    }
		}
}

var getFirstManagerStaff = function(obj){
	console.log($(obj).prop('checked'));
	todolist.getFirstManagerStaffOptions.postData={
			deptName:obj.value,
			ifChecked:$(obj).prop('checked')
	}
	util.commAjax(todolist.getFirstManagerStaffOptions);
};

var financialBillsForm = function(){};
financialBillsForm.financeSubjectTreeId = '#financeSubjectTree';
function zpChange(){
	var zMoney = $('#zMoney').val();
	var zTax = $('#zTax').val();
	var total = 0;
	if(!isEmpty(zMoney))
		total +=parseFloat(zMoney);
	if(!isEmpty(zTax))
		total+=parseFloat(zTax);
	$('#zTotal').val(total);
	smalltoBIG(total,'zBig');
};
function zpRealityChange(){
	var zMoney = $('#realityzMoney').val();
	var zTax = $('#realityzTax').val();
	var total = 0;
	if(!isEmpty(zMoney))
		total +=parseFloat(zMoney);
	if(!isEmpty(zTax))
		total+=parseFloat(zTax);
	$('#realityzTotal').val(total);
	smalltoBIG(total,'realityzBig');
};
function smalltoBIG(n,spanId){    
    var fraction = ['角', '分'];    
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
    var head = n < 0? '欠': '';    
    n = Math.abs(n);    
    var s = '';    
    for (var i = 0; i < fraction.length; i++){    
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
    }    
    s = s || '整';    
    n = Math.floor(n);    
    for (var i = 0; i < unit[0].length && n > 0; i++){    
        var p = '';    
        for (var j = 0; j < unit[1].length && n > 0; j++){    
            p = digit[n % 10] + unit[1][j] + p;    
            n = Math.floor(n / 10);    
        }    
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
    }
    document.getElementById(spanId).innerHTML = head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整')
}  
function changeInvoiceType(flag){
	if(flag ==1){
		$('#zMoney').removeAttr('disabled');
		$('#zTax').removeAttr('disabled');
		$('#zTotal').removeAttr('disabled');
		$('#nzMoney').attr('disabled',true);
		$('#twMoney').attr('disabled',true);
	}else if(flag == 2){
		$('#zMoney').attr('disabled',true);
		$('#zTax').attr('disabled',true);
		$('#zTotal').attr('disabled',true);
		$('#nzMoney').removeAttr('disabled');
		$('#twMoney').attr('disabled',true);
	}else{
		$('#zMoney').attr('disabled',true);
		$('#zTax').attr('disabled',true);
		$('#zTotal').attr('disabled',true);
		$('#nzMoney').attr('disabled',true);
		$('#twMoney').removeAttr('disabled');
	}
};
function changerealityInvoiceType(flag){
	if(flag ==1){
		$('#realityzMoney').removeAttr('disabled');
		$('#realityzTax').removeAttr('disabled');
		$('#realityzTotal').removeAttr('disabled');
		$('#realitynzMoney').attr('disabled',true);
		$('#realitytwMoney').attr('disabled',true);
	}else if(flag == 2){
		$('#realityzMoney').attr('disabled',true);
		$('#realityzTax').attr('disabled',true);
		$('#realityzTotal').attr('disabled',true);
		$('#realitynzMoney').removeAttr('disabled');
		$('#realitytwMoney').attr('disabled',true);
	}else{
		$('#realityzMoney').attr('disabled',true);
		$('#realityzTax').attr('disabled',true);
		$('#realityzTotal').attr('disabled',true);
		$('#realitynzMoney').attr('disabled',true);
		$('#realitytwMoney').removeAttr('disabled');
	}
};
financialBillsForm.financeSubjectTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

financialBillsForm.loadSecondFinanceSubjectTreeNodesOptions = {
		url		:	'/financeSubject/loadSecondFinanceSubjectTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(financialBillsForm.financeSubjectTreeId ), financialBillsForm.financeSubjectTreeSetting, data);
			} else
				swal({
		            title: "加载数据失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "加载数据失败!",
	            type: "error"
		    });
		}		
};


financialBillsForm.loadThreeFinanceSubjectTreeNodesOptions = {
		url		:	'/financeSubject/loadThreeFinanceSubjectTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(financialBillsForm.financeSubjectTreeId ), financialBillsForm.financeSubjectTreeSetting, data);
			} else
				swal({
		            title: "加载数据失败!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "加载数据失败!",
	            type: "error"
		    });
		}		
};

function selSecondFinanceSubject(inputId){
	$('#selOneSubId').val(inputId);
	$('#reimbursementAmount').val('');
	$('#reimbursementInfo').text('');
	util.commAjax(financialBillsForm.loadSecondFinanceSubjectTreeNodesOptions);
	util.showModal('selFinanceSubjectModal');
};

function selThreeFinanceSubject(inputId){
	if($('#parentFSId').val() == ''){
		swal({
            title: "请先选择业务类型!",
            type: "error"
	    });
		return false;
	}
	$('#selOneSubId').val(inputId);
	$('#reimbursementAmount').val('');
	$('#reimbursementInfo').text('');
	financialBillsForm.loadThreeFinanceSubjectTreeNodesOptions.postData = {
			'pId':$('#parentFSId').val()
	};
	util.commAjax(financialBillsForm.loadThreeFinanceSubjectTreeNodesOptions);
	util.showModal('selFinanceSubjectModal');
};

function addFinanceSubject(){
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0 || selectNodes[0].level == 1){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	var node = selectNodes[0];
	if($('#selOneSubId').val() == 'bussinessType')
		$('#parentFSId').val(node.id);
//	if(node.isParent){
//		swal({
//            title: "请选择最底层节点!",
//            type: "error"
//	    });
//		return false;
//	}
	var reimbursementAmount = $('#reimbursementAmount').val();
	if(isEmpty(reimbursementAmount)){
		swal({
            title: "请输入金额!",
            type: "error"
	    });
		return false;
	}
	$("#"+$('#selOneSubId').val()).val(node.name+"("+reimbursementAmount+")");
	if($('#selOneSubId').val() == 'bussinessType')
		smalltoBIG(reimbursementAmount,'bussinessBig');
	util.hideModal('selFinanceSubjectModal');
};

financialBillsForm.getBalanceOptions = 
{
		
		url		:	'/financeSubject/balance'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){
			var reimbursementInfo = $('#reimbursementInfo').text();
			if(reimbursementInfo == ''){
				$('#reimbursementInfo').text("该项目余额为:"+data);
				if(data<0)
					$('#reimbursementInfo').css('color','red');
				else 
					$('#reimbursementInfo').css('color','');
			}
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "服务器遇到问题!",
	            type: "error"
		    });
		}		
};

function getBalance(){
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0 || selectNodes[0].level == 1){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	var node = selectNodes[0];
//	if(node.isParent){
//		swal({
//            title: "请选择最底层节点!",
//            type: "error"
//	    });
//		return false;
//	}
	financialBillsForm.getBalanceOptions.postData={
			id:node.id
	}
	util.commAjax(financialBillsForm.getBalanceOptions);
}
