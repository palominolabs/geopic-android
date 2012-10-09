package com.palominolabs.geopic;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.MapActivity;

public class MainActivity extends MapActivity implements LocationListener {
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		final int MIN_UPDATE_INTERVAL_MS = 5 * 1000;
		final int MIN_UPDATE_DISTANCE_METERS = 100;

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MIN_UPDATE_INTERVAL_MS, MIN_UPDATE_DISTANCE_METERS, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {
		Log.d("geopic", "Location changed: " + location);
	}


	public void onProviderDisabled(String provider) {
	}


	public void onProviderEnabled(String provider) {
	}


	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
