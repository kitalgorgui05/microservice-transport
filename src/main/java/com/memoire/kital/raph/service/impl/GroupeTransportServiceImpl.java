package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.GroupeTransportService;
import com.memoire.kital.raph.domain.GroupeTransport;
import com.memoire.kital.raph.repository.GroupeTransportRepository;
import com.memoire.kital.raph.service.dto.GroupeTransportDTO;
import com.memoire.kital.raph.service.mapper.GroupeTransportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupeTransport}.
 */
@Service
@Transactional
public class GroupeTransportServiceImpl implements GroupeTransportService {

    private final Logger log = LoggerFactory.getLogger(GroupeTransportServiceImpl.class);

    private final GroupeTransportRepository groupeTransportRepository;

    private final GroupeTransportMapper groupeTransportMapper;

    public GroupeTransportServiceImpl(GroupeTransportRepository groupeTransportRepository, GroupeTransportMapper groupeTransportMapper) {
        this.groupeTransportRepository = groupeTransportRepository;
        this.groupeTransportMapper = groupeTransportMapper;
    }

    @Override
    public GroupeTransportDTO save(GroupeTransportDTO groupeTransportDTO) {
        log.debug("Request to save GroupeTransport : {}", groupeTransportDTO);
        GroupeTransport groupeTransport = groupeTransportMapper.toEntity(groupeTransportDTO);
        groupeTransport = groupeTransportRepository.save(groupeTransport);
        return groupeTransportMapper.toDto(groupeTransport);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupeTransportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupeTransports");
        return groupeTransportRepository.findAll(pageable)
            .map(groupeTransportMapper::toDto);
    }


    public Page<GroupeTransportDTO> findAllWithEagerRelationships(Pageable pageable) {
        return groupeTransportRepository.findAllWithEagerRelationships(pageable).map(groupeTransportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupeTransportDTO> findOne(Long id) {
        log.debug("Request to get GroupeTransport : {}", id);
        return groupeTransportRepository.findOneWithEagerRelationships(id)
            .map(groupeTransportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupeTransport : {}", id);
        groupeTransportRepository.deleteById(id);
    }
}
