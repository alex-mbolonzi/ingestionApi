package dev.ingestion.web.rest;

import dev.ingestion.service.TechnicalDetailsService;
import dev.ingestion.service.dto.TechnicalDetailsDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.TechnicalDetails}.
 */
@RestController
@RequestMapping("/api")
public class TechnicalDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TechnicalDetailsResource.class);

    private static final String ENTITY_NAME = "technicalDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechnicalDetailsService technicalDetailsService;

    public TechnicalDetailsResource(TechnicalDetailsService technicalDetailsService) {
        this.technicalDetailsService = technicalDetailsService;
    }

    /**
     * {@code POST  /technical-details} : Create a new technicalDetails.
     *
     * @param technicalDetailsDTO the technicalDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new technicalDetailsDTO, or with status {@code 400 (Bad Request)} if the technicalDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/technical-details")
    public ResponseEntity<TechnicalDetailsDTO> createTechnicalDetails(@Valid @RequestBody TechnicalDetailsDTO technicalDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save TechnicalDetails : {}", technicalDetailsDTO);
        if (technicalDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new technicalDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnicalDetailsDTO result = technicalDetailsService.save(technicalDetailsDTO);
        return ResponseEntity.created(new URI("/api/technical-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /technical-details} : Updates an existing technicalDetails.
     *
     * @param technicalDetailsDTO the technicalDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technicalDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the technicalDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the technicalDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/technical-details")
    public ResponseEntity<TechnicalDetailsDTO> updateTechnicalDetails(@Valid @RequestBody TechnicalDetailsDTO technicalDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to update TechnicalDetails : {}", technicalDetailsDTO);
        if (technicalDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechnicalDetailsDTO result = technicalDetailsService.save(technicalDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, technicalDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /technical-details} : get all the technicalDetails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of technicalDetails in body.
     */
    @GetMapping("/technical-details")
    public List<TechnicalDetailsDTO> getAllTechnicalDetails() {
        log.debug("REST request to get all TechnicalDetails");
        return technicalDetailsService.findAll();
    }

    /**
     * {@code GET  /technical-details/:id} : get the "id" technicalDetails.
     *
     * @param id the id of the technicalDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the technicalDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/technical-details/{id}")
    public ResponseEntity<TechnicalDetailsDTO> getTechnicalDetails(@PathVariable Long id) {
        log.debug("REST request to get TechnicalDetails : {}", id);
        Optional<TechnicalDetailsDTO> technicalDetailsDTO = technicalDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technicalDetailsDTO);
    }

    /**
     * {@code DELETE  /technical-details/:id} : delete the "id" technicalDetails.
     *
     * @param id the id of the technicalDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/technical-details/{id}")
    public ResponseEntity<Void> deleteTechnicalDetails(@PathVariable Long id) {
        log.debug("REST request to delete TechnicalDetails : {}", id);
        technicalDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
