String.prototype.replaceAll = function(regexp,replaceValue){   
	return this.replace(new RegExp(regexp,"gm"),replaceValue);   
};

var hideTreeView = function(treeId){
	$('#'+treeId).hide(500);
};

var showTreeView = function(treeId){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var selIdHidden = treeId.substring(0,treeId.length-4)+'IdHidden';
	var selId = $('#'+selIdHidden).val();
	var node = treeObj.getNodeByParam("id", selId, null);
	if (node)
		treeObj.selectNode(node);
	$('#'+treeId).show(500);
};

var isEmpty = function(str){
	if(str == undefined || str == "" || str == " " || str == null)
		return true;
	return false;
};

var getSystmeInfoOptionsForForm = 
{
		url		:	'/workflow/getSystemInfo'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if(data == null){
				swal({
		            title: "暂无流程，请联系管理员",
		            type: "error"
			    });
			}else{
				$('#formId').text(data[0]);
				$('#applyDate').text(data[1]);
				$('#applyStaff').text(data[2]);
				$('#staffNo').text(data[3]);
				$('#deptName').text(data[4]);
				$('#overtimeTotal').text(data[5]);
				$('#nextApproval').text(data[6]);
				var map = {
					"text" : data[6],
					"value" : data[6]
				}
				var array = new Array()
				array.push(map);
				util.initSelectArray('#nextApprovalSelect',array,'请选择');
				$('#companyName').text(data[7]);
				$('#item').text(data[8]);
			}
		}
	,	error	:	function(xhr,st,err){		
		swal({
            title: "暂无流程，请联系管理员!",
            type: "error"
	    });
	}
};

var allApprovalStaffOptionsForForm = 
{
		url		:	'/workflow/getAllApprovalStaffs'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(ApprovalStaffInputSelForUtil,data,InputSelDefaultForUtil);
		}
};

$(function(){
	var ifForm= window.location.href.indexOf("allProcessLi_");
	if(ifForm > 0){
		var workflowId = $('#processLiIdForHidden').val().split('_')[1];
		allApprovalStaffOptionsForForm.postData={
				'subStaffId':null,
				'workflowId':workflowId
		}
		getSystmeInfoOptionsForForm.postData = {
				'workflowId':workflowId
		}
		util.commAjax(allApprovalStaffOptionsForForm);
		util.commAjax(getSystmeInfoOptionsForForm);
	}
});

var modelObj = {
		1:"/overtimeFormModel.html",
		2:"/leaveFormModel.html",
		3:"/fileAccessFormModel.html",
		4:"/financialBillsFormModel.html",
		5:"/partyFileFormModel.html",
		6:"/useSealFormModel.html",
		7:"/fixedAssetsFormModel.html",
		8:"/contractWillFormModel.html",
		9:"/goOutFormModel.html",
		10:"/salaryFormModel.html",
		11:"/addOrRemoveStaffFormModel.html",
		12:"/expandRewardFormModel.html",
		13:"/supplierFormModel.html",
		14:"/companyVIFormModel.html",
		15:"/postChangeFormModel.html"
}

var overtimeTypesInputSelForUtil = '#overtimeType';
var ApprovalStaffInputSelForUtil = '#ApprovalStaff';
var InputSelDefaultForUtil = '请选择';
var workflowInstanceIdForUtil;
var workFlowTypeForUtil;
var lookOrOperateForUtil = 1;// 1 :办理　2：查看
var hasPrintForUtil =false;
var overtimeTypeNameOptionsForUtil = 
{
		url		:	'/overtimeType/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){		
			$('#overtimeType').text(data);
		}
};

var sealTypeNameOptionsForUtil = 
{
		url		:	'/sealType/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){	
			$('#useType').text(data);
		}
};

var assetTypeNameOptionsForUtil = 
{
		url		:	'/assetType/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){	
			$('#bussinessType').text(data);
		}
};

var departMentNameOptionsForUtil = 
{
		url		:	'/department/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){	
			$('#company').text(data);
		}
};

var departMentNameThreeOptionsForUtil = 
{
		url		:	'/department/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){	
			$('#deptName').text(data);
		}
};

var budgetCommissionerOptionsForUtil = 
{
		url		:	'/budgetCommissioner/getName'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType:'text'
	,	success :	function(data,st, xhr){	
			$('#budgetPerson').text(data);
		}
};

