package edu.oit.cst407.project;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

	public String deviceLang = Locale.getDefault().getDisplayLanguage();
	private double latitude;
	private double longitude;
	//private EditText latLong;
	private EditText dateText;
	private EditText timeText;
	private EditText minAgeText;
	private EditText maxAgeText;
	private RadioGroup radioGenderGroup; 
	private RadioButton radioGenderBtn; 
	private RadioGroup radioPitchGroup; 
	private RadioButton radioPitchBtn; 
	private RadioGroup radioGameTypeGroup; 
	private RadioButton radioGameBtn;
	private SeekBar skillLevelBar;
	
	public DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		setupVariables();
		
		openDB();
	}
	
	private void setupVariables(){
		
		// Widgets
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
		    latitude = extras.getDouble("EXTRA_LATITUDE");
		    longitude = extras.getDouble("EXTRA_LONGITUDE");
		}
		
		//latLong = (EditText) findViewById(R.id.locationText);
		dateText = (EditText) findViewById(R.id.dateText);
		timeText = (EditText) findViewById(R.id.timeText);
		minAgeText = (EditText) findViewById(R.id.minAge);
		maxAgeText = (EditText) findViewById(R.id.maxAge);
		skillLevelBar = (SeekBar) findViewById(R.id.skillLevelBar);
		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGender); 
		radioGenderBtn = (RadioButton) findViewById(R.id.maleRadio); 
		radioPitchGroup = (RadioGroup) findViewById(R.id.radioPitch); 
		radioPitchBtn = (RadioButton) findViewById(R.id.grassRadio); 
		radioGameTypeGroup = (RadioGroup) findViewById(R.id.radioGameType); 
		radioGameBtn = (RadioButton) findViewById(R.id.outdoorRadio); 
		
		
		//String latLng = String.valueOf(latitude) + ", " + String.valueOf(longitude);
		
		//latLong.setText(""+latLng);
		
		// Listeners
		//latLong.setOnClickListener(this);
		dateText.setOnClickListener(this);
		timeText.setOnClickListener(this);
		minAgeText.setOnClickListener(this);
		maxAgeText.setOnClickListener(this);
		
		skillLevelBar.setOnSeekBarChangeListener( new OnSeekBarChangeListener(){      

		    @Override       
		    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {     
		        // TODO Auto-generated method stub      
		    	
		    	skillLevelBar.setProgress(progress);
		    }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}       
		});  
		
		radioGenderGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	        	
	        	radioGenderBtn = (RadioButton) findViewById(checkedId); 
	        }
	    });
		
		radioPitchGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	        	
	        	radioPitchBtn = (RadioButton) findViewById(checkedId); 
	        }
	    });
		
		radioGameTypeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	        	
	        	radioGameBtn = (RadioButton) findViewById(checkedId); 
	        }
	    });	
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeDB();
	}

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	
	private void closeDB() {
		myDb.close();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId())
		{
			//case R.id.locationText	: latLong.setText(latitude + ", " + longitude);
										//break;
										
			case R.id.dateText	 	: dateText.setText("");
										break;
										
			case R.id.timeText	 	: timeText.setText("");
										break;
										
			case R.id.minAge	 	: minAgeText.setText("");
										break;
			
			case R.id.maxAge	 	: maxAgeText.setText("");
										break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_game_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
				
		switch(item.getItemId())
		{
			case R.id.action_save :
				saveGameClicked();
				return true;
			
			case R.id.action_cancel :
				cancelClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void saveGameClicked() {
	
		//
		// save info to database
		//
		String date = dateText.getText().toString();
		String time = timeText.getText().toString();
		String min = minAgeText.getText().toString();
		String max = maxAgeText.getText().toString();
		int skillLevel = skillLevelBar.getProgress();
		String genderBtn = radioGenderBtn.getText().toString();
		String pitchBtn = radioPitchBtn.getText().toString();
		String gameBtn = radioGameBtn.getText().toString();
		
		long newId = myDb.insertRow(latitude, longitude, date, time, min, max, skillLevel, genderBtn, pitchBtn, gameBtn);
		
		String toastLang;
		
		if( deviceLang.equals("espa–ol")){
			toastLang = getString(R.string.game_created_touch_es);
		}
		else{
			toastLang = getString(R.string.game_created_touch_en);
		}
		
		// touch notification
		Toast toast = Toast.makeText(getApplicationContext(), toastLang, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		finish();
	} 
	
	public void cancelClicked() {
		finish();
	}
}
