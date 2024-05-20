package dev.ingestion.service;

import dev.ingestion.domain.DatasetTechAndAssay;
import dev.ingestion.repository.DatasetTechAndAssayRepository;
import dev.ingestion.service.dto.DatasetTechAndAssayDTO;
import dev.ingestion.service.mapper.DatasetTechAndAssayMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetTechAndAssay}.
 */
@Service
@Transactional
public class DatasetTechAndAssayService {

    private final Logger log = LoggerFactory.getLogger(DatasetTechAndAssayService.class);

    private final DatasetTechAndAssayRepository datasetTechAndAssayRepository;

    private final DatasetTechAndAssayMapper datasetTechAndAssayMapper;

    public DatasetTechAndAssayService(
        DatasetTechAndAssayRepository datasetTechAndAssayRepository,
        DatasetTechAndAssayMapper datasetTechAndAssayMapper
    ) {
        this.datasetTechAndAssayRepository = datasetTechAndAssayRepository;
        this.datasetTechAndAssayMapper = datasetTechAndAssayMapper;
    }

    /**
     * Save a datasetTechAndAssay.
     *
     * @param datasetTechAndAssayDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetTechAndAssayDTO save(DatasetTechAndAssayDTO datasetTechAndAssayDTO) {
        log.debug("Request to save DatasetTechAndAssay : {}", datasetTechAndAssayDTO);
        DatasetTechAndAssay datasetTechAndAssay = datasetTechAndAssayMapper.toEntity(datasetTechAndAssayDTO);
        datasetTechAndAssay = datasetTechAndAssayRepository.save(datasetTechAndAssay);
        return datasetTechAndAssayMapper.toDto(datasetTechAndAssay);
    }

    /**
     * Get all the datasetTechAndAssays.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetTechAndAssayDTO> findAll() {
        log.debug("Request to get all DatasetTechAndAssays");
        return datasetTechAndAssayRepository
            .findAll()
            .stream()
            .map(datasetTechAndAssayMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetTechAndAssay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetTechAndAssayDTO> findOne(Long id) {
        log.debug("Request to get DatasetTechAndAssay : {}", id);
        return datasetTechAndAssayRepository.findById(id).map(datasetTechAndAssayMapper::toDto);
    }

    /**
     * Delete the datasetTechAndAssay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetTechAndAssay : {}", id);
        datasetTechAndAssayRepository.deleteById(id);
    }
}
