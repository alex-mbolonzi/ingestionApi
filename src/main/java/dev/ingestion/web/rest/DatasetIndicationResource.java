package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetIndicationService;
import dev.ingestion.service.dto.DatasetIndicationDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetIndication}.
 */
@RestController
@RequestMapping("/api")
public class DatasetIndicationResource {

    private final Logger log = LoggerFactory.getLogger(DatasetIndicationResource.class);

    private static final String ENTITY_NAME = "datasetIndication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetIndicationService datasetIndicationService;

    public DatasetIndicationResource(DatasetIndicationService datasetIndicationService) {
        this.datasetIndicationService = datasetIndicationService;
    }

    /**
     * {@code POST  /dataset-indications} : Create a new datasetIndication.
     *
     * @param datasetIndicationDTO the datasetIndicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetIndicationDTO, or with status {@code 400 (Bad Request)} if the datasetIndication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-indications")
    public ResponseEntity<DatasetIndicationDTO> createDatasetIndication(@Valid @RequestBody DatasetIndicationDTO datasetIndicationDTO)
        throws URISyntaxException {
        log.debug("REST request to save DatasetIndication : {}", datasetIndicationDTO);
        if (datasetIndicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetIndication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetIndicationDTO result = datasetIndicationService.save(datasetIndicationDTO);
        return ResponseEntity.created(new URI("/api/dataset-indications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-indications} : Updates an existing datasetIndication.
     *
     * @param datasetIndicationDTO the datasetIndicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetIndicationDTO,
     * or with status {@code 400 (Bad Request)} if the datasetIndicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetIndicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-indications")
    public ResponseEntity<DatasetIndicationDTO> updateDatasetIndication(@Valid @RequestBody DatasetIndicationDTO datasetIndicationDTO)
        throws URISyntaxException {
        log.debug("REST request to update DatasetIndication : {}", datasetIndicationDTO);
        if (datasetIndicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetIndicationDTO result = datasetIndicationService.save(datasetIndicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetIndicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-indications} : get all the datasetIndications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetIndications in body.
     */
    @GetMapping("/dataset-indications")
    public List<DatasetIndicationDTO> getAllDatasetIndications() {
        log.debug("REST request to get all DatasetIndications");
        return datasetIndicationService.findAll();
    }

    /**
     * {@code GET  /dataset-indications/:id} : get the "id" datasetIndication.
     *
     * @param id the id of the datasetIndicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetIndicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-indications/{id}")
    public ResponseEntity<DatasetIndicationDTO> getDatasetIndication(@PathVariable Long id) {
        log.debug("REST request to get DatasetIndication : {}", id);
        Optional<DatasetIndicationDTO> datasetIndicationDTO = datasetIndicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetIndicationDTO);
    }

    /**
     * {@code DELETE  /dataset-indications/:id} : delete the "id" datasetIndication.
     *
     * @param id the id of the datasetIndicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-indications/{id}")
    public ResponseEntity<Void> deleteDatasetIndication(@PathVariable Long id) {
        log.debug("REST request to delete DatasetIndication : {}", id);
        datasetIndicationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
