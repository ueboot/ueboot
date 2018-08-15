/*
 *  Copyright 2012, NEWTOUCH Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF NEWTOUCH CO., LTD.
 *  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *  TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF NEWTOUCH CO., LTD.
 */
package com.ueboot.file.web.view;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.ueboot.file.service.impl.FileUploadServiceImpl;
import com.ueboot.file.util.ZIPUtil;
import com.ueboot.file.web.view.vo.FileDownload;
import com.ueboot.file.web.view.vo.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ueboot.file.exceptions.FileUploadException;
import com.ueboot.file.service.FileUploadService;


/**
 * 文件上传下载接口
 *
 * @author YangKui
 */
@Controller
@RequestMapping("/ueboot/file")
@ConditionalOnBean(value = FileUploadServiceImpl.class)
public class FileUploadController {
    // ~ Static fields/initializers
    // -----------------------------------------------------------------
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);


    // ~ Instance fields
    // ----------------------------------------------------------------------------
    @Autowired
    private FileUploadService fileUploadService;
    // ~ Methods
    // ------------------------------------------------------------------------------------
    

    @RequestMapping(value ="/upload",method = RequestMethod.POST)
    @ResponseBody
    public FileUpload upload(ModelMap model, @RequestParam("file") MultipartFile zipFile
    		, @RequestParam(defaultValue = "") String policyNo
    		, @RequestParam(defaultValue = "") String snCode
    		, @RequestParam(defaultValue = "") String encryptValue)
        throws Exception {
    	long t1 = System.currentTimeMillis();	 
    	//当前时间
    	String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	logger.info("开始上传文件,当前时间：{}, 保单号：{}, SN号:{}", new Object[]{currentTime, policyNo, snCode});
    	FileUpload f = this.fileUploadService.validateFile(zipFile,policyNo,snCode,encryptValue);
    	
    	Long fileSize = zipFile.getSize();	
    	
    	if(f!=null){
    		logger.info("上传文件失败：{}", f.getErrorMsg());
    		return f;
    	}else{
    		f = new FileUpload(true,"上传解压成功!");
    	}
        InputStream input = zipFile.getInputStream();
        try{
        	String saveFilePath = this.fileUploadService.getExportFilePath();
        	
        	int count = ZIPUtil.unzipFilesToPathWithChecksum(input, saveFilePath);
        	long t2 = System.currentTimeMillis();
        	logger.info("结束上传文件,当前时间：{}, 保单号：{}, SN号:{}, 文件大小：{}, 总耗时：{}", new Object[]{currentTime, policyNo, snCode, fileSize, t2-t1});
        	logger.info("保单号:{},共上传成功{}张图片",new Object[]{policyNo,count});
        }catch(Exception e){
        	f = new FileUpload(false,"文件解压异常："+e.getMessage());
        	logger.error("保单号:{},文件解压异常",policyNo,e);
        }
        return f;
    }

    @RequestMapping(value ="/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public FileUpload uploadFile(ModelMap model,@RequestParam("file") MultipartFile file
    		,@RequestParam(defaultValue = "") String snCode
    		,@RequestParam(defaultValue = "") String businessCode
    		,@RequestParam(defaultValue = "") String uploadFileName
    		,@RequestParam(defaultValue = "") String encryptValue)
        throws Exception {
		long t1 = System.currentTimeMillis();
		logger.info("开始上传文件{},业务识别码:{},SN号:{}", new Object[] {uploadFileName, businessCode, snCode });
		FileUpload f = this.fileUploadService.validateFile(snCode, encryptValue, uploadFileName, file);

		Long fileSize = file.getSize();

		if (f != null) {
			logger.info("上传文件失败：{},业务识别码:{}", f.getErrorMsg(), businessCode);
			return f;
		} else {
			f = new FileUpload(true, "上传解压成功!");
		}
		try {
			if ("".equals(uploadFileName)) {
				f = this.fileUploadService.saveFile(file);
			} else {
				f = this.fileUploadService.saveNamedFile(file, uploadFileName);
			}

			long t2 = System.currentTimeMillis();
			logger.info("结束上传文件,业务识别码：{}, SN号:{},文件名:{}, 文件大小：{}, 总耗时：{}", new Object[] { businessCode, snCode, uploadFileName, fileSize, t2 - t1 });
		} catch (FileUploadException e) {
			f = new FileUpload(false, e.getMessage());
			logger.error("文件保存异常", e);
		} catch (Exception e) {
			f = new FileUpload(false, "文件保存异常：" + e.getMessage());
			logger.error("文件保存异常", e);
		}
        return f;
    }


	@RequestMapping(value = "/download")
	public void download(@RequestParam(defaultValue = "") String fileId,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		if (null == fileId || "".equals(fileId.trim())) {
			Writer writer = response.getWriter();
			writer.write("{error:'fileId不能为空'}");
			writer.close();
			return ;
		}
		FileDownload fileDown = this.fileUploadService.getFile(fileId);
		if (fileDown == null) {
			Writer writer = response.getWriter();
			writer.write("{error:'文件不存在!'}");
			writer.close();
			return ;
		}
		File file = new File(fileDown.getFilePath());
		if (!file.exists()) {
			Writer writer = response.getWriter();
			writer.write("{error:'文件不存在!'}");
			writer.close();
			return ;
		}

		long totalSize = 0;
		long filelength = file.length();
		byte[] b = new byte[1024];

		// 设置文件输出流
		FileInputStream fin = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fin);

		response.reset();
		
		// 设置响应头信息，让下载的文件显示保存信息
		response.setHeader("Content-disposition", "attachment;filename="
				+ java.net.URLEncoder.encode(fileDown.getFileName(), "UTF-8"));
		// 设置输出流的MIME类型
		response.setContentType(fileDown.getFileContentType());
		// 确定长度
		String fileSize = Long.toString(filelength);
		// 设置输出文件的长度
		response.setHeader("Content-Length", fileSize);
		
		// 取出输出流
		ServletOutputStream servletOut = response.getOutputStream();
		try{
			// 发送文件数据，每次1024字节，最后一次单独计算
			while (totalSize < filelength) {
				totalSize += 1024;
				if (totalSize > filelength) {
					// 最后一次传送的字节数
					byte[] leftpart = new byte[1024 - (int) (totalSize - filelength)];
					in.readFully(leftpart);// 读入字节数组
					servletOut.write(leftpart);// 写入输出流
				} else {
					in.readFully(b);// 读入1024个字节到字节数组b
					servletOut.write(b);// 写出输出流
				}
			}
		}catch(Exception e){
			logger.error("文件输出异常",e);
		}finally{
			servletOut.close();
			if (in != null) {
				in.close();
			}
			if (fin != null) {
				fin.close();
			}
		}

	}

    @RequestMapping("/test")
    public String saleInterfaceTest(ModelMap model) {
        return "file/uploadFile";
    }
    

    @RequestMapping("/fileUpload")
    public String fileUploadInterface(ModelMap model) {
        return "file/upload";
    }
}
