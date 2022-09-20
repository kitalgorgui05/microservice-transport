package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.BusService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.BusDTO;
import com.memoire.kital.raph.service.dto.BusCriteria;
import com.memoire.kital.raph.service.BusQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Bus}.
 */
@RestController
@RequestMapping("/api")
public class BusResource {

    private final Logger log = LoggerFactory.getLogger(BusResource.class);

    private static final String ENTITY_NAME = "transportBus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusService busService;

    private final BusQueryService busQueryService;

    public BusResource(BusService busService, BusQueryService busQueryService) {
        this.busService = busService;
        this.busQueryService = busQueryService;
    }

    /**
     * {@code POST  /buses} : Create a new bus.
     *
     * @param busDTO the busDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new busDTO, or with status {@code 400 (Bad Request)} if the bus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buses")
    public ResponseEntity<BusDTO> createBus(@Valid @RequestBody BusDTO busDTO) throws URISyntaxException {
        log.debug("REST request to save Bus : {}", busDTO);
        if (busDTO.getId() != null) {
            throw new BadRequestAlertException("A new bus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusDTO result = busService.save(busDTO);
        return ResponseEntity.created(new URI("/api/buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buses} : Updates an existing bus.
     *
     * @param busDTO the busDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated busDTO,
     * or with status {@code 400 (Bad Request)} if the busDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the busDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buses")
    public ResponseEntity<BusDTO> updateBus(@Valid @RequestBody BusDTO busDTO) throws URISyntaxException {
        log.debug("REST request to update Bus : {}", busDTO);
        if (busDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusDTO result = busService.save(busDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, busDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /buses} : get all the buses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buses in body.
     */
    @GetMapping("/buses")
    public ResponseEntity<List<BusDTO>> getAllBuses(BusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Buses by criteria: {}", criteria);
        Page<BusDTO> page = busQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buses/count} : count all the buses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/buses/count")
    public ResponseEntity<Long> countBuses(BusCriteria criteria) {
        log.debug("REST request to count Buses by criteria: {}", criteria);
        return ResponseEntity.ok().body(busQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /buses/:id} : get the "id" bus.
     *
     * @param id the id of the busDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the busDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buses/{id}")
    public ResponseEntity<BusDTO> getBus(@PathVariable Long id) {
        log.debug("REST request to get Bus : {}", id);
        Optional<BusDTO> busDTO = busService.findOne(id);
        return ResponseUtil.wrapOrNotFound(busDTO);
    }

    /**
     * {@code DELETE  /buses/:id} : delete the "id" bus.
     *
     * @param id the id of the busDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buses/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.debug("REST request to delete Bus : {}", id);
        busService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
