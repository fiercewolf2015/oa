var overtimeForm = function(){};
overtimeForm.overtimeTypesInputSel = '#overtimeType';
overtimeForm.InputSelDefault = '请选择';
$(function(){
	util.commAjax(overtimeForm.overtimetypesOptions);
});

overtimeForm.overtimetypesOptions = 
{
		url		:	'/overtimeType/getAllOvertimeTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(overtimeForm.overtimeTypesInputSel,data,overtimeForm.InputSelDefault);
		}
}

overtimeForm.selOvertime = function(){
	var overtimeStart = $('#overtimeSectionFrom').val();
	var overtimeEnd = $('#overtimeSectionTo').val();
	if(overtimeStart != null && overtimeStart != '' && overtimeEnd != null && overtimeEnd != ''){
		if(overtimeStart >= overtimeEnd){
			swal({
	            title: "开始时间不能大于等于结束时间!",
	            type: "error"
		    });
			$('#overtimeSectionTo').val(overtimeStart);
			$('#totalTime').val('0');
			return;
		}else{
			 overtimeStart = (overtimeStart).replace(/-/g,"/");
			 overtimeEnd = (overtimeEnd).replace(/-/g,"/");
			 var bngDate = new Date(overtimeStart);
	       var endDate = new Date(overtimeEnd);
	       var hours = (endDate.getTime()-bngDate.getTime())/1000/60/60;
	       $('#totalTime').val(parseFloat(hours).toFixed(1));
	       getOvertimeMonth(overtimeStart,overtimeEnd);
		}
	}
};
