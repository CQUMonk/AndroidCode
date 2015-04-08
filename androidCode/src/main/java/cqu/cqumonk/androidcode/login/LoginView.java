package cqu.cqumonk.androidcode.login;

/**
 * Created by Administrator on 2015/4/6.
 */
public interface LoginView {

    public void showProgress();

    public void hideProgress();

    public void setUsernameError(String error);

    public void setPasswordError(String error);

    public void navigateToHome();
}
