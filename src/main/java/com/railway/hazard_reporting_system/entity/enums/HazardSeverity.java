package com.railway.hazard_reporting_system.entity.enums;

public enum HazardSeverity {
    LOW(1, "Low Priority", "Minor issue, no immediate safety concern"),
    MEDIUM(2, "Medium Priority", "Moderate issue, monitor closely"),
    HIGH(3, "High Priority", "Significant safety concern, prompt action required"),
    CRITICAL(4, "Critical Priority", "Severe safety hazard, immediate action required"),
    EMERGENCY(5, "Emergency", "Life-threatening situation, stop all traffic immediately");

    private final int priorityLevel;
    private final String displayName;
    private final String description;

    HazardSeverity(int priorityLevel, String displayName, String description) {
        this.priorityLevel = priorityLevel;
        this.displayName = displayName;
        this.description = description;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
