package cqu.cqumonk.androidcode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private View mBackground;
	private TextView mNumView;
	
	private int num=0;
	
	public Card(Context context) {
		super(context);
		
		
		
		initView();
	}
	private void initView() {
		LayoutParams layoutParams=new LayoutParams(-1,-1);
		layoutParams.setMargins(10, 10, 0, 0);
		mBackground=new View(getContext());
		mBackground.setBackgroundColor(0x33ffffff);
		addView(mBackground,layoutParams);
		
		mNumView=new TextView(getContext());
		mNumView.setTextSize(28);
		mNumView.setGravity(Gravity.CENTER);
		addView(mNumView,layoutParams);
		setNum(0);
		
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if (num<=0) {
			this.mNumView.setText("");
		}else {
			this.mNumView.setText(num+"");
		}
		
		switch (num) {
		case 0:
			mNumView.setBackgroundColor(0x00000000);
			break;
		case 2:
			mNumView.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			mNumView.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			mNumView.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			mNumView.setBackgroundColor(0xfff59563);
			break;
		case 32:
			mNumView.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			mNumView.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			mNumView.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			mNumView.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			mNumView.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			mNumView.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			mNumView.setBackgroundColor(0xffedc22e);
			break;
		default:
			mNumView.setBackgroundColor(0xff3c3a32);
			break;
		}
	}
	public boolean isSame(Card card){
		if (card.getNum()==this.num) {
			return true;
		}
		return false;
	}
	@Override
	protected Card clone()  {
		Card card=new Card(getContext());
		card.setNum(getNum());
		return card;
	}
	public TextView getNumView() {
		return mNumView;
	}

	


}
