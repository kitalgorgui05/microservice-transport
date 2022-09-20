package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.GroupeTransportService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.GroupeTransportDTO;
import com.memoire.kital.raph.service.dto.GroupeTransportCriteria;
import com.memoire.kital.raph.service.GroupeTransportQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.GroupeTransport}.
 */
@RestController
@RequestMapping("/api")
public class GroupeTransportResource {

    private final Logger log = LoggerFactory.getLogger(GroupeTransportResource.class);

    private static final String ENTITY_NAME = "transportGroupeTransport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupeTransportService groupeTransportService;

    private final GroupeTransportQueryService groupeTransportQueryService;

    public GroupeTransportResource(GroupeTransportService groupeTransportService, GroupeTransportQueryService groupeTransportQueryService) {
        this.groupeTransportService = groupeTransportService;
        this.groupeTransportQueryService = groupeTransportQueryService;
    }

    /**
     * {@code POST  /groupe-transports} : Create a new groupeTransport.
     *
     * @param groupeTransportDTO the groupeTransportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupeTransportDTO, or with status {@code 400 (Bad Request)} if the groupeTransport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groupe-transports")
    public ResponseEntity<GroupeTransportDTO> createGroupeTransport(@Valid @RequestBody GroupeTransportDTO groupeTransportDTO) throws URISyntaxException {
        log.debug("REST request to save GroupeTransport : {}", groupeTransportDTO);
        if (groupeTransportDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupeTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupeTransportDTO result = groupeTransportService.save(groupeTransportDTO);
        return ResponseEntity.created(new URI("/api/groupe-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupe-transports} : Updates an existing groupeTransport.
     *
     * @param groupeTransportDTO the groupeTransportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupeTransportDTO,
     * or with status {@code 400 (Bad Request)} if the groupeTransportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupeTransportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groupe-transports")
    public ResponseEntity<GroupeTransportDTO> updateGroupeTransport(@Valid @RequestBody GroupeTransportDTO groupeTransportDTO) throws URISyntaxException {
        log.debug("REST request to update GroupeTransport : {}", groupeTransportDTO);
        if (groupeTransportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupeTransportDTO result = groupeTransportService.save(groupeTransportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupeTransportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groupe-transports} : get all the groupeTransports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupeTransports in body.
     */
    @GetMapping("/groupe-transports")
    public ResponseEntity<List<GroupeTransportDTO>> getAllGroupeTransports(GroupeTransportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GroupeTransports by criteria: {}", criteria);
        Page<GroupeTransportDTO> page = groupeTransportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groupe-transports/count} : count all the groupeTransports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/groupe-transports/count")
    public ResponseEntity<Long> countGroupeTransports(GroupeTransportCriteria criteria) {
        log.debug("REST request to count GroupeTransports by criteria: {}", criteria);
        return ResponseEntity.ok().body(groupeTransportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /groupe-transports/:id} : get the "id" groupeTransport.
     *
     * @param id the id of the groupeTransportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupeTransportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groupe-transports/{id}")
    public ResponseEntity<GroupeTransportDTO> getGroupeTransport(@PathVariable Long id) {
        log.debug("REST request to get GroupeTransport : {}", id);
        Optional<GroupeTransportDTO> groupeTransportDTO = groupeTransportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupeTransportDTO);
    }

    /**
     * {@code DELETE  /groupe-transports/:id} : delete the "id" groupeTransport.
     *
     * @param id the id of the groupeTransportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groupe-transports/{id}")
    public ResponseEntity<Void> deleteGroupeTransport(@PathVariable Long id) {
        log.debug("REST request to delete GroupeTransport : {}", id);
        groupeTransportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
