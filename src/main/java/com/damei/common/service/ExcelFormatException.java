package com.damei.common.service;

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
