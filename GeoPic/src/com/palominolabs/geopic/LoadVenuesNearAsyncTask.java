package com.palominolabs.geopic;

import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;

public class LoadVenuesNearAsyncTask extends
		AsyncTask<GeoPointWrapper, Void, List<Venue>> {

	private final FoursquareVenuesFetcher foursquareVenuesFetcher;
	private final VenuesItemizedOverlay venuesItemizedOverlay;
	private final VenuesAdapter venuesAdapter;

	public LoadVenuesNearAsyncTask(
			FoursquareVenuesFetcher foursquareVenuesFetcher,
			VenuesItemizedOverlay venuesItemizedOverlay,
			VenuesAdapter venuesAdapter) {
		this.foursquareVenuesFetcher = foursquareVenuesFetcher;
		this.venuesItemizedOverlay = venuesItemizedOverlay;
		this.venuesAdapter = venuesAdapter;
	}

	@Override
	protected List<Venue> doInBackground(GeoPointWrapper... params) {
		if (params.length != 1) {
			throw new IllegalArgumentException();
		}

		try {
			return foursquareVenuesFetcher.getVenuesNear(params[0]);
		} catch (Exception e) {
			Trace.exception(e);
			return Collections.emptyList();
		}
	}

	@Override
	protected void onPostExecute(List<Venue> result) {
		venuesItemizedOverlay.setVenues(result);
		venuesAdapter.setVenues(result);
	}

}
