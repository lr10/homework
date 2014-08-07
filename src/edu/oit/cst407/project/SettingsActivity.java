package edu.oit.cst407.project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private EditText addressText;
	private Switch pushSwitch;
	private Switch reminderSwitch;
	private EditText radiusText;
	private RadioGroup radioMapGroup;
	private RadioButton selectedRBtn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
			
		setupVariables();
		
		loadPrefs();
	}
	
	private void setupVariables(){
		
		// Widgets
		addressText = (EditText) findViewById(R.id.defaultLocation);
		pushSwitch = (Switch) findViewById(R.id.pushSwitch);
		reminderSwitch = (Switch) findViewById(R.id.remindersSwitch);
		radiusText = (EditText) findViewById(R.id.milesRadius);
		radioMapGroup = (RadioGroup) findViewById(R.id.radioGameType); 
		
		// Listeners
		addressText.setOnClickListener(this);
		radiusText.setOnClickListener(this);
	    radioMapGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	        	
	        	selectedRBtn = (RadioButton) findViewById(checkedId); 
	        }
	    });
		
		
	}
	
	private void loadPrefs(){
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String address = prefs.getString("SHAREDPREF_ITEM_DEFAULT_ADDRESS", "");
		Boolean push = prefs.getBoolean("SHAREDPREF_ITEM_PUSH", false);
		Boolean reminders = prefs.getBoolean("SHAREDPREF_ITEM_REMINDERS", false);
		String radius = prefs.getString("SHAREDPREF_ITEM_RADIUS", "");
		String radioBtn = prefs.getString("SHAREDPREF_ITEM_MAP_TYPE", "Normal");
		
		addressText.setText(address);
				
		if( push == false )
		{
			pushSwitch.setChecked(false);
		}
		else
		{
			pushSwitch.setChecked(true);
		}
		
		if( reminders == false )
		{
			reminderSwitch.setChecked(false);
		}
		else
		{
			reminderSwitch.setChecked(true);
		}
		
		radiusText.setText(radius);
		
		if( radioBtn == "Normal" )
		{
			selectedRBtn = (RadioButton) findViewById(R.id.normalRadio);
			selectedRBtn.setChecked(true);
		}
		else if( radioBtn == "Hybrid" )
		{
			selectedRBtn = (RadioButton) findViewById(R.id.hybridRadio);
			selectedRBtn.setChecked(true);
		}
		else if( radioBtn == "Satellite" )
		{
			selectedRBtn = (RadioButton) findViewById(R.id.satelliteRadio);
			selectedRBtn.setChecked(true);
		}
	}
	
	private void savePrefs(String key, String value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	private void savePrefs(String key, boolean value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	@Override
	public void onClick(View v){
	
		switch( v.getId())
		{
			case R.id.defaultLocation : addressText.setText("");
										break;
										
			case R.id.milesRadius	 : radiusText.setText("");
										break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_settings_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
				
		switch(item.getItemId())
		{
			case R.id.action_save :
				saveSettingsClicked();
				return true;
			
			case R.id.action_cancel :
				cancelClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void saveSettingsClicked() {
	    
		// save info 
		savePrefs("SHAREDPREF_ITEM_DEFAULT_ADDRESS", addressText.getText().toString());
		savePrefs("SHAREDPREF_ITEM_PUSH", pushSwitch.isChecked());
		savePrefs("SHAREDPREF_ITEM_REMINDERS", reminderSwitch.isChecked());
		savePrefs("SHAREDPREF_ITEM_RADIUS", radiusText.getText().toString());
		
		String radioBtnText = selectedRBtn.getText().toString();
		
		if( radioBtnText.equals( "Normal") )
		{
			savePrefs("SHAREDPREF_ITEM_MAP_TYPE", "Normal");
		}
		else if( radioBtnText.equals("Hybrid") )
		{
			savePrefs("SHAREDPREF_ITEM_MAP_TYPE", "Hybrid");
		}
		else if( radioBtnText.equals( "Satellite") )
		{
			savePrefs("SHAREDPREF_ITEM_MAP_TYPE", "Satellite");
		}
		
		
		// zoom camera in based on radius
		
		// touch notification
		Toast toast = Toast.makeText(getApplicationContext(), "Preferences saved.", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		finish();
	} 
	
	public void cancelClicked() {
		finish();
	} 
	
	
}
