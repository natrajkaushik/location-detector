package com.detector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * POJO object for Location Data
 */
public class LocationData {
	private double latitude;
	private double longitude;

	private float accuracy;
	private float bearing;
	private float speed;
	private float time;

	public LocationData() {

	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Latitude: " + latitude + ", ");
		sb.append("Longitude: " + longitude + ", ");
		sb.append("Accuracy: " + accuracy + ", ");
		sb.append("Bearing: " + bearing + ", ");
		sb.append("Speed: " + speed + ", ");
		sb.append("Time: " + time + "\n");
		return sb.toString();
	}



	/**
	 * @return serialized byte representation of object
	 */
	public byte[] getBytes() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] result = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			result = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @param data byte representation of LocationData object
	 * @return LocationData object
	 */
	public static LocationData getFromBytes(byte[] data){
		if(data == null || data.length == 0){
			return null;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		
		LocationData result = null;
		Object o = null;
		
		try {
		  in = new ObjectInputStream(bis);
		  o = in.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return (LocationData) o;
	}

	public LocationData(double latitude, double longitude, float accuracy,
			float bearing, float speed, float time) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.bearing = bearing;
		this.speed = speed;
		this.time = time;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public float getBearing() {
		return bearing;
	}

	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

}
