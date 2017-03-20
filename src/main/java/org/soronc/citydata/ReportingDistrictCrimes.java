package org.soronc.citydata;

import java.util.Collection;

public class ReportingDistrictCrimes implements Comparable<ReportingDistrictCrimes>{

    private String reportingDistrict;
    private Collection<CrimeReport> reports;
    
    public String getReportingDistrict() {
    
        return reportingDistrict;
    }
    
    public void setReportingDistrict(String reportingDistrict) {
    
        this.reportingDistrict = reportingDistrict;
    }
    
    public Collection<CrimeReport> getReports() {
    
        return reports;
    }
    
    public void setReports(Collection<CrimeReport> reports) {
    
        this.reports = reports;
    }

    public ReportingDistrictCrimes(String reportingDistrict,
            Collection<CrimeReport> reports) {
        super();
        this.reportingDistrict = reportingDistrict;
        this.reports = reports;
    }

    @Override
    public int compareTo(ReportingDistrictCrimes o) {

        return Integer.parseInt(reportingDistrict) - Integer.parseInt(o.getReportingDistrict());
    }
    
    
}
