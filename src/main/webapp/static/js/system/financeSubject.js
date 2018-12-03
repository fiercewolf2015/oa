var financeSubject = function(){};
financeSubject.financeSubjectTreeId = '#financeSubjectTree';
financeSubject.postInputSel = '#posts';
$(function(){
	var leftHeight = $('#leftPanel').height();
	var rightHeight = $('#rightPanel').height();
	if(leftHeight > rightHeight)
		$('#rightPanel').height(leftHeight);
	else
		$('#leftPanel').height(rightHeight);
	util.initSelectMenu('financeSubjectLi');
	var config = {
	          '.chosen-select'           : {}
	 }
	for (var selector in config) {
	     $(selector).chosen(config[selector]);
	}
	$('.chosen-container-multi').width(200);
	financeSubject.postOptions.postData = {
			departmentId:null
		}
	util.commAjax(financeSubject.postOptions);
	util.commAjax(financeSubject.loadFinanceSubjectTreeNodesOptions);
});

financeSubject.postOptions = 
{
		url		:	'/post/getAllPosts'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){
			 if(postIdArr == null)
				 util.initSelectArray(financeSubject.postInputSel,data,null);
			 else
				 util.initSelectBySelcted(financeSubject.postInputSel,data,postIdArr);
			 var config = {
			          '.chosen-select'           : {}
			   }
		  	 for (var selector in config) {
		  	     $(selector).chosen(config[selector]);
		 	  }
		 	 $('.chosen-container-multi').width(200);
		}
};

financeSubject.financeSubjectTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: financeSubjectNodeBeforeclick,
			onClick: financeSubjectNodeOnclick
		}
		
};
function financeSubjectNodeBeforeclick(treeId, treeNode, clickFlag){
	clearInput();
};
var postIdArr;
function financeSubjectNodeOnclick(event,treeId, treeNode){
	$('#editFinanceSubjectPanel').text(treeNode.name+"属性");
	$('#financeSubjectName').val(treeNode.name);
	$('#financeSubjectLevel').val(treeNode.level);
	$('#remarks').val(treeNode.remark);
	$('#type').val(treeNode.type);
	//编辑时设置岗位
	var postIds = treeNode.postIds;
	postIdArr = null;
	if(isEmpty(postIds))
		$('#posts').val('');
	else{
		postIdArr = postIds.split(',');
		$('#posts').val(postIdArr);
	}
	financeSubject.postOptions.postData = {
			departmentId:null
	}
	util.commAjax(financeSubject.postOptions);
};
financeSubject.loadFinanceSubjectTreeNodesOptions = {
		url		:	'/financeSubject/loadFinanceSubjectTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($(financeSubject.financeSubjectTreeId ), financeSubject.financeSubjectTreeSetting, data);
				$.fn.zTree.getZTreeObj('financeSubjectTree').expandAll(true);
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

financeSubject.addFinanceSubjectOptions = {
		url		:	'/financeSubject/add'
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
						util.commAjax(financeSubject.loadFinanceSubjectTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "会计科目名称已存在",
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

financeSubject.delFinanceSubjectOptions = {
		url		:	'/financeSubject/delete'
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
						util.commAjax(financeSubject.loadFinanceSubjectTreeNodesOptions);
			        });
			}else if(data == -100){
				swal({
	                title: "所选科目已有模板使用不能删除!",
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

financeSubject.editFinanceSubjectOptions = {
		url		:	'/financeSubject/edit'
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
						util.commAjax(financeSubject.loadFinanceSubjectTreeNodesOptions);
			        });
			}else if(data === 2) {
				swal({
	                title: "会计科目名称已存在",
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
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	$('#financeSubjectName').val('').removeAttr('disabled');
	$('#financeSubjectLevel').val('');
	$('#remarks').val('').removeAttr('disabled');
	$('#type').removeAttr('disabled');
	$('#posts').removeAttr('disabled');
	$('#saveAndCancelDiv').show();
	$('#saveAndCancelDivForEdit').hide();
	postIdArr =null;
	financeSubject.postOptions.postData = {
			departmentId:null
	}
	util.commAjax(financeSubject.postOptions);
};

var edit = function(){
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	financeSubjectNodeOnclick(null,'', selectNodes[0]);
	$('#financeSubjectName').removeAttr('disabled');
	$('#remarks').removeAttr('disabled');
	$('#type').removeAttr('disabled');
	$('#posts').removeAttr('disabled');
	$('#saveAndCancelDivForEdit').show();
	$('#saveAndCancelDiv').hide();
};

var saveForEdit = function(){
	var canSave = util.validate("financeSubjectInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	var id = selectNodes[0].id;
	var params = util.findFormData('#financeSubjectInfoDiv :input');
	var selPostIds = $('#posts').val();
	financeSubject.editFinanceSubjectOptions.postData = {
		params : params,
		id	   : id,
		postsIds : selPostIds.join(',')
	};
	util.commAjax(financeSubject.editFinanceSubjectOptions);
	loadingAjax();
};

var save = function(){
	var canSave = util.validate("financeSubjectInfoDiv");
	if(canSave == false)
		return false;
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个会计科目节点!",
            type: "error"
	    });
		return false;
	}
	var pId = selectNodes[0].id;
	var params = util.findFormData('#financeSubjectInfoDiv :input');
	var selPostIds = $('#posts').val();
	financeSubject.addFinanceSubjectOptions.postData = {
		params : params,
		pId	   : pId,
		postsIds : selPostIds.join(',')
	};
	util.commAjax(financeSubject.addFinanceSubjectOptions);
	loadingAjax();
};

var clearInput = function(){
	$('#editFinanceSubjectPanel').text('');
	$('#financeSubjectName').val('').attr('disabled','true');
	$('#financeSubjectLevel').val('');
	$('#remarks').val('').attr('disabled','true');
	$('#type').val(1).attr('disabled',true);
	$('#posts').val('').attr('disabled',true);
	$('#saveAndCancelDiv').hide();
	$('#saveAndCancelDivForEdit').hide();
	postIdArr =null;
	financeSubject.postOptions.postData = {
			departmentId:null
	}
	util.commAjax(financeSubject.postOptions);
};

var del = function(){
	var treeObj = $.fn.zTree.getZTreeObj("financeSubjectTree");
	var selectNodes = treeObj.getSelectedNodes();
	if(selectNodes == null || selectNodes.length == 0){
		swal({
            title: "请选择一个会计科目节点!",
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
	        title: "是否确认删除该会计科目节点?",
	        text: "您将要删除一个父级节点，系统将会自动删除其下全部子会计科目节点",
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
		        	financeSubject.delFinanceSubjectOptions.postData = {id:id};
	        		util.commAjax(financeSubject.delFinanceSubjectOptions);
		        } else
		            swal( {title: "提示", text: "您已取消删除"});
	        });
	}else{
		swal({
	        title: "是否确认删除该会计科目节点?",
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
		        	financeSubject.delFinanceSubjectOptions.postData = {id:id};
	        		util.commAjax(financeSubject.delFinanceSubjectOptions);
		        } else
		        	swal( {title: "提示", text: "您已取消删除"});
	        });
	}
}