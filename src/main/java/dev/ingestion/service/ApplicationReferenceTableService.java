package dev.ingestion.service;

import dev.ingestion.domain.ApplicationReferenceTable;
import dev.ingestion.repository.ApplicationReferenceTableRepository;
import dev.ingestion.service.dto.ApplicationReferenceTableDTO;
import dev.ingestion.service.mapper.ApplicationReferenceTableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationReferenceTable}.
 */
@Service
@Transactional
public class ApplicationReferenceTableService {

    private final Logger log = LoggerFactory.getLogger(ApplicationReferenceTableService.class);

    private final ApplicationReferenceTableRepository applicationReferenceTableRepository;

    private final ApplicationReferenceTableMapper applicationReferenceTableMapper;

    public ApplicationReferenceTableService(
        ApplicationReferenceTableRepository applicationReferenceTableRepository,
        ApplicationReferenceTableMapper applicationReferenceTableMapper
    ) {
        this.applicationReferenceTableRepository = applicationReferenceTableRepository;
        this.applicationReferenceTableMapper = applicationReferenceTableMapper;
    }

    /**
     * Save a applicationReferenceTable.
     *
     * @param applicationReferenceTableDTO the entity to save.
     * @return the persisted entity.
     */
    public ApplicationReferenceTableDTO save(ApplicationReferenceTableDTO applicationReferenceTableDTO) {
        log.debug("Request to save ApplicationReferenceTable : {}", applicationReferenceTableDTO);
        ApplicationReferenceTable applicationReferenceTable = applicationReferenceTableMapper.toEntity(applicationReferenceTableDTO);
        applicationReferenceTable = applicationReferenceTableRepository.save(applicationReferenceTable);
        return applicationReferenceTableMapper.toDto(applicationReferenceTable);
    }

    /**
     * Get all the applicationReferenceTables.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationReferenceTableDTO> findAll() {
        log.debug("Request to get all ApplicationReferenceTables");
        return applicationReferenceTableRepository
            .findAll()
            .stream()
            .map(applicationReferenceTableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one applicationReferenceTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationReferenceTableDTO> findOne(Long id) {
        log.debug("Request to get ApplicationReferenceTable : {}", id);
        return applicationReferenceTableRepository.findById(id).map(applicationReferenceTableMapper::toDto);
    }

    /**
     * Delete the applicationReferenceTable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationReferenceTable : {}", id);
        applicationReferenceTableRepository.deleteById(id);
    }
}
