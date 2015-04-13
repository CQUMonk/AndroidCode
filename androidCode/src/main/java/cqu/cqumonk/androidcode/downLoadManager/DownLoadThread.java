package cqu.cqumonk.androidcode.downLoadManager;

import android.content.Context;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CQUMonk on 2015/4/12.
 * 文件下载线程
 */
public class DownLoadThread extends Thread {
    private ThreadInfo mThreadInfo=null;
    private FileInfo mFileInfo=null;
    private ThreadDAO threadDAO;
    public DownLoadThread(Context context,ThreadInfo mThreadInfo,FileInfo fileInfo) {
        this.mThreadInfo = mThreadInfo;
        this.threadDAO=new ThreadDAOImpl(context);
        this.mFileInfo=fileInfo;
    }

    @Override
    public void run() {
        //在数据库中找到该线程的信息，如果没有则插入

        if (!threadDAO.isExists(mThreadInfo.getThreadId(),mThreadInfo.getUrl())){
            threadDAO.InsertThread(mThreadInfo);
        }
        //确定文件下载的位置，如果已经下载过则从之前下载的位置开始

        URL url=null;
        HttpURLConnection conn=null;
        RandomAccessFile randomAccessFile=null;
        InputStream inputStream=null;
        try {
            url=new URL(mThreadInfo.getUrl());
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);

            //确定文件下载位置

            int startloc=mThreadInfo.getStart()+mThreadInfo.getFinished();
            conn.setRequestProperty("Range","bytes="+startloc+"-"+mThreadInfo.getEnd());

            //确定文件的写入位置
            File file=new File(DownLoadService.DOWNLOAD_PATH,mFileInfo.getFileName());
            randomAccessFile=new RandomAccessFile(file,"rwd");
            randomAccessFile.seek(startloc);
            //开始下载
            if (conn.getResponseCode()== HttpStatus.SC_OK){
                //读取数据
                inputStream=conn.getInputStream();
                byte[] buffer=new byte[1024*4];
                int len=-1;
                while((len=inputStream.read(buffer))!=-1){
                    randomAccessFile.write(buffer,0,len);
                    //将下载进度发送给service
                }
                //暂停时候要保存进度


            }

        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}
