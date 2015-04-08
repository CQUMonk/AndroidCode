package cqu.cqumonk.androidcode.main;

import java.util.List;

import cqu.cqumonk.androidcode.model.MyItem;

/**
 * Created by cqumonk on 2015/4/6.
 */
public interface IMainView {
    public void showProgress();

    public void hideProgress();

    public void initDas(List<MyItem> items);
}
