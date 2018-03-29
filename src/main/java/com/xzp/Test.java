package com.xzp;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.util.List;

/**
 * Created by Val1ant on 2017/9/27.
 */
public class Test {




    public static void main(String[] args) {
        String filePath = "C:\\Users\\Xbear\\Desktop\\1.docx";
        File file = new File(filePath);

        try {
            ZipFile zipFile = new ZipFile(file);
            System.out.println(zipFile.isEncrypted());

            List fileHeaderList = zipFile.getFileHeaders();
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                if (fileHeader!=null){
                    System.out.println(fileHeader.getFileName());
                }
            }

        }catch (ZipException e){
            e.printStackTrace();
        }
    }
}
