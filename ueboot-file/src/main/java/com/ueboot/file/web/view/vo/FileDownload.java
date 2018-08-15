package com.ueboot.file.web.view.vo;

/**
 * 文件下载服务VO
 * @author YangKui
 *
 */
public class FileDownload {
	/**
	 * 文件名称，包含扩展名
	 */
	private String fileName;
	/**
	 * 文件全路径，包含文件名称
	 */
	private String filePath;
	/**
	 * 文件类型
	 */
	private String fileContentType;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
