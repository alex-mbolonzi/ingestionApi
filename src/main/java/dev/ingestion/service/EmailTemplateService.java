package dev.ingestion.service;

import dev.ingestion.domain.EmailTemplate;
import dev.ingestion.repository.EmailTemplateRepository;
import dev.ingestion.service.dto.EmailTemplateDTO;
import dev.ingestion.service.mapper.EmailTemplateMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmailTemplate}.
 */
@Service
@Transactional
public class EmailTemplateService {

    private final Logger log = LoggerFactory.getLogger(EmailTemplateService.class);

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailTemplateMapper emailTemplateMapper;

    public EmailTemplateService(EmailTemplateRepository emailTemplateRepository, EmailTemplateMapper emailTemplateMapper) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailTemplateMapper = emailTemplateMapper;
    }

    /**
     * Save a emailTemplate.
     *
     * @param emailTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTemplateDTO save(EmailTemplateDTO emailTemplateDTO) {
        log.debug("Request to save EmailTemplate : {}", emailTemplateDTO);
        EmailTemplate emailTemplate = emailTemplateMapper.toEntity(emailTemplateDTO);
        emailTemplate = emailTemplateRepository.save(emailTemplate);
        return emailTemplateMapper.toDto(emailTemplate);
    }

    /**
     * Get all the emailTemplates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateDTO> findAll() {
        log.debug("Request to get all EmailTemplates");
        return emailTemplateRepository.findAll().stream().map(emailTemplateMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emailTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailTemplateDTO> findOne(Long id) {
        log.debug("Request to get EmailTemplate : {}", id);
        return emailTemplateRepository.findById(id).map(emailTemplateMapper::toDto);
    }

    /**
     * Delete the emailTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailTemplate : {}", id);
        emailTemplateRepository.deleteById(id);
    }
}
