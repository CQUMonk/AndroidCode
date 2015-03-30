package cqu.cqumonk.androidcode.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {
	public static final String TITTLE = "tittle";
	private String mTittle = "default";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getArguments() != null) {

			mTittle = getArguments().getString(TITTLE);
		}
		TextView textView = new TextView(getActivity());
		textView.setTextSize(30);
		textView.setBackgroundColor(Color.parseColor("#ffffffff"));
		textView.setText(mTittle);
		textView.setGravity(Gravity.CENTER);
		return textView;
	}

}
