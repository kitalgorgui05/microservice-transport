package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Chauffeur;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Chauffeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, String>, JpaSpecificationExecutor<Chauffeur> {
}
