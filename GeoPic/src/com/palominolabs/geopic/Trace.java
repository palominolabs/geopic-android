package com.palominolabs.geopic;

import android.util.Log;

public final class Trace {

	private Trace() {}
	
	private static final String TAG = "geopic";
	
	private static boolean isLoggingEnabled() {
		return BuildConfig.DEBUG;
	}
	
	public static void debug(String msg) {
		if (!isLoggingEnabled()) {
			return;
		}
		
		Log.d(TAG, msg);
		
	}
	
}
