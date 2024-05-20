package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetRoleDetailsService;
import dev.ingestion.service.dto.DatasetRoleDetailsDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetRoleDetails}.
 */
@RestController
@RequestMapping("/api")
public class DatasetRoleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DatasetRoleDetailsResource.class);

    private static final String ENTITY_NAME = "datasetRoleDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetRoleDetailsService datasetRoleDetailsService;

    public DatasetRoleDetailsResource(DatasetRoleDetailsService datasetRoleDetailsService) {
        this.datasetRoleDetailsService = datasetRoleDetailsService;
    }

    /**
     * {@code POST  /dataset-role-details} : Create a new datasetRoleDetails.
     *
     * @param datasetRoleDetailsDTO the datasetRoleDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetRoleDetailsDTO, or with status {@code 400 (Bad Request)} if the datasetRoleDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-role-details")
    public ResponseEntity<DatasetRoleDetailsDTO> createDatasetRoleDetails(@Valid @RequestBody DatasetRoleDetailsDTO datasetRoleDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DatasetRoleDetails : {}", datasetRoleDetailsDTO);
        if (datasetRoleDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetRoleDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetRoleDetailsDTO result = datasetRoleDetailsService.save(datasetRoleDetailsDTO);
        return ResponseEntity.created(new URI("/api/dataset-role-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-role-details} : Updates an existing datasetRoleDetails.
     *
     * @param datasetRoleDetailsDTO the datasetRoleDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetRoleDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the datasetRoleDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetRoleDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-role-details")
    public ResponseEntity<DatasetRoleDetailsDTO> updateDatasetRoleDetails(@Valid @RequestBody DatasetRoleDetailsDTO datasetRoleDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to update DatasetRoleDetails : {}", datasetRoleDetailsDTO);
        if (datasetRoleDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetRoleDetailsDTO result = datasetRoleDetailsService.save(datasetRoleDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetRoleDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-role-details} : get all the datasetRoleDetails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetRoleDetails in body.
     */
    @GetMapping("/dataset-role-details")
    public List<DatasetRoleDetailsDTO> getAllDatasetRoleDetails() {
        log.debug("REST request to get all DatasetRoleDetails");
        return datasetRoleDetailsService.findAll();
    }

    /**
     * {@code GET  /dataset-role-details/:id} : get the "id" datasetRoleDetails.
     *
     * @param id the id of the datasetRoleDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetRoleDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-role-details/{id}")
    public ResponseEntity<DatasetRoleDetailsDTO> getDatasetRoleDetails(@PathVariable Long id) {
        log.debug("REST request to get DatasetRoleDetails : {}", id);
        Optional<DatasetRoleDetailsDTO> datasetRoleDetailsDTO = datasetRoleDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetRoleDetailsDTO);
    }

    /**
     * {@code DELETE  /dataset-role-details/:id} : delete the "id" datasetRoleDetails.
     *
     * @param id the id of the datasetRoleDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-role-details/{id}")
    public ResponseEntity<Void> deleteDatasetRoleDetails(@PathVariable Long id) {
        log.debug("REST request to delete DatasetRoleDetails : {}", id);
        datasetRoleDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
