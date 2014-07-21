package edu.oit.cst407.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	private GoogleMap map;
	private final LatLng LOCATION_CURRENT = new LatLng(51.5033630,-0.1276250);
	
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
				
				return true;
			
			case R.id.action_settings :
				 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
