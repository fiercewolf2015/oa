package com.xyj.oa.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcelUtil {

	public static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16; //设置cell编码解决中文高位字节截断

	private static final String HOUR = "小时";

	private static final String MIN = "分";

	public static Map<String, HSSFCellStyle> detailStyle = new HashMap<String, HSSFCellStyle>();
	
	public static final int EXPOR_PAGESIZE = 65535;

	/**
	 * 设置列值
	 * 判断传入值得类型 然后设置为相应的类型 以保证数据运算的准确
	 * @param cell
	 * @param value
	 * @param hssfCellStyle 
	 */
	public static void setCellValue(HSSFCell cell, Object value) {
		if (value instanceof String) {
			HSSFRichTextString richText = new HSSFRichTextString(value.toString());
			cell.setCellValue(richText);
		} else if (value instanceof Integer || value instanceof Float || value instanceof Double) {
			cell.setCellValue(Double.valueOf(value.toString()));
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof java.util.Date) {
			cell.setCellValue((java.util.Date) value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
		} else if (value instanceof HSSFRichTextString) {
			cell.setCellValue((HSSFRichTextString) value);
		} else {
			HSSFRichTextString richText = new HSSFRichTextString(value == null ? "" : value.toString());
			cell.setCellValue(richText);
		}
	}

	public static HSSFCellStyle getCellStyle(HSSFCellStyle defaultCellStyle, HSSFWorkbook workbook) {
		if (defaultCellStyle == null) {
			defaultCellStyle = workbook.createCellStyle();
		}
		return defaultCellStyle;
	}

	/**
	 * 设置标题cell格式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getHeadCellStyle(HSSFWorkbook workbook, boolean needBold) {
		HSSFFont headFont = workbook.createFont(); // 创建字体格式
		headFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		if (needBold)
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle headStyle = workbook.createCellStyle(); // 创建单元格风格.
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());

		headStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//设置背景颜色为浅灰色
		headStyle.setFont(headFont); // 将字体格式加入到单元格风格当中
		headStyle.setWrapText(true);
		return headStyle;
	}
	
	public static HSSFCellStyle getRowStyle(HSSFWorkbook workbook) {
		HSSFCellStyle headStyle = workbook.createCellStyle(); // 创建单元格风格.
		headStyle.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());//设置背景颜色为浅灰色
		return headStyle;
	}

	public static HSSFCellStyle getDetailInfoCellStyle(HSSFWorkbook workbook, boolean needRed) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		if (needRed)
			dFont.setColor(HSSFFont.COLOR_RED);//设置字体红色
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.AQUA().getIndex());//设置背景颜色为黄色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	public static HSSFCellStyle getDetailInfoCellStyleForEven(HSSFWorkbook workbook, boolean needRed) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		if (needRed)
			dFont.setColor(HSSFFont.COLOR_RED);//设置字体红色
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.LIGHT_YELLOW().getIndex());//设置背景颜色为黄色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	public static HSSFCellStyle getHalfPathHeadCellStyle(HSSFWorkbook workbook) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		dFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.LIGHT_ORANGE().getIndex());//设置背景颜色为浅橘色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	public static HSSFCellStyle getHalfPathDetailInfoCellStyle(HSSFWorkbook workbook, boolean needRed) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		if (needRed)
			dFont.setColor(HSSFFont.COLOR_RED);//设置字体红色
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.LIGHT_ORANGE().getIndex());//设置背景颜色为黄色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	/**
	 * 设置在途时间标头样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getTransitHeadCellStyle(HSSFWorkbook workbook) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		dFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.PALE_BLUE().getIndex());//设置背景颜色为浅蓝色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	/**
	 * 设置停靠时间标头样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getStopTimeHeadCellStyle(HSSFWorkbook workbook) {
		HSSFFont dFont = workbook.createFont(); // 创建字体格式
		dFont.setColor(HSSFFont.SS_NONE); // 设置单元格字体的颜色.
		dFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle dStyle = workbook.createCellStyle(); // 创建单元格风格.
		dStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		dStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dStyle.setRightBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dStyle.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dStyle.setLeftBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dStyle.setTopBorderColor(new HSSFColor.BLACK().getIndex());
		dStyle.setFillForegroundColor(new HSSFColor.LIGHT_CORNFLOWER_BLUE().getIndex());//设置背景颜色为浅矢车菊蓝色
		dStyle.setFont(dFont); // 将字体格式加入到单元格风格当中
		dStyle.setWrapText(true);
		return dStyle;
	}

	public static HSSFCellStyle getStaffCellStyle(HSSFWorkbook workbook, String color) {
		HSSFFont cellFont = workbook.createFont(); // 创建字体格式
		if ("blue".equals(color))
			cellFont.setColor(new HSSFColor.BLUE().getIndex());
		else if ("red".equals(color))
			cellFont.setColor(new HSSFColor.RED().getIndex());
		else
			cellFont.setColor(new HSSFColor.BLACK().getIndex());
		HSSFCellStyle headStyle = workbook.createCellStyle(); // 创建单元格风格.
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
		headStyle.setFont(cellFont);
		return headStyle;
	}

	public static HSSFRow getRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	public static String returnHourTypeByMin(int minTime) {
		String result = "";
		if (minTime <= 0)
			return result;
		int hour = minTime / 60;
		int min = minTime % 60;
		result = hour + HOUR + min + MIN;
		return result;
	}
}
