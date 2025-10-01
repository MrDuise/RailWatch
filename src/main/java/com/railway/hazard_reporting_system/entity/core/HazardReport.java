package com.railway.hazard_reporting_system.entity.core;

import com.railway.hazard_reporting_system.entity.enums.HazardType;
import com.railway.hazard_reporting_system.entity.enums.HazardSeverity;
import com.railway.hazard_reporting_system.entity.enums.HazardStatus;
import com.railway.hazard_reporting_system.entity.supporting.Notification;
import com.railway.hazard_reporting_system.entity.supporting.FileAttachment;
//import com.railway.hazard_reporting_system.entity.supporting.AuditLog;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hazard_reports", indexes = {
        @Index(name = "idx_hazard_severity", columnList = "severity"),
        @Index(name = "idx_hazard_status", columnList = "status"),
        @Index(name = "idx_hazard_type", columnList = "hazard_type"),
        @Index(name = "idx_hazard_created", columnList = "created_at"),
        @Index(name = "idx_hazard_segment", columnList = "rail_segment_id"),
        @Index(name = "idx_hazard_reporter", columnList = "reporter_id")
})
public class HazardReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hazard_type")
    private HazardType hazardType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HazardSeverity severity;

    @Enumerated(EnumType.STRING)
    private HazardStatus status = HazardStatus.REPORTED;

    // PostGIS point for exact hazard location (optional)
    @Column(name = "location", columnDefinition = "geometry(POINT,4326)")
    private Point location;

    @Column(name = "mile_post")
    private Double milePost;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "estimated_repair_time_hours")
    private Integer estimatedRepairTimeHours;

    @Column(name = "actual_repair_time_hours")
    private Integer actualRepairTimeHours;

    @Column(name = "traffic_impact")
    private String trafficImpact;

    @Column(name = "weather_conditions")
    private String weatherConditions;

    @Column(name = "temperature_fahrenheit")
    private Integer temperatureFahrenheit;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "priority_score")
    private Integer priorityScore; // Calculated priority based on severity, location, etc.

    @Column(name = "external_reference_id")
    private String externalReferenceId; // Reference to external systems

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rail_segment_id", nullable = false)
    private RailSegment railSegment;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @OneToMany(mappedBy = "hazardReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notification> notifications = new HashSet<>();

    // Base64 encoded image attachment
    @Column(name = "attachment_base64", columnDefinition = "TEXT")
    private String attachmentBase64;

    //@OneToMany(mappedBy = "hazardReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Set<AuditLog> auditLogs = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public HazardReport() {}

    public HazardReport(String title, HazardType hazardType, HazardSeverity severity,
                        User reporter, RailSegment railSegment) {
        this.title = title;
        this.hazardType = hazardType;
        this.severity = severity;
        this.reporter = reporter;
        this.railSegment = railSegment;
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

    public Point getLocation() { return location; }
    public void setLocation(Point location) { this.location = location; }

    public Double getMilePost() { return milePost; }
    public void setMilePost(Double milePost) { this.milePost = milePost; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getEstimatedRepairTimeHours() { return estimatedRepairTimeHours; }
    public void setEstimatedRepairTimeHours(Integer estimatedRepairTimeHours) { this.estimatedRepairTimeHours = estimatedRepairTimeHours; }

    public Integer getActualRepairTimeHours() { return actualRepairTimeHours; }
    public void setActualRepairTimeHours(Integer actualRepairTimeHours) { this.actualRepairTimeHours = actualRepairTimeHours; }

    public String getTrafficImpact() { return trafficImpact; }
    public void setTrafficImpact(String trafficImpact) { this.trafficImpact = trafficImpact; }

    public String getWeatherConditions() { return weatherConditions; }
    public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }

    public Integer getTemperatureFahrenheit() { return temperatureFahrenheit; }
    public void setTemperatureFahrenheit(Integer temperatureFahrenheit) { this.temperatureFahrenheit = temperatureFahrenheit; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public Integer getPriorityScore() { return priorityScore; }
    public void setPriorityScore(Integer priorityScore) { this.priorityScore = priorityScore; }

    public String getExternalReferenceId() { return externalReferenceId; }
    public void setExternalReferenceId(String externalReferenceId) { this.externalReferenceId = externalReferenceId; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public RailSegment getRailSegment() { return railSegment; }
    public void setRailSegment(RailSegment railSegment) { this.railSegment = railSegment; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

    public Set<Notification> getNotifications() { return notifications; }
    public void setNotifications(Set<Notification> notifications) { this.notifications = notifications; }

    public String getAttachmentBase64() { return attachmentBase64; }
    public void setAttachmentBase64(String attachmentBase64) { this.attachmentBase64 = attachmentBase64; }


    //public Set<AuditLog> getAuditLogs() { return auditLogs; }
    //public void setAuditLogs(Set<AuditLog> auditLogs) { this.auditLogs = auditLogs; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
