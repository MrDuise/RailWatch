package com.railway.hazard_reporting_system.entity.enums;

public enum NotificationType {
    EMAIL("Email Notification"),
    SMS("SMS/Text Message"),
    PUSH_NOTIFICATION("Push Notification"),
    SYSTEM_ALERT("System Alert"),
    EMERGENCY_ALERT("Emergency Alert");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
