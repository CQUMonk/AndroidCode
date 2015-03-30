package cqu.cqumonk.androidcode.view;

import cqu.cqumonk.androidcode.model.Dot;
import cqu.cqumonk.androidcode.model.Dot.Status;
import cqu.cqumonk.androidcode.model.DotMatrix;
import cqu.cqumonk.androidcode.utils.Utils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class PlayGround extends SurfaceView{
	private DotMatrix dotMatrix;
	private Dot mCat;
	private Dot mDot;
	Canvas mCanvas;
	Paint mPaint;
	int WIDTH=40;


	public PlayGround(Context context) {
		super(context);
		initGame();
		getHolder().addCallback(callback);
		initEvent();
		
		
	}
	private void initEvent() {
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					//Toast.makeText(getContext(), event.getX()+","+event.getY(), Toast.LENGTH_LONG).show();
					int x=(int) (event.getY()/WIDTH);
					int y=(int) ((event.getX()-(x%2==0?1:0)*WIDTH/2)/WIDTH);
					//Toast.makeText(getContext(), x+","+y, Toast.LENGTH_LONG).show();
					if(x+1>Utils.ROW||y+1>Utils.COL)
					{
						initGame();
				
					}else {
						//点击该点，变成路障
						Dot dot=dotMatrix.getDot(x, y);
						if(dot!=null&&dot.getStatus()!=Status.IN){
							dot.setStatus(Status.ON);
							//神经猫根据现在所在位置判断并逃跑一步
							escapeOneStep(mCat);
						}						
					}
					reDraw();
				}
				return true;
			}
		});
	}
	//初始化游戏
	private void initGame() {
		dotMatrix=new DotMatrix();
		mCat=dotMatrix.getDot(5, 4);
		mCat.setStatus(Status.IN);
	}

	private void moveTo(Dot dot){
		if(dot!=null){
			dot.setStatus(Status.IN);
			if(mCat!=null){
				mCat.setStatus(Status.OFF);
			}
			mCat=dot;
			reDraw();
		}
	}
	/**
	 * 从当前点找到最合理路径，逃出一步
	 * @param dot
	 */
	private void  escapeOneStep(Dot dot) {
		if(Utils.isEdge(dot)){
			lose();
		}else {
			int dir=dotMatrix.getMoveDirection(dot);
			//向该方向的那个邻居点移动
			moveTo(dotMatrix.getNeiborDot(dot, dir));
		}
	}
	//游戏失败
	private void lose() {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "you lose", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 重新绘制界面
	 */
	private void reDraw(){
		mCanvas=getHolder().lockCanvas();
		mCanvas.drawColor(Color.LTGRAY);
		
		mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		int offset=0;
		//画点
		for (int i = 0; i < Utils.ROW; i++) {
			if(i%2==0){
				offset=WIDTH/2;
			}else {
				offset=0;
			}
			
			for (int j = 0; j < Utils.COL; j++) {
				mDot = dotMatrix.getDot(i, j);
				switch (mDot.getStatus()) {
				case IN:
					mPaint.setColor(0xFFFF0000);

					break;
				case ON:
					mPaint.setColor(0xFFFFAA00);

					break;
				case OFF:
					mPaint.setColor(0xFFEEEEEE);

					break;

				}
				//画椭圆
				mCanvas.drawOval(new RectF(mDot.getY()*WIDTH+offset, mDot.getX()*WIDTH, 
						(mDot.getY()+1)*WIDTH+offset, (mDot.getX()+1)*WIDTH), mPaint);
			}

		}
		
		//取消对canvas的锁定，并把更新绘制到界面上去。
		getHolder().unlockCanvasAndPost(mCanvas);
		
	}

	Callback callback =new Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
		}
		//surface创建时候回调
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			
			reDraw();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			WIDTH=width/(Utils.COL+1);
			reDraw();
			
		}
	};

}
