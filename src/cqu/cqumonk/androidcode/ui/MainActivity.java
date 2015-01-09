package cqu.cqumonk.androidcode.ui;





import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	//页面间参数传递按钮
	private Button mBtn_PassParams;
	//类似QQ菜单侧边栏按钮
	private Button mBtn_QQslideMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}

	/**
	 * 初始化并配置view
	 */
	private void initView() {
		//页面参数传递页面跳转
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
		//QQ侧边栏菜单页面跳转
		mBtn_QQslideMenu=(Button) findViewById(R.id.btn_QQslideMenu);
		mBtn_QQslideMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,QQslide_menu.class);
				startActivity(intent);
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
