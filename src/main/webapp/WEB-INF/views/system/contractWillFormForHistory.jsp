<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>历史合同</title>
</head>
<body>
	 <div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchContractWillDiv">
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractWillName">合同名称</label>
                    <input type="text" id="contractWillName" name="contractWillName" placeholder="合同名称" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractWillNo">合同编号</label>
                    <input type="text" id="contractWillNo" name="contractWillNo" placeholder="合同编号" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractWillBeginDate">合同开始日期</label>
                    <input type="text"  id="contractWillBeginDate" name="contractWillBeginDate" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractWillEndDate">合同结束日期</label>
                    <input type="text"  id="contractWillEndDate" name="contractWillEndDate" class="form-control tableInput" onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true})">
                </div>
            </div>
            <div class="col-sm-4">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
                <button type="button" class="btn btn-sm btn-danger btnWithoutLabel" onclick="deleteContractWill()"><i class="fa fa-bitbucket"></i>批量删除</button>
                <button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="showAddModal();"><i class="fa fa-plus"></i>新增</button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox">
                <div class="ibox-content">
							<div class="jqGrid_wrapper">
                         <table id="table_contractWill"></table>
                         <div id="pager_contractWill"></div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="modal inmodal" id="addContractWillModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">新增历史合同</h4>
				</div>
				<div class="modal-body" id="addContractWillDiv" style="height: 670px;">
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">合同编号:</label>
							<select id="contractNo1" name="contractNo1"class="formSpan transform need" style="float:left;width:15%" onchange="changeNo();">
								<option value="TFBB">TFBB</option>
								<option value="TFON">TFON</option>
								<option value="TFTW">TFTW</option>
								<option value="TFTH">TFTH</option>
								<option value="TFFO">TFFO</option>
								<option value="TFFI">TFFI</option>
							</select>
							<label style="float:left;">—</label>
							<select id="contractNo2" name="contractNo2"class="formSpan transform need" style="float:left;width:15%"></select>
							<label style="float:left;">—</label>
							<select id="contractNo3" name="contractNo3"class="formSpan transform need" style="float:left;width:15%">
								<option value="SRWY">SRWY</option>
								<option value="SRYS">SRYS</option>
								<option value="SRTY">SRTY</option>
								<option value="SRCZ">SRCZ</option>
								<option value="SRQT">SRQT</option>
								<option value="ZCWW">ZCWW</option>
								<option value="ZCGX">ZCGX</option>
								<option value="ZCSG">ZCSG</option>
								<option value="CZGY">CZGY</option>
								<option value="ZCQT">ZCQT</option>
							</select>
							<label style="float:left;">—</label>
							<input id="contractNo4" name="contractNo4" type="text" class="form-control" style="float:left;width:15%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">合同份数:</label>
							<input id="contractCount" name="contractCount" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;">
							<label class="formLabel">合同期限:</label>
							<input id="contractBeginDate" name="contractBeginDate" type="text" class="form-control" style="float:left;width:142px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" required>
							<label style="float:left;">——</label>
							<input id="contractEndDate" name="contractEndDate" type="text" class="form-control" style="float:left;width:142px;height:26px;"  onfocus="WdatePicker({skin:'whyGreen',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" required>
						</div>
						<div class="form-group formDiv" style="float:left;">
							<label class="formLabel">合同名称:</label>
							<input id="contractName" name="contractName" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">合同单价:</label>
							<input id="contractPrice" name="contractPrice" type="text" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">合同月金额:</label>
							<input id="contractMonthPrice" name="contractMonthPrice" type="text" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:100%;">
							<label class="formLabel">合同总价:</label>
							<input id="contractPriceTotal" name="contractPriceTotal" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">收/付款时间:</label>
							<input id="paymentDate" name="paymentDate" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">收/付款金额:</label>
							<input id="paymentMoney" name="paymentMoney" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">开具发票时间:</label>
							<input id="invoiceDate" name="invoiceDate" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;width:49%;">
							<label class="formLabel">收/付款时间段:</label>
							<select id="paymentTimeSlot" name="paymentTimeSlot"class="form-control" style="float:left;width:70%;height:26px;padding: 2px 12px;">
								<option value="按月支付">按月支付</option>
								<option value="按季度支付">按季度支付</option>
								<option value="按半年支付">按半年支付</option>
								<option value="按年支付">按年支付</option>
								<option value="其他">其他</option>
							</select>
						</div>
						<div class="form-group formDiv" style="float:left;">
							<label class="formLabel">甲方名称:</label>
							<input id="jiaName" name="jiaName" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;">
							<label class="formLabel">乙方名称:</label>
							<input id="yiName" name="yiName" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;">
							<label class="formLabel">丙方名称:</label>
							<input id="bingName" name="bingName" class="form-control" style="float:left;width:70%;height:26px;" required>
						</div>
						<div class="form-group formDiv" style="float:left;height: 50px;">
							<label class="formLabel">合同类别:</label>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="物业合同"/><span>物业合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="延伸合同"/><span>延伸合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="特约合同"/><span>特约合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="场租合同"/><span>场租合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="其他收入"/><span>其他收入</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox" class="transform" value="外委合同" /><span>外委合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="购销合同"/><span>购销合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="施工合同"/><span>施工合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="公共事业合同"/><span>公共事业合同</span></div>
							<div style="width:16%;float:left;"><input name="contractType" type="checkbox"  class="transform" value="其他支出"/><span>其他支出</span></div>
						</div>
					<div class="form-group formDiv" style="float:left;height: 70px;">
						<label class="labelForInput">备注:</label>
						<textarea id="reason" name ="reason" style="width:600px;max-width:600px;max-height:50px;" class="form-control" required></textarea>
					</div>
					<div class="form-group formDiv" style="float:left;width:49%;">
						<label class="formLabel">项目业态类型:</label>
						<select id="projectOperationType" name="projectOperationType"class="form-control" style="float:left;width:70%;height:26px;padding: 2px 12px;" >
							<option value="住宅类">住宅类</option>
							<option value="公寓类">公寓类</option>
							<option value="商业类">商业类</option>
							<option value="写字楼">写字楼</option>
							<option value="学校后勤">学校后勤</option>
							<option value="工业园区厂房">工业园区厂房</option>
							<option value="交通设施">交通设施</option>
							<option value="其他">其他</option>
						</select>
					</div>
					<div class="form-group formDiv" style="float:left;width:49%;">
						<label class="formLabel">面积统计:</label>
						<input id="areaStatistics" name="areaStatistics" class="form-control" style="float:left;width:70%;height:26px;" required>
					</div>
					<div class="form-group formDiv" style="float:left;width: 49%">
						<label class="formLabel">项目地址:</label>
						<input id="projectAddr" name="projectAddr" class="form-control" style="float:left;width:70%;height:26px;" required>
					</div>
					<div class="form-group formDiv" style="float:left;width: 49%">
						<label class="formLabel">甲方名称:</label>
						<input id="projectJIaName" name="projectJIaName" class="form-control" style="float:left;width:70%;height:26px;" required>
					</div>
				　</div>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary btn-sm" onclick="saveContractWill()">保存</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				</div>
			   </div>
			</div>
		</div>
    <script type="text/javascript" src="${ctx }/static/js/system/contractWillHistory.js"></script>
</body>
</html>