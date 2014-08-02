package edu.oit.cst407.project;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationListener {

	public GoogleMap googleMap;
	
	private LatLng myPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
				
		switch(item.getItemId())
		{
			case R.id.action_newgame :
				newgameClicked();
				return true;
			
			case R.id.action_settings :
				settingsClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void newgameClicked() {
	    // do something
		Intent gameIntent = new Intent(this, GameActivity.class);
		startActivity(gameIntent);
	} 
	
	public void settingsClicked() {
	    // do something
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	} 
	
}


