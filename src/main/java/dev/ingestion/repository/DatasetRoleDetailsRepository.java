package dev.ingestion.repository;

import dev.ingestion.domain.DatasetRoleDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatasetRoleDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatasetRoleDetailsRepository extends JpaRepository<DatasetRoleDetails, Long> {}
