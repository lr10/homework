package edu.oit.cst407.project;

import java.util.Locale;

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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class implements the user's first interaction with the Playmaker app.
 * @author Leander Rodriguez
 * @version 1.0
 *
 */
public class MainActivity extends Activity implements LocationListener {

	public String deviceLang = Locale.getDefault().getDisplayLanguage();
	public GoogleMap googleMap;
	private final LatLng LOCATION_OIT = new LatLng(45.321722, -122.766344);
	private MarkerOptions markerOptions = new MarkerOptions();
	private LatLng latLng;
	public DBAdapter myDb;
	
	/**
	 * This method creates and populates the map that the user will 
	 * interact with and instructs the user on how to interact with
	 * map.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "onCreate()");
		
		setUpMapIfNeeded();
		
		displayToast();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("MainActivity", "onStart()");
	}
	
	/**
	 * This method recreates the map and populates it with
	 * map markers. The map markers carry information pulled
	 * from the SQLite database. 
	 * 
	 * {@inheritDoc}
	 */
	@Override 
	protected void onResume() {
		super.onResume();
		Log.d("MainActivity", "onResume()");
		
		setUpMapIfNeeded();
		
		openDB();
		
		displayMarkers();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MainActivity", "onPause()");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("MainActivity", "onStop()");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("MainActivity", "onRestart()");
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
		Log.d("MainActivity", "onDestroy()");
		closeDB();

		//this.deleteDatabase("MyDb");
	}
	
	/**
	 * This method sets up the app's main google map and animates
	 * to the user's current location. It also introduces
	 * a map click listener, that allows the user to create a new
	 * game by pressing and holding a location on the map.
	 */
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
        		
        		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                    	
                    	// Get position of marker
                    	LatLng pos = marker.getPosition();
              
                    	double markerLat = pos.latitude;
                    	double markerLng = pos.longitude;
                    	
                    	// Use position to get row in SQLite database
                    	
                    	Cursor cursor = myDb.getAllRows();
                    	// Reset cursor to start, checking to see if there's data:
                		if (cursor.moveToFirst()) {
                			
                			do {
                				// Process the data:
                				if( markerLat == cursor.getDouble(DBAdapter.COL_LATITUDE)){
                					if(markerLng == cursor.getDouble(DBAdapter.COL_LONGITUDE)){
                						long row = cursor.getLong(DBAdapter.COL_ROWID);
                						Cursor current = myDb.getRow(row);
                                		
                						// Use row information to populate information activity and display it
                						Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                						infoIntent.putExtra("EXTRA_LATITUDE", current.getDouble(DBAdapter.COL_LATITUDE));
                						infoIntent.putExtra("EXTRA_LONGITUDE", current.getDouble(DBAdapter.COL_LONGITUDE));
                						infoIntent.putExtra("EXTRA_DATE", current.getString(DBAdapter.COL_DATE));
                						infoIntent.putExtra("EXTRA_TIME", current.getString(DBAdapter.COL_TIME));
                						infoIntent.putExtra("EXTRA_AGE_MIN", current.getString(DBAdapter.COL_MIN_AGE));
                						infoIntent.putExtra("EXTRA_AGE_MAX", current.getString(DBAdapter.COL_MAX_AGE));
                						infoIntent.putExtra("EXTRA_SKILL_LEVEL", current.getInt(DBAdapter.COL_SKILL_LEVEL));
                						infoIntent.putExtra("EXTRA_GENDER", current.getString(DBAdapter.COL_GENDER));
                						infoIntent.putExtra("EXTRA_PITCH", current.getString(DBAdapter.COL_PITCH));
                						infoIntent.putExtra("EXTRA_GAME_TYPE", current.getString(DBAdapter.COL_GAME_TYPE));
                						startActivity(infoIntent);
                	                    current.close();
                						break;
                					}
                				}

                			} while(cursor.moveToNext());		
                		}
                		
                		// Close the cursor to avoid a resource leak.
                		cursor.close();
                    }
                });
                
            }
        }
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
	 * This method displays all game markers onto map
	 */
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
	
	/**
	 * This method give each marker a position, title, and snippet of information
	 * @param latLng the latitude and longitude of the game the current marker is referring to
	 * @param date the date of the game the current marker is referring to
	 * @param time the time of the game the current marker is referring to
	 */
	private void addMarkers(LatLng latLng, String date, String time){
		
		markerOptions.position(latLng);
		
		if( deviceLang.equals("espa–ol")){
			markerOptions.title("Fecha: " + date).snippet("Hora: " + time);
		}
		else{
			markerOptions.title("Date: " + date).snippet("Time: " + time);
		}
		
		 googleMap.addMarker(markerOptions);
	}
	
	/**
	 * This method adds an action bar to the top of the application.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This method listens to when the action bar's Settings icon
	 * is selected.
	 * 
	 * {@inheritDoc}
	 */
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
	
	/**
	 * This method displays the introductory toast/message when the user
	 * first enters the app.  The toast's language is based on the device's
	 * language setting.
	 */
	private void displayToast() {
		
		String toastLang;
		
		if( deviceLang.equals("espa–ol")){
			toastLang = getString(R.string.create_game_touch_es);
		}
		else{
			toastLang = getString(R.string.create_game_touch_en);
		}
		
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), toastLang, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	
	/**
	 * This method opens a new GameActivity. The latitude and longitude are passed along
	 * to the next activity, to be placed into an SQLite Database.
	 * @param latitude the latitude of the location where the user wants to create a game
	 * @param longitude the longitude of the location where the user wants to create a game
	 */
	public void newgameClicked(double latitude, double longitude) {
	    
		Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
		gameIntent.putExtra("EXTRA_LATITUDE",latitude);
		gameIntent.putExtra("EXTRA_LONGITUDE", longitude);
		startActivity(gameIntent);
	} 
	
	/**
	 * This method manipulates the map based on the user's saved settings.
	 * 
	 * {@inheritDoc}
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	        	
	        	String mapType = data.getStringExtra("mapType");
	        	
	        	if( mapType.equals( "Normal") )
	    		{
	        		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    		}
	    		else if( mapType.equals("Hybrid") || mapType.equals("Hibrido") )
	    		{
	    			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	    		}
	    		else if( mapType.equals( "Satellite") || mapType.equals("Satelite") )
	    		{
	    			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	    		}
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
	
	/**
	 * This method opens a new SettingsActivity.
	 */
	public void settingsClicked() {
	    // do something
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivityForResult(settingsIntent, 1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	} 
	
}


