package cqu.cqumonk.androidcode.downLoadManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by CQUMonk on 2015/4/11.
 */
public class DownLoadPresenter implements IDownLoadPresentser {
    private Context mContext;
    private DownLoadView mDownloadView;
    private FileInfo fileInfo;
    private BroadcastReceiver updateRecevier=null;

    public DownLoadPresenter(Context mContext,DownLoadView downLoadView) {
        this.mContext = mContext;
        this.mDownloadView=downLoadView;
        String url="http://static.cnblogs.com/images/logo_small.gif";

        fileInfo=new FileInfo(0,"testFile",url,0,0);



        /**
         * 接收到service传来的更新UI的消息
         */
        updateRecevier=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (DownLoadService.ACTION_UPDATE.equals(intent.getAction())){

                    int progress=intent.getIntExtra("progress",0);
                    Log.e("send", "send" + progress);
                    mDownloadView.updateProgressBar(progress);
                }
            }
        };

    }

    @Override
    public void startDownLoad() {
        Intent service=new Intent(mContext,DownLoadService.class);
        service.setAction(DownLoadService.ACTION_START);
        service.putExtra("fileinfo",fileInfo);
        mContext.startService(service);
    }

    @Override
    public void pauseDownLoad() {
        Intent service=new Intent(mContext,DownLoadService.class);
        service.setAction(DownLoadService.ACTION_PAUSE);
        service.putExtra("fileinfo",fileInfo);
        mContext.startService(service);
    }

    public BroadcastReceiver getUpdateRecevier() {
        return updateRecevier;
    }

}
