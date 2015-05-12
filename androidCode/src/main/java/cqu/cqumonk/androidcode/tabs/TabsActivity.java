package cqu.cqumonk.androidcode.tabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import cqu.cqumonk.androidcode.R;


public class TabsActivity extends FragmentActivity {



    private FragmentTabHost mTabHost;

    //存放fragment页面
    private Class[] mFragments={FragmentA.class,FragmentB.class,FragmentC.class,FragmentD.class};

    //存放tab背景图片
    private int[] mBackgrounds={R.drawable.tab_home_btn,
            R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
            R.drawable.tab_more_btn};

    //选项卡文字
    private String[] mTabTexts={"ONE","TWO","THREE","FOUR"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        initViews();
    }

    private void initViews() {

        mTabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this,getSupportFragmentManager(),R.id.fl_tabs_realcontent);

        for (int i=0;i<mFragments.length;i++){
            //每个tab包括indicator，content，tag；tabSpec用来管理他们
            //为每一个tab设置内容，文字，图标
            TabHost.TabSpec tabSpec=mTabHost.newTabSpec(mTabTexts[i]).setIndicator(getTabItemView(i));

            mTabHost.addTab(tabSpec,mFragments[i], null);

            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }
    private View getTabItemView(int index){
        View view= LayoutInflater.from(this).inflate(R.layout.tab_item_view,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mBackgrounds[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTabTexts[index]);



        return view;
    }


}
