package cqu.cqumonk.androidcode.ui;

import java.util.ArrayList;
import java.util.List;

import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cal extends Activity {
	static final String UPPER_NUM="upper";
	EditText mEditText;
	CalThread calThread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal);
		
		mEditText=(EditText) findViewById(R.id.id_edCal);
		calThread=new CalThread();
		calThread.start();
		
	}
	public void caculate(View source){
		Message msg=new Message();
		msg.what=0x111;
		Bundle bundle=new Bundle();
		bundle.putInt(UPPER_NUM, Integer.parseInt(mEditText.getText().toString()));
		msg.setData(bundle);
		calThread.mHandler.sendMessage(msg);
		
	}
	//定义一个线程类
	class CalThread extends Thread{
		public Handler mHandler;
		
		@Override
		public void run() {
			Looper.prepare();
			mHandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					if(msg.what==0x111){
						int upper=msg.getData().getInt(UPPER_NUM);
						List<Integer> nums=new ArrayList<Integer>();
						for(int i=2;i<=upper;++i){
							if(isPrime(i)){
								nums.add(i);
							}
						}
						Toast.makeText(Cal.this, nums.toString(), 
								Toast.LENGTH_LONG).show();
						
					}
				}
			};
			Looper.loop();
		}
		//计算质数
		public boolean isPrime(int num){
			if(num<=1) return false;
			int sqrt=(int) Math.sqrt(num);
			for(int i=2;i<=sqrt;++i){
				if(num%i==0){
					return false;
				}
			}
			
			return true;
				
		}
	}

}
