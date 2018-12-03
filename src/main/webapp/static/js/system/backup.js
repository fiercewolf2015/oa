var backup = function(){};
$(function(){
	util.initSelectMenu('backUpLi');
});
var uploadFile = function(){
	var allHasSelStaff = $("span[id^='selstaff_']");
	if(allHasSelStaff.length == 0){
		swal({
            title: "请选择通知人员",
            type: "error"
	    });
		return;
	}
	var selStaffIdArr = new Array();
	if(allHasSelStaff.length > 0){
		for(var i=0;i<allHasSelStaff.length;i++){
			selStaffIdArr.push(allHasSelStaff[i].id.split('_')[1]);
		}
	}
	$('#staffIds').val(selStaffIdArr.join(','));
	$('#fileDiv').ajaxSubmit(function(result){
		if(result > 0){
			swal({
				title: "保存成功",
				type: "success"
			},function(){
				util.hideModal('addNoticeModal'); 
				searchPargerList();
			});
		}else if(result == -100){
			swal({
				title: "文件大小不能超过10M",
				type: "error"
			});
		}else{
			swal({
				title: "保存失败！",
				type: "error"
			});
		}
	});
};
