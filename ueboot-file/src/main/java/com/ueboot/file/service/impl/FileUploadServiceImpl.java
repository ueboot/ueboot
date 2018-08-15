package com.ueboot.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ueboot.file.util.DateUtil;
import com.ueboot.file.util.IdWorker;
import com.ueboot.file.util.MD5Util;
import com.ueboot.file.web.view.vo.FileDownload;
import com.ueboot.file.web.view.vo.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ueboot.file.exceptions.FileUploadException;
import com.ueboot.file.service.FileUploadService;
import com.ueboot.file.util.DesUtil;

/**
 * 文件上传服务类实现
 * @author YangKui
 *
 */
public class FileUploadServiceImpl implements FileUploadService,InitializingBean {
	
	private Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	/**
	 * 文件可上传类型如：zip,只校验当前上传的文件后缀对压缩文件内的信息不做校验。
	 */
	private List<String> fileType;
	
	/**
	 * ZIP文件存放基础路径
	 */
	private String zipFileBasePath;
	
	/**
	 * 上传的文件存放基础路径，在此路径下再按日期存放
	 */
	private String uploadFileBasePath;
	/**
	 * 上传的自定义文件名的文件存放基础路径，在此路径下再按日期存放
	 */
	private String uploadNamedFileBasePath;
	
	 //标志是否已判断文件夹创建过
    private  boolean hasMkdir;
    /**
     * 校验请求来源是否合法，加密参数，与文件提交方保持一致
     */
    private String hexChars;
    
    /**
	 * 文件上传最大值，每种类型的文件可以设置不同的最大值
	 */
	private Map<String,Long> fileMaxSizes ;
	
	/**
	 * 文件下载时的contentType标识，根据文件扩展名来确定
	 * 如：pdf  application/pdf
	 */
	private Map<String,String> fileContentTypes;
	
	/**
	 * 允许客户端上传的自定义文件名的文件类型，这类文件单独保存
	 */
	private Map<String,String> allowNamedFileTypesMap;
	
    /**
     * 文件名加密KEY值，解密时也需要此值。此值一旦使用，不要随意更改，防止以前生成的文件后期无法读取。
     */
    private String fileSaveHexChars = "newtouch" ;
    
    private static String fileSeparator = System.getProperty("file.separator");
    
	
	@Override
	public FileUpload validateFile(MultipartFile file, String policyNo, String snCode, String encryptValue) {
		
		if("".equals(policyNo)){
			logger.warn("保单号不能为空");
    		return new FileUpload(false,"保单号不能为空！");
    	}
		
        FileUpload f = this.checkFile(file, snCode,encryptValue, null);
		return f;
	}

	@Override
	public FileUpload validateFile(String snCode, String encryptValue, String uploadFileName, MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String ext = this.getFileExt(fileName);
		if ("zip".equals(ext)) {
			logger.warn("当前上传的文件类型不允许,{}", fileName);
			return new FileUpload(false, "当前上传的文件类型不允许！");
		}

		FileUpload f = this.checkFile(file, snCode, encryptValue, uploadFileName);
		return f;
	}


