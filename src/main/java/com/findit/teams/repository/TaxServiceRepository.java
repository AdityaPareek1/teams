package com.findit.teams.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.findit.teams.domain.TaxDoamin;

/**
 * Spring Data SQL repository for the TaxService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxServiceRepository extends JpaRepository<TaxDoamin, Long> {}
