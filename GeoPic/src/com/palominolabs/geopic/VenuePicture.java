package com.palominolabs.geopic;

import com.stackmob.sdk.api.StackMobFile;
import com.stackmob.sdk.model.StackMobModel;

public class VenuePicture extends StackMobModel {

	private String foursquare_id;
	private String venue_name;
	public StackMobFile picture;

	public VenuePicture() {
		super(VenuePicture.class);

	}

	public VenuePicture(String foursquare_id, String venue_name,
			StackMobFile picture) {
		this();

		this.foursquare_id = foursquare_id;
		this.venue_name = venue_name;
		this.picture = picture;
	}

	public String getFoursquareId() {
		return foursquare_id;
	}

	public String getVenueName() {
		return venue_name;
	}

	public StackMobFile getPicture() {
		return picture;
	}
}
