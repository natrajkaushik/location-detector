package com.detector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

/**
 * manages the storing of location values into a file
 */
public class StorageHelper {

	// file name where location data is written
	private static final String DATA_FILE_NAME = "location-data.txt";
	
	private Context context;
	private static StorageHelper STORAGE_HELPER = null;
	private FileOutputStream fos;
	
	public static StorageHelper getStorageHelper(Context context){
		if(STORAGE_HELPER == null){
			return new StorageHelper(context);
		}
		return STORAGE_HELPER;
	}
	
	
	private StorageHelper(Context context) {
		this.context = context;
		init();
	}
	
	/**
	 * Initialize the outputstream to data file
	 */
	private void init(){
		try {
			File SDCARD = Environment.getExternalStorageDirectory(); 
			File dir = new File (SDCARD.getAbsolutePath() + "/LocationDetector"); 
			dir.mkdirs(); 
			
			File outputFile = File.createTempFile(DATA_FILE_NAME, "txt", dir);

			fos = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method is exposed to the LocationDetectorService to write a String to the file
	 * @param str
	 */
	public void write(String str){
		(new WriteToFile(str)).start();
	}
	
	/**
	 * Thread to write data to file
	 */
	class WriteToFile extends Thread{

		private String str;
		
		public WriteToFile(String str) {
			this.str = str;
		}
		
		@Override
		public void run() {
			try {
				synchronized(StorageHelper.this){
					// write to the file in a synchronized manner. Only one WriteToFile thread can write to the file.
					if(fos != null){
						fos.write(str.getBytes());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * close the outputstream and exit
	 */
	public void stop(){
		if(fos != null){
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
