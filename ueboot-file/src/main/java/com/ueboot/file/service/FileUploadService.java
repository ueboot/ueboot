package com.ueboot.file.service;

import com.ueboot.file.web.view.vo.FileDownload;
import com.ueboot.file.web.view.vo.FileUpload;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务类
 * @author YangKui
 *
 */
public interface FileUploadService {
	
	/**
	 * 对上传的ZIP文件做校验处理，如果返回对象不为空，则校验失败。
	 * @param zipFile
	 * @return
	 */
	public FileUpload validateFile(MultipartFile zipFile, String policyNo, String snCode, String encryptValue);
	
	/**
	 *  对上传的文件做校验处理，如果返回对象不为空，则校验失败。
	 * @param file  文件
	 * @param snCode 上传文件的设备号
	 * @param encryptValue 校验值=md5(设备号+hexChars）
	 * @return
	 */
	public FileUpload validateFile(String snCode, String encryptValue, String uploadFileName, MultipartFile file);
	
	/**
	 *  获取文件解压存放路径
	 * @return
	 */
	public String getExportFilePath();
	
	/**
	 * 保存上传的文件，返回文件标识
	 * @param file
	 * @return
	 */
	public FileUpload saveFile(MultipartFile file);
	
	
	/**
	 * 保存上传的自定义文件名的文件，该文件区别于其他文件上传，上传时需要提供文件名而非程序自动生成，返回文件标识
	 * @param file
	 * @return
	 */
	public FileUpload saveNamedFile(MultipartFile file, String uploadFileName);
	
	
	/**
	 * 根据加密后的文件ID，获取文件
	 * @param fileId
	 * @return
	 */
	public FileDownload getFile(String fileId);

}
