package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetDataCategory} entity.
 */
public class DatasetDataCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetDataCategoryId;

    @NotNull
    @Size(max = 255)
    private String dataCategoryRef;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetDataCategoryId() {
        return datasetDataCategoryId;
    }

    public void setDatasetDataCategoryId(Long datasetDataCategoryId) {
        this.datasetDataCategoryId = datasetDataCategoryId;
    }

    public String getDataCategoryRef() {
        return dataCategoryRef;
    }

    public void setDataCategoryRef(String dataCategoryRef) {
        this.dataCategoryRef = dataCategoryRef;
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

        DatasetDataCategoryDTO datasetDataCategoryDTO = (DatasetDataCategoryDTO) o;
        if (datasetDataCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetDataCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetDataCategoryDTO{" +
            "id=" +
            getId() +
            ", datasetDataCategoryId=" +
            getDatasetDataCategoryId() +
            ", dataCategoryRef='" +
            getDataCategoryRef() +
            "'" +
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
