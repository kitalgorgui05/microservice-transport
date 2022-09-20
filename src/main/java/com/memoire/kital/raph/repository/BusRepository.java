package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Bus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusRepository extends JpaRepository<Bus, Long>, JpaSpecificationExecutor<Bus> {
}
