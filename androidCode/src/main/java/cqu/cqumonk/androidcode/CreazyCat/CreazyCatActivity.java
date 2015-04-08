package cqu.cqumonk.androidcode.CreazyCat;

import android.app.Activity;
import android.os.Bundle;

public class CreazyCatActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new PlayGround(this));
	}

}
