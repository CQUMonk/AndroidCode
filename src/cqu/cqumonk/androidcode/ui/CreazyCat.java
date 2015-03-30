package cqu.cqumonk.androidcode.ui;

import cqu.cqumonk.androidcode.view.PlayGround;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CreazyCat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new PlayGround(this));
	}

}
