package com.ueboot.shiro.util;

import jodd.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileDeleteTest {

    @Test
    public void test() throws IOException {
        File file = new File("/Users/yangkui/workspace/baiyang/支付核心0731/code/");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                this.delete(f);
            }
        }
    }

    public void delete(File file) throws IOException {
        String fileName = file.getName();
        if(file.isDirectory()&&(fileName.equals(".settings")
                || fileName.equals("target"))){
            FileUtil.delete(file);
            System.out.println("删除文件夹"+file.getAbsolutePath()+fileName);
        }else if(file.isFile() && (fileName.equals(".classpath")
                || fileName.equals(".project") || fileName.equals(".gitignore"))
        ){
            file.delete();
            System.out.println("删除文件"+file.getAbsolutePath()+fileName);
        }
        File[] files = file.listFiles();
        if(files == null){
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            this.delete(f);
        }
    }
}
