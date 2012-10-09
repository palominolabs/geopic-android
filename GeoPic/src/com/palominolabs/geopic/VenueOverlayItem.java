package com.palominolabs.geopic;

import com.google.android.maps.OverlayItem;

public class VenueOverlayItem extends OverlayItem {

	public VenueOverlayItem(Venue venue) {
		super(venue.getLocation().getGeoPoint(), venue.getName(), null);
	}
	
}
