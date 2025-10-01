package com.railway.hazard_reporting_system.repository;

import com.railway.hazard_reporting_system.entity.core.RailSegment;
import com.railway.hazard_reporting_system.entity.core.RailroadCompany;
//import com.railway.entity.SegmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RailSegmentRepository extends JpaRepository<RailSegment, Long> {

    List<RailSegment> findByRailroadCompany(RailroadCompany railroadCompany);

    List<RailSegment> findByRailroadCompanyCode(String companyCode);

    //List<RailSegment> findByStatus(SegmentStatus status);

    Optional<RailSegment> findBySegmentCode(String segmentCode);

    List<RailSegment> findByNameContainingIgnoreCase(String name);

    @Query("SELECT rs FROM RailSegment rs WHERE rs.startLocation LIKE %:location% OR rs.endLocation LIKE %:location%")
    List<RailSegment> findByLocation(@Param("location") String location);

    @Query("SELECT rs FROM RailSegment rs WHERE rs.railroadCompany.code IN :companyCodes")
    List<RailSegment> findByRailroadCompanyCodes(@Param("companyCodes") List<String> companyCodes);

    boolean existsBySegmentCode(String segmentCode);
}
