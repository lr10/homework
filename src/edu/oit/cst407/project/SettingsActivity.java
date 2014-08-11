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

/**
 * This class implements the user's settings portion of the application.
 * It allows the user to specify how they would like the application
 * to personally interact with them.
 * 
 * @author Leander Rodriguez
 * @version 1.0
 *
 */
public class SettingsActivity extends Activity implements OnClickListener {
	
	private EditText addressText;
	private Switch pushSwitch;
	private Switch reminderSwitch;
	private EditText radiusText;
	private RadioGroup radioMapGroup;
	private RadioButton selectedRBtn;
	
	/**
	 * This method calls a method to set up all class variables
	 * and also calls a method to load the stored SharedPreferences.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
			
		setupVariables();
		
		loadPrefs();
	}
	
	/**
	 * This method initializes all member variables with all the widgets
	 * in the SettingsActivity layout respectfully.  Click listeners for all
	 * these widgets are also introduced. 
	 */
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
	
	/**
	 * This method introduces the storage of the user's by way of
	 * SharedPreferences.  The user's settings are stored into storage
	 * variables 'forever' until the user decides to change them.
	 */
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
	
	/**
	 * This method instantiates the SharedPreferences storage functionality.
	 * 
	 * @param key the identification that will be used to reference a stored value
	 * @param value the string value of what is being stored
	 */
	private void savePrefs(String key, String value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * This method instantiates the SharedPreferences storage functionality.
	 * 
	 * @param key the identification that will be used to reference a stored value
	 * @param value the boolean value of what is being stored
	 */
	private void savePrefs(String key, boolean value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * This methods listens to when an EditText widget is 
	 * clicked and clears any current text to allow the user
	 * to input new text.
	 * 
	 * {@inheritDoc}
	 */
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
	
	/**
	 * This method adds an action bar to the top of the application.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_settings_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This method listens to when the action bar Save or
	 * Cancel icon is selected.
	 * 
	 * {@inheritDoc}
	 */
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
	
	/**
	 * This method saves the user's settings into the SharedPreferences
	 * storage and notifies the user that the settings have been saved.
	 */
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
	
	/**
	 * This method closes the current SettingsAcitivty and returns
	 * to the MainActivity.
	 */
	public void cancelClicked() {
		finish();
	} 
}