	/**
	 * 校验文件是否合法
	 * @param file
	 * @param f
	 */
	private FileUpload checkFile(MultipartFile file,String snCode, String encryptValue, String uploadFileName) {
		if(file==null){
			logger.warn("上传的文件为空");
			return new FileUpload(false,"上传的文件为空!");
		}
		
		if("".equals(snCode)){
			logger.warn("snCode不能为空！");
			return new FileUpload(false,"snCode不能为空！");
		}
		if("".equals(encryptValue)){
			logger.warn("encryptValue不能为空！");
			return new FileUpload(false,"encryptValue不能为空！");
		}
		
		if(!MD5Util.md5Hex(snCode+this.getHexChars()).equals(encryptValue)){
			logger.warn("encryptValue校验不通过！");
			return new FileUpload(false,"encryptValue校验不通过！");
		}
		
		String fileName = file.getOriginalFilename();
        Long fileSize = file.getSize(); 
        logger.info("当前文件名：{},文件大小：{}",new Object[]{fileName,fileSize});   
        if(fileName.indexOf(".")==0){
        	logger.warn("当前上传的文件类型不符，无扩展名,{}",fileName);
        	return new FileUpload(false,"当前上传的文件类型不符，无扩展名！");
		}
        String ext = this.getFileExt(fileName);
      
        if(!fileType.contains(ext)){
        	logger.warn("当前上传的文件类型不允许,{}",fileName);
        	return new FileUpload(false,"当前上传的文件类型不允许！");
        }
        if (StringUtils.hasText(uploadFileName)) {
			Matcher matcher = Pattern.compile("^[A-Za-z0-9]+$").matcher(uploadFileName);
			if (!matcher.find()) {
				logger.warn("uploadFileName:{}校验不通过！",uploadFileName);
				return new FileUpload(false,"文件名校验不通过,文件名只能由数字和大小写字母组成！");
			}
			if (allowNamedFileTypesMap.get(ext) == null) {
				logger.warn("当前上传的文件{}不是允许自定义文件名的文件类型", uploadFileName + "." + ext);
				return new FileUpload(false,"当前上传的文件不是允许自定义文件名的文件类型！");
			}
		}
        Long fileMaxSize = fileMaxSizes.get(ext); 
        
        if(fileSize>fileMaxSize){
        	logger.warn("当前上传的文件类型为：{}，大小为：{}，超过最大限制:{}",new Object[]{ext,fileSize,fileMaxSize});
        	return new FileUpload(false,"当前上传的文件大小为："+fileSize+"，超过最大限制:"+fileMaxSize);
        }
        return null;
	}
	

	/**
	 * 获取Zip文件解压存放路径
	 */
	@Override
	public String getExportFilePath() {
		if(!hasMkdir){
    		File file = new File(zipFileBasePath);
        	if(!file.exists()){
        		if(!file.mkdirs()){
        			throw new RuntimeException("当前文件夹无法创建"+zipFileBasePath);
        		}
        	}
        	hasMkdir = true;
    	}
    	return zipFileBasePath;
	}
	@Override
	public FileUpload saveFile(MultipartFile file){
		FileUpload fileUpload = new FileUpload(true,"上传成功！");
		String fileSavePath = this.getFileSavePath();
		Long fileId = new IdWorker(1).nextId();
		String ext = this.getFileExt(file.getOriginalFilename());
		String fileName = fileSavePath+"/"+fileId+"."+ext;
		try {
			file.transferTo(new File(fileName));
		} catch (IllegalStateException e) {
			logger.error("文件保存异常IllegalStateException",e);
			throw new FileUploadException("文件保存异常",e);
		} catch (IOException e) {
			logger.error("文件保存异常IOException",e);
			throw new FileUploadException("文件保存异常",e);
		}
		String filePath = fileName.substring(fileName.indexOf(this.uploadFileBasePath)+this.uploadFileBasePath.length(),fileName.length());
		try {
			fileUpload.setFilePath(filePath);
			//对文件名(除基础路径以外的所有名称)加密,如：文件名为：/20130129/12342342323.jpg
			String encryptFileId = DesUtil.encrypt(filePath, this.fileSaveHexChars);
			fileUpload.setFileId(encryptFileId);
		} catch (Exception e) {
			logger.error("文件名加密出现异常",e);
			throw new FileUploadException("文件保存异常",e);
		}
		
		return fileUpload;
	}
	@Override
	public FileUpload saveNamedFile(MultipartFile file, String uploadFileName){
		FileUpload fileUpload = new FileUpload(true, "上传成功！");
		String fileSavePath = this.getNamedFileSavePath();
		String ext = this.getFileExt(file.getOriginalFilename());
		String fileName = fileSavePath+"/"+uploadFileName+"."+ext;
		try {
			file.transferTo(new File(fileName));
		} catch (IllegalStateException e) {
			logger.error("文件保存异常IllegalStateException",e);
			throw new FileUploadException("文件保存异常",e);
		} catch (IOException e) {
			logger.error("文件保存异常IOException",e);
			throw new FileUploadException("文件保存异常",e);
		}
		
		return fileUpload;
	}

