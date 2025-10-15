package com.railway.hazard_reporting_system.request;

import com.railway.hazard_reporting_system.entity.enums.HazardSeverity;
import com.railway.hazard_reporting_system.entity.enums.HazardType;

public class CreateHazardReportRequest {
    private String title;
    private String description;
    private HazardType hazardType;
    private HazardSeverity severity;
    private Double latitude;
    private Double longitude;
    private Double milePost;
    private String weatherConditions;
    private String trafficImpact;
    private String attachmentBase64;
    private Long reporterId; // User who is reporting

    // Constructors
    public CreateHazardReportRequest() {}

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public HazardType getHazardType() { return hazardType; }
    public void setHazardType(HazardType hazardType) { this.hazardType = hazardType; }

    public HazardSeverity getSeverity() { return severity; }
    public void setSeverity(HazardSeverity severity) { this.severity = severity; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getMilePost() { return milePost; }
    public void setMilePost(Double milePost) { this.milePost = milePost; }

    public String getWeatherConditions() { return weatherConditions; }
    public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }

    public String getTrafficImpact() { return trafficImpact; }
    public void setTrafficImpact(String trafficImpact) { this.trafficImpact = trafficImpact; }

    public String getAttachmentBase64() { return attachmentBase64; }
    public void setAttachmentBase64(String attachmentBase64) { this.attachmentBase64 = attachmentBase64; }

    public Long getReporterId() { return reporterId; }
    public void setReporterId(Long reporterId) { this.reporterId = reporterId; }
}
