package org.soronc.citydata;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.common.base.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	private double latitude;
	private double longitude;
	
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
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("latitude", latitude).add("longitude", longitude).toString();
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(latitude, longitude);
	}
	@Override
	public boolean equals(Object object) {
		if (object instanceof Location) {
			Location that = (Location) object;
			return Objects.equal(this.latitude, that.latitude) && Objects.equal(this.longitude, that.longitude);
		}
		return false;
	}
	
	
	
	
}
