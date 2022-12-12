package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Zone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Zone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZoneRepository extends JpaRepository<Zone, String>, JpaSpecificationExecutor<Zone> {
}
