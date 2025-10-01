package com.railway.hazard_reporting_system.repository;

import com.railway.hazard_reporting_system.entity.core.User;
import com.railway.hazard_reporting_system.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByRailroadCompanyId(Long railroadCompanyId);

    List<User> findByIsActiveTrue();

    @Query("SELECT u FROM User u WHERE u.railroadCompany.code = :companyCode")
    List<User> findByRailroadCompanyCode(@Param("companyCode") String companyCode);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
