package dev.ingestion.repository;

import dev.ingestion.domain.DatasetIndication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetIndication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetIndicationRepository extends JpaRepository<DatasetIndication, Long> {}
