package com.palominolabs.geopic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VenueDetailsActivity extends Activity {

	private Venue venue;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        
        venue = (Venue) getIntent().getExtras().getSerializable("venue");
        
        setTitle(venue.getName());
        
        TextView venueNameView = (TextView) findViewById(R.id.venue_details_nameValue);
        venueNameView.setText(venue.getName());
        
        TextView categoryView = (TextView) findViewById(R.id.venue_details_categoryValue);
        categoryView.setText(venue.getCategory());
        
        TextView checkinsView = (TextView) findViewById(R.id.venue_details_checkinsValue);
        checkinsView.setText(String.valueOf(venue.getCheckins()));
        
        TextView formattedPhoneView = (TextView) findViewById(R.id.venue_details_formattedPhoneValue);
        formattedPhoneView.setText(venue.getFormattedPhone());
        
    }

}
