package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetTechAndAssay} entity.
 */
public class DatasetTechAndAssayDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetTechAndAssayId;

    @NotNull
    @Size(max = 255)
    private String techniqueAndAssay;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetTechAndAssayId() {
        return datasetTechAndAssayId;
    }

    public void setDatasetTechAndAssayId(Long datasetTechAndAssayId) {
        this.datasetTechAndAssayId = datasetTechAndAssayId;
    }

    public String getTechniqueAndAssay() {
        return techniqueAndAssay;
    }

    public void setTechniqueAndAssay(String techniqueAndAssay) {
        this.techniqueAndAssay = techniqueAndAssay;
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

        DatasetTechAndAssayDTO datasetTechAndAssayDTO = (DatasetTechAndAssayDTO) o;
        if (datasetTechAndAssayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetTechAndAssayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetTechAndAssayDTO{" +
            "id=" +
            getId() +
            ", datasetTechAndAssayId=" +
            getDatasetTechAndAssayId() +
            ", techniqueAndAssay='" +
            getTechniqueAndAssay() +
            "'" +
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
