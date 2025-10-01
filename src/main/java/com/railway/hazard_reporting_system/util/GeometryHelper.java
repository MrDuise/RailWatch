package com.railway.hazard_reporting_system.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

/**
 * Helper utility for creating PostGIS geometry objects
 * SRID 4326 = WGS84 (standard GPS coordinates)
 */
public class GeometryHelper {

    private static final int SRID = 4326; // WGS84 - standard GPS coordinate system
    private static final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), SRID);

    /**
     * Create a Point from latitude and longitude
     * @param latitude Latitude in decimal degrees (-90 to 90)
     * @param longitude Longitude in decimal degrees (-180 to 180)
     * @return PostGIS Point object
     */
    public static Point createPoint(double latitude, double longitude) {
        // Note: PostGIS uses (longitude, latitude) order, not (lat, long)!
        Coordinate coord = new Coordinate(longitude, latitude);
        return geometryFactory.createPoint(coord);
    }

    /**
     * Create a LineString from an array of coordinates
     * @param coordinates Array of [latitude, longitude] pairs
     * @return PostGIS LineString object representing the path
     */
    public static LineString createLineString(double[][] coordinates) {
        Coordinate[] coords = new Coordinate[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            // coordinates[i][0] = latitude, coordinates[i][1] = longitude
            coords[i] = new Coordinate(coordinates[i][1], coordinates[i][0]);
        }
        return geometryFactory.createLineString(coords);
    }

    /**
     * Create a simplified LineString from start and end points
     * In reality, you'd have many waypoints along the route
     * @param startLat Start latitude
     * @param startLon Start longitude
     * @param endLat End latitude
     * @param endLon End longitude
     * @return PostGIS LineString with just 2 points (simplified)
     */
    public static LineString createSimpleLineString(
            double startLat, double startLon,
            double endLat, double endLon) {
        Coordinate[] coords = new Coordinate[] {
                new Coordinate(startLon, startLat),
                new Coordinate(endLon, endLat)
        };
        return geometryFactory.createLineString(coords);
    }

    /**
     * Calculate approximate distance between two points in miles
     * Uses Haversine formula for spherical distance
     */
    public static double distanceInMiles(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_MILES = 3959;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_MILES * c;
    }

    /**
     * Check if a point is within a certain distance (in miles) of a line segment
     * Simplified version - in production you'd use PostGIS ST_Distance
     */
    public static boolean isNearSegment(Point point, LineString segment, double maxDistanceMiles) {
        // This is a simplified check using start/end points
        // In production, use PostGIS ST_Distance for accurate results
        Coordinate pointCoord = point.getCoordinate();
        Coordinate[] segmentCoords = segment.getCoordinates();

        // Check distance to start and end of segment
        for (Coordinate segCoord : segmentCoords) {
            double distance = distanceInMiles(
                    pointCoord.y, pointCoord.x,
                    segCoord.y, segCoord.x
            );
            if (distance <= maxDistanceMiles) {
                return true;
            }
        }
        return false;
    }
}
