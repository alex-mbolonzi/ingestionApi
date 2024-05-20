package dev.ingestion.repository;

import dev.ingestion.domain.TechnicalDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TechnicalDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnicalDetailsRepository extends JpaRepository<TechnicalDetails, Long> {}
