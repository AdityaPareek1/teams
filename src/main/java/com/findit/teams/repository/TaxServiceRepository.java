package com.findit.teams.repository;

import com.findit.teams.domain.TaxService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaxService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxServiceRepository extends JpaRepository<TaxService, Long> {}
