package com.xyj.oa.api.vo;

public enum APIErrorCode {
	LoginNameError("001", "用户名有误。"), //
	PassWordError("002", "密码有误。"), //
	UserNoExist("003", "用户不存在"), //
	ParamError("004", "缺少参数。"), //
	ServerError("005", "服务器内部错误。"), //
	ReqStampError("006", "请求时间戳超过允许范围。"), //
	CLIDNeedRegistedError("007", "CLID需要先激活才能使用。"), //
	NeedBodyError("008", "缺少消息体。"), //
	ActiveCodeError("101", "激活码无效。"), //
	CLIDExistError("102", "CLID已被使用。"),
	NotHasOa("103","该员工没有开通"),
	NotHasProcess("104","无可用流程");

	private String errorCode;
	private String error;

	APIErrorCode(String code, String error) {
		errorCode = code;
		this.error = error;
	}

	public String getError() {
		return this.error;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
}