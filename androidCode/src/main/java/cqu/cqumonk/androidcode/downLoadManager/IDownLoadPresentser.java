package cqu.cqumonk.androidcode.downLoadManager;

import android.content.BroadcastReceiver;

/**
 * Created by CQUMonk on 2015/4/11.
 */
public interface IDownLoadPresentser {
   public void startDownLoad();
    public void pauseDownLoad();
    public BroadcastReceiver getUpdateRecevier();
}
