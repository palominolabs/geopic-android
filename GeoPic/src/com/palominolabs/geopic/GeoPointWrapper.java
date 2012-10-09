package com.palominolabs.geopic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class GeoPointWrapper implements Serializable {

	private static final long serialVersionUID = 7751358956138919275L;

	private static final int TEN_E6 = 1000000;

	private GeoPoint geoPoint;

	public GeoPointWrapper(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	public GeoPointWrapper(double lat, double lon) {
		this(new GeoPoint((int) (lat * TEN_E6), (int) (lon * TEN_E6)));
	}

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	private void readObject(ObjectInputStream ois) throws IOException,
			ClassNotFoundException {
		int lat = ois.readInt();
		int lon = ois.readInt();
		geoPoint = new GeoPoint(lat, lon);
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeInt(geoPoint.getLatitudeE6());
		oos.writeInt(geoPoint.getLongitudeE6());
	}

	public double getLatitude() {
		return (double) geoPoint.getLatitudeE6() / TEN_E6;
	}

	public double getLongitude() {
		return (double) geoPoint.getLongitudeE6() / TEN_E6;
	}
}