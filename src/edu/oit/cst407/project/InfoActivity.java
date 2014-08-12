package edu.oit.cst407.project;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * This class displays the game details of the marker selected by the user.
 * @author Leander Rodriguez
 * @version 1.0
 *
 */
public class InfoActivity extends Activity {

	public String deviceLang = Locale.getDefault().getDisplayLanguage();
	private double latitude;
	private double longitude;
	private EditText location;
	private EditText dateText;
	private EditText timeText;
	private EditText minAgeText;
	private EditText maxAgeText;
	private SeekBar skillLevelBar;
	private RadioButton radioGenderBtn; 
	private RadioButton radioPitchBtn; 
	private RadioButton radioGameBtn;
	
	/**
	 * This method calls a method to set up all class variables
	 * to reflect the game marker information passed in.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		setupVariables();
	}
	
	
	/**
	 * This method sets the variables to the correct game marker
	 * information once again.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setupVariables();
	}



	/**
	 * This method initializes all member variables with all the widgets
	 * in the InfoActivity layout respectfully. Widget values are then
	 * set to show the marker information the user had chosen.
	 */
	private void setupVariables(){
		
		// Widgets
		location = (EditText) findViewById(R.id.defaultLocation);
		dateText = (EditText) findViewById(R.id.dateText);
		timeText = (EditText) findViewById(R.id.timeText);
		minAgeText = (EditText) findViewById(R.id.minAge);
		maxAgeText = (EditText) findViewById(R.id.maxAge);
		skillLevelBar = (SeekBar) findViewById(R.id.skillLevelBar);
		radioGenderBtn = (RadioButton) findViewById(R.id.maleRadio); 
		radioPitchBtn = (RadioButton) findViewById(R.id.grassRadio);
		radioGameBtn = (RadioButton) findViewById(R.id.outdoorRadio); 
		
		// Set marker info
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
		    latitude = extras.getDouble("EXTRA_LATITUDE");
		    longitude = extras.getDouble("EXTRA_LONGITUDE");
		    location.setText(String.valueOf(latitude).substring(0,10) 
								+ ", " 
								+ String.valueOf(longitude).substring(0,10));
		    dateText.setText(extras.getString("EXTRA_DATE"));
		    timeText.setText(extras.getString("EXTRA_TIME"));
		    minAgeText.setText(extras.getString("EXTRA_MIN_AGE"));
		    maxAgeText.setText(extras.getString("EXTRA_MAX_AGE"));
		    skillLevelBar.setProgress(extras.getInt("EXTRA_SKILL_LEVEL"));
		    
		    String gender = extras.getString("EXTRA_GENDER");
		    String pitch = extras.getString("EXTRA_PITCH");
		    String gameType = extras.getString("EXTRA_GAME_TYPE");
		    
		    if( gender.equals("Male") ){
		    	radioGenderBtn = (RadioButton) findViewById(R.id.maleRadio);
		    }
		    else if( gender.equals("Female") ){
		    	radioGenderBtn = (RadioButton) findViewById(R.id.femaleRadio);
		    }
		    else if( gender.equals("Coed") ){
		    	radioGenderBtn = (RadioButton) findViewById(R.id.coedRadio);
		    }
		    
		    if( pitch.equals("Male") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.grassRadio);
		    }
		    else if( pitch.equals("Female") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.turfRadio);
		    }
		    else if( pitch.equals("Coed") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.futsalRadio);
		    }
		    
		    if( gameType.equals("Male") ){
		    	radioGameBtn = (RadioButton) findViewById(R.id.indoorRadio);
		    }
		    else if( gameType.equals("Female") ){
		    	radioGameBtn = (RadioButton) findViewById(R.id.outdoorRadio);
		    }
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
		inflater.inflate(R.menu.activity_game_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This method listens to when the action bar Join or
	 * Cancel icon is selected.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
				
		switch(item.getItemId())
		{
			case R.id.action_save :
				joinGameClicked();
				return true;
			
			case R.id.action_cancel :
				cancelClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * This method adds you to the currently selected game.
	 */
	public void joinGameClicked() {

		// touch notification
		Toast toast = Toast.makeText(getApplicationContext(), "You have joined this game.", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		finish();
	} 
	
	/**
	 * This method closes the current InfoAcitivty and returns
	 * to the MainActivity.
	 */
	public void cancelClicked() {
		finish();
	}
}
