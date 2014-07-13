package edu.oit.cst407.hw3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GenieActivity extends Activity {

	private static final String TAG = "SavePreferences: ";
	private Button[] buttons = new Button[3];
	private boolean [] boolButtonsClicked = new boolean[3];
	
	private SharedPreferences prefs;
	private String prefName = "MyPref";
	private static final String BUTTON1_STATE = "button1_State";
	private static final String BUTTON2_STATE = "button2_State";
	private static final String BUTTON3_STATE = "button3_State";
	
	public int idx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genie);
		Log.v(TAG, "onCreate()");
		
		buttons[0] = (Button)findViewById(R.id.button1);
		buttons[1] = (Button)findViewById(R.id.button2);
		buttons[2] = (Button)findViewById(R.id.button3);
		
		buttons[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
				boolButtonsClicked[0] = true;
			}
		});
		buttons[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
				boolButtonsClicked[1] = true;
			}
		});
		buttons[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
				boolButtonsClicked[2] = true;
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "onPause()");
		
		for( idx = 0; idx < buttons.length; ++idx)
		{
			if( !buttons[idx].isEnabled() )
			{
				boolButtonsClicked[idx] = true;
			}
			else
			{
				boolButtonsClicked[idx] = false;
			}
		}
		
		
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(BUTTON1_STATE, boolButtonsClicked[0]);
		editor.putBoolean(BUTTON2_STATE, boolButtonsClicked[1]);
		editor.putBoolean(BUTTON3_STATE, boolButtonsClicked[2]);
		editor.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume()");
		
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		boolButtonsClicked[0] = prefs.getBoolean(BUTTON1_STATE, false);
		boolButtonsClicked[1] = prefs.getBoolean(BUTTON2_STATE, false);
		boolButtonsClicked[2] = prefs.getBoolean(BUTTON3_STATE, false);
		
		for( idx = 0; idx < boolButtonsClicked.length; ++idx)
		{
			if( boolButtonsClicked[idx] == true )
			{
				buttons[idx].setEnabled(false);
			}
			else
			{
				buttons[idx].setEnabled(true);
			}
		}
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "onStart()");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v(TAG, "onStop()");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "onDestroy()");
	}
}
