package com.palominolabs.geopic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

public class FoursquareVenuesFetcher {

	private static final int MAX_RESULTS = 10;
	
	private final String clientId;
	private final String clientSecret;

	public FoursquareVenuesFetcher(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public List<Venue> getVenuesNear(GeoPointWrapper geoPointWrapper)
			throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(prepareQuery(geoPointWrapper));
		HttpResponse httpResponse = client.execute(get);
		String body = IOUtils.toString(httpResponse.getEntity().getContent());
		return extractVenuesFromFoursquareReponse(body);
	}

	String prepareQuery(GeoPointWrapper geoPointWrapper) {
		return Uri
				.parse("https://api.foursquare.com/v2/venues/explore")
				.buildUpon()
				.appendQueryParameter("v", "20121002")
				.appendQueryParameter("limit", String.valueOf(MAX_RESULTS))
				.appendQueryParameter("client_id", clientId)
				.appendQueryParameter("client_secret", clientSecret)
				.appendQueryParameter(
						"ll",
						geoPointWrapper.getLatitude() + ","
								+ geoPointWrapper.getLongitude()).build().toString();

	}

	private static List<Venue> extractVenuesFromFoursquareReponse(String body)
			throws JSONException {
		JSONObject parsedBody = new JSONObject(body);
		JSONObject response = parsedBody.getJSONObject("response");
		JSONArray groups = response.getJSONArray("groups");
		JSONObject firstGroup = groups.getJSONObject(0);
		JSONArray items = firstGroup.getJSONArray("items");

		List<Venue> result = new ArrayList<Venue>();

		for (int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			JSONObject venue = item.getJSONObject("venue");
			result.add(translateVenue(venue));
		}

		return result;
	}

	static Venue translateVenue(JSONObject venue) throws JSONException {
		String foursquareId = venue.getString("id");
		String name = venue.getString("name");
		JSONObject location = venue.getJSONObject("location");
		double lat = location.getDouble("lat");
		double lon = location.getDouble("lng");
		String url = venue.optString("url", null);

		JSONArray categories = venue.getJSONArray("categories");
		JSONObject category = categories.getJSONObject(0);
		String categoryName = category.getString("shortName");

		JSONObject stats = venue.getJSONObject("stats");
		int checkins = stats.getInt("checkinsCount");

		JSONObject contact = venue.getJSONObject("contact");
		String formattedPhone = contact.optString("formattedPhone");

		return new Venue(foursquareId, name, new GeoPointWrapper(lat, lon),
				categoryName, checkins, formattedPhone, url);
	}

}
