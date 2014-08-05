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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private EditText addressText;
	private Switch pushSwitch;
	private Switch reminderSwitch;
	private EditText radiusText;
	private Button normalBtn;
	private Button hybridBtn;
	private Button satelliteBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
			
		setupVariables();
		
		loadPrefs();
	}
	
	private void setupVariables(){
		addressText = (EditText) findViewById(R.id.defaultAddress);
		pushSwitch = (Switch) findViewById(R.id.pushSwitch);
		reminderSwitch = (Switch) findViewById(R.id.remindersSwitch);
		radiusText = (EditText) findViewById(R.id.milesRadius);
		normalBtn = (Button) findViewById(R.id.normalButton);
		hybridBtn = (Button) findViewById(R.id.hybridButton);
		satelliteBtn = (Button) findViewById(R.id.satelliteButton);
		
		addressText.setOnClickListener(this);
		radiusText.setOnClickListener(this);
		normalBtn.setOnClickListener(this);
		hybridBtn.setOnClickListener(this);
		satelliteBtn.setOnClickListener(this);
	}
	
	private void loadPrefs(){
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String address = prefs.getString("SHAREDPREF_ITEM_DEFAULT_ADDRESS", "");
		Boolean push = prefs.getBoolean("SHAREDPREF_ITEM_PUSH", false);
		Boolean reminders = prefs.getBoolean("SHAREDPREF_ITEM_REMINDERS", false);
		String radius = prefs.getString("SHAREDPREF_ITEM_RADIUS", "5");
		Boolean normal = prefs.getBoolean("SHAREDPREF_ITEM_MAP_NORMAL", true);
		Boolean hybrid = prefs.getBoolean("SHAREDPREF_ITEM_MAP_HYBRID", false);
		Boolean satellite = prefs.getBoolean("SHAREDPREF_ITEM_MAP_SATELLITE", false);
		
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
		
		if( normal == true )
		{
			normalBtn.setSelected(true);
			hybridBtn.setSelected(false);
			satelliteBtn.setSelected(false);
		}
		else if( hybrid == true )
		{
			hybridBtn.setSelected(true);
			normalBtn.setSelected(false);
			satelliteBtn.setSelected(false);
		}
		else if( satellite == true )
		{
			satelliteBtn.setSelected(true);
			normalBtn.setSelected(false);
			hybridBtn.setSelected(false);
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
			case R.id.defaultAddress : addressText.setText("");
										break;
										
			case R.id.milesRadius	 : radiusText.setText("");
										break;
										
			case R.id.normalButton	 : normalBtn.setPressed(true);
										break;
															
			case R.id.hybridButton	 : hybridBtn.setSelected(true);
										break;
						
			case R.id.satelliteButton: satelliteBtn.setSelected(true);
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
		
		
		if( normalBtn.isSelected() )
		{
			savePrefs("SHAREDPREF_ITEM_MAP_NORMAL", true);
		}
		else if( hybridBtn.isSelected() )
		{
			savePrefs("SHAREDPREF_ITEM_MAP_HYBRID", true);
		}
		else if( satelliteBtn.isSelected())
		{
			savePrefs("SHAREDPREF_ITEM_MAP_SATELLITE", true);
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
