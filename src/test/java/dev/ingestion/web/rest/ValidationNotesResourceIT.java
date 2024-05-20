package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.domain.ValidationNotes;
import dev.ingestion.repository.ValidationNotesRepository;
import dev.ingestion.service.ValidationNotesService;
import dev.ingestion.service.dto.ValidationNotesDTO;
import dev.ingestion.service.mapper.ValidationNotesMapper;
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
 * Integration tests for the {@link ValidationNotesResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class ValidationNotesResourceIT {

    private static final Long DEFAULT_NOTES_ID = 1L;
    private static final Long UPDATED_NOTES_ID = 2L;
    private static final Long SMALLER_NOTES_ID = 1L - 1L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

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
    private ValidationNotesRepository validationNotesRepository;

    @Autowired
    private ValidationNotesMapper validationNotesMapper;

    @Autowired
    private ValidationNotesService validationNotesService;

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

    private MockMvc restValidationNotesMockMvc;

    private ValidationNotes validationNotes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValidationNotesResource validationNotesResource = new ValidationNotesResource(validationNotesService);
        this.restValidationNotesMockMvc = MockMvcBuilders.standaloneSetup(validationNotesResource)
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
    public static ValidationNotes createEntity(EntityManager em) {
        ValidationNotes validationNotes = new ValidationNotes()
            .notesId(DEFAULT_NOTES_ID)
            .notes(DEFAULT_NOTES)
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
        validationNotes.setIngestionRequestId(ingestionRequestDetails);
        return validationNotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidationNotes createUpdatedEntity(EntityManager em) {
        ValidationNotes validationNotes = new ValidationNotes()
            .notesId(UPDATED_NOTES_ID)
            .notes(UPDATED_NOTES)
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
        validationNotes.setIngestionRequestId(ingestionRequestDetails);
        return validationNotes;
    }

    @BeforeEach
    public void initTest() {
        validationNotes = createEntity(em);
    }

    @Test
    @Transactional
    public void createValidationNotes() throws Exception {
        int databaseSizeBeforeCreate = validationNotesRepository.findAll().size();

        // Create the ValidationNotes
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);
        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ValidationNotes in the database
        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeCreate + 1);
        ValidationNotes testValidationNotes = validationNotesList.get(validationNotesList.size() - 1);
        assertThat(testValidationNotes.getNotesId()).isEqualTo(DEFAULT_NOTES_ID);
        assertThat(testValidationNotes.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testValidationNotes.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testValidationNotes.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testValidationNotes.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testValidationNotes.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createValidationNotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationNotesRepository.findAll().size();

        // Create the ValidationNotes with an existing ID
        validationNotes.setId(1L);
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationNotes in the database
        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNotesIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationNotesRepository.findAll().size();
        // set the field null
        validationNotes.setNotesId(null);

        // Create the ValidationNotes, which fails.
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationNotesRepository.findAll().size();
        // set the field null
        validationNotes.setNotes(null);

        // Create the ValidationNotes, which fails.
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationNotesRepository.findAll().size();
        // set the field null
        validationNotes.setCreatedBy(null);

        // Create the ValidationNotes, which fails.
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationNotesRepository.findAll().size();
        // set the field null
        validationNotes.setCreatedDate(null);

        // Create the ValidationNotes, which fails.
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        restValidationNotesMockMvc
            .perform(
                post("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValidationNotes() throws Exception {
        // Initialize the database
        validationNotesRepository.saveAndFlush(validationNotes);

        // Get all the validationNotesList
        restValidationNotesMockMvc
            .perform(get("/api/validation-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationNotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].notesId").value(hasItem(DEFAULT_NOTES_ID.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getValidationNotes() throws Exception {
        // Initialize the database
        validationNotesRepository.saveAndFlush(validationNotes);

        // Get the validationNotes
        restValidationNotesMockMvc
            .perform(get("/api/validation-notes/{id}", validationNotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(validationNotes.getId().intValue()))
            .andExpect(jsonPath("$.notesId").value(DEFAULT_NOTES_ID.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValidationNotes() throws Exception {
        // Get the validationNotes
        restValidationNotesMockMvc.perform(get("/api/validation-notes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValidationNotes() throws Exception {
        // Initialize the database
        validationNotesRepository.saveAndFlush(validationNotes);

        int databaseSizeBeforeUpdate = validationNotesRepository.findAll().size();

        // Update the validationNotes
        ValidationNotes updatedValidationNotes = validationNotesRepository.findById(validationNotes.getId()).get();
        // Disconnect from session so that the updates on updatedValidationNotes are not directly saved in db
        em.detach(updatedValidationNotes);
        updatedValidationNotes
            .notesId(UPDATED_NOTES_ID)
            .notes(UPDATED_NOTES)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(updatedValidationNotes);

        restValidationNotesMockMvc
            .perform(
                put("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ValidationNotes in the database
        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeUpdate);
        ValidationNotes testValidationNotes = validationNotesList.get(validationNotesList.size() - 1);
        assertThat(testValidationNotes.getNotesId()).isEqualTo(UPDATED_NOTES_ID);
        assertThat(testValidationNotes.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testValidationNotes.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testValidationNotes.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testValidationNotes.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testValidationNotes.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingValidationNotes() throws Exception {
        int databaseSizeBeforeUpdate = validationNotesRepository.findAll().size();

        // Create the ValidationNotes
        ValidationNotesDTO validationNotesDTO = validationNotesMapper.toDto(validationNotes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationNotesMockMvc
            .perform(
                put("/api/validation-notes")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(validationNotesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationNotes in the database
        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteValidationNotes() throws Exception {
        // Initialize the database
        validationNotesRepository.saveAndFlush(validationNotes);

        int databaseSizeBeforeDelete = validationNotesRepository.findAll().size();

        // Delete the validationNotes
        restValidationNotesMockMvc
            .perform(delete("/api/validation-notes/{id}", validationNotes.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ValidationNotes> validationNotesList = validationNotesRepository.findAll();
        assertThat(validationNotesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationNotes.class);
        ValidationNotes validationNotes1 = new ValidationNotes();
        validationNotes1.setId(1L);
        ValidationNotes validationNotes2 = new ValidationNotes();
        validationNotes2.setId(validationNotes1.getId());
        assertThat(validationNotes1).isEqualTo(validationNotes2);
        validationNotes2.setId(2L);
        assertThat(validationNotes1).isNotEqualTo(validationNotes2);
        validationNotes1.setId(null);
        assertThat(validationNotes1).isNotEqualTo(validationNotes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationNotesDTO.class);
        ValidationNotesDTO validationNotesDTO1 = new ValidationNotesDTO();
        validationNotesDTO1.setId(1L);
        ValidationNotesDTO validationNotesDTO2 = new ValidationNotesDTO();
        assertThat(validationNotesDTO1).isNotEqualTo(validationNotesDTO2);
        validationNotesDTO2.setId(validationNotesDTO1.getId());
        assertThat(validationNotesDTO1).isEqualTo(validationNotesDTO2);
        validationNotesDTO2.setId(2L);
        assertThat(validationNotesDTO1).isNotEqualTo(validationNotesDTO2);
        validationNotesDTO1.setId(null);
        assertThat(validationNotesDTO1).isNotEqualTo(validationNotesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(validationNotesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(validationNotesMapper.fromId(null)).isNull();
    }
}
