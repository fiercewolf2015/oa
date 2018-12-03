package com.xyj.oa.api.attendance.exception;

public enum APIErrorCode {
	UnKnownError(1, "一个未知的错误发生。"), //
	ServiceUnavailable(2, "服务临时不可用。"), //
	OutOfRequestLimit(3, "应用已达到设定的请求上限。"), // 
	InvalidAccout(4, "企业帐号无效。"), //
	WrongIdKey(5, "非法身份识别码（id_key无效）。"), //
	WrongSecrectKey(6, "密码验证错误（s_key无效）。"), //
	WrongParams(7, "请求参数错误。"), //
	InvalidVisitTime(8, "无效的访问时间，即不在平台的允许时间段内访问平台。"), //
	WrongXMLFormat(9, "请求XML格式错误。"), // 
	CalculateDailyReportRunning(201, "有自动汇算段中日报进行，访问超时。"), //
	UnInitializeDepts(202, "尚未初始化企业部门结构，请先采用API同步企业部门结构进行初始化。");//

	private int errorCode;
	private String error;

	APIErrorCode(int code, String error) {
		errorCode = code;
		this.error = error;
	}

	public String getError() {
		return this.error;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
}
