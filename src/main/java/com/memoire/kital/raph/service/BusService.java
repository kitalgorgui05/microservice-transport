package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.BusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Bus}.
 */
public interface BusService {
    BusDTO save(BusDTO busDTO);
    Page<BusDTO> findAll(Pageable pageable);
    Optional<BusDTO> findOne(String id);
    void delete(String id);
}
