package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.TransportApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Bus;
import com.memoire.kital.raph.domain.Chauffeur;
import com.memoire.kital.raph.repository.BusRepository;
import com.memoire.kital.raph.service.BusService;
import com.memoire.kital.raph.service.dto.BusDTO;
import com.memoire.kital.raph.service.mapper.BusMapper;
import com.memoire.kital.raph.service.dto.BusCriteria;
import com.memoire.kital.raph.service.BusQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BusResource} REST controller.
 */
@SpringBootTest(classes = { TransportApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BusResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBREPLACE = 1;
    private static final Integer UPDATED_NOMBREPLACE = 2;
    private static final Integer SMALLER_NOMBREPLACE = 1 - 1;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusMapper busMapper;

    @Autowired
    private BusService busService;

    @Autowired
    private BusQueryService busQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusMockMvc;

    private Bus bus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createEntity(EntityManager em) {
        Bus bus = new Bus()
            .matricule(DEFAULT_MATRICULE)
            .numero(DEFAULT_NUMERO)
            .nombreplace(DEFAULT_NOMBREPLACE);
        return bus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createUpdatedEntity(EntityManager em) {
        Bus bus = new Bus()
            .matricule(UPDATED_MATRICULE)
            .numero(UPDATED_NUMERO)
            .nombreplace(UPDATED_NOMBREPLACE);
        return bus;
    }

    @BeforeEach
    public void initTest() {
        bus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();
        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);
        restBusMockMvc.perform(post("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testBus.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testBus.getNombreplace()).isEqualTo(DEFAULT_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void createBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus with an existing ID
        bus.setId(null);
        BusDTO busDTO = busMapper.toDto(bus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusMockMvc.perform(post("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = busRepository.findAll().size();
        // set the field null
        bus.setMatricule(null);

        // Create the Bus, which fails.
        BusDTO busDTO = busMapper.toDto(bus);


        restBusMockMvc.perform(post("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isBadRequest());

        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = busRepository.findAll().size();
        // set the field null
        bus.setNumero(null);

        // Create the Bus, which fails.
        BusDTO busDTO = busMapper.toDto(bus);


        restBusMockMvc.perform(post("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isBadRequest());

        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreplaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = busRepository.findAll().size();
        // set the field null
        bus.setNombreplace(null);

        // Create the Bus, which fails.
        BusDTO busDTO = busMapper.toDto(bus);


        restBusMockMvc.perform(post("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isBadRequest());

        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuses() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList
        restBusMockMvc.perform(get("/api/buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nombreplace").value(hasItem(DEFAULT_NOMBREPLACE)));
    }

    @Test
    @Transactional
    public void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bus.getId()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nombreplace").value(DEFAULT_NOMBREPLACE));
    }


    @Test
    @Transactional
    public void getBusesByIdFiltering() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        String id = bus.getId();

        defaultBusShouldBeFound("id.equals=" + id);
        defaultBusShouldNotBeFound("id.notEquals=" + id);

        defaultBusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusShouldNotBeFound("id.greaterThan=" + id);

        defaultBusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBusesByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule equals to DEFAULT_MATRICULE
        defaultBusShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the busList where matricule equals to UPDATED_MATRICULE
        defaultBusShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllBusesByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule not equals to DEFAULT_MATRICULE
        defaultBusShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the busList where matricule not equals to UPDATED_MATRICULE
        defaultBusShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllBusesByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultBusShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the busList where matricule equals to UPDATED_MATRICULE
        defaultBusShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllBusesByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule is not null
        defaultBusShouldBeFound("matricule.specified=true");

        // Get all the busList where matricule is null
        defaultBusShouldNotBeFound("matricule.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusesByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule contains DEFAULT_MATRICULE
        defaultBusShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the busList where matricule contains UPDATED_MATRICULE
        defaultBusShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllBusesByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where matricule does not contain DEFAULT_MATRICULE
        defaultBusShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the busList where matricule does not contain UPDATED_MATRICULE
        defaultBusShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllBusesByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero equals to DEFAULT_NUMERO
        defaultBusShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the busList where numero equals to UPDATED_NUMERO
        defaultBusShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllBusesByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero not equals to DEFAULT_NUMERO
        defaultBusShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the busList where numero not equals to UPDATED_NUMERO
        defaultBusShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllBusesByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultBusShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the busList where numero equals to UPDATED_NUMERO
        defaultBusShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllBusesByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero is not null
        defaultBusShouldBeFound("numero.specified=true");

        // Get all the busList where numero is null
        defaultBusShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusesByNumeroContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero contains DEFAULT_NUMERO
        defaultBusShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the busList where numero contains UPDATED_NUMERO
        defaultBusShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllBusesByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where numero does not contain DEFAULT_NUMERO
        defaultBusShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the busList where numero does not contain UPDATED_NUMERO
        defaultBusShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace equals to DEFAULT_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.equals=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace equals to UPDATED_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.equals=" + UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace not equals to DEFAULT_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.notEquals=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace not equals to UPDATED_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.notEquals=" + UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace in DEFAULT_NOMBREPLACE or UPDATED_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.in=" + DEFAULT_NOMBREPLACE + "," + UPDATED_NOMBREPLACE);

        // Get all the busList where nombreplace equals to UPDATED_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.in=" + UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace is not null
        defaultBusShouldBeFound("nombreplace.specified=true");

        // Get all the busList where nombreplace is null
        defaultBusShouldNotBeFound("nombreplace.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace is greater than or equal to DEFAULT_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.greaterThanOrEqual=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace is greater than or equal to UPDATED_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.greaterThanOrEqual=" + UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace is less than or equal to DEFAULT_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.lessThanOrEqual=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace is less than or equal to SMALLER_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.lessThanOrEqual=" + SMALLER_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsLessThanSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace is less than DEFAULT_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.lessThan=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace is less than UPDATED_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.lessThan=" + UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void getAllBusesByNombreplaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where nombreplace is greater than DEFAULT_NOMBREPLACE
        defaultBusShouldNotBeFound("nombreplace.greaterThan=" + DEFAULT_NOMBREPLACE);

        // Get all the busList where nombreplace is greater than SMALLER_NOMBREPLACE
        defaultBusShouldBeFound("nombreplace.greaterThan=" + SMALLER_NOMBREPLACE);
    }


    @Test
    @Transactional
    public void getAllBusesByChauffeurIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);
        Chauffeur chauffeur = ChauffeurResourceIT.createEntity(em);
        em.persist(chauffeur);
        em.flush();
        bus.setChauffeur(chauffeur);
        busRepository.saveAndFlush(bus);
        String chauffeurId = chauffeur.getId();

        // Get all the busList where chauffeur equals to chauffeurId
        defaultBusShouldBeFound("chauffeurId.equals=" + chauffeurId);

        // Get all the busList where chauffeur equals to chauffeurId + 1
        defaultBusShouldNotBeFound("chauffeurId.equals=" + (chauffeurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusShouldBeFound(String filter) throws Exception {
        restBusMockMvc.perform(get("/api/buses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nombreplace").value(hasItem(DEFAULT_NOMBREPLACE)));

        // Check, that the count call also returns 1
        restBusMockMvc.perform(get("/api/buses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusShouldNotBeFound(String filter) throws Exception {
        restBusMockMvc.perform(get("/api/buses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusMockMvc.perform(get("/api/buses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        Bus updatedBus = busRepository.findById(bus.getId()).get();
        // Disconnect from session so that the updates on updatedBus are not directly saved in db
        em.detach(updatedBus);
        updatedBus
            .matricule(UPDATED_MATRICULE)
            .numero(UPDATED_NUMERO)
            .nombreplace(UPDATED_NOMBREPLACE);
        BusDTO busDTO = busMapper.toDto(updatedBus);

        restBusMockMvc.perform(put("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testBus.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testBus.getNombreplace()).isEqualTo(UPDATED_NOMBREPLACE);
    }

    @Test
    @Transactional
    public void updateNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc.perform(put("/api/buses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(busDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Delete the bus
        restBusMockMvc.perform(delete("/api/buses/{id}", bus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
