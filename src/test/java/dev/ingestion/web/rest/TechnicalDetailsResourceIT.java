package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.domain.TechnicalDetails;
import dev.ingestion.repository.TechnicalDetailsRepository;
import dev.ingestion.service.TechnicalDetailsService;
import dev.ingestion.service.dto.TechnicalDetailsDTO;
import dev.ingestion.service.mapper.TechnicalDetailsMapper;
import dev.ingestion.web.rest.errors.ExceptionTranslator;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

/**
 * Integration tests for the {@link TechnicalDetailsResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class TechnicalDetailsResourceIT {

    private static final Long DEFAULT_TECHNICAL_REQUEST_ID = 1L;
    private static final Long UPDATED_TECHNICAL_REQUEST_ID = 2L;
    private static final Long SMALLER_TECHNICAL_REQUEST_ID = 1L - 1L;

    private static final String DEFAULT_DATA_LOCATION_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DATA_LOCATION_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_REFRESH_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_DATA_REFRESH_FREQUENCY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TARGET_INGESTION_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARGET_INGESTION_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TARGET_INGESTION_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TARGET_INGESTION_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARGET_INGESTION_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TARGET_INGESTION_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TARGET_PATH = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DATASET_TYPE_INGESTION_REF = "AAAAAAAAAA";
    private static final String UPDATED_DATASET_TYPE_INGESTION_REF = "BBBBBBBBBB";

    private static final String DEFAULT_GUEST_USERS_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_GUEST_USERS_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WHITELIST_IP_ADDRESSES = "AAAAAAAAAA";
    private static final String UPDATED_WHITELIST_IP_ADDRESSES = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_STAGING_CONTAINER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_STAGING_CONTAINER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_DATA_SOURCE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_DATA_SOURCE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_GSK_ACCESS_SOURCE_LOCATION_REF = "AAAAAAAAAA";
    private static final String UPDATED_GSK_ACCESS_SOURCE_LOCATION_REF = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_SOURCE_SECRET_KEY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_SOURCE_SECRET_KEY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_REQUEST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXISTING_DATA_LOCATION_IDENTIFIED = "AAAAAAAAAA";
    private static final String UPDATED_EXISTING_DATA_LOCATION_IDENTIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_DATE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_MODIFIED_DATE = Instant.ofEpochMilli(-1L);

    @Autowired
    private TechnicalDetailsRepository technicalDetailsRepository;

    @Autowired
    private TechnicalDetailsMapper technicalDetailsMapper;

    @Autowired
    private TechnicalDetailsService technicalDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTechnicalDetailsMockMvc;

    private TechnicalDetails technicalDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechnicalDetailsResource technicalDetailsResource = new TechnicalDetailsResource(technicalDetailsService);
        this.restTechnicalDetailsMockMvc = MockMvcBuilders.standaloneSetup(technicalDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator)
            .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicalDetails createEntity(EntityManager em) {
        TechnicalDetails technicalDetails = new TechnicalDetails()
            .technicalRequestId(DEFAULT_TECHNICAL_REQUEST_ID)
            .dataLocationPath(DEFAULT_DATA_LOCATION_PATH)
            .dataRefreshFrequency(DEFAULT_DATA_REFRESH_FREQUENCY)
            .targetIngestionStartDate(DEFAULT_TARGET_INGESTION_START_DATE)
            .targetIngestionEndDate(DEFAULT_TARGET_INGESTION_END_DATE)
            .targetPath(DEFAULT_TARGET_PATH)
            .datasetTypeIngestionRef(DEFAULT_DATASET_TYPE_INGESTION_REF)
            .guestUsersEmail(DEFAULT_GUEST_USERS_EMAIL)
            .whitelistIpAddresses(DEFAULT_WHITELIST_IP_ADDRESSES)
            .externalStagingContainerName(DEFAULT_EXTERNAL_STAGING_CONTAINER_NAME)
            .externalDataSourceLocation(DEFAULT_EXTERNAL_DATA_SOURCE_LOCATION)
            .gskAccessSourceLocationRef(DEFAULT_GSK_ACCESS_SOURCE_LOCATION_REF)
            .externalSourceSecretKeyName(DEFAULT_EXTERNAL_SOURCE_SECRET_KEY_NAME)
            .domainRequestId(DEFAULT_DOMAIN_REQUEST_ID)
            .existingDataLocationIdentified(DEFAULT_EXISTING_DATA_LOCATION_IDENTIFIED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        // Add required entity
        IngestionRequestDetails ingestionRequestDetails;
        if (TestUtil.findAll(em, IngestionRequestDetails.class).isEmpty()) {
            ingestionRequestDetails = IngestionRequestDetailsResourceIT.createEntity(em);
            em.persist(ingestionRequestDetails);
            em.flush();
        } else {
            ingestionRequestDetails = TestUtil.findAll(em, IngestionRequestDetails.class).get(0);
        }
        technicalDetails.setIngestionRequestId(ingestionRequestDetails);
        return technicalDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicalDetails createUpdatedEntity(EntityManager em) {
        TechnicalDetails technicalDetails = new TechnicalDetails()
            .technicalRequestId(UPDATED_TECHNICAL_REQUEST_ID)
            .dataLocationPath(UPDATED_DATA_LOCATION_PATH)
            .dataRefreshFrequency(UPDATED_DATA_REFRESH_FREQUENCY)
            .targetIngestionStartDate(UPDATED_TARGET_INGESTION_START_DATE)
            .targetIngestionEndDate(UPDATED_TARGET_INGESTION_END_DATE)
            .targetPath(UPDATED_TARGET_PATH)
            .datasetTypeIngestionRef(UPDATED_DATASET_TYPE_INGESTION_REF)
            .guestUsersEmail(UPDATED_GUEST_USERS_EMAIL)
            .whitelistIpAddresses(UPDATED_WHITELIST_IP_ADDRESSES)
            .externalStagingContainerName(UPDATED_EXTERNAL_STAGING_CONTAINER_NAME)
            .externalDataSourceLocation(UPDATED_EXTERNAL_DATA_SOURCE_LOCATION)
            .gskAccessSourceLocationRef(UPDATED_GSK_ACCESS_SOURCE_LOCATION_REF)
            .externalSourceSecretKeyName(UPDATED_EXTERNAL_SOURCE_SECRET_KEY_NAME)
            .domainRequestId(UPDATED_DOMAIN_REQUEST_ID)
            .existingDataLocationIdentified(UPDATED_EXISTING_DATA_LOCATION_IDENTIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        // Add required entity
        IngestionRequestDetails ingestionRequestDetails;
        if (TestUtil.findAll(em, IngestionRequestDetails.class).isEmpty()) {
            ingestionRequestDetails = IngestionRequestDetailsResourceIT.createUpdatedEntity(em);
            em.persist(ingestionRequestDetails);
            em.flush();
        } else {
            ingestionRequestDetails = TestUtil.findAll(em, IngestionRequestDetails.class).get(0);
        }
        technicalDetails.setIngestionRequestId(ingestionRequestDetails);
        return technicalDetails;
    }

    @BeforeEach
    public void initTest() {
        technicalDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnicalDetails() throws Exception {
        int databaseSizeBeforeCreate = technicalDetailsRepository.findAll().size();

        // Create the TechnicalDetails
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);
        restTechnicalDetailsMockMvc
            .perform(
                post("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TechnicalDetails in the database
        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TechnicalDetails testTechnicalDetails = technicalDetailsList.get(technicalDetailsList.size() - 1);
        assertThat(testTechnicalDetails.getTechnicalRequestId()).isEqualTo(DEFAULT_TECHNICAL_REQUEST_ID);
        assertThat(testTechnicalDetails.getDataLocationPath()).isEqualTo(DEFAULT_DATA_LOCATION_PATH);
        assertThat(testTechnicalDetails.getDataRefreshFrequency()).isEqualTo(DEFAULT_DATA_REFRESH_FREQUENCY);
        assertThat(testTechnicalDetails.getTargetIngestionStartDate()).isEqualTo(DEFAULT_TARGET_INGESTION_START_DATE);
        assertThat(testTechnicalDetails.getTargetIngestionEndDate()).isEqualTo(DEFAULT_TARGET_INGESTION_END_DATE);
        assertThat(testTechnicalDetails.getTargetPath()).isEqualTo(DEFAULT_TARGET_PATH);
        assertThat(testTechnicalDetails.getDatasetTypeIngestionRef()).isEqualTo(DEFAULT_DATASET_TYPE_INGESTION_REF);
        assertThat(testTechnicalDetails.getGuestUsersEmail()).isEqualTo(DEFAULT_GUEST_USERS_EMAIL);
        assertThat(testTechnicalDetails.getWhitelistIpAddresses()).isEqualTo(DEFAULT_WHITELIST_IP_ADDRESSES);
        assertThat(testTechnicalDetails.getExternalStagingContainerName()).isEqualTo(DEFAULT_EXTERNAL_STAGING_CONTAINER_NAME);
        assertThat(testTechnicalDetails.getExternalDataSourceLocation()).isEqualTo(DEFAULT_EXTERNAL_DATA_SOURCE_LOCATION);
        assertThat(testTechnicalDetails.getGskAccessSourceLocationRef()).isEqualTo(DEFAULT_GSK_ACCESS_SOURCE_LOCATION_REF);
        assertThat(testTechnicalDetails.getExternalSourceSecretKeyName()).isEqualTo(DEFAULT_EXTERNAL_SOURCE_SECRET_KEY_NAME);
        assertThat(testTechnicalDetails.getDomainRequestId()).isEqualTo(DEFAULT_DOMAIN_REQUEST_ID);
        assertThat(testTechnicalDetails.getExistingDataLocationIdentified()).isEqualTo(DEFAULT_EXISTING_DATA_LOCATION_IDENTIFIED);
        assertThat(testTechnicalDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTechnicalDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTechnicalDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTechnicalDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createTechnicalDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technicalDetailsRepository.findAll().size();

        // Create the TechnicalDetails with an existing ID
        technicalDetails.setId(1L);
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnicalDetailsMockMvc
            .perform(
                post("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicalDetails in the database
        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTechnicalRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicalDetailsRepository.findAll().size();
        // set the field null
        technicalDetails.setTechnicalRequestId(null);

        // Create the TechnicalDetails, which fails.
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);

        restTechnicalDetailsMockMvc
            .perform(
                post("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicalDetailsRepository.findAll().size();
        // set the field null
        technicalDetails.setCreatedBy(null);

        // Create the TechnicalDetails, which fails.
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);

        restTechnicalDetailsMockMvc
            .perform(
                post("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicalDetailsRepository.findAll().size();
        // set the field null
        technicalDetails.setCreatedDate(null);

        // Create the TechnicalDetails, which fails.
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);

        restTechnicalDetailsMockMvc
            .perform(
                post("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnicalDetails() throws Exception {
        // Initialize the database
        technicalDetailsRepository.saveAndFlush(technicalDetails);

        // Get all the technicalDetailsList
        restTechnicalDetailsMockMvc
            .perform(get("/api/technical-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technicalDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].technicalRequestId").value(hasItem(DEFAULT_TECHNICAL_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataLocationPath").value(hasItem(DEFAULT_DATA_LOCATION_PATH.toString())))
            .andExpect(jsonPath("$.[*].dataRefreshFrequency").value(hasItem(DEFAULT_DATA_REFRESH_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].targetIngestionStartDate").value(hasItem(DEFAULT_TARGET_INGESTION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].targetIngestionEndDate").value(hasItem(DEFAULT_TARGET_INGESTION_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].targetPath").value(hasItem(DEFAULT_TARGET_PATH.toString())))
            .andExpect(jsonPath("$.[*].datasetTypeIngestionRef").value(hasItem(DEFAULT_DATASET_TYPE_INGESTION_REF.toString())))
            .andExpect(jsonPath("$.[*].guestUsersEmail").value(hasItem(DEFAULT_GUEST_USERS_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].whitelistIpAddresses").value(hasItem(DEFAULT_WHITELIST_IP_ADDRESSES.toString())))
            .andExpect(jsonPath("$.[*].externalStagingContainerName").value(hasItem(DEFAULT_EXTERNAL_STAGING_CONTAINER_NAME.toString())))
            .andExpect(jsonPath("$.[*].externalDataSourceLocation").value(hasItem(DEFAULT_EXTERNAL_DATA_SOURCE_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].gskAccessSourceLocationRef").value(hasItem(DEFAULT_GSK_ACCESS_SOURCE_LOCATION_REF.toString())))
            .andExpect(jsonPath("$.[*].externalSourceSecretKeyName").value(hasItem(DEFAULT_EXTERNAL_SOURCE_SECRET_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].domainRequestId").value(hasItem(DEFAULT_DOMAIN_REQUEST_ID.toString())))
            .andExpect(
                jsonPath("$.[*].existingDataLocationIdentified").value(hasItem(DEFAULT_EXISTING_DATA_LOCATION_IDENTIFIED.toString()))
            )
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTechnicalDetails() throws Exception {
        // Initialize the database
        technicalDetailsRepository.saveAndFlush(technicalDetails);

        // Get the technicalDetails
        restTechnicalDetailsMockMvc
            .perform(get("/api/technical-details/{id}", technicalDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technicalDetails.getId().intValue()))
            .andExpect(jsonPath("$.technicalRequestId").value(DEFAULT_TECHNICAL_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.dataLocationPath").value(DEFAULT_DATA_LOCATION_PATH.toString()))
            .andExpect(jsonPath("$.dataRefreshFrequency").value(DEFAULT_DATA_REFRESH_FREQUENCY.toString()))
            .andExpect(jsonPath("$.targetIngestionStartDate").value(DEFAULT_TARGET_INGESTION_START_DATE.toString()))
            .andExpect(jsonPath("$.targetIngestionEndDate").value(DEFAULT_TARGET_INGESTION_END_DATE.toString()))
            .andExpect(jsonPath("$.targetPath").value(DEFAULT_TARGET_PATH.toString()))
            .andExpect(jsonPath("$.datasetTypeIngestionRef").value(DEFAULT_DATASET_TYPE_INGESTION_REF.toString()))
            .andExpect(jsonPath("$.guestUsersEmail").value(DEFAULT_GUEST_USERS_EMAIL.toString()))
            .andExpect(jsonPath("$.whitelistIpAddresses").value(DEFAULT_WHITELIST_IP_ADDRESSES.toString()))
            .andExpect(jsonPath("$.externalStagingContainerName").value(DEFAULT_EXTERNAL_STAGING_CONTAINER_NAME.toString()))
            .andExpect(jsonPath("$.externalDataSourceLocation").value(DEFAULT_EXTERNAL_DATA_SOURCE_LOCATION.toString()))
            .andExpect(jsonPath("$.gskAccessSourceLocationRef").value(DEFAULT_GSK_ACCESS_SOURCE_LOCATION_REF.toString()))
            .andExpect(jsonPath("$.externalSourceSecretKeyName").value(DEFAULT_EXTERNAL_SOURCE_SECRET_KEY_NAME.toString()))
            .andExpect(jsonPath("$.domainRequestId").value(DEFAULT_DOMAIN_REQUEST_ID.toString()))
            .andExpect(jsonPath("$.existingDataLocationIdentified").value(DEFAULT_EXISTING_DATA_LOCATION_IDENTIFIED.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnicalDetails() throws Exception {
        // Get the technicalDetails
        restTechnicalDetailsMockMvc.perform(get("/api/technical-details/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnicalDetails() throws Exception {
        // Initialize the database
        technicalDetailsRepository.saveAndFlush(technicalDetails);

        int databaseSizeBeforeUpdate = technicalDetailsRepository.findAll().size();

        // Update the technicalDetails
        TechnicalDetails updatedTechnicalDetails = technicalDetailsRepository.findById(technicalDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTechnicalDetails are not directly saved in db
        em.detach(updatedTechnicalDetails);
        updatedTechnicalDetails
            .technicalRequestId(UPDATED_TECHNICAL_REQUEST_ID)
            .dataLocationPath(UPDATED_DATA_LOCATION_PATH)
            .dataRefreshFrequency(UPDATED_DATA_REFRESH_FREQUENCY)
            .targetIngestionStartDate(UPDATED_TARGET_INGESTION_START_DATE)
            .targetIngestionEndDate(UPDATED_TARGET_INGESTION_END_DATE)
            .targetPath(UPDATED_TARGET_PATH)
            .datasetTypeIngestionRef(UPDATED_DATASET_TYPE_INGESTION_REF)
            .guestUsersEmail(UPDATED_GUEST_USERS_EMAIL)
            .whitelistIpAddresses(UPDATED_WHITELIST_IP_ADDRESSES)
            .externalStagingContainerName(UPDATED_EXTERNAL_STAGING_CONTAINER_NAME)
            .externalDataSourceLocation(UPDATED_EXTERNAL_DATA_SOURCE_LOCATION)
            .gskAccessSourceLocationRef(UPDATED_GSK_ACCESS_SOURCE_LOCATION_REF)
            .externalSourceSecretKeyName(UPDATED_EXTERNAL_SOURCE_SECRET_KEY_NAME)
            .domainRequestId(UPDATED_DOMAIN_REQUEST_ID)
            .existingDataLocationIdentified(UPDATED_EXISTING_DATA_LOCATION_IDENTIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(updatedTechnicalDetails);

        restTechnicalDetailsMockMvc
            .perform(
                put("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TechnicalDetails in the database
        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeUpdate);
        TechnicalDetails testTechnicalDetails = technicalDetailsList.get(technicalDetailsList.size() - 1);
        assertThat(testTechnicalDetails.getTechnicalRequestId()).isEqualTo(UPDATED_TECHNICAL_REQUEST_ID);
        assertThat(testTechnicalDetails.getDataLocationPath()).isEqualTo(UPDATED_DATA_LOCATION_PATH);
        assertThat(testTechnicalDetails.getDataRefreshFrequency()).isEqualTo(UPDATED_DATA_REFRESH_FREQUENCY);
        assertThat(testTechnicalDetails.getTargetIngestionStartDate()).isEqualTo(UPDATED_TARGET_INGESTION_START_DATE);
        assertThat(testTechnicalDetails.getTargetIngestionEndDate()).isEqualTo(UPDATED_TARGET_INGESTION_END_DATE);
        assertThat(testTechnicalDetails.getTargetPath()).isEqualTo(UPDATED_TARGET_PATH);
        assertThat(testTechnicalDetails.getDatasetTypeIngestionRef()).isEqualTo(UPDATED_DATASET_TYPE_INGESTION_REF);
        assertThat(testTechnicalDetails.getGuestUsersEmail()).isEqualTo(UPDATED_GUEST_USERS_EMAIL);
        assertThat(testTechnicalDetails.getWhitelistIpAddresses()).isEqualTo(UPDATED_WHITELIST_IP_ADDRESSES);
        assertThat(testTechnicalDetails.getExternalStagingContainerName()).isEqualTo(UPDATED_EXTERNAL_STAGING_CONTAINER_NAME);
        assertThat(testTechnicalDetails.getExternalDataSourceLocation()).isEqualTo(UPDATED_EXTERNAL_DATA_SOURCE_LOCATION);
        assertThat(testTechnicalDetails.getGskAccessSourceLocationRef()).isEqualTo(UPDATED_GSK_ACCESS_SOURCE_LOCATION_REF);
        assertThat(testTechnicalDetails.getExternalSourceSecretKeyName()).isEqualTo(UPDATED_EXTERNAL_SOURCE_SECRET_KEY_NAME);
        assertThat(testTechnicalDetails.getDomainRequestId()).isEqualTo(UPDATED_DOMAIN_REQUEST_ID);
        assertThat(testTechnicalDetails.getExistingDataLocationIdentified()).isEqualTo(UPDATED_EXISTING_DATA_LOCATION_IDENTIFIED);
        assertThat(testTechnicalDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTechnicalDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTechnicalDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTechnicalDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnicalDetails() throws Exception {
        int databaseSizeBeforeUpdate = technicalDetailsRepository.findAll().size();

        // Create the TechnicalDetails
        TechnicalDetailsDTO technicalDetailsDTO = technicalDetailsMapper.toDto(technicalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnicalDetailsMockMvc
            .perform(
                put("/api/technical-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(technicalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TechnicalDetails in the database
        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechnicalDetails() throws Exception {
        // Initialize the database
        technicalDetailsRepository.saveAndFlush(technicalDetails);

        int databaseSizeBeforeDelete = technicalDetailsRepository.findAll().size();

        // Delete the technicalDetails
        restTechnicalDetailsMockMvc
            .perform(delete("/api/technical-details/{id}", technicalDetails.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechnicalDetails> technicalDetailsList = technicalDetailsRepository.findAll();
        assertThat(technicalDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicalDetails.class);
        TechnicalDetails technicalDetails1 = new TechnicalDetails();
        technicalDetails1.setId(1L);
        TechnicalDetails technicalDetails2 = new TechnicalDetails();
        technicalDetails2.setId(technicalDetails1.getId());
        assertThat(technicalDetails1).isEqualTo(technicalDetails2);
        technicalDetails2.setId(2L);
        assertThat(technicalDetails1).isNotEqualTo(technicalDetails2);
        technicalDetails1.setId(null);
        assertThat(technicalDetails1).isNotEqualTo(technicalDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnicalDetailsDTO.class);
        TechnicalDetailsDTO technicalDetailsDTO1 = new TechnicalDetailsDTO();
        technicalDetailsDTO1.setId(1L);
        TechnicalDetailsDTO technicalDetailsDTO2 = new TechnicalDetailsDTO();
        assertThat(technicalDetailsDTO1).isNotEqualTo(technicalDetailsDTO2);
        technicalDetailsDTO2.setId(technicalDetailsDTO1.getId());
        assertThat(technicalDetailsDTO1).isEqualTo(technicalDetailsDTO2);
        technicalDetailsDTO2.setId(2L);
        assertThat(technicalDetailsDTO1).isNotEqualTo(technicalDetailsDTO2);
        technicalDetailsDTO1.setId(null);
        assertThat(technicalDetailsDTO1).isNotEqualTo(technicalDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(technicalDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(technicalDetailsMapper.fromId(null)).isNull();
    }
}
