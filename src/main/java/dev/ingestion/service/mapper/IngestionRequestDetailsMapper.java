package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.IngestionRequestDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngestionRequestDetails} and its DTO {@link IngestionRequestDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IngestionRequestDetailsMapper extends EntityMapper<IngestionRequestDetailsDTO, IngestionRequestDetails> {
    @Mapping(target = "datasetDetails", ignore = true)
    @Mapping(target = "technicalDetails", ignore = true)
    @Mapping(target = "requestStatusDetails", ignore = true)
    @Mapping(target = "removeRequestStatusDetails", ignore = true)
    @Mapping(target = "validationNotes", ignore = true)
    @Mapping(target = "removeValidationNotes", ignore = true)
    IngestionRequestDetails toEntity(IngestionRequestDetailsDTO ingestionRequestDetailsDTO);

    default IngestionRequestDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngestionRequestDetails ingestionRequestDetails = new IngestionRequestDetails();
        ingestionRequestDetails.setId(id);
        return ingestionRequestDetails;
    }
}
