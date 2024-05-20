package dev.ingestion.service;

import dev.ingestion.domain.DatasetDataCategory;
import dev.ingestion.repository.DatasetDataCategoryRepository;
import dev.ingestion.service.dto.DatasetDataCategoryDTO;
import dev.ingestion.service.mapper.DatasetDataCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DatasetDataCategory}.
 */
@Service
@Transactional
public class DatasetDataCategoryService {

    private final Logger log = LoggerFactory.getLogger(DatasetDataCategoryService.class);

    private final DatasetDataCategoryRepository datasetDataCategoryRepository;

    private final DatasetDataCategoryMapper datasetDataCategoryMapper;

    public DatasetDataCategoryService(
        DatasetDataCategoryRepository datasetDataCategoryRepository,
        DatasetDataCategoryMapper datasetDataCategoryMapper
    ) {
        this.datasetDataCategoryRepository = datasetDataCategoryRepository;
        this.datasetDataCategoryMapper = datasetDataCategoryMapper;
    }

    /**
     * Save a datasetDataCategory.
     *
     * @param datasetDataCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public DatasetDataCategoryDTO save(DatasetDataCategoryDTO datasetDataCategoryDTO) {
        log.debug("Request to save DatasetDataCategory : {}", datasetDataCategoryDTO);
        DatasetDataCategory datasetDataCategory = datasetDataCategoryMapper.toEntity(datasetDataCategoryDTO);
        datasetDataCategory = datasetDataCategoryRepository.save(datasetDataCategory);
        return datasetDataCategoryMapper.toDto(datasetDataCategory);
    }

    /**
     * Get all the datasetDataCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetDataCategoryDTO> findAll() {
        log.debug("Request to get all DatasetDataCategories");
        return datasetDataCategoryRepository
            .findAll()
            .stream()
            .map(datasetDataCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one datasetDataCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatasetDataCategoryDTO> findOne(Long id) {
        log.debug("Request to get DatasetDataCategory : {}", id);
        return datasetDataCategoryRepository.findById(id).map(datasetDataCategoryMapper::toDto);
    }

    /**
     * Delete the datasetDataCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatasetDataCategory : {}", id);
        datasetDataCategoryRepository.deleteById(id);
    }
}
