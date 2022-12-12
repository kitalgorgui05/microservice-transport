package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.ProgrammeTransportService;
import com.memoire.kital.raph.domain.ProgrammeTransport;
import com.memoire.kital.raph.repository.ProgrammeTransportRepository;
import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;
import com.memoire.kital.raph.service.mapper.ProgrammeTransportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProgrammeTransport}.
 */
@Service
@Transactional
public class ProgrammeTransportServiceImpl implements ProgrammeTransportService {

    private final Logger log = LoggerFactory.getLogger(ProgrammeTransportServiceImpl.class);

    private final ProgrammeTransportRepository programmeTransportRepository;

    private final ProgrammeTransportMapper programmeTransportMapper;

    public ProgrammeTransportServiceImpl(ProgrammeTransportRepository programmeTransportRepository, ProgrammeTransportMapper programmeTransportMapper) {
        this.programmeTransportRepository = programmeTransportRepository;
        this.programmeTransportMapper = programmeTransportMapper;
    }

    @Override
    public ProgrammeTransportDTO save(ProgrammeTransportDTO programmeTransportDTO) {
        log.debug("Request to save ProgrammeTransport : {}", programmeTransportDTO);
        ProgrammeTransport programmeTransport = programmeTransportMapper.toEntity(programmeTransportDTO);
        programmeTransport = programmeTransportRepository.saveAndFlush(programmeTransport);
        return programmeTransportMapper.toDto(programmeTransport);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgrammeTransportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProgrammeTransports");
        return programmeTransportRepository.findAll(pageable)
            .map(programmeTransportMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProgrammeTransportDTO> findOne(String id) {
        log.debug("Request to get ProgrammeTransport : {}", id);
        return programmeTransportRepository.findById(id)
            .map(programmeTransportMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgrammeTransport : {}", id);
        programmeTransportRepository.deleteById(id);
    }
}
