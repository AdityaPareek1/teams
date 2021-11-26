package com.findit.teams.repository;

import com.findit.teams.domain.TaxServiceActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaxServiceActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxServiceActivityRepository extends JpaRepository<TaxServiceActivity, Long> {}
