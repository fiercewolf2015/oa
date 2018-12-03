var contractWill = function(){};
contractWill.gridId = "#table_contractWill";
contractWill.pagerId = "#pager_contractWill";
contractWill.searchDivInput = "#searchContractWillDiv :input";
contractWill.searchBtn = "#searchBtn";
contractWill.params = 'params';
$(function(){
	util.initSelectMenu('contractWillFormLi');
	$(contractWill.gridId).jqGrid(contractWill.gridOption);
	$(window).resize(function(){
		$(contractWill.gridId).setGridWidth(util.getGridWith());
	});
});
var contractWillIds = new Array();
contractWill.gridOption = { 
		url: context+"/contractWillStatistic/list",
		postData: {'param':{}},
		colNames : ['申请日期','分公司','项目名称','流程编号','合同编号','合同份数','合同开始日期','合同结束日期','合同名称','合同单价','合同月金额','合同总价','收/付款时间','收/付款金额','开具发票时间','收/付款时间段','甲方名称','乙方名称','丙方名称','合同类别','备注','项目业态类型','面积统计','项目地址','甲方名称'],
		colModel : [
					{name : 'applyDate', width : 50, align : "center"},
					{name : 'companyName', width : 50, align : "center"},
					{name : 'projectName', width : 50, align : "center"},
					{name : 'instanceNum', width : 50, align : "center"},
		            {name : 'contractNo', width : 50, align : "center"},
		            {name : 'contractCount', width : 50, align : "center"},
		            {name : 'contractBeginDate', width : 50, align : "center"},
		            {name : 'contractEndDate', width : 50, align : "center"},
		            {name : 'contractName', width : 50, align : "center"},
		            {name : 'contractPrice', width : 50, align : "center"},
		            {name : 'contractMonthPrice', width : 50, align : "center"},
		            {name : 'contractPriceTotal', width : 50, align : "center"},
		            {name : 'paymentDate', width : 50, align : "center"},
		            {name : 'paymentMoney', width : 50, align : "center"},
		            {name : 'invoiceDate', width : 50, align : "center"},
		            {name : 'paymentTimeSlot', width : 50, align : "center"},
		            {name : 'jiaName', width : 50, align : "center"},
		            {name : 'yiName', width : 50, align : "center"},
		            {name : 'bingName', width : 50, align : "center"},
		            {name : 'contractType', width : 50, align : "center"},
		            {name : 'reason', width : 50, align : "center"},
		            {name : 'projectOperationType', width : 50, align : "center"},
		            {name : 'areaStatistics', width : 50, align : "center"},
		            {name : 'projectAddr', width : 50, align : "center"},
		            {name : 'projectJiaName', width : 50, align : "center"}
		            
		],
		caption : "合同经营数据",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: contractWill.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(contractWill.gridId, contractWillIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(contractWillIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(contractWillIds, rowIds, status);
			else
				macIds = new Array();
		}
};
var searchPargerList = function(){
	util.searchListWithParams(contractWill.gridId,contractWill.searchDivInput,contractWill.params);
};
contractWill.exportExcelOptions = 
{
		url		:	'/contractWillStatistic/exportExcel'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){}	
}
var exportExcel = function(){
	location.href = "contractWillStatistic/exportExcel";
}