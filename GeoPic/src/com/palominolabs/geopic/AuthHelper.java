package com.palominolabs.geopic;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public final class AuthHelper {
	
	private static final String SHARED_PREFS_NAME = "geopic";
	private static final String USER_IDENTIFIER_KEY = "user_identifier";

	
	public static void withLoggedInUser(final Context context, final Callback callback) {
		final String userIdentifier = getUserIdentifier(context);
		
		if (StackMob.getStackMob().isLoggedIn()) {
			callback.call(userIdentifier);
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", getUserIdentifier(context));
		params.put("password", getUserIdentifier(context));

		StackMob.getStackMob().login(params, new StackMobCallback() {

			@Override
			public void success(String userJson) {
				try {
					JSONObject user = new JSONObject(userJson);
					callback.call(user.getString("sm_owner"));
				} catch (JSONException e) {
				}

			}

			@Override
			public void failure(StackMobException e) {
				User user = new User(userIdentifier, userIdentifier);
				user.save(new StackMobCallback() {

					@Override
					public void success(String arg0) {
						AuthHelper.withLoggedInUser(context, callback);
					}

					@Override
					public void failure(StackMobException e) {
					}
				});
			}

		});
	}

	private static String getUserIdentifier(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
		String userIdentifier = sharedPreferences.getString(USER_IDENTIFIER_KEY, null);
		
		if (userIdentifier != null) {
			return userIdentifier;
		}
		
		SecureRandom secureRandom = new SecureRandom();
		byte bytes[] = new byte[64];
		secureRandom.nextBytes(bytes);
		userIdentifier = Base64.encodeToString(bytes, Base64.DEFAULT);
		
		sharedPreferences.edit().putString(USER_IDENTIFIER_KEY, userIdentifier).commit();
		
		return userIdentifier;
	}

	
	public interface Callback {
		void call(String userId);
	}

}
