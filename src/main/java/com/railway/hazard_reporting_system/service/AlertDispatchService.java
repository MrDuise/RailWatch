package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertDispatchService {

    private static final Logger log = LoggerFactory.getLogger(AlertDispatchService.class);

    /**
     * Dispatch alerts to relevant parties based on hazard severity and location
     */
    public void dispatchAlert(HazardReport report) {
        log.warn("ALERT: High priority hazard reported - ID={}, Severity={}, Location={}",
                report.getId(),
                report.getSeverity(),
                report.getRailSegment().getName());

        // TODO: Implement actual alert dispatch
        // - Send email to railroad company dispatch
        // - Send SMS to on-duty inspectors
        //just do the websocket for now, the others are extra
        // - Trigger WebSocket notification to dashboard
        // - Create push notifications for mobile apps
    }
}
