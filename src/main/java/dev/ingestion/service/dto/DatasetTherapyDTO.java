package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetTherapy} entity.
 */
public class DatasetTherapyDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetTheraphyId;

    @NotNull
    @Size(max = 255)
    private String therapy;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetTheraphyId() {
        return datasetTheraphyId;
    }

    public void setDatasetTheraphyId(Long datasetTheraphyId) {
        this.datasetTheraphyId = datasetTheraphyId;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
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

        DatasetTherapyDTO datasetTherapyDTO = (DatasetTherapyDTO) o;
        if (datasetTherapyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetTherapyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetTherapyDTO{" +
            "id=" +
            getId() +
            ", datasetTheraphyId=" +
            getDatasetTheraphyId() +
            ", therapy='" +
            getTherapy() +
            "'" +
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
