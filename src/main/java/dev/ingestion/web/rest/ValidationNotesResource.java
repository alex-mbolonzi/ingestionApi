package dev.ingestion.web.rest;

import dev.ingestion.service.ValidationNotesService;
import dev.ingestion.service.dto.ValidationNotesDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.ValidationNotes}.
 */
@RestController
@RequestMapping("/api")
public class ValidationNotesResource {

    private final Logger log = LoggerFactory.getLogger(ValidationNotesResource.class);

    private static final String ENTITY_NAME = "validationNotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidationNotesService validationNotesService;

    public ValidationNotesResource(ValidationNotesService validationNotesService) {
        this.validationNotesService = validationNotesService;
    }

    /**
     * {@code POST  /validation-notes} : Create a new validationNotes.
     *
     * @param validationNotesDTO the validationNotesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validationNotesDTO, or with status {@code 400 (Bad Request)} if the validationNotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/validation-notes")
    public ResponseEntity<ValidationNotesDTO> createValidationNotes(@Valid @RequestBody ValidationNotesDTO validationNotesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ValidationNotes : {}", validationNotesDTO);
        if (validationNotesDTO.getId() != null) {
            throw new BadRequestAlertException("A new validationNotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValidationNotesDTO result = validationNotesService.save(validationNotesDTO);
        return ResponseEntity.created(new URI("/api/validation-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /validation-notes} : Updates an existing validationNotes.
     *
     * @param validationNotesDTO the validationNotesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validationNotesDTO,
     * or with status {@code 400 (Bad Request)} if the validationNotesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validationNotesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/validation-notes")
    public ResponseEntity<ValidationNotesDTO> updateValidationNotes(@Valid @RequestBody ValidationNotesDTO validationNotesDTO)
        throws URISyntaxException {
        log.debug("REST request to update ValidationNotes : {}", validationNotesDTO);
        if (validationNotesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ValidationNotesDTO result = validationNotesService.save(validationNotesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validationNotesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /validation-notes} : get all the validationNotes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validationNotes in body.
     */
    @GetMapping("/validation-notes")
    public List<ValidationNotesDTO> getAllValidationNotes() {
        log.debug("REST request to get all ValidationNotes");
        return validationNotesService.findAll();
    }

    /**
     * {@code GET  /validation-notes/:id} : get the "id" validationNotes.
     *
     * @param id the id of the validationNotesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validationNotesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/validation-notes/{id}")
    public ResponseEntity<ValidationNotesDTO> getValidationNotes(@PathVariable Long id) {
        log.debug("REST request to get ValidationNotes : {}", id);
        Optional<ValidationNotesDTO> validationNotesDTO = validationNotesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(validationNotesDTO);
    }

    /**
     * {@code DELETE  /validation-notes/:id} : delete the "id" validationNotes.
     *
     * @param id the id of the validationNotesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/validation-notes/{id}")
    public ResponseEntity<Void> deleteValidationNotes(@PathVariable Long id) {
        log.debug("REST request to delete ValidationNotes : {}", id);
        validationNotesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
