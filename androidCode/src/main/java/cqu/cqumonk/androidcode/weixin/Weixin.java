package cqu.cqumonk.androidcode.weixin;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cqu.cqumonk.androidcode.R;

public class Weixin extends FragmentActivity implements OnClickListener, OnPageChangeListener {
	private ViewPager mViewPager;
	private List<Fragment> mTabs=new ArrayList<Fragment>();
	private String[] mTitles={
			"First Tab",
			"Second Tab",
			"Third Tab",
			"Fourth Tab"
	};
	private List<ChangeColorIcon> TabIndicators=new ArrayList<ChangeColorIcon>();
	private FragmentPagerAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weixin);
		setOverflowButton();
		//将actionBar的图标隐藏
		getActionBar().setDisplayShowHomeEnabled(false);
		
		initViews();
		initDataSource();
		initEvent();
		mViewPager.setAdapter(mAdapter);
	}
	/**
	 * 初始化事件
	 */
	private void initEvent() {
		mViewPager.setOnPageChangeListener(this);
	}
	/**
	 * 初始化数据源
	 */
	private void initDataSource() {
		for(String title:mTitles){
			TabFragment tab=new TabFragment();
			Bundle bundle=new Bundle();
			bundle.putString(TabFragment.TITTLE, title);
			tab.setArguments(bundle);
			
			mTabs.add(tab);
		}
		mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return mTabs.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};
	}
	/**
	 * 初始化控件
	 */
	private void initViews() {
		mViewPager=(ViewPager) findViewById(R.id.id_viewpager);
		
		ChangeColorIcon indicator_one=(ChangeColorIcon) findViewById(R.id.id_indicator_one);
		ChangeColorIcon indicator_two=(ChangeColorIcon) findViewById(R.id.id_indicator_two);
		ChangeColorIcon indicator_three=(ChangeColorIcon) findViewById(R.id.id_indicator_three);
		ChangeColorIcon indicator_four=(ChangeColorIcon) findViewById(R.id.id_indicator_four);
		
		TabIndicators.add(indicator_one);
		TabIndicators.add(indicator_two);
		TabIndicators.add(indicator_three);
		TabIndicators.add(indicator_four);
		
		indicator_one.setOnClickListener(this);
		indicator_two.setOnClickListener(this);
		indicator_three.setOnClickListener(this);
		indicator_four.setOnClickListener(this);
		
		indicator_one.setIconAlpha(1.0f);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weixin_menu, menu);
		return true;
	}

	/**
	 * 利用反射设置OverflowButton的style
	 */
	private void setOverflowButton(){
		//获得view的配置信息
		ViewConfiguration config=ViewConfiguration.get(this);
		try {
			Field menuKey=ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		//overflow中菜单项的图标显示
		if(featureId==Window.FEATURE_ACTION_BAR&&menu!=null){
			if(menu.getClass().getSimpleName().equals("MenuBuilder")){
				try {
					Method iconVisible=menu.getClass().
							getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					iconVisible.setAccessible(true);
					
					iconVisible.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
		}
		return super.onMenuOpened(featureId, menu);
	}
	@Override
	public void onClick(View v) {
		clickTabs(v);
		
	}
	/**
	 * 点击tab响应事件
	 * 
	 * @param v
	 */
	private void clickTabs(View v) {
		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			TabIndicators.get(0).setIconAlpha(1.0f);
			//切换到当前fragment，无动画效果
			mViewPager.setCurrentItem(0,false);
			
			break;
		case R.id.id_indicator_two:
			TabIndicators.get(1).setIconAlpha(1.0f);
			//切换到当前fragment，无动画效果
			mViewPager.setCurrentItem(1,false);
			
			break;
		case R.id.id_indicator_three:
			TabIndicators.get(2).setIconAlpha(1.0f);
			//切换到当前fragment，无动画效果
			mViewPager.setCurrentItem(2,false);
			
			break;
		case R.id.id_indicator_four:
			TabIndicators.get(3).setIconAlpha(1.0f);
			//切换到当前fragment，无动画效果
			mViewPager.setCurrentItem(3,false);
			
			break;
		
		}
	}
	/**
	 * 重置其他的tab的indicator的颜色
	 */
	private void resetOtherTabs() {
		for(ChangeColorIcon indicator:TabIndicators){
			indicator.setIconAlpha(0);
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @param position 当前View索引，左边为pos，右边为pos+1
	 * @param positionOffset从左向右滑动，该值从0到1变化
	 * @param positionOffsetPixels
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if(positionOffset>0){
			ChangeColorIcon left_indicator=TabIndicators.get(position);
			
			ChangeColorIcon right_indicator=TabIndicators.get(position+1);
			left_indicator.setIconAlpha(1-positionOffset);
			right_indicator.setIconAlpha(positionOffset);
		}
		
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
