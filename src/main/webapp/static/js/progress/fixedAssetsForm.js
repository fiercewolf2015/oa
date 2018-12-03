var fixedAssetsForm = function(){};
fixedAssetsForm.assetTypesInputSel = '#bussinessType';
fixedAssetsForm.InputSelDefault = '请选择';

$(function(){
	util.commAjax(fixedAssetsForm.assetTypesOptions);
	util.commAjax(fixedAssetsForm.loadDepartmentTreeNodesOptions);
	util.commAjax(companyNameOptions);
	$('.ztree').css('z-index','10000');
});

fixedAssetsForm.assetTypesOptions = 
{
		url		:	'/assetType/getAllAssetTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){	
          util.initSelectArray(fixedAssetsForm.assetTypesInputSel,data,fixedAssetsForm.InputSelDefault);
		}
}

function checkSelectModalSaveDiv(){
	var result = true;
	$('#SelectModal input').each(function(){
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
function showSelectModal(){
	if($('.assetsInfo').length==5){
		swal({
            title: "超过5条请添加到附件中!",
            type: "error"
	    });
		return false;
	}
	$('#SelectModal input').val('');
	$('#SelectModal input').css('border','');
	util.showModal('SelectModal');
}
function addInfo(){
	var result = checkSelectModalSaveDiv();
	if(!result){
		return false;
	}
	var valObj = {};
	$('#SelectModal input').each(function(index){
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
	util.hideModal('SelectModal');
	tongji();
};
var infoObj = null;
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
	+'</label><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="addEditInfo(this);">编辑</button><button type="button" class="btn btn-primary btn-sm" style=width:5%;text-align: center;" onclick="cancelInfo(this);tongji()">删除</button></div>';
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
	$('.assetsInfo').each(function(){
		var divCnt = 0;
		var divPrice = 0;
		var divOldPrice = 0;
		var divReduce = 0;
		var divLeft = 0;
		var labelObj = $(this).children();
		for (var i = 0; i < labelObj.length; i++) {
			if(i == 8 || i == 9)
				continue;
			if(i == 3)
				divCnt = parseInt($(labelObj[i]).text());
			else if (i == 4)
				divPrice = parseFloat($(labelObj[i]).text());
			else if(i == 5)
				divOldPrice = parseFloat($(labelObj[i]).text());
			else if( i == 6)
				divReduce = parseFloat($(labelObj[i]).text());
			else if(i == 7)
				divLeft = parseFloat($(labelObj[i]).text());
		}
		totalCnt += divCnt;
		totalPrice +=  divPrice;
		totalOldPrice += divOldPrice;
		totalReduce += divReduce;
		totalLeft += divLeft;
	});
	$('#totalCnt').text(totalCnt);
	$('#totalPrice').text(totalPrice);
	$('#totalOldPrice').text(totalOldPrice);
	$('#totalReduce').text(totalReduce);
	$('#totalLeft').text(totalLeft);
	$('#totalYear').text('');
};
function checkOldPrice(){
	var cnt = parseFloat($('#newCnt').val());
	var price = parseFloat($('#newPrice').val());
	var reduce = parseFloat($('#newReduce').val() == '' ? '0' : $('#newReduce').val());
	$('#newOldPrice').val(cnt * price);
	$('#newLeft').val(Math.round(parseFloat($('#newOldPrice').val() - reduce)*100)/100);
}
function editCheckOldPrice(){
	var cnt = parseFloat($('#oldCnt').val());
	var price = parseFloat($('#oldPrice').val());
	var reduce = parseFloat($('#oldReduce').val() == '' ? '0' : $('#oldReduce').val());
	$('#oldOldPrice').val(cnt * price);
	$('#oldLeft').val(Math.round(parseFloat($('#oldOldPrice').val() - reduce)*100)/100);
}
fixedAssetsForm.departmentTreeSetting = {
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
		hideTreeView('departmentTreeOld')
	}else if(treeId =='departmentTreeNew'){
		$('#newDept').val(treeNode.name);
		hideTreeView('departmentTreeNew')
	}
};

fixedAssetsForm.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTreeOld"), fixedAssetsForm.departmentTreeSetting, data);
				$.fn.zTree.init($("#departmentTreeNew"), fixedAssetsForm.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTreeOld').expandAll(false);
				$.fn.zTree.getZTreeObj('departmentTreeNew').expandAll(false);
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

