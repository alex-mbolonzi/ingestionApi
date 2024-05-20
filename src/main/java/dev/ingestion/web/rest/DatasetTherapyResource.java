package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetTherapyService;
import dev.ingestion.service.dto.DatasetTherapyDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetTherapy}.
 */
@RestController
@RequestMapping("/api")
public class DatasetTherapyResource {

    private final Logger log = LoggerFactory.getLogger(DatasetTherapyResource.class);

    private static final String ENTITY_NAME = "datasetTherapy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetTherapyService datasetTherapyService;

    public DatasetTherapyResource(DatasetTherapyService datasetTherapyService) {
        this.datasetTherapyService = datasetTherapyService;
    }

    /**
     * {@code POST  /dataset-therapies} : Create a new datasetTherapy.
     *
     * @param datasetTherapyDTO the datasetTherapyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetTherapyDTO, or with status {@code 400 (Bad Request)} if the datasetTherapy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-therapies")
    public ResponseEntity<DatasetTherapyDTO> createDatasetTherapy(@Valid @RequestBody DatasetTherapyDTO datasetTherapyDTO)
        throws URISyntaxException {
        log.debug("REST request to save DatasetTherapy : {}", datasetTherapyDTO);
        if (datasetTherapyDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetTherapy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetTherapyDTO result = datasetTherapyService.save(datasetTherapyDTO);
        return ResponseEntity.created(new URI("/api/dataset-therapies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-therapies} : Updates an existing datasetTherapy.
     *
     * @param datasetTherapyDTO the datasetTherapyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetTherapyDTO,
     * or with status {@code 400 (Bad Request)} if the datasetTherapyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetTherapyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-therapies")
    public ResponseEntity<DatasetTherapyDTO> updateDatasetTherapy(@Valid @RequestBody DatasetTherapyDTO datasetTherapyDTO)
        throws URISyntaxException {
        log.debug("REST request to update DatasetTherapy : {}", datasetTherapyDTO);
        if (datasetTherapyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetTherapyDTO result = datasetTherapyService.save(datasetTherapyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetTherapyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-therapies} : get all the datasetTherapies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetTherapies in body.
     */
    @GetMapping("/dataset-therapies")
    public List<DatasetTherapyDTO> getAllDatasetTherapies() {
        log.debug("REST request to get all DatasetTherapies");
        return datasetTherapyService.findAll();
    }

    /**
     * {@code GET  /dataset-therapies/:id} : get the "id" datasetTherapy.
     *
     * @param id the id of the datasetTherapyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetTherapyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-therapies/{id}")
    public ResponseEntity<DatasetTherapyDTO> getDatasetTherapy(@PathVariable Long id) {
        log.debug("REST request to get DatasetTherapy : {}", id);
        Optional<DatasetTherapyDTO> datasetTherapyDTO = datasetTherapyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetTherapyDTO);
    }

    /**
     * {@code DELETE  /dataset-therapies/:id} : delete the "id" datasetTherapy.
     *
     * @param id the id of the datasetTherapyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-therapies/{id}")
    public ResponseEntity<Void> deleteDatasetTherapy(@PathVariable Long id) {
        log.debug("REST request to delete DatasetTherapy : {}", id);
        datasetTherapyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
