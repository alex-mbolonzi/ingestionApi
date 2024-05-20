package dev.ingestion.repository;

import dev.ingestion.domain.DatasetUserUsageRestriction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetUserUsageRestriction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetUserUsageRestrictionRepository extends JpaRepository<DatasetUserUsageRestriction, Long> {}
