package com.ueboot.file.exceptions;

/**
 * 文件 上传异常类
 * 
 * @author YangKui
 *
 */
public class FileUploadException extends RuntimeException {
	private String errorMsg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191369512546915628L;

	public FileUploadException(String errorMsg,Throwable e) {
		super(errorMsg,e);
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}