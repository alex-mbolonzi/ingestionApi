package dev.ingestion.service;

import dev.ingestion.domain.DatasetStudy;
import dev.ingestion.repository.DatasetStudyRepository;
import dev.ingestion.service.dto.DatasetStudyDTO;
import dev.ingestion.service.mapper.DatasetStudyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetStudy}.
 */
@Service
@Transactional
public class DatasetStudyService {

    private final Logger log = LoggerFactory.getLogger(DatasetStudyService.class);

    private final DatasetStudyRepository datasetStudyRepository;

    private final DatasetStudyMapper datasetStudyMapper;

    public DatasetStudyService(DatasetStudyRepository datasetStudyRepository, DatasetStudyMapper datasetStudyMapper) {
        this.datasetStudyRepository = datasetStudyRepository;
        this.datasetStudyMapper = datasetStudyMapper;
    }

    /**
     * Save a datasetStudy.
     *
     * @param datasetStudyDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetStudyDTO save(DatasetStudyDTO datasetStudyDTO) {
        log.debug("Request to save DatasetStudy : {}", datasetStudyDTO);
        DatasetStudy datasetStudy = datasetStudyMapper.toEntity(datasetStudyDTO);
        datasetStudy = datasetStudyRepository.save(datasetStudy);
        return datasetStudyMapper.toDto(datasetStudy);
    }

    /**
     * Get all the datasetStudies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetStudyDTO> findAll() {
        log.debug("Request to get all DatasetStudies");
        return datasetStudyRepository.findAll().stream().map(datasetStudyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetStudy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetStudyDTO> findOne(Long id) {
        log.debug("Request to get DatasetStudy : {}", id);
        return datasetStudyRepository.findById(id).map(datasetStudyMapper::toDto);
    }

    /**
     * Delete the datasetStudy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetStudy : {}", id);
        datasetStudyRepository.deleteById(id);
    }
}
