package com.railway.hazard_reporting_system.entity.enums;

public enum TrackType {
    SINGLE_TRACK("Single Track", "Single track mainline"),
    DOUBLE_TRACK("Double Track", "Double track mainline"),
    MULTIPLE_TRACK("Multiple Track", "Three or more parallel tracks"),
    YARD_TRACK("Yard Track", "Railroad yard or classification track"),
    SIDING_TRACK("Siding Track", "Siding or passing track"),
    INDUSTRIAL_TRACK("Industrial Track", "Industrial or spur track"),
    BRANCH_LINE("Branch Line", "Branch line or secondary track");

    private final String displayName;
    private final String description;

    TrackType(String displayName, String description) {
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
