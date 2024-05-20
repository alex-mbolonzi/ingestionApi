package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetTherapy;
import dev.ingestion.repository.DatasetTherapyRepository;
import dev.ingestion.service.DatasetTherapyService;
import dev.ingestion.service.dto.DatasetTherapyDTO;
import dev.ingestion.service.mapper.DatasetTherapyMapper;
import dev.ingestion.web.rest.errors.ExceptionTranslator;
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
 * Integration tests for the {@link DatasetTherapyResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetTherapyResourceIT {

    private static final Long DEFAULT_DATASET_THERAPHY_ID = 1L;
    private static final Long UPDATED_DATASET_THERAPHY_ID = 2L;
    private static final Long SMALLER_DATASET_THERAPHY_ID = 1L - 1L;

    private static final String DEFAULT_THERAPY = "AAAAAAAAAA";
    private static final String UPDATED_THERAPY = "BBBBBBBBBB";

    @Autowired
    private DatasetTherapyRepository datasetTherapyRepository;

    @Autowired
    private DatasetTherapyMapper datasetTherapyMapper;

    @Autowired
    private DatasetTherapyService datasetTherapyService;

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

    private MockMvc restDatasetTherapyMockMvc;

    private DatasetTherapy datasetTherapy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetTherapyResource datasetTherapyResource = new DatasetTherapyResource(datasetTherapyService);
        this.restDatasetTherapyMockMvc = MockMvcBuilders.standaloneSetup(datasetTherapyResource)
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
    public static DatasetTherapy createEntity(EntityManager em) {
        DatasetTherapy datasetTherapy = new DatasetTherapy().datasetTheraphyId(DEFAULT_DATASET_THERAPHY_ID).therapy(DEFAULT_THERAPY);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetTherapy.setDatasetId(datasetDetails);
        return datasetTherapy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetTherapy createUpdatedEntity(EntityManager em) {
        DatasetTherapy datasetTherapy = new DatasetTherapy().datasetTheraphyId(UPDATED_DATASET_THERAPHY_ID).therapy(UPDATED_THERAPY);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetTherapy.setDatasetId(datasetDetails);
        return datasetTherapy;
    }

    @BeforeEach
    public void initTest() {
        datasetTherapy = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetTherapy() throws Exception {
        int databaseSizeBeforeCreate = datasetTherapyRepository.findAll().size();

        // Create the DatasetTherapy
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(datasetTherapy);
        restDatasetTherapyMockMvc
            .perform(
                post("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetTherapy in the database
        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetTherapy testDatasetTherapy = datasetTherapyList.get(datasetTherapyList.size() - 1);
        assertThat(testDatasetTherapy.getDatasetTheraphyId()).isEqualTo(DEFAULT_DATASET_THERAPHY_ID);
        assertThat(testDatasetTherapy.getTherapy()).isEqualTo(DEFAULT_THERAPY);
    }

    @Test
    @Transactional
    public void createDatasetTherapyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetTherapyRepository.findAll().size();

        // Create the DatasetTherapy with an existing ID
        datasetTherapy.setId(1L);
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(datasetTherapy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetTherapyMockMvc
            .perform(
                post("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetTherapy in the database
        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetTheraphyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetTherapyRepository.findAll().size();
        // set the field null
        datasetTherapy.setDatasetTheraphyId(null);

        // Create the DatasetTherapy, which fails.
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(datasetTherapy);

        restDatasetTherapyMockMvc
            .perform(
                post("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTherapyIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetTherapyRepository.findAll().size();
        // set the field null
        datasetTherapy.setTherapy(null);

        // Create the DatasetTherapy, which fails.
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(datasetTherapy);

        restDatasetTherapyMockMvc
            .perform(
                post("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetTherapies() throws Exception {
        // Initialize the database
        datasetTherapyRepository.saveAndFlush(datasetTherapy);

        // Get all the datasetTherapyList
        restDatasetTherapyMockMvc
            .perform(get("/api/dataset-therapies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetTherapy.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetTheraphyId").value(hasItem(DEFAULT_DATASET_THERAPHY_ID.intValue())))
            .andExpect(jsonPath("$.[*].therapy").value(hasItem(DEFAULT_THERAPY.toString())));
    }

    @Test
    @Transactional
    public void getDatasetTherapy() throws Exception {
        // Initialize the database
        datasetTherapyRepository.saveAndFlush(datasetTherapy);

        // Get the datasetTherapy
        restDatasetTherapyMockMvc
            .perform(get("/api/dataset-therapies/{id}", datasetTherapy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetTherapy.getId().intValue()))
            .andExpect(jsonPath("$.datasetTheraphyId").value(DEFAULT_DATASET_THERAPHY_ID.intValue()))
            .andExpect(jsonPath("$.therapy").value(DEFAULT_THERAPY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetTherapy() throws Exception {
        // Get the datasetTherapy
        restDatasetTherapyMockMvc.perform(get("/api/dataset-therapies/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetTherapy() throws Exception {
        // Initialize the database
        datasetTherapyRepository.saveAndFlush(datasetTherapy);

        int databaseSizeBeforeUpdate = datasetTherapyRepository.findAll().size();

        // Update the datasetTherapy
        DatasetTherapy updatedDatasetTherapy = datasetTherapyRepository.findById(datasetTherapy.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetTherapy are not directly saved in db
        em.detach(updatedDatasetTherapy);
        updatedDatasetTherapy.datasetTheraphyId(UPDATED_DATASET_THERAPHY_ID).therapy(UPDATED_THERAPY);
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(updatedDatasetTherapy);

        restDatasetTherapyMockMvc
            .perform(
                put("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetTherapy in the database
        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeUpdate);
        DatasetTherapy testDatasetTherapy = datasetTherapyList.get(datasetTherapyList.size() - 1);
        assertThat(testDatasetTherapy.getDatasetTheraphyId()).isEqualTo(UPDATED_DATASET_THERAPHY_ID);
        assertThat(testDatasetTherapy.getTherapy()).isEqualTo(UPDATED_THERAPY);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetTherapy() throws Exception {
        int databaseSizeBeforeUpdate = datasetTherapyRepository.findAll().size();

        // Create the DatasetTherapy
        DatasetTherapyDTO datasetTherapyDTO = datasetTherapyMapper.toDto(datasetTherapy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetTherapyMockMvc
            .perform(
                put("/api/dataset-therapies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTherapyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetTherapy in the database
        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetTherapy() throws Exception {
        // Initialize the database
        datasetTherapyRepository.saveAndFlush(datasetTherapy);

        int databaseSizeBeforeDelete = datasetTherapyRepository.findAll().size();

        // Delete the datasetTherapy
        restDatasetTherapyMockMvc
            .perform(delete("/api/dataset-therapies/{id}", datasetTherapy.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetTherapy> datasetTherapyList = datasetTherapyRepository.findAll();
        assertThat(datasetTherapyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetTherapy.class);
        DatasetTherapy datasetTherapy1 = new DatasetTherapy();
        datasetTherapy1.setId(1L);
        DatasetTherapy datasetTherapy2 = new DatasetTherapy();
        datasetTherapy2.setId(datasetTherapy1.getId());
        assertThat(datasetTherapy1).isEqualTo(datasetTherapy2);
        datasetTherapy2.setId(2L);
        assertThat(datasetTherapy1).isNotEqualTo(datasetTherapy2);
        datasetTherapy1.setId(null);
        assertThat(datasetTherapy1).isNotEqualTo(datasetTherapy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetTherapyDTO.class);
        DatasetTherapyDTO datasetTherapyDTO1 = new DatasetTherapyDTO();
        datasetTherapyDTO1.setId(1L);
        DatasetTherapyDTO datasetTherapyDTO2 = new DatasetTherapyDTO();
        assertThat(datasetTherapyDTO1).isNotEqualTo(datasetTherapyDTO2);
        datasetTherapyDTO2.setId(datasetTherapyDTO1.getId());
        assertThat(datasetTherapyDTO1).isEqualTo(datasetTherapyDTO2);
        datasetTherapyDTO2.setId(2L);
        assertThat(datasetTherapyDTO1).isNotEqualTo(datasetTherapyDTO2);
        datasetTherapyDTO1.setId(null);
        assertThat(datasetTherapyDTO1).isNotEqualTo(datasetTherapyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetTherapyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetTherapyMapper.fromId(null)).isNull();
    }
}
