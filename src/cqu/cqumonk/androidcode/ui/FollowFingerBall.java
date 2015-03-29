package cqu.cqumonk.androidcode.ui;

import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.os.Bundle;
/**
 * 演示了一个自定义控件，该控件在指定位置绘制一个小球，并可以动态改变位置
 * 当用户手指在屏幕上移动时被监听到
 * 手指位置被传入控件并在该位置重绘
 * @author Administrator
 *
 */
public class FollowFingerBall extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ball);
	}

}
