/*
 *  Copyright 2012, NEWTOUCH Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF NEWTOUCH CO., LTD.
 *  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *  TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF NEWTOUCH CO., LTD.
 */

package com.ueboot.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *ZIP 文件操作类
 * @author 阳葵
 * @version 1.0
  */
public class ZIPUtil {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final String PATH_SEP = System.getProperty("file.separator");

    /** DOCUMENT ME! */
    public static final int BUFFER = 20480;
    private static Logger logger = LoggerFactory.getLogger(ZIPUtil.class);
    //~ Methods ------------------------------------------------------------------------------------

    
    /**
     * 对文件流解压
     * @param inputStream inputStream
     * @param fileExtractPath 文件解压路径
     * @return void
     */
    public static int unzipFilesToPathWithChecksum(InputStream inputStream,final String fileExtractPath
            ) throws IOException {
    	final CheckedInputStream checkStream     = new CheckedInputStream(inputStream, new CRC32());
        final ZipInputStream     zis             =
            new ZipInputStream(new BufferedInputStream(checkStream));
        int fileCount = 0;
        try {
            ZipEntry entry = null;

            while ((entry = zis.getNextEntry()) != null) {
            	long size = entry.getSize();
            	if(size==0){
            		continue;
            	}
            	fileCount++;
                /*int                        count;
                byte[]                     data = new byte[BUFFER];*/
                final FileOutputStream     fos  =
                    new FileOutputStream(fileExtractPath + PATH_SEP + entry.getName());
                
                final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                int b = -1;
                while ((b = zis.read()) != -1) {
                	dest.write(b);
				}
               /* while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }*/
                String fileName = entry.getName();
                logger.info("zipEntryFileName:{}",fileName);
                dest.flush();
                dest.close();
            }
        } finally {
            zis.close();
            inputStream.close();
            checkStream.close();
        }

        /*if (checkStream.getChecksum().getValue()!= checksum) {
        	throw new RuntimeException("文件CRC32大小校验失败");
        }*/

        return fileCount;
    }
    
}
