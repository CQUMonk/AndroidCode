package cqu.cqumonk.androidcode.main;

/**
 * Created by cqumonk on 2015/4/6.
 */
public interface IMainView {
    public void showProgress();

    public void hideProgress();


    public void setListViewAdapter(MyAdapter adapter);
}
