package dev.ingestion.repository;

import dev.ingestion.domain.DatasetTherapy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetTherapy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetTherapyRepository extends JpaRepository<DatasetTherapy, Long> {}
