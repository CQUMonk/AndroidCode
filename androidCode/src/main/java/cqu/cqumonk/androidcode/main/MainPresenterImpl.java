package cqu.cqumonk.androidcode.main;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import cqu.cqumonk.androidcode.BarCode.BarCodeActivity;
import cqu.cqumonk.androidcode.CreazyCat.CreazyCatActivity;
import cqu.cqumonk.androidcode.Game2048.Game2048Activity;
import cqu.cqumonk.androidcode.QQSliding.QQslideActivity;
import cqu.cqumonk.androidcode.downLoadManager.DownLoadActivity;
import cqu.cqumonk.androidcode.model.MyItem;
import cqu.cqumonk.androidcode.prime.PrimeActivity;
import cqu.cqumonk.androidcode.Ball.FollowFingerBall;
import cqu.cqumonk.androidcode.tabs.TabsActivity;
import cqu.cqumonk.androidcode.weixin.Weixin;

/**
 * Created by CQUMonk on 2015/4/6.
 */
public class MainPresenterImpl implements IMainPresenter,OnFinishedListener{
    private IMainView mMainView;
    private Context mContext;
    private MyAdapter mAdapter;

    private IFindItemsInteractor findItemsInteractor;

    public MainPresenterImpl(IMainView view,Context context) {
        this.mMainView=view;
        this.mContext=context;
        mAdapter=new MyAdapter(context);
        this.findItemsInteractor=new FindItemsInteractor(this);
    }

    @Override
    public void onResume() {
        this.mMainView.showProgress();
        //请求数据
        this.findItemsInteractor.requestItems();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent;
        switch (position) {
            case 0:
            //QQ侧边栏菜单页面跳转
                intent=new Intent(mContext,QQslideActivity.class);

                this.mContext.startActivity(intent);
                break;

            case 1:
                //断点续传任务
                intent=new Intent(mContext,DownLoadActivity.class);
                this.mContext.startActivity(intent);
                break;
            case 2:
                //weixin界面
                intent= new Intent(mContext,Weixin.class);
                this.mContext.startActivity(intent);
                break;
            case 3:
                //演示自定义控件，跟随手指小球
                intent =new Intent(mContext,FollowFingerBall.class);
                this.mContext.startActivity(intent);
                break;
            case 4:
                //新线程计算质数
                intent=new Intent(mContext,PrimeActivity.class);
                this.mContext.startActivity(intent);
                break;
            case 5:
                //神经猫
                intent=new Intent(mContext,CreazyCatActivity.class);
                this.mContext.startActivity(intent);
                break;
            case 6:
                intent =new  Intent(mContext,BarCodeActivity.class);
                this.mContext.startActivity(intent);
                break;
            case 7:
                intent =new  Intent(mContext,Game2048Activity.class);
                this.mContext.startActivity(intent);
                break;
            case 8:
                intent =new  Intent(mContext,TabsActivity.class);
                this.mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemDelete(int position) {

        findItemsInteractor.removeItem(position);

    }

    /**
     * 数据请求完毕时被回调
     * @param items
     */
    @Override
    public void onDataPrepared(List<MyItem> items) {
        //adapter获得到返回的数据
        mAdapter.setMyItems(items);
        //listView刷新
        mMainView.setListViewAdapter(mAdapter);
        mMainView.hideProgress();
    }

}
