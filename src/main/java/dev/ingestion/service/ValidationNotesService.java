package dev.ingestion.service;

import dev.ingestion.domain.ValidationNotes;
import dev.ingestion.repository.ValidationNotesRepository;
import dev.ingestion.service.dto.ValidationNotesDTO;
import dev.ingestion.service.mapper.ValidationNotesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ValidationNotes}.
 */
@Service
@Transactional
public class ValidationNotesService {

    private final Logger log = LoggerFactory.getLogger(ValidationNotesService.class);

    private final ValidationNotesRepository validationNotesRepository;

    private final ValidationNotesMapper validationNotesMapper;

    public ValidationNotesService(ValidationNotesRepository validationNotesRepository, ValidationNotesMapper validationNotesMapper) {
        this.validationNotesRepository = validationNotesRepository;
        this.validationNotesMapper = validationNotesMapper;
    }

    /**
     * Save a validationNotes.
     *
     * @param validationNotesDTO the entity to save.
     * @return the persisted entity.
     */
    public ValidationNotesDTO save(ValidationNotesDTO validationNotesDTO) {
        log.debug("Request to save ValidationNotes : {}", validationNotesDTO);
        ValidationNotes validationNotes = validationNotesMapper.toEntity(validationNotesDTO);
        validationNotes = validationNotesRepository.save(validationNotes);
        return validationNotesMapper.toDto(validationNotes);
    }

    /**
     * Get all the validationNotes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ValidationNotesDTO> findAll() {
        log.debug("Request to get all ValidationNotes");
        return validationNotesRepository
            .findAll()
            .stream()
            .map(validationNotesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one validationNotes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ValidationNotesDTO> findOne(Long id) {
        log.debug("Request to get ValidationNotes : {}", id);
        return validationNotesRepository.findById(id).map(validationNotesMapper::toDto);
    }

    /**
     * Delete the validationNotes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ValidationNotes : {}", id);
        validationNotesRepository.deleteById(id);
    }
}
