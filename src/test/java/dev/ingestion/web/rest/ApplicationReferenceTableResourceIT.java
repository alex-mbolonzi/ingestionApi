package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.ApplicationReferenceTable;
import dev.ingestion.repository.ApplicationReferenceTableRepository;
import dev.ingestion.service.ApplicationReferenceTableService;
import dev.ingestion.service.dto.ApplicationReferenceTableDTO;
import dev.ingestion.service.mapper.ApplicationReferenceTableMapper;
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
 * Integration tests for the {@link ApplicationReferenceTableResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class ApplicationReferenceTableResourceIT {

    private static final Long DEFAULT_REFERENCE_ID = 1L;
    private static final Long UPDATED_REFERENCE_ID = 2L;
    private static final Long SMALLER_REFERENCE_ID = 1L - 1L;

    private static final String DEFAULT_REFERENCE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_DATA_TYPE = "BBBBBBBBBB";

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

    private static final Long DEFAULT_REFERENCE_ORDER = 1L;
    private static final Long UPDATED_REFERENCE_ORDER = 2L;
    private static final Long SMALLER_REFERENCE_ORDER = 1L - 1L;

    private static final String DEFAULT_REFERENCE_GROUP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_GROUP_TYPE = "BBBBBBBBBB";

    @Autowired
    private ApplicationReferenceTableRepository applicationReferenceTableRepository;

    @Autowired
    private ApplicationReferenceTableMapper applicationReferenceTableMapper;

    @Autowired
    private ApplicationReferenceTableService applicationReferenceTableService;

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

    private MockMvc restApplicationReferenceTableMockMvc;

    private ApplicationReferenceTable applicationReferenceTable;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationReferenceTableResource applicationReferenceTableResource = new ApplicationReferenceTableResource(
            applicationReferenceTableService
        );
        this.restApplicationReferenceTableMockMvc = MockMvcBuilders.standaloneSetup(applicationReferenceTableResource)
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
    public static ApplicationReferenceTable createEntity(EntityManager em) {
        ApplicationReferenceTable applicationReferenceTable = new ApplicationReferenceTable()
            .referenceId(DEFAULT_REFERENCE_ID)
            .referenceData(DEFAULT_REFERENCE_DATA)
            .referenceDataType(DEFAULT_REFERENCE_DATA_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .referenceOrder(DEFAULT_REFERENCE_ORDER)
            .referenceGroupType(DEFAULT_REFERENCE_GROUP_TYPE);
        return applicationReferenceTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationReferenceTable createUpdatedEntity(EntityManager em) {
        ApplicationReferenceTable applicationReferenceTable = new ApplicationReferenceTable()
            .referenceId(UPDATED_REFERENCE_ID)
            .referenceData(UPDATED_REFERENCE_DATA)
            .referenceDataType(UPDATED_REFERENCE_DATA_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .referenceOrder(UPDATED_REFERENCE_ORDER)
            .referenceGroupType(UPDATED_REFERENCE_GROUP_TYPE);
        return applicationReferenceTable;
    }

    @BeforeEach
    public void initTest() {
        applicationReferenceTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationReferenceTable() throws Exception {
        int databaseSizeBeforeCreate = applicationReferenceTableRepository.findAll().size();

        // Create the ApplicationReferenceTable
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);
        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApplicationReferenceTable in the database
        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationReferenceTable testApplicationReferenceTable = applicationReferenceTableList.get(
            applicationReferenceTableList.size() - 1
        );
        assertThat(testApplicationReferenceTable.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testApplicationReferenceTable.getReferenceData()).isEqualTo(DEFAULT_REFERENCE_DATA);
        assertThat(testApplicationReferenceTable.getReferenceDataType()).isEqualTo(DEFAULT_REFERENCE_DATA_TYPE);
        assertThat(testApplicationReferenceTable.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationReferenceTable.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplicationReferenceTable.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApplicationReferenceTable.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testApplicationReferenceTable.getReferenceOrder()).isEqualTo(DEFAULT_REFERENCE_ORDER);
        assertThat(testApplicationReferenceTable.getReferenceGroupType()).isEqualTo(DEFAULT_REFERENCE_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void createApplicationReferenceTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationReferenceTableRepository.findAll().size();

        // Create the ApplicationReferenceTable with an existing ID
        applicationReferenceTable.setId(1L);
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationReferenceTable in the database
        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReferenceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setReferenceId(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setReferenceData(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setReferenceDataType(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setCreatedBy(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setCreatedDate(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationReferenceTableRepository.findAll().size();
        // set the field null
        applicationReferenceTable.setReferenceOrder(null);

        // Create the ApplicationReferenceTable, which fails.
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                post("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationReferenceTables() throws Exception {
        // Initialize the database
        applicationReferenceTableRepository.saveAndFlush(applicationReferenceTable);

        // Get all the applicationReferenceTableList
        restApplicationReferenceTableMockMvc
            .perform(get("/api/application-reference-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationReferenceTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].referenceData").value(hasItem(DEFAULT_REFERENCE_DATA.toString())))
            .andExpect(jsonPath("$.[*].referenceDataType").value(hasItem(DEFAULT_REFERENCE_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].referenceOrder").value(hasItem(DEFAULT_REFERENCE_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].referenceGroupType").value(hasItem(DEFAULT_REFERENCE_GROUP_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getApplicationReferenceTable() throws Exception {
        // Initialize the database
        applicationReferenceTableRepository.saveAndFlush(applicationReferenceTable);

        // Get the applicationReferenceTable
        restApplicationReferenceTableMockMvc
            .perform(get("/api/application-reference-tables/{id}", applicationReferenceTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationReferenceTable.getId().intValue()))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID.intValue()))
            .andExpect(jsonPath("$.referenceData").value(DEFAULT_REFERENCE_DATA.toString()))
            .andExpect(jsonPath("$.referenceDataType").value(DEFAULT_REFERENCE_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.referenceOrder").value(DEFAULT_REFERENCE_ORDER.intValue()))
            .andExpect(jsonPath("$.referenceGroupType").value(DEFAULT_REFERENCE_GROUP_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationReferenceTable() throws Exception {
        // Get the applicationReferenceTable
        restApplicationReferenceTableMockMvc
            .perform(get("/api/application-reference-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationReferenceTable() throws Exception {
        // Initialize the database
        applicationReferenceTableRepository.saveAndFlush(applicationReferenceTable);

        int databaseSizeBeforeUpdate = applicationReferenceTableRepository.findAll().size();

        // Update the applicationReferenceTable
        ApplicationReferenceTable updatedApplicationReferenceTable = applicationReferenceTableRepository
            .findById(applicationReferenceTable.getId())
            .get();
        // Disconnect from session so that the updates on updatedApplicationReferenceTable are not directly saved in db
        em.detach(updatedApplicationReferenceTable);
        updatedApplicationReferenceTable
            .referenceId(UPDATED_REFERENCE_ID)
            .referenceData(UPDATED_REFERENCE_DATA)
            .referenceDataType(UPDATED_REFERENCE_DATA_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .referenceOrder(UPDATED_REFERENCE_ORDER)
            .referenceGroupType(UPDATED_REFERENCE_GROUP_TYPE);
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(updatedApplicationReferenceTable);

        restApplicationReferenceTableMockMvc
            .perform(
                put("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationReferenceTable in the database
        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeUpdate);
        ApplicationReferenceTable testApplicationReferenceTable = applicationReferenceTableList.get(
            applicationReferenceTableList.size() - 1
        );
        assertThat(testApplicationReferenceTable.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testApplicationReferenceTable.getReferenceData()).isEqualTo(UPDATED_REFERENCE_DATA);
        assertThat(testApplicationReferenceTable.getReferenceDataType()).isEqualTo(UPDATED_REFERENCE_DATA_TYPE);
        assertThat(testApplicationReferenceTable.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationReferenceTable.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplicationReferenceTable.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApplicationReferenceTable.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testApplicationReferenceTable.getReferenceOrder()).isEqualTo(UPDATED_REFERENCE_ORDER);
        assertThat(testApplicationReferenceTable.getReferenceGroupType()).isEqualTo(UPDATED_REFERENCE_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationReferenceTable() throws Exception {
        int databaseSizeBeforeUpdate = applicationReferenceTableRepository.findAll().size();

        // Create the ApplicationReferenceTable
        ApplicationReferenceTableDTO applicationReferenceTableDTO = applicationReferenceTableMapper.toDto(applicationReferenceTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationReferenceTableMockMvc
            .perform(
                put("/api/application-reference-tables")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(applicationReferenceTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationReferenceTable in the database
        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationReferenceTable() throws Exception {
        // Initialize the database
        applicationReferenceTableRepository.saveAndFlush(applicationReferenceTable);

        int databaseSizeBeforeDelete = applicationReferenceTableRepository.findAll().size();

        // Delete the applicationReferenceTable
        restApplicationReferenceTableMockMvc
            .perform(
                delete("/api/application-reference-tables/{id}", applicationReferenceTable.getId()).accept(TestUtil.APPLICATION_JSON_UTF8)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationReferenceTable> applicationReferenceTableList = applicationReferenceTableRepository.findAll();
        assertThat(applicationReferenceTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationReferenceTable.class);
        ApplicationReferenceTable applicationReferenceTable1 = new ApplicationReferenceTable();
        applicationReferenceTable1.setId(1L);
        ApplicationReferenceTable applicationReferenceTable2 = new ApplicationReferenceTable();
        applicationReferenceTable2.setId(applicationReferenceTable1.getId());
        assertThat(applicationReferenceTable1).isEqualTo(applicationReferenceTable2);
        applicationReferenceTable2.setId(2L);
        assertThat(applicationReferenceTable1).isNotEqualTo(applicationReferenceTable2);
        applicationReferenceTable1.setId(null);
        assertThat(applicationReferenceTable1).isNotEqualTo(applicationReferenceTable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationReferenceTableDTO.class);
        ApplicationReferenceTableDTO applicationReferenceTableDTO1 = new ApplicationReferenceTableDTO();
        applicationReferenceTableDTO1.setId(1L);
        ApplicationReferenceTableDTO applicationReferenceTableDTO2 = new ApplicationReferenceTableDTO();
        assertThat(applicationReferenceTableDTO1).isNotEqualTo(applicationReferenceTableDTO2);
        applicationReferenceTableDTO2.setId(applicationReferenceTableDTO1.getId());
        assertThat(applicationReferenceTableDTO1).isEqualTo(applicationReferenceTableDTO2);
        applicationReferenceTableDTO2.setId(2L);
        assertThat(applicationReferenceTableDTO1).isNotEqualTo(applicationReferenceTableDTO2);
        applicationReferenceTableDTO1.setId(null);
        assertThat(applicationReferenceTableDTO1).isNotEqualTo(applicationReferenceTableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationReferenceTableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationReferenceTableMapper.fromId(null)).isNull();
    }
}
