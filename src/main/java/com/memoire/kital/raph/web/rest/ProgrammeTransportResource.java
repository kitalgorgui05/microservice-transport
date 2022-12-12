package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.ProgrammeTransportService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;
import com.memoire.kital.raph.service.dto.ProgrammeTransportCriteria;
import com.memoire.kital.raph.service.ProgrammeTransportQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.ProgrammeTransport}.
 */
@RestController
@RequestMapping("/api")
public class ProgrammeTransportResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammeTransportResource.class);

    private static final String ENTITY_NAME = "transportProgrammeTransport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgrammeTransportService programmeTransportService;

    private final ProgrammeTransportQueryService programmeTransportQueryService;

    public ProgrammeTransportResource(ProgrammeTransportService programmeTransportService, ProgrammeTransportQueryService programmeTransportQueryService) {
        this.programmeTransportService = programmeTransportService;
        this.programmeTransportQueryService = programmeTransportQueryService;
    }

    /**
     * {@code POST  /programme-transports} : Create a new programmeTransport.
     *
     * @param programmeTransportDTO the programmeTransportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programmeTransportDTO, or with status {@code 400 (Bad Request)} if the programmeTransport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programme-transports")
    public ResponseEntity<ProgrammeTransportDTO> createProgrammeTransport(@Valid @RequestBody ProgrammeTransportDTO programmeTransportDTO) throws URISyntaxException {
        log.debug("REST request to save ProgrammeTransport : {}", programmeTransportDTO);
        if (programmeTransportDTO.getId() != null) {
            throw new BadRequestAlertException("A new programmeTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgrammeTransportDTO result = programmeTransportService.save(programmeTransportDTO);
        return ResponseEntity.created(new URI("/api/programme-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programme-transports} : Updates an existing programmeTransport.
     *
     * @param programmeTransportDTO the programmeTransportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programmeTransportDTO,
     * or with status {@code 400 (Bad Request)} if the programmeTransportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programmeTransportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programme-transports")
    public ResponseEntity<ProgrammeTransportDTO> updateProgrammeTransport(@Valid @RequestBody ProgrammeTransportDTO programmeTransportDTO) throws URISyntaxException {
        log.debug("REST request to update ProgrammeTransport : {}", programmeTransportDTO);
        if (programmeTransportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgrammeTransportDTO result = programmeTransportService.save(programmeTransportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programmeTransportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /programme-transports} : get all the programmeTransports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programmeTransports in body.
     */
    @GetMapping("/programme-transports")
    public ResponseEntity<List<ProgrammeTransportDTO>> getAllProgrammeTransports(ProgrammeTransportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProgrammeTransports by criteria: {}", criteria);
        Page<ProgrammeTransportDTO> page = programmeTransportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /programme-transports/count} : count all the programmeTransports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/programme-transports/count")
    public ResponseEntity<Long> countProgrammeTransports(ProgrammeTransportCriteria criteria) {
        log.debug("REST request to count ProgrammeTransports by criteria: {}", criteria);
        return ResponseEntity.ok().body(programmeTransportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /programme-transports/:id} : get the "id" programmeTransport.
     *
     * @param id the id of the programmeTransportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programmeTransportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programme-transports/{id}")
    public ResponseEntity<ProgrammeTransportDTO> getProgrammeTransport(@PathVariable String id) {
        log.debug("REST request to get ProgrammeTransport : {}", id);
        Optional<ProgrammeTransportDTO> programmeTransportDTO = programmeTransportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programmeTransportDTO);
    }

    /**
     * {@code DELETE  /programme-transports/:id} : delete the "id" programmeTransport.
     *
     * @param id the id of the programmeTransportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programme-transports/{id}")
    public ResponseEntity<Void> deleteProgrammeTransport(@PathVariable String id) {
        log.debug("REST request to delete ProgrammeTransport : {}", id);
        programmeTransportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
