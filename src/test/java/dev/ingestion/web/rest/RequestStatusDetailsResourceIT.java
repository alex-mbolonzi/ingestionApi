package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.domain.RequestStatusDetails;
import dev.ingestion.domain.Status;
import dev.ingestion.repository.RequestStatusDetailsRepository;
import dev.ingestion.service.RequestStatusDetailsService;
import dev.ingestion.service.dto.RequestStatusDetailsDTO;
import dev.ingestion.service.mapper.RequestStatusDetailsMapper;
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
 * Integration tests for the {@link RequestStatusDetailsResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class RequestStatusDetailsResourceIT {

    private static final Long DEFAULT_REQUEST_STATUS_ID = 1L;
    private static final Long UPDATED_REQUEST_STATUS_ID = 2L;
    private static final Long SMALLER_REQUEST_STATUS_ID = 1L - 1L;

    private static final String DEFAULT_DECISION_BY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DECISION_BY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DECISION_BY_MUDID = "AAAAAAAAAA";
    private static final String UPDATED_DECISION_BY_MUDID = "BBBBBBBBBB";

    private static final String DEFAULT_DECISION_BY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_DECISION_BY_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DECISION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DECISION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_DECISION_DATE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_DECISION_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_DECISION_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

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
    private RequestStatusDetailsRepository requestStatusDetailsRepository;

    @Autowired
    private RequestStatusDetailsMapper requestStatusDetailsMapper;

    @Autowired
    private RequestStatusDetailsService requestStatusDetailsService;

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

    private MockMvc restRequestStatusDetailsMockMvc;

    private RequestStatusDetails requestStatusDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestStatusDetailsResource requestStatusDetailsResource = new RequestStatusDetailsResource(requestStatusDetailsService);
        this.restRequestStatusDetailsMockMvc = MockMvcBuilders.standaloneSetup(requestStatusDetailsResource)
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
    public static RequestStatusDetails createEntity(EntityManager em) {
        RequestStatusDetails requestStatusDetails = new RequestStatusDetails()
            .requestStatusId(DEFAULT_REQUEST_STATUS_ID)
            .decisionByName(DEFAULT_DECISION_BY_NAME)
            .decisionByMudid(DEFAULT_DECISION_BY_MUDID)
            .decisionByEmail(DEFAULT_DECISION_BY_EMAIL)
            .decisionDate(DEFAULT_DECISION_DATE)
            .decisionComments(DEFAULT_DECISION_COMMENTS)
            .rejectionReason(DEFAULT_REJECTION_REASON)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
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
        requestStatusDetails.setIngestionRequestId(ingestionRequestDetails);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        requestStatusDetails.setStatusId(status);
        return requestStatusDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestStatusDetails createUpdatedEntity(EntityManager em) {
        RequestStatusDetails requestStatusDetails = new RequestStatusDetails()
            .requestStatusId(UPDATED_REQUEST_STATUS_ID)
            .decisionByName(UPDATED_DECISION_BY_NAME)
            .decisionByMudid(UPDATED_DECISION_BY_MUDID)
            .decisionByEmail(UPDATED_DECISION_BY_EMAIL)
            .decisionDate(UPDATED_DECISION_DATE)
            .decisionComments(UPDATED_DECISION_COMMENTS)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .activeFlag(UPDATED_ACTIVE_FLAG)
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
        requestStatusDetails.setIngestionRequestId(ingestionRequestDetails);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createUpdatedEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        requestStatusDetails.setStatusId(status);
        return requestStatusDetails;
    }

    @BeforeEach
    public void initTest() {
        requestStatusDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestStatusDetails() throws Exception {
        int databaseSizeBeforeCreate = requestStatusDetailsRepository.findAll().size();

        // Create the RequestStatusDetails
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);
        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestStatusDetails in the database
        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RequestStatusDetails testRequestStatusDetails = requestStatusDetailsList.get(requestStatusDetailsList.size() - 1);
        assertThat(testRequestStatusDetails.getRequestStatusId()).isEqualTo(DEFAULT_REQUEST_STATUS_ID);
        assertThat(testRequestStatusDetails.getDecisionByName()).isEqualTo(DEFAULT_DECISION_BY_NAME);
        assertThat(testRequestStatusDetails.getDecisionByMudid()).isEqualTo(DEFAULT_DECISION_BY_MUDID);
        assertThat(testRequestStatusDetails.getDecisionByEmail()).isEqualTo(DEFAULT_DECISION_BY_EMAIL);
        assertThat(testRequestStatusDetails.getDecisionDate()).isEqualTo(DEFAULT_DECISION_DATE);
        assertThat(testRequestStatusDetails.getDecisionComments()).isEqualTo(DEFAULT_DECISION_COMMENTS);
        assertThat(testRequestStatusDetails.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
        assertThat(testRequestStatusDetails.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testRequestStatusDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRequestStatusDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRequestStatusDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRequestStatusDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createRequestStatusDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestStatusDetailsRepository.findAll().size();

        // Create the RequestStatusDetails with an existing ID
        requestStatusDetails.setId(1L);
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatusDetails in the database
        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setRequestStatusId(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDecisionByNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setDecisionByName(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDecisionByMudidIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setDecisionByMudid(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDecisionByEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setDecisionByEmail(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDecisionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setDecisionDate(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setActiveFlag(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setCreatedBy(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusDetailsRepository.findAll().size();
        // set the field null
        requestStatusDetails.setCreatedDate(null);

        // Create the RequestStatusDetails, which fails.
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                post("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequestStatusDetails() throws Exception {
        // Initialize the database
        requestStatusDetailsRepository.saveAndFlush(requestStatusDetails);

        // Get all the requestStatusDetailsList
        restRequestStatusDetailsMockMvc
            .perform(get("/api/request-status-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestStatusDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestStatusId").value(hasItem(DEFAULT_REQUEST_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].decisionByName").value(hasItem(DEFAULT_DECISION_BY_NAME.toString())))
            .andExpect(jsonPath("$.[*].decisionByMudid").value(hasItem(DEFAULT_DECISION_BY_MUDID.toString())))
            .andExpect(jsonPath("$.[*].decisionByEmail").value(hasItem(DEFAULT_DECISION_BY_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].decisionDate").value(hasItem(DEFAULT_DECISION_DATE.toString())))
            .andExpect(jsonPath("$.[*].decisionComments").value(hasItem(DEFAULT_DECISION_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON.toString())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRequestStatusDetails() throws Exception {
        // Initialize the database
        requestStatusDetailsRepository.saveAndFlush(requestStatusDetails);

        // Get the requestStatusDetails
        restRequestStatusDetailsMockMvc
            .perform(get("/api/request-status-details/{id}", requestStatusDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestStatusDetails.getId().intValue()))
            .andExpect(jsonPath("$.requestStatusId").value(DEFAULT_REQUEST_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.decisionByName").value(DEFAULT_DECISION_BY_NAME.toString()))
            .andExpect(jsonPath("$.decisionByMudid").value(DEFAULT_DECISION_BY_MUDID.toString()))
            .andExpect(jsonPath("$.decisionByEmail").value(DEFAULT_DECISION_BY_EMAIL.toString()))
            .andExpect(jsonPath("$.decisionDate").value(DEFAULT_DECISION_DATE.toString()))
            .andExpect(jsonPath("$.decisionComments").value(DEFAULT_DECISION_COMMENTS.toString()))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON.toString()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestStatusDetails() throws Exception {
        // Get the requestStatusDetails
        restRequestStatusDetailsMockMvc.perform(get("/api/request-status-details/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestStatusDetails() throws Exception {
        // Initialize the database
        requestStatusDetailsRepository.saveAndFlush(requestStatusDetails);

        int databaseSizeBeforeUpdate = requestStatusDetailsRepository.findAll().size();

        // Update the requestStatusDetails
        RequestStatusDetails updatedRequestStatusDetails = requestStatusDetailsRepository.findById(requestStatusDetails.getId()).get();
        // Disconnect from session so that the updates on updatedRequestStatusDetails are not directly saved in db
        em.detach(updatedRequestStatusDetails);
        updatedRequestStatusDetails
            .requestStatusId(UPDATED_REQUEST_STATUS_ID)
            .decisionByName(UPDATED_DECISION_BY_NAME)
            .decisionByMudid(UPDATED_DECISION_BY_MUDID)
            .decisionByEmail(UPDATED_DECISION_BY_EMAIL)
            .decisionDate(UPDATED_DECISION_DATE)
            .decisionComments(UPDATED_DECISION_COMMENTS)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(updatedRequestStatusDetails);

        restRequestStatusDetailsMockMvc
            .perform(
                put("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestStatusDetails in the database
        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeUpdate);
        RequestStatusDetails testRequestStatusDetails = requestStatusDetailsList.get(requestStatusDetailsList.size() - 1);
        assertThat(testRequestStatusDetails.getRequestStatusId()).isEqualTo(UPDATED_REQUEST_STATUS_ID);
        assertThat(testRequestStatusDetails.getDecisionByName()).isEqualTo(UPDATED_DECISION_BY_NAME);
        assertThat(testRequestStatusDetails.getDecisionByMudid()).isEqualTo(UPDATED_DECISION_BY_MUDID);
        assertThat(testRequestStatusDetails.getDecisionByEmail()).isEqualTo(UPDATED_DECISION_BY_EMAIL);
        assertThat(testRequestStatusDetails.getDecisionDate()).isEqualTo(UPDATED_DECISION_DATE);
        assertThat(testRequestStatusDetails.getDecisionComments()).isEqualTo(UPDATED_DECISION_COMMENTS);
        assertThat(testRequestStatusDetails.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
        assertThat(testRequestStatusDetails.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testRequestStatusDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequestStatusDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRequestStatusDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRequestStatusDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestStatusDetails() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusDetailsRepository.findAll().size();

        // Create the RequestStatusDetails
        RequestStatusDetailsDTO requestStatusDetailsDTO = requestStatusDetailsMapper.toDto(requestStatusDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStatusDetailsMockMvc
            .perform(
                put("/api/request-status-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatusDetails in the database
        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequestStatusDetails() throws Exception {
        // Initialize the database
        requestStatusDetailsRepository.saveAndFlush(requestStatusDetails);

        int databaseSizeBeforeDelete = requestStatusDetailsRepository.findAll().size();

        // Delete the requestStatusDetails
        restRequestStatusDetailsMockMvc
            .perform(delete("/api/request-status-details/{id}", requestStatusDetails.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestStatusDetails> requestStatusDetailsList = requestStatusDetailsRepository.findAll();
        assertThat(requestStatusDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatusDetails.class);
        RequestStatusDetails requestStatusDetails1 = new RequestStatusDetails();
        requestStatusDetails1.setId(1L);
        RequestStatusDetails requestStatusDetails2 = new RequestStatusDetails();
        requestStatusDetails2.setId(requestStatusDetails1.getId());
        assertThat(requestStatusDetails1).isEqualTo(requestStatusDetails2);
        requestStatusDetails2.setId(2L);
        assertThat(requestStatusDetails1).isNotEqualTo(requestStatusDetails2);
        requestStatusDetails1.setId(null);
        assertThat(requestStatusDetails1).isNotEqualTo(requestStatusDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatusDetailsDTO.class);
        RequestStatusDetailsDTO requestStatusDetailsDTO1 = new RequestStatusDetailsDTO();
        requestStatusDetailsDTO1.setId(1L);
        RequestStatusDetailsDTO requestStatusDetailsDTO2 = new RequestStatusDetailsDTO();
        assertThat(requestStatusDetailsDTO1).isNotEqualTo(requestStatusDetailsDTO2);
        requestStatusDetailsDTO2.setId(requestStatusDetailsDTO1.getId());
        assertThat(requestStatusDetailsDTO1).isEqualTo(requestStatusDetailsDTO2);
        requestStatusDetailsDTO2.setId(2L);
        assertThat(requestStatusDetailsDTO1).isNotEqualTo(requestStatusDetailsDTO2);
        requestStatusDetailsDTO1.setId(null);
        assertThat(requestStatusDetailsDTO1).isNotEqualTo(requestStatusDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requestStatusDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requestStatusDetailsMapper.fromId(null)).isNull();
    }
}
