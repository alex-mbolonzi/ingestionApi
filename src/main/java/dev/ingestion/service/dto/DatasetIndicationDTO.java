package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetIndication} entity.
 */
public class DatasetIndicationDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetIndicationId;

    @NotNull
    @Size(max = 255)
    private String indication;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetIndicationId() {
        return datasetIndicationId;
    }

    public void setDatasetIndicationId(Long datasetIndicationId) {
        this.datasetIndicationId = datasetIndicationId;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Long getDatasetIdId() {
        return datasetIdId;
    }

    public void setDatasetIdId(Long datasetDetailsId) {
        this.datasetIdId = datasetDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatasetIndicationDTO datasetIndicationDTO = (DatasetIndicationDTO) o;
        if (datasetIndicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetIndicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetIndicationDTO{" +
            "id=" +
            getId() +
            ", datasetIndicationId=" +
            getDatasetIndicationId() +
            ", indication='" +
            getIndication() +
            "'" +
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
