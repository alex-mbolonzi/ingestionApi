package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetDataCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetDataCategory} and its DTO {@link DatasetDataCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetDataCategoryMapper extends EntityMapper<DatasetDataCategoryDTO, DatasetDataCategory> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetDataCategoryDTO toDto(DatasetDataCategory datasetDataCategory);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetDataCategory toEntity(DatasetDataCategoryDTO datasetDataCategoryDTO);

    default DatasetDataCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetDataCategory datasetDataCategory = new DatasetDataCategory();
        datasetDataCategory.setId(id);
        return datasetDataCategory;
    }
}
