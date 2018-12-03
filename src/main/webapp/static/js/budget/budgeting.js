var budgeting = function(){};
budgeting.departmentTree = '#departmentTree';
budgeting.gridId = "#table_budgeting";
budgeting.pagerId = "#pager_budgeting";
budgeting.searchDivInput = "#searchBudgetingDiv :input";
budgeting.params = 'params';

budgeting.gridHistoryId = "#table_budgeting_history";
budgeting.pagerHistoryId = "#pager_budgeting_history";
budgeting.searchDivHistoryInput = "#searchBudgetingHistoyDiv :input";
var budgetModel = function(){};
$(function(){
	util.initSelectMenu('budgetingLi');
	$(budgeting.gridId).jqGrid(budgeting.gridOption);
	$(budgeting.gridHistoryId).jqGrid(budgeting.gridHistoryOption);
	$(window).resize(function(){
		$(budgeting.gridId).setGridWidth(util.getGridWith());
	});
});
var returnHistoryWidth = function(objId){
	var jqObj = $('#'+objId);
	if(jqObj.parent().attr('class') == 'active')
		return;
	$(budgeting.gridHistoryId).setGridWidth(util.getGridWith());
}
var operationFormatter = function(cellvalue,rowObject,subgrid_table_id){
	var result ="";
	if(rowObject.ifChild == 1 && rowObject.financeApproval.approvalFlag != 100)
		result= '<button type="button" class="btn btn-xs btn-primary operationBtn" onclick="showEditModal('+subgrid_table_id+','+cellvalue+')"><i class="fa fa-paste"></i>编辑</button>'
	+'<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="delBudgetingSubject('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
	return result;
};

var nameFormatter = function(cellvalue,rowObject){
	return cellvalue+"("+(rowObject.financeSubjects.level-1)+")";
};

budgeting.gridOption = {
		url : context + "/budgeting/list",
		caption : "预算编制",
		postData : {
			'param' : {}
		},
		colNames : ['编号','名称','年份','驳回意见'],
		colModel : [ 
		    {name: 'id', hidden:true},
		    {name: 'financeApprovalName', width:100,align:"center",sorttype:"string"},
		    {name: 'financeYear', width:100,align:"center",sorttype:"string" },
		    {name: 'rejectReason', width:100,align:"center",sorttype:"string" }
		],
		mtype : "POST",
		datatype : "json",
		jsonReader : {
		repeatitems : false,
		},
		emptyrecords : "无符合条件数据",
		rowNum : 20,
		height : '100%',
		pager : budgeting.pagerId,
		autowidth : true,
		shrinkToFit:true,
		autoScroll: true,
		gridview : false,
		viewrecords : true,
		multiselect : true,
		multiselectWidth : 25,
		subGrid: true,
		subGridRowExpanded: function(subgrid_id, row_id) { 
				var subgrid_table_id, pager_id; 
				subgrid_table_id = subgrid_id+"_t"; 
				pager_id = "p_"+subgrid_table_id; 
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>"); 
				jQuery("#"+subgrid_table_id).jqGrid({ 
					url : context + "/budgeting/allSubjectInfo", 
					postData : {
						'id' : row_id
					},
					colNames : [ '项目','1月','2月','3月','4月','5月','6月','7月','8月' ,'9月','10月','11月','12月','合计','操作'],
					colModel : [ 
					    {name: 'financeSubjects.name', width:80,align:"center",formatter:function(cellvalue, options, rowObject){return nameFormatter(cellvalue,rowObject);}},
					    {name: 'subjectFinanceJanuary', width:80,align:"center"},
					    {name: 'subjectFinanceFebruary', width:80,align:"center"},
					    {name: 'subjectFinanceMarch', width:80,align:"center"},
					    {name: 'subjectFinanceApril', width:80,align:"center"},
						 {name: 'subjectFinanceMay', width:80,align:"center"},
						 {name: 'subjectFinanceJune', width:80,align:"center"},
						 {name: 'subjectFinanceJuly', width:80,align:"center"},
						 {name: 'subjectFinanceAugust', width:80,align:"center"},
					    {name: 'subjectFinanceSeptember', width:80,align:"center"},
					    {name: 'subjectFinanceOctober', width:80,align:"center"},
					    {name: 'subjectFinanceNovember', width:80,align:"center"},
					    {name: 'subjectFinanceDecember', width:80,align:"center"},
					    {name: 'subjectFinanceAll', width:100,align:"center"},
					    {name: 'id', width:100,align:"center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue,rowObject,subgrid_table_id);}}
					],
					mtype : "POST",
					datatype : "json",
					jsonReader : {
					repeatitems : false,
					},
					autowidth : true,
					autoScroll: true,
					emptyrecords : "无符合条件数据",
					rowNum:1000, 
					pager: pager_id, 
					height: '100%' ,
					viewrecords : true // 显示总记录数
				}); 
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false}) 
		}
};

