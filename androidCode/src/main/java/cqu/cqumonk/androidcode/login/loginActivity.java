package cqu.cqumonk.androidcode.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.main.MainActivity;

public class loginActivity extends Activity implements LoginView,View.OnClickListener{

    private EditText mEt_username;
    private EditText mEt_pwd;
    private Button mBtn_login;
    private ProgressBar mPb_login_progress;

    private ILoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loginPresenter=new LoginPresenter(this);

    }

    private void initViews() {
        mEt_username= (EditText) findViewById(R.id.et_login_username);
        mEt_pwd=(EditText) findViewById(R.id.et_login_password);
        mBtn_login= (Button) findViewById(R.id.btn_login_login);
        mPb_login_progress= (ProgressBar) findViewById(R.id.pb_login_progress);

        mBtn_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        String name=mEt_username.getText().toString();
        String pwd=mEt_pwd.getText().toString();

        loginPresenter.validateCredentials(name,pwd);
    }

    @Override
    public void showProgress() {
        mPb_login_progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mPb_login_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUsernameError(String error) {

        mEt_username.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        mEt_pwd.setError(error);
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
