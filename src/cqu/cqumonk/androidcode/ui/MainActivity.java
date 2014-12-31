package cqu.cqumonk.androidcode.ui;





import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button mBtn_PassParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	/**
	 * 初始化并配置view
	 */
	private void initView() {
		//实现页面跳转
		mBtn_PassParams=(Button) findViewById(R.id.btn_passParams);
		mBtn_PassParams.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,PassParams.class);
				//包名+变量名传递参数
				intent.putExtra("cqu.cqumonk.androidcode.ui.age", 20);
				//Bundle类似于一个hashmap<sting,object>
				Bundle bundle=new Bundle();
				bundle.putString("name", "cqumonk");
				intent.putExtra("bundle", bundle);
				
				startActivityForResult(intent, 1);
			}
		});

	}
	/**
	 * 处理该页面接收的参数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//接收回传数据
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&&resultCode==2){
			String string=data.getStringExtra("returnParams");
			Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
		}
		
	}




}
