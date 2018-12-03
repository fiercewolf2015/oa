<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>合同经营数据分析</title>
</head>
<body>
	 <div class="ibox-content m-b-sm border-bottom">
        <div class="row" id="searchContractWillDiv">
        	<div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractWillName">流程编号</label>
                    <input type="text" id="instanceNum" name="instanceNum" placeholder="流程编号" class="form-control">
                </div>
            </div>
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
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractPriceTotal">合同总金额</label>
                    <input type="text" id="contractPriceTotal" name="contractPriceTotal" placeholder="金额" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractTime">合同期限</label>
                    <input type="text" id="contractTime" name="contractTime" placeholder="期限（天）" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="contractType">合同类型</label>
                    <select id="contractType" name="contractType"class="form-control tableInput">
                   	 	<option value="">请选择</option>
						<option value="物业合同">物业合同</option>
						<option value="延伸合同">延伸合同</option>
						<option value="特约合同">特约合同</option>
						<option value="场租合同">场租合同</option>
						<option value="其他收入">其他收入</option>
						<option value="购销合同">购销合同</option>
						<option value="施工合同">施工合同</option>
						<option value="公共事业合同">公共事业合同</option>
						<option value="其他支出">其他支出</option>
					</select>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="paymentDate">收付款时间</label>
                    <input type="text" id="paymentDate" name="paymentDate" placeholder="收付款时间" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="paymentMoney">收付款金额</label>
                    <input type="text" id="paymentMoney" name="paymentMoney" placeholder="收付款金额" class="form-control">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="paymentTimeSlot">收付款时间段</label>
                    <select id="paymentTimeSlot" name="paymentTimeSlot"class="form-control tableInput" >
                    	<option value="">请选择</option>
						<option value="按月支付">按月支付</option>
						<option value="按季度支付">按季度支付</option>
						<option value="按半年支付">按半年支付</option>
						<option value="按年支付">按年支付</option>
						<option value="其他">其他</option>
					</select>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="invoiceDate">开具发票时间</label>
                    <input type="text" id="invoiceDate" name="invoiceDate" placeholder="开具发票时间" class="form-control">
                </div>
            </div>
            <div class="col-sm-2" style="float: right;">
                <button type="button" class="btn btn-sm btn-primary btnSearch" onclick="searchPargerList()"><i class="fa fa-search"></i>搜索</button>
            	<button type="button" class="btn btn-sm btn-primary btnWithoutLabel"  onclick="exportExcel();"><i class="fa fa-plus"></i>导出</button>
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
    <script type="text/javascript" src="${ctx }/static/js/statistic/contractWillForm.js"></script>
</body>
</html>