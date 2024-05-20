package dev.ingestion.web.rest;

import static dev.ingestion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.ingestion.IngestionApiApp;
import dev.ingestion.domain.EmailTemplate;
import dev.ingestion.repository.EmailTemplateRepository;
import dev.ingestion.service.EmailTemplateService;
import dev.ingestion.service.dto.EmailTemplateDTO;
import dev.ingestion.service.mapper.EmailTemplateMapper;
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
 * Integration tests for the {@link EmailTemplateResource} REST controller.
 */
@SpringBootTest(classes = IngestionApiApp.class)
public class EmailTemplateResourceIT {

    private static final Long DEFAULT_TEMPLATE_ID = 1L;
    private static final Long UPDATED_TEMPLATE_ID = 2L;
    private static final Long SMALLER_TEMPLATE_ID = 1L - 1L;

    private static final String DEFAULT_TEMPLATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailTemplateMapper emailTemplateMapper;

    @Autowired
    private EmailTemplateService emailTemplateService;

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

    private MockMvc restEmailTemplateMockMvc;

    private EmailTemplate emailTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailTemplateResource emailTemplateResource = new EmailTemplateResource(emailTemplateService);
        this.restEmailTemplateMockMvc = MockMvcBuilders.standaloneSetup(emailTemplateResource)
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
    public static EmailTemplate createEntity(EntityManager em) {
        EmailTemplate emailTemplate = new EmailTemplate()
            .templateId(DEFAULT_TEMPLATE_ID)
            .templateCode(DEFAULT_TEMPLATE_CODE)
            .subject(DEFAULT_SUBJECT)
            .body(DEFAULT_BODY);
        return emailTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTemplate createUpdatedEntity(EntityManager em) {
        EmailTemplate emailTemplate = new EmailTemplate()
            .templateId(UPDATED_TEMPLATE_ID)
            .templateCode(UPDATED_TEMPLATE_CODE)
            .subject(UPDATED_SUBJECT)
            .body(UPDATED_BODY);
        return emailTemplate;
    }

    @BeforeEach
    public void initTest() {
        emailTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTemplate() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);
        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testEmailTemplate.getTemplateCode()).isEqualTo(DEFAULT_TEMPLATE_CODE);
        assertThat(testEmailTemplate.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmailTemplate.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createEmailTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate with an existing ID
        emailTemplate.setId(1L);
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setTemplateId(null);

        // Create the EmailTemplate, which fails.
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setTemplateCode(null);

        // Create the EmailTemplate, which fails.
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setSubject(null);

        // Create the EmailTemplate, which fails.
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBodyIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setBody(null);

        // Create the EmailTemplate, which fails.
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        restEmailTemplateMockMvc
            .perform(
                post("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get all the emailTemplateList
        restEmailTemplateMockMvc
            .perform(get("/api/email-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateCode").value(hasItem(DEFAULT_TEMPLATE_CODE.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())));
    }

    @Test
    @Transactional
    public void getEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get the emailTemplate
        restEmailTemplateMockMvc
            .perform(get("/api/email-templates/{id}", emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplate.getId().intValue()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.intValue()))
            .andExpect(jsonPath("$.templateCode").value(DEFAULT_TEMPLATE_CODE.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailTemplate() throws Exception {
        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Update the emailTemplate
        EmailTemplate updatedEmailTemplate = emailTemplateRepository.findById(emailTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedEmailTemplate are not directly saved in db
        em.detach(updatedEmailTemplate);
        updatedEmailTemplate
            .templateId(UPDATED_TEMPLATE_ID)
            .templateCode(UPDATED_TEMPLATE_CODE)
            .subject(UPDATED_SUBJECT)
            .body(UPDATED_BODY);
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(updatedEmailTemplate);

        restEmailTemplateMockMvc
            .perform(
                put("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testEmailTemplate.getTemplateCode()).isEqualTo(UPDATED_TEMPLATE_CODE);
        assertThat(testEmailTemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailTemplate.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTemplateMockMvc
            .perform(
                put("/api/email-templates")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        int databaseSizeBeforeDelete = emailTemplateRepository.findAll().size();

        // Delete the emailTemplate
        restEmailTemplateMockMvc
            .perform(delete("/api/email-templates/{id}", emailTemplate.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplate.class);
        EmailTemplate emailTemplate1 = new EmailTemplate();
        emailTemplate1.setId(1L);
        EmailTemplate emailTemplate2 = new EmailTemplate();
        emailTemplate2.setId(emailTemplate1.getId());
        assertThat(emailTemplate1).isEqualTo(emailTemplate2);
        emailTemplate2.setId(2L);
        assertThat(emailTemplate1).isNotEqualTo(emailTemplate2);
        emailTemplate1.setId(null);
        assertThat(emailTemplate1).isNotEqualTo(emailTemplate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplateDTO.class);
        EmailTemplateDTO emailTemplateDTO1 = new EmailTemplateDTO();
        emailTemplateDTO1.setId(1L);
        EmailTemplateDTO emailTemplateDTO2 = new EmailTemplateDTO();
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
        emailTemplateDTO2.setId(emailTemplateDTO1.getId());
        assertThat(emailTemplateDTO1).isEqualTo(emailTemplateDTO2);
        emailTemplateDTO2.setId(2L);
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
        emailTemplateDTO1.setId(null);
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailTemplateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailTemplateMapper.fromId(null)).isNull();
    }
}
