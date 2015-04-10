package cqu.cqumonk.androidcode.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import cqu.cqumonk.androidcode.R;

/**
 * Created by CQUMonk on 2015/4/9.
 */
public class MainListView extends ListView implements OnGestureListener,View.OnTouchListener {

    //用于监听手势
    private GestureDetector mGestureDetector;

    private OnDeleteListener mDeleteListener;
    //button
    private View mBtn_delete;
    //item
    private ViewGroup mItemLayout;
    private int mSelectedItem;
    //删除按钮是否显示
    private boolean mIsDeleteShown;




    public MainListView(Context context) {
        this(context, null);
    }

    public MainListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MainListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //传入context和OnGestureListener
        mGestureDetector=new GestureDetector(context,this);
        setOnTouchListener(this);
    }


    public void setOnDeleteListener(OnDeleteListener listener){
        this.mDeleteListener=listener;
    }
    //gestureListener

    /**
     * 当手指按下时调用
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //删除按钮未显示时
        if (!mIsDeleteShown){
            //判断出当前选中的是ListView的哪一行
            mSelectedItem=pointToPosition((int)e.getX(),(int)e.getY());
            Log.e("lv","ondown:"+mSelectedItem);
        }
        return false;
    }



    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 当手指快速滑动时,加载delete_button.xml这个布局，然后将删除按钮添加到当前选中的那一行item上。
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        //如果删除按钮未显示，且横向滑动
        if((!mIsDeleteShown)&&Math.abs(velocityX)>Math.abs(velocityY)){

            mBtn_delete= LayoutInflater.from(getContext()).inflate(R.layout.delete_button,null);
            mBtn_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemLayout.removeView(mBtn_delete);
                    mIsDeleteShown=false;
                    mBtn_delete=null;
                    mDeleteListener.onDelete(mSelectedItem);

                }
            });
            mItemLayout=(ViewGroup)getChildAt(mSelectedItem-getFirstVisiblePosition());


            Log.e("lv","onfling:"+mItemLayout.getChildCount());
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            mItemLayout.addView(mBtn_delete,params);
            mIsDeleteShown=true;

        }


        return false;
    }

    //OnTouchListener

    /**
     * 进行判断，如果删除按钮已经显示了，就将它移除掉，如果删除按钮没有显示，就使用GestureDetector来处理当前手势。
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mIsDeleteShown){
            mItemLayout.removeView(mBtn_delete);
            mBtn_delete=null;
            mIsDeleteShown=false;
            return false;
        }else {
            return mGestureDetector.onTouchEvent(event);
        }

    }
}
