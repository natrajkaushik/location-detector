package com.detector;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Service that detects the most accurate location in the background
 */
public class LocationDetectorService extends Service {

	public static final String TAG = "LOCATION_DETECTOR_SERVICE";
	private static LocationTracker LOCATION_TRACKER;
	private static StorageHelper STORAGE_HELPER;

	/**
	 * initialize the service
	 */
	public void init() {
		// Initialize the LocationTracker
		LOCATION_TRACKER = LocationTracker.getLocationHelper(this, handler);
		LOCATION_TRACKER.setupLocationDetection();
		STORAGE_HELPER = StorageHelper.getStorageHelper(this);
	}
	
	/**
	 * Handler to pass to LocationTracker
	 */
	private final Handler handler = new Handler() {
		@Override
		/**
		 * process the location data sent to service by LocationTracker
		 */
		public void handleMessage(Message msg) {
			LocationData locData = (LocationData) msg.obj;
			Log.d(TAG, "Received message from LocationTracker : \n" + locData);
			
			STORAGE_HELPER.write(locData.toString());
		}
	};

	@Override
	/**
	 * called once when the service is created
	 */
	public void onCreate() {
		Toast.makeText(this, "LocationDetectionService starting", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "LocationDetectionService is starting");

		
		init();
		STORAGE_HELPER.write("Testing");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// return null as we are not binding the service to anything
		return null;
	}

	@Override
	/**
	 * cleanly exit
	 */
	public void onDestroy() {
		LOCATION_TRACKER.stop();
		STORAGE_HELPER.stop();
		Toast.makeText(this, "LocationDetectionService exiting", Toast.LENGTH_SHORT).show();
	}
}
