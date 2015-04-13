package cqu.cqumonk.androidcode.downLoadManager;

import android.os.Handler;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CQUMonk on 2015/4/11.
 * 找到网络上的文件，确定长度，在本地sd卡上创建该文件
 */
public class InitThread extends Thread {
    private Handler handler;
    private FileInfo fileInfo;
    public  InitThread(Handler handler,FileInfo fileInfo){
        this.handler=handler;
        this.fileInfo=fileInfo;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection conn = null;

        RandomAccessFile randomAccessFile = null;
        try {

            //连接网络文件
            url = new URL(fileInfo.getUrl());
            conn= (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");

            int length=-1;
            //获取网络文件成功
            if (conn.getResponseCode()== HttpStatus.SC_OK){
            //获得文件长度
                length=conn.getContentLength();
            }
            Log.e("test","length="+length);
            if (length<=0){return;}

            //创建本地文件
            File dir=new File(DownLoadService.DOWNLOAD_PATH);
            if (!dir.exists()){
                dir.mkdir();
            }
            File file=new File(dir,fileInfo.getFileName());

            randomAccessFile=new RandomAccessFile(file,"rwd");

            Log.e("test",DownLoadService.DOWNLOAD_PATH);

            //设置文件长度
            randomAccessFile.setLength(length);

            //将初始化好的文件返回给service
            fileInfo.setLength(length);

            handler.obtainMessage(DownLoadService.MSG_INIT,fileInfo).sendToTarget();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn!=null){
                conn.disconnect();
            }
            if (randomAccessFile!=null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
