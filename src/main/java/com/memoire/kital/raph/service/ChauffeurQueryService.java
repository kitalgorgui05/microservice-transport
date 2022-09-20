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

import com.memoire.kital.raph.domain.Chauffeur;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.ChauffeurRepository;
import com.memoire.kital.raph.service.dto.ChauffeurCriteria;
import com.memoire.kital.raph.service.dto.ChauffeurDTO;
import com.memoire.kital.raph.service.mapper.ChauffeurMapper;

/**
 * Service for executing complex queries for {@link Chauffeur} entities in the database.
 * The main input is a {@link ChauffeurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChauffeurDTO} or a {@link Page} of {@link ChauffeurDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChauffeurQueryService extends QueryService<Chauffeur> {

    private final Logger log = LoggerFactory.getLogger(ChauffeurQueryService.class);

    private final ChauffeurRepository chauffeurRepository;

    private final ChauffeurMapper chauffeurMapper;

    public ChauffeurQueryService(ChauffeurRepository chauffeurRepository, ChauffeurMapper chauffeurMapper) {
        this.chauffeurRepository = chauffeurRepository;
        this.chauffeurMapper = chauffeurMapper;
    }

    /**
     * Return a {@link List} of {@link ChauffeurDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChauffeurDTO> findByCriteria(ChauffeurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chauffeur> specification = createSpecification(criteria);
        return chauffeurMapper.toDto(chauffeurRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChauffeurDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChauffeurDTO> findByCriteria(ChauffeurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chauffeur> specification = createSpecification(criteria);
        return chauffeurRepository.findAll(specification, page)
            .map(chauffeurMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChauffeurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chauffeur> specification = createSpecification(criteria);
        return chauffeurRepository.count(specification);
    }

    /**
     * Function to convert {@link ChauffeurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Chauffeur> createSpecification(ChauffeurCriteria criteria) {
        Specification<Chauffeur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Chauffeur_.id));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Chauffeur_.prenom));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Chauffeur_.nom));
            }
            if (criteria.getDateNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNaissance(), Chauffeur_.dateNaissance));
            }
            if (criteria.getLieuNaissance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNaissance(), Chauffeur_.lieuNaissance));
            }
            if (criteria.getCin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCin(), Chauffeur_.cin));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Chauffeur_.telephone));
            }
        }
        return specification;
    }
}