var lookInstanceInfoOptions = 
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
		    	var wfi = data.wfId;
		    	if(isEmpty(filePath))
		    		$('#showFileDiv').append('<span class="formSpan transform">无附件</span>');
		    	else{
		    		var fileNameArr = fileName.split('##');
		    		for (var i = 0; i < fileNameArr.length; i++) {
		    			if(workflowType == 8)
		    				$('#showFileDiv').append('<a style="float:left;margin-left:5px;"href="'+context+"/static/file_temp/"+fileNameArr[i]+'">附件'+(i+1)+'</a><i class="fa fa-times" id="fileI'+i+'" style="float:left;margin-top:2px;cursor: pointer;" title="删除" onclick="delFileForContract(this.id,'+workflowInstanceIdForUtil+',\''+fileNameArr[i]+'\')"></i>');
		    			else
		    				$('#showFileDiv').append('<a style="margin-left:5px;"href="'+context+"/static/file_temp/"+fileNameArr[i]+'">附件'+(i+1)+'</a>');
					}
		    	}
		    	var instanceInfoObj = JSON.parse(instanceInfo);
		    	if(workflowType == 2){
		    		var leaveInfoArr = instanceInfoObj.leaveInfo.split('#####');
		    		for(var i=0;i<leaveInfoArr.length;i++){
		    			var oneObj = JSON.parse(leaveInfoArr[i]);
		    			var str = '<div class="leaveInfo"><label name="leaveSectionFrom" style="float:left;width:33%;">'+oneObj.leaveSectionFrom
		    			+'</label><label name="leaveSectionTo" style="float:left;width:33%;">'+oneObj.leaveSectionTo
		    			+'</label><label name="leaveTypeName" style="float:left;width:33%;">'+oneObj.leaveTypeName
		    			+'</label></div>';
		    			$('#leaveInfo').append(str);
		    		}
		    		$('#leaveInfoDiv').height((leaveInfoArr.length+1)*25);
		    	}
		    	if(workflowType == 4){//财务票据
		    		var invoiceType = instanceInfoObj.invoiceType;
		    		if(invoiceType == '专票')
		    			$('#zMoneyDiv').show();
		    		else if(invoiceType.indexOf('非专票')>=0)
		    			$('#nzMoneyDiv').show();
		    		else
		    			$('#twMoneyDiv').show();
		    		var realityinvoiceType = instanceInfoObj.realityinvoiceType;
		    		if(realityinvoiceType != null){
		    			if(realityinvoiceType == '专票')
		    				$('#realityzMoneyDiv').show();
		    			else if(realityinvoiceType.indexOf('非专票')>=0)
		    				$('#realitynzMoneyDiv').show();
		    			else
		    				$('#realitytwMoneyDiv').show();
		    		}
		    		$('#contractNo').text(instanceInfoObj.contractNo1+'-'+instanceInfoObj.contractNo2+'-'+instanceInfoObj.contractNo3+'-'+instanceInfoObj.contractNo4);
		    	}
		    	if(workflowType == 7){
		    		if(instanceInfoObj.assetsInfo != undefined && instanceInfoObj.assetsInfo != null){
		    			var assetsInfoArr = instanceInfoObj.assetsInfo.split('#####');
		    			for(var i=0;i<assetsInfoArr.length;i++){
		    				var oneObj = JSON.parse(assetsInfoArr[i]);
		    				var str = '<div class="assetsInfo" style="margin-top:5px;">'
		    					+'<label name="oneNo" style="width:11%;text-align: center;">'+oneObj.oneNo
		    					+'</label><label name="oneName" style="width:11%;text-align: center;">'+oneObj.oneName
			    				+'</label><label name="oneType" style="width:11%;text-align: center;">'+oneObj.oneType
			    				+'</label><label name="oneCnt" style="width:11%;text-align: center;">'+oneObj.oneCnt
			    				+'</label><label name="onePrice" style="width:11%;text-align: center;">'+oneObj.onePrice
			    				+'</label><label name="oneOldPrice" style="width:11%;text-align: center;">'+oneObj.oneOldPrice
			    				+'</label><label name="oneReduce" style="width:11%;text-align: center;">'+oneObj.oneReduce
			    				+'</label><label name="oneLeft" style="width:11%;text-align: center;">'+oneObj.oneLeft
			    				+'</label><label name="oneYear" style="width:11%;text-align: center;">'+oneObj.oneYear
			    				+'</label></div>';
		    				$('#assetsInfo').append(str);
		    			}
		    		}
		    	}
		    	if(workflowType == 15){
		    		var postChangeInfo = instanceInfoObj.postChangeInfo.split('#####');
		    		for(var i=0;i<postChangeInfo.length;i++){
		    			var oneObj = JSON.parse(postChangeInfo[i]);
		    			var str = '<div class="postChangeInfo" style="margin-top:5px;">'
		    				+'<label name="changeStaffName" style="width:16%;text-align: center;">'+oneObj.changeStaffName
		    			+'</label><label name="changeStaffNo" style="width:16%;text-align: center;">'+oneObj['changeStaffNo']
		    			+'</label><label name="oldDepartmentName" style="width:16%;text-align: center;">'+oneObj['oldDepartmentName']
		    			+'</label><label name="oldPostNames" style="width:16%;text-align: center;">'+oneObj['oldPostNames']
		    			+'</label><label name="newDepartmentName" style="width:16%;text-align: center;">'+oneObj['newDepartmentName']
		    			+'</label><label name="newPostNames" style="width:16%;text-align: center;">'+oneObj['newPostNames']+'</label></div>';
		    			$('#postChangeInfo').append(str);
		    		}
		    	}
		    	$('.transform').each(function(){
		    		this.disabled = true;
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
		    	if(workflowType == 1){
		    		var overtimeType = instanceInfoObj.overtimeType;
		    		overtimeTypeNameOptionsForUtil.postData={
		    				typeId:overtimeType
		    		}
		    		util.commAjax(overtimeTypeNameOptionsForUtil);
		    	}
		    	if(workflowType == 6){
		    		var useTypeArr = instanceInfoObj.useType.split(',');
		    		var div = $('#useTypeDiv');
					for (var i = 0; i < useTypeArr.length; i++){
						var num = instanceInfoObj[useTypeArr[i]] == undefined ? 0 : instanceInfoObj[useTypeArr[i]];
						if(i== 0 || i == 1)
							div.append('<span id="useType" name="useType" class="formSpan transform" style="width:60%;">'+useTypeArr[i]+': '+num+'次</span>')
						else
							div.append('<span id="useType" name="useType" class="formSpan transform" style="width:60%;margin-left:100px;">'+useTypeArr[i]+': '+num+'次</span>')
					}
		    	}
		    	if(workflowType == 4){
		    		$('#parentFSId').val(instanceInfoObj.parentFSId);
		    		var budgetPerson = instanceInfoObj.budgetPerson;
		    		budgetCommissionerOptionsForUtil.postData={
		    				id:budgetPerson
		    		}
		    		util.commAjax(budgetCommissionerOptionsForUtil);
		    		departMentNameOptionsForUtil.postData={
		    				dId:instanceInfoObj.company
		    		}
		    		util.commAjax(departMentNameOptionsForUtil);
		    		departMentNameThreeOptionsForUtil.postData={
		    				dId:instanceInfoObj.deptName
		    		}
		    		util.commAjax(departMentNameThreeOptionsForUtil);
		    	}
		    	if(workflowType == 6){
		    		var useType = instanceInfoObj.useType;
		    		sealTypeNameOptionsForUtil.postData={
		    				typeId:useType
		    		}
		    		util.commAjax(sealTypeNameOptionsForUtil);
		    	}
		    	if(workflowType == 7){
		    		var bussinessType = instanceInfoObj.bussinessType;
		    		assetTypeNameOptionsForUtil.postData={
		    				typeId:bussinessType
		    		}
		    		util.commAjax(assetTypeNameOptionsForUtil);
		    		
		    		departMentNameOptionsForUtil.postData={
		    				dId:instanceInfoObj.company
		    		}
		    		util.commAjax(departMentNameOptionsForUtil);
		    		
		    		departMentNameThreeOptionsForUtil.postData={
		    				dId:instanceInfoObj.deptName
		    		}
		    		util.commAjax(departMentNameThreeOptionsForUtil);
		    	}
		    	if(workflowType == 8){
//		    		document.getElementById('fileForm').style.display = 'none';
		    		$('#nearType').text(instanceInfoObj.nearType+instanceInfoObj.othernearType);
		    		departMentNameOptionsForUtil.postData={
		    				dId:instanceInfoObj.company
		    		}
		    		util.commAjax(departMentNameOptionsForUtil);
		    		departMentNameThreeOptionsForUtil.postData={
		    				dId:instanceInfoObj.deptName
		    		}
		    		util.commAjax(departMentNameThreeOptionsForUtil);
		    	}
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
								}
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
		    	if(lookOrOperateForUtil == 2)
		    		$('.lookHidden').hide();
		    	else{
		    		$('.lookHidden').show();
		    		$('#approvalReason').val('同意');
		    		var  nextApproval = data.nextApproval;
		    		if(nextApproval != null)
		    			util.initSelectArray(ApprovalStaffInputSelForUtil,nextApproval,InputSelDefaultForUtil);
		    		else
		    			$('#nextApprovalDiv').hide();
		    	}
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
		    	if(hasPrintForUtil)
		    		$('#printBtn').show();
		    	else
		    		$('#printBtn').hide();
		    	util.showModal('instanceInfoModal');
		    }
		}
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "服务器当前遇到问题!",
	            type: "error"
		    });
		}		
};

