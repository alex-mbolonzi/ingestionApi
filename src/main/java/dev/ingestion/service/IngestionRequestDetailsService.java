package dev.ingestion.service;

import dev.ingestion.domain.IngestionRequestDetails;
import dev.ingestion.repository.IngestionRequestDetailsRepository;
import dev.ingestion.service.dto.IngestionRequestDetailsDTO;
import dev.ingestion.service.mapper.IngestionRequestDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IngestionRequestDetails}.
 */
@Service
@Transactional
public class IngestionRequestDetailsService {

    private final Logger log = LoggerFactory.getLogger(IngestionRequestDetailsService.class);

    private final IngestionRequestDetailsRepository ingestionRequestDetailsRepository;

    private final IngestionRequestDetailsMapper ingestionRequestDetailsMapper;

    public IngestionRequestDetailsService(
        IngestionRequestDetailsRepository ingestionRequestDetailsRepository,
        IngestionRequestDetailsMapper ingestionRequestDetailsMapper
    ) {
        this.ingestionRequestDetailsRepository = ingestionRequestDetailsRepository;
        this.ingestionRequestDetailsMapper = ingestionRequestDetailsMapper;
    }

    /**
     * Save a ingestionRequestDetails.
     *
     * @param ingestionRequestDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public IngestionRequestDetailsDTO save(IngestionRequestDetailsDTO ingestionRequestDetailsDTO) {
        log.debug("Request to save IngestionRequestDetails : {}", ingestionRequestDetailsDTO);
        IngestionRequestDetails ingestionRequestDetails = ingestionRequestDetailsMapper.toEntity(ingestionRequestDetailsDTO);
        ingestionRequestDetails = ingestionRequestDetailsRepository.save(ingestionRequestDetails);
        return ingestionRequestDetailsMapper.toDto(ingestionRequestDetails);
    }

    /**
     * Get all the ingestionRequestDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngestionRequestDetailsDTO> findAll() {
        log.debug("Request to get all IngestionRequestDetails");
        return ingestionRequestDetailsRepository
            .findAll()
            .stream()
            .map(ingestionRequestDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ingestionRequestDetails where DatasetDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngestionRequestDetailsDTO> findAllWhereDatasetDetailsIsNull() {
        log.debug("Request to get all ingestionRequestDetails where DatasetDetails is null");
        return StreamSupport.stream(ingestionRequestDetailsRepository.findAll().spliterator(), false)
            .filter(ingestionRequestDetails -> ingestionRequestDetails.getDatasetDetails() == null)
            .map(ingestionRequestDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the ingestionRequestDetails where TechnicalDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngestionRequestDetailsDTO> findAllWhereTechnicalDetailsIsNull() {
        log.debug("Request to get all ingestionRequestDetails where TechnicalDetails is null");
        return StreamSupport.stream(ingestionRequestDetailsRepository.findAll().spliterator(), false)
            .filter(ingestionRequestDetails -> ingestionRequestDetails.getTechnicalDetails() == null)
            .map(ingestionRequestDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ingestionRequestDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IngestionRequestDetailsDTO> findOne(Long id) {
        log.debug("Request to get IngestionRequestDetails : {}", id);
        return ingestionRequestDetailsRepository.findById(id).map(ingestionRequestDetailsMapper::toDto);
    }

    /**
     * Delete the ingestionRequestDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IngestionRequestDetails : {}", id);
        ingestionRequestDetailsRepository.deleteById(id);
    }
}
