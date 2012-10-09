package com.palominolabs.geopic;

import android.app.Activity;
import android.os.Bundle;

public class VenueDetailsActivity extends Activity {

	private Venue venue;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        
        venue = (Venue) getIntent().getExtras().getSerializable("venue");
        
        setTitle(venue.getName());
        
        Trace.debug("Showing Venue: " + venue);
    }

}
