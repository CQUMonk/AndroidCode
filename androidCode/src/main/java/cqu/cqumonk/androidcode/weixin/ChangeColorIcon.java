package cqu.cqumonk.androidcode.weixin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cqu.cqumonk.androidcode.R;

/**
 * 
 * @author Monk 
 * 自定义view 
 * 在atrr.xml中定义属性
 *  布局文件使用属性
 *   在构造方法中获取自定义属性
 *   onMearsure方法
 *   onDraw方法
 */
public class ChangeColorIcon extends View {
	// 自定义属性
	private int mColor = 0xFF45C01A;
	private Bitmap mIcon;
	private String mText = "Weixin";
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	//绘图所用变量
	//在内存中，通过bitmap得到Canvas，通过Canvas得到paint
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mIconPaint;
	//透明度
	private float mAlpha;
	
	private Rect mIconRect;
	private Rect mTextBound;
	
	private Paint mTextPaint;
	/**
	 * 未自定义属性时候调用该方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public ChangeColorIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChangeColorIcon(Context context) {
		this(context, null);
	}

	/**
	 * 自定义属性时候调用该方法
	 * 获取了自定义属性
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ChangeColorIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//获取到自定义属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeColorIcon);


		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.ChangeColorIcon_icon:
				BitmapDrawable bitDrawable=(BitmapDrawable) typedArray.getDrawable(attr);
				mIcon=bitDrawable.getBitmap();
				break;
			case R.styleable.ChangeColorIcon_color:
				mColor=typedArray.getColor(attr, 0xFF45C01A);
				break;
			case R.styleable.ChangeColorIcon_text:
				mText=typedArray.getString(attr);
				break;
			case R.styleable.ChangeColorIcon_text_size:
				mTextSize=(int)typedArray.getDimension(attr, 
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
								12, getResources().getDisplayMetrics()));
				break;
			}
		}
		typedArray.recycle();
		//初始化绘图变量,文字部分
		mTextBound=new Rect();
		mTextPaint=new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//确定Icon的绘制范围
		//ICon的边长应该取下面宽高的最小值
		//高：View的高度-TopPadding-BottomPading-mTextBound.height
		//宽：View的宽度-LeftPadding-RightPadding
		int IconWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
				getMeasuredHeight()-getPaddingBottom()-getPaddingTop()-mTextBound.height());
		
		//Icon左上角坐标
		int icon_x=getMeasuredWidth()/2-IconWidth/2;
		int icon_y=(getMeasuredHeight()-mTextBound.height())/2-IconWidth/2;
		
		mIconRect=new Rect(icon_x, icon_y, icon_x+IconWidth, icon_y+IconWidth);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//原图,绘制icon图片
		canvas.drawBitmap(mIcon, null, mIconRect,null);
		//在内存中准备mBitmap,设置透明度，绘制纯色，xfermode，图标
		int alpha=(int) Math.ceil(255*mAlpha);
		setupTargetBitmap(alpha);
		//绘制原文本
		drawOriginText(canvas,alpha);
		//绘制变色文本
		drawTargetText(canvas,alpha);
		
		canvas.drawBitmap(mBitmap, 0, 0, null);
		
	}

	/**
	 * 在内存中绘制可变色的Icon
	 */
	private void setupTargetBitmap(int alpha) {
		//创建一个View大小的图片
		mBitmap=Bitmap.createBitmap(getMeasuredWidth(), 
				getMeasuredHeight(), Config.ARGB_8888);
		mCanvas=new Canvas(mBitmap);
		//设置paint的一些属性
		mIconPaint=new Paint();
		mIconPaint.setColor(mColor);
		//paint加上抗锯齿标识
		mIconPaint.setAntiAlias(true);
		mIconPaint.setDither(true);
		
		mIconPaint.setAlpha(alpha);
		//绘制一个ICon大小的，mcolor的纯色区域
		mCanvas.drawRect(mIconRect, mIconPaint);
		//设置
		mIconPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mIconPaint.setAlpha(255);
		//绘制出Icon
		mCanvas.drawBitmap(mIcon, null, mIconRect, mIconPaint);
		
		
		
	}

	/**
	 * 绘制原文本
	 */
	private void drawOriginText(Canvas canvas,int alpha) {
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255-alpha);
		int text_x=getMeasuredWidth()/2-mTextBound.width()/2;
		int text_y=mIconRect.bottom+mTextBound.height();
		canvas.drawText(mText, text_x, text_y, mTextPaint);
		
		
	}
	/**
	 * 绘制变色文本
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int text_x=getMeasuredWidth()/2-mTextBound.width()/2;
		int text_y=mIconRect.bottom+mTextBound.height();
		canvas.drawText(mText, text_x, text_y, mTextPaint);
	}

	public void setIconAlpha(float alpha){
		this.mAlpha=alpha;
		invalidateView();
	}

	/**
	 * 重绘
	 */
	private void invalidateView() {
		//判断是否是UI线程
		if(Looper.getMainLooper()==Looper.myLooper()){
			invalidate();
		}else {
			postInvalidate();
		}
	}
	private static final String INSTANCE_STATUS="instance_status";
	private static final String STATUS_ALPHA="status_alpha";
	//触发onSaveInstanceState的情况一般有如下几种
	/**
	 * 当系统“未经你许可”时销毁了你的activity，则onSaveInstanceState会被系统调用
	 * 	1、当用户按下HOME键时。 
		2、长按HOME键，选择运行其他的程序时。 
		3、按下电源按键（关闭屏幕显示）时。 
		4、从activity A中启动一个新的activity时。 
		5、屏幕方向切换时，例如从竖屏切换到横屏时。
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle=new Bundle();
		//存入父view所要恢复的数据
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		//存入当前透明度
		bundle.putFloat(STATUS_ALPHA, mAlpha);
		return bundle;
	}
	/**
	 * onRestoreInstanceState被调用的前提是，activity “确实”被系统销毁了
	 * onRestoreInstanceState的bundle参数也会传递到onCreate方法中，也可以选择在onCreate方法中做数据还原。
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(state instanceof Bundle){
			Bundle bundle=(Bundle) state;
			//取出系统所保存数据，交给系统去处理
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			//取出透明度
			mAlpha=bundle.getFloat(STATUS_ALPHA);
			return;
		}
		super.onRestoreInstanceState(state);
		
	}

}
