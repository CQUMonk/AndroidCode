package cqu.cqumonk.androidcode.ui;


import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.config.Config;
import cqu.cqumonk.androidcode.view.AnimLayer;
import cqu.cqumonk.androidcode.view.GameView;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Game2048 extends Activity {
	public Game2048(){
		gameActivity=this;
	}

	private LinearLayout rootLayout;
	private TextView mScoreTextView, mBestScoreTextView;
	private Button mNewGameButton;
	private GameView mGameView;
	private AnimLayer mAnimLayer;
	
	public static Game2048 gameActivity=null;
	
	private int score=0;
	private int bestScore=0;
	public static final String SP_KEY_BEST_SCORE = "bestScore";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game2048);
		initViews();
		initEvents();
	}

	private void initEvents() {
		mNewGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame();
			}
		});
	}

	private void initViews() {
		rootLayout=(LinearLayout) findViewById(R.id.ll_container);
		rootLayout.setBackgroundColor(0xfffaf8ef);
		mScoreTextView=(TextView) findViewById(R.id.tv_score);
		mBestScoreTextView=(TextView) findViewById(R.id.tv_bestScore);
		mNewGameButton=(Button) findViewById(R.id.btnNewGame);
		
		mGameView=(GameView) findViewById(R.id.id_gameview2048);
		mAnimLayer=(AnimLayer) findViewById(R.id.id_animLayer);
		
	}
	public void startGame(){
		score=0;
		mScoreTextView.setText(score+"");
		mBestScoreTextView.setText(getBestScore()+"");
		mGameView.initCardMatirx();
		mGameView.addRandomNum();
		mGameView.addRandomNum();
		
		
	}
	public void saveBestScore(){
		if(bestScore>getBestScore()){
			Editor e = getPreferences(MODE_PRIVATE).edit();
			e.putInt(SP_KEY_BEST_SCORE, bestScore);
			e.commit();
		}
		
	}

	public int getBestScore(){
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}
	public  AnimLayer getAnimLayer() {
		return mAnimLayer;
	}
	public void addScore(int increase){
		score+=increase;
		mScoreTextView.setText(score+"");
		if(score>bestScore){
			bestScore=score;
			mBestScoreTextView.setText(bestScore+"");
		}
	}
	
	public static Game2048 getGameActivity(){
		return gameActivity;
	}

}
