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

import com.memoire.kital.raph.domain.Bus;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.BusRepository;
import com.memoire.kital.raph.service.dto.BusCriteria;
import com.memoire.kital.raph.service.dto.BusDTO;
import com.memoire.kital.raph.service.mapper.BusMapper;

/**
 * Service for executing complex queries for {@link Bus} entities in the database.
 * The main input is a {@link BusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusDTO} or a {@link Page} of {@link BusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusQueryService extends QueryService<Bus> {

    private final Logger log = LoggerFactory.getLogger(BusQueryService.class);

    private final BusRepository busRepository;

    private final BusMapper busMapper;

    public BusQueryService(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    /**
     * Return a {@link List} of {@link BusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusDTO> findByCriteria(BusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bus> specification = createSpecification(criteria);
        return busMapper.toDto(busRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusDTO> findByCriteria(BusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bus> specification = createSpecification(criteria);
        return busRepository.findAll(specification, page)
            .map(busMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bus> specification = createSpecification(criteria);
        return busRepository.count(specification);
    }

    /**
     * Function to convert {@link BusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bus> createSpecification(BusCriteria criteria) {
        Specification<Bus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Bus_.id));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Bus_.matricule));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Bus_.numero));
            }
            if (criteria.getNombreplace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreplace(), Bus_.nombreplace));
            }
            if (criteria.getChauffeurId() != null) {
                specification = specification.and(buildSpecification(criteria.getChauffeurId(),
                    root -> root.join(Bus_.chauffeur, JoinType.LEFT).get(Chauffeur_.id)));
            }
        }
        return specification;
    }
}
