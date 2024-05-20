package dev.ingestion.web.rest;

import dev.ingestion.service.RequestStatusDetailsService;
import dev.ingestion.service.dto.RequestStatusDetailsDTO;
import dev.ingestion.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link dev.ingestion.domain.RequestStatusDetails}.
 */
@RestController
@RequestMapping("/api")
public class RequestStatusDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RequestStatusDetailsResource.class);

    private static final String ENTITY_NAME = "requestStatusDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestStatusDetailsService requestStatusDetailsService;

    public RequestStatusDetailsResource(RequestStatusDetailsService requestStatusDetailsService) {
        this.requestStatusDetailsService = requestStatusDetailsService;
    }

    /**
     * {@code POST  /request-status-details} : Create a new requestStatusDetails.
     *
     * @param requestStatusDetailsDTO the requestStatusDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestStatusDetailsDTO, or with status {@code 400 (Bad Request)} if the requestStatusDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-status-details")
    public ResponseEntity<RequestStatusDetailsDTO> createRequestStatusDetails(
        @Valid @RequestBody RequestStatusDetailsDTO requestStatusDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RequestStatusDetails : {}", requestStatusDetailsDTO);
        if (requestStatusDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestStatusDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestStatusDetailsDTO result = requestStatusDetailsService.save(requestStatusDetailsDTO);
        return ResponseEntity.created(new URI("/api/request-status-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-status-details} : Updates an existing requestStatusDetails.
     *
     * @param requestStatusDetailsDTO the requestStatusDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestStatusDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the requestStatusDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestStatusDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-status-details")
    public ResponseEntity<RequestStatusDetailsDTO> updateRequestStatusDetails(
        @Valid @RequestBody RequestStatusDetailsDTO requestStatusDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestStatusDetails : {}", requestStatusDetailsDTO);
        if (requestStatusDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestStatusDetailsDTO result = requestStatusDetailsService.save(requestStatusDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestStatusDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-status-details} : get all the requestStatusDetails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestStatusDetails in body.
     */
    @GetMapping("/request-status-details")
    public List<RequestStatusDetailsDTO> getAllRequestStatusDetails() {
        log.debug("REST request to get all RequestStatusDetails");
        return requestStatusDetailsService.findAll();
    }

    /**
     * {@code GET  /request-status-details/:id} : get the "id" requestStatusDetails.
     *
     * @param id the id of the requestStatusDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestStatusDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-status-details/{id}")
    public ResponseEntity<RequestStatusDetailsDTO> getRequestStatusDetails(@PathVariable Long id) {
        log.debug("REST request to get RequestStatusDetails : {}", id);
        Optional<RequestStatusDetailsDTO> requestStatusDetailsDTO = requestStatusDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestStatusDetailsDTO);
    }

    /**
     * {@code DELETE  /request-status-details/:id} : delete the "id" requestStatusDetails.
     *
     * @param id the id of the requestStatusDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-status-details/{id}")
    public ResponseEntity<Void> deleteRequestStatusDetails(@PathVariable Long id) {
        log.debug("REST request to delete RequestStatusDetails : {}", id);
        requestStatusDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
