package edu.oit.cst407.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.activity_main_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		
		switch(item.getItemId())
		{
			case R.id.action_newgame :
				newgameClicked();
				break;
			
			case R.id.action_settings :
				settingsClicked(); 
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void newgameClicked() {
	    // do something
	} 
	
	public void settingsClicked() {
	    // do something
	} 
	
}