budgeting.gridHistoryOption = {
		url : context + "/budgeting/listHistory",
		caption : "预算编制历史",
		postData : {
			'param' : {}
		},
		colNames : ['编号','名称','年份'],
		colModel : [ 
		    {name: 'id', hidden:true},
		    {name: 'financeApprovalName', width:100,align:"center",sorttype:"string"},
		    {name: 'financeYear', width:100,align:"center",sorttype:"string" }
		],
		mtype : "POST",
		datatype : "json",
		jsonReader : {
		repeatitems : false,
		},
		emptyrecords : "无符合条件数据",
		rowNum : 20,
		height : '100%',
		pager : budgeting.pagerHistoryId,
		autowidth : true,
		shrinkToFit:true,
		autoScroll: true,
		gridview : false,
		viewrecords : true,
		multiselect : true,
		multiselectWidth : 25,
		subGrid: true,
		subGridRowExpanded: function(subgrid_id, row_id) { 
				var subgrid_table_id, pager_id; 
				subgrid_table_id = subgrid_id+"_t"; 
				pager_id = "p_"+subgrid_table_id; 
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>"); 
				jQuery("#"+subgrid_table_id).jqGrid({ 
					url : context + "/budgeting/allSubjectInfo", 
					postData : {
						'id' : row_id
					},
					colNames : [ '项目','1月','2月','3月','4月','5月','6月','7月','8月' ,'9月','10月','11月','12月','合计','操作'],
					colModel : [ 
					    {name: 'financeSubjects.name', width:80,align:"center",formatter:function(cellvalue, options, rowObject){return nameFormatter(cellvalue,rowObject);}},
					    {name: 'subjectFinanceJanuary', width:80,align:"center"},
					    {name: 'subjectFinanceFebruary', width:80,align:"center"},
					    {name: 'subjectFinanceMarch', width:80,align:"center"},
					    {name: 'subjectFinanceApril', width:80,align:"center"},
						 {name: 'subjectFinanceMay', width:80,align:"center"},
						 {name: 'subjectFinanceJune', width:80,align:"center"},
						 {name: 'subjectFinanceJuly', width:80,align:"center"},
						 {name: 'subjectFinanceAugust', width:80,align:"center"},
					    {name: 'subjectFinanceSeptember', width:80,align:"center"},
					    {name: 'subjectFinanceOctober', width:80,align:"center"},
					    {name: 'subjectFinanceNovember', width:80,align:"center"},
					    {name: 'subjectFinanceDecember', width:80,align:"center"},
					    {name: 'subjectFinanceAll', width:100,align:"center"},
					    {name: 'id', width:100,align:"center"}
					],
					mtype : "POST",
					datatype : "json",
					jsonReader : {
					repeatitems : false,
					},
					autowidth : true,
					autoScroll: true,
					emptyrecords : "无符合条件数据",
					rowNum:1000, 
					pager: pager_id, 
					height: '100%' ,
					viewrecords : true // 显示总记录数
				}); 
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false}) 
		}
};

var searchPargerList = function(){
	util.searchListWithParams(budgeting.gridId,budgeting.searchDivInput,budgeting.params);
};

var showAddModal = function(){
	$(budgetModel.gridId).jqGrid(budgetModel.gridOption);
	$('#addName').val('');
	$('#addYear').val('');
	$('#firstStep').show();
	$('#secondStep').hide();
	searchPargerListForModel();
	util.commAjax(budgeting.loadDepartmentTreeNodesOptions);
	util.showModal('addBudgetingModal');
};

