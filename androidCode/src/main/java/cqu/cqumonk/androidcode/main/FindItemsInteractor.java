package cqu.cqumonk.androidcode.main;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.model.MyItem;

/**
 * Created by cqumonk on 2015/4/6.
 */


public class FindItemsInteractor implements IFindItemsInteractor{
    private List<MyItem> DataSource=null;

    private  OnFinishedListener mListener;


    public FindItemsInteractor(final OnFinishedListener listener){

        mListener=listener;
        getDas();
    }

    public List<MyItem> getDataSource() {
        return DataSource;
    }

    private void  getDas() {
        this.DataSource = new ArrayList<MyItem>();
        MyItem item1 = new MyItem(R.drawable.img_1, "QQ侧边栏", "QQ侧边栏菜单页面效果");
        this.DataSource.add(item1);
        MyItem item2 = new MyItem(R.drawable.img_2, "断点续传组件", "断点续传多线程下载任务");
        this.DataSource.add(item2);
        MyItem item3 = new MyItem(R.drawable.img_3, "微信界面", "weixin界面效果");
        this.DataSource.add(item3);
        MyItem item4 = new MyItem(R.drawable.img_4, "跟随手指小球", "演示自定义控件，跟随手指小球");
        this.DataSource.add(item4);
        MyItem item5 = new MyItem(R.drawable.img_5, "计算质数", "演示新线程计算质数");
        this.DataSource.add(item5);
        MyItem item6 = new MyItem(R.drawable.img_1, "神经猫", "模拟神经猫计算逻辑");
        this.DataSource.add(item6);
        MyItem item7 = new MyItem(R.drawable.img_2, "扫一扫", "二维码扫描");
        this.DataSource.add(item7);
        MyItem item8 = new MyItem(R.drawable.img_3, "2048", "2048游戏");
        this.DataSource.add(item8);

    }
    @Override
    public void requestItems() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mListener.onDataPrepared(getDataSource());
            }
        },2000);
    }

    @Override
    public void removeItem(int pos) {

        this.DataSource.remove(pos);
        mListener.onDataPrepared(getDataSource());

    }
}
