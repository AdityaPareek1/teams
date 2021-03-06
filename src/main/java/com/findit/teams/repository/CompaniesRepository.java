package com.findit.teams.repository;

import com.findit.teams.domain.Companies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Companies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Long> {}
