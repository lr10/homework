package edu.oit.cst407.project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationListener {

	public GoogleMap googleMap;
	private final LatLng LOCATION_OIT = new LatLng(45.321722, -122.766344);
	private MarkerOptions markerOptions = new MarkerOptions();
	private LatLng latLng;
	public DBAdapter myDb;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "onCreate()");
		
		setUpMapIfNeeded();
		
		displayToast();
		
		openDB();
		
		// IF USER DOES NOT CLICK SAVE IN GAMEACTIVITY, DO NOT ADD MARKER TO MAP
        // OR IF NO INFO IS RETURNED FROM GAMEACTIVITY?
		displayMarkers();
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("MainActivity", "onStart()");
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		Log.d("MainActivity", "onResume()");
		
		setUpMapIfNeeded();
		
		openDB();
		
		// IF USER DOES NOT CLICK SAVE IN GAMEACTIVITY, DO NOT ADD MARKER TO MAP
        // OR IF NO INFO IS RETURNED FROM GAMEACTIVITY?
		displayMarkers();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MainActivity", "onPause()");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("MainActivity", "onStop()");
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("MainActivity", "onRestart()");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("MainActivity", "onDestroy()");
		closeDB();

		//this.deleteDatabase("MyDb");
	}
	
	private void setUpMapIfNeeded() {
		
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
        	
            // Try to obtain the map from the SupportMapFragment.
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.setMyLocationEnabled(true);
           
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
            
            	// Setting map type to Normal
            	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            	
        		// Animating to OIT
        		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_OIT, 15));
        		
        		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
        			
        	        @Override
        	        public void onMapLongClick(LatLng latLng) {

        	            // Setting the position for the marker
        	            markerOptions.position(latLng);

        	            // Animating to the touched position
        	            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        	            // Open GameActivity for user to create game
        	            newgameClicked(latLng.latitude, latLng.longitude);
        	        }
        	    });
                
            }
        }
    }

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	private void closeDB() {
		myDb.close();
	}
	
	// Display all markers onto map
	private void displayMarkers() {
			
		Cursor cursor = myDb.getAllRows();
		
		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				double lat = cursor.getDouble(DBAdapter.COL_LATITUDE);
				double lng = cursor.getDouble(DBAdapter.COL_LONGITUDE);
				String date = cursor.getString(DBAdapter.COL_DATE);
				String time = cursor.getString(DBAdapter.COL_TIME);
				
				latLng = new LatLng(lat, lng);
				
				addMarkers(latLng, date, time);

			} while(cursor.moveToNext());		
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
	}
	
	private void addMarkers(LatLng latLng, String date, String time){
		
		markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        markerOptions.title("Date: " + date).snippet("Time: " + time);
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
			case R.id.action_settings :
				settingsClicked(); 
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void displayToast() {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), "Press and hold a location on the map to create a new game.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	
	public void newgameClicked(double latitude, double longitude) {
	    // do something
		Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
		gameIntent.putExtra("EXTRA_LATITUDE",latitude);
		gameIntent.putExtra("EXTRA_LONGITUDE", longitude);
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


