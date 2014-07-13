package edu.oit.cst407.hw3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class GenieActivity extends Activity {

	private static final String TAG = "SavePreferences: ";
	private ToggleButton [] toggleButtons = new ToggleButton[3];
	private boolean [] toggleButtonsClicked = new boolean[3];
	private static final String t_BUTTON1_STATE = "t_button1_State";
	private static final String t_BUTTON2_STATE = "t_button2_State";
	private static final String t_BUTTON3_STATE = "t_button3_State";
	
	private SharedPreferences prefs;
	private String prefName = "MyPref";
	public int idx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genie);
		Log.v(TAG, "onCreate()");
		
		toggleButtons[0] = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleButtons[1] = (ToggleButton) findViewById(R.id.toggleButton2);
		toggleButtons[2] = (ToggleButton) findViewById(R.id.toggleButton3);
		
		
		toggleButtons[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        	toggleButtons[0].setChecked(true);
		        } /*else {
		            // The toggle is disabled
		        	toggleButtons[0].setEnabled(false);
		        }*/
		    }
		});
		toggleButtons[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        	toggleButtons[1].setChecked(true);
		        } /*else {
		            // The toggle is disabled
		        	toggleButtons[1].setEnabled(false);
		        }*/
		    }
		});
		toggleButtons[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        	toggleButtons[2].setChecked(true);
		        } /*else {
		            // The toggle is disabled
		        	toggleButtons[2].setEnabled(false);
		        }*/
		    }
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "onPause()");
		
		for( idx = 0; idx < toggleButtons.length; ++idx)
		{
			
			if( toggleButtons[idx].isChecked() )
			{
				toggleButtonsClicked[idx] = true;
			}
			else
			{
				toggleButtonsClicked[idx] = false;
			}
		}
		
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(t_BUTTON1_STATE, toggleButtonsClicked[0]);
		editor.putBoolean(t_BUTTON2_STATE, toggleButtonsClicked[1]);
		editor.putBoolean(t_BUTTON3_STATE, toggleButtonsClicked[2]);
		editor.commit();
		
		//editor.clear();
		//editor.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume()");
		
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		toggleButtonsClicked[0] = prefs.getBoolean(t_BUTTON1_STATE, false);
		toggleButtonsClicked[1] = prefs.getBoolean(t_BUTTON2_STATE, false);
		toggleButtonsClicked[2] = prefs.getBoolean(t_BUTTON3_STATE, false);
		
		for( idx = 0; idx < toggleButtonsClicked.length; ++idx)
		{
			if( toggleButtonsClicked[idx] == true )
			{
				toggleButtons[idx].setChecked(true);
			}
			else
			{
				toggleButtons[idx].setChecked(false);
			}
		}
	}
}
