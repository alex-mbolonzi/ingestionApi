package dev.ingestion.service;

import dev.ingestion.domain.DatasetTherapy;
import dev.ingestion.repository.DatasetTherapyRepository;
import dev.ingestion.service.dto.DatasetTherapyDTO;
import dev.ingestion.service.mapper.DatasetTherapyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetTherapy}.
 */
@Service
@Transactional
public class DatasetTherapyService {

    private final Logger log = LoggerFactory.getLogger(DatasetTherapyService.class);

    private final DatasetTherapyRepository datasetTherapyRepository;

    private final DatasetTherapyMapper datasetTherapyMapper;

    public DatasetTherapyService(DatasetTherapyRepository datasetTherapyRepository, DatasetTherapyMapper datasetTherapyMapper) {
        this.datasetTherapyRepository = datasetTherapyRepository;
        this.datasetTherapyMapper = datasetTherapyMapper;
    }

    /**
     * Save a datasetTherapy.
     *
     * @param datasetTherapyDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetTherapyDTO save(DatasetTherapyDTO datasetTherapyDTO) {
        log.debug("Request to save DatasetTherapy : {}", datasetTherapyDTO);
        DatasetTherapy datasetTherapy = datasetTherapyMapper.toEntity(datasetTherapyDTO);
        datasetTherapy = datasetTherapyRepository.save(datasetTherapy);
        return datasetTherapyMapper.toDto(datasetTherapy);
    }

    /**
     * Get all the datasetTherapies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetTherapyDTO> findAll() {
        log.debug("Request to get all DatasetTherapies");
        return datasetTherapyRepository
            .findAll()
            .stream()
            .map(datasetTherapyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetTherapy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetTherapyDTO> findOne(Long id) {
        log.debug("Request to get DatasetTherapy : {}", id);
        return datasetTherapyRepository.findById(id).map(datasetTherapyMapper::toDto);
    }

    /**
     * Delete the datasetTherapy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetTherapy : {}", id);
        datasetTherapyRepository.deleteById(id);
    }
}
