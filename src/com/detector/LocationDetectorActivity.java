package com.detector;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class LocationDetectorActivity extends Activity {

	private static final String TAG = "LocationDetectionActivity";
	
	// Menu items
	private static final int START_SERVICE = 0;
	private static final int STOP_SERVICE = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Started LocationDetectorActivity");
		
		setLayoutAndTitle();
	}

	private void setLayoutAndTitle() {
		// Set up the window layout
		setContentView(R.layout.main);		
	}
	
	/**
	 * checks if a service is running in the background
	 * @param klass Class of the service being queried
	 * @return true is service is running
	 */
	private boolean isServiceRunning(Class klass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (klass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		generateMenu(menu);
		return true;
	}

	@Override
	/**
	 * For API Level >= 11, we can use invalidateOptionsMenu() instead
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		return onCreateOptionsMenu(menu);
	}

	private void generateMenu(Menu menu) {
		menu.clear();
		boolean isServiceRunning = isServiceRunning(LocationDetectorService.class);
		if (!isServiceRunning) {
			menu.add(Menu.NONE, START_SERVICE, 0, "Start Service");
		} else {
			menu.add(Menu.NONE, STOP_SERVICE, 0, "Stop Service");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case START_SERVICE:
			// start the BuzzGuardian service
			Intent intent = new Intent(this, LocationDetectorService.class);
			startService(intent);
			return true;
		case STOP_SERVICE:
			if (isServiceRunning(LocationDetectorService.class)) {
				stopService(new Intent(this, LocationDetectorService.class));
			}
			return true;
		}
		return false;
	}

}
