package com.palominolabs.geopic;

import junit.framework.TestCase;

import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class FoursquareVenuesFetcherTest extends TestCase {
	public void testTranslateVenue_ReturnsCorrectVenue_ForStandardVenue()
			throws Exception {
		Venue v = FoursquareVenuesFetcher
				.translateVenue(new JSONObject(
						"{\n"
								+ "                            \"categories\": [\n"
								+ "                                {\n"
								+ "                                    \"icon\": {\n"
								+ "                                        \"prefix\": \"https://foursquare.com/img/categories_v2/food/default_\", \n"
								+ "                                        \"suffix\": \".png\"\n"
								+ "                                    }, \n"
								+ "                                    \"id\": \"4bf58dd8d48988d14e941735\", \n"
								+ "                                    \"name\": \"American Restaurant\", \n"
								+ "                                    \"pluralName\": \"American Restaurants\", \n"
								+ "                                    \"primary\": true, \n"
								+ "                                    \"shortName\": \"American\"\n"
								+ "                                }\n"
								+ "                            ], \n"
								+ "                            \"contact\": {\n"
								+ "                                \"formattedPhone\": \"(650) 365-6297\", \n"
								+ "                                \"phone\": \"6503656297\"\n"
								+ "                            }, \n"
								+ "                            \"id\": \"4abc3476f964a520c08620e3\", \n"
								+ "                            \"likes\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"location\": {\n"
								+ "                                \"address\": \"1001 El Camino Real\", \n"
								+ "                                \"cc\": \"US\", \n"
								+ "                                \"city\": \"Redwood City\", \n"
								+ "                                \"country\": \"United States\", \n"
								+ "                                \"crossStreet\": \"at Jefferson Ave\", \n"
								+ "                                \"distance\": 1468, \n"
								+ "                                \"lat\": 37.48442790854927, \n"
								+ "                                \"lng\": -122.2325348854065, \n"
								+ "                                \"postalCode\": \"94063\", \n"
								+ "                                \"state\": \"CA\"\n"
								+ "                            }, \n"
								+ "                            \"menu\": {\n"
								+ "                                \"mobileUrl\": \"https://foursquare.com/v/4abc3476f964a520c08620e3/device_menu\", \n"
								+ "                                \"type\": \"foodAndBeverage\", \n"
								+ "                                \"url\": \"https://foursquare.com/v/maxs-restaurant/4abc3476f964a520c08620e3/menu\"\n"
								+ "                            }, \n"
								+ "                            \"name\": \"Max's Restaurant\", \n"
								+ "                            \"photos\": {\n"
								+ "                                \"count\": 9, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"specials\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"items\": []\n"
								+ "                            }, \n"
								+ "                            \"stats\": {\n"
								+ "                                \"checkinsCount\": 796, \n"
								+ "                                \"tipCount\": 12, \n"
								+ "                                \"usersCount\": 465\n"
								+ "                            }, \n"
								+ "                            \"url\": \"http://www.maxsworld.com/\", \n"
								+ "                            \"verified\": false\n"
								+ "                        }"));

		assertEquals("American", v.getCategory());
		assertEquals(796, v.getCheckins());
		assertEquals("(650) 365-6297", v.getFormattedPhone());
		assertEquals("4abc3476f964a520c08620e3", v.getFoursquareId());
		assertEquals("Max's Restaurant", v.getName());
		assertEquals("http://www.maxsworld.com/", v.getUrl());
		assertEquals(new GeoPoint(37484427, -122232534), v.getLocation()
				.getGeoPoint());
	}

	public void testTranslateVenue_ReturnsCorrectVenue_IfNoPhone()
			throws Exception {
		Venue v = FoursquareVenuesFetcher
				.translateVenue(new JSONObject(
						"{\n"
								+ "                            \"categories\": [\n"
								+ "                                {\n"
								+ "                                    \"icon\": {\n"
								+ "                                        \"prefix\": \"https://foursquare.com/img/categories_v2/food/default_\", \n"
								+ "                                        \"suffix\": \".png\"\n"
								+ "                                    }, \n"
								+ "                                    \"id\": \"4bf58dd8d48988d14e941735\", \n"
								+ "                                    \"name\": \"American Restaurant\", \n"
								+ "                                    \"pluralName\": \"American Restaurants\", \n"
								+ "                                    \"primary\": true, \n"
								+ "                                    \"shortName\": \"American\"\n"
								+ "                                }\n"
								+ "                            ], \n"
								+ "                            \"contact\": {\n"
								+ "                                \"phone\": \"6503656297\"\n"
								+ "                            }, \n"
								+ "                            \"id\": \"4abc3476f964a520c08620e3\", \n"
								+ "                            \"likes\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"location\": {\n"
								+ "                                \"address\": \"1001 El Camino Real\", \n"
								+ "                                \"cc\": \"US\", \n"
								+ "                                \"city\": \"Redwood City\", \n"
								+ "                                \"country\": \"United States\", \n"
								+ "                                \"crossStreet\": \"at Jefferson Ave\", \n"
								+ "                                \"distance\": 1468, \n"
								+ "                                \"lat\": 37.48442790854927, \n"
								+ "                                \"lng\": -122.2325348854065, \n"
								+ "                                \"postalCode\": \"94063\", \n"
								+ "                                \"state\": \"CA\"\n"
								+ "                            }, \n"
								+ "                            \"menu\": {\n"
								+ "                                \"mobileUrl\": \"https://foursquare.com/v/4abc3476f964a520c08620e3/device_menu\", \n"
								+ "                                \"type\": \"foodAndBeverage\", \n"
								+ "                                \"url\": \"https://foursquare.com/v/maxs-restaurant/4abc3476f964a520c08620e3/menu\"\n"
								+ "                            }, \n"
								+ "                            \"name\": \"Max's Restaurant\", \n"
								+ "                            \"photos\": {\n"
								+ "                                \"count\": 9, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"specials\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"items\": []\n"
								+ "                            }, \n"
								+ "                            \"stats\": {\n"
								+ "                                \"checkinsCount\": 796, \n"
								+ "                                \"tipCount\": 12, \n"
								+ "                                \"usersCount\": 465\n"
								+ "                            }, \n"
								+ "                            \"url\": \"http://www.maxsworld.com/\", \n"
								+ "                            \"verified\": false\n"
								+ "                        }"));

		assertEquals("", v.getFormattedPhone());
	}

	public void testTranslateVenue_ReturnsCorrectVenue_IfNoURL()
			throws Exception {
		Venue v = FoursquareVenuesFetcher
				.translateVenue(new JSONObject(
						"{\n"
								+ "                            \"categories\": [\n"
								+ "                                {\n"
								+ "                                    \"icon\": {\n"
								+ "                                        \"prefix\": \"https://foursquare.com/img/categories_v2/food/default_\", \n"
								+ "                                        \"suffix\": \".png\"\n"
								+ "                                    }, \n"
								+ "                                    \"id\": \"4bf58dd8d48988d14e941735\", \n"
								+ "                                    \"name\": \"American Restaurant\", \n"
								+ "                                    \"pluralName\": \"American Restaurants\", \n"
								+ "                                    \"primary\": true, \n"
								+ "                                    \"shortName\": \"American\"\n"
								+ "                                }\n"
								+ "                            ], \n"
								+ "                            \"contact\": {\n"
								+ "                                \"formattedPhone\": \"(650) 365-6297\", \n"
								+ "                                \"phone\": \"6503656297\"\n"
								+ "                            }, \n"
								+ "                            \"id\": \"4abc3476f964a520c08620e3\", \n"
								+ "                            \"likes\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"location\": {\n"
								+ "                                \"address\": \"1001 El Camino Real\", \n"
								+ "                                \"cc\": \"US\", \n"
								+ "                                \"city\": \"Redwood City\", \n"
								+ "                                \"country\": \"United States\", \n"
								+ "                                \"crossStreet\": \"at Jefferson Ave\", \n"
								+ "                                \"distance\": 1468, \n"
								+ "                                \"lat\": 37.48442790854927, \n"
								+ "                                \"lng\": -122.2325348854065, \n"
								+ "                                \"postalCode\": \"94063\", \n"
								+ "                                \"state\": \"CA\"\n"
								+ "                            }, \n"
								+ "                            \"menu\": {\n"
								+ "                                \"mobileUrl\": \"https://foursquare.com/v/4abc3476f964a520c08620e3/device_menu\", \n"
								+ "                                \"type\": \"foodAndBeverage\", \n"
								+ "                                \"url\": \"https://foursquare.com/v/maxs-restaurant/4abc3476f964a520c08620e3/menu\"\n"
								+ "                            }, \n"
								+ "                            \"name\": \"Max's Restaurant\", \n"
								+ "                            \"photos\": {\n"
								+ "                                \"count\": 9, \n"
								+ "                                \"groups\": []\n"
								+ "                            }, \n"
								+ "                            \"specials\": {\n"
								+ "                                \"count\": 0, \n"
								+ "                                \"items\": []\n"
								+ "                            }, \n"
								+ "                            \"stats\": {\n"
								+ "                                \"checkinsCount\": 796, \n"
								+ "                                \"tipCount\": 12, \n"
								+ "                                \"usersCount\": 465\n"
								+ "                            }, \n"
								+ "                            \"verified\": false\n"
								+ "                        }"));

		assertNull(v.getUrl());
	}

}
