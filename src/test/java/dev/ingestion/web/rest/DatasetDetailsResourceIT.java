package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.repository.DatasetDetailsRepository;
import dev.ingestion.service.DatasetDetailsService;
import dev.ingestion.service.dto.DatasetDetailsDTO;
import dev.ingestion.service.mapper.DatasetDetailsMapper;
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
 * Integration tests for the {@link DatasetDetailsResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetDetailsResourceIT {

    private static final Long DEFAULT_DATASET_ID = 1L;
    private static final Long UPDATED_DATASET_ID = 2L;
    private static final Long SMALLER_DATASET_ID = 1L - 1L;

    private static final String DEFAULT_DATASET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATASET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATASET_ORIGIN_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DATASET_ORIGIN_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_DATA_LOCATION_REF = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_DATA_LOCATION_REF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_METEOR_SPACE_DOMINO_USAGE_FLAG = false;
    private static final Boolean UPDATED_METEOR_SPACE_DOMINO_USAGE_FLAG = true;

    private static final Boolean DEFAULT_IHD_FLAG = false;
    private static final Boolean UPDATED_IHD_FLAG = true;

    private static final String DEFAULT_DATASET_REQUIRED_FOR_REF = "AAAAAAAAAA";
    private static final String UPDATED_DATASET_REQUIRED_FOR_REF = "BBBBBBBBBB";

    private static final String DEFAULT_ESTIMATED_DATA_VOLUME_REF = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATED_DATA_VOLUME_REF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANALYSIS_INIT_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANALYSIS_INIT_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ANALYSIS_INIT_DT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ANALYSIS_END_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANALYSIS_END_DT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ANALYSIS_END_DT = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_DTA_CONTRACT_COMPLETE_FLAG = false;
    private static final Boolean UPDATED_DTA_CONTRACT_COMPLETE_FLAG = true;

    private static final LocalDate DEFAULT_DTA_EXPECTED_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DTA_EXPECTED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DTA_EXPECTED_COMPLETION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DATASET_TYPE_REF = "AAAAAAAAAA";
    private static final String UPDATED_DATASET_TYPE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION_CLASSIFICATION_TYPE_REF = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION_CLASSIFICATION_TYPE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_PII_TYPE_REF = "AAAAAAAAAA";
    private static final String UPDATED_PII_TYPE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_RETENTION_RULES = "AAAAAAAAAA";
    private static final String UPDATED_RETENTION_RULES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RETENTION_RULES_CONTRACT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETENTION_RULES_CONTRACT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RETENTION_RULES_CONTRACT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONTRACT_PARTNER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_PARTNER = "BBBBBBBBBB";

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
    private DatasetDetailsRepository datasetDetailsRepository;

    @Autowired
    private DatasetDetailsMapper datasetDetailsMapper;

    @Autowired
    private DatasetDetailsService datasetDetailsService;

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

    private MockMvc restDatasetDetailsMockMvc;

    private DatasetDetails datasetDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetDetailsResource datasetDetailsResource = new DatasetDetailsResource(datasetDetailsService);
        this.restDatasetDetailsMockMvc = MockMvcBuilders.standaloneSetup(datasetDetailsResource)
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
    public static DatasetDetails createEntity(EntityManager em) {
        DatasetDetails datasetDetails = new DatasetDetails()
            .datasetId(DEFAULT_DATASET_ID)
            .datasetName(DEFAULT_DATASET_NAME)
            .datasetOriginSource(DEFAULT_DATASET_ORIGIN_SOURCE)
            .currentDataLocationRef(DEFAULT_CURRENT_DATA_LOCATION_REF)
            .meteorSpaceDominoUsageFlag(DEFAULT_METEOR_SPACE_DOMINO_USAGE_FLAG)
            .ihdFlag(DEFAULT_IHD_FLAG)
            .datasetRequiredForRef(DEFAULT_DATASET_REQUIRED_FOR_REF)
            .estimatedDataVolumeRef(DEFAULT_ESTIMATED_DATA_VOLUME_REF)
            .analysisInitDt(DEFAULT_ANALYSIS_INIT_DT)
            .analysisEndDt(DEFAULT_ANALYSIS_END_DT)
            .dtaContractCompleteFlag(DEFAULT_DTA_CONTRACT_COMPLETE_FLAG)
            .dtaExpectedCompletionDate(DEFAULT_DTA_EXPECTED_COMPLETION_DATE)
            .datasetTypeRef(DEFAULT_DATASET_TYPE_REF)
            .informationClassificationTypeRef(DEFAULT_INFORMATION_CLASSIFICATION_TYPE_REF)
            .piiTypeRef(DEFAULT_PII_TYPE_REF)
            .retentionRules(DEFAULT_RETENTION_RULES)
            .retentionRulesContractDate(DEFAULT_RETENTION_RULES_CONTRACT_DATE)
            .contractPartner(DEFAULT_CONTRACT_PARTNER)
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
        datasetDetails.setIngestionRequestId(ingestionRequestDetails);
        return datasetDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetDetails createUpdatedEntity(EntityManager em) {
        DatasetDetails datasetDetails = new DatasetDetails()
            .datasetId(UPDATED_DATASET_ID)
            .datasetName(UPDATED_DATASET_NAME)
            .datasetOriginSource(UPDATED_DATASET_ORIGIN_SOURCE)
            .currentDataLocationRef(UPDATED_CURRENT_DATA_LOCATION_REF)
            .meteorSpaceDominoUsageFlag(UPDATED_METEOR_SPACE_DOMINO_USAGE_FLAG)
            .ihdFlag(UPDATED_IHD_FLAG)
            .datasetRequiredForRef(UPDATED_DATASET_REQUIRED_FOR_REF)
            .estimatedDataVolumeRef(UPDATED_ESTIMATED_DATA_VOLUME_REF)
            .analysisInitDt(UPDATED_ANALYSIS_INIT_DT)
            .analysisEndDt(UPDATED_ANALYSIS_END_DT)
            .dtaContractCompleteFlag(UPDATED_DTA_CONTRACT_COMPLETE_FLAG)
            .dtaExpectedCompletionDate(UPDATED_DTA_EXPECTED_COMPLETION_DATE)
            .datasetTypeRef(UPDATED_DATASET_TYPE_REF)
            .informationClassificationTypeRef(UPDATED_INFORMATION_CLASSIFICATION_TYPE_REF)
            .piiTypeRef(UPDATED_PII_TYPE_REF)
            .retentionRules(UPDATED_RETENTION_RULES)
            .retentionRulesContractDate(UPDATED_RETENTION_RULES_CONTRACT_DATE)
            .contractPartner(UPDATED_CONTRACT_PARTNER)
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
        datasetDetails.setIngestionRequestId(ingestionRequestDetails);
        return datasetDetails;
    }

    @BeforeEach
    public void initTest() {
        datasetDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetDetails() throws Exception {
        int databaseSizeBeforeCreate = datasetDetailsRepository.findAll().size();

        // Create the DatasetDetails
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);
        restDatasetDetailsMockMvc
            .perform(
                post("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetDetails in the database
        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetDetails testDatasetDetails = datasetDetailsList.get(datasetDetailsList.size() - 1);
        assertThat(testDatasetDetails.getDatasetId()).isEqualTo(DEFAULT_DATASET_ID);
        assertThat(testDatasetDetails.getDatasetName()).isEqualTo(DEFAULT_DATASET_NAME);
        assertThat(testDatasetDetails.getDatasetOriginSource()).isEqualTo(DEFAULT_DATASET_ORIGIN_SOURCE);
        assertThat(testDatasetDetails.getCurrentDataLocationRef()).isEqualTo(DEFAULT_CURRENT_DATA_LOCATION_REF);
        assertThat(testDatasetDetails.isMeteorSpaceDominoUsageFlag()).isEqualTo(DEFAULT_METEOR_SPACE_DOMINO_USAGE_FLAG);
        assertThat(testDatasetDetails.isIhdFlag()).isEqualTo(DEFAULT_IHD_FLAG);
        assertThat(testDatasetDetails.getDatasetRequiredForRef()).isEqualTo(DEFAULT_DATASET_REQUIRED_FOR_REF);
        assertThat(testDatasetDetails.getEstimatedDataVolumeRef()).isEqualTo(DEFAULT_ESTIMATED_DATA_VOLUME_REF);
        assertThat(testDatasetDetails.getAnalysisInitDt()).isEqualTo(DEFAULT_ANALYSIS_INIT_DT);
        assertThat(testDatasetDetails.getAnalysisEndDt()).isEqualTo(DEFAULT_ANALYSIS_END_DT);
        assertThat(testDatasetDetails.isDtaContractCompleteFlag()).isEqualTo(DEFAULT_DTA_CONTRACT_COMPLETE_FLAG);
        assertThat(testDatasetDetails.getDtaExpectedCompletionDate()).isEqualTo(DEFAULT_DTA_EXPECTED_COMPLETION_DATE);
        assertThat(testDatasetDetails.getDatasetTypeRef()).isEqualTo(DEFAULT_DATASET_TYPE_REF);
        assertThat(testDatasetDetails.getInformationClassificationTypeRef()).isEqualTo(DEFAULT_INFORMATION_CLASSIFICATION_TYPE_REF);
        assertThat(testDatasetDetails.getPiiTypeRef()).isEqualTo(DEFAULT_PII_TYPE_REF);
        assertThat(testDatasetDetails.getRetentionRules()).isEqualTo(DEFAULT_RETENTION_RULES);
        assertThat(testDatasetDetails.getRetentionRulesContractDate()).isEqualTo(DEFAULT_RETENTION_RULES_CONTRACT_DATE);
        assertThat(testDatasetDetails.getContractPartner()).isEqualTo(DEFAULT_CONTRACT_PARTNER);
        assertThat(testDatasetDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDatasetDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDatasetDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDatasetDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDatasetDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetDetailsRepository.findAll().size();

        // Create the DatasetDetails with an existing ID
        datasetDetails.setId(1L);
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetDetailsMockMvc
            .perform(
                post("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetDetails in the database
        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetDetailsRepository.findAll().size();
        // set the field null
        datasetDetails.setDatasetId(null);

        // Create the DatasetDetails, which fails.
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);

        restDatasetDetailsMockMvc
            .perform(
                post("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetDetailsRepository.findAll().size();
        // set the field null
        datasetDetails.setCreatedBy(null);

        // Create the DatasetDetails, which fails.
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);

        restDatasetDetailsMockMvc
            .perform(
                post("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetDetailsRepository.findAll().size();
        // set the field null
        datasetDetails.setCreatedDate(null);

        // Create the DatasetDetails, which fails.
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);

        restDatasetDetailsMockMvc
            .perform(
                post("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetDetails() throws Exception {
        // Initialize the database
        datasetDetailsRepository.saveAndFlush(datasetDetails);

        // Get all the datasetDetailsList
        restDatasetDetailsMockMvc
            .perform(get("/api/dataset-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetId").value(hasItem(DEFAULT_DATASET_ID.intValue())))
            .andExpect(jsonPath("$.[*].datasetName").value(hasItem(DEFAULT_DATASET_NAME.toString())))
            .andExpect(jsonPath("$.[*].datasetOriginSource").value(hasItem(DEFAULT_DATASET_ORIGIN_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].currentDataLocationRef").value(hasItem(DEFAULT_CURRENT_DATA_LOCATION_REF.toString())))
            .andExpect(jsonPath("$.[*].meteorSpaceDominoUsageFlag").value(hasItem(DEFAULT_METEOR_SPACE_DOMINO_USAGE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].ihdFlag").value(hasItem(DEFAULT_IHD_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].datasetRequiredForRef").value(hasItem(DEFAULT_DATASET_REQUIRED_FOR_REF.toString())))
            .andExpect(jsonPath("$.[*].estimatedDataVolumeRef").value(hasItem(DEFAULT_ESTIMATED_DATA_VOLUME_REF.toString())))
            .andExpect(jsonPath("$.[*].analysisInitDt").value(hasItem(DEFAULT_ANALYSIS_INIT_DT.toString())))
            .andExpect(jsonPath("$.[*].analysisEndDt").value(hasItem(DEFAULT_ANALYSIS_END_DT.toString())))
            .andExpect(jsonPath("$.[*].dtaContractCompleteFlag").value(hasItem(DEFAULT_DTA_CONTRACT_COMPLETE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].dtaExpectedCompletionDate").value(hasItem(DEFAULT_DTA_EXPECTED_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].datasetTypeRef").value(hasItem(DEFAULT_DATASET_TYPE_REF.toString())))
            .andExpect(
                jsonPath("$.[*].informationClassificationTypeRef").value(hasItem(DEFAULT_INFORMATION_CLASSIFICATION_TYPE_REF.toString()))
            )
            .andExpect(jsonPath("$.[*].piiTypeRef").value(hasItem(DEFAULT_PII_TYPE_REF.toString())))
            .andExpect(jsonPath("$.[*].retentionRules").value(hasItem(DEFAULT_RETENTION_RULES.toString())))
            .andExpect(jsonPath("$.[*].retentionRulesContractDate").value(hasItem(DEFAULT_RETENTION_RULES_CONTRACT_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractPartner").value(hasItem(DEFAULT_CONTRACT_PARTNER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDatasetDetails() throws Exception {
        // Initialize the database
        datasetDetailsRepository.saveAndFlush(datasetDetails);

        // Get the datasetDetails
        restDatasetDetailsMockMvc
            .perform(get("/api/dataset-details/{id}", datasetDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetDetails.getId().intValue()))
            .andExpect(jsonPath("$.datasetId").value(DEFAULT_DATASET_ID.intValue()))
            .andExpect(jsonPath("$.datasetName").value(DEFAULT_DATASET_NAME.toString()))
            .andExpect(jsonPath("$.datasetOriginSource").value(DEFAULT_DATASET_ORIGIN_SOURCE.toString()))
            .andExpect(jsonPath("$.currentDataLocationRef").value(DEFAULT_CURRENT_DATA_LOCATION_REF.toString()))
            .andExpect(jsonPath("$.meteorSpaceDominoUsageFlag").value(DEFAULT_METEOR_SPACE_DOMINO_USAGE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.ihdFlag").value(DEFAULT_IHD_FLAG.booleanValue()))
            .andExpect(jsonPath("$.datasetRequiredForRef").value(DEFAULT_DATASET_REQUIRED_FOR_REF.toString()))
            .andExpect(jsonPath("$.estimatedDataVolumeRef").value(DEFAULT_ESTIMATED_DATA_VOLUME_REF.toString()))
            .andExpect(jsonPath("$.analysisInitDt").value(DEFAULT_ANALYSIS_INIT_DT.toString()))
            .andExpect(jsonPath("$.analysisEndDt").value(DEFAULT_ANALYSIS_END_DT.toString()))
            .andExpect(jsonPath("$.dtaContractCompleteFlag").value(DEFAULT_DTA_CONTRACT_COMPLETE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.dtaExpectedCompletionDate").value(DEFAULT_DTA_EXPECTED_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.datasetTypeRef").value(DEFAULT_DATASET_TYPE_REF.toString()))
            .andExpect(jsonPath("$.informationClassificationTypeRef").value(DEFAULT_INFORMATION_CLASSIFICATION_TYPE_REF.toString()))
            .andExpect(jsonPath("$.piiTypeRef").value(DEFAULT_PII_TYPE_REF.toString()))
            .andExpect(jsonPath("$.retentionRules").value(DEFAULT_RETENTION_RULES.toString()))
            .andExpect(jsonPath("$.retentionRulesContractDate").value(DEFAULT_RETENTION_RULES_CONTRACT_DATE.toString()))
            .andExpect(jsonPath("$.contractPartner").value(DEFAULT_CONTRACT_PARTNER.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetDetails() throws Exception {
        // Get the datasetDetails
        restDatasetDetailsMockMvc.perform(get("/api/dataset-details/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetDetails() throws Exception {
        // Initialize the database
        datasetDetailsRepository.saveAndFlush(datasetDetails);

        int databaseSizeBeforeUpdate = datasetDetailsRepository.findAll().size();

        // Update the datasetDetails
        DatasetDetails updatedDatasetDetails = datasetDetailsRepository.findById(datasetDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetDetails are not directly saved in db
        em.detach(updatedDatasetDetails);
        updatedDatasetDetails
            .datasetId(UPDATED_DATASET_ID)
            .datasetName(UPDATED_DATASET_NAME)
            .datasetOriginSource(UPDATED_DATASET_ORIGIN_SOURCE)
            .currentDataLocationRef(UPDATED_CURRENT_DATA_LOCATION_REF)
            .meteorSpaceDominoUsageFlag(UPDATED_METEOR_SPACE_DOMINO_USAGE_FLAG)
            .ihdFlag(UPDATED_IHD_FLAG)
            .datasetRequiredForRef(UPDATED_DATASET_REQUIRED_FOR_REF)
            .estimatedDataVolumeRef(UPDATED_ESTIMATED_DATA_VOLUME_REF)
            .analysisInitDt(UPDATED_ANALYSIS_INIT_DT)
            .analysisEndDt(UPDATED_ANALYSIS_END_DT)
            .dtaContractCompleteFlag(UPDATED_DTA_CONTRACT_COMPLETE_FLAG)
            .dtaExpectedCompletionDate(UPDATED_DTA_EXPECTED_COMPLETION_DATE)
            .datasetTypeRef(UPDATED_DATASET_TYPE_REF)
            .informationClassificationTypeRef(UPDATED_INFORMATION_CLASSIFICATION_TYPE_REF)
            .piiTypeRef(UPDATED_PII_TYPE_REF)
            .retentionRules(UPDATED_RETENTION_RULES)
            .retentionRulesContractDate(UPDATED_RETENTION_RULES_CONTRACT_DATE)
            .contractPartner(UPDATED_CONTRACT_PARTNER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(updatedDatasetDetails);

        restDatasetDetailsMockMvc
            .perform(
                put("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetDetails in the database
        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeUpdate);
        DatasetDetails testDatasetDetails = datasetDetailsList.get(datasetDetailsList.size() - 1);
        assertThat(testDatasetDetails.getDatasetId()).isEqualTo(UPDATED_DATASET_ID);
        assertThat(testDatasetDetails.getDatasetName()).isEqualTo(UPDATED_DATASET_NAME);
        assertThat(testDatasetDetails.getDatasetOriginSource()).isEqualTo(UPDATED_DATASET_ORIGIN_SOURCE);
        assertThat(testDatasetDetails.getCurrentDataLocationRef()).isEqualTo(UPDATED_CURRENT_DATA_LOCATION_REF);
        assertThat(testDatasetDetails.isMeteorSpaceDominoUsageFlag()).isEqualTo(UPDATED_METEOR_SPACE_DOMINO_USAGE_FLAG);
        assertThat(testDatasetDetails.isIhdFlag()).isEqualTo(UPDATED_IHD_FLAG);
        assertThat(testDatasetDetails.getDatasetRequiredForRef()).isEqualTo(UPDATED_DATASET_REQUIRED_FOR_REF);
        assertThat(testDatasetDetails.getEstimatedDataVolumeRef()).isEqualTo(UPDATED_ESTIMATED_DATA_VOLUME_REF);
        assertThat(testDatasetDetails.getAnalysisInitDt()).isEqualTo(UPDATED_ANALYSIS_INIT_DT);
        assertThat(testDatasetDetails.getAnalysisEndDt()).isEqualTo(UPDATED_ANALYSIS_END_DT);
        assertThat(testDatasetDetails.isDtaContractCompleteFlag()).isEqualTo(UPDATED_DTA_CONTRACT_COMPLETE_FLAG);
        assertThat(testDatasetDetails.getDtaExpectedCompletionDate()).isEqualTo(UPDATED_DTA_EXPECTED_COMPLETION_DATE);
        assertThat(testDatasetDetails.getDatasetTypeRef()).isEqualTo(UPDATED_DATASET_TYPE_REF);
        assertThat(testDatasetDetails.getInformationClassificationTypeRef()).isEqualTo(UPDATED_INFORMATION_CLASSIFICATION_TYPE_REF);
        assertThat(testDatasetDetails.getPiiTypeRef()).isEqualTo(UPDATED_PII_TYPE_REF);
        assertThat(testDatasetDetails.getRetentionRules()).isEqualTo(UPDATED_RETENTION_RULES);
        assertThat(testDatasetDetails.getRetentionRulesContractDate()).isEqualTo(UPDATED_RETENTION_RULES_CONTRACT_DATE);
        assertThat(testDatasetDetails.getContractPartner()).isEqualTo(UPDATED_CONTRACT_PARTNER);
        assertThat(testDatasetDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDatasetDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDatasetDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDatasetDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetDetails() throws Exception {
        int databaseSizeBeforeUpdate = datasetDetailsRepository.findAll().size();

        // Create the DatasetDetails
        DatasetDetailsDTO datasetDetailsDTO = datasetDetailsMapper.toDto(datasetDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetDetailsMockMvc
            .perform(
                put("/api/dataset-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetDetails in the database
        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetDetails() throws Exception {
        // Initialize the database
        datasetDetailsRepository.saveAndFlush(datasetDetails);

        int databaseSizeBeforeDelete = datasetDetailsRepository.findAll().size();

        // Delete the datasetDetails
        restDatasetDetailsMockMvc
            .perform(delete("/api/dataset-details/{id}", datasetDetails.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetDetails> datasetDetailsList = datasetDetailsRepository.findAll();
        assertThat(datasetDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetDetails.class);
        DatasetDetails datasetDetails1 = new DatasetDetails();
        datasetDetails1.setId(1L);
        DatasetDetails datasetDetails2 = new DatasetDetails();
        datasetDetails2.setId(datasetDetails1.getId());
        assertThat(datasetDetails1).isEqualTo(datasetDetails2);
        datasetDetails2.setId(2L);
        assertThat(datasetDetails1).isNotEqualTo(datasetDetails2);
        datasetDetails1.setId(null);
        assertThat(datasetDetails1).isNotEqualTo(datasetDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetDetailsDTO.class);
        DatasetDetailsDTO datasetDetailsDTO1 = new DatasetDetailsDTO();
        datasetDetailsDTO1.setId(1L);
        DatasetDetailsDTO datasetDetailsDTO2 = new DatasetDetailsDTO();
        assertThat(datasetDetailsDTO1).isNotEqualTo(datasetDetailsDTO2);
        datasetDetailsDTO2.setId(datasetDetailsDTO1.getId());
        assertThat(datasetDetailsDTO1).isEqualTo(datasetDetailsDTO2);
        datasetDetailsDTO2.setId(2L);
        assertThat(datasetDetailsDTO1).isNotEqualTo(datasetDetailsDTO2);
        datasetDetailsDTO1.setId(null);
        assertThat(datasetDetailsDTO1).isNotEqualTo(datasetDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetDetailsMapper.fromId(null)).isNull();
    }
}
