package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.TransportApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.GroupeTransport;
import com.memoire.kital.raph.domain.Zone;
import com.memoire.kital.raph.repository.GroupeTransportRepository;
import com.memoire.kital.raph.service.GroupeTransportService;
import com.memoire.kital.raph.service.dto.GroupeTransportDTO;
import com.memoire.kital.raph.service.mapper.GroupeTransportMapper;
import com.memoire.kital.raph.service.dto.GroupeTransportCriteria;
import com.memoire.kital.raph.service.GroupeTransportQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupeTransportResource} REST controller.
 */
@SpringBootTest(classes = { TransportApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupeTransportResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_ELEVES = 1;
    private static final Integer UPDATED_NOMBRE_ELEVES = 2;
    private static final Integer SMALLER_NOMBRE_ELEVES = 1 - 1;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    @Autowired
    private GroupeTransportRepository groupeTransportRepository;

    @Mock
    private GroupeTransportRepository groupeTransportRepositoryMock;

    @Autowired
    private GroupeTransportMapper groupeTransportMapper;

    @Mock
    private GroupeTransportService groupeTransportServiceMock;

    @Autowired
    private GroupeTransportService groupeTransportService;

    @Autowired
    private GroupeTransportQueryService groupeTransportQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupeTransportMockMvc;

    private GroupeTransport groupeTransport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupeTransport createEntity(EntityManager em) {
        GroupeTransport groupeTransport = new GroupeTransport()
            .nom(DEFAULT_NOM)
            .nombreEleves(DEFAULT_NOMBRE_ELEVES)
            .etat(DEFAULT_ETAT);
        // Add required entity
        Zone zone;
        if (TestUtil.findAll(em, Zone.class).isEmpty()) {
            zone = ZoneResourceIT.createEntity(em);
            em.persist(zone);
            em.flush();
        } else {
            zone = TestUtil.findAll(em, Zone.class).get(0);
        }
        groupeTransport.getZones().add(zone);
        return groupeTransport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupeTransport createUpdatedEntity(EntityManager em) {
        GroupeTransport groupeTransport = new GroupeTransport()
            .nom(UPDATED_NOM)
            .nombreEleves(UPDATED_NOMBRE_ELEVES)
            .etat(UPDATED_ETAT);
        // Add required entity
        Zone zone;
        if (TestUtil.findAll(em, Zone.class).isEmpty()) {
            zone = ZoneResourceIT.createUpdatedEntity(em);
            em.persist(zone);
            em.flush();
        } else {
            zone = TestUtil.findAll(em, Zone.class).get(0);
        }
        groupeTransport.getZones().add(zone);
        return groupeTransport;
    }

    @BeforeEach
    public void initTest() {
        groupeTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupeTransport() throws Exception {
        int databaseSizeBeforeCreate = groupeTransportRepository.findAll().size();
        // Create the GroupeTransport
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(groupeTransport);
        restGroupeTransportMockMvc.perform(post("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupeTransport in the database
        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeCreate + 1);
        GroupeTransport testGroupeTransport = groupeTransportList.get(groupeTransportList.size() - 1);
        assertThat(testGroupeTransport.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGroupeTransport.getNombreEleves()).isEqualTo(DEFAULT_NOMBRE_ELEVES);
        assertThat(testGroupeTransport.isEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createGroupeTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeTransportRepository.findAll().size();

        // Create the GroupeTransport with an existing ID
        groupeTransport.setId(null);
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(groupeTransport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeTransportMockMvc.perform(post("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeTransport in the database
        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeTransportRepository.findAll().size();
        // set the field null
        groupeTransport.setNom(null);

        // Create the GroupeTransport, which fails.
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(groupeTransport);


        restGroupeTransportMockMvc.perform(post("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isBadRequest());

        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreElevesIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeTransportRepository.findAll().size();
        // set the field null
        groupeTransport.setNombreEleves(null);

        // Create the GroupeTransport, which fails.
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(groupeTransport);


        restGroupeTransportMockMvc.perform(post("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isBadRequest());

        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupeTransports() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeTransport.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreEleves").value(hasItem(DEFAULT_NOMBRE_ELEVES)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupeTransportsWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupeTransportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupeTransportMockMvc.perform(get("/api/groupe-transports?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupeTransportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupeTransportsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupeTransportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupeTransportMockMvc.perform(get("/api/groupe-transports?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupeTransportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupeTransport() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get the groupeTransport
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports/{id}", groupeTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupeTransport.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nombreEleves").value(DEFAULT_NOMBRE_ELEVES))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()));
    }


    @Test
    @Transactional
    public void getGroupeTransportsByIdFiltering() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        String id = groupeTransport.getId();

        defaultGroupeTransportShouldBeFound("id.equals=" + id);
        defaultGroupeTransportShouldNotBeFound("id.notEquals=" + id);

        defaultGroupeTransportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGroupeTransportShouldNotBeFound("id.greaterThan=" + id);

        defaultGroupeTransportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGroupeTransportShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGroupeTransportsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom equals to DEFAULT_NOM
        defaultGroupeTransportShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the groupeTransportList where nom equals to UPDATED_NOM
        defaultGroupeTransportShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom not equals to DEFAULT_NOM
        defaultGroupeTransportShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the groupeTransportList where nom not equals to UPDATED_NOM
        defaultGroupeTransportShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultGroupeTransportShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the groupeTransportList where nom equals to UPDATED_NOM
        defaultGroupeTransportShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom is not null
        defaultGroupeTransportShouldBeFound("nom.specified=true");

        // Get all the groupeTransportList where nom is null
        defaultGroupeTransportShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupeTransportsByNomContainsSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom contains DEFAULT_NOM
        defaultGroupeTransportShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the groupeTransportList where nom contains UPDATED_NOM
        defaultGroupeTransportShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nom does not contain DEFAULT_NOM
        defaultGroupeTransportShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the groupeTransportList where nom does not contain UPDATED_NOM
        defaultGroupeTransportShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves equals to DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.equals=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.equals=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves not equals to DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.notEquals=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves not equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.notEquals=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsInShouldWork() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves in DEFAULT_NOMBRE_ELEVES or UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.in=" + DEFAULT_NOMBRE_ELEVES + "," + UPDATED_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.in=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves is not null
        defaultGroupeTransportShouldBeFound("nombreEleves.specified=true");

        // Get all the groupeTransportList where nombreEleves is null
        defaultGroupeTransportShouldNotBeFound("nombreEleves.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves is greater than or equal to DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.greaterThanOrEqual=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves is greater than or equal to UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.greaterThanOrEqual=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves is less than or equal to DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.lessThanOrEqual=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves is less than or equal to SMALLER_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.lessThanOrEqual=" + SMALLER_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsLessThanSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves is less than DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.lessThan=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves is less than UPDATED_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.lessThan=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByNombreElevesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where nombreEleves is greater than DEFAULT_NOMBRE_ELEVES
        defaultGroupeTransportShouldNotBeFound("nombreEleves.greaterThan=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeTransportList where nombreEleves is greater than SMALLER_NOMBRE_ELEVES
        defaultGroupeTransportShouldBeFound("nombreEleves.greaterThan=" + SMALLER_NOMBRE_ELEVES);
    }


    @Test
    @Transactional
    public void getAllGroupeTransportsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where etat equals to DEFAULT_ETAT
        defaultGroupeTransportShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the groupeTransportList where etat equals to UPDATED_ETAT
        defaultGroupeTransportShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where etat not equals to DEFAULT_ETAT
        defaultGroupeTransportShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the groupeTransportList where etat not equals to UPDATED_ETAT
        defaultGroupeTransportShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultGroupeTransportShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the groupeTransportList where etat equals to UPDATED_ETAT
        defaultGroupeTransportShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        // Get all the groupeTransportList where etat is not null
        defaultGroupeTransportShouldBeFound("etat.specified=true");

        // Get all the groupeTransportList where etat is null
        defaultGroupeTransportShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupeTransportsByZonesIsEqualToSomething() throws Exception {
        // Get already existing entity
        Zone zones = (Zone) groupeTransport.getZones();
        groupeTransportRepository.saveAndFlush(groupeTransport);
        String zonesId = zones.getId();

        // Get all the groupeTransportList where zones equals to zonesId
        defaultGroupeTransportShouldBeFound("zonesId.equals=" + zonesId);

        // Get all the groupeTransportList where zones equals to zonesId + 1
        defaultGroupeTransportShouldNotBeFound("zonesId.equals=" + (zonesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupeTransportShouldBeFound(String filter) throws Exception {
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeTransport.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreEleves").value(hasItem(DEFAULT_NOMBRE_ELEVES)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));

        // Check, that the count call also returns 1
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupeTransportShouldNotBeFound(String filter) throws Exception {
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGroupeTransport() throws Exception {
        // Get the groupeTransport
        restGroupeTransportMockMvc.perform(get("/api/groupe-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupeTransport() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        int databaseSizeBeforeUpdate = groupeTransportRepository.findAll().size();

        // Update the groupeTransport
        GroupeTransport updatedGroupeTransport = groupeTransportRepository.findById(groupeTransport.getId()).get();
        // Disconnect from session so that the updates on updatedGroupeTransport are not directly saved in db
        em.detach(updatedGroupeTransport);
        updatedGroupeTransport
            .nom(UPDATED_NOM)
            .nombreEleves(UPDATED_NOMBRE_ELEVES)
            .etat(UPDATED_ETAT);
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(updatedGroupeTransport);

        restGroupeTransportMockMvc.perform(put("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isOk());

        // Validate the GroupeTransport in the database
        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeUpdate);
        GroupeTransport testGroupeTransport = groupeTransportList.get(groupeTransportList.size() - 1);
        assertThat(testGroupeTransport.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGroupeTransport.getNombreEleves()).isEqualTo(UPDATED_NOMBRE_ELEVES);
        assertThat(testGroupeTransport.isEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupeTransport() throws Exception {
        int databaseSizeBeforeUpdate = groupeTransportRepository.findAll().size();

        // Create the GroupeTransport
        GroupeTransportDTO groupeTransportDTO = groupeTransportMapper.toDto(groupeTransport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeTransportMockMvc.perform(put("/api/groupe-transports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeTransportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeTransport in the database
        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupeTransport() throws Exception {
        // Initialize the database
        groupeTransportRepository.saveAndFlush(groupeTransport);

        int databaseSizeBeforeDelete = groupeTransportRepository.findAll().size();

        // Delete the groupeTransport
        restGroupeTransportMockMvc.perform(delete("/api/groupe-transports/{id}", groupeTransport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupeTransport> groupeTransportList = groupeTransportRepository.findAll();
        assertThat(groupeTransportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
