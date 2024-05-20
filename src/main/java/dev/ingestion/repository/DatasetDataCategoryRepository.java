package dev.ingestion.repository;

import dev.ingestion.domain.DatasetDataCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetDataCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetDataCategoryRepository extends JpaRepository<DatasetDataCategory, Long> {}
