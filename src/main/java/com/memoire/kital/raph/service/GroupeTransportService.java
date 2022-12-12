package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.GroupeTransportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.GroupeTransport}.
 */
public interface GroupeTransportService {

    /**
     * Save a groupeTransport.
     *
     * @param groupeTransportDTO the entity to save.
     * @return the persisted entity.
     */
    GroupeTransportDTO save(GroupeTransportDTO groupeTransportDTO);

    /**
     * Get all the groupeTransports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupeTransportDTO> findAll(Pageable pageable);

    /**
     * Get all the groupeTransports with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<GroupeTransportDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" groupeTransport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupeTransportDTO> findOne(String id);

    /**
     * Delete the "id" groupeTransport.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
