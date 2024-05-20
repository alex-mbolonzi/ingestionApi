package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetDetails} and its DTO {@link DatasetDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { IngestionRequestDetailsMapper.class })
public interface DatasetDetailsMapper extends EntityMapper<DatasetDetailsDTO, DatasetDetails> {
    @Mapping(source = "ingestionRequestId.id", target = "ingestionRequestIdId")
    DatasetDetailsDTO toDto(DatasetDetails datasetDetails);

    @Mapping(source = "ingestionRequestIdId", target = "ingestionRequestId")
    @Mapping(target = "datasetDataCategories", ignore = true)
    @Mapping(target = "removeDatasetDataCategory", ignore = true)
    @Mapping(target = "datasetIndications", ignore = true)
    @Mapping(target = "removeDatasetIndication", ignore = true)
    @Mapping(target = "datasetRoleDetails", ignore = true)
    @Mapping(target = "removeDatasetRoleDetails", ignore = true)
    @Mapping(target = "datasetStudies", ignore = true)
    @Mapping(target = "removeDatasetStudy", ignore = true)
    @Mapping(target = "datasetTechAndAssays", ignore = true)
    @Mapping(target = "removeDatasetTechAndAssay", ignore = true)
    @Mapping(target = "datasetTherapies", ignore = true)
    @Mapping(target = "removeDatasetTherapy", ignore = true)
    @Mapping(target = "datasetUserUsageRestrictions", ignore = true)
    @Mapping(target = "removeDatasetUserUsageRestriction", ignore = true)
    DatasetDetails toEntity(DatasetDetailsDTO datasetDetailsDTO);

    default DatasetDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetDetails datasetDetails = new DatasetDetails();
        datasetDetails.setId(id);
        return datasetDetails;
    }
}
