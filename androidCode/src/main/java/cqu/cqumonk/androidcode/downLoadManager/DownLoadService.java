package cqu.cqumonk.androidcode.downLoadManager;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by CQUMonk on 2015/4/11.
 */
public class DownLoadService extends Service {

    public final static String DOWNLOAD_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Downloads/";
    public final  static String ACTION_START="ACTION_START";
    public final  static String ACTION_PAUSE="ACTION_PAUSE";
    public final  static int MSG_INIT=0;
    public final  static String ACTION_UPDATE="ACTION_UPDATE";


    private Myhandler handler;

    private static DownLoadTask task=null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.handler=new Myhandler(this);
    }

    @Override
    public void onDestroy() {
        //清除以该Handler为target的所有Message
        this.handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo;
        if (ACTION_START.equals(intent.getAction())){
            fileInfo = intent.getParcelableExtra("fileinfo");

            //初始化SD卡上的文件
            new InitThread(this.handler,fileInfo).start();

        }else if (ACTION_PAUSE.equals(intent.getAction())){
            fileInfo=intent.getParcelableExtra("fileinfo");
            if(task!=null){
                task.setPause(true);
            }

        }




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 为了防止handler leak 自定义的Myhandler内部类
     */
    static class Myhandler extends Handler {
        WeakReference<DownLoadService> outerClass;

        Myhandler(DownLoadService outer) {
            outerClass=new WeakReference<DownLoadService>(outer);
        }

        @Override
        public void handleMessage(Message msg) {

            DownLoadService outer=outerClass.get();

            if (outer!=null){
                switch (msg.what){
                    case MSG_INIT:
                        FileInfo fileInfo= (FileInfo) msg.obj;
                        Log.e("test",fileInfo.toString());
                        //启动下载任务

                        task = new DownLoadTask(outer,fileInfo);

                        task.downLoad();
                        break;
                }
            }
        }
    }
}
/**
 * handler的泄露原因
 * （1）排队中的Message对象对Handler的持有导致泄漏；
 * （2）Handler对象对外部类（如Activity或Service）实例的强引用持有。
 * 是由于这两个原因同时作用导致出现泄漏的可能。
 * 解决：
 * （1）在使用Handler的组件生命周期结束前清除掉MessageQueue中的发送给Handler的Message对象；
 * （2）Handler的实现类采用静态内部类的方式，避免对外部类的强引用，在其内部声明一个WeakReference引用到外部类的实例。
 *
 */
