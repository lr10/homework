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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genie);
		Log.v(TAG, "onCreate()");
		
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
		
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
	
	
}
