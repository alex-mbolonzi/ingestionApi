package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetTechAndAssay;
import dev.ingestion.repository.DatasetTechAndAssayRepository;
import dev.ingestion.service.DatasetTechAndAssayService;
import dev.ingestion.service.dto.DatasetTechAndAssayDTO;
import dev.ingestion.service.mapper.DatasetTechAndAssayMapper;
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
 * Integration tests for the {@link DatasetTechAndAssayResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetTechAndAssayResourceIT {

    private static final Long DEFAULT_DATASET_TECH_AND_ASSAY_ID = 1L;
    private static final Long UPDATED_DATASET_TECH_AND_ASSAY_ID = 2L;
    private static final Long SMALLER_DATASET_TECH_AND_ASSAY_ID = 1L - 1L;

    private static final String DEFAULT_TECHNIQUE_AND_ASSAY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNIQUE_AND_ASSAY = "BBBBBBBBBB";

    @Autowired
    private DatasetTechAndAssayRepository datasetTechAndAssayRepository;

    @Autowired
    private DatasetTechAndAssayMapper datasetTechAndAssayMapper;

    @Autowired
    private DatasetTechAndAssayService datasetTechAndAssayService;

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

    private MockMvc restDatasetTechAndAssayMockMvc;

    private DatasetTechAndAssay datasetTechAndAssay;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetTechAndAssayResource datasetTechAndAssayResource = new DatasetTechAndAssayResource(datasetTechAndAssayService);
        this.restDatasetTechAndAssayMockMvc = MockMvcBuilders.standaloneSetup(datasetTechAndAssayResource)
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
    public static DatasetTechAndAssay createEntity(EntityManager em) {
        DatasetTechAndAssay datasetTechAndAssay = new DatasetTechAndAssay()
            .datasetTechAndAssayId(DEFAULT_DATASET_TECH_AND_ASSAY_ID)
            .techniqueAndAssay(DEFAULT_TECHNIQUE_AND_ASSAY);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetTechAndAssay.setDatasetId(datasetDetails);
        return datasetTechAndAssay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetTechAndAssay createUpdatedEntity(EntityManager em) {
        DatasetTechAndAssay datasetTechAndAssay = new DatasetTechAndAssay()
            .datasetTechAndAssayId(UPDATED_DATASET_TECH_AND_ASSAY_ID)
            .techniqueAndAssay(UPDATED_TECHNIQUE_AND_ASSAY);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetTechAndAssay.setDatasetId(datasetDetails);
        return datasetTechAndAssay;
    }

    @BeforeEach
    public void initTest() {
        datasetTechAndAssay = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetTechAndAssay() throws Exception {
        int databaseSizeBeforeCreate = datasetTechAndAssayRepository.findAll().size();

        // Create the DatasetTechAndAssay
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(datasetTechAndAssay);
        restDatasetTechAndAssayMockMvc
            .perform(
                post("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetTechAndAssay in the database
        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetTechAndAssay testDatasetTechAndAssay = datasetTechAndAssayList.get(datasetTechAndAssayList.size() - 1);
        assertThat(testDatasetTechAndAssay.getDatasetTechAndAssayId()).isEqualTo(DEFAULT_DATASET_TECH_AND_ASSAY_ID);
        assertThat(testDatasetTechAndAssay.getTechniqueAndAssay()).isEqualTo(DEFAULT_TECHNIQUE_AND_ASSAY);
    }

    @Test
    @Transactional
    public void createDatasetTechAndAssayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetTechAndAssayRepository.findAll().size();

        // Create the DatasetTechAndAssay with an existing ID
        datasetTechAndAssay.setId(1L);
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(datasetTechAndAssay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetTechAndAssayMockMvc
            .perform(
                post("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetTechAndAssay in the database
        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetTechAndAssayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetTechAndAssayRepository.findAll().size();
        // set the field null
        datasetTechAndAssay.setDatasetTechAndAssayId(null);

        // Create the DatasetTechAndAssay, which fails.
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(datasetTechAndAssay);

        restDatasetTechAndAssayMockMvc
            .perform(
                post("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTechniqueAndAssayIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetTechAndAssayRepository.findAll().size();
        // set the field null
        datasetTechAndAssay.setTechniqueAndAssay(null);

        // Create the DatasetTechAndAssay, which fails.
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(datasetTechAndAssay);

        restDatasetTechAndAssayMockMvc
            .perform(
                post("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetTechAndAssays() throws Exception {
        // Initialize the database
        datasetTechAndAssayRepository.saveAndFlush(datasetTechAndAssay);

        // Get all the datasetTechAndAssayList
        restDatasetTechAndAssayMockMvc
            .perform(get("/api/dataset-tech-and-assays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetTechAndAssay.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetTechAndAssayId").value(hasItem(DEFAULT_DATASET_TECH_AND_ASSAY_ID.intValue())))
            .andExpect(jsonPath("$.[*].techniqueAndAssay").value(hasItem(DEFAULT_TECHNIQUE_AND_ASSAY.toString())));
    }

    @Test
    @Transactional
    public void getDatasetTechAndAssay() throws Exception {
        // Initialize the database
        datasetTechAndAssayRepository.saveAndFlush(datasetTechAndAssay);

        // Get the datasetTechAndAssay
        restDatasetTechAndAssayMockMvc
            .perform(get("/api/dataset-tech-and-assays/{id}", datasetTechAndAssay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetTechAndAssay.getId().intValue()))
            .andExpect(jsonPath("$.datasetTechAndAssayId").value(DEFAULT_DATASET_TECH_AND_ASSAY_ID.intValue()))
            .andExpect(jsonPath("$.techniqueAndAssay").value(DEFAULT_TECHNIQUE_AND_ASSAY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetTechAndAssay() throws Exception {
        // Get the datasetTechAndAssay
        restDatasetTechAndAssayMockMvc.perform(get("/api/dataset-tech-and-assays/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetTechAndAssay() throws Exception {
        // Initialize the database
        datasetTechAndAssayRepository.saveAndFlush(datasetTechAndAssay);

        int databaseSizeBeforeUpdate = datasetTechAndAssayRepository.findAll().size();

        // Update the datasetTechAndAssay
        DatasetTechAndAssay updatedDatasetTechAndAssay = datasetTechAndAssayRepository.findById(datasetTechAndAssay.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetTechAndAssay are not directly saved in db
        em.detach(updatedDatasetTechAndAssay);
        updatedDatasetTechAndAssay.datasetTechAndAssayId(UPDATED_DATASET_TECH_AND_ASSAY_ID).techniqueAndAssay(UPDATED_TECHNIQUE_AND_ASSAY);
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(updatedDatasetTechAndAssay);

        restDatasetTechAndAssayMockMvc
            .perform(
                put("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetTechAndAssay in the database
        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeUpdate);
        DatasetTechAndAssay testDatasetTechAndAssay = datasetTechAndAssayList.get(datasetTechAndAssayList.size() - 1);
        assertThat(testDatasetTechAndAssay.getDatasetTechAndAssayId()).isEqualTo(UPDATED_DATASET_TECH_AND_ASSAY_ID);
        assertThat(testDatasetTechAndAssay.getTechniqueAndAssay()).isEqualTo(UPDATED_TECHNIQUE_AND_ASSAY);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetTechAndAssay() throws Exception {
        int databaseSizeBeforeUpdate = datasetTechAndAssayRepository.findAll().size();

        // Create the DatasetTechAndAssay
        DatasetTechAndAssayDTO datasetTechAndAssayDTO = datasetTechAndAssayMapper.toDto(datasetTechAndAssay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetTechAndAssayMockMvc
            .perform(
                put("/api/dataset-tech-and-assays")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetTechAndAssayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetTechAndAssay in the database
        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetTechAndAssay() throws Exception {
        // Initialize the database
        datasetTechAndAssayRepository.saveAndFlush(datasetTechAndAssay);

        int databaseSizeBeforeDelete = datasetTechAndAssayRepository.findAll().size();

        // Delete the datasetTechAndAssay
        restDatasetTechAndAssayMockMvc
            .perform(delete("/api/dataset-tech-and-assays/{id}", datasetTechAndAssay.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetTechAndAssay> datasetTechAndAssayList = datasetTechAndAssayRepository.findAll();
        assertThat(datasetTechAndAssayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetTechAndAssay.class);
        DatasetTechAndAssay datasetTechAndAssay1 = new DatasetTechAndAssay();
        datasetTechAndAssay1.setId(1L);
        DatasetTechAndAssay datasetTechAndAssay2 = new DatasetTechAndAssay();
        datasetTechAndAssay2.setId(datasetTechAndAssay1.getId());
        assertThat(datasetTechAndAssay1).isEqualTo(datasetTechAndAssay2);
        datasetTechAndAssay2.setId(2L);
        assertThat(datasetTechAndAssay1).isNotEqualTo(datasetTechAndAssay2);
        datasetTechAndAssay1.setId(null);
        assertThat(datasetTechAndAssay1).isNotEqualTo(datasetTechAndAssay2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetTechAndAssayDTO.class);
        DatasetTechAndAssayDTO datasetTechAndAssayDTO1 = new DatasetTechAndAssayDTO();
        datasetTechAndAssayDTO1.setId(1L);
        DatasetTechAndAssayDTO datasetTechAndAssayDTO2 = new DatasetTechAndAssayDTO();
        assertThat(datasetTechAndAssayDTO1).isNotEqualTo(datasetTechAndAssayDTO2);
        datasetTechAndAssayDTO2.setId(datasetTechAndAssayDTO1.getId());
        assertThat(datasetTechAndAssayDTO1).isEqualTo(datasetTechAndAssayDTO2);
        datasetTechAndAssayDTO2.setId(2L);
        assertThat(datasetTechAndAssayDTO1).isNotEqualTo(datasetTechAndAssayDTO2);
        datasetTechAndAssayDTO1.setId(null);
        assertThat(datasetTechAndAssayDTO1).isNotEqualTo(datasetTechAndAssayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetTechAndAssayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetTechAndAssayMapper.fromId(null)).isNull();
    }
}