var showEditModal = function(tableId,id){
	var rowData = $("#"+tableId.id).jqGrid('getRowData', id);
	$('#subjectName').text(rowData["financeSubjects.name"]);
	$('#subjectFinanceJanuary').val(rowData["subjectFinanceJanuary"]);
	$('#subjectFinanceFebruary').val(rowData["subjectFinanceFebruary"]);
	$('#subjectFinanceMarch').val(rowData["subjectFinanceMarch"]);
	$('#subjectFinanceApril').val(rowData["subjectFinanceApril"]);
	$('#subjectFinanceMay').val(rowData["subjectFinanceMay"]);
	$('#subjectFinanceJune').val(rowData["subjectFinanceJune"]);
	$('#subjectFinanceJuly').val(rowData["subjectFinanceJuly"]);
	$('#subjectFinanceAugust').val(rowData["subjectFinanceAugust"]);
	$('#subjectFinanceSeptember').val(rowData["subjectFinanceSeptember"]);
	$('#subjectFinanceOctober').val(rowData["subjectFinanceOctober"]);
	$('#subjectFinanceNovember').val(rowData["subjectFinanceNovember"]);
	$('#subjectFinanceDecember').val(rowData["subjectFinanceDecember"]);
	$('#totalForEdit').val(rowData["subjectFinanceAll"]);
	$('#selOneSubId').val(id);
	util.showModal('editOneSubjectModal');
};

var delBudgetingSubject = function(id){
	budgeting.delOneSubjectOptions.postData = {
			id : id
	};
	swal({
        title: "如果该项目的父节点只有该项目一个子节点,则父节点也将同时被删除,确定删除选择的项目?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(budgeting.delOneSubjectOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}

var changeSubjectForEdit = function(){
	var total = 0;
	$('.oneForEdit').each(function(){
		total +=parseInt($(this).val());
	})
	$('#totalForEdit').val(total);
};

var saveEditOneSubject = function(){
	var canSave = util.validate("editOneSubjectDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#editOneSubjectDiv :input');
	var selectId = $('#selOneSubId').val();
	budgeting.editOneSubjectOptions.postData = {
		params : params,
		subjectId : selectId
	};
	util.commAjax(budgeting.editOneSubjectOptions);
};

budgeting.editOneSubjectOptions = 
{
		
		url		:	'/budgeting/editOneSubject'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "编辑成功!",
                type: "success"
		        },function(){
		        	util.hideModal('editOneSubjectModal'); 
		        	searchPargerList();
		        });
				searchPargerList();
			}else if (data === -2) {
				swal({
	                title: "已结束的流程或者不是最底层节点不能编辑!",
	                type: "error"
			        },function(){
			        	util.hideModal('editOneSubjectModal'); 
			        });
			}else
				swal({
					title: "编辑失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "编辑失败!",
	            type: "error"
		    });
		}		
};

budgeting.getubjectForModelOptions = 
{
		
		url		:	'/budgetModel/getAllSubject'						
	,	mtype	:	'POST'					
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data) {
				$('#firstStep').hide();
				$('#subjectContent').empty();
				for(var i=0;i<data.length;i++){
					var selfId = data[i].id;
					var level = data[i].level-2;
					var pId = data[i].pId;
					var isParent = false;
					for(var k=i;k<data.length;k++){
						if(data[k].pId == selfId){
							isParent = true;
							break;
						}
					}
					var tr;
					if(!isParent)
						tr=$('<tr class="NotParent"><td id="name_'+selfId+'" style="border:solid 2px #add9c0;width:140px;"><div style="margin-left:'+level*8+'px" title="'+data[i].remark+'">'+data[i].name+'</div></td></tr>');
					else
						tr=$('<tr><td id="name_'+selfId+'" style="border:solid 2px #add9c0;width:140px;"><div style="margin-left:'+level*8+'px" title="'+data[i].remark+'">'+data[i].name+'</div></td></tr>');
					for(var j=1;j<=12;j++){
						if(!isParent)
							tr.append('<td style="border:solid 2px #add9c0;"><input id="subject_id'+selfId+'_pId'+pId+'_'+j+'month" type="text" class="noBorderInput level_'+level+'" value="0" onchange="changeSubjectForRow(this.id);changeSubjectForCell(this.id)"></td>');
						else
							tr.append('<td style="border:solid 2px #add9c0;"><input id="subject_id'+selfId+'_pId'+pId+'_'+j+'month" type="text" class="noBorderInput level_'+level+'" style="background-color:#C4C4C4;" value="0" onchange="changeSubjectForRow(this.id);changeSubjectForCell(this.id)" readonly="readonly"></td>');
					}
					tr.append('<td style="border:solid 2px #add9c0;"><input type="text" id="total_id'+selfId+'_pId'+pId+'" class="noBorderInput level_'+level+'" style="background-color:#C4C4C4;" value="0" readonly="readonly"></td>');
					$('#subjectContent').append(tr);
				}
				$('#secondStep').show();
			}else
				swal({
					title: "服务器遇到问题!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "服务器遇到问题!",
	            type: "error"
		    });
		}		
};

