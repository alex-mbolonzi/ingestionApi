package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetUserUsageRestrictionService;
import dev.ingestion.service.dto.DatasetUserUsageRestrictionDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetUserUsageRestriction}.
 */
@RestController
@RequestMapping("/api")
public class DatasetUserUsageRestrictionResource {

    private final Logger log = LoggerFactory.getLogger(DatasetUserUsageRestrictionResource.class);

    private static final String ENTITY_NAME = "datasetUserUsageRestriction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetUserUsageRestrictionService datasetUserUsageRestrictionService;

    public DatasetUserUsageRestrictionResource(DatasetUserUsageRestrictionService datasetUserUsageRestrictionService) {
        this.datasetUserUsageRestrictionService = datasetUserUsageRestrictionService;
    }

    /**
     * {@code POST  /dataset-user-usage-restrictions} : Create a new datasetUserUsageRestriction.
     *
     * @param datasetUserUsageRestrictionDTO the datasetUserUsageRestrictionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetUserUsageRestrictionDTO, or with status {@code 400 (Bad Request)} if the datasetUserUsageRestriction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-user-usage-restrictions")
    public ResponseEntity<DatasetUserUsageRestrictionDTO> createDatasetUserUsageRestriction(
        @Valid @RequestBody DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DatasetUserUsageRestriction : {}", datasetUserUsageRestrictionDTO);
        if (datasetUserUsageRestrictionDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetUserUsageRestriction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetUserUsageRestrictionDTO result = datasetUserUsageRestrictionService.save(datasetUserUsageRestrictionDTO);
        return ResponseEntity.created(new URI("/api/dataset-user-usage-restrictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-user-usage-restrictions} : Updates an existing datasetUserUsageRestriction.
     *
     * @param datasetUserUsageRestrictionDTO the datasetUserUsageRestrictionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetUserUsageRestrictionDTO,
     * or with status {@code 400 (Bad Request)} if the datasetUserUsageRestrictionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetUserUsageRestrictionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-user-usage-restrictions")
    public ResponseEntity<DatasetUserUsageRestrictionDTO> updateDatasetUserUsageRestriction(
        @Valid @RequestBody DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DatasetUserUsageRestriction : {}", datasetUserUsageRestrictionDTO);
        if (datasetUserUsageRestrictionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetUserUsageRestrictionDTO result = datasetUserUsageRestrictionService.save(datasetUserUsageRestrictionDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetUserUsageRestrictionDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code GET  /dataset-user-usage-restrictions} : get all the datasetUserUsageRestrictions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetUserUsageRestrictions in body.
     */
    @GetMapping("/dataset-user-usage-restrictions")
    public List<DatasetUserUsageRestrictionDTO> getAllDatasetUserUsageRestrictions() {
        log.debug("REST request to get all DatasetUserUsageRestrictions");
        return datasetUserUsageRestrictionService.findAll();
    }

    /**
     * {@code GET  /dataset-user-usage-restrictions/:id} : get the "id" datasetUserUsageRestriction.
     *
     * @param id the id of the datasetUserUsageRestrictionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetUserUsageRestrictionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-user-usage-restrictions/{id}")
    public ResponseEntity<DatasetUserUsageRestrictionDTO> getDatasetUserUsageRestriction(@PathVariable Long id) {
        log.debug("REST request to get DatasetUserUsageRestriction : {}", id);
        Optional<DatasetUserUsageRestrictionDTO> datasetUserUsageRestrictionDTO = datasetUserUsageRestrictionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetUserUsageRestrictionDTO);
    }

    /**
     * {@code DELETE  /dataset-user-usage-restrictions/:id} : delete the "id" datasetUserUsageRestriction.
     *
     * @param id the id of the datasetUserUsageRestrictionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-user-usage-restrictions/{id}")
    public ResponseEntity<Void> deleteDatasetUserUsageRestriction(@PathVariable Long id) {
        log.debug("REST request to delete DatasetUserUsageRestriction : {}", id);
        datasetUserUsageRestrictionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
