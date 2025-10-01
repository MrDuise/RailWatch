package com.railway.hazard_reporting_system.entity.enums;

public enum HazardStatus {
    REPORTED("Reported", "Hazard has been reported but not yet reviewed"),
    ACKNOWLEDGED("Acknowledged", "Hazard report has been acknowledged by dispatcher"),
    INVESTIGATING("Investigating", "Hazard is being investigated"),
    IN_PROGRESS("In Progress", "Work is in progress to resolve the hazard"),
    PENDING_VERIFICATION("Pending Verification", "Waiting for verification that hazard is resolved"),
    RESOLVED("Resolved", "Hazard has been successfully resolved"),
    CLOSED("Closed", "Hazard report is closed and archived"),
    CANCELLED("Cancelled", "Hazard report was cancelled (false alarm, duplicate, etc.)"),
    ESCALATED("Escalated", "Hazard has been escalated to higher authority");

    private final String displayName;
    private final String description;

    HazardStatus(String displayName, String description) {
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
