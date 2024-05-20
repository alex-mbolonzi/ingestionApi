package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.ApplicationReferenceTableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationReferenceTable} and its DTO {@link ApplicationReferenceTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationReferenceTableMapper extends EntityMapper<ApplicationReferenceTableDTO, ApplicationReferenceTable> {
    default ApplicationReferenceTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationReferenceTable applicationReferenceTable = new ApplicationReferenceTable();
        applicationReferenceTable.setId(id);
        return applicationReferenceTable;
    }
}
