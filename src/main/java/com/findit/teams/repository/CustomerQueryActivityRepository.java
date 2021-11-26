package com.findit.teams.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.findit.teams.domain.CustomerQueryActivity;

/**
 * Spring Data SQL repository for the CustomerQueryActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerQueryActivityRepository extends JpaRepository<CustomerQueryActivity, Long> {}
