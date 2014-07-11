package edu.oit.cst407.hw3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GenieActivity<MainActivity> extends Activity {

	private static final String TAG = "SavePreferences: ";
	private Button b1 = null;
	private Button b2 = null;
	private Button b3 = null;
	private Boolean boolButtonClicked = false;
	private static final String BUTTON_CLICKED = "Button_Clicked";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genie);
		Log.v(TAG, "onCreate()");
		
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
		
		buttonClicked();
		
		/*
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});*/
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "onPause()");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.v(TAG, "onSaveInstanceState()");
		
		outState.putBoolean(BUTTON_CLICKED, boolButtonClicked);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.v(TAG, "onRestoreInstanceState()");
		
		if( savedInstanceState != null && savedInstanceState.containsKey(BUTTON_CLICKED))
		{
			boolButtonClicked = savedInstanceState.getBoolean(BUTTON_CLICKED);
		}
		
		buttonClicked();
	}
	
	private void buttonClicked() {
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button)v).setEnabled(false);
			}
		});
	}
}
