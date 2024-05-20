package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.domain.DatasetRoleDetails;
import dev.ingestion.repository.DatasetRoleDetailsRepository;
import dev.ingestion.service.DatasetRoleDetailsService;
import dev.ingestion.service.dto.DatasetRoleDetailsDTO;
import dev.ingestion.service.mapper.DatasetRoleDetailsMapper;
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
 * Integration tests for the {@link DatasetRoleDetailsResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class DatasetRoleDetailsResourceIT {

    private static final Long DEFAULT_DATASET_ROLE_DETAILS_ID = 1L;
    private static final Long UPDATED_DATASET_ROLE_DETAILS_ID = 2L;
    private static final Long SMALLER_DATASET_ROLE_DETAILS_ID = 1L - 1L;

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MUDID = "AAAAAAAAAA";
    private static final String UPDATED_MUDID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DatasetRoleDetailsRepository datasetRoleDetailsRepository;

    @Autowired
    private DatasetRoleDetailsMapper datasetRoleDetailsMapper;

    @Autowired
    private DatasetRoleDetailsService datasetRoleDetailsService;

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

    private MockMvc restDatasetRoleDetailsMockMvc;

    private DatasetRoleDetails datasetRoleDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetRoleDetailsResource datasetRoleDetailsResource = new DatasetRoleDetailsResource(datasetRoleDetailsService);
        this.restDatasetRoleDetailsMockMvc = MockMvcBuilders.standaloneSetup(datasetRoleDetailsResource)
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
    public static DatasetRoleDetails createEntity(EntityManager em) {
        DatasetRoleDetails datasetRoleDetails = new DatasetRoleDetails()
            .datasetRoleDetailsId(DEFAULT_DATASET_ROLE_DETAILS_ID)
            .role(DEFAULT_ROLE)
            .email(DEFAULT_EMAIL)
            .mudid(DEFAULT_MUDID)
            .name(DEFAULT_NAME);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetRoleDetails.setDatasetId(datasetDetails);
        return datasetRoleDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatasetRoleDetails createUpdatedEntity(EntityManager em) {
        DatasetRoleDetails datasetRoleDetails = new DatasetRoleDetails()
            .datasetRoleDetailsId(UPDATED_DATASET_ROLE_DETAILS_ID)
            .role(UPDATED_ROLE)
            .email(UPDATED_EMAIL)
            .mudid(UPDATED_MUDID)
            .name(UPDATED_NAME);
        // Add required entity
        DatasetDetails datasetDetails;
        if (TestUtil.findAll(em, DatasetDetails.class).isEmpty()) {
            datasetDetails = DatasetDetailsResourceIT.createUpdatedEntity(em);
            em.persist(datasetDetails);
            em.flush();
        } else {
            datasetDetails = TestUtil.findAll(em, DatasetDetails.class).get(0);
        }
        datasetRoleDetails.setDatasetId(datasetDetails);
        return datasetRoleDetails;
    }

    @BeforeEach
    public void initTest() {
        datasetRoleDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatasetRoleDetails() throws Exception {
        int databaseSizeBeforeCreate = datasetRoleDetailsRepository.findAll().size();

        // Create the DatasetRoleDetails
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);
        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DatasetRoleDetails in the database
        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DatasetRoleDetails testDatasetRoleDetails = datasetRoleDetailsList.get(datasetRoleDetailsList.size() - 1);
        assertThat(testDatasetRoleDetails.getDatasetRoleDetailsId()).isEqualTo(DEFAULT_DATASET_ROLE_DETAILS_ID);
        assertThat(testDatasetRoleDetails.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testDatasetRoleDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDatasetRoleDetails.getMudid()).isEqualTo(DEFAULT_MUDID);
        assertThat(testDatasetRoleDetails.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDatasetRoleDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetRoleDetailsRepository.findAll().size();

        // Create the DatasetRoleDetails with an existing ID
        datasetRoleDetails.setId(1L);
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetRoleDetails in the database
        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatasetRoleDetailsIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRoleDetailsRepository.findAll().size();
        // set the field null
        datasetRoleDetails.setDatasetRoleDetailsId(null);

        // Create the DatasetRoleDetails, which fails.
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRoleDetailsRepository.findAll().size();
        // set the field null
        datasetRoleDetails.setRole(null);

        // Create the DatasetRoleDetails, which fails.
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRoleDetailsRepository.findAll().size();
        // set the field null
        datasetRoleDetails.setEmail(null);

        // Create the DatasetRoleDetails, which fails.
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMudidIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRoleDetailsRepository.findAll().size();
        // set the field null
        datasetRoleDetails.setMudid(null);

        // Create the DatasetRoleDetails, which fails.
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRoleDetailsRepository.findAll().size();
        // set the field null
        datasetRoleDetails.setName(null);

        // Create the DatasetRoleDetails, which fails.
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                post("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasetRoleDetails() throws Exception {
        // Initialize the database
        datasetRoleDetailsRepository.saveAndFlush(datasetRoleDetails);

        // Get all the datasetRoleDetailsList
        restDatasetRoleDetailsMockMvc
            .perform(get("/api/dataset-role-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datasetRoleDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].datasetRoleDetailsId").value(hasItem(DEFAULT_DATASET_ROLE_DETAILS_ID.intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mudid").value(hasItem(DEFAULT_MUDID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDatasetRoleDetails() throws Exception {
        // Initialize the database
        datasetRoleDetailsRepository.saveAndFlush(datasetRoleDetails);

        // Get the datasetRoleDetails
        restDatasetRoleDetailsMockMvc
            .perform(get("/api/dataset-role-details/{id}", datasetRoleDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datasetRoleDetails.getId().intValue()))
            .andExpect(jsonPath("$.datasetRoleDetailsId").value(DEFAULT_DATASET_ROLE_DETAILS_ID.intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mudid").value(DEFAULT_MUDID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasetRoleDetails() throws Exception {
        // Get the datasetRoleDetails
        restDatasetRoleDetailsMockMvc.perform(get("/api/dataset-role-details/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasetRoleDetails() throws Exception {
        // Initialize the database
        datasetRoleDetailsRepository.saveAndFlush(datasetRoleDetails);

        int databaseSizeBeforeUpdate = datasetRoleDetailsRepository.findAll().size();

        // Update the datasetRoleDetails
        DatasetRoleDetails updatedDatasetRoleDetails = datasetRoleDetailsRepository.findById(datasetRoleDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDatasetRoleDetails are not directly saved in db
        em.detach(updatedDatasetRoleDetails);
        updatedDatasetRoleDetails
            .datasetRoleDetailsId(UPDATED_DATASET_ROLE_DETAILS_ID)
            .role(UPDATED_ROLE)
            .email(UPDATED_EMAIL)
            .mudid(UPDATED_MUDID)
            .name(UPDATED_NAME);
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(updatedDatasetRoleDetails);

        restDatasetRoleDetailsMockMvc
            .perform(
                put("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatasetRoleDetails in the database
        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeUpdate);
        DatasetRoleDetails testDatasetRoleDetails = datasetRoleDetailsList.get(datasetRoleDetailsList.size() - 1);
        assertThat(testDatasetRoleDetails.getDatasetRoleDetailsId()).isEqualTo(UPDATED_DATASET_ROLE_DETAILS_ID);
        assertThat(testDatasetRoleDetails.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testDatasetRoleDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDatasetRoleDetails.getMudid()).isEqualTo(UPDATED_MUDID);
        assertThat(testDatasetRoleDetails.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDatasetRoleDetails() throws Exception {
        int databaseSizeBeforeUpdate = datasetRoleDetailsRepository.findAll().size();

        // Create the DatasetRoleDetails
        DatasetRoleDetailsDTO datasetRoleDetailsDTO = datasetRoleDetailsMapper.toDto(datasetRoleDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetRoleDetailsMockMvc
            .perform(
                put("/api/dataset-role-details")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(datasetRoleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatasetRoleDetails in the database
        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatasetRoleDetails() throws Exception {
        // Initialize the database
        datasetRoleDetailsRepository.saveAndFlush(datasetRoleDetails);

        int databaseSizeBeforeDelete = datasetRoleDetailsRepository.findAll().size();

        // Delete the datasetRoleDetails
        restDatasetRoleDetailsMockMvc
            .perform(delete("/api/dataset-role-details/{id}", datasetRoleDetails.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatasetRoleDetails> datasetRoleDetailsList = datasetRoleDetailsRepository.findAll();
        assertThat(datasetRoleDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetRoleDetails.class);
        DatasetRoleDetails datasetRoleDetails1 = new DatasetRoleDetails();
        datasetRoleDetails1.setId(1L);
        DatasetRoleDetails datasetRoleDetails2 = new DatasetRoleDetails();
        datasetRoleDetails2.setId(datasetRoleDetails1.getId());
        assertThat(datasetRoleDetails1).isEqualTo(datasetRoleDetails2);
        datasetRoleDetails2.setId(2L);
        assertThat(datasetRoleDetails1).isNotEqualTo(datasetRoleDetails2);
        datasetRoleDetails1.setId(null);
        assertThat(datasetRoleDetails1).isNotEqualTo(datasetRoleDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetRoleDetailsDTO.class);
        DatasetRoleDetailsDTO datasetRoleDetailsDTO1 = new DatasetRoleDetailsDTO();
        datasetRoleDetailsDTO1.setId(1L);
        DatasetRoleDetailsDTO datasetRoleDetailsDTO2 = new DatasetRoleDetailsDTO();
        assertThat(datasetRoleDetailsDTO1).isNotEqualTo(datasetRoleDetailsDTO2);
        datasetRoleDetailsDTO2.setId(datasetRoleDetailsDTO1.getId());
        assertThat(datasetRoleDetailsDTO1).isEqualTo(datasetRoleDetailsDTO2);
        datasetRoleDetailsDTO2.setId(2L);
        assertThat(datasetRoleDetailsDTO1).isNotEqualTo(datasetRoleDetailsDTO2);
        datasetRoleDetailsDTO1.setId(null);
        assertThat(datasetRoleDetailsDTO1).isNotEqualTo(datasetRoleDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetRoleDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetRoleDetailsMapper.fromId(null)).isNull();
    }
}
