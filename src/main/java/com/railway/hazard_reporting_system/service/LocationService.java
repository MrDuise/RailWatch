package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.RailSegment;
import com.railway.hazard_reporting_system.exceptions.RailSegmentNotFoundException;
import com.railway.hazard_reporting_system.exceptions.RepositoryException;
import com.railway.hazard_reporting_system.repository.RailSegmentRepository;
import com.railway.hazard_reporting_system.util.GeospatialCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final RailSegmentRepository railSegmentRepository;
    private final GeospatialCalculator geoCalculator;

    public LocationService(RailSegmentRepository repo, GeospatialCalculator calc) {
        this.railSegmentRepository = repo;
        this.geoCalculator = calc;
    }

    /**
     * Return Optional so callers can decide how to handle "not found".
     * Repository exceptions are translated into RepositoryException with context.
     */
    public Optional<RailSegment> findNearestRailSegment(Double lat, Double lng) {
        try {
            return railSegmentRepository.findNearestSegment(lat, lng, 1609.34);
        } catch (DataAccessException dae) {
            // add context (lat/lng) and rethrow as a domain/infrastructure exception
            String msg = String.format("Failed to query nearest rail segment for lat=%s,lng=%s", lat, lng);
            log.error(msg, dae);
            throw new RepositoryException(msg, dae);
        }
    }

    /**
     * Determine owning railroad code. Throws RailSegmentNotFoundException if no nearby segment exists.
     */
    public String determineOwningRailroad(Double lat, Double lng) {
        RailSegment segment = findNearestRailSegment(lat, lng)
                .orElseThrow(() -> new RailSegmentNotFoundException(
                        String.format("No rail segment found near lat=%s,lng=%s", lat, lng)
                ));

        if (segment.getRailroadCompany() == null) {
            // business choice: treat missing company as "not found" too (explicit)
            throw new RailSegmentNotFoundException(
                    String.format("Rail segment found but railroad company missing for lat=%s,lng=%s", lat, lng)
            );
        }

        return segment.getRailroadCompany().getCode();
    }

    /**
     * Returns true if within threshold. Throws RailSegmentNotFoundException when no segment is found.
     * Wraps calculation exceptions so upstream can handle or log them centrally.
     */
    public Boolean isLocationNearRail(Double lat, Double lng, Double threshold) {
        RailSegment nearest = findNearestRailSegment(lat, lng)
                .orElseThrow(() -> new RailSegmentNotFoundException(
                        String.format("No rail segment found near lat=%s,lng=%s", lat, lng)
                ));

        try {
            double distance = geoCalculator.calculateDistance(lat, lng, nearest.getGeometry());
            return distance <= threshold;
        } catch (Exception ex) {
            String msg = String.format("Failed to calculate distance for lat=%s,lng=%s to segment id=%s",
                    lat, lng, nearest.getId());
            log.error(msg, ex);
            throw new RepositoryException(msg, ex);
        }
    }

    /**
     * Calculates distance; throws RailSegmentNotFoundException if no segment exists.
     * If you prefer a nullable return rather than exception, change this method to return Optional<Double>.
     */
    public Double calculateDistanceToRail(Double lat, Double lng) {
        RailSegment nearest = findNearestRailSegment(lat, lng)
                .orElseThrow(() -> new RailSegmentNotFoundException(
                        String.format("No rail segment found near lat=%s,lng=%s", lat, lng)
                ));

        try {
            return geoCalculator.calculateDistance(lat, lng, nearest.getGeometry());
        } catch (Exception ex) {
            String msg = String.format("Failed to calculate distance for lat=%s,lng=%s to segment id=%s",
                    lat, lng, nearest.getId());
            log.error(msg, ex);
            throw new RepositoryException(msg, ex);
        }
    }
}
