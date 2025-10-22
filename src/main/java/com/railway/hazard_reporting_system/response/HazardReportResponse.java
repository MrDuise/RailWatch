package com.railway.hazard_reporting_system.response;


import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.entity.enums.HazardSeverity;
import com.railway.hazard_reporting_system.entity.enums.HazardStatus;
import com.railway.hazard_reporting_system.entity.enums.HazardType;

import java.time.LocalDateTime;

/**
 * Clean response DTO for HazardReport - only includes IDs for related entities
 */
public class HazardReportResponse {

    private Long id;
    private String title;
    private String description;
    private HazardType hazardType;
    private HazardSeverity severity;
    private HazardStatus status;
    private Double latitude;
    private Double longitude;
    private Double milePost;
    private String trafficImpact;
    private String weatherConditions;
    private Integer priorityScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Related entity IDs only (not full objects!)
    private Long reporterId;
    private String reporterUsername;
    private Long railSegmentId;
    private String railSegmentName;
    private String railroadCompanyCode;
    private Long assignedToId;
    private String assignedToUsername;

    // Constructors
    public HazardReportResponse() {}

    /**
     * Factory method to convert entity to response DTO
     */
    public static HazardReportResponse fromEntity(HazardReport report) {
        HazardReportResponse response = new HazardReportResponse();
        response.setId(report.getId());
        response.setTitle(report.getTitle());
        response.setDescription(report.getDescription());
        response.setHazardType(report.getHazardType());
        response.setSeverity(report.getSeverity());
        response.setStatus(report.getStatus());
        response.setLatitude(report.getLatitude());
        response.setLongitude(report.getLongitude());
        response.setMilePost(report.getMilePost());
        response.setTrafficImpact(report.getTrafficImpact());
        response.setWeatherConditions(report.getWeatherConditions());
        response.setPriorityScore(report.getPriorityScore());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());

        // Set related entity info (IDs and names only)
        if (report.getReporter() != null) {
            response.setReporterId(report.getReporter().getId());
            response.setReporterUsername(report.getReporter().getUsername());
        }

        if (report.getRailSegment() != null) {
            response.setRailSegmentId(report.getRailSegment().getId());
            response.setRailSegmentName(report.getRailSegment().getName());

            if (report.getRailSegment().getRailroadCompany() != null) {
                response.setRailroadCompanyCode(report.getRailSegment().getRailroadCompany().getCode());
            }
        }

        if (report.getAssignedTo() != null) {
            response.setAssignedToId(report.getAssignedTo().getId());
            response.setAssignedToUsername(report.getAssignedTo().getUsername());
        }

        return response;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public HazardType getHazardType() { return hazardType; }
    public void setHazardType(HazardType hazardType) { this.hazardType = hazardType; }

    public HazardSeverity getSeverity() { return severity; }
    public void setSeverity(HazardSeverity severity) { this.severity = severity; }

    public HazardStatus getStatus() { return status; }
    public void setStatus(HazardStatus status) { this.status = status; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getMilePost() { return milePost; }
    public void setMilePost(Double milePost) { this.milePost = milePost; }

    public String getTrafficImpact() { return trafficImpact; }
    public void setTrafficImpact(String trafficImpact) { this.trafficImpact = trafficImpact; }

    public String getWeatherConditions() { return weatherConditions; }
    public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }

    public Integer getPriorityScore() { return priorityScore; }
    public void setPriorityScore(Integer priorityScore) { this.priorityScore = priorityScore; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getReporterId() { return reporterId; }
    public void setReporterId(Long reporterId) { this.reporterId = reporterId; }

    public String getReporterUsername() { return reporterUsername; }
    public void setReporterUsername(String reporterUsername) { this.reporterUsername = reporterUsername; }

    public Long getRailSegmentId() { return railSegmentId; }
    public void setRailSegmentId(Long railSegmentId) { this.railSegmentId = railSegmentId; }

    public String getRailSegmentName() { return railSegmentName; }
    public void setRailSegmentName(String railSegmentName) { this.railSegmentName = railSegmentName; }

    public String getRailroadCompanyCode() { return railroadCompanyCode; }
    public void setRailroadCompanyCode(String railroadCompanyCode) { this.railroadCompanyCode = railroadCompanyCode; }

    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }

    public String getAssignedToUsername() { return assignedToUsername; }
    public void setAssignedToUsername(String assignedToUsername) { this.assignedToUsername = assignedToUsername; }
}
