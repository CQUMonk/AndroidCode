package cqu.cqumonk.androidcode.main;





import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import cqu.cqumonk.androidcode.R;

public class MainActivity extends Activity implements IMainView
{
	
	private MainListView mListView;
    private ProgressBar mProgress;

    private IMainPresenter mainPresenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();

		initEvent();

	}

	/**
	 * 初始化并配置view
	 */
	private void initView() {
		
		mListView=(MainListView) findViewById(R.id.lv_mian_list);
        mProgress= (ProgressBar) findViewById(R.id.pb_main_progress);

        mainPresenter=new MainPresenterImpl(this,this);


    }

	private void initEvent(){
        //点击item时候
		mListView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                mainPresenter.onItemClicked(position);
				
			}
		});
        //触发删除事件
        mListView.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                mainPresenter.onItemDelete(position);

            }
        });
	}


    @Override
    public void showProgress() {
        mListView.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mListView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }
    @Override
    public void setListViewAdapter(MyAdapter adapter){
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }


}
