package cqu.cqumonk.androidcode.downLoadManager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by CQUMonk on 2015/4/12.
 * 该类实现数据下载，启动多个线程进行网络数据的访问
 */
public class DownLoadTask {
    private Context mContext=null;
    private FileInfo fileInfo=null;
    private ThreadDAO mThreadDAO=null;
    private List<ThreadInfo> threads=null;
    private int mFinished=0;
    private boolean isPause=false;

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public DownLoadTask(Context mContext, FileInfo fileInfo) {
        this.mContext = mContext;
        this.fileInfo = fileInfo;
        mThreadDAO=new ThreadDAOImpl(mContext);


    }
    public void downLoad(){
        threads=mThreadDAO.queryThreads(fileInfo.getUrl());
        //如果是第一次下载该文件
        ThreadInfo threadInfo;
        if (threads.size()==0){

            threadInfo = new ThreadInfo(0,fileInfo.getUrl(),0,fileInfo.getLength(),0);
        }else {
            threadInfo=threads.get(0);
        }
        new DownLoadThread(threadInfo).start();
    }


    class DownLoadThread extends Thread {
        private ThreadInfo mThreadInfo=null;


        public DownLoadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;


        }

        @Override
        public void run() {
            //在数据库中找到该线程的信息，如果没有则插入

            if (!mThreadDAO.isExists(mThreadInfo.getThreadId(),mThreadInfo.getUrl())){
                mThreadDAO.InsertThread(mThreadInfo);
            }
            //确定文件下载的位置，如果已经下载过则从之前下载的位置开始

            URL url=null;
            HttpURLConnection conn=null;
            RandomAccessFile randomAccessFile=null;
            InputStream inputStream=null;
            Intent intent=new Intent();
            intent.setAction(DownLoadService.ACTION_UPDATE);
            try {
                url=new URL(mThreadInfo.getUrl());
                conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);

                //确定文件下载位置

                int startloc=mThreadInfo.getStart()+mThreadInfo.getFinished();
                conn.setRequestProperty("Range","bytes="+startloc+"-"+mThreadInfo.getEnd());

                //确定文件的写入位置
                File file=new File(DownLoadService.DOWNLOAD_PATH,fileInfo.getFileName());
                randomAccessFile=new RandomAccessFile(file,"rwd");
                randomAccessFile.seek(startloc);
                mFinished+=mThreadInfo.getFinished();
                long time=System.currentTimeMillis();
                //开始下载
                if (conn.getResponseCode()== HttpStatus.SC_PARTIAL_CONTENT){
                    //读取数据
                    inputStream=conn.getInputStream();
                    byte[] buffer=new byte[1024*4];
                    int len=-1;
                    while((len=inputStream.read(buffer))!=-1){
                        randomAccessFile.write(buffer,0,len);
                        //将下载进度发送给service
                        mFinished+=len;
                        if(System.currentTimeMillis()-time>50){
                            time=System.currentTimeMillis();
                            intent.putExtra("progress",mFinished*100/fileInfo.getLength()+1);
                            Log.e("send","send"+mFinished*100/fileInfo.getLength());
                            mContext.sendBroadcast(intent);

                        }

                        //暂停
                        if (isPause()){
                        //暂停时候要保存进度
                            mThreadDAO.updateThreadProgress(mThreadInfo.getThreadId(),mThreadInfo.getUrl(),mFinished);
                            return;
                        }
                    }



                }

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
                if (inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }






        }
    }
}
