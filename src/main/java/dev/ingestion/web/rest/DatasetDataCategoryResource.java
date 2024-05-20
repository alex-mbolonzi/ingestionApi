package dev.ingestion.web.rest;

import dev.ingestion.service.DatasetDataCategoryService;
import dev.ingestion.service.dto.DatasetDataCategoryDTO;
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
 * REST controller for managing {@link dev.ingestion.domain.DatasetDataCategory}.
 */
@RestController
@RequestMapping("/api")
public class DatasetDataCategoryResource {

    private final Logger log = LoggerFactory.getLogger(DatasetDataCategoryResource.class);

    private static final String ENTITY_NAME = "datasetDataCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatasetDataCategoryService datasetDataCategoryService;

    public DatasetDataCategoryResource(DatasetDataCategoryService datasetDataCategoryService) {
        this.datasetDataCategoryService = datasetDataCategoryService;
    }

    /**
     * {@code POST  /dataset-data-categories} : Create a new datasetDataCategory.
     *
     * @param datasetDataCategoryDTO the datasetDataCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datasetDataCategoryDTO, or with status {@code 400 (Bad Request)} if the datasetDataCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataset-data-categories")
    public ResponseEntity<DatasetDataCategoryDTO> createDatasetDataCategory(
        @Valid @RequestBody DatasetDataCategoryDTO datasetDataCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DatasetDataCategory : {}", datasetDataCategoryDTO);
        if (datasetDataCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new datasetDataCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetDataCategoryDTO result = datasetDataCategoryService.save(datasetDataCategoryDTO);
        return ResponseEntity.created(new URI("/api/dataset-data-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dataset-data-categories} : Updates an existing datasetDataCategory.
     *
     * @param datasetDataCategoryDTO the datasetDataCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datasetDataCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the datasetDataCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datasetDataCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataset-data-categories")
    public ResponseEntity<DatasetDataCategoryDTO> updateDatasetDataCategory(
        @Valid @RequestBody DatasetDataCategoryDTO datasetDataCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DatasetDataCategory : {}", datasetDataCategoryDTO);
        if (datasetDataCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetDataCategoryDTO result = datasetDataCategoryService.save(datasetDataCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datasetDataCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dataset-data-categories} : get all the datasetDataCategories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datasetDataCategories in body.
     */
    @GetMapping("/dataset-data-categories")
    public List<DatasetDataCategoryDTO> getAllDatasetDataCategories() {
        log.debug("REST request to get all DatasetDataCategories");
        return datasetDataCategoryService.findAll();
    }

    /**
     * {@code GET  /dataset-data-categories/:id} : get the "id" datasetDataCategory.
     *
     * @param id the id of the datasetDataCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datasetDataCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataset-data-categories/{id}")
    public ResponseEntity<DatasetDataCategoryDTO> getDatasetDataCategory(@PathVariable Long id) {
        log.debug("REST request to get DatasetDataCategory : {}", id);
        Optional<DatasetDataCategoryDTO> datasetDataCategoryDTO = datasetDataCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetDataCategoryDTO);
    }

    /**
     * {@code DELETE  /dataset-data-categories/:id} : delete the "id" datasetDataCategory.
     *
     * @param id the id of the datasetDataCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataset-data-categories/{id}")
    public ResponseEntity<Void> deleteDatasetDataCategory(@PathVariable Long id) {
        log.debug("REST request to delete DatasetDataCategory : {}", id);
        datasetDataCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
