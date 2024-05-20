package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetTechAndAssayService;
import dev.ingestion.service.dto.DatasetTechAndAssayDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetTechAndAssay}.
 */
@RestController
@RequestMapping("/api")
public class DatasetTechAndAssayResource {

    private final Logger log = LoggerFactory.getLogger(DatasetTechAndAssayResource.class);

    private static final String ENTITY_NAME = "datasetTechAndAssay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetTechAndAssayService datasetTechAndAssayService;

    public DatasetTechAndAssayResource(DatasetTechAndAssayService datasetTechAndAssayService) {
        this.datasetTechAndAssayService = datasetTechAndAssayService;
    }

    /**
     * {@code POST  /dataset-tech-and-assays} : Create a new datasetTechAndAssay.
     *
     * @param datasetTechAndAssayDTO the datasetTechAndAssayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetTechAndAssayDTO, or with status {@code 400 (Bad Request)} if the datasetTechAndAssay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-tech-and-assays")
    public ResponseEntity<DatasetTechAndAssayDTO> createDatasetTechAndAssay(
        @Valid @RequestBody DatasetTechAndAssayDTO datasetTechAndAssayDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DatasetTechAndAssay : {}", datasetTechAndAssayDTO);
        if (datasetTechAndAssayDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetTechAndAssay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetTechAndAssayDTO result = datasetTechAndAssayService.save(datasetTechAndAssayDTO);
        return ResponseEntity.created(new URI("/api/dataset-tech-and-assays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-tech-and-assays} : Updates an existing datasetTechAndAssay.
     *
     * @param datasetTechAndAssayDTO the datasetTechAndAssayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetTechAndAssayDTO,
     * or with status {@code 400 (Bad Request)} if the datasetTechAndAssayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetTechAndAssayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-tech-and-assays")
    public ResponseEntity<DatasetTechAndAssayDTO> updateDatasetTechAndAssay(
        @Valid @RequestBody DatasetTechAndAssayDTO datasetTechAndAssayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DatasetTechAndAssay : {}", datasetTechAndAssayDTO);
        if (datasetTechAndAssayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetTechAndAssayDTO result = datasetTechAndAssayService.save(datasetTechAndAssayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetTechAndAssayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-tech-and-assays} : get all the datasetTechAndAssays.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetTechAndAssays in body.
     */
    @GetMapping("/dataset-tech-and-assays")
    public List<DatasetTechAndAssayDTO> getAllDatasetTechAndAssays() {
        log.debug("REST request to get all DatasetTechAndAssays");
        return datasetTechAndAssayService.findAll();
    }

    /**
     * {@code GET  /dataset-tech-and-assays/:id} : get the "id" datasetTechAndAssay.
     *
     * @param id the id of the datasetTechAndAssayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetTechAndAssayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-tech-and-assays/{id}")
    public ResponseEntity<DatasetTechAndAssayDTO> getDatasetTechAndAssay(@PathVariable Long id) {
        log.debug("REST request to get DatasetTechAndAssay : {}", id);
        Optional<DatasetTechAndAssayDTO> datasetTechAndAssayDTO = datasetTechAndAssayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetTechAndAssayDTO);
    }

    /**
     * {@code DELETE  /dataset-tech-and-assays/:id} : delete the "id" datasetTechAndAssay.
     *
     * @param id the id of the datasetTechAndAssayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-tech-and-assays/{id}")
    public ResponseEntity<Void> deleteDatasetTechAndAssay(@PathVariable Long id) {
        log.debug("REST request to delete DatasetTechAndAssay : {}", id);
        datasetTechAndAssayService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
