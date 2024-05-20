package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetUserUsageRestrictionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetUserUsageRestriction} and its DTO {@link DatasetUserUsageRestrictionDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetUserUsageRestrictionMapper extends EntityMapper<DatasetUserUsageRestrictionDTO, DatasetUserUsageRestriction> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetUserUsageRestrictionDTO toDto(DatasetUserUsageRestriction datasetUserUsageRestriction);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetUserUsageRestriction toEntity(DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO);

    default DatasetUserUsageRestriction fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetUserUsageRestriction datasetUserUsageRestriction = new DatasetUserUsageRestriction();
        datasetUserUsageRestriction.setId(id);
        return datasetUserUsageRestriction;
    }
}
