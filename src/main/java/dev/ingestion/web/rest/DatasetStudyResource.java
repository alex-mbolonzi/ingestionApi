package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetStudyService;
import dev.ingestion.service.dto.DatasetStudyDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetStudy}.
 */
@RestController
@RequestMapping("/api")
public class DatasetStudyResource {

    private final Logger log = LoggerFactory.getLogger(DatasetStudyResource.class);

    private static final String ENTITY_NAME = "datasetStudy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetStudyService datasetStudyService;

    public DatasetStudyResource(DatasetStudyService datasetStudyService) {
        this.datasetStudyService = datasetStudyService;
    }

    /**
     * {@code POST  /dataset-studies} : Create a new datasetStudy.
     *
     * @param datasetStudyDTO the datasetStudyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetStudyDTO, or with status {@code 400 (Bad Request)} if the datasetStudy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-studies")
    public ResponseEntity<DatasetStudyDTO> createDatasetStudy(@Valid @RequestBody DatasetStudyDTO datasetStudyDTO)
        throws URISyntaxException {
        log.debug("REST request to save DatasetStudy : {}", datasetStudyDTO);
        if (datasetStudyDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetStudy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetStudyDTO result = datasetStudyService.save(datasetStudyDTO);
        return ResponseEntity.created(new URI("/api/dataset-studies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-studies} : Updates an existing datasetStudy.
     *
     * @param datasetStudyDTO the datasetStudyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetStudyDTO,
     * or with status {@code 400 (Bad Request)} if the datasetStudyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetStudyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-studies")
    public ResponseEntity<DatasetStudyDTO> updateDatasetStudy(@Valid @RequestBody DatasetStudyDTO datasetStudyDTO)
        throws URISyntaxException {
        log.debug("REST request to update DatasetStudy : {}", datasetStudyDTO);
        if (datasetStudyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetStudyDTO result = datasetStudyService.save(datasetStudyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetStudyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-studies} : get all the datasetStudies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetStudies in body.
     */
    @GetMapping("/dataset-studies")
    public List<DatasetStudyDTO> getAllDatasetStudies() {
        log.debug("REST request to get all DatasetStudies");
        return datasetStudyService.findAll();
    }

    /**
     * {@code GET  /dataset-studies/:id} : get the "id" datasetStudy.
     *
     * @param id the id of the datasetStudyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetStudyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-studies/{id}")
    public ResponseEntity<DatasetStudyDTO> getDatasetStudy(@PathVariable Long id) {
        log.debug("REST request to get DatasetStudy : {}", id);
        Optional<DatasetStudyDTO> datasetStudyDTO = datasetStudyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetStudyDTO);
    }

    /**
     * {@code DELETE  /dataset-studies/:id} : delete the "id" datasetStudy.
     *
     * @param id the id of the datasetStudyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-studies/{id}")
    public ResponseEntity<Void> deleteDatasetStudy(@PathVariable Long id) {
        log.debug("REST request to delete DatasetStudy : {}", id);
        datasetStudyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
