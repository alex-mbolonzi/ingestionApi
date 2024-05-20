package dev.ingestion.repository;

import dev.ingestion.domain.EmailTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {}
