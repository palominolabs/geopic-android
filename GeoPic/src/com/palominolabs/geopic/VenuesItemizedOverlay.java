package com.palominolabs.geopic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

public class VenuesItemizedOverlay extends ItemizedOverlay<VenueOverlayItem> {

	private List<Venue> venues;

	public VenuesItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		this.venues = Collections.emptyList();
		setVenues(new LinkedList<Venue>());
	}

	@Override
	protected VenueOverlayItem createItem(int i) {
		synchronized (venues) {
			return new VenueOverlayItem(venues.get(i));
		}
	}

	@Override
	public int size() {
		synchronized (venues) {
			return venues.size();
		}
	}

	public void setVenues(List<Venue> venues) {
		synchronized (this.venues) {
			this.venues = venues;
		}
		populate();
		setLastFocusedIndex(-1);
	}

}
