var workflow = function(){};
workflow.gridId = "#table_workflow";
workflow.pagerId = "#pager_workflow";
workflow.searchDivInput = "#searchWorkflowDiv :input";
workflow.searchBtn = "#searchBtn";
workflow.params = 'params';

workflow.workflowTypeInputSel = '#addWorkflowType';
workflow.searchWorkflowTypeInputSel = '#workflowType';
workflow.InputSelDefault = '请选择';

workflow.occupationTreeId = '#occupationTree';

workflow.staffgridId = "#table_staff";
workflow.staffpagerId = "#pager_staff";
workflow.staffsearchDivInput = "#searchStaffDiv :input";

workflow.postgridId = "#table_post";
workflow.postpagerId = "#pager_post";
workflow.postsearchDivInput = "#searchPostDiv :input";

$(function(){
	util.initSelectMenu('workflowLi');
	$(workflow.gridId).jqGrid(workflow.gridOption);
	$(window).resize(function(){
		$(workflow.gridId).setGridWidth(util.getGridWith());
	});
	$(workflow.staffgridId).jqGrid(workflow.staffgridOption);
	$(workflow.postgridId).jqGrid(workflow.postgridOption);
	util.commAjax(workflow.workflowtypesOptions);
	util.commAjax(workflow.loadOccupationTreeNodesOptions);
});

workflow.addWorkflowOptions = 
{
		
		url		:	'/workflow/add'							
	,	mtype	:	'POST'	
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addWorkflowModal'); 
		        });
				searchPargerList();
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

workflow.staffgridOption = { 
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
	   height:200,
	   width:560,
	   pager: workflow.staffpagerId,  
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		autoScroll: false  
};

var staffsearchPargerList = function(){
	util.searchListWithParams(workflow.staffgridId,workflow.staffsearchDivInput,workflow.params);
};

workflow.postgridOption = { 
		url: context+"/post/list",
		postData: {'param':{}},
		colNames : ['岗位名称','岗位代码','岗位描述','备注','部门'],
		colModel : [
		            {name : 'postName', width : 100, align : "center"},
		            {name : 'postNo', width : 100, align : "center"},
		            {name : 'postDescribe', width : 100, align : "center"},
		            {name : 'remarks', width : 100, align : "center"},
		            {name : 'deptNames', width : 150, align : "center"}
		],
		caption : "岗位",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:200,
	   width:560,
	   pager: workflow.postpagerId,  
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		shrinkToFit:true
};

var postsearchPargerList = function(){
	util.searchListWithParams(workflow.postgridId,workflow.postsearchDivInput,workflow.params);
}

workflow.occupationTreeSetting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "s", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

workflow.loadOccupationTreeNodesOptions = 
{
		url		:	'/occupation/loadOccupationTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(workflow.occupationTreeId), workflow.occupationTreeSetting, data);
				$.fn.zTree.getZTreeObj('occupationTree').expandAll(true);
			} else
				swal({
		            title: "操作失败：未加载数据!",
		            type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){	
			swal({
	            title: "操作失败：未加载数据!",
	            type: "error"
		    });
		}		
};

workflow.workflowtypesOptions = 
{
		url		:	'/workflow/getAllWorkflowType'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
		    util.initSelectArray(workflow.searchWorkflowTypeInputSel,data,workflow.InputSelDefault);
          util.initSelectArray(workflow.workflowTypeInputSel,data,workflow.InputSelDefault);
		}
}

