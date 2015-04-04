package cqu.cqumonk.androidcode.view;

import java.util.ArrayList;
import java.util.List;

import cqu.cqumonk.androidcode.config.Config;
import cqu.cqumonk.androidcode.view.Card;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class AnimLayer extends FrameLayout {
	private List<Card> cards = new ArrayList<Card>();

	public AnimLayer(Context context) {
		this(context, null);
	}

	public AnimLayer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}
	/**
	 * 策略是传入初始card和最终位置的card
	 * 中间部分的动画新创建一个card来完成
	 * 动画结束后将其回收
	 * @param from
	 * @param to
	 * @param fromX
	 * @param toX
	 * @param fromY
	 * @param toY
	 */
	public void createMoveAnim(final Card from,final Card to,
			int fromX,int toX,int fromY,int toY) {
		//从回收队列中取出一个card
		final Card card=getCard(from.getNum());
		//设置card的尺寸大小
		LayoutParams layoutParams=new LayoutParams(Config.CARD_WIDTH,Config.CARD_WIDTH);
		layoutParams.leftMargin=fromX*Config.CARD_WIDTH;
		layoutParams.topMargin=fromX*Config.CARD_WIDTH;
		card.setLayoutParams(layoutParams);
		
		if (to.getNum()<=0) {
			to.getNumView().setVisibility(View.INVISIBLE);
		}
		//创建移动动画
		TranslateAnimation ta = new TranslateAnimation(0,//动画从此点开始
				Config.CARD_WIDTH*(toX-fromX), //在该点结束
				0, 
				Config.CARD_WIDTH*(toY-fromY));
		ta.setDuration(100);
		ta.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//动画结束，回收演示动画的card
				to.getNumView().setVisibility(View.VISIBLE);
				recycleCard(card);
			}
		});
		card.startAnimation(ta);
	}
	private void recycleCard(Card card) {
		card.setVisibility(View.INVISIBLE);
		card.setAnimation(null);
		cards.add(card);
	}

	private Card getCard(int num){
		Card c;
		if (cards.size()>0) {
			c = cards.remove(0);
		}else{
			c = new Card(getContext());
			addView(c);
		}
		c.setVisibility(View.VISIBLE);
		c.setNum(num);
		return c;
	}

	public void createScaleTo1(Card target){
		ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1,//起始处的缩放尺寸
				Animation.RELATIVE_TO_SELF, //x轴上伸缩模式
				0.5f,//伸缩值
				Animation.RELATIVE_TO_SELF, 
				0.5f);
		sa.setDuration(100);
		target.setAnimation(null);
		target.getNumView().startAnimation(sa);
	}
}
