package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetStudyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetStudy} and its DTO {@link DatasetStudyDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetStudyMapper extends EntityMapper<DatasetStudyDTO, DatasetStudy> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetStudyDTO toDto(DatasetStudy datasetStudy);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetStudy toEntity(DatasetStudyDTO datasetStudyDTO);

    default DatasetStudy fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetStudy datasetStudy = new DatasetStudy();
        datasetStudy.setId(id);
        return datasetStudy;
    }
}