workflow.getWorkflowInfoOptions = 
{
		url		:	'/workflow/getWorkflowInfo'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){	
			if(data == null){
				swal({
		            title: "服务器遇到问题，请稍后重试",
		            type: "error"
			    });
				return;
			}else{
				for(var i=0;i<data.length;i++){
					var everyNodeInfo = data[i].split('#####');
					var lititle = "";
					if(!isEmpty(everyNodeInfo[3]))
						lititle = lititle+"职务："+everyNodeInfo[3]+";";
					if(!isEmpty(everyNodeInfo[5]))
						lititle = lititle+"岗位："+everyNodeInfo[5]+";";
					if(!isEmpty(everyNodeInfo[3]))
						lititle = lititle+"员工："+everyNodeInfo[7]+";";
					lititle = lititle.substring(0,lititle.length-1);
					var obj={
							pointName:everyNodeInfo[1],
							occupationId:everyNodeInfo[2],
							occupationNames:everyNodeInfo[3],
							postId:everyNodeInfo[4],
							postNames:everyNodeInfo[5],
							staffId:everyNodeInfo[6],
							staffNames:everyNodeInfo[7]
					}
					workflowNodeArr.push(obj);
					$('#allNodeOl').append('<li style="list-style-type:decimal;text-decoration:underline;font-size:15px;" title="'+lititle+'">'+everyNodeInfo[1]
							+' <i class="fa fa-wrench" style="margin-left:10px;cursor: pointer;" title="编辑" onclick="editNode('+i+
							')"></i><i class="fa fa-times" style="margin-left:10px;cursor: pointer;" title="删除" onclick="deleteNode('+i+')"></i><i class="fa fa-arrow-up" style="margin-left:10px;cursor: pointer;" title="向上" onclick ="nodeUp('+i+');"></i><i class="fa fa-arrow-down" style="margin-left:10px;cursor: pointer;" title="向下" onclick ="nodeDown('+i+');"></i></li>');
					util.showModal('addWorkflowModal');
				}
			}
		}
}

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteWorkflow('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var workflowIds = new Array();
workflow.gridOption = { 
		url: context+"/workflow/list",
		postData: {'param':{}},
		colNames : ['流程名称','流程类别','流程信息','节点个数','申请人(员工)','申请人(员工id)','申请人(职务)','申请人(职务id)','申请人(岗位)','申请人(岗位id)','版本号','创建时间','更新时间','操作','workflowtypeId'],
		colModel : [
		            {name : 'name', width : 100, align : "center"},
		            {name : 'workFlowType.name', width : 100, align : "center"},
		            {name : 'workflowInfo', width : 100, align : "center"},
		            {name : 'point', width : 100, align : "center"},
		            {name : 'staffNames', width : 100, align : "center"},
		            {name : 'staffIds', width : 100, align : "center",hidden:true},
		            {name : 'occupationNames', width : 100, align : "center"},
		            {name : 'occupationIds', width : 100, align : "center",hidden:true},
		            {name : 'postNames', width : 100, align : "center"},
		            {name : 'postIds', width : 100, align : "center",hidden:true},
		            {name : 'version', width : 100, align : "center"},
		            {name : 'createTime', width : 100, align : "center"},
		            {name : 'updateTime', width : 100, align : "center"},
		            {name : 'id', width : 100, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}},
		            {name : 'workFlowType.id', width : 100, align : "center",hidden:true}
		],
		caption : "自定义流程列表",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: workflow.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(workflow.gridId, workflowIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(workflowIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(workflowIds, rowIds, status);
			else
				macIds = new Array();
		} 
};

var searchPargerList = function(){
	util.searchListWithParams(workflow.gridId,workflow.searchDivInput,workflow.params);
}

var workflowNodeArr;
var showAddModal = function(){
	$('#selWorkflowId').val('0');
	workflowNodeArr = new Array();
	$('#addWorkflowName').val('');
	$('#addWorkflowType').val('');
	$('#allNodeOl').empty();
	util.showModal('addWorkflowModal');
};

var clearAddnodeModal = function(){
	$('#addNodeName').val('');
	var treeObj = $.fn.zTree.getZTreeObj('occupationTree');
	treeObj.expandAll(true);
	treeObj.checkAllNodes(false);
	$('#searchStaffName').val('');
	$('#searchStaffNo').val('');
	$('#selPostDiv').empty().append('<span style="float:left;width:100%;">已选择的岗位(双击删除):</span>');
	$('#selStaffDiv').empty().append('<span style="float:left;width:100%;">已选择的员工(双击删除):</span>');
	staffsearchPargerList();
	$('#postName').val('');
	$('#postNo').val('');
	postsearchPargerList();
};

var addNode = function(){
	clearAddnodeModal();
	$('#addOrEdit').val('1000');
	$('#addNodeTitle').text('新增审批节点');
	$('#nodeInfo').show();
	$('#saveNodeBtn').show();
	$('#saveStartPersonBtn').hide();
	util.showModal('addNodeModal');
};

var saveNode = function(){
	var addNodeName = $('#addNodeName').val();
	if(isEmpty(addNodeName)){
		swal({
            title: "节点名称不能为空",
            type: "error"
	    });
		return;
	}
	var allHasSelPost = $("span[id^='selpost_']");
	var allHasSelStaff = $("span[id^='selstaff_']");
	var occupationTreeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var occupation_nodes = occupationTreeObj.getCheckedNodes(true);
	var allLi = $('#allNodeOl').find('li');
	var occupationIdArr = new Array();
	var selStaffIdArr = new Array();
	var selPostIdArr = new Array();
	var selOccupationNameArr = new Array();
	var selStaffNameArr = new Array();
	var selPostNameArr = new Array();
	var lititle = '';
	var addOrEdit = $('#addOrEdit').val();
	if(occupation_nodes.length == 0 && allHasSelPost.length == 0 && allHasSelStaff.length == 0){
		swal({
            title: "请选择职务,岗位或人员",
            type: "error"
	    });
		return;
	}
	if(occupation_nodes.length >0){
		lititle = lititle+"职务：";
		for(var i=0;i<occupation_nodes.length;i++){
			lititle= lititle+occupation_nodes[i].name+",";
			occupationIdArr.push(occupation_nodes[i].id);
			selOccupationNameArr.push(occupation_nodes[i].name);
		}
		lititle = lititle.substring(0,lititle.length-1)+";";
	}
	if(allHasSelPost.length>0){
		lititle = lititle+"岗位：";
		for(var i=0;i<allHasSelPost.length;i++){
			lititle= lititle+allHasSelPost[i].innerHTML+",";
			selPostIdArr.push(allHasSelPost[i].id.split('_')[1]);
			selPostNameArr.push(allHasSelPost[i].innerHTML);
		}
		lititle = lititle.substring(0,lititle.length-1)+";";
	}
	if(allHasSelStaff.length > 0){
		lititle = lititle+"员工：";
		for(var i=0;i<allHasSelStaff.length;i++){
			lititle= lititle+allHasSelStaff[i].innerHTML+",";
			selStaffIdArr.push(allHasSelStaff[i].id.split('_')[1]);
			selStaffNameArr.push(allHasSelStaff[i].innerHTML);
		}
		lititle = lititle.substring(0,lititle.length-1)+";";
	}
	if(addOrEdit == "1000"){//新增
		var obj={
				pointName:addNodeName,
				staffId:selStaffIdArr.join(','),
				postId:selPostIdArr.join(','),
				occupationId:occupationIdArr.join(','),
				occupationNames:selOccupationNameArr.join(','),
				staffNames:selStaffNameArr.join(','),
				postNames:selPostNameArr.join(',')
		}
		workflowNodeArr.push(obj);
		$('#allNodeOl').append('<li style="list-style-type:decimal;text-decoration:underline;font-size:15px;" title="'+lititle+'">'+addNodeName
				+' <i class="fa fa-wrench" style="margin-left:10px;cursor: pointer;" title="编辑" onclick="editNode('+allLi.length+
				')"></i><i class="fa fa-times" style="margin-left:10px;cursor: pointer;" title="删除" onclick ="deleteNode('+allLi.length+')"></i><i class="fa fa-arrow-up" style="margin-left:10px;cursor: pointer;" title="向上" onclick ="nodeUp('+allLi.length+');"></i><i class="fa fa-arrow-down" style="margin-left:10px;cursor: pointer;" title="向下" onclick ="nodeDown('+allLi.length+');"></i></li>');
		
	}else{//编辑
		var thiseditNode = workflowNodeArr[addOrEdit];
		thiseditNode.pointName = addNodeName;
		thiseditNode.staffId = selStaffIdArr.join(',');
		thiseditNode.postId = selPostIdArr.join(',');
		thiseditNode.occupationId = occupationIdArr.join(',');
		thiseditNode.occupationNames = selOccupationNameArr.join(',');
		thiseditNode.staffNames = selStaffNameArr.join(',');
		thiseditNode.postNames = selPostNameArr.join(',');
		$("#allNodeOl li:eq("+addOrEdit+")").empty().append(addNodeName+' <i class="fa fa-wrench" style="margin-left:10px;cursor: pointer;" title="编辑" onclick="editNode('+addOrEdit+
				')"></i><i class="fa fa-times" style="margin-left:10px;cursor: pointer;" title="删除" onclick ="deleteNode('+addOrEdit+')"></i><i class="fa fa-arrow-up" style="margin-left:10px;cursor: pointer;" title="向上" onclick ="nodeUp('+addOrEdit+');"></i><i class="fa fa-arrow-down" style="margin-left:10px;cursor: pointer;" title="向下" onclick ="nodeDown('+addOrEdit+');"></i>');
		$("#allNodeOl li:eq("+addOrEdit+")").attr('title',lititle);
	}
	util.hideModal('addNodeModal');
};	
var nodeUp = function(index){
	if(index == 0)
		return;
	var allLi = $('#allNodeOl').find('li');
	var a = allLi[index];
	var b = allLi[index - 1];
	allLi.splice(index,1,b);
	allLi.splice(index - 1,1,a);
	var c = workflowNodeArr[index];
	var d = workflowNodeArr[index - 1];
	workflowNodeArr.splice(index,1,d);
	workflowNodeArr.splice(index - 1,1,c);
	$('#allNodeOl').empty();
	for (var i = 0; i < allLi.length; i++) {
		$(allLi[i]).empty();
		$(allLi[i]).append(workflowNodeArr[i].pointName + ' <i class="fa fa-wrench" style="margin-left:10px;cursor: pointer;" title="编辑" onclick="editNode('+i+
				')"></i><i class="fa fa-times" style="margin-left:10px;cursor: pointer;" title="删除" onclick ="deleteNode('+i+')"></i><i class="fa fa-arrow-up" style="margin-left:10px;cursor: pointer;" title="向上" onclick ="nodeUp('+i+');"></i><i class="fa fa-arrow-down" style="margin-left:10px;cursor: pointer;" title="向下" onclick ="nodeDown('+i+');"></i>');
		$('#allNodeOl').append($(allLi[i]));
	}
}
var nodeDown = function(index){
	if(index == workflowNodeArr.length - 1)
		return;
	var allLi = $('#allNodeOl').find('li');
	var a = allLi[index];
	var b = allLi[index + 1];
	allLi.splice(index,1,b);
	allLi.splice(index + 1,1,a);
	var c = workflowNodeArr[index];
	var d = workflowNodeArr[index + 1];
	workflowNodeArr.splice(index,1,d);
	workflowNodeArr.splice(index + 1,1,c);
	$('#allNodeOl').empty();
	for (var i = 0; i < allLi.length; i++) {
		$(allLi[i]).empty();
		$(allLi[i]).append(workflowNodeArr[i].pointName + ' <i class="fa fa-wrench" style="margin-left:10px;cursor: pointer;" title="编辑" onclick="editNode('+i+
				')"></i><i class="fa fa-times" style="margin-left:10px;cursor: pointer;" title="删除" onclick ="deleteNode('+i+')"></i><i class="fa fa-arrow-up" style="margin-left:10px;cursor: pointer;" title="向上" onclick ="nodeUp('+i+');"></i><i class="fa fa-arrow-down" style="margin-left:10px;cursor: pointer;" title="向下" onclick ="nodeDown('+i+');"></i>');
		$('#allNodeOl').append($(allLi[i]));
	}
}
var editNode = function(selIndex){
	var node = workflowNodeArr[selIndex];
	clearAddnodeModal();
	$('#addOrEdit').val(selIndex);//1000:新增 其他:编辑
	$('#addNodeName').val(node.pointName);
	$('#selPostDiv').empty().append('<span style="float:left;width:100%;">已选择的岗位(双击删除):</span>');
	$('#selStaffDiv').empty().append('<span style="float:left;width:100%;">已选择的员工(双击删除):</span>');
	if(!isEmpty(node.occupationId)){
		var occupationIdArr = node.occupationId.split(',');
		if(occupationIdArr !=null && occupationIdArr.length>0){
			var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
			for (var i=0 ;i< occupationIdArr.length;i++){
				var treeNode = treeObj.getNodeByParam("id", occupationIdArr[i], null);
				if (treeNode)
					treeObj.checkNode(treeNode,true,false);
			}
		}
	}
	if(!isEmpty(node.staffId)){
		var staffIdArr = node.staffId.split(',');
		var staffNameArr = node.staffNames.split(',');
		if(staffIdArr != null && staffIdArr.length>0){
			for(var i=0;i<staffIdArr.length;i++){
				$('#selStaffDiv').append('<span id="selstaff_'+staffIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'
						+staffNameArr[i]+'</span>');
			}
		}
	}
	if(!isEmpty(node.postId)){
		var postIdArr = node.postId.split(',');
		var postNameArr = node.postNames.split(',');
		if(postIdArr != null && postIdArr.length>0){
			for(var i=0;i<postIdArr.length;i++){
				$('#selPostDiv').append('<span id="selpost_'+postIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'
						+postNameArr[i]+'</span>');
			}
		}
	}
	$('#addNodeTitle').text('新增审批节点');
	$('#nodeInfo').show();
	$('#saveNodeBtn').show();
	$('#saveStartPersonBtn').hide();
	util.showModal('addNodeModal');
};

var deleteNode = function(nodeIndex){
	$("#allNodeOl li:eq("+nodeIndex+")").remove();
	workflowNodeArr.splice(nodeIndex,1);
};

var addPost = function(){
	var postIdArr = $(workflow.postgridId).jqGrid("getGridParam", "selarrrow");
	if(postIdArr.length == 0){
		swal({
            title: "请选择岗位",
            type: "error"
	    });
		return;
	}
	if(postIdArr.length > 0){
		for(var i=0;i<postIdArr.length;i++){
			var rowData = $(workflow.postgridId).jqGrid('getRowData',postIdArr[i]);
			var allHasSel = $("span[id^='selpost_']");
			var canAdd = true;
			for(var j=0;j<allHasSel.length;j++){
				if(allHasSel[j].id == "selpost_"+postIdArr[i]){
					canAdd = false;
					break;
				}
			}
			if(canAdd)
				$('#selPostDiv').append('<span id="selpost_'+postIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'+rowData.postName+'</span>')
		}
	}
};

var addStaff = function(){
	var staffIdArr = $(workflow.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIdArr.length == 0){
		swal({
            title: "请选择员工",
            type: "error"
	    });
		return;
	}
	if(staffIdArr.length > 0){
		for(var i=0;i<staffIdArr.length;i++){
			var rowData = $(workflow.staffgridId).jqGrid('getRowData',staffIdArr[i]);
			var allHasSel = $("span[id^='selstaff_']");
			var canAdd = true;
			for(var j=0;j<allHasSel.length;j++){
				if(allHasSel[j].id == "selstaff_"+staffIdArr[i]){
					canAdd = false;
					break;
				}
			}
			if(canAdd)
				$('#selStaffDiv').append('<span id="selstaff_'+staffIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'+rowData.staffName+'</span>')
		}
	}
};

var saveWorkflow = function(){
	var addWorkflowName = $('#addWorkflowName').val();
	if(isEmpty(addWorkflowName)){
		swal({
            title: "流程名称必填",
            type: "error"
	    });
		return;
	}
	var addWorkflowType = $('#addWorkflowType').val();
	if(isEmpty(addWorkflowType)){
		swal({
            title: "请选择一个流程类别",
            type: "error"
	    });
		return;
	}
	if(workflowNodeArr.length<=0){
		swal({
            title: "请定义审批人",
            type: "error"
	    });
		return;
	}
	var workflowNodeArrStr = "";
	var num = 1;
	for(var i=0;i<workflowNodeArr.length;i++){
		var objStr = '{"pointName":"'+workflowNodeArr[i].pointName+'","num":'+num+',"staffId":"'+workflowNodeArr[i].staffId
		+'","postId":"'+workflowNodeArr[i].postId+'","occupationId":"'+workflowNodeArr[i].occupationId
		+'","occupationNames":"'+workflowNodeArr[i].occupationNames+'","staffNames":"'+workflowNodeArr[i].staffNames+'","postNames":"'+workflowNodeArr[i].postNames+'"}';
		workflowNodeArrStr = workflowNodeArrStr+objStr;
		if(i != workflowNodeArr.length-1)
			workflowNodeArrStr = workflowNodeArrStr+"#####";
		num++;
	}
	var saveOrUpdate = $('#selWorkflowId').val();//0 新增
	workflow.addWorkflowOptions.postData = {
			params : workflowNodeArrStr,
			wfName:addWorkflowName,
			workTypeId:addWorkflowType,
			saveOrUpdate:saveOrUpdate
	};
   util.commAjax(workflow.addWorkflowOptions);
};

workflow.delWorkflowOptions = {
		url		:	'/workflow/delete'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data >= 0){
				if(data == delWorkFlowNum){
					swal({
						title: "删除成功",
						type: "success"
					});
				}else{
					swal({
						title: "删除成功，有"+parseInt(delWorkFlowNum-data)+"条流程已被占用无法删除",
						type: "success"
					});
				}
				searchPargerList();
			}else{
				swal({
					title: "删除失败",
					type: "error"
				});
			}
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "删除失败!",
	            type: "error"
		    });
		}		
}
var delWorkFlowNum;
var deleteWorkflow = function(workflowId){
	var selWorkflowIds =  workflowId;
	if(workflowId == undefined){
		var workflowIds = $(workflow.gridId).jqGrid("getGridParam", "selarrrow");
		if(workflowIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selWorkflowIds = workflowIds.join(',');
		delWorkFlowNum = workflowIds.length;
	}else
		delWorkFlowNum = 1;
	workflow.delWorkflowOptions.postData = {
			workflowIds : selWorkflowIds
	};
   swal({
         title: "确定删除选择的流程?",
         type: "warning",
         showCancelButton: true,
         confirmButtonColor: "#DD6B55",
         confirmButtonText: "确定",
         cancelButtonText: "取消",
         closeOnConfirm: false,
         closeOnCancel: false },
	     function (isConfirm) {
	         if (isConfirm) {
	        	 util.commAjax(workflow.delWorkflowOptions);
	         } else {
	             swal("已取消删除", "", "error");
	         }
      });
};

var showEditModal = function(workflowId){
	$('#selWorkflowId').val(workflowId);
	var rowData = $(workflow.gridId).jqGrid('getRowData',workflowId);
	workflowNodeArr = new Array();
	$('#addWorkflowName').val(rowData['name']);
	$('#addWorkflowType').val(rowData['workFlowType.id']);
	$('#allNodeOl').empty();
	workflow.getWorkflowInfoOptions.postData = {
			wfId : workflowId
	};
	util.commAjax(workflow.getWorkflowInfoOptions);
};

var showEditStartModal = function(){
	var workFlowIdArr = $(workflow.gridId).jqGrid("getGridParam", "selarrrow");
	if(workFlowIdArr.length <=0){
		swal({
			title: "至少选择一个流程编辑申请人"
		});
		return;
	}
	clearAddnodeModal();
	$('#addNodeTitle').text('编辑申请人');
	$('#nodeInfo').hide();
	$('#selPostDiv').empty().append('<span style="float:left;width:100%;">已选择的岗位(双击删除):</span>');
	$('#selStaffDiv').empty().append('<span style="float:left;width:100%;">已选择的员工(双击删除):</span>');
	$('#selWorkflowIdForStartPer').val(workFlowIdArr.join(','));
	if(workFlowIdArr.length == 1){//如果只选择了一个流程则反填
		var rowData = $(workflow.gridId).jqGrid('getRowData',workFlowIdArr[0]);
		var staffIds = rowData['staffIds'];
		var staffNames = rowData['staffNames'];
		var occupationIds = rowData['occupationIds'];
		var postIds = rowData['postIds'];
		var postNames = rowData['postNames'];
		if(!isEmpty(occupationIds)){
			var occupationIdArr = occupationIds.split(',');
			if(occupationIdArr !=null && occupationIdArr.length>0){
				var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
				for (var i=0 ;i< occupationIdArr.length;i++){
					var treeNode = treeObj.getNodeByParam("id", occupationIdArr[i], null);
					if (treeNode)
						treeObj.checkNode(treeNode,true,false);
				}
			}
		}
		if(!isEmpty(staffIds)){
			var staffIdArr = staffIds.split(',');
			var staffNameArr = staffNames.split(',');
			if(staffIdArr != null && staffIdArr.length>0){
				for(var i=0;i<staffIdArr.length;i++){
					$('#selStaffDiv').append('<span id="selstaff_'+staffIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'
							+staffNameArr[i]+'</span>');
				}
			}
		}
		if(!isEmpty(postIds)){
			var postIdArr = postIds.split(',');
			var postNameArr = postNames.split(',');
			if(postIdArr != null && postIdArr.length>0){
				for(var i=0;i<postIdArr.length;i++){
					$('#selPostDiv').append('<span id="selpost_'+postIdArr[i]+'" style="float:left;width:100%;margin-top:5px;cursor:pointer;" ondblclick="$(this).remove();">'
							+postNameArr[i]+'</span>');
				}
			}
		}
	}
	$('#saveNodeBtn').hide();
	$('#saveStartPersonBtn').show();
	util.showModal('addNodeModal');
};

workflow.editStartPersonOptions = {
		url		:	'/workflow/editStartPerson'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data ==1){
				swal({
					title: "编辑申请人成功",
					type: "success"
				});
				searchPargerList();
				util.hideModal('addNodeModal');
			}else{
				swal({
					title: "编辑申请人失败",
					type: "error"
				});
			}
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "编辑申请人失败!",
	            type: "error"
		    });
		}		
}

