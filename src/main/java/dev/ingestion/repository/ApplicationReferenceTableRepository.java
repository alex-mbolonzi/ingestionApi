package dev.ingestion.repository;

import dev.ingestion.domain.ApplicationReferenceTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationReferenceTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationReferenceTableRepository extends JpaRepository<ApplicationReferenceTable, Long> {}
