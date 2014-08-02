package edu.oit.cst407.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

public class SettingsActivity extends Activity {

	private Button normalBtn;
	private Button hybridBtn;
	private Button satelliteBtn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		normalBtn = (Button) findViewById(R.id.normalButton);
		hybridBtn = (Button) findViewById(R.id.hybridButton);
		satelliteBtn = (Button) findViewById(R.id.satelliteButton);
		
		normalBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        // Do something in response to button click
		    	//googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    }
		});
		hybridBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        // Do something in response to button click
		    	//googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		    }
		});
		satelliteBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        // Do something in response to button click
		    	//googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		    }
		});
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
	    // do something
		//Intent gameIntent = new Intent(this, GameActivity.class);
		//startActivity(gameIntent);
		
		// save info and add marker at address location
		
		// touch notification
		Context context = getApplicationContext();
		CharSequence text = "Preferences saved.";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		finish();
	} 
	
	public void cancelClicked() {
	    // do something
		finish();
	} 
}
