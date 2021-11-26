package com.findit.teams.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.findit.teams.domain.CustomerProfile;

/**
 * Spring Data SQL repository for the CustomerProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {}
