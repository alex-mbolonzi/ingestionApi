package dev.ingestion.repository;

import dev.ingestion.domain.DatasetTechAndAssay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetTechAndAssay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetTechAndAssayRepository extends JpaRepository<DatasetTechAndAssay, Long> {}