var saveStartPerson = function(){
	var allHasSelPost = $("span[id^='selpost_']");
	var allHasSelStaff = $("span[id^='selstaff_']");
	var occupationTreeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var occupation_nodes = occupationTreeObj.getCheckedNodes(true);
	var occupationIdArr = new Array();
	var selStaffIdArr = new Array();
	var selPostIdArr = new Array();
	var selOccupationNameArr = new Array();
	var selStaffNameArr = new Array();
	var selPostNameArr = new Array();
	if(occupation_nodes.length == 0 && allHasSelPost.length == 0 && allHasSelStaff.length == 0){
		swal({
            title: "请选择职务,岗位或人员",
            type: "error"
	    });
		return;
	}
	if(occupation_nodes.length >0){
		for(var i=0;i<occupation_nodes.length;i++){
			occupationIdArr.push(occupation_nodes[i].id);
			selOccupationNameArr.push(occupation_nodes[i].name);
		}
	}
	if(allHasSelPost.length>0){
		for(var i=0;i<allHasSelPost.length;i++){
			selPostIdArr.push(allHasSelPost[i].id.split('_')[1]);
			selPostNameArr.push(allHasSelPost[i].innerHTML);
		}
	}
	if(allHasSelStaff.length > 0){
		for(var i=0;i<allHasSelStaff.length;i++){
			selStaffIdArr.push(allHasSelStaff[i].id.split('_')[1]);
			selStaffNameArr.push(allHasSelStaff[i].innerHTML);
		}
	}
	workflow.editStartPersonOptions.postData = {
			wfIds:$('#selWorkflowIdForStartPer').val(),
			staffIds:selStaffIdArr.join(','),
			staffNames:selStaffNameArr.join(','),
			postIds:selPostIdArr.join(','),
			postNames:selPostNameArr.join(','),
			occupationIds:occupationIdArr.join(','),
			occupationNames:selOccupationNameArr.join(',')
	}
	util.commAjax(workflow.editStartPersonOptions);
};

