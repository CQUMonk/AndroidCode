package cqu.cqumonk.androidcode.view;

import java.util.Iterator;

import com.nineoldandroids.view.ViewHelper;

import cqu.cqumonk.androidcode.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 自定义ViewGroup，需要复写的方法 ：
 * 1、onMeasure 控制内部View(子View)的宽高，以及自己的宽高 
 * 2、onLayout控制内部View(子View)的摆放位置
 * 3、onTouchEvent 
 * 判断用户手指滑动状态，控制当手指按下(ActionDown)，抬起(ActionUp)，移动(ActionMove)的移动状态，
 * 如果没有则可以不复写
 * 自定义属性步骤：
 * 1、在values/attr.xml文件中定义属性
 * 2、在布局文件中使用属性
 * 3、在构造方法中获取定义的属性值
 * @author CQUMonk
 */

public class SlidingMenu extends HorizontalScrollView {

	// 横向滚动条中的LinearLayout
	private LinearLayout mWapper;
	// 菜单栏及其宽度
	private ViewGroup mLeftMenu;
	private int mLeftMenuWidth;
	private ViewGroup mRightMenu;
	private int mRightMenuWith;
	// 中央内容区域
	private ViewGroup mContent;
	// 屏幕宽度
	private int mScreenWidth;
	// 左侧菜单栏与屏幕右侧边框的距离，默认50dp
	private int mMenuPadding ;
	
	// onMeasure方法是否调用
	private boolean mOnceMearsure = false;
	//菜单状态
	private boolean mLeftIsOpen=false;
	private boolean mRightIsOpen=false;
	

	/**
	 * 未使用自定义属性时，调用该构造方法。
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {

		this(context, attrs, 0);

	}

	/**
	 * 使用自定义属性时，调用该构造函数
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 获取自定义属性
		// R.styleable.SlidingMenu是在values/attr.xml中定义好的
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.SlidingMenu, defStyle, 0);
		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_menuRightPadding:
				//找到自定义的属性菜单栏与屏幕右侧边框的距离menuRightPadding，默认50dp，转化为像素
				// ，把dp转化为像素值px
				mMenuPadding = typedArray.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50, context
										.getResources().getDisplayMetrics()));
				break;

			}
		}
		// 释放资源
		typedArray.recycle();
		// 获取屏幕宽度
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		

	}

	public SlidingMenu(Context context) {
		this(context, null);
	}

	/**
	 * 控制内部View(子View)的宽高，以及自己的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!mOnceMearsure) {
			// 获取到里面的LinearLayout,scroll里面只能放一个View，故索引为0
			mWapper = (LinearLayout) getChildAt(0);
			// 获取到菜单栏
			mLeftMenu = (ViewGroup) mWapper.getChildAt(0);
			mRightMenu=(ViewGroup) mWapper.getChildAt(2);
			// 获取到内容区域
			mContent = (ViewGroup) mWapper.getChildAt(1);

			// 设置左侧菜单栏的宽度=屏幕的宽度-菜单栏与屏幕右侧边框的距离
			mLeftMenu.getLayoutParams().width = mScreenWidth - mMenuPadding;
			mLeftMenuWidth = mLeftMenu.getLayoutParams().width;
			//设置右侧菜单宽度=屏幕-菜单栏与屏幕左侧的距离
			mRightMenu.getLayoutParams().width=mScreenWidth-mMenuPadding;
			mRightMenuWith=mRightMenu.getLayoutParams().width;
			// 内容区域的宽度为屏幕的宽度
			mContent.getLayoutParams().width = mScreenWidth;
			// 整个wapper的宽度应该为内容区的宽度+两侧菜单栏的宽度，由于直接放在scroll内部，所以可以不设置

			// 只调用一次
			mOnceMearsure = true;
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	/**
	 * 控制内部View(子View)的摆放位置 通过设置偏移量，将menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 如果直接把menu和content放入屏幕中，则首先显示的是左侧menu，然后显示content区域和右侧menu。
		// 这里希望在屏幕中首先显示整个content
		super.onLayout(changed, l, t, r, b);
		// 如果当前布局不发生变化，则不调用
		if (changed) {
			// 滚动条向右，content向左
			this.scrollTo(mLeftMenuWidth, 0);
		}

	}

	/**
	 * 判断用户手指滑动状态
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 获得action
		int action = ev.getAction();
		// 判断用户动作
		// 因为horizontalScroll会自动判断按下和移动的动作，所以这里只需处理抬起的动作
		switch (action) {
		case MotionEvent.ACTION_UP:
			// 得到scrollX(像素)，即屏幕左侧未显示的滚动条宽度
			int scrollX = getScrollX();
			// 当scrollX <1/2菜单栏宽度时，显示菜单栏；否则隐藏菜单栏
			if (scrollX < mLeftMenuWidth / 2) {
				//
				this.smoothScrollTo(0, 0);
				mLeftIsOpen=true;
				mRightIsOpen=false;
			} else if (mLeftMenuWidth/2<scrollX&&scrollX<mLeftMenuWidth+mRightMenuWith/2){
				// 动画效果的隐藏
				this.smoothScrollTo(mLeftMenuWidth, 0);
				mLeftIsOpen=false;
				mRightIsOpen=false;

			}else {
				this.smoothScrollTo(mLeftMenuWidth+mRightMenuWith, 0);
				mLeftIsOpen=false;
				mRightIsOpen=true;
				
			}
			return true;

		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 实现抽屉式菜单滑动
	 * 当滚动条滚动时，触发该事件
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// 这里的l是scrollX，即屏幕左侧未显示的宽度，初始为mMenuWidth，手指向右滑动X像素，则为mMenuWidth-X
		super.onScrollChanged(l, t, oldl, oldt);
		
		
		//左侧菜单
		if(l<mLeftMenuWidth){
			float leftScale=1.0f*l/mLeftMenuWidth;
			/**
			 * 内容区域的缩放1.0-0.7
			 * scale：1.0-0.0
			 * 内容区域：0.7+0.3*scale
			 */
			float contentScale=0.7f+0.3f*leftScale;
			/**
			 * 菜单区域缩放0.7-1.0
			 * 菜单透明度0.6-1.0
			 * scale：1.0-0.0
			 * leftMenuScale=1.0f-0.3f*scale
			 * leftMenuAlpha=1.0f-0.4f*scale
			 */
			float leftMenuScale=1.0f-0.3f*leftScale;
			float leftMenuAlpha=1.0f-0.4f*leftScale;
			//内容区缩放的动画
			//设置内容区域缩放中心点为左边
			ViewHelper.setPivotX(mContent, 0);
			ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
			
