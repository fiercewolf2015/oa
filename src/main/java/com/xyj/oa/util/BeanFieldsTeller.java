package com.xyj.oa.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

@SuppressWarnings("all")
public class BeanFieldsTeller {

	private static final Log log = LogFactory.getLog(BeanFieldsTeller.class);

	private static Map<String, Map<String, String>> beanFiledsPresentValueTable = new HashMap();

	private static boolean inited = false;

	private static final int MAXTOTALCOUNT = 65536;//控制excel记录数最多导出65535的情况

	public static HSSFWorkbook getWorkbookForDto(Object[] result, List<String> cols, List<String> method, Class className) throws Exception {
		HSSFWorkbook workbook = null;
		if (result == null) {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFCell cell = sheet.createRow(0).createCell(0);
			sheet.setColumnWidth(0, 20 * 256);
			cell.setCellValue("没有符合要求的数据");
			return workbook;
		}
		HSSFSheet sheet = null;
		Class recordClass = className;
		int index = 0;
		if (result != null) {
			workbook = new HSSFWorkbook();
			Method mostLineMethod = null;
			String dataPoint = ",";
			Method getmethod = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			Object invokeresult = null;
			for (Object record : result) {
				if (index > MAXTOTALCOUNT - 1 || index == 0) {
					sheet = workbook.createSheet();
					sheet.setDefaultColumnWidth(sheet.getDefaultColumnWidth() + 8);
					index = 0;
				}
				if (index == 0) {//判断当前为第一行或者新的sheet时，创建标题行
					row = sheet.createRow(index);
					for (int i = 0; i < cols.size(); i++) {
						cell = row.createCell(i);
						cell.setCellValue(cols.get(i));
						setCellCenter(cell);
					}
					index++;
				}
				row = sheet.createRow(index);
				int j = 0;
				int incremental = 1;
				for (String m : method) {
					cell = row.createCell(j);
					getmethod = recordClass.getMethod(m);
					invokeresult = getmethod.invoke(record);
					ExportExcelUtil.setCellValue(cell, invokeresult);
					setCellCenter(cell);
					j++;
				}
				index += incremental;
			}
		}
		return workbook;
	}

	public static void setCellCenter(HSSFCell cell) {
		cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER); //横向对齐
		cell.getCellStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//纵向对齐
	}

	public static HSSFCell getCell(HSSFRow row, int columnIndex) {
		HSSFCell cell = row.getCell(columnIndex);
		if (cell == null) {
			cell = row.createCell(columnIndex);
		}
		return cell;
	}

	public static void addCellRange(HSSFSheet sheet, HSSFRow row, Object value, int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress address = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(address);
		HSSFCell cell = getCell(row, firstCol);
		ExportExcelUtil.setCellValue(cell, value);
		setCellCenter(cell);
	}

}