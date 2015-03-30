package cqu.cqumonk.androidcode.ui;





import java.util.ArrayList;

import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.model.MyItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ListView mListView;
	private ArrayList<MyItem> mDas;
	private MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initDas();
		initEvent();
	}

	/**
	 * 初始化并配置view
	 */
	private void initView() {
		
		mListView=(ListView) findViewById(R.id.lv_item);
		

	}
	private void initDas(){
		mDas=new ArrayList<MyItem>();
		MyItem item1=new MyItem(R.drawable.img_1,"页面间回传数据","演示通过intent传递参数");
		mDas.add(item1);
		MyItem item2=new MyItem(R.drawable.img_2,"QQ侧边栏","QQ侧边栏菜单页面效果");
		mDas.add(item2);
		MyItem item3=new MyItem(R.drawable.img_3,"weixin界面","weixin界面效果");
		mDas.add(item3);
		MyItem item4=new MyItem(R.drawable.img_4,"跟随手指小球","演示自定义控件，跟随手指小球");
		mDas.add(item4);
		MyItem item5=new MyItem(R.drawable.img_5,"计算质数","演示新线程计算质数");
		mDas.add(item5);
		MyItem item6=new MyItem(R.drawable.img_1,"神经猫","模拟神经猫计算逻辑");
		mDas.add(item6);
		
		mAdapter=new MyAdapter(this, mDas);
		mListView.setAdapter(mAdapter);
	}
	private void initEvent(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					intent=new Intent(MainActivity.this,PassParams.class);
					//包名+变量名传递参数
					intent.putExtra("cqu.cqumonk.androidcode.ui.age", 20);
					//Bundle类似于一个hashmap<sting,object>
					Bundle bundle=new Bundle();
					bundle.putString("name", "cqumonk");
					intent.putExtra("bundle", bundle);
					
					startActivityForResult(intent, 1);
					break;

				case 1:
					//QQ侧边栏菜单页面跳转
					intent=new Intent(MainActivity.this,QQslide_menu.class);
					startActivity(intent);
					break;
				case 2:
					//weixin界面
					intent= new Intent(MainActivity.this,Weixin.class);
					startActivity(intent);
					break;
				case 3:
					//演示自定义控件，跟随手指小球
					intent =new Intent(MainActivity.this,FollowFingerBall.class);
					startActivity(intent);
					break;
				case 4:
					//新线程计算质数
					intent=new Intent(MainActivity.this,Cal.class);
					startActivity(intent);
					break;
				case 5:
					//新线程计算质数
					intent=new Intent(MainActivity.this,CreazyCat.class);
					startActivity(intent);
					break;

				}
				
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
