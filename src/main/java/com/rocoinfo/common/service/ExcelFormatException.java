package com.rocoinfo.common.service;

/**
 * 
 * <dl>
 * <dd>描述: excel 文件错误.数据格式不对。</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>@创建时间：2015-9-9 下午5:28:59</dd>
 * <dd>@author： 张文山</dd>
 * </dl>
 */
public class ExcelFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcelFormatException() {
		super();
	}

	public ExcelFormatException(String message) {
		super(message);
	}

	public ExcelFormatException(Throwable cause) {
		super(cause);
	}

	public ExcelFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
