package cqu.cqumonk.androidcode.login;

import android.os.Handler;

/**
 * Created by Administrator on 2015/4/6.
 */
public class LoginInteractor implements ILoginInteractor {
    @Override
    public void login(String username, final String pwd, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!pwd.equals("cqumonk")){
                    listener.onPasswordError();
                }else {
                    listener.onSuccess();
                }
            }
        },3000);
    }
}
