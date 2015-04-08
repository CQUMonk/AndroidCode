package cqu.cqumonk.androidcode.prime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cqu.cqumonk.androidcode.R;

public class PrimeActivity extends Activity implements IPrimeView,View.OnClickListener{
	static final String UPPER_NUM="upper";
	private EditText mEt_num;
    private Button mBtn_calculate;

    private PrimePresenter primePresenter;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prime_activity);

        mEt_num=(EditText) findViewById(R.id.et_prime_upper);
        mBtn_calculate = (Button) findViewById(R.id.btn_prime_caculate);
        primePresenter=new PrimePresenter(this);

        mBtn_calculate.setOnClickListener(this);

		
	}


    @Override
    public void onClick(View v) {
        primePresenter.calculatePrime(mEt_num.getText().toString());
    }

    @Override
    public void showNumError(String error) {
        mEt_num.setError(error);
    }
    @Override
    public void showResult(String result){
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }

}
