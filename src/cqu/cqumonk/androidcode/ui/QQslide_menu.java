package cqu.cqumonk.androidcode.ui;

import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.view.SlidingMenu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class QQslide_menu extends Activity{
	private SlidingMenu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slide_menu);
		menu=(SlidingMenu) findViewById(R.id.id_menu);
	}
	public void toggleLeftMenu(View view){
		menu.toggleLeft();
	}
	public void toggleRightMenu(View view){
		menu.toggleRight();
	}

}
