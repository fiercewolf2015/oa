var occupation = function(){};
occupation.occupationTreeId = '#occupationTree';
$(function(){
	var leftHeight = $('#leftPanel').height();
	var rightHeight = $('#rightPanel').height();
	if(leftHeight > rightHeight)
		$('#rightPanel').height(leftHeight);
	else
		$('#leftPanel').height(rightHeight);
	util.initSelectMenu('occupationLi');
	util.commAjax(occupation.loadOccupationTreeNodesOptions);
});

occupation.occupationTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: occupationNodeBeforeclick,
			onClick: occupationNodeOnclick
		}
		
};
function occupationNodeBeforeclick(treeId, treeNode, clickFlag){
	clearInput();
};
function occupationNodeOnclick(event,treeId, treeNode){
	var occupationId = treeNode.id;
	$('#editOccupationPanel').text(treeNode.name+"属性");
	$('#occupationName').val(treeNode.name);
	$('#occupationNo').val(treeNode.occupation_no);
	$('#occupationLevel').val(treeNode.occupation_level);
	$('#remark').val(treeNode.remark);
};
occupation.loadOccupationTreeNodesOptions = {
		url		:	'/occupation/loadOccupationTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(occupation.occupationTreeId), occupation.occupationTreeSetting, data);
				$.fn.zTree.getZTreeObj('occupationTree').expandAll(true);
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

occupation.addOccupationOptions = {
		url		:	'/occupation/add'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "保存成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(occupation.loadOccupationTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "职务名称已存在",
	                type: "error"
			     });
			}else if(data === 3) {
				swal({
	                title: "职务代码已存在",
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
};

occupation.delOccupationOptions = {
		url		:	'/occupation/delete'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "删除成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(occupation.loadOccupationTreeNodesOptions);
			        });
			}else if(data === 2){
				swal({
	                title: "删除失败!",
	                text:"此职务（或下级职务）包含员工，不能删除",
	                type: "error"
		        });
			}else
				swal({
	                title: "删除失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			swal({
	            title: "删除失败!",
	            type: "error"
		    });
		}		
};

occupation.editOccupationOptions = {
		url		:	'/occupation/edit'
	,	mtype	:	'POST'
	,	postData	:	{}
	,	success :	function(data,st, xhr){
			hideAjax();
			if (data === 1) {
				swal({
		                title: "编辑成功!",
		                type: "success"
			        },
			        function(){
			        	clearInput();
						util.commAjax(occupation.loadOccupationTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "职务名称已存在",
	                type: "error"
			     });
			}else if(data === 3) {
				swal({
	                title: "职务代码已存在",
	                type: "error"
			     });
			}else
				swal({
	                title: "编辑失败!",
	                type: "error"
			    });
		}	
	,	error	:	function(xhr,st,err){
			swal({
	            title: "编辑失败!",
	            type: "error"
		    });
		}		
};


var add = function(){
	var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个职务节点!",
            type: "error"
	    });
		return false;
	}
	$('#occupationName').val('').removeAttr('disabled');
	$('#occupationNo').val('').removeAttr('disabled');
	$('#occupationLevel').val('');
	$('#remark').val('').removeAttr('disabled');
	$('#buttons button').attr('disabled','true');
	$('#saveAndCancelDiv').show();
};

var edit = function(){
	var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个职务节点!",
            type: "error"
	    });
		return false;
	}
	occupationNodeOnclick(null,'', selectNodes[0]);
	$('#occupationName').removeAttr('disabled');
	$('#occupationNo').removeAttr('disabled');
	$('#occupationDescribe').removeAttr('disabled');
	$('#remark').removeAttr('disabled');
	$('#chosePostbtn').removeAttr('disabled');
	$('#buttons button').attr('disabled','true');
	$('#saveAndCancelDivForEdit').show();
};

var saveForEdit = function(){
	var canSave = util.validate("occupationInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个职务节点!",
            type: "error"
	    });
		return false;
	}
	var id = selectNodes[0].id;
	var params = util.findFormData('#occupationInfoDiv :input');
	occupation.editOccupationOptions.postData = {
		params : params,
		id	   : id
	};
	util.commAjax(occupation.editOccupationOptions);
	loadingAjax();
};

var save = function(){
	var canSave = util.validate("occupationInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个职务节点!",
            type: "error"
	    });
		return false;
	}
	var pId = selectNodes[0].id;
	var params = util.findFormData('#occupationInfoDiv :input');
	occupation.addOccupationOptions.postData = {
		params : params,
		pId	   : pId
	};
	util.commAjax(occupation.addOccupationOptions);
	loadingAjax();
};

var clearInput = function(){
	$('#occupationName').val('').attr('disabled','true');
	$('#occupationNo').val('').attr('disabled','true');
	$('#occupationLevel').val('');
	$('#remark').val('').attr('disabled','true');
	$('#buttons button').removeAttr('disabled');
	$('#saveAndCancelDiv').hide();
	$('#saveAndCancelDivForEdit').hide();
};

var del = function(){
	var treeObj = $.fn.zTree.getZTreeObj("occupationTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个职务节点!",
            type: "error"
	    });
		return false;
	}
	if(selectNodes[0].level == 0){
		swal( {title: "错误", text: "根节点不能删除",type:'error'});
		return false;
	}
	var isParent = selectNodes[0].isParent;
	var id = selectNodes[0].id;
	if(isParent){
		swal({
	        title: "是否确认删除该职务节点?",
	        text: "您将要删除一个父级节点，系统将会自动删除其下全部子职务节点",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消",
	        closeOnConfirm: false,
	        closeOnCancel: false 
	        },
	        function (isConfirm) {
		        if (isConfirm) {
		        	loadingAjax();
		        	occupation.delOccupationOptions.postData = {id:id};
	        		util.commAjax(occupation.delOccupationOptions);
		        } else
		            swal( {title: "提示", text: "您已取消删除"});
	        });
	}else{
		swal({
	        title: "是否确认删除该职务节点?",
	        text: "",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确认删除",
	        cancelButtonText: "取消",
	        closeOnConfirm: false,
	        closeOnCancel: false 
	        },
	        function (isConfirm) {
		        if (isConfirm) {
		        	loadingAjax();
		        	occupation.delOccupationOptions.postData = {id:id};
	        		util.commAjax(occupation.delOccupationOptions);
		        } else
		        	swal( {title: "提示", text: "您已取消删除"});
	        });
	}
}