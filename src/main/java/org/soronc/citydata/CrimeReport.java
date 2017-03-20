package org.soronc.citydata;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrimeReport implements Comparable<CrimeReport>{
	
	public static final TypeReference<List<CrimeReport>> LIST_TYPE = new TypeReference<List<CrimeReport>>() {};
	
	@JsonProperty("date_occ")
	private Date occurred;
	
	@JsonProperty("date_rptd")
	private Date reported;
	
	@JsonProperty("crm_cd_desc")
	private String description;
	
	@JsonProperty("crm_cd")
	private String crimeCode;
	
	@JsonProperty("location")
	private String locationName;
	
	@JsonProperty("status_desc")
	private String status;
	
	@JsonProperty("location_1")
	private Location location;
	
	@JsonProperty("rd")
	private String reportingDistrict;
	
	@JsonProperty("area_name")
	private String areaName;
	
	@JsonProperty("time_occ")
	private String timeOccurred;
	
	@JsonProperty("area")
	private String area;

	public Date getOccurred() {
		return occurred;
	}

	public void setOccurred(Date occurred) {
		this.occurred = occurred;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getReported() {
		return reported;
	}

	public void setReported(Date reported) {
		this.reported = reported;
	}

	public String getReportingDistrict() {
		return reportingDistrict;
	}

	public void setReportingDistrict(String reportingDistrict) {
		this.reportingDistrict = reportingDistrict;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTimeOccurred() {
		return timeOccurred;
	}

	public void setTimeOccurred(String timeOccurred) {
		this.timeOccurred = timeOccurred;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
    public String getCrimeCode() {
    
        return crimeCode;
    }

    
    public void setCrimeCode(String crimeCode) {
    
        this.crimeCode = crimeCode;
    }

    @Override
	public String toString() {
		return Objects.toStringHelper(this).add("occurred", occurred).add("reported", reported)
				.add("description", description).add("locationName", locationName).add("status", status)
				.add("location", location).add("reportingDistrict", reportingDistrict).add("areaName", areaName)
				.add("timeOccurred", timeOccurred).add("area", area).toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(occurred, reported, description, locationName, status, location, reportingDistrict,
				areaName, timeOccurred, area);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CrimeReport) {
			CrimeReport that = (CrimeReport) object;
			return Objects.equal(this.occurred, that.occurred) && Objects.equal(this.reported, that.reported)
					&& Objects.equal(this.description, that.description)
					&& Objects.equal(this.locationName, that.locationName) && Objects.equal(this.status, that.status)
					&& Objects.equal(this.location, that.location)
					&& Objects.equal(this.reportingDistrict, that.reportingDistrict)
					&& Objects.equal(this.areaName, that.areaName)
					&& Objects.equal(this.timeOccurred, that.timeOccurred) && Objects.equal(this.area, that.area);
		}
		return false;
	}

    @Override
    public int compareTo(CrimeReport o) {

        return this.getOccurred().compareTo(o.getOccurred());
        
    }





}
