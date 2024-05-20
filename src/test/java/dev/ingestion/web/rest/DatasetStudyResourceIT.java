package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetStudy;
import dev.ingestion.repository.DatasetStudyRepository;
import dev.ingestion.service.DatasetStudyService;
import dev.ingestion.service.dto.DatasetStudyDTO;
import dev.ingestion.service.mapper.DatasetStudyMapper;
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
 * Integration tests for the {@link DatasetStudyResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetStudyResourceIT {

    private static final Long DEFAULT_DATASET_STUDY_ID = 1L;
    private static final Long UPDATED_DATASET_STUDY_ID = 2L;
    private static final Long SMALLER_DATASET_STUDY_ID = 1L - 1L;

    private static final String DEFAULT_STUDY_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDY_ID = "BBBBBBBBBB";

    @Autowired
    private DatasetStudyRepository datasetStudyRepository;

    @Autowired
    private DatasetStudyMapper datasetStudyMapper;

    @Autowired
    private DatasetStudyService datasetStudyService;

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

    private MockMvc restDatasetStudyMockMvc;

    private DatasetStudy datasetStudy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetStudyResource datasetStudyResource = new DatasetStudyResource(datasetStudyService);
        this.restDatasetStudyMockMvc = MockMvcBuilders.standaloneSetup(datasetStudyResource)
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
    public static DatasetStudy createEntity(EntityManager em) {
        DatasetStudy datasetStudy = new DatasetStudy().datasetStudyId(DEFAULT_DATASET_STUDY_ID).studyId(DEFAULT_STUDY_ID);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetStudy.setDatasetId(datasetDetails);
        return datasetStudy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetStudy createUpdatedEntity(EntityManager em) {
        DatasetStudy datasetStudy = new DatasetStudy().datasetStudyId(UPDATED_DATASET_STUDY_ID).studyId(UPDATED_STUDY_ID);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetStudy.setDatasetId(datasetDetails);
        return datasetStudy;
    }

    @BeforeEach
    public void initTest() {
        datasetStudy = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetStudy() throws Exception {
        int databaseSizeBeforeCreate = datasetStudyRepository.findAll().size();

        // Create the DatasetStudy
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(datasetStudy);
        restDatasetStudyMockMvc
            .perform(
                post("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetStudy in the database
        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetStudy testDatasetStudy = datasetStudyList.get(datasetStudyList.size() - 1);
        assertThat(testDatasetStudy.getDatasetStudyId()).isEqualTo(DEFAULT_DATASET_STUDY_ID);
        assertThat(testDatasetStudy.getStudyId()).isEqualTo(DEFAULT_STUDY_ID);
    }

    @Test
    @Transactional
    public void createDatasetStudyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetStudyRepository.findAll().size();

        // Create the DatasetStudy with an existing ID
        datasetStudy.setId(1L);
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(datasetStudy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetStudyMockMvc
            .perform(
                post("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetStudy in the database
        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetStudyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetStudyRepository.findAll().size();
        // set the field null
        datasetStudy.setDatasetStudyId(null);

        // Create the DatasetStudy, which fails.
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(datasetStudy);

        restDatasetStudyMockMvc
            .perform(
                post("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetStudyRepository.findAll().size();
        // set the field null
        datasetStudy.setStudyId(null);

        // Create the DatasetStudy, which fails.
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(datasetStudy);

        restDatasetStudyMockMvc
            .perform(
                post("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetStudies() throws Exception {
        // Initialize the database
        datasetStudyRepository.saveAndFlush(datasetStudy);

        // Get all the datasetStudyList
        restDatasetStudyMockMvc
            .perform(get("/api/dataset-studies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetStudy.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetStudyId").value(hasItem(DEFAULT_DATASET_STUDY_ID.intValue())))
            .andExpect(jsonPath("$.[*].studyId").value(hasItem(DEFAULT_STUDY_ID.toString())));
    }

    @Test
    @Transactional
    public void getDatasetStudy() throws Exception {
        // Initialize the database
        datasetStudyRepository.saveAndFlush(datasetStudy);

        // Get the datasetStudy
        restDatasetStudyMockMvc
            .perform(get("/api/dataset-studies/{id}", datasetStudy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetStudy.getId().intValue()))
            .andExpect(jsonPath("$.datasetStudyId").value(DEFAULT_DATASET_STUDY_ID.intValue()))
            .andExpect(jsonPath("$.studyId").value(DEFAULT_STUDY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetStudy() throws Exception {
        // Get the datasetStudy
        restDatasetStudyMockMvc.perform(get("/api/dataset-studies/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetStudy() throws Exception {
        // Initialize the database
        datasetStudyRepository.saveAndFlush(datasetStudy);

        int databaseSizeBeforeUpdate = datasetStudyRepository.findAll().size();

        // Update the datasetStudy
        DatasetStudy updatedDatasetStudy = datasetStudyRepository.findById(datasetStudy.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetStudy are not directly saved in db
        em.detach(updatedDatasetStudy);
        updatedDatasetStudy.datasetStudyId(UPDATED_DATASET_STUDY_ID).studyId(UPDATED_STUDY_ID);
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(updatedDatasetStudy);

        restDatasetStudyMockMvc
            .perform(
                put("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetStudy in the database
        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeUpdate);
        DatasetStudy testDatasetStudy = datasetStudyList.get(datasetStudyList.size() - 1);
        assertThat(testDatasetStudy.getDatasetStudyId()).isEqualTo(UPDATED_DATASET_STUDY_ID);
        assertThat(testDatasetStudy.getStudyId()).isEqualTo(UPDATED_STUDY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetStudy() throws Exception {
        int databaseSizeBeforeUpdate = datasetStudyRepository.findAll().size();

        // Create the DatasetStudy
        DatasetStudyDTO datasetStudyDTO = datasetStudyMapper.toDto(datasetStudy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetStudyMockMvc
            .perform(
                put("/api/dataset-studies")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetStudyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetStudy in the database
        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetStudy() throws Exception {
        // Initialize the database
        datasetStudyRepository.saveAndFlush(datasetStudy);

        int databaseSizeBeforeDelete = datasetStudyRepository.findAll().size();

        // Delete the datasetStudy
        restDatasetStudyMockMvc
            .perform(delete("/api/dataset-studies/{id}", datasetStudy.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetStudy> datasetStudyList = datasetStudyRepository.findAll();
        assertThat(datasetStudyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetStudy.class);
        DatasetStudy datasetStudy1 = new DatasetStudy();
        datasetStudy1.setId(1L);
        DatasetStudy datasetStudy2 = new DatasetStudy();
        datasetStudy2.setId(datasetStudy1.getId());
        assertThat(datasetStudy1).isEqualTo(datasetStudy2);
        datasetStudy2.setId(2L);
        assertThat(datasetStudy1).isNotEqualTo(datasetStudy2);
        datasetStudy1.setId(null);
        assertThat(datasetStudy1).isNotEqualTo(datasetStudy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetStudyDTO.class);
        DatasetStudyDTO datasetStudyDTO1 = new DatasetStudyDTO();
        datasetStudyDTO1.setId(1L);
        DatasetStudyDTO datasetStudyDTO2 = new DatasetStudyDTO();
        assertThat(datasetStudyDTO1).isNotEqualTo(datasetStudyDTO2);
        datasetStudyDTO2.setId(datasetStudyDTO1.getId());
        assertThat(datasetStudyDTO1).isEqualTo(datasetStudyDTO2);
        datasetStudyDTO2.setId(2L);
        assertThat(datasetStudyDTO1).isNotEqualTo(datasetStudyDTO2);
        datasetStudyDTO1.setId(null);
        assertThat(datasetStudyDTO1).isNotEqualTo(datasetStudyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetStudyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetStudyMapper.fromId(null)).isNull();
    }
}
