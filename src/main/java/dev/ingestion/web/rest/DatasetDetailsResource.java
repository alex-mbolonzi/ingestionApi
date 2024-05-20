package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetDetailsService;
import dev.ingestion.service.dto.DatasetDetailsDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetDetails}.
 */
@RestController
@RequestMapping("/api")
public class DatasetDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DatasetDetailsResource.class);

    private static final String ENTITY_NAME = "datasetDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetDetailsService datasetDetailsService;

    public DatasetDetailsResource(DatasetDetailsService datasetDetailsService) {
        this.datasetDetailsService = datasetDetailsService;
    }

    /**
     * {@code POST  /dataset-details} : Create a new datasetDetails.
     *
     * @param datasetDetailsDTO the datasetDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetDetailsDTO, or with status {@code 400 (Bad Request)} if the datasetDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-details")
    public ResponseEntity<DatasetDetailsDTO> createDatasetDetails(@Valid @RequestBody DatasetDetailsDTO datasetDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DatasetDetails : {}", datasetDetailsDTO);
        if (datasetDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetDetailsDTO result = datasetDetailsService.save(datasetDetailsDTO);
        return ResponseEntity.created(new URI("/api/dataset-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-details} : Updates an existing datasetDetails.
     *
     * @param datasetDetailsDTO the datasetDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the datasetDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-details")
    public ResponseEntity<DatasetDetailsDTO> updateDatasetDetails(@Valid @RequestBody DatasetDetailsDTO datasetDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to update DatasetDetails : {}", datasetDetailsDTO);
        if (datasetDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetDetailsDTO result = datasetDetailsService.save(datasetDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-details} : get all the datasetDetails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetDetails in body.
     */
    @GetMapping("/dataset-details")
    public List<DatasetDetailsDTO> getAllDatasetDetails() {
        log.debug("REST request to get all DatasetDetails");
        return datasetDetailsService.findAll();
    }

    /**
     * {@code GET  /dataset-details/:id} : get the "id" datasetDetails.
     *
     * @param id the id of the datasetDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-details/{id}")
    public ResponseEntity<DatasetDetailsDTO> getDatasetDetails(@PathVariable Long id) {
        log.debug("REST request to get DatasetDetails : {}", id);
        Optional<DatasetDetailsDTO> datasetDetailsDTO = datasetDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetDetailsDTO);
    }

    /**
     * {@code DELETE  /dataset-details/:id} : delete the "id" datasetDetails.
     *
     * @param id the id of the datasetDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-details/{id}")
    public ResponseEntity<Void> deleteDatasetDetails(@PathVariable Long id) {
        log.debug("REST request to delete DatasetDetails : {}", id);
        datasetDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
