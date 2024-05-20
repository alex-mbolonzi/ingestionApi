package dev.ingestion.repository;

import dev.ingestion.domain.DatasetDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetDetailsRepository extends JpaRepository<DatasetDetails, Long> {}
