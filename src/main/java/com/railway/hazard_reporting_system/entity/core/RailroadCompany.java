package com.railway.hazard_reporting_system.entity.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "railroad_companies", indexes = {
        @Index(name = "idx_railroad_code", columnList = "code")
})
public class RailroadCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(unique = true, length = 10)
    private String code; // e.g., "BNSF", "UP", "CSX"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "headquarters_location")
    private String headquartersLocation;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "railroadCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> employees = new HashSet<>();

    @OneToMany(mappedBy = "railroadCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RailSegment> railSegments = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public RailroadCompany() {}

    public RailroadCompany(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeadquartersLocation() { return headquartersLocation; }
    public void setHeadquartersLocation(String headquartersLocation) { this.headquartersLocation = headquartersLocation; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Set<User> getEmployees() { return employees; }
    public void setEmployees(Set<User> employees) { this.employees = employees; }

    public Set<RailSegment> getRailSegments() { return railSegments; }
    public void setRailSegments(Set<RailSegment> railSegments) { this.railSegments = railSegments; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
