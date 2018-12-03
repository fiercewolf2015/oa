var leaveForm = function(){};
leaveForm.leaveTypesInputSel = '#leaveType';
leaveForm.InputSelDefault = '请选择';
$(function(){
	util.commAjax(leaveForm.leaveTypesOptions);
});

leaveForm.leaveTypesOptions = 
{
		url		:	'/leaveType/getAllLeaveTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(leaveForm.leaveTypesInputSel,data,leaveForm.InputSelDefault);
		}
}

leaveForm.selOvertime = function(){
	var leaveSectionFrom = $('#leaveSectionFrom').val();
	var leaveSectionTo = $('#leaveSectionTo').val();
	if(leaveSectionFrom != null && leaveSectionFrom != '' && leaveSectionTo != null && leaveSectionTo != ''){
		if(leaveSectionFrom > leaveSectionTo){
			swal({
	            title: "开始时间不能大于结束时间!",
	            type: "error"
		    });
			$('#leaveSectionTo').val(leaveSectionFrom);
			$('#totalTime').val('0');
			return;
		}else{
			leaveSectionFrom = (leaveSectionFrom).replace(/-/g,"/");
			leaveSectionTo = (leaveSectionTo).replace(/-/g,"/");
			var bngDate = new Date(leaveSectionFrom);
			var endDate = new Date(leaveSectionTo);
			var days = (endDate.getTime()-bngDate.getTime())/1000/60/60/24;
			$('#totalTime').val(Math.ceil(days*10)/10);
		}
	}
};

function addLeaveInfo(){
	var leaveSectionFrom = $('#leaveSectionFrom').val();
	var leaveSectionTo = $('#leaveSectionTo').val();
	var totalTime = $('#totalTime').val();
	var leaveType = $('#leaveType').val();
	if(leaveSectionFrom == ''){
		swal({
            title: "开始时间必填!",
            type: "error"
	    });
		return;
	}
	if(leaveSectionTo == ''){
		swal({
            title: "结束时间必填!",
            type: "error"
	    });
		return;
	}
	if(leaveSectionFrom > leaveSectionTo){
		swal({
            title: "开始时间不能大于结束时间!",
            type: "error"
	    });
		return;
	}
	if(totalTime == ''){
		swal({
            title: "请填写总时长",
            type: "error"
	    });
		return;
	}
	if(leaveType == -1 || leaveType == ''){
		swal({
            title: "请选择请假类型",
            type: "error"
	    });
		return;
	}
	var leaveTypeName = $("#leaveType option:selected").text();
	var str = '<div class="leaveInfo"><label name="leaveSectionFrom" style="float:left;width:30%;">'+leaveSectionFrom
				+'</label><label name="leaveSectionTo" style="float:left;width:30%;">'+leaveSectionTo
				+'</label><label name="leaveTypeName" style="float:left;width:25%;">'+leaveTypeName
				+'</label><button type="button" class="btn btn-primary btn-sm" style=width:9%;text-align: center;" onclick="cancelInfo(this);">删除</button></div>';
	$('#leaveInfo').append(str);
};

function cancelInfo(obj){
	$(obj).parent().remove();
};
