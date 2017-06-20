package com.fge.voice.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import static com.fge.voice.util.VoicesVersion.VOICES_CONFIG_NAME;


/**
 * 文件操作工具类
 * Created by hm on 2016/12/9.
 */

public class FileUtil {
    /***
     *  读取配置文件
     *  如下示例
     *  a=b
     *  version=1
     * @param filepath 路径
     * @return  map
     */
    public static Map<String, String> getFileConfig(String filepath){
        Map<String, String> map = new HashMap<>();
        if(!new File(filepath).exists()){return map;}
        InputStream in=null;
        BufferedReader bufferedReader = null;
        try {
            in = new FileInputStream(new File(filepath));
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                String strs[] = str.split("=");
                if (strs.length == 2) {
                    if (!TextUtils.isEmpty(strs[0]) && !TextUtils.isEmpty(strs[1])) {
                        map.put(strs[0], strs[1]);
                    }
                }
            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /***
     *  把字符串写入到文件里
     * @param filepath 文件路径
     * @param content 字符串内容
     * @return  成功 or  失败
     */
    public static boolean writeConfig(String filepath,String content){
        boolean isWrite=false;
        FileOutputStream out=null;
        try {
            out = new FileOutputStream (new File(filepath));
            out.write(content.getBytes("utf-8"));
            isWrite=true;
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isWrite;
    }

    /***
     * 解压缩文件
     * @param zipFilePath 压缩包文件路径
     * @param outPath  输出的路径
     * @return 成功 or 失败
     * @throws FileNotFoundException
     */
    public static boolean unZipFolder(String zipFilePath, String outPath)
            throws FileNotFoundException {

        // 创建文件对象
        File file = new File(zipFilePath);
        if (!file.exists()) {
            FileWriteLog.writeLog("压缩包 不存在 "+zipFilePath);
            throw new FileNotFoundException("zip file is not exists");
        }

        // deleteFile(new File(BBB));
        // 创建zip文件对象
        ZipFile zipFile = null;
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        try {
            zipFile = new ZipFile(file);
            // 创建本zip文件解压目录
            File unzipFile = new File(outPath);
            if (unzipFile.exists())
                unzipFile.delete();
            unzipFile.mkdirs();
            // 得到zip文件条目枚举对象
            Enumeration zipEnum = zipFile.entries();
            // 循环读取条目
            while (zipEnum.hasMoreElements()) {
                // 得到当前条目
                ZipEntry entry = (ZipEntry) zipEnum.nextElement();
                String entryName = new String(entry.getName().getBytes(
                        "ISO8859_1"));
                System.out.println(entryName);
                if (entry.isDirectory()) {
                    new File(unzipFile.getAbsolutePath() + "/" + entryName)
                            .mkdirs();
                } else { // 若当前条目为文件则解压到相应目录
                    input = zipFile.getInputStream(entry);
                    File mFile = new File(unzipFile.getAbsolutePath() + "/"
                            + entryName);
                    if (!mFile.getParentFile().exists()) {
                        mFile.getParentFile().mkdirs();
                    }
                    output = new FileOutputStream(mFile);
                    byte[] buffer = new byte[1024 * 8];
                    int readLen = 0;
                    while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
                        output.write(buffer, 0, readLen);
                    input.close();
                    output.flush();
                    output.close();
                    output = null;
                    input = null;
                }
            }
            return true;
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            file.delete();
        }
        return false;
    }

    /***
     * 根据条件删除文件或者目录
     * @param file  要删除的文件夹
     * @param filter 要过滤删除的文件夹or 文件
     */
    public static void deleteFile(File file, File filter) {
        if (file.exists()) {// 判断文件是否存在
            if (file.isFile()) {// 判断是否是文件
                if (!file.getName().endsWith(VOICES_CONFIG_NAME)
                        && !file.getAbsolutePath().startsWith(
                        filter.getAbsolutePath())) {
                    file.delete();// 删除文件
                }
            } else if (file.isDirectory()) {// 否则如果它是一个目录
                File[] files = file.listFiles();// 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
                    deleteFile(files[i], filter);// 把每个文件用这个方法进行迭代
                }
                //过滤
                if (!file.getAbsolutePath().startsWith(
                        filter.getAbsolutePath())) {
                    file.delete();// 删除文件夹
                }
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }
    public static void deleteFile(String file, String filter) {
        deleteFile(new File(file),new File(filter));
    }
}
