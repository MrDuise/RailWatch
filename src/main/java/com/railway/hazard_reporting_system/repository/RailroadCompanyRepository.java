package com.railway.hazard_reporting_system.repository;

import com.railway.hazard_reporting_system.entity.core.RailroadCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RailroadCompanyRepository extends JpaRepository<RailroadCompany, Long> {

    Optional<RailroadCompany> findByCode(String code);

    Optional<RailroadCompany> findByName(String name);

    List<RailroadCompany> findByIsActiveTrue();

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
