package br.com.busaojp.rotamaps;

import java.io.Serializable;

public class Posicao implements Serializable {

	private static final long serialVersionUID = 2782959726906079539L;
	private double latitude;
	private double longitude;
	
	public Posicao(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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
}
