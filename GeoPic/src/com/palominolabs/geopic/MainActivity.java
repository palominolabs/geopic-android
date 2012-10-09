package com.palominolabs.geopic;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainActivity extends MapActivity implements LocationListener {

	private MapView mapView;
	private MyLocationOverlay myLocationOverlay;
	private FoursquareVenuesFetcher foursquareVenuesFetcher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		foursquareVenuesFetcher = new FoursquareVenuesFetcher(
				getString(R.string.api_key_foursquare_client_id),
				getString(R.string.api_key_foursquare_client_secret));

		mapView = (MapView) findViewById(R.id.mapview);
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();

		myLocationOverlay.enableMyLocation();

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

		myLocationOverlay.disableMyLocation();

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {
		GeoPointWrapper geoPointWrapper = new GeoPointWrapper(
				location.getLatitude(), location.getLongitude());

		mapView.getController().animateTo(geoPointWrapper.getGeoPoint());
		mapView.getController().setZoom(13);

		new LoadVenuesNearAsyncTask(foursquareVenuesFetcher)
				.execute(geoPointWrapper);
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
