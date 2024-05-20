package dev.ingestion.web.rest;

import dev.ingestion.service.ApplicationReferenceTableService;
import dev.ingestion.service.dto.ApplicationReferenceTableDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.ApplicationReferenceTable}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationReferenceTableResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationReferenceTableResource.class);

    private static final String ENTITY_NAME = "applicationReferenceTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationReferenceTableService applicationReferenceTableService;

    public ApplicationReferenceTableResource(ApplicationReferenceTableService applicationReferenceTableService) {
        this.applicationReferenceTableService = applicationReferenceTableService;
    }

    /**
     * {@code POST  /application-reference-tables} : Create a new applicationReferenceTable.
     *
     * @param applicationReferenceTableDTO the applicationReferenceTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationReferenceTableDTO, or with status {@code 400 (Bad Request)} if the applicationReferenceTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-reference-tables")
    public ResponseEntity<ApplicationReferenceTableDTO> createApplicationReferenceTable(
        @Valid @RequestBody ApplicationReferenceTableDTO applicationReferenceTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ApplicationReferenceTable : {}", applicationReferenceTableDTO);
        if (applicationReferenceTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationReferenceTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationReferenceTableDTO result = applicationReferenceTableService.save(applicationReferenceTableDTO);
        return ResponseEntity.created(new URI("/api/application-reference-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-reference-tables} : Updates an existing applicationReferenceTable.
     *
     * @param applicationReferenceTableDTO the applicationReferenceTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationReferenceTableDTO,
     * or with status {@code 400 (Bad Request)} if the applicationReferenceTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationReferenceTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-reference-tables")
    public ResponseEntity<ApplicationReferenceTableDTO> updateApplicationReferenceTable(
        @Valid @RequestBody ApplicationReferenceTableDTO applicationReferenceTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApplicationReferenceTable : {}", applicationReferenceTableDTO);
        if (applicationReferenceTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationReferenceTableDTO result = applicationReferenceTableService.save(applicationReferenceTableDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationReferenceTableDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code GET  /application-reference-tables} : get all the applicationReferenceTables.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationReferenceTables in body.
     */
    @GetMapping("/application-reference-tables")
    public List<ApplicationReferenceTableDTO> getAllApplicationReferenceTables() {
        log.debug("REST request to get all ApplicationReferenceTables");
        return applicationReferenceTableService.findAll();
    }

    /**
     * {@code GET  /application-reference-tables/:id} : get the "id" applicationReferenceTable.
     *
     * @param id the id of the applicationReferenceTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationReferenceTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-reference-tables/{id}")
    public ResponseEntity<ApplicationReferenceTableDTO> getApplicationReferenceTable(@PathVariable Long id) {
        log.debug("REST request to get ApplicationReferenceTable : {}", id);
        Optional<ApplicationReferenceTableDTO> applicationReferenceTableDTO = applicationReferenceTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationReferenceTableDTO);
    }

    /**
     * {@code DELETE  /application-reference-tables/:id} : delete the "id" applicationReferenceTable.
     *
     * @param id the id of the applicationReferenceTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-reference-tables/{id}")
    public ResponseEntity<Void> deleteApplicationReferenceTable(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationReferenceTable : {}", id);
        applicationReferenceTableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
