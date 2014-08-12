package edu.oit.cst407.project;

import static java.lang.System.err;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
	private long rowId;
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
	private RadioGroup radioGenderGroup; 
	private RadioGroup radioPitchGroup; 
	private RadioGroup radioGameTypeGroup;
	private int players;
	public DBAdapter myDb;
	private boolean update;
	
	
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
		openDB();
	}
	
	/**
	 * This method calls a function that closes the SQLite Database.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeDB();
	}

	/**
	 * This method opens the SQLite Database
	 */
	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	
	/**
	 * This method closes the SQLite Database
	 */
	private void closeDB() {
		myDb.close();
	}



	/**
	 * This method initializes all member variables with all the widgets
	 * in the InfoActivity layout respectfully. Widget values are then
	 * set to show the marker information the user had chosen. Widgets
	 * are also disabled as this activity is for viewing only.
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
		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGender);
		radioPitchGroup = (RadioGroup) findViewById(R.id.radioPitch);
		radioGameTypeGroup = (RadioGroup) findViewById(R.id.radioGameType);
		
		// Set marker info
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			rowId = extras.getLong("EXTRA_ROW_ID");
		    latitude = extras.getDouble("EXTRA_LATITUDE");
		    longitude = extras.getDouble("EXTRA_LONGITUDE");
		    location.setText(String.valueOf(latitude).substring(0,10) 
								+ ", " 
								+ String.valueOf(longitude).substring(0,10));
		    dateText.setText(extras.getString("EXTRA_DATE"));
		    timeText.setText(extras.getString("EXTRA_TIME"));
		    minAgeText.setText(extras.getString("EXTRA_AGE_MIN"));
		    maxAgeText.setText(extras.getString("EXTRA_AGE_MAX"));
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
		    
		    radioGenderBtn.setChecked(true);
		    
		    if( pitch.equals("Grass") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.grassRadio);
		    }
		    else if( pitch.equals("Turf") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.turfRadio);
		    }
		    else if( pitch.equals("Futsal") ){
		    	radioPitchBtn = (RadioButton) findViewById(R.id.futsalRadio);
		    }
		    
		    radioPitchBtn.setChecked(true);
		    
		    if( gameType.equals("Male") ){
		    	radioGameBtn = (RadioButton) findViewById(R.id.indoorRadio);
		    }
		    else if( gameType.equals("Female") ){
		    	radioGameBtn = (RadioButton) findViewById(R.id.outdoorRadio);
		    }
		    
		    radioGameBtn.setChecked(true);
		    
		    players = extras.getInt("EXTRA_PLAYERS");
		}
		
		
		// Disable all widgets, making activity read only
		location.setEnabled(false);
		dateText.setEnabled(false);
		timeText.setEnabled(false);
		minAgeText.setEnabled(false);
		maxAgeText.setEnabled(false);
		skillLevelBar.setEnabled(false);
		
		for(int i = 0; i < radioGenderGroup.getChildCount(); i++){
		    ((RadioButton)radioGenderGroup.getChildAt(i)).setEnabled(false);
		}
		
		for(int i = 0; i < radioPitchGroup.getChildCount(); i++){
		    ((RadioButton)radioPitchGroup.getChildAt(i)).setEnabled(false);
		}
		
		for(int i = 0; i < radioGameTypeGroup.getChildCount(); i++){
		    ((RadioButton)radioGameTypeGroup.getChildAt(i)).setEnabled(false);
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
		inflater.inflate(R.menu.activity_info_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This method listens to when the action bar's Join,
	 * Leave or Cancel icons are selected.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
				
		switch(item.getItemId())
		{
			case R.id.action_join :
				joinGameClicked();
				return true;
				
			case R.id.action_leave :
				leaveGameClicked();
				return true;
			
			case R.id.action_cancel :
				cancelClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * This method closes asked the user if they would like to 
	 * join the current game.  The user makes a selection and
	 * returns to the MainActivity.
	 */
	public void joinGameClicked() {
		
		if( deviceLang.equals("espa–ol")){
			new AlertDialog.Builder(this)
		    .setTitle("Unirse A Este Juego")
		    .setMessage("Seguro que quieres unirte a este juego?")
		    .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	// Add player
		        	players += 1;
		        
		    		update = myDb.updateRow(rowId, latitude, longitude, dateText.getText().toString(),
		    								timeText.getText().toString(), minAgeText.getText().toString(),
		    								maxAgeText.getText().toString(), skillLevelBar.getProgress(),
		    								radioGenderBtn.getText().toString(), radioPitchBtn.getText().toString(),
		    								radioGameBtn.getText().toString(), players);
		        	
		        	// touch notification
		    		Toast toast = Toast.makeText(getApplicationContext(), "Te has unido a este juego.", Toast.LENGTH_SHORT);
		    		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		    		toast.show();
		    		finish();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
		else{
			new AlertDialog.Builder(this)
		    .setTitle("Join Game")
		    .setMessage("Are you sure you want to join this game?")
		    .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	// Add player
		        	players += 1;
		        
		    		update = myDb.updateRow(rowId, latitude, longitude, dateText.getText().toString(),
		    								timeText.getText().toString(), minAgeText.getText().toString(),
		    								maxAgeText.getText().toString(), skillLevelBar.getProgress(),
		    								radioGenderBtn.getText().toString(), radioPitchBtn.getText().toString(),
		    								radioGameBtn.getText().toString(), players);

		        	// touch notification
		    		Toast toast = Toast.makeText(getApplicationContext(), "You have joined this game.", Toast.LENGTH_SHORT);
		    		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		    		toast.show();
		    		finish();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
	} 
	
	/**
	 * This method closes asked the user if they would like to 
	 * leave the current game.  The user makes a selection and
	 * returns to the MainActivity.
	 */
	public void leaveGameClicked() {
		
		if( deviceLang.equals("espa–ol")){
			
			new AlertDialog.Builder(this)
		    .setTitle("Dejar Este Juego")
		    .setMessage("Seguro que quieres dejar este juego?")
		    .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	// Subtract player
		        	players -= 1;
		        
		    		update = myDb.updateRow(rowId, latitude, longitude, dateText.getText().toString(),
		    								timeText.getText().toString(), minAgeText.getText().toString(),
		    								maxAgeText.getText().toString(), skillLevelBar.getProgress(),
		    								radioGenderBtn.getText().toString(), radioPitchBtn.getText().toString(),
		    								radioGameBtn.getText().toString(), players);
		        	
		        	// touch notification
		    		Toast toast = Toast.makeText(getApplicationContext(), "Has dejado este juego.", Toast.LENGTH_SHORT);
		    		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		    		toast.show();
		    		finish();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
		else{
			
			new AlertDialog.Builder(this)
		    .setTitle("Leave Game")
		    .setMessage("Are you sure you want to leave this game?")
		    .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	// Subtract player
		        	players -= 1;
		        
		    		update = myDb.updateRow(rowId, latitude, longitude, dateText.getText().toString(),
		    								timeText.getText().toString(), minAgeText.getText().toString(),
		    								maxAgeText.getText().toString(), skillLevelBar.getProgress(),
		    								radioGenderBtn.getText().toString(), radioPitchBtn.getText().toString(),
		    								radioGameBtn.getText().toString(), players);
		        	
		        	// touch notification
		    		Toast toast = Toast.makeText(getApplicationContext(), "You have left this game.", Toast.LENGTH_SHORT);
		    		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		    		toast.show();
		    		finish();
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
	}
	
	/**
	 * This method closes the current InfoAcitivty and returns
	 * to the MainActivity.
	 */
	public void cancelClicked() {
		finish();
	}
}
