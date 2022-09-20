package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.TransportApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Zone;
import com.memoire.kital.raph.domain.GroupeTransport;
import com.memoire.kital.raph.repository.ZoneRepository;
import com.memoire.kital.raph.service.ZoneService;
import com.memoire.kital.raph.service.dto.ZoneDTO;
import com.memoire.kital.raph.service.mapper.ZoneMapper;
import com.memoire.kital.raph.service.dto.ZoneCriteria;
import com.memoire.kital.raph.service.ZoneQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ZoneResource} REST controller.
 */
@SpringBootTest(classes = { TransportApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ZoneResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneQueryService zoneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZoneMockMvc;

    private Zone zone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createEntity(EntityManager em) {
        Zone zone = new Zone()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION);
        return zone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createUpdatedEntity(EntityManager em) {
        Zone zone = new Zone()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);
        return zone;
    }

    @BeforeEach
    public void initTest() {
        zone = createEntity(em);
    }

    @Test
    @Transactional
    public void createZone() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();
        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);
        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate + 1);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testZone.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();

        // Create the Zone with an existing ID
        zone.setId(1L);
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneRepository.findAll().size();
        // set the field null
        zone.setLibelle(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);


        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZones() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", zone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zone.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }


    @Test
    @Transactional
    public void getZonesByIdFiltering() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        Long id = zone.getId();

        defaultZoneShouldBeFound("id.equals=" + id);
        defaultZoneShouldNotBeFound("id.notEquals=" + id);

        defaultZoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.greaterThan=" + id);

        defaultZoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllZonesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle equals to DEFAULT_LIBELLE
        defaultZoneShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the zoneList where libelle equals to UPDATED_LIBELLE
        defaultZoneShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllZonesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle not equals to DEFAULT_LIBELLE
        defaultZoneShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the zoneList where libelle not equals to UPDATED_LIBELLE
        defaultZoneShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllZonesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultZoneShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the zoneList where libelle equals to UPDATED_LIBELLE
        defaultZoneShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllZonesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle is not null
        defaultZoneShouldBeFound("libelle.specified=true");

        // Get all the zoneList where libelle is null
        defaultZoneShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle contains DEFAULT_LIBELLE
        defaultZoneShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the zoneList where libelle contains UPDATED_LIBELLE
        defaultZoneShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllZonesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where libelle does not contain DEFAULT_LIBELLE
        defaultZoneShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the zoneList where libelle does not contain UPDATED_LIBELLE
        defaultZoneShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllZonesByGroupetransportsIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        GroupeTransport groupetransports = GroupeTransportResourceIT.createEntity(em);
        em.persist(groupetransports);
        em.flush();
        zone.addGroupetransports(groupetransports);
        zoneRepository.saveAndFlush(zone);
        Long groupetransportsId = groupetransports.getId();

        // Get all the zoneList where groupetransports equals to groupetransportsId
        defaultZoneShouldBeFound("groupetransportsId.equals=" + groupetransportsId);

        // Get all the zoneList where groupetransports equals to groupetransportsId + 1
        defaultZoneShouldNotBeFound("groupetransportsId.equals=" + (groupetransportsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZoneShouldBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZoneShouldNotBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingZone() throws Exception {
        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Update the zone
        Zone updatedZone = zoneRepository.findById(zone.getId()).get();
        // Disconnect from session so that the updates on updatedZone are not directly saved in db
        em.detach(updatedZone);
        updatedZone
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);
        ZoneDTO zoneDTO = zoneMapper.toDto(updatedZone);

        restZoneMockMvc.perform(put("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isOk());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testZone.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingZone() throws Exception {
        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneMockMvc.perform(put("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeDelete = zoneRepository.findAll().size();

        // Delete the zone
        restZoneMockMvc.perform(delete("/api/zones/{id}", zone.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
