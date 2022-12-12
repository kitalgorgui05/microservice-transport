package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.TransportApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.ProgrammeTransport;
import com.memoire.kital.raph.domain.GroupeTransport;
import com.memoire.kital.raph.domain.Bus;
import com.memoire.kital.raph.repository.ProgrammeTransportRepository;
import com.memoire.kital.raph.service.ProgrammeTransportService;
import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;
import com.memoire.kital.raph.service.mapper.ProgrammeTransportMapper;
import com.memoire.kital.raph.service.dto.ProgrammeTransportCriteria;
import com.memoire.kital.raph.service.ProgrammeTransportQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProgrammeTransportResource} REST controller.
 */
@SpringBootTest(classes = { TransportApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProgrammeTransportResourceIT {

    private static final Instant DEFAULT_HEUR_DEPART = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEUR_DEPART = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATE_DEPART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPART = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DEPART = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ProgrammeTransportRepository programmeTransportRepository;

    @Autowired
    private ProgrammeTransportMapper programmeTransportMapper;

    @Autowired
    private ProgrammeTransportService programmeTransportService;

    @Autowired
    private ProgrammeTransportQueryService programmeTransportQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgrammeTransportMockMvc;

    private ProgrammeTransport programmeTransport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgrammeTransport createEntity(EntityManager em) {
        ProgrammeTransport programmeTransport = new ProgrammeTransport()
            .heurDepart(DEFAULT_HEUR_DEPART)
            .dateDepart(DEFAULT_DATE_DEPART);
        return programmeTransport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgrammeTransport createUpdatedEntity(EntityManager em) {
        ProgrammeTransport programmeTransport = new ProgrammeTransport()
            .heurDepart(UPDATED_HEUR_DEPART)
            .dateDepart(UPDATED_DATE_DEPART);
        return programmeTransport;
    }

    @BeforeEach
    public void initTest() {
        programmeTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgrammeTransport() throws Exception {
        int databaseSizeBeforeCreate = programmeTransportRepository.findAll().size();
        // Create the ProgrammeTransport
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(programmeTransport);
        restProgrammeTransportMockMvc.perform(post("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgrammeTransport in the database
        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeCreate + 1);
        ProgrammeTransport testProgrammeTransport = programmeTransportList.get(programmeTransportList.size() - 1);
        assertThat(testProgrammeTransport.getHeurDepart()).isEqualTo(DEFAULT_HEUR_DEPART);
        assertThat(testProgrammeTransport.getDateDepart()).isEqualTo(DEFAULT_DATE_DEPART);
    }

    @Test
    @Transactional
    public void createProgrammeTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmeTransportRepository.findAll().size();

        // Create the ProgrammeTransport with an existing ID
        programmeTransport.setId(null);
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(programmeTransport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammeTransportMockMvc.perform(post("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammeTransport in the database
        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHeurDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeTransportRepository.findAll().size();
        // set the field null
        programmeTransport.setHeurDepart(null);

        // Create the ProgrammeTransport, which fails.
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(programmeTransport);


        restProgrammeTransportMockMvc.perform(post("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeTransportRepository.findAll().size();
        // set the field null
        programmeTransport.setDateDepart(null);

        // Create the ProgrammeTransport, which fails.
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(programmeTransport);


        restProgrammeTransportMockMvc.perform(post("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransports() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmeTransport.getId())))
            .andExpect(jsonPath("$.[*].heurDepart").value(hasItem(DEFAULT_HEUR_DEPART.toString())))
            .andExpect(jsonPath("$.[*].dateDepart").value(hasItem(DEFAULT_DATE_DEPART.toString())));
    }

    @Test
    @Transactional
    public void getProgrammeTransport() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get the programmeTransport
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports/{id}", programmeTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programmeTransport.getId()))
            .andExpect(jsonPath("$.heurDepart").value(DEFAULT_HEUR_DEPART.toString()))
            .andExpect(jsonPath("$.dateDepart").value(DEFAULT_DATE_DEPART.toString()));
    }


    @Test
    @Transactional
    public void getProgrammeTransportsByIdFiltering() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        String id = programmeTransport.getId();

        defaultProgrammeTransportShouldBeFound("id.equals=" + id);
        defaultProgrammeTransportShouldNotBeFound("id.notEquals=" + id);

        defaultProgrammeTransportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProgrammeTransportShouldNotBeFound("id.greaterThan=" + id);

        defaultProgrammeTransportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProgrammeTransportShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProgrammeTransportsByHeurDepartIsEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where heurDepart equals to DEFAULT_HEUR_DEPART
        defaultProgrammeTransportShouldBeFound("heurDepart.equals=" + DEFAULT_HEUR_DEPART);

        // Get all the programmeTransportList where heurDepart equals to UPDATED_HEUR_DEPART
        defaultProgrammeTransportShouldNotBeFound("heurDepart.equals=" + UPDATED_HEUR_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByHeurDepartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where heurDepart not equals to DEFAULT_HEUR_DEPART
        defaultProgrammeTransportShouldNotBeFound("heurDepart.notEquals=" + DEFAULT_HEUR_DEPART);

        // Get all the programmeTransportList where heurDepart not equals to UPDATED_HEUR_DEPART
        defaultProgrammeTransportShouldBeFound("heurDepart.notEquals=" + UPDATED_HEUR_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByHeurDepartIsInShouldWork() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where heurDepart in DEFAULT_HEUR_DEPART or UPDATED_HEUR_DEPART
        defaultProgrammeTransportShouldBeFound("heurDepart.in=" + DEFAULT_HEUR_DEPART + "," + UPDATED_HEUR_DEPART);

        // Get all the programmeTransportList where heurDepart equals to UPDATED_HEUR_DEPART
        defaultProgrammeTransportShouldNotBeFound("heurDepart.in=" + UPDATED_HEUR_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByHeurDepartIsNullOrNotNull() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where heurDepart is not null
        defaultProgrammeTransportShouldBeFound("heurDepart.specified=true");

        // Get all the programmeTransportList where heurDepart is null
        defaultProgrammeTransportShouldNotBeFound("heurDepart.specified=false");
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart equals to DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.equals=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart equals to UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.equals=" + UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart not equals to DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.notEquals=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart not equals to UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.notEquals=" + UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsInShouldWork() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart in DEFAULT_DATE_DEPART or UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.in=" + DEFAULT_DATE_DEPART + "," + UPDATED_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart equals to UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.in=" + UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsNullOrNotNull() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart is not null
        defaultProgrammeTransportShouldBeFound("dateDepart.specified=true");

        // Get all the programmeTransportList where dateDepart is null
        defaultProgrammeTransportShouldNotBeFound("dateDepart.specified=false");
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart is greater than or equal to DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.greaterThanOrEqual=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart is greater than or equal to UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.greaterThanOrEqual=" + UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart is less than or equal to DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.lessThanOrEqual=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart is less than or equal to SMALLER_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.lessThanOrEqual=" + SMALLER_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsLessThanSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart is less than DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.lessThan=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart is less than UPDATED_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.lessThan=" + UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void getAllProgrammeTransportsByDateDepartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        // Get all the programmeTransportList where dateDepart is greater than DEFAULT_DATE_DEPART
        defaultProgrammeTransportShouldNotBeFound("dateDepart.greaterThan=" + DEFAULT_DATE_DEPART);

        // Get all the programmeTransportList where dateDepart is greater than SMALLER_DATE_DEPART
        defaultProgrammeTransportShouldBeFound("dateDepart.greaterThan=" + SMALLER_DATE_DEPART);
    }


    @Test
    @Transactional
    public void getAllProgrammeTransportsByGroupeTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);
        GroupeTransport groupeTransport = GroupeTransportResourceIT.createEntity(em);
        em.persist(groupeTransport);
        em.flush();
        programmeTransport.setGroupeTransport(groupeTransport);
        programmeTransportRepository.saveAndFlush(programmeTransport);
        String groupeTransportId = groupeTransport.getId();

        // Get all the programmeTransportList where groupeTransport equals to groupeTransportId
        defaultProgrammeTransportShouldBeFound("groupeTransportId.equals=" + groupeTransportId);

        // Get all the programmeTransportList where groupeTransport equals to groupeTransportId + 1
        defaultProgrammeTransportShouldNotBeFound("groupeTransportId.equals=" + (groupeTransportId + 1));
    }


    @Test
    @Transactional
    public void getAllProgrammeTransportsByBusIsEqualToSomething() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);
        Bus bus = BusResourceIT.createEntity(em);
        em.persist(bus);
        em.flush();
        programmeTransport.setBus(bus);
        programmeTransportRepository.saveAndFlush(programmeTransport);
        String busId = bus.getId();

        // Get all the programmeTransportList where bus equals to busId
        defaultProgrammeTransportShouldBeFound("busId.equals=" + busId);

        // Get all the programmeTransportList where bus equals to busId + 1
        defaultProgrammeTransportShouldNotBeFound("busId.equals=" + (busId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProgrammeTransportShouldBeFound(String filter) throws Exception {
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmeTransport.getId())))
            .andExpect(jsonPath("$.[*].heurDepart").value(hasItem(DEFAULT_HEUR_DEPART.toString())))
            .andExpect(jsonPath("$.[*].dateDepart").value(hasItem(DEFAULT_DATE_DEPART.toString())));

        // Check, that the count call also returns 1
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProgrammeTransportShouldNotBeFound(String filter) throws Exception {
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProgrammeTransport() throws Exception {
        // Get the programmeTransport
        restProgrammeTransportMockMvc.perform(get("/api/programme-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgrammeTransport() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        int databaseSizeBeforeUpdate = programmeTransportRepository.findAll().size();

        // Update the programmeTransport
        ProgrammeTransport updatedProgrammeTransport = programmeTransportRepository.findById(programmeTransport.getId()).get();
        // Disconnect from session so that the updates on updatedProgrammeTransport are not directly saved in db
        em.detach(updatedProgrammeTransport);
        updatedProgrammeTransport
            .heurDepart(UPDATED_HEUR_DEPART)
            .dateDepart(UPDATED_DATE_DEPART);
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(updatedProgrammeTransport);

        restProgrammeTransportMockMvc.perform(put("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isOk());

        // Validate the ProgrammeTransport in the database
        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeUpdate);
        ProgrammeTransport testProgrammeTransport = programmeTransportList.get(programmeTransportList.size() - 1);
        assertThat(testProgrammeTransport.getHeurDepart()).isEqualTo(UPDATED_HEUR_DEPART);
        assertThat(testProgrammeTransport.getDateDepart()).isEqualTo(UPDATED_DATE_DEPART);
    }

    @Test
    @Transactional
    public void updateNonExistingProgrammeTransport() throws Exception {
        int databaseSizeBeforeUpdate = programmeTransportRepository.findAll().size();

        // Create the ProgrammeTransport
        ProgrammeTransportDTO programmeTransportDTO = programmeTransportMapper.toDto(programmeTransport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammeTransportMockMvc.perform(put("/api/programme-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programmeTransportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammeTransport in the database
        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgrammeTransport() throws Exception {
        // Initialize the database
        programmeTransportRepository.saveAndFlush(programmeTransport);

        int databaseSizeBeforeDelete = programmeTransportRepository.findAll().size();

        // Delete the programmeTransport
        restProgrammeTransportMockMvc.perform(delete("/api/programme-transports/{id}", programmeTransport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgrammeTransport> programmeTransportList = programmeTransportRepository.findAll();
        assertThat(programmeTransportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
