package dev.ingestion.repository;

import dev.ingestion.domain.DatasetStudy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetStudy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetStudyRepository extends JpaRepository<DatasetStudy, Long> {}
