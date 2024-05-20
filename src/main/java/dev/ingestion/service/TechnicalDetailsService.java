package dev.ingestion.service;

import dev.ingestion.domain.TechnicalDetails;
import dev.ingestion.repository.TechnicalDetailsRepository;
import dev.ingestion.service.dto.TechnicalDetailsDTO;
import dev.ingestion.service.mapper.TechnicalDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TechnicalDetails}.
 */
@Service
@Transactional
public class TechnicalDetailsService {

    private final Logger log = LoggerFactory.getLogger(TechnicalDetailsService.class);

    private final TechnicalDetailsRepository technicalDetailsRepository;

    private final TechnicalDetailsMapper technicalDetailsMapper;

    public TechnicalDetailsService(TechnicalDetailsRepository technicalDetailsRepository, TechnicalDetailsMapper technicalDetailsMapper) {
        this.technicalDetailsRepository = technicalDetailsRepository;
        this.technicalDetailsMapper = technicalDetailsMapper;
    }

    /**
     * Save a technicalDetails.
     *
     * @param technicalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public TechnicalDetailsDTO save(TechnicalDetailsDTO technicalDetailsDTO) {
        log.debug("Request to save TechnicalDetails : {}", technicalDetailsDTO);
        TechnicalDetails technicalDetails = technicalDetailsMapper.toEntity(technicalDetailsDTO);
        technicalDetails = technicalDetailsRepository.save(technicalDetails);
        return technicalDetailsMapper.toDto(technicalDetails);
    }

    /**
     * Get all the technicalDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechnicalDetailsDTO> findAll() {
        log.debug("Request to get all TechnicalDetails");
        return technicalDetailsRepository
            .findAll()
            .stream()
            .map(technicalDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one technicalDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechnicalDetailsDTO> findOne(Long id) {
        log.debug("Request to get TechnicalDetails : {}", id);
        return technicalDetailsRepository.findById(id).map(technicalDetailsMapper::toDto);
    }

    /**
     * Delete the technicalDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechnicalDetails : {}", id);
        technicalDetailsRepository.deleteById(id);
    }
}
