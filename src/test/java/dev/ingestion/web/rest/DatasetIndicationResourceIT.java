package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetIndication;
import dev.ingestion.repository.DatasetIndicationRepository;
import dev.ingestion.service.DatasetIndicationService;
import dev.ingestion.service.dto.DatasetIndicationDTO;
import dev.ingestion.service.mapper.DatasetIndicationMapper;
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
 * Integration tests for the {@link DatasetIndicationResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetIndicationResourceIT {

    private static final Long DEFAULT_DATASET_INDICATION_ID = 1L;
    private static final Long UPDATED_DATASET_INDICATION_ID = 2L;
    private static final Long SMALLER_DATASET_INDICATION_ID = 1L - 1L;

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    @Autowired
    private DatasetIndicationRepository datasetIndicationRepository;

    @Autowired
    private DatasetIndicationMapper datasetIndicationMapper;

    @Autowired
    private DatasetIndicationService datasetIndicationService;

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

    private MockMvc restDatasetIndicationMockMvc;

    private DatasetIndication datasetIndication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetIndicationResource datasetIndicationResource = new DatasetIndicationResource(datasetIndicationService);
        this.restDatasetIndicationMockMvc = MockMvcBuilders.standaloneSetup(datasetIndicationResource)
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
    public static DatasetIndication createEntity(EntityManager em) {
        DatasetIndication datasetIndication = new DatasetIndication()
            .datasetIndicationId(DEFAULT_DATASET_INDICATION_ID)
            .indication(DEFAULT_INDICATION);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetIndication.setDatasetId(datasetDetails);
        return datasetIndication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetIndication createUpdatedEntity(EntityManager em) {
        DatasetIndication datasetIndication = new DatasetIndication()
            .datasetIndicationId(UPDATED_DATASET_INDICATION_ID)
            .indication(UPDATED_INDICATION);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetIndication.setDatasetId(datasetDetails);
        return datasetIndication;
    }

    @BeforeEach
    public void initTest() {
        datasetIndication = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetIndication() throws Exception {
        int databaseSizeBeforeCreate = datasetIndicationRepository.findAll().size();

        // Create the DatasetIndication
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(datasetIndication);
        restDatasetIndicationMockMvc
            .perform(
                post("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetIndication in the database
        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetIndication testDatasetIndication = datasetIndicationList.get(datasetIndicationList.size() - 1);
        assertThat(testDatasetIndication.getDatasetIndicationId()).isEqualTo(DEFAULT_DATASET_INDICATION_ID);
        assertThat(testDatasetIndication.getIndication()).isEqualTo(DEFAULT_INDICATION);
    }

    @Test
    @Transactional
    public void createDatasetIndicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetIndicationRepository.findAll().size();

        // Create the DatasetIndication with an existing ID
        datasetIndication.setId(1L);
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(datasetIndication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetIndicationMockMvc
            .perform(
                post("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetIndication in the database
        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetIndicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetIndicationRepository.findAll().size();
        // set the field null
        datasetIndication.setDatasetIndicationId(null);

        // Create the DatasetIndication, which fails.
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(datasetIndication);

        restDatasetIndicationMockMvc
            .perform(
                post("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndicationIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetIndicationRepository.findAll().size();
        // set the field null
        datasetIndication.setIndication(null);

        // Create the DatasetIndication, which fails.
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(datasetIndication);

        restDatasetIndicationMockMvc
            .perform(
                post("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetIndications() throws Exception {
        // Initialize the database
        datasetIndicationRepository.saveAndFlush(datasetIndication);

        // Get all the datasetIndicationList
        restDatasetIndicationMockMvc
            .perform(get("/api/dataset-indications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetIndication.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetIndicationId").value(hasItem(DEFAULT_DATASET_INDICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION.toString())));
    }

    @Test
    @Transactional
    public void getDatasetIndication() throws Exception {
        // Initialize the database
        datasetIndicationRepository.saveAndFlush(datasetIndication);

        // Get the datasetIndication
        restDatasetIndicationMockMvc
            .perform(get("/api/dataset-indications/{id}", datasetIndication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetIndication.getId().intValue()))
            .andExpect(jsonPath("$.datasetIndicationId").value(DEFAULT_DATASET_INDICATION_ID.intValue()))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetIndication() throws Exception {
        // Get the datasetIndication
        restDatasetIndicationMockMvc.perform(get("/api/dataset-indications/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetIndication() throws Exception {
        // Initialize the database
        datasetIndicationRepository.saveAndFlush(datasetIndication);

        int databaseSizeBeforeUpdate = datasetIndicationRepository.findAll().size();

        // Update the datasetIndication
        DatasetIndication updatedDatasetIndication = datasetIndicationRepository.findById(datasetIndication.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetIndication are not directly saved in db
        em.detach(updatedDatasetIndication);
        updatedDatasetIndication.datasetIndicationId(UPDATED_DATASET_INDICATION_ID).indication(UPDATED_INDICATION);
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(updatedDatasetIndication);

        restDatasetIndicationMockMvc
            .perform(
                put("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetIndication in the database
        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeUpdate);
        DatasetIndication testDatasetIndication = datasetIndicationList.get(datasetIndicationList.size() - 1);
        assertThat(testDatasetIndication.getDatasetIndicationId()).isEqualTo(UPDATED_DATASET_INDICATION_ID);
        assertThat(testDatasetIndication.getIndication()).isEqualTo(UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetIndication() throws Exception {
        int databaseSizeBeforeUpdate = datasetIndicationRepository.findAll().size();

        // Create the DatasetIndication
        DatasetIndicationDTO datasetIndicationDTO = datasetIndicationMapper.toDto(datasetIndication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetIndicationMockMvc
            .perform(
                put("/api/dataset-indications")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetIndicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetIndication in the database
        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetIndication() throws Exception {
        // Initialize the database
        datasetIndicationRepository.saveAndFlush(datasetIndication);

        int databaseSizeBeforeDelete = datasetIndicationRepository.findAll().size();

        // Delete the datasetIndication
        restDatasetIndicationMockMvc
            .perform(delete("/api/dataset-indications/{id}", datasetIndication.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetIndication> datasetIndicationList = datasetIndicationRepository.findAll();
        assertThat(datasetIndicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetIndication.class);
        DatasetIndication datasetIndication1 = new DatasetIndication();
        datasetIndication1.setId(1L);
        DatasetIndication datasetIndication2 = new DatasetIndication();
        datasetIndication2.setId(datasetIndication1.getId());
        assertThat(datasetIndication1).isEqualTo(datasetIndication2);
        datasetIndication2.setId(2L);
        assertThat(datasetIndication1).isNotEqualTo(datasetIndication2);
        datasetIndication1.setId(null);
        assertThat(datasetIndication1).isNotEqualTo(datasetIndication2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetIndicationDTO.class);
        DatasetIndicationDTO datasetIndicationDTO1 = new DatasetIndicationDTO();
        datasetIndicationDTO1.setId(1L);
        DatasetIndicationDTO datasetIndicationDTO2 = new DatasetIndicationDTO();
        assertThat(datasetIndicationDTO1).isNotEqualTo(datasetIndicationDTO2);
        datasetIndicationDTO2.setId(datasetIndicationDTO1.getId());
        assertThat(datasetIndicationDTO1).isEqualTo(datasetIndicationDTO2);
        datasetIndicationDTO2.setId(2L);
        assertThat(datasetIndicationDTO1).isNotEqualTo(datasetIndicationDTO2);
        datasetIndicationDTO1.setId(null);
        assertThat(datasetIndicationDTO1).isNotEqualTo(datasetIndicationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetIndicationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetIndicationMapper.fromId(null)).isNull();
    }
}
