package dev.ingestion.service;

import dev.ingestion.domain.DatasetDetails;
import dev.ingestion.repository.DatasetDetailsRepository;
import dev.ingestion.service.dto.DatasetDetailsDTO;
import dev.ingestion.service.mapper.DatasetDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetDetails}.
 */
@Service
@Transactional
public class DatasetDetailsService {

    private final Logger log = LoggerFactory.getLogger(DatasetDetailsService.class);

    private final DatasetDetailsRepository datasetDetailsRepository;

    private final DatasetDetailsMapper datasetDetailsMapper;

    public DatasetDetailsService(DatasetDetailsRepository datasetDetailsRepository, DatasetDetailsMapper datasetDetailsMapper) {
        this.datasetDetailsRepository = datasetDetailsRepository;
        this.datasetDetailsMapper = datasetDetailsMapper;
    }

    /**
     * Save a datasetDetails.
     *
     * @param datasetDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetDetailsDTO save(DatasetDetailsDTO datasetDetailsDTO) {
        log.debug("Request to save DatasetDetails : {}", datasetDetailsDTO);
        DatasetDetails datasetDetails = datasetDetailsMapper.toEntity(datasetDetailsDTO);
        datasetDetails = datasetDetailsRepository.save(datasetDetails);
        return datasetDetailsMapper.toDto(datasetDetails);
    }

    /**
     * Get all the datasetDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetDetailsDTO> findAll() {
        log.debug("Request to get all DatasetDetails");
        return datasetDetailsRepository
            .findAll()
            .stream()
            .map(datasetDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetDetailsDTO> findOne(Long id) {
        log.debug("Request to get DatasetDetails : {}", id);
        return datasetDetailsRepository.findById(id).map(datasetDetailsMapper::toDto);
    }

    /**
     * Delete the datasetDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetDetails : {}", id);
        datasetDetailsRepository.deleteById(id);
    }
}
