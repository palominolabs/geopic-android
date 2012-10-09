package com.palominolabs.geopic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

import com.google.android.maps.GeoPoint;

public class GeoPointWrapperTest extends TestCase {
	public void testCreateFromGeoPoint_SetsGeoPoint() {
		GeoPoint geoPoint = new GeoPoint(123, 456);
		GeoPointWrapper wrapper = new GeoPointWrapper(geoPoint);

		assertEquals(geoPoint, wrapper.getGeoPoint());
	}

	public void testCreateFromLatLon_SetsGeoPoint() {
		GeoPointWrapper wrapper = new GeoPointWrapper(123.456, 78.9);

		assertEquals(new GeoPoint(123456000, 78900000), wrapper.getGeoPoint());
	}

	public void testGetLatitude_ReturnsLatInDegrees() {
		GeoPointWrapper wrapper = new GeoPointWrapper(44.55, 66.789);

		assertEquals(44.55, wrapper.getLatitude());
	}

	public void testGetLongitude_ReturnsLonInDegrees() {
		GeoPointWrapper wrapper = new GeoPointWrapper(44.55, 66.789);

		assertEquals(66.789, wrapper.getLongitude());
	}

	public void testSerialization() throws Exception {
		GeoPoint gp = new GeoPoint(123, -44);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(new GeoPointWrapper(gp));
		oos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);

		GeoPointWrapper geoPointWrapper = (GeoPointWrapper) ois.readObject();

		assertEquals(gp, geoPointWrapper.getGeoPoint());
	}
}
