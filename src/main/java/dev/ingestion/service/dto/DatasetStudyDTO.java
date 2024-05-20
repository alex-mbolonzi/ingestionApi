package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetStudy} entity.
 */
public class DatasetStudyDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetStudyId;

    @NotNull
    @Size(max = 255)
    private String studyId;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetStudyId() {
        return datasetStudyId;
    }

    public void setDatasetStudyId(Long datasetStudyId) {
        this.datasetStudyId = datasetStudyId;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
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

        DatasetStudyDTO datasetStudyDTO = (DatasetStudyDTO) o;
        if (datasetStudyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetStudyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetStudyDTO{" +
            "id=" +
            getId() +
            ", datasetStudyId=" +
            getDatasetStudyId() +
            ", studyId='" +
            getStudyId() +
            "'" +
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
