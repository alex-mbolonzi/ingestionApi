package dev.ingestion.repository;

import dev.ingestion.domain.ValidationNotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ValidationNotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidationNotesRepository extends JpaRepository<ValidationNotes, Long> {}
