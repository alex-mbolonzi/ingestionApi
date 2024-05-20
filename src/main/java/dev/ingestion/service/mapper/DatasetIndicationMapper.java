package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.DatasetIndicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatasetIndication} and its DTO {@link DatasetIndicationDTO}.
 */
@Mapper(componentModel = "spring", uses = { DatasetDetailsMapper.class })
public interface DatasetIndicationMapper extends EntityMapper<DatasetIndicationDTO, DatasetIndication> {
    @Mapping(source = "datasetId.id", target = "datasetIdId")
    DatasetIndicationDTO toDto(DatasetIndication datasetIndication);

    @Mapping(source = "datasetIdId", target = "datasetId")
    DatasetIndication toEntity(DatasetIndicationDTO datasetIndicationDTO);

    default DatasetIndication fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatasetIndication datasetIndication = new DatasetIndication();
        datasetIndication.setId(id);
        return datasetIndication;
    }
}
