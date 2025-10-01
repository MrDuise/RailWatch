package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.RailSegment;
import com.railway.hazard_reporting_system.repository.RailSegmentRepository;
import com.railway.hazard_reporting_system.util.GeospatialCalculator;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final RailSegmentRepository railSegmentRepository;
    private final GeospatialCalculator geoCalculator;

    public LocationService(RailSegmentRepository repo, GeospatialCalculator calc) {
        this.railSegmentRepository = repo;
        this.geoCalculator = calc;
    }

    public RailSegment findNearestRailSegment(Double lat, Double lng) {
        return railSegmentRepository.findNearestSegment(lat, lng, 1609.34)
                .orElse(null);
    }

    public String determineOwningRailroad(Double lat, Double lng) {
        RailSegment segment = findNearestRailSegment(lat, lng);
        if (segment != null && segment.getRailroadCompany() != null) {
            return segment.getRailroadCompany().getCode();
        }
        return null;
    }

    public Boolean isLocationNearRail(Double lat, Double lng, Double threshold) {
        RailSegment nearest = findNearestRailSegment(lat, lng);
        if (nearest == null) {
            return false;
        }

        // Calculate distance using GeospatialCalculator
        double distance = geoCalculator.calculateDistance(
                lat, lng,
                nearest.getGeometry()
        );

        return distance <= threshold;
    }

    public Double calculateDistanceToRail(Double lat, Double lng) {
        RailSegment nearest = findNearestRailSegment(lat, lng);
        if (nearest == null) {
            return null;
        }

        return geoCalculator.calculateDistance(lat, lng, nearest.getGeometry());
    }
}
