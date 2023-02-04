package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProgrammeTransportService {
    ProgrammeTransportDTO save(ProgrammeTransportDTO programmeTransportDTO);
    Page<ProgrammeTransportDTO> findAll(Pageable pageable);
    Optional<ProgrammeTransportDTO> findOne(String id);
    void delete(String id);
}
