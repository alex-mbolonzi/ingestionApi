package dev.ingestion.service;

import dev.ingestion.domain.DatasetIndication;
import dev.ingestion.repository.DatasetIndicationRepository;
import dev.ingestion.service.dto.DatasetIndicationDTO;
import dev.ingestion.service.mapper.DatasetIndicationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetIndication}.
 */
@Service
@Transactional
public class DatasetIndicationService {

    private final Logger log = LoggerFactory.getLogger(DatasetIndicationService.class);

    private final DatasetIndicationRepository datasetIndicationRepository;

    private final DatasetIndicationMapper datasetIndicationMapper;

    public DatasetIndicationService(
        DatasetIndicationRepository datasetIndicationRepository,
        DatasetIndicationMapper datasetIndicationMapper
    ) {
        this.datasetIndicationRepository = datasetIndicationRepository;
        this.datasetIndicationMapper = datasetIndicationMapper;
    }

    /**
     * Save a datasetIndication.
     *
     * @param datasetIndicationDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetIndicationDTO save(DatasetIndicationDTO datasetIndicationDTO) {
        log.debug("Request to save DatasetIndication : {}", datasetIndicationDTO);
        DatasetIndication datasetIndication = datasetIndicationMapper.toEntity(datasetIndicationDTO);
        datasetIndication = datasetIndicationRepository.save(datasetIndication);
        return datasetIndicationMapper.toDto(datasetIndication);
    }

    /**
     * Get all the datasetIndications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetIndicationDTO> findAll() {
        log.debug("Request to get all DatasetIndications");
        return datasetIndicationRepository
            .findAll()
            .stream()
            .map(datasetIndicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetIndication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetIndicationDTO> findOne(Long id) {
        log.debug("Request to get DatasetIndication : {}", id);
        return datasetIndicationRepository.findById(id).map(datasetIndicationMapper::toDto);
    }

    /**
     * Delete the datasetIndication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetIndication : {}", id);
        datasetIndicationRepository.deleteById(id);
    }
}
