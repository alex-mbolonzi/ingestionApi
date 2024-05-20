package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetTherapyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetTherapy} and its DTO {@link DatasetTherapyDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetTherapyMapper extends EntityMapper<DatasetTherapyDTO, DatasetTherapy> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetTherapyDTO toDto(DatasetTherapy datasetTherapy);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetTherapy toEntity(DatasetTherapyDTO datasetTherapyDTO);

    default DatasetTherapy fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetTherapy datasetTherapy = new DatasetTherapy();
        datasetTherapy.setId(id);
        return datasetTherapy;
    }
}
