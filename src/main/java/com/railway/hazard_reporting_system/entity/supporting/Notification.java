package com.railway.hazard_reporting_system.entity.supporting;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.entity.core.User;
import com.railway.hazard_reporting_system.entity.enums.NotificationStatus;
import com.railway.hazard_reporting_system.entity.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notification_user", columnList = "user_id"),
        @Index(name = "idx_notification_sent_at", columnList = "sent_at"),
        @Index(name = "idx_notification_status", columnList = "status")
})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "delivery_attempts")
    private Integer deliveryAttempts = 0;

    @Column(name = "error_message")
    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hazard_report_id")
    private HazardReport hazardReport;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Notification() {}

    public Notification(String subject, String message, NotificationType type, User user) {
        this.subject = subject;
        this.message = message;
        this.type = type;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public Integer getDeliveryAttempts() { return deliveryAttempts; }
    public void setDeliveryAttempts(Integer deliveryAttempts) { this.deliveryAttempts = deliveryAttempts; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public HazardReport getHazardReport() { return hazardReport; }
    public void setHazardReport(HazardReport hazardReport) { this.hazardReport = hazardReport; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
