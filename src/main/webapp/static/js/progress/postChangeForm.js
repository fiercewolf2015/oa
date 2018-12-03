var postChangeForm = function(){};
postChangeForm.InputSelDefault = '请选择';
postChangeForm.postInputSel = '#posts';

$(function(){
	$(postChangeForm.staffgridId).jqGrid(postChangeForm.staffgridOption);
	util.commAjax(postChangeForm.postOptions);
});

var allPostData;
postChangeForm.postOptions = 
{
		url		:	'/post/getAllPosts'							
	,	mtype	:	'POST'						
	,	postData	:	{departmentId:null}						
	,	success :	function(data,st, xhr){	
			allPostData = data;
		}
}

postChangeForm.departmentTreeSetting = {
		check: {
			enable: true,
			chkStyle: "radio",
			chkboxType: { "Y": "s", "N": "s" },
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};
postChangeForm.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTree"), postChangeForm.departmentTreeSetting, data);
				$.fn.zTree.getZTreeObj('departmentTree').expandAll(true);
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

postChangeForm.staffgridId = "#table_staff";
postChangeForm.staffpagerId = "#pager_staff";
postChangeForm.staffsearchDivInput = "#searchStaffDiv :input";
postChangeForm.staffparams = 'params';
postChangeForm.staffgridOption = { 
		url: context+"/staff/list",
		postData: {'param':{}},
		colNames : ['姓名','员工编号','部门','职务','职称职级','岗位'],
		colModel : [
		            {name : 'staffName', width : 100, align : "center"},
		            {name : 'staffNo', width : 100, align : "center"},
		            {name : 'department.departmentName', width : 100, align : "center"},
		            {name : 'occupation.occupationName', width : 100, align : "center"},
		            {name : 'title.titleName', width : 100, align : "center"},
		            {name : 'postNames', width : 100, align : "center",sortable:false}
		],
		caption : "员工",
		mtype:"POST",
	    datatype: "json",
	    emptyrecords:"无符合条件数据",
	    rowNum:50000, 
	    height:280,
	    pager: postChangeForm.staffpagerId,  
	    autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		shrinkToFit:false,
		autoScroll: false  
};
var staffsearchPargerList = function(){
	util.searchListWithParams(postChangeForm.staffgridId,postChangeForm.staffsearchDivInput,postChangeForm.staffparams);
};
function showSelectModal(){
	$('#searchStaffNo').val('');
	$('#searchStaffName').val('');
	util.commAjax(postChangeForm.loadDepartmentTreeNodesOptions);
	staffsearchPargerList();
	util.initSelectArray(postChangeForm.postInputSel,allPostData,null);
	util.initSelectArray(postChangeForm.postInputSel,allPostData,null);
	 var config = {
	          '.chosen-select' : {}
	   }
 	 for (var selector in config) {
 	     $(selector).chosen(config[selector]);
	  }
	 $('.chosen-container-multi').width(200);
	util.showModal('SelectModal');
}

function addPostChangeInfo(){
	var staffIdArr = $(postChangeForm.staffgridId).jqGrid("getGridParam", "selarrrow");
	if(staffIdArr.length < 1){
		swal({
			title: "至少选择一个员工"
		});
		return false;
	}
	var departmentTreeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var department_nodes = departmentTreeObj.getCheckedNodes(true);
	var departmentNewName = "";
	if(department_nodes == '' || department_nodes == null || department_nodes.length == 0) {
	}else
		departmentNewName = department_nodes[0].name;
	var postNames = "";
	$("#posts option:selected").each(function(){
		if(postNames == '')
			postNames = this.text;
		else
			postNames+=","+this.text;
	});
	
	for (var i = 0; i < staffIdArr.length; i++) {
		var rowData = $(postChangeForm.staffgridId).jqGrid('getRowData', staffIdArr[i]);
		var str = '<div class="postChangeInfo" style="margin-top:5px;">'
		+'<label name="changeStaffName" style="width:10%;text-align: center;">'+rowData['staffName']
		+'</label><label name="changeStaffNo" style="width:10%;text-align: center;">'+rowData['staffNo']
		+'</label><label name="oldDepartmentName" style="width:15%;text-align: center;">'+rowData['department.departmentName']
		+'</label><label name="oldPostNames" style="width:20%;text-align: center;">'+rowData['postNames']
		+'</label><label name="newDepartmentName" style="width:16%;text-align: center;">'+(departmentNewName==""?rowData['department.departmentName']:departmentNewName)
		+'</label><label name="newPostNames" style="width:19%;text-align: center;">'+(postNames==""?rowData['postNames']:postNames)
		+'</label><button type="button" class="btn btn-primary btn-sm" style=width:10%;text-align: center;" onclick="cancelInfo(this);">删除</button></div>';
		$('#postChangeInfo').append(str);
	}
	util.hideModal('SelectModal');
};

function cancelInfo(obj){
	$(obj).parent().remove();
};
