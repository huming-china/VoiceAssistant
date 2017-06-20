package com.fge.voice.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日志打印和写入文件工具类
 * Created by hm on 2016/9/8.
 */
public class FileWriteLog {
    public static boolean SHOW_LOG;
    public static boolean WRITE_LOG;
    private static  ExecutorService singleThreadPool;

    public static synchronized void writeLog(String tag,String content) {
        if(SHOW_LOG){
            Log.d(tag,content);
        }
        if(!WRITE_LOG){
            return;
        }
        wirte(content);
    }
    /**
     * B方法追加文件：使用FileWriter
     */
    private static void wirte(final String content){
        if(singleThreadPool==null) {
            singleThreadPool = Executors.newSingleThreadExecutor();
        }
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FileWriter writer = null;
                try {
                    File fileName= new File(Environment.getExternalStorageDirectory(),"voiceslog.txt");
                    if(System.currentTimeMillis()-fileName.lastModified()>3600000||fileName.length()>1024000){
                        fileName.delete();//定期或者定量 删除
                    }
                    //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                    writer = new FileWriter(fileName, true);
                    writer.write(content);

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(writer!=null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }

   final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void writeLog(String content) {
        String date = sdf.format(new Date(System.currentTimeMillis()));
        writeLog("SHOW_LOG","("+date+") "+content+"\n");
    }
}
