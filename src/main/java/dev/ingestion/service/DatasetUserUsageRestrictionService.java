package dev.ingestion.service;

import dev.ingestion.domain.DatasetUserUsageRestriction;
import dev.ingestion.repository.DatasetUserUsageRestrictionRepository;
import dev.ingestion.service.dto.DatasetUserUsageRestrictionDTO;
import dev.ingestion.service.mapper.DatasetUserUsageRestrictionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetUserUsageRestriction}.
 */
@Service
@Transactional
public class DatasetUserUsageRestrictionService {

    private final Logger log = LoggerFactory.getLogger(DatasetUserUsageRestrictionService.class);

    private final DatasetUserUsageRestrictionRepository datasetUserUsageRestrictionRepository;

    private final DatasetUserUsageRestrictionMapper datasetUserUsageRestrictionMapper;

    public DatasetUserUsageRestrictionService(
        DatasetUserUsageRestrictionRepository datasetUserUsageRestrictionRepository,
        DatasetUserUsageRestrictionMapper datasetUserUsageRestrictionMapper
    ) {
        this.datasetUserUsageRestrictionRepository = datasetUserUsageRestrictionRepository;
        this.datasetUserUsageRestrictionMapper = datasetUserUsageRestrictionMapper;
    }

    /**
     * Save a datasetUserUsageRestriction.
     *
     * @param datasetUserUsageRestrictionDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetUserUsageRestrictionDTO save(DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO) {
        log.debug("Request to save DatasetUserUsageRestriction : {}", datasetUserUsageRestrictionDTO);
        DatasetUserUsageRestriction datasetUserUsageRestriction = datasetUserUsageRestrictionMapper.toEntity(
            datasetUserUsageRestrictionDTO
        );
        datasetUserUsageRestriction = datasetUserUsageRestrictionRepository.save(datasetUserUsageRestriction);
        return datasetUserUsageRestrictionMapper.toDto(datasetUserUsageRestriction);
    }

    /**
     * Get all the datasetUserUsageRestrictions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetUserUsageRestrictionDTO> findAll() {
        log.debug("Request to get all DatasetUserUsageRestrictions");
        return datasetUserUsageRestrictionRepository
            .findAll()
            .stream()
            .map(datasetUserUsageRestrictionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetUserUsageRestriction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetUserUsageRestrictionDTO> findOne(Long id) {
        log.debug("Request to get DatasetUserUsageRestriction : {}", id);
        return datasetUserUsageRestrictionRepository.findById(id).map(datasetUserUsageRestrictionMapper::toDto);
    }

    /**
     * Delete the datasetUserUsageRestriction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetUserUsageRestriction : {}", id);
        datasetUserUsageRestrictionRepository.deleteById(id);
    }
}
