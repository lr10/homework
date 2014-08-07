package edu.oit.cst407.project;

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

	//private EditText location;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		setupVariables();
	}
	
	private void setupVariables(){
		
		// Widgets
		//location = (EditText) findViewById(R.id.locationText);
		dateText = (EditText) findViewById(R.id.dateText);
		timeText = (EditText) findViewById(R.id.timeText);
		minAgeText = (EditText) findViewById(R.id.minAge);
		maxAgeText = (EditText) findViewById(R.id.maxAge);
		skillLevelBar = (SeekBar) findViewById(R.id.skillLevelBar);
		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGender); 
		radioPitchGroup = (RadioGroup) findViewById(R.id.radioPitch); 
		radioGameTypeGroup = (RadioGroup) findViewById(R.id.radioGameType); 
		
		// Listeners
		//location.setOnClickListener(this);
		dateText.setOnClickListener(this);
		timeText.setOnClickListener(this);
		minAgeText.setOnClickListener(this);
		maxAgeText.setOnClickListener(this);
		
		skillLevelBar.setOnSeekBarChangeListener( new OnSeekBarChangeListener(){      

		    @Override       
		    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {     
		        // TODO Auto-generated method stub      

		        //t1.setTextSize(progress);
		        Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId())
		{
			//case R.id.locationText : location.setText("");
										//break;
										
			case R.id.dateText	 	: dateText.setText("");
										break;
										
			case R.id.timeText	 	: timeText.setPressed(true);
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
	
		// save info
		//
		//
		//
		/*addressText.getText().toString();
		dateText.getText().toString();
		timeText.getText().toString();
		minAgeText.getText().toString();
		maxAgeText.getText().toString();
		maxNumText.getText().toString();*/
		
		// touch notification
		Toast toast = Toast.makeText(getApplicationContext(), "Game created!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		finish();
	} 
	
	public void cancelClicked() {
		finish();
	}
}
