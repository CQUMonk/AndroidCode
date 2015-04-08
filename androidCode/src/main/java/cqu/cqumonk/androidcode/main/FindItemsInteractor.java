package cqu.cqumonk.androidcode.main;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.model.MyItem;

/**
 * Created by cqumonk on 2015/4/6.
 */


public class FindItemsInteractor implements IFindItemsInteractor{




    private List<MyItem> getDas() {
        List<MyItem> mDas = new ArrayList<MyItem>();
        MyItem item1 = new MyItem(R.drawable.img_1, "QQ侧边栏", "QQ侧边栏菜单页面效果");
        mDas.add(item1);
        MyItem item2 = new MyItem(R.drawable.img_2, "QQ侧边栏", "QQ侧边栏菜单页面效果");
        mDas.add(item2);
        MyItem item3 = new MyItem(R.drawable.img_3, "weixin界面", "weixin界面效果");
        mDas.add(item3);
        MyItem item4 = new MyItem(R.drawable.img_4, "跟随手指小球", "演示自定义控件，跟随手指小球");
        mDas.add(item4);
        MyItem item5 = new MyItem(R.drawable.img_5, "计算质数", "演示新线程计算质数");
        mDas.add(item5);
        MyItem item6 = new MyItem(R.drawable.img_1, "神经猫", "模拟神经猫计算逻辑");
        mDas.add(item6);
        MyItem item7 = new MyItem(R.drawable.img_2, "扫一扫", "二维码扫描");
        mDas.add(item7);
        MyItem item8 = new MyItem(R.drawable.img_3, "2048", "2048游戏");
        mDas.add(item8);

        return mDas;
    }
    @Override
    public void findItems(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listener.onDataPrepared(getDas());
            }
        },2000);
    }
}
