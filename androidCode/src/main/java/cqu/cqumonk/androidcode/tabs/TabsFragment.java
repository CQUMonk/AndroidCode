package cqu.cqumonk.androidcode.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cqu.cqumonk.androidcode.R;

/**
 * Created by CQUMonk on 2015/5/9.
 */
public class TabsFragment extends Fragment {
    public static final String FRAGMENT_TITTLE="FRAGMENT_TITTLE";
    private TextView mTittle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_tabs,container,false);
        mTittle= (TextView) fragmentView.findViewById(R.id.tv_tabs_tittle);
        if (getArguments()!=null){
            mTittle.setText(getArguments().getString(FRAGMENT_TITTLE));
        }

        return inflater.inflate(R.layout.fragment_tabs,container,false);
    }
}
