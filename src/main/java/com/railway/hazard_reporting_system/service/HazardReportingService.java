// HazardReportingService.java
package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.entity.core.RailSegment;
//import com.railway.hazard_reporting_system.entity.enums.HazardState;
import com.railway.hazard_reporting_system.repository.HazardReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HazardReportingService {

    private static final Logger log = LoggerFactory.getLogger(HazardReportingService.class);

    private final HazardReportRepository hazardReportRepository;
    private final LocationService locationService;
    //private final PriorityAssessmentService priorityService;
    private final AlertDispatchService alertService;
    //private final MediaService mediaService;

    public HazardReportingService(
            HazardReportRepository repo,
            LocationService locService,

            AlertDispatchService alService
            ) {
        this.hazardReportRepository = repo;
        this.locationService = locService;

        this.alertService = alService;

    }

    /**
     * Create a new hazard report with automatic rail segment detection
     */
    public HazardReport createHazardReport(HazardReport report, Double lat, Double lng) {
        log.info("Creating hazard report at lat={}, lng={}", lat, lng);

        // Automatically determine which rail segment this hazard is on
        RailSegment segment = locationService.findNearestRailSegment(lat, lng)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No rail segment found near provided coordinates"
                ));

        report.setRailSegment(segment);
        report.setLatitude(lat);
        report.setLongitude(lng);

        // Calculate and set priority score
        //for mvp hard code it
        Integer priorityScore = 1;
        //create ml priority service later
                //priorityService.calculatePriority(report);
        report.setPriorityScore(priorityScore);

        // Save the report
        HazardReport saved = hazardReportRepository.save(report);

        // Dispatch alerts if high priority
        if (priorityScore >= 7) {
            alertService.dispatchAlert(saved);
        }

        log.info("Hazard report created with ID={}, priority={}", saved.getId(), priorityScore);
        return saved;
    }

    /**
     * Get hazards near a specific location
     */
    public List<HazardReport> getHazardsByLocation(Double lat, Double lng, Double radius) {
        log.debug("Finding hazards near lat={}, lng={} within {} miles", lat, lng, radius);

        // Find nearby rail segments first
        RailSegment nearestSegment = locationService.findNearestRailSegment(lat, lng)
                .orElse(null);

        if (nearestSegment == null) {
            return List.of();
        }

        // Return hazards on that segment
        return hazardReportRepository.findByRailSegmentId(nearestSegment.getId());
    }

    /**
     * Update hazard status with state management
     */
//    public HazardReport updateHazardStatus(Long id, HazardState status) {
//        log.info("Updating hazard {} to status {}", id, status);
//
//        HazardReport report = hazardReportRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Hazard report not found"));
//
//        // Update status (assuming HazardState is your status enum)
//        // report.setStatus(status); // Adjust based on your actual entity field
//
//        return hazardReportRepository.save(report);
//    }

    /**
     * Validate if a hazard location is actually near rail infrastructure
     */
    public Boolean validateHazardLocation(Double lat, Double lng) {
        log.debug("Validating hazard location lat={}, lng={}", lat, lng);

        // Check if location is within 100 meters of rail
        return locationService.isLocationNearRail(lat, lng, 100.0);
    }

    /**
     * Get all active hazards (not resolved/closed)
     */
    public List<HazardReport> getActiveHazardsBySegment(Long segmentId) {
        log.debug("Getting active hazards for segment {}", segmentId);

        return hazardReportRepository.findByRailSegmentId(segmentId).stream()
                .filter(h -> h.getStatus() != null &&
                        !h.getStatus().name().equals("RESOLVED") &&
                        !h.getStatus().name().equals("CLOSED"))
                .toList();
    }

    /**
     * simple method for finding a report by the id
     * @param hazardId
     * @return
     */
    public HazardReport getHazardsById(Long hazardId){
        return hazardReportRepository.findById(hazardId)
                .orElseThrow(() -> new IllegalArgumentException("Hazard report not found"));
    }
}








