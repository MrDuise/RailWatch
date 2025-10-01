package com.railway.hazard_reporting_system.entity.enums;

public enum HazardType {
    // Track Related
    TRACK_DEFECT("Track defect or damage"),
    BROKEN_RAIL("Broken or cracked rail"),
    LOOSE_RAIL("Loose rail or fasteners"),
    JOINT_DEFECT("Rail joint defect"),
    RAIL_BUCKLING("Rail buckling due to heat"),

    // Signal and Communication
    SIGNAL_MALFUNCTION("Signal system malfunction"),
    CROSSING_MALFUNCTION("Railroad crossing malfunction"),
    COMMUNICATION_FAILURE("Communication system failure"),

    // Infrastructure
    BRIDGE_DAMAGE("Bridge structural damage"),
    CULVERT_DAMAGE("Culvert or drainage damage"),
    TUNNEL_ISSUE("Tunnel structural issue"),
    PLATFORM_DAMAGE("Station platform damage"),

    // Environmental Obstacles
    VEGETATION_OBSTRUCTION("Vegetation blocking track"),
    DEBRIS_ON_TRACK("Debris or obstruction on track"),
    WASHOUT("Track washout from flooding"),
    LANDSLIDE("Landslide affecting track"),
    ROCKFALL("Rockfall on or near track"),

    // Animals and Trespassers
    ANIMAL_ON_TRACK("Animal on or near track"),
    ANIMAL_STRIKE("Animal strike incident"),
    TRESPASSER("Unauthorized person on property"),
    VEHICLE_ON_TRACK("Vehicle on railroad tracks"),

    // Weather Related
    WEATHER_DAMAGE("Weather-related damage"),
    FLOODING("Flooding affecting operations"),
    ICE_SNOW("Ice or snow accumulation"),
    WIND_DAMAGE("Wind-related damage"),

    // Equipment and Mechanical
    EQUIPMENT_FAILURE("Equipment or machinery failure"),
    POWER_FAILURE("Electrical power failure"),

    // Security and Safety
    SECURITY_THREAT("Security threat or concern"),
    VANDALISM("Vandalism or sabotage"),
    THEFT("Theft of railroad property"),
    SUSPICIOUS_ACTIVITY("Suspicious activity"),

    // Hazmat and Environmental
    HAZMAT_SPILL("Hazardous material spill"),
    ENVIRONMENTAL_CONCERN("Environmental concern"),

    // General
    OTHER("Other hazard type");

    private final String description;

    HazardType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
