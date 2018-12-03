var contractWill = function(){};
contractWill.gridId = "#table_contractWill";
contractWill.pagerId = "#pager_contractWill";
contractWill.searchDivInput = "#searchContractWillDiv :input";
contractWill.searchBtn = "#searchBtn";
contractWill.params = 'params';
$(function(){
	util.initSelectMenu('contractHistoryLi');
	$(contractWill.gridId).jqGrid(contractWill.gridOption);
	$(window).resize(function(){
		$(contractWill.gridId).setGridWidth(util.getGridWith());
	});
	changeNo();
});

var operationFormatter = function(cellvalue){
	return '<button type="button" class="btn btn-xs btn-danger operationBtn" onclick="deleteContractWill('+cellvalue+')"><i class="fa fa-bitbucket"></i>删除</button>';
}
var contractWillIds = new Array();
contractWill.gridOption = { 
		url: context+"/contractWillHistory/list",
		postData: {'param':'{"flag":"0"}'},
		colNames : ['合同编号','合同份数','合同开始日期','合同结束日期','合同名称','合同单价','合同月金额','合同总价','收/付款时间','收/付款金额','开具发票时间','收/付款时间段','甲方','乙方','丙方','合同类别','备注','项目业态类型','面积统计','项目地址','甲方名称','操作'],
		colModel : [
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
		            {name : 'projectJiaName', width : 50, align : "center"},
		            {name : 'id', width : 30, align : "center",formatter:function(cellvalue, options, rowObject){return operationFormatter(cellvalue);}}
		            
		],
		caption : "历史合同数据",
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

contractWill.addContractWillOptions = 
{
		url		:	'/contractWillHistory/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addContractWillModal'); 
		        });
				searchPargerList();
			}else if(data === -100){
				swal({
					title: "合同编号已存在",
	                type: "error"
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
}
contractWill.delContractWillOptions = 
{
		
		url		:	'/contractWillHistory/delete'					
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data == 1){
				swal({
					title: "删除成功！",
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
}

var searchPargerList = function(){
	util.searchListWithParams(contractWill.gridId,contractWill.searchDivInput,contractWill.params);
};
var showAddModal = function(){
	$('#contractNo4').val('');
	$('#contractCount').val('');
	$('#contractBeginDate').val('');
	$('#contractEndDate').val('');
	$('#contractName').val('');
	$('#contractPrice').val('');
	$('#contractMonthPrice').val('');
	$('#contractPriceTotal').val('');
	$('#paymentDate').val('');
	$('#paymentMoney').val('');
	$('#invoiceDate').val('');
	$('#paymentTimeSlot').val('按月支付');
	$('#jiaName').val('');
	$('#yiName').val('');
	$('#bingName').val('');
	$('#reason').val('');
	$('#projectOperationType').val('住宅类');
	$('#areaStatistics').val('');
	$('#projectAddr').val('');
	$('#projectJIaName').val('');
	$("input[name='contractType']").attr("checked",false);
	util.showModal('addContractWillModal');
};

var saveContractWill = function(){
	var canSave = util.validate("addContractWillDiv");
	if(canSave == false)
		return false;
	var j = 0;
	var inputArr = $("input[name='contractType']");
	for (var i = 0; i < inputArr.length; i++) {
		if(inputArr[i].checked == false)
			j++;
	}
	if(j == inputArr.length){
		swal({
			title: "请选择合同类别"
		});
		return;
	}
	var params = util.findFormData('#addContractWillDiv :input');
	contractWill.addContractWillOptions.postData = {
		params : params
	};
	util.commAjax(contractWill.addContractWillOptions);
};
var deleteContractWill = function(cid){
	var selContractIds =  cid;
	if(cid == undefined){
		var contractIds = $(contractWill.gridId).jqGrid("getGridParam", "selarrrow");
		if(contractIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selContractIds = contractIds.join(',');
	}
	contractWill.delContractWillOptions.postData = {
			contractIds : selContractIds
	};
	swal({
        title: "确定删除选择的历史合同?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(contractWill.delContractWillOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
