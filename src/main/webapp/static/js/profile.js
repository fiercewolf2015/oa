var profile = function(){};
profile.nationInputSel = '#nation';
profile.politicalInputSel = '#political';
profile.qualificationSelectorOr = '#education';
profile.qualificationSelectorHi = '#highestDegree';
profile.inputSelDefault = '请选择';
$(function(){
	util.initSelect(profile.nationInputSel,nationArrayForUtil,profile.inputSelDefault);
	util.initSelect(profile.politicalInputSel,politicalArrayForUtil,profile.inputSelDefault);
	util.initSelect(profile.qualificationSelectorOr,qualificationArrayForUtil,profile.inputSelDefault);
	util.initSelect(profile.qualificationSelectorHi,qualificationArrayForUtil,profile.inputSelDefault);
	util.commAjax(profile.staffInfoOptions);
})

profile.staffInfoOptions = 
{
		url		:	'/profile/staffInfo'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if(data != null)
				profile.afterGetStaffInfo(data);
		}
}

profile.editStaffOptions = 
{
		
		url		:	'/profile/editStaff'						
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
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

profile.afterGetStaffInfo = function(data){
	$('.formSpan').each(function(){
		if(this.id != 'name'){
			if($(this).is('span'))
				$(this).text(data[this.id]);
			else
				$(this).val(data[this.id]);
		}
	});
	$('#occupation').text(data.occupation.occupationName);
	$('#department').text(data.department.departmentName);
	$('#title').text(data.title.titleName);
	$('#staffId').val(data.id);
	var allPost = data.posts;
	var postNames = '';
	if(allPost != null && allPost.length > 0){
		for(var i=0;i<allPost.length;i++){
			if(i != allPost.length - 1)
				postNames = postNames+allPost[i].postName+",";
			else
				postNames = postNames+allPost[i].postName;
		}
	}
	$('#posts').text(postNames);
};

var save = function(){
	var canSave = util.validate("profileDiv");
	if(canSave == false)
		return false;
	var password = $('#password').val();
	var confirmPassword = $('#confirmPassword').val();
	var file = $('#file').val();
	if(file != null && file != ""){
		if(!/.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(file)){
			swal({title: "请上传图片格式的头像",type: "error"});
			return false;
		}
	}
	if(password != ''){
		if(password != confirmPassword){
			swal({title: "两次输入的密码不一致，请检查",type: "error"});
			return false;
		}
	}
	loadingAjax();
	$('#profileDiv').ajaxSubmit(function(result){
		hideAjax();
		if(result == 1){
			swal({
					title: "保存成功"
				},function(){
					$('#file').val('');
					window.location.reload();
				});
		}else{
			swal({title: "保存失败",type: "error"});
			return false;
		}
	});
};

var saveStaffInfo = function(){
	var params = util.findFormData('#person-info :input');
	profile.editStaffOptions.postData = {
		params : params
	};
	util.commAjax(profile.editStaffOptions);
};