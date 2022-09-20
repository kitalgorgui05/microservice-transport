package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.ProgrammeTransport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProgrammeTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammeTransportRepository extends JpaRepository<ProgrammeTransport, Long>, JpaSpecificationExecutor<ProgrammeTransport> {
}
