package com.railway.hazard_reporting_system.entity.enums;

public enum UserRole {
    ADMIN("System Administrator"),
    RAILROAD_ADMIN("Railroad Company Administrator"),
    DISPATCHER("Train Dispatcher"),
    INSPECTOR("Track Inspector"),
    MAINTENANCE_SUPERVISOR("Maintenance Supervisor"),
    REPORTER("Hazard Reporter"),
    VIEWER("Read-Only Viewer");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
