package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.ProgrammeTransport;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.ProgrammeTransportRepository;
import com.memoire.kital.raph.service.dto.ProgrammeTransportCriteria;
import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;
import com.memoire.kital.raph.service.mapper.ProgrammeTransportMapper;

/**
 * Service for executing complex queries for {@link ProgrammeTransport} entities in the database.
 * The main input is a {@link ProgrammeTransportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProgrammeTransportDTO} or a {@link Page} of {@link ProgrammeTransportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProgrammeTransportQueryService extends QueryService<ProgrammeTransport> {
    private final Logger log = LoggerFactory.getLogger(ProgrammeTransportQueryService.class);
    private final ProgrammeTransportRepository programmeTransportRepository;
    private final ProgrammeTransportMapper programmeTransportMapper;
    public ProgrammeTransportQueryService(ProgrammeTransportRepository programmeTransportRepository, ProgrammeTransportMapper programmeTransportMapper) {
        this.programmeTransportRepository = programmeTransportRepository;
        this.programmeTransportMapper = programmeTransportMapper;
    }
    @Transactional(readOnly = true)
    public List<ProgrammeTransportDTO> findByCriteria(ProgrammeTransportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProgrammeTransport> specification = createSpecification(criteria);
        return programmeTransportMapper.toDto(programmeTransportRepository.findAll(specification));
    }
    @Transactional(readOnly = true)
    public Page<ProgrammeTransportDTO> findByCriteria(ProgrammeTransportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProgrammeTransport> specification = createSpecification(criteria);
        return programmeTransportRepository.findAll(specification, page)
            .map(programmeTransportMapper::toDto);
    }
    @Transactional(readOnly = true)
    public long countByCriteria(ProgrammeTransportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProgrammeTransport> specification = createSpecification(criteria);
        return programmeTransportRepository.count(specification);
    }
    protected Specification<ProgrammeTransport> createSpecification(ProgrammeTransportCriteria criteria) {
        Specification<ProgrammeTransport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), ProgrammeTransport_.id));
            }
            if (criteria.getHeurDepart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeurDepart(), ProgrammeTransport_.heurDepart));
            }
            if (criteria.getDateDepart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDepart(), ProgrammeTransport_.dateDepart));
            }
            if (criteria.getGroupeTransportId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeTransportId(),
                    root -> root.join(ProgrammeTransport_.groupeTransport, JoinType.LEFT).get(GroupeTransport_.id)));
            }
            if (criteria.getBusId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusId(),
                    root -> root.join(ProgrammeTransport_.bus, JoinType.LEFT).get(Bus_.id)));
            }
        }
        return specification;
    }
}
