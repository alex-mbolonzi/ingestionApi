package dev.ingestion.repository;

import dev.ingestion.domain.RequestStatusDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RequestStatusDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStatusDetailsRepository extends JpaRepository<RequestStatusDetails, Long> {}
