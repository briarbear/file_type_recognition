package com.xzp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Val1ant on 2017/9/22.
 */
public class FileTypes {




    public boolean reNameFileType(File file){
        if (file==null){
            return false;
        }

        if (file.getName().contains("."))
            return false;

        String fileName = file.getAbsolutePath();
        String Type = this.getFileTypes(file);
        if (Type!=null)
            file.renameTo(new File(fileName+"."+Type));
        return true;
    }

    private String getFileTypes(File file){
        HashMap<String,List<String>> Types = new HashMap<>();
        Types.put("ffd8ff",new ArrayList<String>(){{add("jpg");}});
        Types.put("255044462d312e",new ArrayList<String>(){{add("pdf");}});
        Types.put("504b0304",new ArrayList<String>(){{add("zip");add("docx");add("xlsx");add("pptx");add("vsdx");}});
        Types.put("d0cf11e0a1b11ae1",new ArrayList<String>(){{add("doc");add("xls");add("ppt");add("vsd");}});
        Types.put("89504e470d0a1a0a",new ArrayList<String>(){{add("png");}});
        Types.put("474946383961",new ArrayList<String>(){{add("jif");}});
        Types.put("52617221",new ArrayList<String>(){{add("rar");}});
        Types.put("41564920",new ArrayList<String>(){{add("avi");}});

        byte[] b = new byte[8];
        InputStream inputStream = null;
        String header,sub;
        List<String> list = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b,0,8);
            header = this.bytesToHex(b);
            for (int i = 0; i <=16; i+=2) {
                sub = header.substring(0,i);
                if ((list = Types.get(sub))!=null)
                    break;
            }

            if (list!=null){
                if (list.contains("zip"))
                    return new Offices().getNewType(file);
                if (list.contains("doc"))
                    return new Offices().getOldType(file);
                else
                    return list.get(0);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }



    private   String getFileHeader(File file){

        byte[] b = new byte[28]; //读取字节长度
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b,0,28);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytesToHex(b);
    }

    private  String bytesToHex(byte[] src){
        StringBuffer sb = new StringBuffer();
        if (src==null || src.length<0){
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] &0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() <2 ){
                sb.append(0);
            }
            sb.append(hv);
        }

        return sb.toString();
    }




    public static void main(String[] args) throws IOException {


        File file = new File("C:/Users/Xbear/Desktop/11");
        System.out.println(new FileTypes().reNameFileType(file));  //是否重命名文件
        if (file.getName().contains(".")){
            System.out.println(file.getName());
        }
//        System.out.println(file.getAbsolutePath());
//        file.renameTo(new File(file.getAbsolutePath()+".png"));
//        System.out.println(new FileTypes().reNameFileType(file));

//        System.out.println(new FileTypes().getFileTypes(file));
    }
}
