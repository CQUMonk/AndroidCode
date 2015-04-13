package cqu.cqumonk.androidcode.downLoadManager;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import cqu.cqumonk.androidcode.R;

public class DownLoadActivity extends Activity implements DownLoadView {
    private TextView mTv_filename;
    private ProgressBar mPb_progress;
    private Button mBtn_start;
    private Button mBtn_pause;

    private IDownLoadPresentser downLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initViews();
        initEvent();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(downLoadPresenter.getUpdateRecevier());
        super.onDestroy();
    }

    private void initViews() {
        mTv_filename= (TextView) findViewById(R.id.tv_download_filename);
        mPb_progress= (ProgressBar) findViewById(R.id.pb_download_progress);
        mBtn_start= (Button) findViewById(R.id.btn_download_start);
        mBtn_pause=(Button) findViewById(R.id.btn_download_pause);

        mPb_progress.setMax(100);

        downLoadPresenter=new DownLoadPresenter(this,this);


        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(DownLoadService.ACTION_UPDATE);
        registerReceiver(downLoadPresenter.getUpdateRecevier(),intentFilter);

    }

    private void initEvent() {

        mBtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downLoadPresenter.startDownLoad();
            }
        });
        mBtn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadPresenter.pauseDownLoad();
            }
        });

    }


    @Override
    public void updateProgressBar(int progress) {
        mPb_progress.setProgress(progress);
    }
}
