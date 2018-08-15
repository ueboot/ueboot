package com.ueboot.file.web.view.vo;

/**
 * 文件上传返回对象，用于解析成JSON格式返回给前端
 * @author YangKui
 *
 */
public class FileUpload {
	//状态
	private boolean status;
	//错误信息
	private String errorMsg;
	
	/**
	 * 文件保存成功后的文件Id
	 */
	private String fileId;
	
	/**
	 * 文件存放路径
	 */
	private String filePath;
	
	
	public FileUpload() {
		super();
	}
	public FileUpload(boolean status, String errorMsg) {
		super();
		this.status = status;
		this.errorMsg = errorMsg;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