	@Override
	public FileDownload getFile(String fileId) {
		String filePath = "";
		try {
			filePath = DesUtil.decrypt(fileId, this.fileSaveHexChars);
		} catch (Exception e) {
			logger.error("文件名解密出现异常",e);
			return null;
		}
		FileDownload file = new FileDownload();
		String fileName = filePath.substring(filePath.lastIndexOf(fileSeparator)+1,filePath.length());
		file.setFileName(fileName);
		file.setFileContentType(fileContentTypes.get(this.getFileExt(fileName)));
		file.setFilePath(this.uploadFileBasePath+filePath);
		return file;
	}

	/**
	 * 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
	 * @param fileName
	 * @return
	 */
	private String getFileExt(String fileName){
		
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        ext = ext.toLowerCase();
        return ext;
		
	}
	
	/**
	 * 按当期日期生成文件存放路径
	 * @return
	 */
	private String getFileSavePath() {
		String dir = DateUtil.getTimeByCustomPattern(new Date(),
				DateUtil.datePatternYYMMDD);
		if (uploadFileBasePath.endsWith("/")) {
			dir = uploadFileBasePath + dir;
		} else {
			dir = uploadFileBasePath + "/" + dir;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				throw new RuntimeException("当前文件夹无法创建目录" + dir);
			}
		}
		return dir;
	}
	
	/**
	 * 按当期日期生成数据库文件存放路径
	 * @return
	 */
	private String getNamedFileSavePath() {
		String dir = DateUtil.getTimeByCustomPattern(new Date(),
				DateUtil.datePatternYYMMDD);
		if (uploadNamedFileBasePath.endsWith("/")) {
			dir = uploadNamedFileBasePath + dir;
		} else {
			dir = uploadNamedFileBasePath + "/" + dir;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				throw new RuntimeException("当前文件夹无法创建目录" + dir);
			}
		}
		return dir;
	}

	public void setFileType(List<String> fileType) {
		this.fileType = fileType;
	}

	public void setBasePath(String basePath) {
		this.zipFileBasePath = basePath;
	}

	public String getHexChars() {
		return hexChars;
	}

	public void setHexChars(String hexChars) {
		this.hexChars = hexChars;
	}
	
	
	/**
	 * @param zipFileBasePath the zipFileBasePath to set
	 */
	public void setZipFileBasePath(String zipFileBasePath) {
		this.zipFileBasePath = zipFileBasePath;
	}


	/**
	 * @param uploadFileBasePath the uploadFileBasePath to set
	 */
	public void setUploadFileBasePath(String uploadFileBasePath) {
		this.uploadFileBasePath = uploadFileBasePath;
	}

	/**
	 * @param uploadNamedFileBasePath the uploadNamedFileBasePath to set
	 */
	public void setUploadNamedFileBasePath(String uploadNamedFileBasePath) {
		this.uploadNamedFileBasePath = uploadNamedFileBasePath;
	}

	/**
	 * @param fileMaxSizes the fileMaxSizes to set
	 */
	public void setFileMaxSizes(Map<String, Long> fileMaxSizes) {
		this.fileMaxSizes = fileMaxSizes;
	}

	/**
	 * @param fileContentTypes the fileContentTypes to set
	 */
	public void setFileContentTypes(Map<String, String> fileContentTypes) {
		this.fileContentTypes = fileContentTypes;
	}

	/**
	 * @param allowNamedFileTypesMap the allowNamedFileTypesMap to set
	 */
	public void setAllowNamedFileTypesMap(Map<String, String> allowNamedFileTypesMap) {
		this.allowNamedFileTypesMap = allowNamedFileTypesMap;
	}

	/**
	 * @param fileSaveHexChars the fileSaveHexChars to set
	 */
	public void setFileSaveHexChars(String fileSaveHexChars) {
		this.fileSaveHexChars = fileSaveHexChars;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(fileType==null || fileType.isEmpty()){
			throw new RuntimeException("fileType属性为空，文件上传类型配置不能为空!");
		}
		for(String type:fileType){
			if(this.fileMaxSizes.get(type)==null){
				throw new RuntimeException("当前文件类型为："+type+"的最大允许上传值为空，必须指定具体的值");
			}
			if(this.fileContentTypes.get(type)==null){
				throw new RuntimeException("当前文件类型为："+type+"的contentType为空，必须指定具体的值");
			}
		}
		
	}

}
