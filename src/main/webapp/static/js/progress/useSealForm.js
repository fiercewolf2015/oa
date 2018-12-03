var useSeal = function(){};
useSeal.sealTypesInputSel = '#useType';
useSeal.InputSelDefault = '请选择';
$(function(){
	util.commAjax(useSeal.sealTypesOptions);
});

useSeal.sealTypesOptions = 
{
		url		:	'/sealType/getAllSealTypes'							
	,	mtype	:	'POST'						
	,	postData	:	{}						
	,	success :	function(data,st, xhr){		
			var div = $('#useTypeDiv');
			for (var i = 0; i < data.length; i++){
				if(i == 0)
					div.append('<div id="'+data[i].value+'div"  class="form-group formDiv" style="width:50%;"><input id="'+data[i].value+'" name="useType" type="checkbox" value="'+data[i].text+'" style="float:left;" class ="transform"/><label class="formLabel" style="width:50px;margin-left:3px;">'+data[i].text+'</label><label class="formLabel" style="width:50px;margin-left:3px;">次数:</label><input id="'+data[i].text+'"  name="'+data[i].text+'" type="text" class="transform" style="float:left;" ></div>')
				else
					div.append('<div id="'+data[i].value+'div"  class="form-group formDiv" style="width:50%;margin-left:100px;"><input id="'+data[i].value+'" name="useType" type="checkbox" value="'+data[i].text+'" style="float:left;" class ="transform"/><label class="formLabel" style="width:50px;margin-left:3px;">'+data[i].text+'</label><label class="formLabel" style="width:50px;margin-left:3px;">次数:</label><input id="'+data[i].text+'"  name="'+data[i].text+'" type="text" class="transform" style="float:left;" ></div>')
			}
		}
}
