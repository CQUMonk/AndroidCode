package cqu.cqumonk.androidcode.ui;

import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PassParams extends Activity {
	private Button Btn_returnParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pass_params);
		
		//接收参数
		Intent intentGetParm=getIntent();
		int age=intentGetParm.getIntExtra("cqu.cqumonk.androidcode.ui.age",0);
		Bundle bundle=intentGetParm.getBundleExtra("bundle");
		String name=bundle.getString("name");
				
		Toast.makeText(this, name+":"+age, Toast.LENGTH_LONG).show();
		Btn_returnParams=(Button) findViewById(R.id.btn_returnParams);
		Btn_returnParams.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 回传数据
				Intent intentReturnParams=new Intent();
				intentReturnParams.putExtra("returnParams", "I'm returned params");
				setResult(2, intentReturnParams);
				
				finish();
			}
		});
		
	}

}
