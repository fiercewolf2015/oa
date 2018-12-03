var budgetApproval = function(){};
budgetApproval.gridId = "#table_budgetApproval";
budgetApproval.pagerId = "#pager_budgetApproval";
budgetApproval.searchDivInput = "#searchBudgetApprovalDiv :input";
budgetApproval.params = 'params';
budgetApproval.simpleGridId = "#table_budgetSimple";
budgetApproval.simplePagerId = "#pager_budgetSimple";
budgetApproval.simpleSearchDivInput = "#searchBudgetApprovalDiv :input";

$(function(){
	util.initSelectMenu('budgetApprovalLi');
	$(budgetApproval.gridId).jqGrid(budgetApproval.gridOption);
	$(window).resize(function(){
		$(budgetApproval.gridId).setGridWidth(util.getGridWith());
	});
	$(budgetApproval.simpleGridId).jqGrid(budgetApproval.simpleGridOption);
	$(budgetApproval.simpleGridId).setGridWidth(util.getGridWith());
});

var nameFormatter = function(cellvalue,rowObject){
	return cellvalue+"("+(rowObject.financeSubjects.level-1)+")";
};

budgetApproval.gridOption = {
		url : context + "/budgetApproval/list",
		caption : "预算编制审批",
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
		pager : budgetApproval.pagerId,
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
					colNames : [ '项目','1月','2月','3月','4月','5月','6月','7月','8月' ,'9月','10月','11月','12月','合计'],
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
					    {name: 'subjectFinanceAll', width:100,align:"center"}
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
		}, 
		subGridRowColapsed: function(subgrid_id, row_id) { 
		}
		
};
var colNames = [ '部门(项目)名称','年份','1月','2月','3月','4月','5月','6月','7月','8月' ,'9月','10月','11月','12月','合计','id','departmentId','ifChild'];
var colModel = [ 
		    {name: 'name', width:80,align:"center"},
		    {name: 'year', width:80,align:"center"},
		    {name: 'january', width:80,align:"center"},
		    {name: 'february', width:80,align:"center"},
		    {name: 'march', width:80,align:"center"},
		    {name: 'april', width:80,align:"center"},
			 {name: 'may', width:80,align:"center"},
			 {name: 'june', width:80,align:"center"},
			 {name: 'july', width:80,align:"center"},
			 {name: 'august', width:80,align:"center"},
		    {name: 'september', width:80,align:"center"},
		    {name: 'october', width:80,align:"center"},
		    {name: 'november', width:80,align:"center"},
		    {name: 'december', width:80,align:"center"},
		    {name: 'all', width:100,align:"center"},
		    {name: 'id', hidden:true},
		    {name: 'departmentId', hidden:true},
		    {name: 'ifChild', hidden:true}];

