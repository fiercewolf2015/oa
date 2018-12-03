var financeLine = function(){};
financeLine.gridId = "#table_financeLine";
financeLine.pagerId = "#pager_financeLine";
financeLine.params = 'params';
$(function(){
	util.initSelectMenu('financeLineLi');
	$(financeLine.gridId).jqGrid(financeLine.gridOption);
	$(window).resize(function(){
		$(financeLine.gridId).setGridWidth(util.getGridWith());
	});
});
var financeLineIds = new Array();
financeLine.gridOption = { 
		url: context+"/financeLine/list",
		postData: {'param':{}},
		colNames : ['第一层级','第二层级','第三层级'],
		colModel : [
		            {name : 'firstLine', width : 50, align : "center"},
		            {name : 'secondLine', width : 50, align : "center"},
		            {name : 'threeLine', width : 50, align : "center"}
		            
		],
		caption : "超预算百分比设置",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: financeLine.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		loadComplete: function(xhr){
			onGridLoadComplete(financeLine.gridId, financeLineIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(financeLineIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(financeLineIds, rowIds, status);
			else
				macIds = new Array();
		}
};

financeLine.addfinanceLineOptions = 
{
		
		url		:	'/financeLine/add'					
	,	mtype	:	'POST'					
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addFinanceLineModal'); 
		        });
				searchPargerList();
			}else if(data === 100){
				swal({
					title: "超预算百分比设置已存在,请先删除在重新设置",
	                type: "error"
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

financeLine.delfinanceLineOptions = 
{
		
		url		:	'/financeLine/delete'					
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
	util.searchListWithParams(financeLine.gridId,null,financeLine.params);
};
var showAddModal = function(){
	$('#firstLine').val('');
	$('#secondLine').val('');
	$('#threeLine').val('');
	util.showModal('addFinanceLineModal');
};

var saveFinanceLine = function(){
	var canSave = util.validate("addFinanceLineDiv");
	if(canSave == false)
		return false;
	var params = util.findFormData('#addFinanceLineDiv :input');
	financeLine.addfinanceLineOptions.postData = {
		params : params
	};
	util.commAjax(financeLine.addfinanceLineOptions);
};

var deletefinanceLine = function(){
	var financeLineIds = $(financeLine.gridId).jqGrid("getGridParam", "selarrrow");
	if(financeLineIds.length < 1){
		swal({
			title: "请选择要删除的记录"
		});
		return;
	}
	financeLine.delfinanceLineOptions.postData = {
			financeLineId : financeLineIds[0]
	};
	swal({
        title: "确定删除?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	util.commAjax(financeLine.delfinanceLineOptions);
        } else {
            swal("已取消删除", "", "error");
        }
     });
}
