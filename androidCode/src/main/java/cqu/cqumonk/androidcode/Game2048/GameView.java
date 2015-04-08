package cqu.cqumonk.androidcode.Game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends LinearLayout{
	private Card[][] cardMatrix = new Card[Config.LINES][Config.LINES];
	//用来管理所有值小于0的card
	private List<Point> emptyPoints=new ArrayList<Point>();



	public GameView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public GameView(Context context) {
		this(context,null);
	}
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initGameView();
		initEvent();
	}
	private void initEvent() {
		this.setOnTouchListener(new OnTouchListener() {
			float startX,startY;
			float offsetX,offsetY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX=event.getX();
					startY=event.getY();
					break;

				case MotionEvent.ACTION_UP:
					offsetX=event.getX()-startX;
					offsetY=event.getY()-startY;
					
					if(Math.abs(offsetX)>Math.abs(offsetY)){
						if (offsetX>10) {
							swipToRight();
						}else if (offsetX<-10) {
							swipToLeft();
						}
					}else {
						if (offsetY>10) {
							swipToDown();
						}else if (offsetY<-10) {
							swipToUp();
						}
					}
					break;
				}
				
				
				return true;
			}

		});
		
	}
	private void swipToRight() {
		boolean merge = false;
		
		//处理向右滑动，逐行遍历card
		for(int y=0;y<Config.LINES;y++){
			for(int x=Config.LINES-1;x>=0;x--){
				//遍历该卡片后面的卡片
				for (int next = x-1; next>=0; next--) {
					//如果下一个卡片不为空
					if (cardMatrix[next][y].getNum()>0) {
						//此处card如果为0,则下一个卡片左移到此card位置
						if (cardMatrix[x][y].getNum()<=0) {
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[next][y],cardMatrix[x][y],
									next, x, y, y);
							cardMatrix[x][y].setNum(cardMatrix[next][y].getNum());
							cardMatrix[next][y].setNum(0);
							//重新判断一次
							x++;
							merge = true;
							
						}else if(cardMatrix[x][y].isSame(cardMatrix[next][y])){
							//如果此处card的数字和下一个card数字相等，则合并
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[next][y],cardMatrix[x][y],
									next, x, y, y);
							cardMatrix[x][y].setNum(cardMatrix[x][y].getNum()*2);
							cardMatrix[next][y].setNum(0);
							
							Game2048Activity.getGameActivity().addScore(cardMatrix[x][y].getNum());
							//重新判断一次
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}		
		
	}
	private void swipToLeft() {
		boolean merge = false;
		
		//处理向左滑动，逐行遍历card
		for(int y=0;y<Config.LINES;y++){
			for(int x=0;x<Config.LINES;x++){
				//遍历该卡片后面的卡片
				for (int next = x+1; next < Config.LINES; next++) {
					//如果下一个卡片不为空
					if (cardMatrix[next][y].getNum()>0) {
						//此处card如果为0,则下一个卡片左移到此card位置
						if (cardMatrix[x][y].getNum()<=0) {
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[next][y],cardMatrix[x][y],
									next, x, y, y);
							cardMatrix[x][y].setNum(cardMatrix[next][y].getNum());
							cardMatrix[next][y].setNum(0);
							//重新判断一次
							x--;
							merge = true;
							
						}else if(cardMatrix[x][y].isSame(cardMatrix[next][y])){
							//如果此处card的数字和下一个card数字相等，则合并
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[next][y],cardMatrix[x][y],
									next, x, y, y);
							cardMatrix[x][y].setNum(cardMatrix[x][y].getNum()*2);
							cardMatrix[next][y].setNum(0);
							
							Game2048Activity.getGameActivity().addScore(cardMatrix[x][y].getNum());
							//重新判断一次
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	
	private void swipToDown() {
		boolean merge = false;
		
		//处理向右滑动，逐行遍历card
		for(int x=0;x<Config.LINES;x++){
			for(int y=Config.LINES-1;y>=0;y--){
				//遍历该卡片后面的卡片
				for (int next = y-1; next>=0; next--) {
					//如果下一个卡片不为空
					if (cardMatrix[x][next].getNum()>0) {
						//此处card如果为0,则下一个卡片左移到此card位置
						if (cardMatrix[x][y].getNum()<=0) {
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[x][next],cardMatrix[x][y],
									x, x, next, y);
							cardMatrix[x][y].setNum(cardMatrix[x][next].getNum());
							cardMatrix[x][next].setNum(0);
							//重新判断一次
							y++;
							merge = true;
							
						}else if(cardMatrix[x][y].isSame(cardMatrix[x][next])){
							//如果此处card的数字和下一个card数字相等，则合并
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[x][next],cardMatrix[x][y],
									x, x, next, y);
							cardMatrix[x][y].setNum(cardMatrix[x][y].getNum()*2);
							cardMatrix[x][next].setNum(0);
							
							Game2048Activity.getGameActivity().addScore(cardMatrix[x][y].getNum());
							//重新判断一次
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	private void swipToUp() {
		boolean merge = false;
		
		//处理向左滑动，逐行遍历card
		for(int x=0;x<Config.LINES;x++){
			for(int y=0;y<Config.LINES;y++){
				//遍历该卡片后面的卡片
				for (int next = y+1; next < Config.LINES; next++) {
					//如果下一个卡片不为空
					if (cardMatrix[x][next].getNum()>0) {
						//此处card如果为0,则下一个卡片左移到此card位置
						if (cardMatrix[x][y].getNum()<=0) {
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[x][next],cardMatrix[x][y],
									x, x, next, y);
							cardMatrix[x][y].setNum(cardMatrix[x][next].getNum());
							cardMatrix[x][next].setNum(0);
							//重新判断一次
							y--;
							merge = true;
							
						}else if(cardMatrix[x][y].isSame(cardMatrix[x][next])){
							//如果此处card的数字和下一个card数字相等，则合并
							Game2048Activity.getGameActivity().getAnimLayer()
							.createMoveAnim(cardMatrix[x][next],cardMatrix[x][y],
									x, x, next, y);
							cardMatrix[x][y].setNum(cardMatrix[x][y].getNum()*2);
							cardMatrix[x][next].setNum(0);
							
							Game2048Activity.getGameActivity().addScore(cardMatrix[x][y].getNum());
							//重新判断一次
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	private void initGameView() {
		this.setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(0xffbbada0);
		
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 适配屏幕大小
		super.onSizeChanged(w, h, oldw, oldh);
		Config.CARD_WIDTH=(Math.min(w, h)-10)/Config.LINES;
		addCards();
		startGame();
	}
	private void startGame() {
		Game2048Activity.getGameActivity().startGame();
	}
	private void addCards() {
		//添加所有的card
		Card card=null;
		//每行存放4个card，用LinearLayout包裹
		LinearLayout line=null;
		LinearLayout.LayoutParams layoutParams=null;
		
		for (int y = 0; y < Config.LINES; y++) {
			line=new LinearLayout(getContext());
			layoutParams=new LayoutParams(-1,Config.CARD_WIDTH);
			addView(line,layoutParams);
			for(int x=0;x<Config.LINES;x++){
				card=new Card(getContext());
				line.addView(card, Config.CARD_WIDTH, Config.CARD_WIDTH);
				
				cardMatrix[x][y]=card;
			}
		}
		
	}
	public void initCardMatirx(){
		for (int y = 0; y < Config.LINES; y++) {
			for(int x=0;x<Config.LINES;x++){
				if (cardMatrix[x][y]!=null) {
					cardMatrix[x][y].setNum(0);
				}
			}
		}
	}
	public  void addRandomNum(){

		emptyPoints.clear();

		//找到所有空card
		for (int y = 0; y < Config.LINES; y++) {
			for (int x = 0; x < Config.LINES; x++) {
				if (cardMatrix[x][y].getNum()<=0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		if (emptyPoints.size()>0) {

			Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
			cardMatrix[p.x][p.y].setNum(Math.random()>0.1?2:4);

			Game2048Activity.getGameActivity().getAnimLayer().createScaleTo1(cardMatrix[p.x][p.y]);
		}
	}
	private void checkComplete() {
		boolean complete = true;

		ALL:
			for (int y = 0; y < Config.LINES; y++) {
				for (int x = 0; x < Config.LINES; x++) {
					if (cardMatrix[x][y].getNum()==0||
							(x>0&&cardMatrix[x][y].equals(cardMatrix[x-1][y]))||
							(x<Config.LINES-1&&cardMatrix[x][y].equals(cardMatrix[x+1][y]))||
							(y>0&&cardMatrix[x][y].equals(cardMatrix[x][y-1]))||
							(y<Config.LINES-1&&cardMatrix[x][y].equals(cardMatrix[x][y+1]))) {

						complete = false;
						break ALL;
					}
				}
			}

		if (complete) {
			Game2048Activity.getGameActivity().saveBestScore();
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
	}
	



}
