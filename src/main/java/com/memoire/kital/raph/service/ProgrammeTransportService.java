package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.ProgrammeTransport}.
 */
public interface ProgrammeTransportService {

    /**
     * Save a programmeTransport.
     *
     * @param programmeTransportDTO the entity to save.
     * @return the persisted entity.
     */
    ProgrammeTransportDTO save(ProgrammeTransportDTO programmeTransportDTO);

    /**
     * Get all the programmeTransports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgrammeTransportDTO> findAll(Pageable pageable);


    /**
     * Get the "id" programmeTransport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgrammeTransportDTO> findOne(Long id);

    /**
     * Delete the "id" programmeTransport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
