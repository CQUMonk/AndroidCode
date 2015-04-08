package cqu.cqumonk.androidcode.Ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BallActivity extends View {
	//小球当前位置，设置初始值
	private float mCurrentX=30;
	private float mCurrentY=30;
	//自定义小球颜色
	private int mColor=Color.GREEN;
	//定义并创建画笔
	private Paint mBallPaint;
	

	public BallActivity(Context context) {
		this(context,null);
	}

	public BallActivity(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	/**
	 * 当使用自定义属性时候调用
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public BallActivity(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//获取到自定属性
		//TypedArray typedArray=context.obtainStyledAttributes(attrs)
		
		mBallPaint=new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 当组件要绘制它的内容时，回调该方法进行绘制
		super.onDraw(canvas);
		if (mBallPaint!=null) {
			//设置画笔颜色为绿色
			mBallPaint.setColor(Color.GREEN);
			//画小球
			canvas.drawCircle(mCurrentX, mCurrentY, 15, mBallPaint);
		}

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//屏幕被触碰时被触发
		
		//获取到当前手指位置
		mCurrentX=event.getX();
		mCurrentY=event.getY();
		Toast.makeText(getContext(), mCurrentX+":"+mCurrentY, Toast.LENGTH_SHORT).show();
		
		//告诉组件重绘自己
		invalidate();
		return true;
	}

}
