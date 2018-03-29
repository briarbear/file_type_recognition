package com.xzp;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Val1ant on 2017/9/26.
 */
 class Offices {


    public static void main(String[] args){

        String filePath = "C:/Users/Val1ant/Desktop/nstl.zip";
        File file = new File(filePath);
//        readZipFile(filePath);
//        System.out.println(new Offices().getOldType(file));
//        System.out.println(new Offices().getOldType(file));
        System.out.println(new Offices().getNewType(file));




    }


    /**
     * 可以处理被加密文件的类型识别
     * @param file
     * @return
     */
    public String getNewType(File file){

        try {
            ZipFile zipFile = new ZipFile(file);
            if (zipFile.isEncrypted())
                System.out.println("该文件被加密");

            List<FileHeader> list = zipFile.getFileHeaders();
            for (int i = 0; i < list.size(); i++) {
                FileHeader header = list.get(i);
                String s = header.getFileName();
                switch (s){
                    case "word/document.xml":return "docx";
                    case "xl/workbook.xml":return "xlsx";
                    case "ppt/presentation.xml":return "pptx";
                    case "visio/document.xml":return "vsdx";
                    default:break;
                }

            }
        }catch (ZipException e){
            e.printStackTrace();
        }

        return "zip";
    }





    /**
     * 由Xbear提供
     * 支持对新版office类型的识别
     * @param file
     * @return 返回文档后缀 docx、xlsx、pptx
     */
    private String getNewType2(File file){

        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        List<PackagePart> list = null;
        try {
            list = opcPackage.getParts();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        for (PackagePart p :
                list) {
            String s =p.getPartName().getName();
//            System.out.println(p.getPartName().getName());
            switch (s){
                case "/word/document.xml":return "docx";
                case "/xl/workbook.xml":return "xlsx";
                case "/ppt/presentation.xml":return "pptx";
                case "/visio/document.xml":return "vsdx";
                default:
                    break;
            }
        }

        return "zip";
    }

    /**
     * 由Xbear提供
     * 支持对老版的office文档类型的识别
     * @param file
     * @return 返回文档后缀 doc、ppt、xls
     * @throws InvalidFormatException
     */
    public String getOldType(File file){



        try {
            NPOIFSFileSystem fileSystem = new NPOIFSFileSystem(file);
            Iterator<Entry> iterator = fileSystem.getRoot().getEntries();
            while (iterator.hasNext()){
                String string = iterator.next().getName();
                System.out.println(string);
                switch (string){
                    case "Workbook":return "xls";
                    case "WordDocument":return "doc";
                    case  "PowerPoint Document":return "ppt";
                    case "VisioDocument":return "vsd";
                    default:break;
                }
            }

            return null;

        }catch (IOException e){
            System.out.println("异常");
            e.printStackTrace();

        }

        return null;
    }


}
