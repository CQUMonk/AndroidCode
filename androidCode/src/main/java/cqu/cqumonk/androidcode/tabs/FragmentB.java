package cqu.cqumonk.androidcode.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cqu.cqumonk.androidcode.R;

/**
 * Created by CQUMonk on 2015/5/11.
 */
public class FragmentB extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot=inflater.inflate(R.layout.fragment_b,null);
        return viewRoot;
    }
}
