package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetUserUsageRestriction;
import dev.ingestion.repository.DatasetUserUsageRestrictionRepository;
import dev.ingestion.service.DatasetUserUsageRestrictionService;
import dev.ingestion.service.dto.DatasetUserUsageRestrictionDTO;
import dev.ingestion.service.mapper.DatasetUserUsageRestrictionMapper;
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
 * Integration tests for the {@link DatasetUserUsageRestrictionResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetUserUsageRestrictionResourceIT {

    private static final Long DEFAULT_USAGE_USER_RESTRICTION_ID = 1L;
    private static final Long UPDATED_USAGE_USER_RESTRICTION_ID = 2L;
    private static final Long SMALLER_USAGE_USER_RESTRICTION_ID = 1L - 1L;

    private static final String DEFAULT_RESTRICTION_REF = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTION_REF = "BBBBBBBBBB";

    private static final String DEFAULT_RESTRICTION_TYPE_REF = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTION_TYPE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_REASON_FOR_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_OTHER = "BBBBBBBBBB";

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
    private DatasetUserUsageRestrictionRepository datasetUserUsageRestrictionRepository;

    @Autowired
    private DatasetUserUsageRestrictionMapper datasetUserUsageRestrictionMapper;

    @Autowired
    private DatasetUserUsageRestrictionService datasetUserUsageRestrictionService;

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

    private MockMvc restDatasetUserUsageRestrictionMockMvc;

    private DatasetUserUsageRestriction datasetUserUsageRestriction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetUserUsageRestrictionResource datasetUserUsageRestrictionResource = new DatasetUserUsageRestrictionResource(
            datasetUserUsageRestrictionService
        );
        this.restDatasetUserUsageRestrictionMockMvc = MockMvcBuilders.standaloneSetup(datasetUserUsageRestrictionResource)
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
    public static DatasetUserUsageRestriction createEntity(EntityManager em) {
        DatasetUserUsageRestriction datasetUserUsageRestriction = new DatasetUserUsageRestriction()
            .usageUserRestrictionId(DEFAULT_USAGE_USER_RESTRICTION_ID)
            .restrictionRef(DEFAULT_RESTRICTION_REF)
            .restrictionTypeRef(DEFAULT_RESTRICTION_TYPE_REF)
            .reasonForOther(DEFAULT_REASON_FOR_OTHER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetUserUsageRestriction.setDatasetId(datasetDetails);
        return datasetUserUsageRestriction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetUserUsageRestriction createUpdatedEntity(EntityManager em) {
        DatasetUserUsageRestriction datasetUserUsageRestriction = new DatasetUserUsageRestriction()
            .usageUserRestrictionId(UPDATED_USAGE_USER_RESTRICTION_ID)
            .restrictionRef(UPDATED_RESTRICTION_REF)
            .restrictionTypeRef(UPDATED_RESTRICTION_TYPE_REF)
            .reasonForOther(UPDATED_REASON_FOR_OTHER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetUserUsageRestriction.setDatasetId(datasetDetails);
        return datasetUserUsageRestriction;
    }

    @BeforeEach
    public void initTest() {
        datasetUserUsageRestriction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetUserUsageRestriction() throws Exception {
        int databaseSizeBeforeCreate = datasetUserUsageRestrictionRepository.findAll().size();

        // Create the DatasetUserUsageRestriction
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );
        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetUserUsageRestriction in the database
        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetUserUsageRestriction testDatasetUserUsageRestriction = datasetUserUsageRestrictionList.get(
            datasetUserUsageRestrictionList.size() - 1
        );
        assertThat(testDatasetUserUsageRestriction.getUsageUserRestrictionId()).isEqualTo(DEFAULT_USAGE_USER_RESTRICTION_ID);
        assertThat(testDatasetUserUsageRestriction.getRestrictionRef()).isEqualTo(DEFAULT_RESTRICTION_REF);
        assertThat(testDatasetUserUsageRestriction.getRestrictionTypeRef()).isEqualTo(DEFAULT_RESTRICTION_TYPE_REF);
        assertThat(testDatasetUserUsageRestriction.getReasonForOther()).isEqualTo(DEFAULT_REASON_FOR_OTHER);
        assertThat(testDatasetUserUsageRestriction.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDatasetUserUsageRestriction.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDatasetUserUsageRestriction.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDatasetUserUsageRestriction.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDatasetUserUsageRestrictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetUserUsageRestrictionRepository.findAll().size();

        // Create the DatasetUserUsageRestriction with an existing ID
        datasetUserUsageRestriction.setId(1L);
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetUserUsageRestriction in the database
        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsageUserRestrictionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetUserUsageRestrictionRepository.findAll().size();
        // set the field null
        datasetUserUsageRestriction.setUsageUserRestrictionId(null);

        // Create the DatasetUserUsageRestriction, which fails.
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRestrictionRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetUserUsageRestrictionRepository.findAll().size();
        // set the field null
        datasetUserUsageRestriction.setRestrictionRef(null);

        // Create the DatasetUserUsageRestriction, which fails.
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRestrictionTypeRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetUserUsageRestrictionRepository.findAll().size();
        // set the field null
        datasetUserUsageRestriction.setRestrictionTypeRef(null);

        // Create the DatasetUserUsageRestriction, which fails.
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetUserUsageRestrictionRepository.findAll().size();
        // set the field null
        datasetUserUsageRestriction.setCreatedBy(null);

        // Create the DatasetUserUsageRestriction, which fails.
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetUserUsageRestrictionRepository.findAll().size();
        // set the field null
        datasetUserUsageRestriction.setCreatedDate(null);

        // Create the DatasetUserUsageRestriction, which fails.
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                post("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetUserUsageRestrictions() throws Exception {
        // Initialize the database
        datasetUserUsageRestrictionRepository.saveAndFlush(datasetUserUsageRestriction);

        // Get all the datasetUserUsageRestrictionList
        restDatasetUserUsageRestrictionMockMvc
            .perform(get("/api/dataset-user-usage-restrictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetUserUsageRestriction.getId().intValue())))
            .andExpect(jsonPath("$.[*].usageUserRestrictionId").value(hasItem(DEFAULT_USAGE_USER_RESTRICTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].restrictionRef").value(hasItem(DEFAULT_RESTRICTION_REF.toString())))
            .andExpect(jsonPath("$.[*].restrictionTypeRef").value(hasItem(DEFAULT_RESTRICTION_TYPE_REF.toString())))
            .andExpect(jsonPath("$.[*].reasonForOther").value(hasItem(DEFAULT_REASON_FOR_OTHER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDatasetUserUsageRestriction() throws Exception {
        // Initialize the database
        datasetUserUsageRestrictionRepository.saveAndFlush(datasetUserUsageRestriction);

        // Get the datasetUserUsageRestriction
        restDatasetUserUsageRestrictionMockMvc
            .perform(get("/api/dataset-user-usage-restrictions/{id}", datasetUserUsageRestriction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetUserUsageRestriction.getId().intValue()))
            .andExpect(jsonPath("$.usageUserRestrictionId").value(DEFAULT_USAGE_USER_RESTRICTION_ID.intValue()))
            .andExpect(jsonPath("$.restrictionRef").value(DEFAULT_RESTRICTION_REF.toString()))
            .andExpect(jsonPath("$.restrictionTypeRef").value(DEFAULT_RESTRICTION_TYPE_REF.toString()))
            .andExpect(jsonPath("$.reasonForOther").value(DEFAULT_REASON_FOR_OTHER.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetUserUsageRestriction() throws Exception {
        // Get the datasetUserUsageRestriction
        restDatasetUserUsageRestrictionMockMvc
            .perform(get("/api/dataset-user-usage-restrictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetUserUsageRestriction() throws Exception {
        // Initialize the database
        datasetUserUsageRestrictionRepository.saveAndFlush(datasetUserUsageRestriction);

        int databaseSizeBeforeUpdate = datasetUserUsageRestrictionRepository.findAll().size();

        // Update the datasetUserUsageRestriction
        DatasetUserUsageRestriction updatedDatasetUserUsageRestriction = datasetUserUsageRestrictionRepository
            .findById(datasetUserUsageRestriction.getId())
            .get();
        // Disconnect from session so that the updates on updatedDatasetUserUsageRestriction are not directly saved in db
        em.detach(updatedDatasetUserUsageRestriction);
        updatedDatasetUserUsageRestriction
            .usageUserRestrictionId(UPDATED_USAGE_USER_RESTRICTION_ID)
            .restrictionRef(UPDATED_RESTRICTION_REF)
            .restrictionTypeRef(UPDATED_RESTRICTION_TYPE_REF)
            .reasonForOther(UPDATED_REASON_FOR_OTHER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            updatedDatasetUserUsageRestriction
        );

        restDatasetUserUsageRestrictionMockMvc
            .perform(
                put("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetUserUsageRestriction in the database
        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeUpdate);
        DatasetUserUsageRestriction testDatasetUserUsageRestriction = datasetUserUsageRestrictionList.get(
            datasetUserUsageRestrictionList.size() - 1
        );
        assertThat(testDatasetUserUsageRestriction.getUsageUserRestrictionId()).isEqualTo(UPDATED_USAGE_USER_RESTRICTION_ID);
        assertThat(testDatasetUserUsageRestriction.getRestrictionRef()).isEqualTo(UPDATED_RESTRICTION_REF);
        assertThat(testDatasetUserUsageRestriction.getRestrictionTypeRef()).isEqualTo(UPDATED_RESTRICTION_TYPE_REF);
        assertThat(testDatasetUserUsageRestriction.getReasonForOther()).isEqualTo(UPDATED_REASON_FOR_OTHER);
        assertThat(testDatasetUserUsageRestriction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDatasetUserUsageRestriction.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDatasetUserUsageRestriction.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDatasetUserUsageRestriction.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetUserUsageRestriction() throws Exception {
        int databaseSizeBeforeUpdate = datasetUserUsageRestrictionRepository.findAll().size();

        // Create the DatasetUserUsageRestriction
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionMapper.toDto(
            datasetUserUsageRestriction
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetUserUsageRestrictionMockMvc
            .perform(
                put("/api/dataset-user-usage-restrictions")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetUserUsageRestrictionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetUserUsageRestriction in the database
        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetUserUsageRestriction() throws Exception {
        // Initialize the database
        datasetUserUsageRestrictionRepository.saveAndFlush(datasetUserUsageRestriction);

        int databaseSizeBeforeDelete = datasetUserUsageRestrictionRepository.findAll().size();

        // Delete the datasetUserUsageRestriction
        restDatasetUserUsageRestrictionMockMvc
            .perform(
                delete("/api/dataset-user-usage-restrictions/{id}", datasetUserUsageRestriction.getId()).accept(
                    TestUtil.APPLICATION_JSON_UTF8
                )
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetUserUsageRestriction> datasetUserUsageRestrictionList = datasetUserUsageRestrictionRepository.findAll();
        assertThat(datasetUserUsageRestrictionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetUserUsageRestriction.class);
        DatasetUserUsageRestriction datasetUserUsageRestriction1 = new DatasetUserUsageRestriction();
        datasetUserUsageRestriction1.setId(1L);
        DatasetUserUsageRestriction datasetUserUsageRestriction2 = new DatasetUserUsageRestriction();
        datasetUserUsageRestriction2.setId(datasetUserUsageRestriction1.getId());
        assertThat(datasetUserUsageRestriction1).isEqualTo(datasetUserUsageRestriction2);
        datasetUserUsageRestriction2.setId(2L);
        assertThat(datasetUserUsageRestriction1).isNotEqualTo(datasetUserUsageRestriction2);
        datasetUserUsageRestriction1.setId(null);
        assertThat(datasetUserUsageRestriction1).isNotEqualTo(datasetUserUsageRestriction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetUserUsageRestrictionDTO.class);
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO1 = new DatasetUserUsageRestrictionDTO();
        datasetUserUsageRestrictionDTO1.setId(1L);
        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO2 = new DatasetUserUsageRestrictionDTO();
        assertThat(datasetUserUsageRestrictionDTO1).isNotEqualTo(datasetUserUsageRestrictionDTO2);
        datasetUserUsageRestrictionDTO2.setId(datasetUserUsageRestrictionDTO1.getId());
        assertThat(datasetUserUsageRestrictionDTO1).isEqualTo(datasetUserUsageRestrictionDTO2);
        datasetUserUsageRestrictionDTO2.setId(2L);
        assertThat(datasetUserUsageRestrictionDTO1).isNotEqualTo(datasetUserUsageRestrictionDTO2);
        datasetUserUsageRestrictionDTO1.setId(null);
        assertThat(datasetUserUsageRestrictionDTO1).isNotEqualTo(datasetUserUsageRestrictionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetUserUsageRestrictionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetUserUsageRestrictionMapper.fromId(null)).isNull();
    }
}
