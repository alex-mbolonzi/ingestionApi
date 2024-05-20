package dev.ingestion.service;

import dev.ingestion.domain.RequestStatusDetails;
import dev.ingestion.repository.RequestStatusDetailsRepository;
import dev.ingestion.service.dto.RequestStatusDetailsDTO;
import dev.ingestion.service.mapper.RequestStatusDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestStatusDetails}.
 */
@Service
@Transactional
public class RequestStatusDetailsService {

    private final Logger log = LoggerFactory.getLogger(RequestStatusDetailsService.class);

    private final RequestStatusDetailsRepository requestStatusDetailsRepository;

    private final RequestStatusDetailsMapper requestStatusDetailsMapper;

    public RequestStatusDetailsService(
        RequestStatusDetailsRepository requestStatusDetailsRepository,
        RequestStatusDetailsMapper requestStatusDetailsMapper
    ) {
        this.requestStatusDetailsRepository = requestStatusDetailsRepository;
        this.requestStatusDetailsMapper = requestStatusDetailsMapper;
    }

    /**
     * Save a requestStatusDetails.
     *
     * @param requestStatusDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestStatusDetailsDTO save(RequestStatusDetailsDTO requestStatusDetailsDTO) {
        log.debug("Request to save RequestStatusDetails : {}", requestStatusDetailsDTO);
        RequestStatusDetails requestStatusDetails = requestStatusDetailsMapper.toEntity(requestStatusDetailsDTO);
        requestStatusDetails = requestStatusDetailsRepository.save(requestStatusDetails);
        return requestStatusDetailsMapper.toDto(requestStatusDetails);
    }

    /**
     * Get all the requestStatusDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestStatusDetailsDTO> findAll() {
        log.debug("Request to get all RequestStatusDetails");
        return requestStatusDetailsRepository
            .findAll()
            .stream()
            .map(requestStatusDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one requestStatusDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestStatusDetailsDTO> findOne(Long id) {
        log.debug("Request to get RequestStatusDetails : {}", id);
        return requestStatusDetailsRepository.findById(id).map(requestStatusDetailsMapper::toDto);
    }

    /**
     * Delete the requestStatusDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestStatusDetails : {}", id);
        requestStatusDetailsRepository.deleteById(id);
    }
}