			ViewHelper.setScaleX(mContent, contentScale);
			ViewHelper.setScaleY(mContent, contentScale);
			//设置左侧菜单动画，menu左侧向右偏移的距离
			//抽屉式的滑动效果
			ViewHelper.setTranslationX(mLeftMenu, leftScale*mLeftMenuWidth*0.7f);
			//菜单缩放
			ViewHelper.setScaleX(mLeftMenu, leftMenuScale);
			ViewHelper.setScaleY(mLeftMenu, leftMenuScale);
			//菜单透明度
			ViewHelper.setAlpha(mLeftMenu, leftMenuAlpha);
			
		}else {
			
			//此时，l的范围是mLeftMenuWidth~mLeftMenuWidth+mRightMenuWith
			float rightScale=1.0f*(l-mLeftMenuWidth)/mRightMenuWith;
			/**
			 * 内容区域的缩放1.0-0.7
			 * rightScale：0.0~1.0
			 * 内容区域：0.7+0.3*scale
			 */
			float contentScale=1.0f-0.3f*rightScale;
			/**
			 * 右侧菜单区域缩放0.7-1.0
			 * 菜单透明度0.6-1.0
			 * rightScale：0.0~1.0
			 * rightMenuScale=0.7f+0.3f*rightScale
			 * menuAlpha=1.0f-0.4f*scale
			 */
			float rightMenuScale=0.7f+0.3f*rightScale;
			float rightmenuAlpha=rightScale;
			//内容区缩放的动画
			//设置内容区域缩放中心点为右边
			ViewHelper.setPivotX(mContent, mContent.getWidth());
			ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
			
			ViewHelper.setScaleX(mContent, contentScale);
			ViewHelper.setScaleY(mContent, contentScale);
			//设置右侧菜单动画
			//抽屉式的滑动效果,这里
			ViewHelper.setTranslationX(mRightMenu, (l-mLeftMenuWidth-mRightMenuWith)*0.5f);
			//菜单缩放
			ViewHelper.setScaleX(mRightMenu, rightMenuScale);
			ViewHelper.setScaleY(mRightMenu, rightMenuScale);
			//菜单透明度
			ViewHelper.setAlpha(mRightMenu, rightmenuAlpha);
			
			
			
		}

		
	}
	/**
	 *打开左侧菜单
	 */
	private void openLeftMenu(){

		this.smoothScrollTo(0, 0);
		mLeftIsOpen=true;
	}
	/**
	 * 隐藏左侧菜单
	 */
	private void closeLeftMenu(){
		this.smoothScrollTo(mLeftMenuWidth, 0);
		mLeftIsOpen=false;
	}
	/**
	 * 切换左侧菜单
	 */
	public void toggleLeft() {
		if(mLeftIsOpen){
			closeLeftMenu();
		}else {
			openLeftMenu();
		}
	}
	/**
	 *打开右侧菜单
	 */
	private void openRightMenu(){

		this.smoothScrollTo(mLeftMenuWidth+mRightMenuWith, 0);
		mRightIsOpen=true;
	}
	/**
	 * 隐藏右侧菜单
	 */
	private void closeRightMenu(){
		this.smoothScrollTo(mLeftMenuWidth, 0);
		mRightIsOpen=false;
	}
	/**
	 * 切换右侧菜单
	 */
	public void toggleRight() {
		if(mRightIsOpen){
			closeRightMenu();
		}else {
			openRightMenu();
		}
	}

}
