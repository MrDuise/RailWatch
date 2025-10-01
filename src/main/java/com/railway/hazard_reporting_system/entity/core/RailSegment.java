package com.railway.hazard_reporting_system.entity.core;

import com.railway.hazard_reporting_system.entity.enums.TrackType;
//import com.railway.hazard_reporting_system.entity.enums.SegmentStatus;
import com.railway.hazard_reporting_system.entity.supporting.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.LineString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rail_segments", indexes = {
        @Index(name = "idx_rail_segment_name", columnList = "name"),
        @Index(name = "idx_rail_segment_company", columnList = "railroad_company_id"),
        @Index(name = "idx_rail_segment_code", columnList = "segment_code")
})
public class RailSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "segment_code", unique = true)
    private String segmentCode;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "end_location")
    private String endLocation;

    @Column(name = "start_milepost")
    private Double startMilepost;

    @Column(name = "end_milepost")
    private Double endMilepost;

    @Column(name = "length_miles")
    private Double lengthMiles;

    @Column(name = "max_speed_mph")
    private Integer maxSpeedMph;

    @Enumerated(EnumType.STRING)
    @Column(name = "track_type")
    private TrackType trackType = TrackType.SINGLE_TRACK;

//    @Enumerated(EnumType.STRING)
//    private SegmentStatus status = SegmentStatus.ACTIVE;

    @Column(name = "subdivision")
    private String subdivision;

    @Column(name = "territory")
    private String territory;

    // PostGIS geometry for the rail line (optional - can be null initially)
    @Column(name = "geometry", columnDefinition = "geometry(LINESTRING,4326)")
    private LineString geometry;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "railroad_company_id", nullable = false)
    private RailroadCompany railroadCompany;

    @OneToMany(mappedBy = "railSegment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HazardReport> hazardReports = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public RailSegment() {}

    public RailSegment(String name, RailroadCompany railroadCompany) {
        this.name = name;
        this.railroadCompany = railroadCompany;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSegmentCode() { return segmentCode; }
    public void setSegmentCode(String segmentCode) { this.segmentCode = segmentCode; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public Double getStartMilepost() { return startMilepost; }
    public void setStartMilepost(Double startMilepost) { this.startMilepost = startMilepost; }

    public Double getEndMilepost() { return endMilepost; }
    public void setEndMilepost(Double endMilepost) { this.endMilepost = endMilepost; }

    public Double getLengthMiles() { return lengthMiles; }
    public void setLengthMiles(Double lengthMiles) { this.lengthMiles = lengthMiles; }

    public Integer getMaxSpeedMph() { return maxSpeedMph; }
    public void setMaxSpeedMph(Integer maxSpeedMph) { this.maxSpeedMph = maxSpeedMph; }

    public TrackType getTrackType() { return trackType; }
    public void setTrackType(TrackType trackType) { this.trackType = trackType; }

    //public SegmentStatus getStatus() { return status; }
    //public void setStatus(SegmentStatus status) { this.status = status; }

    public String getSubdivision() { return subdivision; }
    public void setSubdivision(String subdivision) { this.subdivision = subdivision; }

    public String getTerritory() { return territory; }
    public void setTerritory(String territory) { this.territory = territory; }

    public LineString getGeometry() { return geometry; }
    public void setGeometry(LineString geometry) { this.geometry = geometry; }

    public RailroadCompany getRailroadCompany() { return railroadCompany; }
    public void setRailroadCompany(RailroadCompany railroadCompany) { this.railroadCompany = railroadCompany; }

    public Set<HazardReport> getHazardReports() { return hazardReports; }
    public void setHazardReports(Set<HazardReport> hazardReports) { this.hazardReports = hazardReports; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
