package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.response.HazardReportResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Broadcast new hazard to all connected railroad dashboards
     */
    public void broadcastNewHazard(HazardReportResponse hazard) {
        messagingTemplate.convertAndSend(
                "/topic/hazards/new",  // Channel
                hazard                  // Message payload
        );
    }

    /**
     * Send acknowledgment update to specific user
     */
    public void notifyHazardAcknowledged(HazardReport hazard) {
        messagingTemplate.convertAndSend(
                "/topic/hazards/acknowledged",
                hazard
        );
    }

    /**
     * Broadcast to all users
     */
    public void broadcastHazardResolved(HazardReport hazard) {
        messagingTemplate.convertAndSend(
                "/topic/hazards/resolved",
                hazard
        );
    }
}
