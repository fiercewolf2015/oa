var financialBillsForm = function(){};
financialBillsForm.financeSubjectTreeId = '#financeSubjectTree';
financialBillsForm.budgetPersonInputSel = '#budgetPerson';
financialBillsForm.InputSelDefault = '请选择';
$(function(){
	$("#contractPrice").keyup(function(){
		calculate();
	  });
	$("#contractRate").keyup(function(){
		calculate();
	  });
	util.commAjax(financialBillsForm.budgetPersonOptions);
	util.commAjax(companyNameOptions);
//	changeNo();
//	changeFormDiv('有合同报销');
});

financialBillsForm.budgetPersonOptions = 
{
		url		:	'/budgetCommissioner/getAllBudgetCommissioners'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(financialBillsForm.budgetPersonInputSel,data,financialBillsForm.InputSelDefault);
		}
}

function calculate(){
	var contractPrice = $('#contractPrice').val();
	var contractRate = $('#contractRate').val();
	if(contractPrice != '' && contractRate != ''){
		$('#contractMoney').val((parseFloat(contractPrice)*(parseFloat(contractRate)/100)).toFixed(2));
		$('#priceRate').val((parseFloat($('#contractMoney').val())+parseFloat(contractPrice)).toFixed(2));
	}
};

function changeKaijuOrShouqu(flag){
	if(flag == 1){//开据
		$('input[name="kaiju"]').removeAttr('disabled');
		$('input[name="kaiju"]:eq(0)').prop('checked',true);
		$('input[name="receive"]').attr('disabled',true);
		$('input[name="receive"]').removeAttr('checked');
	}else{//收取
		$('input[name="receive"]').removeAttr('disabled');
		$('input[name="receive"]:eq(0)').prop('checked',true);
		$('input[name="kaiju"]').attr('disabled',true);
		$('input[name="kaiju"]').removeAttr('checked');
	}
};

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
				if(data<=0)
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

function getContractForm(){
	var contractNo = $('#contractNo1').val() +"-"+  $('#contractNo2').val() +"-"+  $('#contractNo3').val() +"-"+  $('#contractNo4').val();
	financialBillsForm.getContractFormOptions.postData = {
			contractNo : contractNo
	};
	util.commAjax(financialBillsForm.getContractFormOptions);
}

financialBillsForm.getContractFormOptions = 
{
		url		:	'/workflowInstance/getContractForm'					
			,	mtype	:	'POST'						
			,	postData	:	{}	
			,	success :	function(data,st, xhr){	
					$('#beginDate').val(data.beginDate);
					$('#endDate').val(data.endDate);
					$('#contractContent').val(data.contractContent);
					$('#contractPrice').val(data.contractPrice);
					$('#contractRate').val(data.contractRate);
					$('#priceRate').val(data.priceRate);
					$('#contractMoney').val(data.contractMoney);
				}	
			,	error	:	function(xhr,st,err){		
					swal({
						title: "获取失败，无对应合同审批单!",
			            type: "error"
				    });
					$('#beginDate').val('');
					$('#endDate').val('');
					$('#contractContent').val('');
					$('#contractPrice').val('');
					$('#contractRate').val('');
					$('#priceRate').val('');
					$('#contractMoney').val('');
				}		
};
function changePrice(){
	var zMoney = $('#zMoney').val();
	var nzMoney = $('#nzMoney').val();
	var twMoney = $('#twMoney').val();
    var realityzMoney = $('#realityzMoney').val();
	var realitynzMoney =$('#realitynzMoney').val();
	var realitytwMoney = $('#realitytwMoney').val();
	if(zMoney != null && realityzMoney != null)
		$('#priceDifference').val(parseFloat(zMoney-realityzMoney));
	else if(nzMoney != null && realitynzMoney != null)
		$('#priceDifference').val(parseFloat(nzMoney-realitynzMoney));
	else if(twMoney != null && realitytwMoney != null)
		$('#priceDifference').val(parseFloat(twMoney-realitytwMoney));
	else
		$('#priceDifference').val('');
}
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