package com.railway.hazard_reporting_system.controller;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.entity.core.User;
import com.railway.hazard_reporting_system.repository.UserRepository;
import com.railway.hazard_reporting_system.request.CreateHazardReportRequest;
import com.railway.hazard_reporting_system.response.HazardReportResponse;
import com.railway.hazard_reporting_system.service.HazardReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hazard-reports")
@CrossOrigin(origins = "*")
public class HazardReportController {

    // Add this to your controller
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(HazardReportController.class);

    private final HazardReportingService hazardReportingService;

    public HazardReportController(HazardReportingService service) {
        this.hazardReportingService = service;
    }

    /**
     * Create a new hazard report
     * POST /hazard-reports
     */
    @PostMapping
    public ResponseEntity<HazardReportResponse> createHazardReport(@RequestBody CreateHazardReportRequest request) {
        log.info("Creating hazard report: {}", request.getTitle());

        try {
            // Convert request to entity (you'll need to implement this mapping)
            HazardReport report = convertToEntity(request);

            // Create the report with automatic location detection
            HazardReport created = hazardReportingService.createHazardReport(
                    report,
                    request.getLatitude(),
                    request.getLongitude()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HazardReportResponse.fromEntity(created));
        } catch (IllegalArgumentException e) {
            log.error("Invalid hazard report request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating hazard report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get hazards near a location
     * GET /hazard-reports/nearby?lat=47.8109&lng=-122.3848&radius=5.0
     */
    @GetMapping("/nearby")
    public ResponseEntity<List<HazardReport>> getHazardsByLocation(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5.0") Double radius) {

        log.debug("Getting hazards near lat={}, lng={}, radius={}", lat, lng, radius);

        try {
            List<HazardReport> hazards = hazardReportingService.getHazardsByLocation(lat, lng, radius);
            return ResponseEntity.ok(hazards);
        } catch (Exception e) {
            log.error("Error retrieving hazards by location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get hazard report by ID
     * GET /hazard-reports/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<HazardReport> getHazardById(@PathVariable Long id) {
        log.debug("Getting hazard report by ID: {}", id);

        try{
            HazardReport hazard = hazardReportingService.getHazardsById(id);
            return ResponseEntity.ok(hazard);
        } catch (Exception e){
            log.error("Error retrieving hazard by id", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //Is extra for now. Since I'm not doing anything with hazards that already excist at the moment, this is not needed

//    /**
//     * Submit verification for a hazard report
//     * POST /hazard-reports/{id}/verification
//     * Note: VerificationService not included per requirements
//     */
//    @PostMapping("/{id}/verification")
//    public ResponseEntity<Void> submitVerification(
//            @PathVariable Long id,
//            @RequestBody Object verificationRequest) {
//
//        log.info("Verification submitted for hazard {}", id);
//
//        // TODO: Implement when VerificationService is added
//        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
//    }
//
//    /**
//     * Get verification status for a hazard report
//     * GET /hazard-reports/{id}/verification
//     */
//    @GetMapping("/{id}/verification")
//    public ResponseEntity<Object> getVerificationStatus(@PathVariable Long id) {
//        log.debug("Getting verification status for hazard {}", id);
//
//        // TODO: Implement when VerificationService is added
//        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
//    }

    /**
     * Helper method to convert request DTO to entity
     */


    private HazardReport convertToEntity(CreateHazardReportRequest request) {
        HazardReport report = new HazardReport();
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setHazardType(request.getHazardType());
        report.setSeverity(request.getSeverity());
        report.setMilePost(request.getMilePost());
        report.setWeatherConditions(request.getWeatherConditions());
        report.setTrafficImpact(request.getTrafficImpact());
        report.setAttachmentBase64(request.getAttachmentBase64());

        // IMPORTANT: Set the reporter (User)
        if (request.getReporterId() != null) {
            User reporter = userRepository.findById(request.getReporterId())
                    .orElseThrow(() -> new IllegalArgumentException("Reporter user not found"));
            report.setReporter(reporter);
        } else {
            // Use a default user if no reporter specified (for testing)
            User defaultReporter = userRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Default reporter not found"));
            report.setReporter(defaultReporter);
        }

        return report;
    }
}