var getResultForInstanceLook = function(){
	$('#instanceInfoModal').empty();
	var url = context +"/static/progressmodel" + modelObj[workFlowTypeForUtil];
	$.ajax({ 
		async:false, 
		url : url, 
		success : function(result){ 
			$('#instanceInfoModal').append(result);
			lookInstanceInfoOptions.postData = {
					instanceId:workflowInstanceIdForUtil
			}
			util.commAjax(lookInstanceInfoOptions);
		} 
	}); 
};

//民族56
var nationArrayForUtil = [ "汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族",
		"朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族",
		"佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族",
		"达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族",
		"塔吉克族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族",
		"塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族" ];
//政治面貌 13种
var politicalArrayForUtil= [ "中共党员", "中共预备党员", "共青团员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员",
              		"九三学社社员", "台盟盟员", "无党派民主人士", "群众"];

var qualificationArrayForUtil= [ "小学", "初中", "中职", "中技", "中专", "高中", "职高", "高职",
                   		"大专", "本科", "硕士", "MBA", "EMBA", "博士", "其他" ];
function util(){
	
	this.gridWithSelector = ".jqGrid_wrapper";
	this.selectorTpl = '<option value="{value}">{text}</option>';
	this.selectorTplSelected = '<option value="{value}" selected>{text}</option>';
	this.selectorTplValue = '{value}';
	this.selectorTplText = '{text}';
	/**
	 * 获取from 表单数据
	 * @param fromSelector
	 * @returns {}
	 */
	this.findFormDataObject = function(fromSelector){
		var formData = { };
		var input = $(fromSelector);
		for (var i = 0; i < input.length; i++){
			if (input[i].name != undefined && input[i].name != ""){
				if (input[i].type == 'radio'){
					if (input[i].checked)
						formData[input[i].name] = input[i].value;
				} else {
					if (input[i].type == 'checkbox'){
						if (input[i].checked){
							var fd  = formData[input[i].name];
							if (fd == undefined || fd== ''){
								formData[input[i].name] = input[i].value;
							}else{
								formData[input[i].name] = fd + "," + input[i].value;
							}
						}
					} else if(input[i].type == 'file'||input[i].name=='fileName'){
						continue;
					}else{
							formData[input[i].name] = input[i].value;
					}
				} 
			}
		}
		return formData;
	};
	
	this.findFormDataObjectForNew = function(fromSelector) {
		var formData = {};
		var input = $(fromSelector + " :input[class*='transform']");
		for (var i = 0; i < input.length; i++) {
			if (input[i].name != undefined && input[i].name != "") {
				if (input[i].type == 'radio') {
					if (input[i].checked)
						formData[input[i].name] = input[i].value;
				} else {
					if (input[i].type == 'checkbox') {
						if (input[i].checked) {
							var fd = formData[input[i].name];
							if (fd == undefined || fd == '') {
								formData[input[i].name] = input[i].value;
							} else {
								formData[input[i].name] = fd + "," + input[i].value;
							}
						}else{
							var fd = formData[input[i].name];
							if (fd == undefined)
								formData[input[i].name] = '';
						}
					} else {
						if(input[i].name=='nearType'){
							var fd  = formData[input[i].name];
							if (fd == undefined ){
								formData[input[i].name] = input[i].value;
							}else{
								formData[input[i].name] = fd + "," + input[i].value;
							}
						}else
							formData[input[i].name] = input[i].value;
					}
				}
			}
		}
		var span = $("span[class*='transform']");
		for (var i = 0; i < span.length; i++) {
			if (span[i].id != undefined && span[i].id != "")
				formData[span[i].id] = $(span[i]).text();
		}
		var label = $("label[class*='transform']");
		for (var i = 0; i < label.length; i++) {
			if (label[i].id != undefined && label[i].id != "")
				formData[label[i].id] = $(label[i]).text();
		}
		return formData;
	};
	
	/**
	 * 序列化：将对象转成可传输字符传
	 * @param fromSelector
	 * @returns stringify
	 */
	this.findFormData = function(fromSelector){
		return JSON.stringify(this.findFormDataObject(fromSelector));
	};
	this.findFormDataForNew = function(fromSelector){
		return this.findFormDataObjectForNew(fromSelector);
	};
	
	/**
	 * 表单查询
	 * @param gridId 	 	grid id 			- '#listid'
	 * @param fromSelector  form data selector 	- '#formid :input'
	 * @param name			controller key		- 'params'
	 */
	this.searchListWithParams = function(gridId,fromSelector,name){
		var formData = this.findFormData(fromSelector);
		var postData = $(gridId).jqGrid("getGridParam", "postData"); 
		postData[name] = formData;
		$(gridId).jqGrid('setGridParam',{ search : true,datatype:'json' }).trigger("reloadGrid", [{page:1}]);
	};
	/**
	 * 传入json data reload gridlist
	 * @param gridId	grid id
	 * @param formData 	json data
	 * @param name	paramKey
	 */
	this.reloadListWithParams = function(gridId,formData,name){
		var postData = $(gridId).jqGrid("getGridParam", "postData"); 
		if (formData != null || name != null)
			postData[name] = JSON.stringify(formData);
		$(gridId).jqGrid('setGridParam',{ search : true }).trigger("reloadGrid", [{page:1}]);
	};
	/**
	 * 
	 * @param ajaxOptions{
	 * 		url		:	''							//请求地址
	 * 		mtype	:	'POST'						//method type : get or post(defualt)
	 * 		data	:	{}							//post data params请求参数json data type.
	 * 		success :	function(data,st, xhr){}	//callback function sucess.
	 * 		error	:	function(xhr,st,err){}		//callback function error.
	 * }
	 */
	this.commAjax = function(ajaxOptions){
		$.ajax({
			url:context+ajaxOptions.url,
			type:ajaxOptions.mtype == undefined ? 'POST' : ajaxOptions.mtype,
			dataType: ajaxOptions.dataType == undefined ? 'json' : ajaxOptions.dataType ,
			data: ajaxOptions.postData == undefined ? {} : ajaxOptions.postData ,
			async:ajaxOptions.async == undefined ? true : ajaxOptions.async,
			success:function(data,st, xhr) {
				if ($.isFunction(ajaxOptions.success)) {
					ajaxOptions.success.call(ajaxOptions,data, st, xhr);
				}
				xhr=null;
			},
			error:function(xhr,st,err){
				if ($.isFunction(ajaxOptions.error)) {
					ajaxOptions.error.call(ajaxOptions,xhr,st,err);
				} 
				xhr=null;
			}
		});
	};
	this.initSelectMenu = function(leftbarId){
		$('.firstLevel').removeClass("active").find('ul').removeClass('in').find('li').removeClass('active');
		$('#'+leftbarId).parents('li').addClass("active");
		$('#'+leftbarId).parents('ul').addClass("in");
		$('#'+leftbarId).addClass('active');
	};
	this.showModal = function(objId){
		$('#' + objId).modal({backdrop: 'static'});
	};
	this.hideModal = function(objId){
		$('#' + objId).modal('hide');
	};
	this.validate = function(divId){
		var canSave = true;
		$('#'+divId+" :input").each(function(){
			var obj = $(this);
			if(obj.attr('required') && (obj.val() == '' || obj.val() == null || obj.val()== 'null')){
				if($('#work-info')){
					$('#work-info').addClass('active');
					$('#workinfohref').addClass('active');
					$('#person-info').removeClass('active');
					$('#personinfohref').removeClass('active');
				}
				if(obj.attr('id') != 'posts')
					obj.css('border','1px solid red');
				else
					obj.next('div').css('border','1px solid red');
				canSave = false;
				return false;
			}else{
				obj.css('border','');
			}
		});
		return canSave;
	};
	
	/**
	 * 当浏览器窗口改变时，gridWith自适应
	 */
	this.getGridWith = function(containerSelector){
		if (undefined == containerSelector)
			return $(this.gridWithSelector).width();
		else
			return $(containerSelector).width() * this.widthPercent;
	};
	
	this.initSelectArray = function(targetSelector, textArray, defaultText){
		var opts = "";
		if(defaultText != null)
		   opts = this.selectorTpl.replaceAll(this.selectorTplValue,'').replaceAll(this.selectorTplText, defaultText);
		for (var i = 0; i < textArray.length; i++){
			opts += this.selectorTpl.replaceAll(this.selectorTplValue,textArray[i].value).replaceAll(this.selectorTplText, textArray[i].text);
		}
		$(targetSelector).html(opts);
	};
	
	this.initSelectBySelcted = function(targetSelector, textArray, selectedArr){
		var opts = "";
		for (var i = 0; i < textArray.length; i++){
			var ifSel = false;
			if(selectedArr != null && selectedArr.length>0){
				for(var j=0;j<selectedArr.length;j++){
					if(parseInt(selectedArr[j]) == textArray[i].value){
						ifSel = true;
						break;
					}
				}
			}
			if(ifSel)
				opts += this.selectorTplSelected.replaceAll(this.selectorTplValue,textArray[i].value).replaceAll(this.selectorTplText, textArray[i].text);
			else
				opts += this.selectorTpl.replaceAll(this.selectorTplValue,textArray[i].value).replaceAll(this.selectorTplText, textArray[i].text);
		}
		$(targetSelector).html(opts);
	};
	
	this.initSelect = function(targetSelector, textArray, defaultText){
		var opts = this.selectorTpl.replaceAll(this.selectorTplValue,'').replaceAll(this.selectorTplText, defaultText);
		for (var i = 0; i < textArray.length; i++){
			opts += this.selectorTpl.replaceAll(this.selectorTplValue,i).replaceAll(this.selectorTplText, textArray[i]);
		}
		$(targetSelector).html(opts);
	};
}
function showOtherDiv(value){
	if(value == "其他")
		document.getElementById('paymentOtherReasonDiv').style.display = '';
	else{
		document.getElementById('paymentOtherReason').value = '';
		document.getElementById('paymentOtherReasonDiv').style.display = 'none';
	}
}
var taxRateObj = {
		'免税':0,
		'0%':0,
		'3%':0.03,
		'6%':0.06,
		'11%':0.11,
		'13%':0.13,
		'17%':0.17
}
function showWorkFlow(workFlowId){
	getWorkFlow(workFlowId.split('_')[1]);
}
function getWorkFlow(workFlowId){
	workflowOptions.postData = {
			wId:workFlowId
	};
	util.commAjax(workflowOptions);
}
workflowOptions = 
{
		url		:	'/workflow/getWorkFlowData'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			processWorkFlowData(data);
		}
}
function processWorkFlowData(data){
	$('#workflowDataDiv').empty();
	for (var i = 0; i < data.length; i++){
		$('#workflowDataDiv').append("<span style='line-height:25px;'>"+data[i]+"</span></br>");
	}
	util.showModal('workflowDataModal');
}
function showProjectOtherDiv(value){
	if(value == "其他")
		document.getElementById('projectOperationTypeReasonDiv').style.display = '';
	else{
		document.getElementById('projectOperationTypeReason').value = '';
		document.getElementById('projectOperationTypeReasonDiv').style.display = 'none';
	}
}
function changeConPrice(){
	if(document.getElementById('billType').value == '专票'){
		var taxRate = document.getElementById('taxRate').value;
		if(document.getElementById('taxRate').value == '免税' || document.getElementById('taxRate').value == '0%')
			document.getElementById('price').value = document.getElementById('contractPriceTotal').value;
		else
			document.getElementById('price').value = parseFloat(document.getElementById('contractPriceTotal').value / (1+taxRateObj[taxRate])).toFixed(2);
	}else{
		document.getElementById('price').value = document.getElementById('contractPriceTotal').value;
	}
	document.getElementById('tax').value = parseFloat(document.getElementById('contractPriceTotal').value - document.getElementById('price').value).toFixed(2);
}
function addOptions(data){
	var opt = document.createElement('option');
	opt.text = data;
	opt.value = data;
	document.getElementById('contractNo2').options.add(opt);
}
var TFBBARRAY= ["TFZH", "TFCW", "TFDQ", "TFZX", "TFRL", "TFAJ"];
var TFONARRAY= ["ONBB", "STFW", "STGW", "STCG", "STBL", "CTXM", "BHZX", "BHXW","TGJJ","NGCF","NGZH","NGXF","NGXS","HRYX","BSDX"];
var TFTWARRAY= ["TWBB", "SMGC", "JBQG", "HJDS", "TDRQ", "XJTC","TDCG", "YHDS", "YXZX", "CHCX", "ZQXM", "GDJT", "SJSL","ZHYX","JRGC","ZTHJ","CJSX","GDPP","GDYH","HYHQ"];
var TFTHARRAY= ["THBB", "FXXY", "XDGC", "KJDX", "LSLL", "KGSW","HDZD","WHDS","JARD","JCZX","SNDX","XYHY","HXYX","JNGC","NYJT","SQZX","HJGC","DTJT","AWSX","ZLYY"];
var TFFOARRAY= ["FOBB", "TDKJ", "ZOSD","TDDL", "YLSD", "KJDX", "TDLH", "XYXQ", "MTMX", "ZNZB","LGLY"];
var TFFIARRAY= ["XQZF"];
var TFTFARRAY= ["TFBB","TZFW","BHGH","HWYX","XQTF","TDRC","JSJT","DJGQ","XJGQ","WDZX","TDGY"];
function changeNo(){
	var no1 = document.getElementById('contractNo1');
	var no1value = no1.value;
	var no2 = document.getElementById('contractNo2');
	var no2length = no2.options.length;
	if(no2length > 0){
		for (var i = 0; i < no2length; i++) 
			no2.options.remove(0);
	}
	if(no1value == 'TFBB'){
		for (var i = 0; i < TFBBARRAY.length; i++) 
			addOptions(TFBBARRAY[i]);
	}else if(no1value == 'TFON'){
		for (var i = 0; i < TFONARRAY.length; i++) 
			addOptions(TFONARRAY[i]);
	}else if(no1value == 'TFTW'){
		for (var i = 0; i < TFTWARRAY.length; i++) 
			addOptions(TFTWARRAY[i]);
	}else if(no1value == 'TFTH'){
		for (var i = 0; i < TFTHARRAY.length; i++) 
			addOptions(TFTHARRAY[i]);
	}else if(no1value == 'TFFO'){
		for (var i = 0; i < TFFOARRAY.length; i++) 
			addOptions(TFFOARRAY[i]);
	}else if(no1value == 'TFFI'){
		for (var i = 0; i < TFFIARRAY.length; i++) 
			addOptions(TFFIARRAY[i]);
	}else if(no1value == 'TFTF'){
		for (var i = 0; i < TFTFARRAY.length; i++) 
			addOptions(TFTFARRAY[i]);
	}
}
function setParentId(value){
	$('#parentDeptId').val(value);
	deptNameOptions.postData = {
			pId:value
	};
	util.commAjax(deptNameOptions);
}
function setAppStaff(value){
	 var workflowId = $('#processLiIdForHidden').val().split('_')[1];
		allApprovalStaffOptionsForForm.postData={
			'subStaffId':null,
			'workflowId':workflowId,
			dId:value
		}
		util.commAjax(allApprovalStaffOptionsForForm);
}
function setEditAppStaff(deptName){
	    var wfId = $("#editWfId").val();
		allApprovalStaffOptionsForForm.postData={
			'subStaffId':null,
			'workflowId':wfId,
			dId:deptName
		}
		util.commAjax(allApprovalStaffOptionsForForm);
}
companyNameOptions = 
{
		url		:	'/department/loadSecondDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray("#company" ,data,InputSelDefaultForUtil);
		}
}
deptNameOptions = 
{
		url		:	'/department/loadThreeDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray("#deptName" ,data,InputSelDefaultForUtil);
		}
}
