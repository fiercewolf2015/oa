package com.xyj.oa.statistic.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xyj.oa.statistic.entity.ContractWillForm;
import com.xyj.oa.system.service.SystemService;
import com.xyj.oa.util.BeanFieldsTeller;
import com.xyj.oa.util.LogUtil;
import com.xyj.oa.util.TfOaUtil;

@Controller
@RequestMapping(value = "contractWillStatistic")
public class ContractWillStatisticController {

	private static Logger logger = LoggerFactory.getLogger(ContractWillStatisticController.class);

	private static final String COLSTR = "申请日期,分公司,项目名称,流程编号,合同名称,甲方名称,乙方名称,丙方名称,第四方,起始日,终止日,合同时长（天）,合同单价（元）,数量,合同每月价款（元）,合同总额（元）,收/付款时间,收/付款金额,开具发票时间,收/付款时间段,合同类型,合同内容,财务类型,是否返还,页数,备注,项目业态类型,面积统计,项目地址,甲方名称,支出";

	private static final String METHODSSTR = "getApplyDate,getCompanyName,getProjectName,getInstanceNum,getContractName,getJiaName,getYiName,getBingName,getDisifangName,getContractBeginDate,getContractEndDate,getContractTime,getContractPrice,getContractCount,getContractMonthPrice,getContractPriceTotal,getPaymentDate,getPaymentMoney,getInvoiceDate,getPaymentTimeSlot,getContractType,getContractConten,getCaiwuType,getIfFanHuan,getYeShu,getReason,getProjectOperationType,getAreaStatistics,getProjectAddr,getProjectJiaName,getZhiChu";

	private static final List<String> cols = new ArrayList<String>();

	private static final List<String> methods = new ArrayList<String>();

	static {
		String[] colsArray = COLSTR.split(",");
		String[] methodArray = METHODSSTR.split(",");
		for (int i = 0; i < colsArray.length; i++)
			cols.add(colsArray[i]);
		for (int i = 0; i < methodArray.length; i++)
			methods.add(methodArray[i]);
	}

	@Autowired
	private SystemService systemService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "statistic/contractWillForm";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo, @RequestParam(defaultValue = "10", value = "rows") int pageSize,
			@RequestParam(required = false, value = "sidx") String sidx, @RequestParam(required = false, value = "sord") String sord) {
		try {
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, systemService.findContractWillCountWithParams(params),
					systemService.findContractWillsListWithParams(params, pageNo, pageSize, sidx, sord));
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			return TfOaUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			response.setHeader("content-disposition", "attachment;filename=" + new String("合同经营数据.xls".getBytes("gb2312"), "iso-8859-1"));
			List<ContractWillForm> list = systemService.findAllCwByParams("");
			HSSFWorkbook workbook = BeanFieldsTeller.getWorkbookForDto(list == null ? null : list.toArray(), cols, methods, ContractWillForm.class);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}

}
