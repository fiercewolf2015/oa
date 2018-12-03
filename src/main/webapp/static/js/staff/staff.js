var staff = function(){};
staff.gridId = "#table_staff";
staff.pagerId = "#pager_staff";
staff.searchDivInput = "#searchStaffDiv :input";
staff.searchBtn = "#searchBtn";
staff.params = 'params';
staff.titleInputSel = '#title';
staff.titleInputSelDefault = '请选择';
staff.postInputSel = '#posts';
staff.nationInputSel = '#nation';
staff.politicalInputSel = '#political';
staff.qualificationSelectorOr = '#education';
staff.qualificationSelectorHi = '#highestDegree';

$(function(){
	util.initSelectMenu('staffLi');
	$(staff.gridId).jqGrid(staff.gridOption);
	$(window).resize(function(){
		$(staff.gridId).setGridWidth(util.getGridWith());
	});
	util.commAjax(staff.loadOccupationTreeNodesOptions);
	util.commAjax(staff.loadDepartmentTreeNodesOptions);
	$('.ztree').css('z-index','10000');
	util.commAjax(staff.titleOptions);
	var config = {
	          '.chosen-select'           : {}
	 }
	for (var selector in config) {
	     $(selector).chosen(config[selector]);
	}
	$('.chosen-container-multi').width(200);
	util.initSelect(staff.nationInputSel,nationArrayForUtil,"请选择");
	util.initSelect(staff.politicalInputSel,politicalArrayForUtil,"请选择");
	util.initSelect(staff.qualificationSelectorOr,qualificationArrayForUtil,"请选择");
	util.initSelect(staff.qualificationSelectorHi,qualificationArrayForUtil,"请选择");
});

staff.occupationTreeSetting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: occupationNodeOnclick
		}
		
};

function occupationNodeOnclick(event,treeId, treeNode){
	$('#occupation').val(treeNode.name);
	$('#occupationIdHidden').val(treeNode.id);
};

