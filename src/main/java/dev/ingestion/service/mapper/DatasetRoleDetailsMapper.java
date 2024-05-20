package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetRoleDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetRoleDetails} and its DTO {@link DatasetRoleDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetRoleDetailsMapper extends EntityMapper<DatasetRoleDetailsDTO, DatasetRoleDetails> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetRoleDetailsDTO toDto(DatasetRoleDetails datasetRoleDetails);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetRoleDetails toEntity(DatasetRoleDetailsDTO datasetRoleDetailsDTO);

    default DatasetRoleDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetRoleDetails datasetRoleDetails = new DatasetRoleDetails();
        datasetRoleDetails.setId(id);
        return datasetRoleDetails;
    }
}
