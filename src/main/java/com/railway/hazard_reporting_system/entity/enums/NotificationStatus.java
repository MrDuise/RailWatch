package com.railway.hazard_reporting_system.entity.enums;

public enum NotificationStatus {
    PENDING("Pending", "Notification is queued for delivery"),
    SENT("Sent", "Notification has been sent successfully"),
    DELIVERED("Delivered", "Notification was delivered to recipient"),
    FAILED("Failed", "Notification delivery failed"),
    CANCELLED("Cancelled", "Notification was cancelled before sending");

    private final String displayName;
    private final String description;

    NotificationStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
