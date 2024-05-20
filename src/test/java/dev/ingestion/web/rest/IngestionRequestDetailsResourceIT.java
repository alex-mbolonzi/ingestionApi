package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.repository.IngestionRequestDetailsRepository;
import dev.ingestion.service.IngestionRequestDetailsService;
import dev.ingestion.service.dto.IngestionRequestDetailsDTO;
import dev.ingestion.service.mapper.IngestionRequestDetailsMapper;
import dev.ingestion.web.rest.errors.ExceptionTranslator;
import java.time.Instant;
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
 * Integration tests for the {@link IngestionRequestDetailsResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class IngestionRequestDetailsResourceIT {

    private static final Long DEFAULT_INGESTION_REQUEST_ID = 1L;
    private static final Long UPDATED_INGESTION_REQUEST_ID = 2L;
    private static final Long SMALLER_INGESTION_REQUEST_ID = 1L - 1L;

    private static final String DEFAULT_REQUESTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTER_MUDID = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTER_MUDID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_RATIONALE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_RATIONALE_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTED_BY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTED_BY_MUDID = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY_MUDID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTED_BY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY_EMAIL = "BBBBBBBBBB";

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

    private static final String DEFAULT_MODIFIED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_REASON = "BBBBBBBBBB";

    @Autowired
    private IngestionRequestDetailsRepository ingestionRequestDetailsRepository;

    @Autowired
    private IngestionRequestDetailsMapper ingestionRequestDetailsMapper;

    @Autowired
    private IngestionRequestDetailsService ingestionRequestDetailsService;

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

    private MockMvc restIngestionRequestDetailsMockMvc;

    private IngestionRequestDetails ingestionRequestDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngestionRequestDetailsResource ingestionRequestDetailsResource = new IngestionRequestDetailsResource(
            ingestionRequestDetailsService
        );
        this.restIngestionRequestDetailsMockMvc = MockMvcBuilders.standaloneSetup(ingestionRequestDetailsResource)
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
    public static IngestionRequestDetails createEntity(EntityManager em) {
        IngestionRequestDetails ingestionRequestDetails = new IngestionRequestDetails()
            .ingestionRequestId(DEFAULT_INGESTION_REQUEST_ID)
            .requesterName(DEFAULT_REQUESTER_NAME)
            .requesterMudid(DEFAULT_REQUESTER_MUDID)
            .requesterEmail(DEFAULT_REQUESTER_EMAIL)
            .requestRationaleReason(DEFAULT_REQUEST_RATIONALE_REASON)
            .requestedByName(DEFAULT_REQUESTED_BY_NAME)
            .requestedByMudid(DEFAULT_REQUESTED_BY_MUDID)
            .requestedByEmail(DEFAULT_REQUESTED_BY_EMAIL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .modifiedReason(DEFAULT_MODIFIED_REASON);
        return ingestionRequestDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngestionRequestDetails createUpdatedEntity(EntityManager em) {
        IngestionRequestDetails ingestionRequestDetails = new IngestionRequestDetails()
            .ingestionRequestId(UPDATED_INGESTION_REQUEST_ID)
            .requesterName(UPDATED_REQUESTER_NAME)
            .requesterMudid(UPDATED_REQUESTER_MUDID)
            .requesterEmail(UPDATED_REQUESTER_EMAIL)
            .requestRationaleReason(UPDATED_REQUEST_RATIONALE_REASON)
            .requestedByName(UPDATED_REQUESTED_BY_NAME)
            .requestedByMudid(UPDATED_REQUESTED_BY_MUDID)
            .requestedByEmail(UPDATED_REQUESTED_BY_EMAIL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedReason(UPDATED_MODIFIED_REASON);
        return ingestionRequestDetails;
    }

    @BeforeEach
    public void initTest() {
        ingestionRequestDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngestionRequestDetails() throws Exception {
        int databaseSizeBeforeCreate = ingestionRequestDetailsRepository.findAll().size();

        // Create the IngestionRequestDetails
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);
        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IngestionRequestDetails in the database
        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        IngestionRequestDetails testIngestionRequestDetails = ingestionRequestDetailsList.get(ingestionRequestDetailsList.size() - 1);
        assertThat(testIngestionRequestDetails.getIngestionRequestId()).isEqualTo(DEFAULT_INGESTION_REQUEST_ID);
        assertThat(testIngestionRequestDetails.getRequesterName()).isEqualTo(DEFAULT_REQUESTER_NAME);
        assertThat(testIngestionRequestDetails.getRequesterMudid()).isEqualTo(DEFAULT_REQUESTER_MUDID);
        assertThat(testIngestionRequestDetails.getRequesterEmail()).isEqualTo(DEFAULT_REQUESTER_EMAIL);
        assertThat(testIngestionRequestDetails.getRequestRationaleReason()).isEqualTo(DEFAULT_REQUEST_RATIONALE_REASON);
        assertThat(testIngestionRequestDetails.getRequestedByName()).isEqualTo(DEFAULT_REQUESTED_BY_NAME);
        assertThat(testIngestionRequestDetails.getRequestedByMudid()).isEqualTo(DEFAULT_REQUESTED_BY_MUDID);
        assertThat(testIngestionRequestDetails.getRequestedByEmail()).isEqualTo(DEFAULT_REQUESTED_BY_EMAIL);
        assertThat(testIngestionRequestDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIngestionRequestDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIngestionRequestDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testIngestionRequestDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testIngestionRequestDetails.getModifiedReason()).isEqualTo(DEFAULT_MODIFIED_REASON);
    }

    @Test
    @Transactional
    public void createIngestionRequestDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingestionRequestDetailsRepository.findAll().size();

        // Create the IngestionRequestDetails with an existing ID
        ingestionRequestDetails.setId(1L);
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngestionRequestDetails in the database
        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIngestionRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setIngestionRequestId(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequesterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequesterName(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequesterMudidIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequesterMudid(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequesterEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequesterEmail(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestedByNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequestedByName(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestedByMudidIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequestedByMudid(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestedByEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setRequestedByEmail(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setCreatedBy(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingestionRequestDetailsRepository.findAll().size();
        // set the field null
        ingestionRequestDetails.setCreatedDate(null);

        // Create the IngestionRequestDetails, which fails.
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                post("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngestionRequestDetails() throws Exception {
        // Initialize the database
        ingestionRequestDetailsRepository.saveAndFlush(ingestionRequestDetails);

        // Get all the ingestionRequestDetailsList
        restIngestionRequestDetailsMockMvc
            .perform(get("/api/ingestion-request-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingestionRequestDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].ingestionRequestId").value(hasItem(DEFAULT_INGESTION_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].requesterName").value(hasItem(DEFAULT_REQUESTER_NAME.toString())))
            .andExpect(jsonPath("$.[*].requesterMudid").value(hasItem(DEFAULT_REQUESTER_MUDID.toString())))
            .andExpect(jsonPath("$.[*].requesterEmail").value(hasItem(DEFAULT_REQUESTER_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].requestRationaleReason").value(hasItem(DEFAULT_REQUEST_RATIONALE_REASON.toString())))
            .andExpect(jsonPath("$.[*].requestedByName").value(hasItem(DEFAULT_REQUESTED_BY_NAME.toString())))
            .andExpect(jsonPath("$.[*].requestedByMudid").value(hasItem(DEFAULT_REQUESTED_BY_MUDID.toString())))
            .andExpect(jsonPath("$.[*].requestedByEmail").value(hasItem(DEFAULT_REQUESTED_BY_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedReason").value(hasItem(DEFAULT_MODIFIED_REASON.toString())));
    }

    @Test
    @Transactional
    public void getIngestionRequestDetails() throws Exception {
        // Initialize the database
        ingestionRequestDetailsRepository.saveAndFlush(ingestionRequestDetails);

        // Get the ingestionRequestDetails
        restIngestionRequestDetailsMockMvc
            .perform(get("/api/ingestion-request-details/{id}", ingestionRequestDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingestionRequestDetails.getId().intValue()))
            .andExpect(jsonPath("$.ingestionRequestId").value(DEFAULT_INGESTION_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.requesterName").value(DEFAULT_REQUESTER_NAME.toString()))
            .andExpect(jsonPath("$.requesterMudid").value(DEFAULT_REQUESTER_MUDID.toString()))
            .andExpect(jsonPath("$.requesterEmail").value(DEFAULT_REQUESTER_EMAIL.toString()))
            .andExpect(jsonPath("$.requestRationaleReason").value(DEFAULT_REQUEST_RATIONALE_REASON.toString()))
            .andExpect(jsonPath("$.requestedByName").value(DEFAULT_REQUESTED_BY_NAME.toString()))
            .andExpect(jsonPath("$.requestedByMudid").value(DEFAULT_REQUESTED_BY_MUDID.toString()))
            .andExpect(jsonPath("$.requestedByEmail").value(DEFAULT_REQUESTED_BY_EMAIL.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedReason").value(DEFAULT_MODIFIED_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIngestionRequestDetails() throws Exception {
        // Get the ingestionRequestDetails
        restIngestionRequestDetailsMockMvc
            .perform(get("/api/ingestion-request-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngestionRequestDetails() throws Exception {
        // Initialize the database
        ingestionRequestDetailsRepository.saveAndFlush(ingestionRequestDetails);

        int databaseSizeBeforeUpdate = ingestionRequestDetailsRepository.findAll().size();

        // Update the ingestionRequestDetails
        IngestionRequestDetails updatedIngestionRequestDetails = ingestionRequestDetailsRepository
            .findById(ingestionRequestDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedIngestionRequestDetails are not directly saved in db
        em.detach(updatedIngestionRequestDetails);
        updatedIngestionRequestDetails
            .ingestionRequestId(UPDATED_INGESTION_REQUEST_ID)
            .requesterName(UPDATED_REQUESTER_NAME)
            .requesterMudid(UPDATED_REQUESTER_MUDID)
            .requesterEmail(UPDATED_REQUESTER_EMAIL)
            .requestRationaleReason(UPDATED_REQUEST_RATIONALE_REASON)
            .requestedByName(UPDATED_REQUESTED_BY_NAME)
            .requestedByMudid(UPDATED_REQUESTED_BY_MUDID)
            .requestedByEmail(UPDATED_REQUESTED_BY_EMAIL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .modifiedReason(UPDATED_MODIFIED_REASON);
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(updatedIngestionRequestDetails);

        restIngestionRequestDetailsMockMvc
            .perform(
                put("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the IngestionRequestDetails in the database
        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeUpdate);
        IngestionRequestDetails testIngestionRequestDetails = ingestionRequestDetailsList.get(ingestionRequestDetailsList.size() - 1);
        assertThat(testIngestionRequestDetails.getIngestionRequestId()).isEqualTo(UPDATED_INGESTION_REQUEST_ID);
        assertThat(testIngestionRequestDetails.getRequesterName()).isEqualTo(UPDATED_REQUESTER_NAME);
        assertThat(testIngestionRequestDetails.getRequesterMudid()).isEqualTo(UPDATED_REQUESTER_MUDID);
        assertThat(testIngestionRequestDetails.getRequesterEmail()).isEqualTo(UPDATED_REQUESTER_EMAIL);
        assertThat(testIngestionRequestDetails.getRequestRationaleReason()).isEqualTo(UPDATED_REQUEST_RATIONALE_REASON);
        assertThat(testIngestionRequestDetails.getRequestedByName()).isEqualTo(UPDATED_REQUESTED_BY_NAME);
        assertThat(testIngestionRequestDetails.getRequestedByMudid()).isEqualTo(UPDATED_REQUESTED_BY_MUDID);
        assertThat(testIngestionRequestDetails.getRequestedByEmail()).isEqualTo(UPDATED_REQUESTED_BY_EMAIL);
        assertThat(testIngestionRequestDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIngestionRequestDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIngestionRequestDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testIngestionRequestDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testIngestionRequestDetails.getModifiedReason()).isEqualTo(UPDATED_MODIFIED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingIngestionRequestDetails() throws Exception {
        int databaseSizeBeforeUpdate = ingestionRequestDetailsRepository.findAll().size();

        // Create the IngestionRequestDetails
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngestionRequestDetailsMockMvc
            .perform(
                put("/api/ingestion-request-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(ingestionRequestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngestionRequestDetails in the database
        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngestionRequestDetails() throws Exception {
        // Initialize the database
        ingestionRequestDetailsRepository.saveAndFlush(ingestionRequestDetails);

        int databaseSizeBeforeDelete = ingestionRequestDetailsRepository.findAll().size();

        // Delete the ingestionRequestDetails
        restIngestionRequestDetailsMockMvc
            .perform(delete("/api/ingestion-request-details/{id}", ingestionRequestDetails.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngestionRequestDetails> ingestionRequestDetailsList = ingestionRequestDetailsRepository.findAll();
        assertThat(ingestionRequestDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngestionRequestDetails.class);
        IngestionRequestDetails ingestionRequestDetails1 = new IngestionRequestDetails();
        ingestionRequestDetails1.setId(1L);
        IngestionRequestDetails ingestionRequestDetails2 = new IngestionRequestDetails();
        ingestionRequestDetails2.setId(ingestionRequestDetails1.getId());
        assertThat(ingestionRequestDetails1).isEqualTo(ingestionRequestDetails2);
        ingestionRequestDetails2.setId(2L);
        assertThat(ingestionRequestDetails1).isNotEqualTo(ingestionRequestDetails2);
        ingestionRequestDetails1.setId(null);
        assertThat(ingestionRequestDetails1).isNotEqualTo(ingestionRequestDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngestionRequestDetailsDTO.class);
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO1 = new IngestionRequestDetailsDTO();
        ingestionRequestDetailsDTO1.setId(1L);
        IngestionRequestDetailsDTO ingestionRequestDetailsDTO2 = new IngestionRequestDetailsDTO();
        assertThat(ingestionRequestDetailsDTO1).isNotEqualTo(ingestionRequestDetailsDTO2);
        ingestionRequestDetailsDTO2.setId(ingestionRequestDetailsDTO1.getId());
        assertThat(ingestionRequestDetailsDTO1).isEqualTo(ingestionRequestDetailsDTO2);
        ingestionRequestDetailsDTO2.setId(2L);
        assertThat(ingestionRequestDetailsDTO1).isNotEqualTo(ingestionRequestDetailsDTO2);
        ingestionRequestDetailsDTO1.setId(null);
        assertThat(ingestionRequestDetailsDTO1).isNotEqualTo(ingestionRequestDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingestionRequestDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingestionRequestDetailsMapper.fromId(null)).isNull();
    }
}
