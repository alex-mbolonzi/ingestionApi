package dev.ingestion.service;

import dev.ingestion.domain.DatasetRoleDetails;
import dev.ingestion.repository.DatasetRoleDetailsRepository;
import dev.ingestion.service.dto.DatasetRoleDetailsDTO;
import dev.ingestion.service.mapper.DatasetRoleDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetRoleDetails}.
 */
@Service
@Transactional
public class DatasetRoleDetailsService {

    private final Logger log = LoggerFactory.getLogger(DatasetRoleDetailsService.class);

    private final DatasetRoleDetailsRepository datasetRoleDetailsRepository;

    private final DatasetRoleDetailsMapper datasetRoleDetailsMapper;

    public DatasetRoleDetailsService(
        DatasetRoleDetailsRepository datasetRoleDetailsRepository,
        DatasetRoleDetailsMapper datasetRoleDetailsMapper
    ) {
        this.datasetRoleDetailsRepository = datasetRoleDetailsRepository;
        this.datasetRoleDetailsMapper = datasetRoleDetailsMapper;
    }

    /**
     * Save a datasetRoleDetails.
     *
     * @param datasetRoleDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetRoleDetailsDTO save(DatasetRoleDetailsDTO datasetRoleDetailsDTO) {
        log.debug("Request to save DatasetRoleDetails : {}", datasetRoleDetailsDTO);
        DatasetRoleDetails datasetRoleDetails = datasetRoleDetailsMapper.toEntity(datasetRoleDetailsDTO);
        datasetRoleDetails = datasetRoleDetailsRepository.save(datasetRoleDetails);
        return datasetRoleDetailsMapper.toDto(datasetRoleDetails);
    }

    /**
     * Get all the datasetRoleDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetRoleDetailsDTO> findAll() {
        log.debug("Request to get all DatasetRoleDetails");
        return datasetRoleDetailsRepository
            .findAll()
            .stream()
            .map(datasetRoleDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetRoleDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetRoleDetailsDTO> findOne(Long id) {
        log.debug("Request to get DatasetRoleDetails : {}", id);
        return datasetRoleDetailsRepository.findById(id).map(datasetRoleDetailsMapper::toDto);
    }

    /**
     * Delete the datasetRoleDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetRoleDetails : {}", id);
        datasetRoleDetailsRepository.deleteById(id);
    }
}