var nextStep = function(){
	var addName = $('#addName').val();
	if(isEmpty(addName)){
		swal({
            title: "名称必填!",
            type: "error"
	    });
		return;
	}
	var addYear = $('#addYear').val();
	if(isEmpty(addYear)){
		swal({
            title: "年份必选!",
            type: "error"
	    });
		return;
	}
	$('#nameAndYear').text(addYear+"年--"+addName);
	//拼数据
	var modelIds = $(budgetModel.gridId).jqGrid("getGridParam", "selarrrow");
	if(modelIds.length == 0 || modelIds.length >1){
		swal({
			title: "请选择一个模板"
		});
		return;
	}
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个部门!",
            type: "error"
	    });
		return false;
	}
	budgeting.getubjectForModelOptions.postData={
			id:modelIds[0]
	}
	util.commAjax(budgeting.getubjectForModelOptions);
};

var changeSubjectForRow = function(id){
	if(id == undefined)
		return;
	var subjectArr = id.split('_');
	var totalId = "total_id"+subjectArr[1].substring(2,subjectArr[1].legnth)+"_pId"+subjectArr[2].substring(3,subjectArr[2].legnth);
	var subjectId = "subject_id"+subjectArr[1].substring(2,subjectArr[1].legnth)+"_pId"+subjectArr[2].substring(3,subjectArr[2].legnth);
	var oneSubjectArr = $("input[id^='"+subjectId+"']");
	var total = 0;
	for(var i=0;i<oneSubjectArr.length;i++){
		total+=parseInt(oneSubjectArr[i].value);
	}
	$('#'+totalId).val(total);
};

var changeSubjectForCell = function(id){
	var classes = $('#'+id).attr('class');
	var classesArr = classes.split(' ');
	var levelClass = classesArr[1];
	var level = levelClass.split('_')[1]; 
	var subjectArr = id.split('_');
	var selfId = subjectArr[1].substring(2,subjectArr[1].legnth);
	var pId = subjectArr[2];
	var month = subjectArr[3];
	var subjectId = "subject_id"+pId.substring(3,pId.legnth)+"_pId";
	var parentSubject = $("input[id^='"+subjectId+"'][id$='_"+month+"']");
	var pIdAndMonth = "_"+pId+"_"+month;
	var total  = 0;
	var oneMonthArr = $("input[id$='"+pIdAndMonth+"']");
	if(oneMonthArr.length > 0){
		for(var i=0;i<oneMonthArr.length;i++){
			total+=parseInt(oneMonthArr[i].value);
		}
	}
	parentSubject.val(total);
	if(parentSubject != undefined){
		changeSubjectForRow(parentSubject.attr('id'));
	}
	if(level >1){
		changeSubjectForCell(parentSubject.attr('id'));
	}
}

