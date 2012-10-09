package com.palominolabs.geopic;

import java.io.Serializable;

public class Venue implements Serializable {
	private static final long serialVersionUID = -3506822728367094122L;

	private final String name;
	private final GeoPointWrapper location;
	private final String category;
	private final int checkins;
	private final String formattedPhone;
	private final String foursquareId;
	private final String url;

	public Venue(String foursquareId, String name, GeoPointWrapper location,
			String category, int checkins, String formattedPhone, String url) {
		this.foursquareId = foursquareId;
		this.name = name;
		this.location = location;
		this.category = category;
		this.checkins = checkins;
		this.formattedPhone = formattedPhone;
		this.url = url;
	}

	public GeoPointWrapper getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getFormattedPhone() {
		return formattedPhone;
	}

	public int getCheckins() {
		return checkins;
	}

	public String getFoursquareId() {
		return foursquareId;
	}

	public String getUrl() {
		return url;
	}

}
