package com.mm.util.gen.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static void compress(File f, String baseDir, ZipOutputStream zos) {
        if (!f.exists()) {
            System.out.println("待压缩的文件目录或文件" + f.getName() + "不存在");
            return;
        }


        File[] fs = f.listFiles();
        BufferedInputStream bis = null;
        //ZipOutputStream zos = null;
        byte[] bufs = new byte[1024 * 10];
        FileInputStream fis = null;


        try {
            //zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < fs.length; i++) {
                String fName = fs[i].getName();
                System.out.println("压缩：" + baseDir + fName);
                if (fs[i].isFile()) {
                    ZipEntry zipEntry = new ZipEntry(baseDir + fName);//
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(fs[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    //如果需要删除源文件，则需要执行下面2句
                    fis.close();
                    fs[i].delete();
                } else if (fs[i].isDirectory()) {
                    compress(fs[i], baseDir + fName + "/", zos);
                }
            }//end for
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bis)
                    bis.close();
                //if(null!=zos)
                //zos.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static boolean deleteDir(String dir) {
        File file = new File(dir);
        boolean delete;
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children.length > 0) {
                /**递归删除目录中的子目录下*/
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(file.getPath() + "/" + children[i]);
                    if (!success) {
                        return false;
                    }
                }
            }
            delete = file.delete();
        } else {
            delete = file.delete();
        }
        return delete;
    }
}
