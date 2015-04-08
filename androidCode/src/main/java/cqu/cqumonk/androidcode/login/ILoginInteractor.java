package cqu.cqumonk.androidcode.login;

/**
 * Created by Administrator on 2015/4/6.
 */
public interface ILoginInteractor {
    public void login(String username,String pwd,OnLoginFinishedListener listener);
}
