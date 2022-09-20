package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.BusService;
import com.memoire.kital.raph.domain.Bus;
import com.memoire.kital.raph.repository.BusRepository;
import com.memoire.kital.raph.service.dto.BusDTO;
import com.memoire.kital.raph.service.mapper.BusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bus}.
 */
@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final Logger log = LoggerFactory.getLogger(BusServiceImpl.class);

    private final BusRepository busRepository;

    private final BusMapper busMapper;

    public BusServiceImpl(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    @Override
    public BusDTO save(BusDTO busDTO) {
        log.debug("Request to save Bus : {}", busDTO);
        Bus bus = busMapper.toEntity(busDTO);
        bus = busRepository.save(bus);
        return busMapper.toDto(bus);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Buses");
        return busRepository.findAll(pageable)
            .map(busMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BusDTO> findOne(Long id) {
        log.debug("Request to get Bus : {}", id);
        return busRepository.findById(id)
            .map(busMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bus : {}", id);
        busRepository.deleteById(id);
    }
}
