package cqu.cqumonk.androidcode.login;

import android.text.TextUtils;

/**
 * Created by cqumonk on 2015/4/6.
 */
public class LoginPresenter implements ILoginPresenter,OnLoginFinishedListener {

    private LoginView mLoginView;
    private ILoginInteractor loginInteractor;

    public LoginPresenter(LoginView view) {
        this.mLoginView=view;
        this.loginInteractor=new LoginInteractor();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(TextUtils.isEmpty(username)){
            mLoginView.setPasswordError("用户名不能为空");
        }else if (TextUtils.isEmpty(password)){
            mLoginView.setPasswordError("密码不能为空");
        }else {
            mLoginView.showProgress();
            //请求数据验证
            loginInteractor.login(username,password,this);

        }
    }

    @Override
    public void onPasswordError() {
        mLoginView.hideProgress();
        mLoginView.setPasswordError("密码错误");
    }

    @Override
    public void onSuccess() {
        mLoginView.navigateToHome();
    }
}