budgetApproval.simpleGridOption = {
		url : context + "/budgetApproval/listForSimple",
		caption : "预算编制简表",
		postData : {
			'param' : {}
		},
		colNames : colNames,
		colModel : colModel,
		mtype : "POST",
		datatype : "json",
		jsonReader : {
		repeatitems : false,
		},
		emptyrecords : "无符合条件数据",
		rowNum : 20,
		height : '100%',
		pager : budgetApproval.simplePagerId,
		autowidth : true,
		shrinkToFit:true,
		autoScroll: true,
		gridview : false,
		viewrecords : true,
		subGrid: false,
		subGridRowExpanded: function(subgrid_id, row_id) { 
			   var rowData = $(budgetApproval.simpleGridId).jqGrid('getRowData', row_id);
			   var year = rowData["year"];
			   var departmentId = rowData["departmentId"];
				var subgrid_table_id, pager_id; 
				subgrid_table_id = subgrid_id+"_t"; 
				pager_id = "p_"+subgrid_table_id; 
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>"); 
				jQuery("#"+subgrid_table_id).jqGrid({ 
					url : context + "/budgetApproval/allDepartment", 
					postData : {
						'id' : departmentId,
						'year':year
					},
					colNames : colNames,
					colModel : colModel,
					mtype : "POST",
					datatype : "json",
					jsonReader : {
					repeatitems : false,
					},
					autowidth : true,
					autoScroll: true,
					emptyrecords : "无符合条件数据",
					rowNum:1000, 
					height: '100%' ,
					viewrecords : true, // 显示总记录数
					subGrid: true,
					subGridRowExpanded: function(subgrid_id_2, row_id_2) { 
							var subgrid_table_id_2, pager_id_2; 
							subgrid_table_id_2 = subgrid_id_2+"_t"; 
							pager_id_2 = "p_"+subgrid_table_id_2; 
							$("#"+subgrid_id_2).html("<table id='"+subgrid_table_id_2+"' class='scroll'></table><div id='"+pager_id_2+"' class='scroll'></div>"); 
							jQuery("#"+subgrid_table_id_2).jqGrid({ 
								url : context + "/budgetApproval/differentLevel", 
								postData : {
									'id' : row_id_2,
									'level':2,
									'year':year
								},
								colNames : colNames,
								colModel : colModel,
								mtype : "POST",
								datatype : "json",
								jsonReader : {
								repeatitems : false,
								},
								autowidth : true,
								autoScroll: true,
								emptyrecords : "无符合条件数据",
								rowNum:1000, 
								height: '100%' ,
								viewrecords : true, // 显示总记录数
								subGrid: true,
								subGridRowExpanded: function(subgrid_id_3, row_id_3) { 
										var subgrid_table_id_3, pager_id_3; 
										subgrid_table_id_3 = subgrid_id_3+"_t"; 
										pager_id_3 = "p_"+subgrid_table_id_3; 
										$("#"+subgrid_id_3).html("<table id='"+subgrid_table_id_3+"' class='scroll'></table><div id='"+pager_id_3+"' class='scroll'></div>");
										jQuery("#"+subgrid_table_id_3).jqGrid({ 
											url : context + "/budgetApproval/differentLevel", 
											postData : {
												'id' : row_id_3,
												'level':3,
												'year':year
											},
											colNames : colNames,
											colModel : colModel,
											mtype : "POST",
											datatype : "json",
											jsonReader : {
												repeatitems : false,
											},
											autowidth : true,
											autoScroll: true,
											emptyrecords : "无符合条件数据",
											rowNum:1000, 
											height: '100%' ,
											viewrecords : true, // 显示总记录数
											subGrid: true,
											subGridRowExpanded: function(subgrid_id_4, row_id_4) {
												var gridArr = subgrid_id_4.split('_');
												var gridNewArr = new Array();
												for(var i=0;i<gridArr.length-1;i++){
													gridNewArr.push(gridArr[i]);
												}
											   var rowData = $("#"+gridNewArr.join('_')).jqGrid('getRowData', row_id_4);
											   var ifChild = rowData["ifChild"];
												var subgrid_table_id_4, pager_id_4; 
												subgrid_table_id_4 = subgrid_id_4+"_t"; 
												pager_id_4 = "p_"+subgrid_table_id_4; 
												if(ifChild == 0){
													$("#"+subgrid_id_4).html("<table id='"+subgrid_table_id_4+"' class='scroll'></table><div id='"+pager_id_4+"' class='scroll'></div>");
													jQuery("#"+subgrid_table_id_4).jqGrid({ 
														url : context + "/budgetApproval/differentLevel", 
														postData : {
															'id' : row_id_4,
															'level':4,
															'year':year
														},
														colNames : colNames,
														colModel : colModel,
														mtype : "POST",
														datatype : "json",
														jsonReader : {
															repeatitems : false,
														},
														autowidth : true,
														autoScroll: true,
														emptyrecords : "无符合条件数据",
														rowNum:1000, 
														height: '100%' ,
														viewrecords : true // 显示总记录数
													}); 
													jQuery("#"+subgrid_table_id_4).jqGrid('navGrid',"#"+pager_id_4,{edit:false,add:false,del:false,search:false}) 
												}
											}
										}); 
										jQuery("#"+subgrid_table_id_3).jqGrid('navGrid',"#"+pager_id_3,{edit:false,add:false,del:false,search:false}) 
								}
							}); 
							jQuery("#"+subgrid_table_id_2).jqGrid('navGrid',"#"+pager_id_2,{edit:false,add:false,del:false,search:false}) 
					} 
				}); 
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false}) 
		}
};
var searchPargerList = function(){
	util.searchListWithParams(budgetApproval.gridId,budgetApproval.searchDivInput,budgetApproval.params);
};
var searchPargerListForSimple = function(){
	util.searchListWithParams(budgetApproval.simpleGridId,budgetApproval.simpleSearchDivInput,budgetApproval.params);
};

var agree = function(){
	var agreeIds = $(budgetApproval.gridId).jqGrid("getGridParam", "selarrrow");
	if(agreeIds.length == 0 || agreeIds.length >1){
		swal({
			title: "请选择一条记录同意"
		});
		return;
	}
	budgetApproval.agreeOptions.postData = {
			id : agreeIds[0]
	};
	util.commAjax(budgetApproval.agreeOptions);
};

budgetApproval.agreeOptions = 
{
		
		url		:	'/budgeting/startWorkFlow'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "操作成功！",
					type: "success"
				});
				searchPargerList();
			}else if(data == -1){
				swal({
					title: "没有审批人！",
					type: "error"
				});
			}else{
				swal({
					title: "操作失败！",
					type: "error"
				});
			}
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "操作失败!",
	            type: "error"
		    });
		}		
};

var reject = function(){
	var agreeIds = $(budgetApproval.gridId).jqGrid("getGridParam", "selarrrow");
	if(agreeIds.length == 0 || agreeIds.length >1){
		swal({
			title: "请选择一条记录驳回"
		});
		return;
	}
	$('#rejectReason').val('');
	util.showModal('rejectModal');
};

var realReject = function(){
	var reason = $('#rejectReason').val();
	if(isEmpty(reason)){
		swal({
            title: "请填写驳回意见!",
            type: "error"
	    });
		return;
	}
	var rejectIds = $(budgetApproval.gridId).jqGrid("getGridParam", "selarrrow");
	budgetApproval.regectOptions.postData = {
			id : rejectIds[0],
			reason:reason
	};
	util.commAjax(budgetApproval.regectOptions);
}

budgetApproval.regectOptions = 
{
		
		url		:	'/budgetApproval/reject'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
	                title: "操作成功!",
	                type: "success"
		        },
		        function(){
		        	util.hideModal('rejectModal');
		        	searchPargerList();
		        });
			}else{
				swal({
					title: "操作失败！",
					type: "error"
				});
			}
		}	
	,	error	:	function(xhr,st,err){		
			swal({
				title: "操作失败!",
	            type: "error"
		    });
		}		
};

