package cqu.cqumonk.androidcode.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cqu.cqumonk.androidcode.R;

/**
 * Created by CQUMonk on 2015/5/11.
 */
public class FragmentA extends Fragment {

    private FragmentTabHost mSubFragmentTabHost;

    private Class[] mSubFragments={SubFragmentA.class,SubFragmentB.class,SubFragmentC.class};
    private String[] tags={"sub1","sub2","sub3"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot=inflater.inflate(R.layout.fragment_a,null);

        mSubFragmentTabHost= (FragmentTabHost) viewRoot.findViewById(R.id.sub_tabhost);

        mSubFragmentTabHost.setup(getActivity(),getChildFragmentManager(),R.id.sub_content);

        for (int i=0;i<mSubFragments.length;i++){
            mSubFragmentTabHost.addTab(mSubFragmentTabHost.newTabSpec(tags[i]).setIndicator(tags[i]),mSubFragments[i],null);
        }

        mSubFragmentTabHost.getTabWidget().setDividerDrawable(R.color.green);

        return viewRoot;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubFragmentTabHost=null;
    }
}
