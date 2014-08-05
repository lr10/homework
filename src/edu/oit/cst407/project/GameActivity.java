package edu.oit.cst407.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

	private EditText addressText;
	private EditText dateText;
	private EditText timeText;
	private Button maleBtn;
	private Button femaleBtn;
	private Button coedBtn;
	private EditText minAgeText;
	private EditText maxAgeText;
	private Button indoorBtn;
	private Button outdoorBtn;
	private Button grassBtn;
	private Button turfBtn;
	private Button futsalBtn;
	private SeekBar skillLevelBar;
	private EditText maxNumText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		setupVariables();
	}
	
	private void setupVariables(){
		
		addressText = (EditText) findViewById(R.id.locationText);
		dateText = (EditText) findViewById(R.id.dateText);
		timeText = (EditText) findViewById(R.id.timeText);
		maleBtn = (Button) findViewById(R.id.maleButton);
		femaleBtn = (Button) findViewById(R.id.femaleButton);
		coedBtn = (Button) findViewById(R.id.coedButton);
		minAgeText = (EditText) findViewById(R.id.minAge);
		maxAgeText = (EditText) findViewById(R.id.maxAge);
		indoorBtn = (Button) findViewById(R.id.indoorButton);
		outdoorBtn = (Button) findViewById(R.id.outdoorButton);
		grassBtn = (Button) findViewById(R.id.grassButton);
		turfBtn = (Button) findViewById(R.id.turfButton);
		futsalBtn = (Button) findViewById(R.id.futsalButton);
		skillLevelBar = (SeekBar) findViewById(R.id.skillLevelBar);
		maxNumText = (EditText) findViewById(R.id.maxNum);
		
		/*addressText.setOnClickListener(this);
		dateText.setOnClickListener(this);
		timeText.setOnClickListener(this);
		maleBtn.setOnClickListener(this);
		femaleBtn.setOnClickListener(this);
		coedBtn.setOnClickListener(this);
		minAgeText.setOnClickListener(this);
		maxAgeText.setOnClickListener(this);
		indoorBtn.setOnClickListener(this);
		outdoorBtn.setOnClickListener(this);
		grassBtn.setOnClickListener(this);
		turfBtn.setOnClickListener(this);
		futsalBtn.setOnClickListener(this);
		skillLevelBar.setOnClickListener(this);
		maxNumText.setOnClickListener(this);*/
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
	
		// save info and add marker at address location entered by user
		//
		//
		//
		
		
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
