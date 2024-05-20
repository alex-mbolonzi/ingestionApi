package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetTechAndAssayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetTechAndAssay} and its DTO {@link DatasetTechAndAssayDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetTechAndAssayMapper extends EntityMapper<DatasetTechAndAssayDTO, DatasetTechAndAssay> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetTechAndAssayDTO toDto(DatasetTechAndAssay datasetTechAndAssay);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetTechAndAssay toEntity(DatasetTechAndAssayDTO datasetTechAndAssayDTO);

    default DatasetTechAndAssay fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetTechAndAssay datasetTechAndAssay = new DatasetTechAndAssay();
        datasetTechAndAssay.setId(id);
        return datasetTechAndAssay;
    }
}
