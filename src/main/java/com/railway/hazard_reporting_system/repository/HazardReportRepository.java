package com.railway.hazard_reporting_system.repository;

import com.railway.hazard_reporting_system.entity.core.HazardReport;
import com.railway.hazard_reporting_system.entity.enums.HazardSeverity;
import com.railway.hazard_reporting_system.entity.enums.HazardStatus;
import com.railway.hazard_reporting_system.entity.enums.HazardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HazardReportRepository extends JpaRepository<HazardReport, Long> {

    List<HazardReport> findByStatus(HazardStatus status);

    List<HazardReport> findBySeverity(HazardSeverity severity);

    List<HazardReport> findByHazardType(HazardType hazardType);

    List<HazardReport> findByRailSegmentId(Long railSegmentId);

    List<HazardReport> findByReporterId(Long reporterId);

    List<HazardReport> findByAssignedToId(Long assignedToId);

    List<HazardReport> findByStatusIn(List<HazardStatus> statuses);

    List<HazardReport> findBySeverityIn(List<HazardSeverity> severities);

    @Query("SELECT hr FROM HazardReport hr WHERE hr.createdAt >= :startDate AND hr.createdAt <= :endDate")
    List<HazardReport> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT hr FROM HazardReport hr WHERE hr.railSegment.railroadCompany.code = :companyCode")
    List<HazardReport> findByRailroadCompanyCode(@Param("companyCode") String companyCode);

    @Query("SELECT hr FROM HazardReport hr WHERE hr.status IN ('REPORTED', 'ACKNOWLEDGED', 'IN_PROGRESS') ORDER BY hr.severity DESC, hr.createdAt ASC")
    List<HazardReport> findActiveHazards();

    @Query("SELECT hr FROM HazardReport hr WHERE hr.severity = 'EMERGENCY' OR hr.severity = 'CRITICAL'")
    List<HazardReport> findCriticalHazards();

    @Query("SELECT COUNT(hr) FROM HazardReport hr WHERE hr.railSegment.id = :segmentId AND hr.status IN ('REPORTED', 'ACKNOWLEDGED', 'IN_PROGRESS')")
    Long countActiveHazardsBySegment(@Param("segmentId") Long segmentId);
}
