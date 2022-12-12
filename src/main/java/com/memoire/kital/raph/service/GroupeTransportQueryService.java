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

import com.memoire.kital.raph.domain.GroupeTransport;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.GroupeTransportRepository;
import com.memoire.kital.raph.service.dto.GroupeTransportCriteria;
import com.memoire.kital.raph.service.dto.GroupeTransportDTO;
import com.memoire.kital.raph.service.mapper.GroupeTransportMapper;

/**
 * Service for executing complex queries for {@link GroupeTransport} entities in the database.
 * The main input is a {@link GroupeTransportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupeTransportDTO} or a {@link Page} of {@link GroupeTransportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupeTransportQueryService extends QueryService<GroupeTransport> {

    private final Logger log = LoggerFactory.getLogger(GroupeTransportQueryService.class);

    private final GroupeTransportRepository groupeTransportRepository;

    private final GroupeTransportMapper groupeTransportMapper;

    public GroupeTransportQueryService(GroupeTransportRepository groupeTransportRepository, GroupeTransportMapper groupeTransportMapper) {
        this.groupeTransportRepository = groupeTransportRepository;
        this.groupeTransportMapper = groupeTransportMapper;
    }

    /**
     * Return a {@link List} of {@link GroupeTransportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupeTransportDTO> findByCriteria(GroupeTransportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupeTransport> specification = createSpecification(criteria);
        return groupeTransportMapper.toDto(groupeTransportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupeTransportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupeTransportDTO> findByCriteria(GroupeTransportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupeTransport> specification = createSpecification(criteria);
        return groupeTransportRepository.findAll(specification, page)
            .map(groupeTransportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupeTransportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GroupeTransport> specification = createSpecification(criteria);
        return groupeTransportRepository.count(specification);
    }

    /**
     * Function to convert {@link GroupeTransportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GroupeTransport> createSpecification(GroupeTransportCriteria criteria) {
        Specification<GroupeTransport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), GroupeTransport_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), GroupeTransport_.nom));
            }
            if (criteria.getNombreEleves() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreEleves(), GroupeTransport_.nombreEleves));
            }
            if (criteria.getZonesId() != null) {
                specification = specification.and(buildSpecification(criteria.getZonesId(),
                    root -> root.join(GroupeTransport_.zones, JoinType.LEFT).get(Zone_.id)));
            }
        }
        return specification;
    }
}
