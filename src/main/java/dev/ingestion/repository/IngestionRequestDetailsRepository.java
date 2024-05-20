package dev.ingestion.repository;

import dev.ingestion.domain.IngestionRequestDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IngestionRequestDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngestionRequestDetailsRepository extends JpaRepository<IngestionRequestDetails, Long> {}
