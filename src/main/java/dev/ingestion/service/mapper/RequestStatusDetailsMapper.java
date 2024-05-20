package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.RequestStatusDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestStatusDetails} and its DTO {@link RequestStatusDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { IngestionRequestDetailsMapper.class, StatusMapper.class })
public interface RequestStatusDetailsMapper extends EntityMapper<RequestStatusDetailsDTO, RequestStatusDetails> {
    @Mapping(source = "ingestionRequestId.id", target = "ingestionRequestIdId")
    @Mapping(source = "statusId.id", target = "statusIdId")
    @Mapping(source = "statusId.statusIdRef", target = "statusIdStatusIdRef")
    RequestStatusDetailsDTO toDto(RequestStatusDetails requestStatusDetails);

    @Mapping(source = "ingestionRequestIdId", target = "ingestionRequestId")
    @Mapping(source = "statusIdId", target = "statusId")
    RequestStatusDetails toEntity(RequestStatusDetailsDTO requestStatusDetailsDTO);

    default RequestStatusDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequestStatusDetails requestStatusDetails = new RequestStatusDetails();
        requestStatusDetails.setId(id);
        return requestStatusDetails;
    }
}
