/**
 * 操作日志
 * bizactionlog.js
 */
var bizactionlog = function(){};

bizactionlog.gridId = '#list';
bizactionlog.searchBtnId = '#search_btn';
bizactionlog.fromDataSel = '#searchForm :input';
bizactionlog.paramsKey = 'params';

bizactionlog.gridOption = {
	    url: context+"/bizActionLog/list",
       caption: "操作日志", 
       postData: {'param':{}},
       colNames : ['编号','操作类型', '业务类型','业务信息','操作者','操作时间'],
	    colModel : [
	       {name : 'id', hidden:true },
		    {name : 'actionType',width:40,align:"center"},
		    {name : 'bizType',	width : 40, align:"center"},
		    {name : 'bizContent',	width : 200, align:"left"},
		    {name : 'creatorName',  width : 50 , align:"center"},
		    {name : 'createTime',	width : 40, align:"center"}
		 ],
       
       mtype:"POST",
       datatype: "json",
       jsonReader : {
    	   repeatitems: false,
       	 }, 
       emptyrecords:"无符合条件数据",
       rowNum:10, 
       height:'100%',
       pager: '#pager',  
       autowidth: true,
	    gridview:false,
	    viewrecords: true, 
	    multiselect: false,
	    multiselectWidth: 25
};

$(function(){
	util.initSelectMenu('bizLogLi');
	$(bizactionlog.gridId).jqGrid(bizactionlog.gridOption);
	$(window).resize(function(){
		$(bizactionlog.gridId).setGridWidth(util.getGridWith());
	});
	$(bizactionlog.searchBtnId).click(function(){ 
		util.searchListWithParams(bizactionlog.gridId,bizactionlog.fromDataSel,bizactionlog.paramsKey);
	});
});

