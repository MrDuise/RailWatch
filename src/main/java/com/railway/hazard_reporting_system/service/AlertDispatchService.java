package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.response.HazardReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertDispatchService {

    private static final Logger log = LoggerFactory.getLogger(AlertDispatchService.class);
    private WebSocketNotificationService socketNotificationService;

    public AlertDispatchService(WebSocketNotificationService websocket){
        this.socketNotificationService = websocket;
    }

    /**
     * Dispatch alerts to relevant parties based on hazard severity and location
     */
    public void dispatchAlert(HazardReportResponse report) {
        log.warn("ALERT: High priority hazard reported - ID={}, Severity={}, Location={}",
                report.getId(),
                report.getSeverity(),
                report);

        //broadcasts the hazard report
        this.socketNotificationService.broadcastNewHazard(report);

        // TODO: Implement actual alert dispatch
        // - Send email to railroad company dispatch
        // - Send SMS to on-duty inspectors
        // - Create push notifications for mobile apps
    }
}
