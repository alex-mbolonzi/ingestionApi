package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.ValidationNotesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ValidationNotes} and its DTO {@link ValidationNotesDTO}.
 */
@Mapper(componentModel = "spring", uses = { IngestionRequestDetailsMapper.class })
public interface ValidationNotesMapper extends EntityMapper<ValidationNotesDTO, ValidationNotes> {
    @Mapping(source = "ingestionRequestId.id", target = "ingestionRequestIdId")
    ValidationNotesDTO toDto(ValidationNotes validationNotes);

    @Mapping(source = "ingestionRequestIdId", target = "ingestionRequestId")
    ValidationNotes toEntity(ValidationNotesDTO validationNotesDTO);

    default ValidationNotes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ValidationNotes validationNotes = new ValidationNotes();
        validationNotes.setId(id);
        return validationNotes;
    }
}