staff.loadOccupationTreeNodesOptions = {
		url		:	'/occupation/loadOccupationTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($('#occupationTree'), staff.occupationTreeSetting, data);
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

staff.departmentTreeSetting = {
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
	$('#department').val(treeNode.name);
	$('#departmentIdHidden').val(treeNode.id);
	staff.postOptions.postData = {
		departmentId:treeNode.id
	}
	util.commAjax(staff.postOptions);
};

staff.loadDepartmentTreeNodesOptions = {
		url		:	'/department/loadDepartmentTree'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			if (data) { 
				$.fn.zTree.init($("#departmentTree"), staff.departmentTreeSetting, data);
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

staff.titleOptions = 
{
		url		:	'/title/getAllTitles'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
          util.initSelectArray(staff.titleInputSel,data,staff.titleInputSelDefault);
		}
}

staff.postOptions = 
{
		url		:	'/post/getAllPosts'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){	
			 var addOrUpdate = $('#selStaffId').val();
			 if(addOrUpdate == '')
				 util.initSelectArray(staff.postInputSel,data,null);
			 else
				 util.initSelectBySelcted(staff.postInputSel,data,postIdArr);
			 var config = {
			          '.chosen-select'           : {}
			   }
		  	 for (var selector in config) {
		  	     $(selector).chosen(config[selector]);
		 	  }
		 	 $('.chosen-container-multi').width(200);
		}
}
var staffIds = new Array();
staff.gridOption = { 
		url: context+"/staff/list",
		postData: {'param':{}},
		colNames : ['姓名','员工编号','部门','部门id','职务','职务id','职称职级','职称职级id','岗位','手机号','固定电话','邮箱','岗位id','身份证号','性別','民族','生日','籍贯','婚姻状况','政治面貌','血型','健康状况','入党时间','原学历','原所学专业',
		                '最高学历','最高学历专业','学位','取证时间','证件编号','其他证书名称','其他取证时间','其他证件编号','參加工作时间','进入本企业时间','家庭住址','家庭电话',
		               '户口所在地','档案所在地','工作所属项目'],
		colModel : [
		            {name : 'staffName', width : 100, align : "center",
		            		formatter:function(cellvalue, options, rowObject){
		            			return '<a href="#" style="color:rgb(51, 102, 153);text-decoration: underline;" onclick="showEditModal('+rowObject.id+',\''+cellvalue+'\')">'+cellvalue+'</a>';
		            		}
		            	},
		            {name : 'staffNo', width : 100, align : "center"},
		            {name : 'department.departmentName', width : 100, align : "center"},
		            {name : 'department.id', width : 100, align : "center",hidden:true},
		            {name : 'occupation.occupationName', width : 100, align : "center"},
		            {name : 'occupation.id', width : 100, align : "center",hidden:true},
		            {name : 'title.titleName', width : 100, align : "center"},
		            {name : 'title.id', width : 100, align : "center",hidden:true},
		            {name : 'postNames', width : 100, align : "center",sortable:false},
		            {name : 'postIds', width : 100, align : "center",hidden:true},
		            {name : 'mobilePhone', width : 100, align : "center"},
		            {name : 'fixedPhone', width : 100, align : "center"},
		            {name : 'email', width : 100, align : "center"},
		            {name : 'personalId', width : 100, align : "center"},
		            {name : 'gender', width : 100, align : "center",
			            	formatter:function(cellvalue, options, rowObject){
			            		if(cellvalue == 1)
			            			return "男";
			            		else if(cellvalue == 2)
			            			return "女";
			            		return "";
			            	}
		            	},
		            {name : 'nation', width : 100, align : "center",
		            		formatter:function(cellvalue, options, rowObject){
		            			if(isEmpty(cellvalue))
		            				return "";
		            			return nationArrayForUtil[cellvalue];
			            	}	
		            	},
		            {name : 'birthday', width : 100, align : "center"},
		            {name : 'nativePlace', width : 100, align : "center"},
		            {name : 'marriage', width : 100, align : "center",
		            		formatter:function(cellvalue, options, rowObject){
		            			if(isEmpty(cellvalue))
		            				return "";
		            			else if(cellvalue == 1)
		            				return "已婚";
		            			else 
		            				return "未婚";
		            		}	
		            	},
		            {name : 'political', width : 100, align : "center",
		            		formatter:function(cellvalue, options, rowObject){
		            			if(isEmpty(cellvalue))
		            				return "";
		            			return politicalArrayForUtil[cellvalue];
			            	}	
		            	},
		            {name : 'blood', width : 100, align : "center"},
		            {name : 'health', width : 100, align : "center"},
		            {name : 'partyDate', width : 100, align : "center"},
		            {name : 'education', width : 100, align : "center",
			            	formatter:function(cellvalue, options, rowObject){
		            			if(isEmpty(cellvalue))
		            				return "";
		            			return qualificationArrayForUtil[cellvalue];
			            	}	
		            	},
		            {name : 'major', width : 100, align : "center"},
		            {name : 'highestDegree', width : 100, align : "center",
			            	formatter:function(cellvalue, options, rowObject){
		            			if(isEmpty(cellvalue))
		            				return "";
		            			return qualificationArrayForUtil[cellvalue];
			            	}	
		            	},
		            {name : 'highestDegreeMajor', width : 100, align : "center"},
		            {name : 'degree', width : 100, align : "center"},
		            {name : 'forensicTime', width : 100, align : "center"},
		            {name : 'certificateNumber', width : 100, align : "center"},
		            {name : 'otherCertificate', width : 100, align : "center"},
		            {name : 'otherForensicTime', width : 100, align : "center"},
		            {name : 'otherCertificateNumber', width : 100, align : "center"},
		            {name : 'jobTime', width : 100, align : "center"},
		            {name : 'enterpriseTime', width : 100, align : "center"},
		            {name : 'homeAddress', width : 100, align : "center"},
		            {name : 'homePhoneNum', width : 100, align : "center"},
		            {name : 'accountAddress', width : 100, align : "center"},
		            {name : 'archivesAddress', width : 100, align : "center"},
		            {name : 'jobProject', width : 100, align : "center"}
		],
		caption : "员工",
		mtype:"POST",
	   datatype: "json",
	   emptyrecords:"无符合条件数据",
	   rowNum:20, 
	   height:'100%',
	   pager: staff.pagerId,  
	   autowidth: true,
		gridview:false,
		viewrecords: true,  
		multiselect: true,
		multiselectWidth: 25,
		shrinkToFit:false,
		autoScroll: false,
		loadComplete: function(xhr){
			onGridLoadComplete(staff.gridId, staffIds);
		},
		onSelectRow: function(rowId, status){
			onGridSelectRow(staffIds, rowId, status);
		},
		onSelectAll: function(rowIds, status){
			if(status)
				onGridSelectAll(staffIds, rowIds, status);
			else
				macIds = new Array();
		}
//		beforeSelectRow: function (rowid, e) {  
//		    var $myGrid = $(this),  
//		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),  
//		        cm = $myGrid.jqGrid('getGridParam', 'colModel');  
//		    return (cm[i].name === 'cb');  
//		}  
};

staff.addStaffOptions = 
{
		
		url		:	'/staff/save'						
	,	mtype	:	'POST'						
	,	postData	:	{}							
	,	success :	function(data,st, xhr){		
			if (data === 1) {
				swal({
                title: "保存成功!",
                type: "success"
		        },function(){
		        	util.hideModal('addStaffModal'); 
		        });
				searchPargerList();
			}else if(data == 2){
				swal({
	                title: "员工编号已存在!",
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
}

staff.delStaffOptions = 
{
		
		url		:	'/staff/delete'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data > 0){
				swal({
					title: "删除成功",
					type: "success"
				});
			}else{
				swal({
					title: "删除失败",
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
	util.searchListWithParams(staff.gridId,staff.searchDivInput,staff.params);
};
var showAddModal = function(){
	clearInput();
	$('#spanText').html('新增员工');
	$('#addStaffDiv :input').val('');
	util.showModal('addStaffModal');
};

var saveStaff = function(){
	var canSave = util.validate("addStaffDiv");
	if(canSave == false){
		return false;
	}
	var params = util.findFormData('#addStaffDiv :input');
	var selPostIds = $('#posts').val();
	staff.addStaffOptions.postData = {
		params : params,
		selPostIds : selPostIds.join(',')
	};
	util.commAjax(staff.addStaffOptions);
};

var postIdArr;
var showEditModal = function(staffId,staffName){
	clearInput();
	$('#spanText').html('编辑员工');
	var rowData = $(staff.gridId).jqGrid('getRowData', staffId);
	$('#selStaffId').val(staffId);
	$('#staffName').val(staffName);
	$('#staffNo').val(rowData['staffNo']);
	$('#occupation').val(rowData['occupation.occupationName']);
	$('#occupationIdHidden').val(rowData['occupation.id']);
	
	$('#department').val(rowData['department.departmentName']);
	$('#departmentIdHidden').val(rowData['department.id']);
	var departmentTree = $.fn.zTree.getZTreeObj("departmentTree");
	var departmentNode = departmentTree.getNodeByParam("id", rowData['department.id'], null);
	if (departmentNode)
		departmentTree.checkNode(departmentNode,true,false);
	$('#title').val(rowData['title.id']);
	$('#jobProject').val(rowData['jobProject']);
	$('#jobTime').val(rowData['jobTime']);
	$('#enterpriseTime').val(rowData['enterpriseTime']);
	var gender = rowData['gender'];
	if(gender == '男')
		$('#gender').val("1");
	else if(gender == '女')
		$('#gender').val("2");
	else
		$('#gender').val("");
	var nation = rowData['nation'];
	if(isEmpty(nation))
		$('#nation').val("");
	else{
		for(var i=0;i<nationArrayForUtil.length;i++){
			if(nation == nationArrayForUtil[i]){
				$('#nation').val(i);
				break;
			}
		}
	}
	$('#birthday').val(rowData['birthday']);
	$('#nativePlace').val(rowData['nativePlace']);
	$('#blood').val(rowData['blood']);
	$('#personalId').val(rowData['personalId']);
	$('#mobilePhone').val(rowData['mobilePhone']);
	$('#fixedPhone').val(rowData['fixedPhone']);
	$('#email').val(rowData['email']);
	var marriage = rowData['marriage'];
	if(marriage == '已婚')
		$('#marriage').val("1");
	else if(marriage == '未婚')
		$('#marriage').val("2");
	else
		$('#marriage').val("");
	var political = rowData['political'];
	if(isEmpty(political))
		$('#political').val("");
	else{
		for(var i=0;i<politicalArrayForUtil.length;i++){
			if(political == politicalArrayForUtil[i]){
				$('#political').val(i);
				break;
			}
		}
	}
	$('#health').val(rowData['health']);
	$('#partyDate').val(rowData['partyDate']);
	$('#homeAddress').val(rowData['homeAddress']);
	$('#homePhoneNum').val(rowData['homePhoneNum']);
	$('#accountAddress').val(rowData['accountAddress']);
	$('#archivesAddress').val(rowData['archivesAddress']);
	var education = rowData['education'];
	if(isEmpty(education))
		$('#education').val("");
	else{
		for(var i=0;i<qualificationArrayForUtil.length;i++){
			if(education == qualificationArrayForUtil[i]){
				$('#education').val(i);
				break;
			}
		}
	}
	$('#major').val(rowData['major']);
	var highestDegree = rowData['highestDegree'];
	if(isEmpty(highestDegree))
		$('#highestDegree').val("");
	else{
		for(var i=0;i<qualificationArrayForUtil.length;i++){
			if(highestDegree == qualificationArrayForUtil[i]){
				$('#highestDegree').val(i);
				break;
			}
		}
	}
	$('#highestDegreeMajor').val(rowData['highestDegreeMajor']);
	$('#degree').val(rowData['degree']);
	$('#forensicTime').val(rowData['forensicTime']);
	$('#certificateNumber').val(rowData['certificateNumber']);
	$('#otherCertificate').val(rowData['otherCertificate']);
	$('#otherForensicTime').val(rowData['otherForensicTime']);
	$('#otherCertificateNumber').val(rowData['otherCertificateNumber']);
	//编辑时设置岗位
	var postIds = rowData['postIds'];
	postIdArr = null;
	if(isEmpty(postIds))
		$('#posts').val('');
	else{
		postIdArr = postIds.split(',');
		$('#posts').val(postIdArr);
		staff.postOptions.postData = {
			departmentId:rowData['department.id']
		}
		util.commAjax(staff.postOptions);
	}
	util.showModal('addStaffModal');
};

var deleteStaff = function(staffId){
	var selStaffIds =  staffId;
	if(staffId == undefined){
		var staffIds = $(staff.gridId).jqGrid("getGridParam", "selarrrow");
		if(staffIds.length < 1){
			swal({
				title: "至少选择一条记录删除"
			});
			return;
		}
		selStaffIds = staffIds.join(',');
	}
	staff.delStaffOptions.postData = {
			staffIds : selStaffIds
	};
	swal({
         title: "确定删除选择的员工?",
         type: "warning",
         showCancelButton: true,
         confirmButtonColor: "#DD6B55",
         confirmButtonText: "确定",
         cancelButtonText: "取消",
         closeOnConfirm: false,
         closeOnCancel: false },
     function (isConfirm) {
         if (isConfirm) {
        	 util.commAjax(staff.delStaffOptions);
         } else {
             swal("已取消删除", "", "error");
         }
     });
};

var clearInput = function(){
	$('#addStaffDiv :input').val('');
	var occupationTree = $.fn.zTree.getZTreeObj("occupationTree");
	var occupationSelNode = occupationTree.getSelectedNodes();
	if (occupationSelNode.length>0) { 
		occupationTree.cancelSelectedNode(occupationSelNode[0]);
	}
	var departmentTree = $.fn.zTree.getZTreeObj("departmentTree");
	var departmentSelNode = departmentTree.getSelectedNodes();
	if (departmentSelNode.length>0) { 
		departmentTree.cancelSelectedNode(departmentSelNode[0]);
	} 
	util.initSelectArray(staff.postInputSel,[],null);
	var config = {
	          '.chosen-select'           : {}
	}
 	for (var selector in config) {
 	     $(selector).chosen(config[selector]);
	 }
	$('.chosen-container-multi').width(200);
};

var showImportModal = function(){
	$('#file').val('');
	$('#errorMse').text('');
	util.showModal('importStaffModal');
};

staff.syncDataOptions = 
{
		
		url		:	'/staff/syncData'							
	,	mtype	:	'POST'						
	,	postData	:	{}	
	,  dataType : 'text' 
	,	success :	function(data,st, xhr){	
			if(data > 0){
				swal({
					title: "同步成功",
					type: "success"
				});
			}else{
				swal({
					title: "同步失败",
					type: "error"
				});
			}
			hideAjax();
		}	
	,	error	:	function(xhr,st,err){		
			swal({
	            title: "同步失败!",
	            type: "error"
		    });
			hideAjax();
		}		
}
var syncData = function(){
	swal({
        title: "确定同步数据?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false },
    function (isConfirm) {
        if (isConfirm) {
        	loadingAjax();
       	 	util.commAjax(staff.syncDataOptions);
        } else {
            swal("已取消同步", "", "error");
        }
    });
}

var uploadFile = function(){
	var file = $('#file').val();
	if(file != null && file != ""){
		if(!/.(xls|xlsx|csv)$/.test(file)){
			swal({title: "请上传正确格式的文件",type: "error"});
			return false;
		}
	}
	loadingAjax();
	$('#fileDiv').ajaxSubmit(function(result){
		hideAjax();
		if(result == ""){
			swal({
				title: "导入成功"
			},function(){
				$('#file').val('');
				$('#errorMse').text('');
				util.hideModal('importStaffModal'); 
				searchPargerList();
			});
		}else if(result == "error"){
			swal({title: "导入失败",type: "error"});
			return false;
		}else{
			swal({title: "导入失败",type: "error"},function(){
				$('#errorMse').text(result);
			});
			return false;
		}
	});
};


