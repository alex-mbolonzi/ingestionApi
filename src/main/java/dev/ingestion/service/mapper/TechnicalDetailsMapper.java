package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.TechnicalDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechnicalDetails} and its DTO {@link TechnicalDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { IngestionRequestDetailsMapper.class })
public interface TechnicalDetailsMapper extends EntityMapper<TechnicalDetailsDTO, TechnicalDetails> {
    @Mapping(source = "ingestionRequestId.id", target = "ingestionRequestIdId")
    TechnicalDetailsDTO toDto(TechnicalDetails technicalDetails);

    @Mapping(source = "ingestionRequestIdId", target = "ingestionRequestId")
    TechnicalDetails toEntity(TechnicalDetailsDTO technicalDetailsDTO);

    default TechnicalDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechnicalDetails technicalDetails = new TechnicalDetails();
        technicalDetails.setId(id);
        return technicalDetails;
    }
}
