package cqu.cqumonk.androidcode.ui;

import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import cqu.cqumonk.androidcode.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BarCode extends Activity {

	private Button mBtn_Scan;
	private Button mBtn_Gen;
	private TextView mTv_qrResult;
	private EditText mEt_qrString;
	private ImageView mIv_Qrimg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_code);
		initView();
		initEvent();
	}

	private void initEvent() {
		mBtn_Scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(BarCode.this,CaptureActivity.class);
				startActivityForResult(intent,0);
			}
		});
		mBtn_Gen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String qrString=mEt_qrString.getText().toString();
				if (qrString.equals("")) {
					Toast.makeText(BarCode.this, "请输入文本", Toast.LENGTH_SHORT).show();
					
				}else {
					try {
						Bitmap qrBitmap=EncodingHandler.createQRCode(qrString, 400);
						mIv_Qrimg.setImageBitmap(qrBitmap);
					} catch (WriterException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	}

	private void initView() {
		mBtn_Scan=(Button) findViewById(R.id.btn_scan);
		mBtn_Gen=(Button) findViewById(R.id.btn_generateQR);
		mTv_qrResult=(TextView) findViewById(R.id.tv_scanResult);
		mEt_qrString=(EditText) findViewById(R.id.et_QRstring);
		mIv_Qrimg=(ImageView) findViewById(R.id.iv_QRimg);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			mEt_qrString.setText(data.getExtras().getString("result"));
			
		}
	}


}
