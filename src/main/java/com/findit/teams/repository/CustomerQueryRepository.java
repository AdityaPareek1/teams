package com.findit.teams.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.findit.teams.domain.CustomerQuery;

/**
 * Spring Data SQL repository for the CustomerQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerQueryRepository extends JpaRepository<CustomerQuery, Long> {}