budgeting.departmentTreeSetting = {
		check: {
			enable: false,
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

budgeting.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(budgeting.departmentTree ), budgeting.departmentTreeSetting, data);
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

var saveBudgeting = function(){
	var addName = $('#addName').val();
	var addYear = $('#addYear').val();
	var allSubjectArr = new Array();
	var oneSubject;
	$('#subjectContent tr').each(function(){
		oneSubject="";
		oneSubject = oneSubject+$(this).find('td:eq(0)').attr('id').split('_')[1]+"#";
		var otherTd = $(this).find('td:gt(0)');
		for(var i=0;i<otherTd.length;i++){
			if(i<otherTd.length-1)
				oneSubject +=$(otherTd[i]).find('input').val()+"#";
			else
				oneSubject +=$(otherTd[i]).find('input').val();
		}
		if($(this).hasClass('NotParent'))
			oneSubject +="#1";
		else
			oneSubject +="#0";
		allSubjectArr.push(oneSubject);
	})
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var selectNodes = treeObj.getSelectedNodes();
	budgeting.addBudgetingOptions.postData={
		params :allSubjectArr.join('&&'),
		financeName:addName,
		year:addYear,
		departmentId:selectNodes[0].id
	}
	util.commAjax(budgeting.addBudgetingOptions);
};

budgeting.addBudgetingOptions = {
		url		:	'/budgeting/add'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "保存成功!",
		                type: "success"
			        },
			        function(){
			        	util.hideModal('addBudgetingModal');
			        	searchPargerList();
			        });
			}else
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
};

budgeting.delBudgetingOptions = 
{
		
		url		:	'/budgeting/delete'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "删除成功！",
					type: "success"
				},function(){
					searchPargerList();
				});
			}
			else if(data == -1){
				swal({
					title: "审批过程中不得删除！",
					type: "error"
				});
			}else{
				swal({
					title: "删除失败！",
					type: "error"
				});
			}
				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "删除失败!",
	            type: "error"
		    });
		}		
};

var deleteBudgeting = function(){
	var budgetingIds = $(budgeting.gridId).jqGrid("getGridParam", "selarrrow");
	if(budgetingIds.length == 0 || budgetingIds.length >1){
		swal({
			title: "请选择一条记录删除"
		});
		return;
	}
	budgeting.delBudgetingOptions.postData = {
			id : budgetingIds[0]
	};
	swal({
        title: "确定删除选择的预算编制?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(budgeting.delBudgetingOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
};

budgeting.delOneSubjectOptions = 
{
		
		url		:	'/budgeting/deleteOneSebject'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "删除成功！",
					type: "success"
				});
			}else if(data == -2){
				swal({
					title: "已结束的流程或者不是最底层节点不能删除！",
					type: "success"
				});
			}else{
				swal({
					title: "删除失败！",
					type: "error"
				});
			}
				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "删除失败!",
	            type: "error"
		    });
		}		
};

var startWorkFlow = function(){
	var budgetingIds = $(budgeting.gridId).jqGrid("getGridParam", "selarrrow");
	if(budgetingIds.length == 0 || budgetingIds.length >1){
		swal({
			title: "请选择一条记录提交申请"
		});
		return;
	}
	budgeting.startWorkflowOptions.postData = {
			id : budgetingIds[0]
	};
	util.commAjax(budgeting.startWorkflowOptions);
};

budgeting.startWorkflowOptions = 
{
		
		url		:	'/budgeting/startWorkFlow'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "提交申请成功！",
					type: "success"
				},function(){
					searchPargerList();
				});
			}else if(data == -1){
				swal({
					title: "没有审批人！",
					type: "error"
				});
			}else{
				swal({
					title: "提交申请失败！",
					type: "error"
				});
			}
//				searchPargerList();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "提交申请失败!",
	            type: "error"
		    });
		}		
};

budgetModel.gridId = "#table_budgetModel";
budgetModel.pagerId = "#pager_budgetModel";
budgetModel.searchDivInput = "#searchBudgeModelDiv :input";
budgetModel.params = 'params';
var modelIds = new Array();
budgetModel.gridOption = {
		url : context + "/budgetModel/list",
		caption : "预算编制模板",
		postData : {
			'param' : {}
		},
		colNames : ['名称','备注','id'],
		colModel : [ 
		    {name: 'name', width:100,align:"center",sorttype:"string"},
		    {name: 'remark', width:350,align:"center"},
		    {name: 'id', hidden:true},
		],
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: budgetModel.pagerId,  
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(budgetModel.gridId, modelIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(modelIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(modelIds, rowIds, status);
			else
				macIds = new Array();
		}
};

var searchPargerListForModel = function(){
	util.searchListWithParams(budgetModel.gridId,budgetModel.searchDivInput,budgetModel.params);
};
