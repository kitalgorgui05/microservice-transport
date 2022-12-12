package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.ChauffeurService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.ChauffeurDTO;
import com.memoire.kital.raph.service.dto.ChauffeurCriteria;
import com.memoire.kital.raph.service.ChauffeurQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Chauffeur}.
 */
@RestController
@RequestMapping("/api")
public class ChauffeurResource {

    private final Logger log = LoggerFactory.getLogger(ChauffeurResource.class);

    private static final String ENTITY_NAME = "transportChauffeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChauffeurService chauffeurService;

    private final ChauffeurQueryService chauffeurQueryService;

    public ChauffeurResource(ChauffeurService chauffeurService, ChauffeurQueryService chauffeurQueryService) {
        this.chauffeurService = chauffeurService;
        this.chauffeurQueryService = chauffeurQueryService;
    }

    /**
     * {@code POST  /chauffeurs} : Create a new chauffeur.
     *
     * @param chauffeurDTO the chauffeurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chauffeurDTO, or with status {@code 400 (Bad Request)} if the chauffeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chauffeurs")
    public ResponseEntity<ChauffeurDTO> createChauffeur(@Valid @RequestBody ChauffeurDTO chauffeurDTO) throws URISyntaxException {
        log.debug("REST request to save Chauffeur : {}", chauffeurDTO);
        if (chauffeurDTO.getId() != null) {
            throw new BadRequestAlertException("A new chauffeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChauffeurDTO result = chauffeurService.save(chauffeurDTO);
        return ResponseEntity.created(new URI("/api/chauffeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chauffeurs} : Updates an existing chauffeur.
     *
     * @param chauffeurDTO the chauffeurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chauffeurDTO,
     * or with status {@code 400 (Bad Request)} if the chauffeurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chauffeurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chauffeurs")
    public ResponseEntity<ChauffeurDTO> updateChauffeur(@Valid @RequestBody ChauffeurDTO chauffeurDTO) throws URISyntaxException {
        log.debug("REST request to update Chauffeur : {}", chauffeurDTO);
        if (chauffeurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChauffeurDTO result = chauffeurService.save(chauffeurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chauffeurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chauffeurs} : get all the chauffeurs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chauffeurs in body.
     */
    @GetMapping("/chauffeurs")
    public ResponseEntity<List<ChauffeurDTO>> getAllChauffeurs(ChauffeurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Chauffeurs by criteria: {}", criteria);
        Page<ChauffeurDTO> page = chauffeurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chauffeurs/count} : count all the chauffeurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/chauffeurs/count")
    public ResponseEntity<Long> countChauffeurs(ChauffeurCriteria criteria) {
        log.debug("REST request to count Chauffeurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(chauffeurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chauffeurs/:id} : get the "id" chauffeur.
     *
     * @param id the id of the chauffeurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chauffeurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chauffeurs/{id}")
    public ResponseEntity<ChauffeurDTO> getChauffeur(@PathVariable String id) {
        log.debug("REST request to get Chauffeur : {}", id);
        Optional<ChauffeurDTO> chauffeurDTO = chauffeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chauffeurDTO);
    }

    /**
     * {@code DELETE  /chauffeurs/:id} : delete the "id" chauffeur.
     *
     * @param id the id of the chauffeurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chauffeurs/{id}")
    public ResponseEntity<Void> deleteChauffeur(@PathVariable String id) {
        log.debug("REST request to delete Chauffeur : {}", id);
        chauffeurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
