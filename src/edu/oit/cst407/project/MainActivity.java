package edu.oit.cst407.project;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
	static final int MAP_REQUEST = 1;
	
	public DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setUpMapIfNeeded();
		
		Toast toast = Toast.makeText(getApplicationContext(), "Press and hold a location on the map to create a new game.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		
		openDB();
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
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
        		//googleMap.addMarker(new MarkerOptions().position(LOCATION_OIT).title("Oregon Institute of Technology"));
        		
        		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
        			
        	        @Override
        	        public void onMapLongClick(LatLng latLng) {

        	            // Creating a marker
        	            MarkerOptions markerOptions = new MarkerOptions();

        	            // Setting the position for the marker
        	            markerOptions.position(latLng);

        	            /*
        	            // Setting the title for the marker.
        	            // This will be displayed on taping the marker
        	            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        	            */
        	            
        	            // Get address based on latitude and longitude
        	            String loc = getAddress(latLng.latitude, latLng.longitude);
        	            
        	            // Setting the title for the marker.
        	            // This will be displayed on taping the marker
        	            markerOptions.title(loc);
        	            
        	            // Clears the previously touched position
        	            googleMap.clear();

        	            // Animating to the touched position
        	            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        	            // Open GameActivity for user to create game
        	            newgameClicked();
        	            
        	            // IF USER DOES NOT CLICK SAVE IN GAMEACTIVITY, DO NOT ADD MARKER TO MAP
        	            // OR IF NO INFO IS RETURNED FROM GAMEACTIVITY?
        	            //
        	            // Placing a marker on the touched position
        	            googleMap.addMarker(markerOptions);
        	            
        	            
        	        }
        	    });
                
            }
        }
    }
	
	private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
	            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
	            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
	            
	            if (addresses.size() > 0) {
	                Address address = addresses.get(0);
	                
	                String locality=address.getLocality();
		            String city=address.getCountryName();
		            String region_code=address.getCountryCode();
		            String zipcode=address.getPostalCode();
		            
	                result.append(locality+" ");
		            result.append(city+" "+ region_code+" ");
		            result.append(zipcode);
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
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
			/*case R.id.action_newgame :
				newgameClicked();
				return true;*/
			
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


