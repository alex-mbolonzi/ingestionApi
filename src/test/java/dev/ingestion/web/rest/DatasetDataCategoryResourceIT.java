package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDataCategory;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.repository.DatasetDataCategoryRepository;
import dev.ingestion.service.DatasetDataCategoryService;
import dev.ingestion.service.dto.DatasetDataCategoryDTO;
import dev.ingestion.service.mapper.DatasetDataCategoryMapper;
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
 * Integration tests for the {@link DatasetDataCategoryResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetDataCategoryResourceIT {

    private static final Long DEFAULT_DATASET_DATA_CATEGORY_ID = 1L;
    private static final Long UPDATED_DATASET_DATA_CATEGORY_ID = 2L;
    private static final Long SMALLER_DATASET_DATA_CATEGORY_ID = 1L - 1L;

    private static final String DEFAULT_DATA_CATEGORY_REF = "AAAAAAAAAA";
    private static final String UPDATED_DATA_CATEGORY_REF = "BBBBBBBBBB";

    @Autowired
    private DatasetDataCategoryRepository datasetDataCategoryRepository;

    @Autowired
    private DatasetDataCategoryMapper datasetDataCategoryMapper;

    @Autowired
    private DatasetDataCategoryService datasetDataCategoryService;

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

    private MockMvc restDatasetDataCategoryMockMvc;

    private DatasetDataCategory datasetDataCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetDataCategoryResource datasetDataCategoryResource = new DatasetDataCategoryResource(datasetDataCategoryService);
        this.restDatasetDataCategoryMockMvc = MockMvcBuilders.standaloneSetup(datasetDataCategoryResource)
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
    public static DatasetDataCategory createEntity(EntityManager em) {
        DatasetDataCategory datasetDataCategory = new DatasetDataCategory()
            .datasetDataCategoryId(DEFAULT_DATASET_DATA_CATEGORY_ID)
            .dataCategoryRef(DEFAULT_DATA_CATEGORY_REF);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetDataCategory.setDatasetId(datasetDetails);
        return datasetDataCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetDataCategory createUpdatedEntity(EntityManager em) {
        DatasetDataCategory datasetDataCategory = new DatasetDataCategory()
            .datasetDataCategoryId(UPDATED_DATASET_DATA_CATEGORY_ID)
            .dataCategoryRef(UPDATED_DATA_CATEGORY_REF);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetDataCategory.setDatasetId(datasetDetails);
        return datasetDataCategory;
    }

    @BeforeEach
    public void initTest() {
        datasetDataCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetDataCategory() throws Exception {
        int databaseSizeBeforeCreate = datasetDataCategoryRepository.findAll().size();

        // Create the DatasetDataCategory
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(datasetDataCategory);
        restDatasetDataCategoryMockMvc
            .perform(
                post("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetDataCategory in the database
        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetDataCategory testDatasetDataCategory = datasetDataCategoryList.get(datasetDataCategoryList.size() - 1);
        assertThat(testDatasetDataCategory.getDatasetDataCategoryId()).isEqualTo(DEFAULT_DATASET_DATA_CATEGORY_ID);
        assertThat(testDatasetDataCategory.getDataCategoryRef()).isEqualTo(DEFAULT_DATA_CATEGORY_REF);
    }

    @Test
    @Transactional
    public void createDatasetDataCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetDataCategoryRepository.findAll().size();

        // Create the DatasetDataCategory with an existing ID
        datasetDataCategory.setId(1L);
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(datasetDataCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetDataCategoryMockMvc
            .perform(
                post("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetDataCategory in the database
        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetDataCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetDataCategoryRepository.findAll().size();
        // set the field null
        datasetDataCategory.setDatasetDataCategoryId(null);

        // Create the DatasetDataCategory, which fails.
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(datasetDataCategory);

        restDatasetDataCategoryMockMvc
            .perform(
                post("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataCategoryRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetDataCategoryRepository.findAll().size();
        // set the field null
        datasetDataCategory.setDataCategoryRef(null);

        // Create the DatasetDataCategory, which fails.
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(datasetDataCategory);

        restDatasetDataCategoryMockMvc
            .perform(
                post("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetDataCategories() throws Exception {
        // Initialize the database
        datasetDataCategoryRepository.saveAndFlush(datasetDataCategory);

        // Get all the datasetDataCategoryList
        restDatasetDataCategoryMockMvc
            .perform(get("/api/dataset-data-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetDataCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetDataCategoryId").value(hasItem(DEFAULT_DATASET_DATA_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataCategoryRef").value(hasItem(DEFAULT_DATA_CATEGORY_REF.toString())));
    }

    @Test
    @Transactional
    public void getDatasetDataCategory() throws Exception {
        // Initialize the database
        datasetDataCategoryRepository.saveAndFlush(datasetDataCategory);

        // Get the datasetDataCategory
        restDatasetDataCategoryMockMvc
            .perform(get("/api/dataset-data-categories/{id}", datasetDataCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetDataCategory.getId().intValue()))
            .andExpect(jsonPath("$.datasetDataCategoryId").value(DEFAULT_DATASET_DATA_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.dataCategoryRef").value(DEFAULT_DATA_CATEGORY_REF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetDataCategory() throws Exception {
        // Get the datasetDataCategory
        restDatasetDataCategoryMockMvc.perform(get("/api/dataset-data-categories/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetDataCategory() throws Exception {
        // Initialize the database
        datasetDataCategoryRepository.saveAndFlush(datasetDataCategory);

        int databaseSizeBeforeUpdate = datasetDataCategoryRepository.findAll().size();

        // Update the datasetDataCategory
        DatasetDataCategory updatedDatasetDataCategory = datasetDataCategoryRepository.findById(datasetDataCategory.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetDataCategory are not directly saved in db
        em.detach(updatedDatasetDataCategory);
        updatedDatasetDataCategory.datasetDataCategoryId(UPDATED_DATASET_DATA_CATEGORY_ID).dataCategoryRef(UPDATED_DATA_CATEGORY_REF);
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(updatedDatasetDataCategory);

        restDatasetDataCategoryMockMvc
            .perform(
                put("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetDataCategory in the database
        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeUpdate);
        DatasetDataCategory testDatasetDataCategory = datasetDataCategoryList.get(datasetDataCategoryList.size() - 1);
        assertThat(testDatasetDataCategory.getDatasetDataCategoryId()).isEqualTo(UPDATED_DATASET_DATA_CATEGORY_ID);
        assertThat(testDatasetDataCategory.getDataCategoryRef()).isEqualTo(UPDATED_DATA_CATEGORY_REF);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = datasetDataCategoryRepository.findAll().size();

        // Create the DatasetDataCategory
        DatasetDataCategoryDTO datasetDataCategoryDTO = datasetDataCategoryMapper.toDto(datasetDataCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetDataCategoryMockMvc
            .perform(
                put("/api/dataset-data-categories")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetDataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetDataCategory in the database
        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetDataCategory() throws Exception {
        // Initialize the database
        datasetDataCategoryRepository.saveAndFlush(datasetDataCategory);

        int databaseSizeBeforeDelete = datasetDataCategoryRepository.findAll().size();

        // Delete the datasetDataCategory
        restDatasetDataCategoryMockMvc
            .perform(delete("/api/dataset-data-categories/{id}", datasetDataCategory.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetDataCategory> datasetDataCategoryList = datasetDataCategoryRepository.findAll();
        assertThat(datasetDataCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetDataCategory.class);
        DatasetDataCategory datasetDataCategory1 = new DatasetDataCategory();
        datasetDataCategory1.setId(1L);
        DatasetDataCategory datasetDataCategory2 = new DatasetDataCategory();
        datasetDataCategory2.setId(datasetDataCategory1.getId());
        assertThat(datasetDataCategory1).isEqualTo(datasetDataCategory2);
        datasetDataCategory2.setId(2L);
        assertThat(datasetDataCategory1).isNotEqualTo(datasetDataCategory2);
        datasetDataCategory1.setId(null);
        assertThat(datasetDataCategory1).isNotEqualTo(datasetDataCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetDataCategoryDTO.class);
        DatasetDataCategoryDTO datasetDataCategoryDTO1 = new DatasetDataCategoryDTO();
        datasetDataCategoryDTO1.setId(1L);
        DatasetDataCategoryDTO datasetDataCategoryDTO2 = new DatasetDataCategoryDTO();
        assertThat(datasetDataCategoryDTO1).isNotEqualTo(datasetDataCategoryDTO2);
        datasetDataCategoryDTO2.setId(datasetDataCategoryDTO1.getId());
        assertThat(datasetDataCategoryDTO1).isEqualTo(datasetDataCategoryDTO2);
        datasetDataCategoryDTO2.setId(2L);
        assertThat(datasetDataCategoryDTO1).isNotEqualTo(datasetDataCategoryDTO2);
        datasetDataCategoryDTO1.setId(null);
        assertThat(datasetDataCategoryDTO1).isNotEqualTo(datasetDataCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetDataCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetDataCategoryMapper.fromId(null)).isNull();
    }
}
