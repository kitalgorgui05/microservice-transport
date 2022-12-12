package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.TransportApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Chauffeur;
import com.memoire.kital.raph.repository.ChauffeurRepository;
import com.memoire.kital.raph.service.ChauffeurService;
import com.memoire.kital.raph.service.dto.ChauffeurDTO;
import com.memoire.kital.raph.service.mapper.ChauffeurMapper;
import com.memoire.kital.raph.service.dto.ChauffeurCriteria;
import com.memoire.kital.raph.service.ChauffeurQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChauffeurResource} REST controller.
 */
@SpringBootTest(classes = { TransportApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ChauffeurResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @Autowired
    private ChauffeurMapper chauffeurMapper;

    @Autowired
    private ChauffeurService chauffeurService;

    @Autowired
    private ChauffeurQueryService chauffeurQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChauffeurMockMvc;

    private Chauffeur chauffeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chauffeur createEntity(EntityManager em) {
        Chauffeur chauffeur = new Chauffeur()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .cin(DEFAULT_CIN)
            .telephone(DEFAULT_TELEPHONE)
            .adresse(DEFAULT_ADRESSE);
        return chauffeur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chauffeur createUpdatedEntity(EntityManager em) {
        Chauffeur chauffeur = new Chauffeur()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .cin(UPDATED_CIN)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE);
        return chauffeur;
    }

    @BeforeEach
    public void initTest() {
        chauffeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createChauffeur() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();
        // Create the Chauffeur
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);
        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isCreated());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate + 1);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testChauffeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testChauffeur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testChauffeur.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testChauffeur.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testChauffeur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testChauffeur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createChauffeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();

        // Create the Chauffeur with an existing ID
        chauffeur.setId(null);
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setPrenom(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setNom(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setDateNaissance(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setLieuNaissance(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCinIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setCin(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = chauffeurRepository.findAll().size();
        // set the field null
        chauffeur.setTelephone(null);

        // Create the Chauffeur, which fails.
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);


        restChauffeurMockMvc.perform(post("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChauffeurs() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList
        restChauffeurMockMvc.perform(get("/api/chauffeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chauffeur.getId())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", chauffeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chauffeur.getId()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }


    @Test
    @Transactional
    public void getChauffeursByIdFiltering() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        String id = chauffeur.getId();

        defaultChauffeurShouldBeFound("id.equals=" + id);
        defaultChauffeurShouldNotBeFound("id.notEquals=" + id);

        defaultChauffeurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChauffeurShouldNotBeFound("id.greaterThan=" + id);

        defaultChauffeurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChauffeurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChauffeursByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom equals to DEFAULT_PRENOM
        defaultChauffeurShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the chauffeurList where prenom equals to UPDATED_PRENOM
        defaultChauffeurShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom not equals to DEFAULT_PRENOM
        defaultChauffeurShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the chauffeurList where prenom not equals to UPDATED_PRENOM
        defaultChauffeurShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultChauffeurShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the chauffeurList where prenom equals to UPDATED_PRENOM
        defaultChauffeurShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom is not null
        defaultChauffeurShouldBeFound("prenom.specified=true");

        // Get all the chauffeurList where prenom is null
        defaultChauffeurShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllChauffeursByPrenomContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom contains DEFAULT_PRENOM
        defaultChauffeurShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the chauffeurList where prenom contains UPDATED_PRENOM
        defaultChauffeurShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where prenom does not contain DEFAULT_PRENOM
        defaultChauffeurShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the chauffeurList where prenom does not contain UPDATED_PRENOM
        defaultChauffeurShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllChauffeursByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom equals to DEFAULT_NOM
        defaultChauffeurShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the chauffeurList where nom equals to UPDATED_NOM
        defaultChauffeurShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom not equals to DEFAULT_NOM
        defaultChauffeurShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the chauffeurList where nom not equals to UPDATED_NOM
        defaultChauffeurShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByNomIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultChauffeurShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the chauffeurList where nom equals to UPDATED_NOM
        defaultChauffeurShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom is not null
        defaultChauffeurShouldBeFound("nom.specified=true");

        // Get all the chauffeurList where nom is null
        defaultChauffeurShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllChauffeursByNomContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom contains DEFAULT_NOM
        defaultChauffeurShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the chauffeurList where nom contains UPDATED_NOM
        defaultChauffeurShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChauffeursByNomNotContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where nom does not contain DEFAULT_NOM
        defaultChauffeurShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the chauffeurList where nom does not contain UPDATED_NOM
        defaultChauffeurShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllChauffeursByDateNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where dateNaissance equals to DEFAULT_DATE_NAISSANCE
        defaultChauffeurShouldBeFound("dateNaissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the chauffeurList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultChauffeurShouldNotBeFound("dateNaissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByDateNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where dateNaissance not equals to DEFAULT_DATE_NAISSANCE
        defaultChauffeurShouldNotBeFound("dateNaissance.notEquals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the chauffeurList where dateNaissance not equals to UPDATED_DATE_NAISSANCE
        defaultChauffeurShouldBeFound("dateNaissance.notEquals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByDateNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where dateNaissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultChauffeurShouldBeFound("dateNaissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the chauffeurList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultChauffeurShouldNotBeFound("dateNaissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByDateNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where dateNaissance is not null
        defaultChauffeurShouldBeFound("dateNaissance.specified=true");

        // Get all the chauffeurList where dateNaissance is null
        defaultChauffeurShouldNotBeFound("dateNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance equals to DEFAULT_LIEU_NAISSANCE
        defaultChauffeurShouldBeFound("lieuNaissance.equals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the chauffeurList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldNotBeFound("lieuNaissance.equals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance not equals to DEFAULT_LIEU_NAISSANCE
        defaultChauffeurShouldNotBeFound("lieuNaissance.notEquals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the chauffeurList where lieuNaissance not equals to UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldBeFound("lieuNaissance.notEquals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance in DEFAULT_LIEU_NAISSANCE or UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldBeFound("lieuNaissance.in=" + DEFAULT_LIEU_NAISSANCE + "," + UPDATED_LIEU_NAISSANCE);

        // Get all the chauffeurList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldNotBeFound("lieuNaissance.in=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance is not null
        defaultChauffeurShouldBeFound("lieuNaissance.specified=true");

        // Get all the chauffeurList where lieuNaissance is null
        defaultChauffeurShouldNotBeFound("lieuNaissance.specified=false");
    }
                @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance contains DEFAULT_LIEU_NAISSANCE
        defaultChauffeurShouldBeFound("lieuNaissance.contains=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the chauffeurList where lieuNaissance contains UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldNotBeFound("lieuNaissance.contains=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByLieuNaissanceNotContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where lieuNaissance does not contain DEFAULT_LIEU_NAISSANCE
        defaultChauffeurShouldNotBeFound("lieuNaissance.doesNotContain=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the chauffeurList where lieuNaissance does not contain UPDATED_LIEU_NAISSANCE
        defaultChauffeurShouldBeFound("lieuNaissance.doesNotContain=" + UPDATED_LIEU_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllChauffeursByCinIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin equals to DEFAULT_CIN
        defaultChauffeurShouldBeFound("cin.equals=" + DEFAULT_CIN);

        // Get all the chauffeurList where cin equals to UPDATED_CIN
        defaultChauffeurShouldNotBeFound("cin.equals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllChauffeursByCinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin not equals to DEFAULT_CIN
        defaultChauffeurShouldNotBeFound("cin.notEquals=" + DEFAULT_CIN);

        // Get all the chauffeurList where cin not equals to UPDATED_CIN
        defaultChauffeurShouldBeFound("cin.notEquals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllChauffeursByCinIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin in DEFAULT_CIN or UPDATED_CIN
        defaultChauffeurShouldBeFound("cin.in=" + DEFAULT_CIN + "," + UPDATED_CIN);

        // Get all the chauffeurList where cin equals to UPDATED_CIN
        defaultChauffeurShouldNotBeFound("cin.in=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllChauffeursByCinIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin is not null
        defaultChauffeurShouldBeFound("cin.specified=true");

        // Get all the chauffeurList where cin is null
        defaultChauffeurShouldNotBeFound("cin.specified=false");
    }
                @Test
    @Transactional
    public void getAllChauffeursByCinContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin contains DEFAULT_CIN
        defaultChauffeurShouldBeFound("cin.contains=" + DEFAULT_CIN);

        // Get all the chauffeurList where cin contains UPDATED_CIN
        defaultChauffeurShouldNotBeFound("cin.contains=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllChauffeursByCinNotContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where cin does not contain DEFAULT_CIN
        defaultChauffeurShouldNotBeFound("cin.doesNotContain=" + DEFAULT_CIN);

        // Get all the chauffeurList where cin does not contain UPDATED_CIN
        defaultChauffeurShouldBeFound("cin.doesNotContain=" + UPDATED_CIN);
    }


    @Test
    @Transactional
    public void getAllChauffeursByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone equals to DEFAULT_TELEPHONE
        defaultChauffeurShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the chauffeurList where telephone equals to UPDATED_TELEPHONE
        defaultChauffeurShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone not equals to DEFAULT_TELEPHONE
        defaultChauffeurShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the chauffeurList where telephone not equals to UPDATED_TELEPHONE
        defaultChauffeurShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultChauffeurShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the chauffeurList where telephone equals to UPDATED_TELEPHONE
        defaultChauffeurShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone is not null
        defaultChauffeurShouldBeFound("telephone.specified=true");

        // Get all the chauffeurList where telephone is null
        defaultChauffeurShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllChauffeursByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone contains DEFAULT_TELEPHONE
        defaultChauffeurShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the chauffeurList where telephone contains UPDATED_TELEPHONE
        defaultChauffeurShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllChauffeursByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurList where telephone does not contain DEFAULT_TELEPHONE
        defaultChauffeurShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the chauffeurList where telephone does not contain UPDATED_TELEPHONE
        defaultChauffeurShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChauffeurShouldBeFound(String filter) throws Exception {
        restChauffeurMockMvc.perform(get("/api/chauffeurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chauffeur.getId())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));

        // Check, that the count call also returns 1
        restChauffeurMockMvc.perform(get("/api/chauffeurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChauffeurShouldNotBeFound(String filter) throws Exception {
        restChauffeurMockMvc.perform(get("/api/chauffeurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChauffeurMockMvc.perform(get("/api/chauffeurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingChauffeur() throws Exception {
        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur
        Chauffeur updatedChauffeur = chauffeurRepository.findById(chauffeur.getId()).get();
        // Disconnect from session so that the updates on updatedChauffeur are not directly saved in db
        em.detach(updatedChauffeur);
        updatedChauffeur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .cin(UPDATED_CIN)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE);
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(updatedChauffeur);

        restChauffeurMockMvc.perform(put("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testChauffeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testChauffeur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testChauffeur.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testChauffeur.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testChauffeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testChauffeur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Create the Chauffeur
        ChauffeurDTO chauffeurDTO = chauffeurMapper.toDto(chauffeur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChauffeurMockMvc.perform(put("/api/chauffeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chauffeurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        int databaseSizeBeforeDelete = chauffeurRepository.findAll().size();

        // Delete the chauffeur
        restChauffeurMockMvc.perform(delete("/api/chauffeurs/{id}", chauffeur.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
