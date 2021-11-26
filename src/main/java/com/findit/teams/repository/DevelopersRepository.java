package com.findit.teams.repository;

import com.findit.teams.domain.Developers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Developers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevelopersRepository extends JpaRepository<Developers, Long> {}
