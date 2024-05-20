package dev.ingestion.web.rest;

import dev.ingestion.service.IngestionRequestDetailsService;
import dev.ingestion.service.dto.IngestionRequestDetailsDTO;
import dev.ingestion.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link dev.ingestion.domain.IngestionRequestDetails}.
 */
@RestController
@RequestMapping("/api")
public class IngestionRequestDetailsResource {

    private final Logger log = LoggerFactory.getLogger(IngestionRequestDetailsResource.class);

    private static final String ENTITY_NAME = "ingestionRequestDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngestionRequestDetailsService ingestionRequestDetailsService;

    public IngestionRequestDetailsResource(IngestionRequestDetailsService ingestionRequestDetailsService) {
        this.ingestionRequestDetailsService = ingestionRequestDetailsService;
    }

    /**
     * {@code POST  /ingestion-request-details} : Create a new ingestionRequestDetails.
     *
     * @param ingestionRequestDetailsDTO the ingestionRequestDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingestionRequestDetailsDTO, or with status {@code 400 (Bad Request)} if the ingestionRequestDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingestion-request-details")
    public ResponseEntity<IngestionRequestDetailsDTO> createIngestionRequestDetails(
        @Valid @RequestBody IngestionRequestDetailsDTO ingestionRequestDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save IngestionRequestDetails : {}", ingestionRequestDetailsDTO);
        if (ingestionRequestDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingestionRequestDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngestionRequestDetailsDTO result = ingestionRequestDetailsService.save(ingestionRequestDetailsDTO);
        return ResponseEntity.created(new URI("/api/ingestion-request-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingestion-request-details} : Updates an existing ingestionRequestDetails.
     *
     * @param ingestionRequestDetailsDTO the ingestionRequestDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingestionRequestDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the ingestionRequestDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingestionRequestDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingestion-request-details")
    public ResponseEntity<IngestionRequestDetailsDTO> updateIngestionRequestDetails(
        @Valid @RequestBody IngestionRequestDetailsDTO ingestionRequestDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IngestionRequestDetails : {}", ingestionRequestDetailsDTO);
        if (ingestionRequestDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngestionRequestDetailsDTO result = ingestionRequestDetailsService.save(ingestionRequestDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingestionRequestDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ingestion-request-details} : get all the ingestionRequestDetails.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingestionRequestDetails in body.
     */
    @GetMapping("/ingestion-request-details")
    public List<IngestionRequestDetailsDTO> getAllIngestionRequestDetails(@RequestParam(required = false) String filter) {
        if ("datasetdetails-is-null".equals(filter)) {
            log.debug("REST request to get all IngestionRequestDetailss where datasetDetails is null");
            return ingestionRequestDetailsService.findAllWhereDatasetDetailsIsNull();
        }
        if ("technicaldetails-is-null".equals(filter)) {
            log.debug("REST request to get all IngestionRequestDetailss where technicalDetails is null");
            return ingestionRequestDetailsService.findAllWhereTechnicalDetailsIsNull();
        }
        log.debug("REST request to get all IngestionRequestDetails");
        return ingestionRequestDetailsService.findAll();
    }

    /**
     * {@code GET  /ingestion-request-details/:id} : get the "id" ingestionRequestDetails.
     *
     * @param id the id of the ingestionRequestDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingestionRequestDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingestion-request-details/{id}")
    public ResponseEntity<IngestionRequestDetailsDTO> getIngestionRequestDetails(@PathVariable Long id) {
        log.debug("REST request to get IngestionRequestDetails : {}", id);
        Optional<IngestionRequestDetailsDTO> ingestionRequestDetailsDTO = ingestionRequestDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingestionRequestDetailsDTO);
    }

    /**
     * {@code DELETE  /ingestion-request-details/:id} : delete the "id" ingestionRequestDetails.
     *
     * @param id the id of the ingestionRequestDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingestion-request-details/{id}")
    public ResponseEntity<Void> deleteIngestionRequestDetails(@PathVariable Long id) {
        log.debug("REST request to delete IngestionRequestDetails : {}", id);
        ingestionRequestDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
