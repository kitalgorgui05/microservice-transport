package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ChauffeurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Chauffeur}.
 */
public interface ChauffeurService {

    /**
     * Save a chauffeur.
     *
     * @param chauffeurDTO the entity to save.
     * @return the persisted entity.
     */
    ChauffeurDTO save(ChauffeurDTO chauffeurDTO);

    /**
     * Get all the chauffeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChauffeurDTO> findAll(Pageable pageable);


    /**
     * Get the "id" chauffeur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChauffeurDTO> findOne(Long id);

    /**
     * Delete the "id" chauffeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
