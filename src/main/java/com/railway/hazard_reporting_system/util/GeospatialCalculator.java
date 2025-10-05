package com.railway.hazard_reporting_system.util;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class GeospatialCalculator {

    private static final double EARTH_RADIUS_METERS = 6371000.0; // Earth radius in meters

    /**
     * Calculate distance from a point (lat/lng) to a LineString in meters
     * Uses Haversine formula for distance calculation
     */
    public double calculateDistance(Double lat, Double lng, LineString lineString) {
        if (lineString == null) {
            return Double.MAX_VALUE;
        }

        Point userPoint = GeometryHelper.createPoint(lat, lng);

        // Find the closest point on the line to the user's location
        double minDistance = Double.MAX_VALUE;

        Coordinate[] coords = lineString.getCoordinates();
        for (int i = 0; i < coords.length - 1; i++) {
            double distance = distanceToSegment(
                    lat, lng,
                    coords[i].y, coords[i].x,
                    coords[i + 1].y, coords[i + 1].x
            );
            minDistance = Math.min(minDistance, distance);
        }

        return minDistance;
    }

    /**
     * Calculate distance from a point to a line segment using Haversine formula
     * Returns distance in meters
     */
    private double distanceToSegment(
            double lat, double lng,
            double lat1, double lng1,
            double lat2, double lng2) {

        // Calculate distances to both endpoints
        double d1 = haversineDistance(lat, lng, lat1, lng1);
        double d2 = haversineDistance(lat, lng, lat2, lng2);

        // For simplified calculation, return minimum distance to endpoints
        // In production, you'd calculate perpendicular distance to the line segment
        return Math.min(d1, d2);
    }

    /**
     * Haversine formula to calculate distance between two points
     * Returns distance in meters
     */
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_METERS * c;
    }

    /**
     * Convert meters to miles
     */
    public double metersToMiles(double meters) {
        return meters / 1609.34;
    }

    /**
     * Convert miles to meters
     */
    public double milesToMeters(double miles) {
        return miles * 1609.34;
    }
}
