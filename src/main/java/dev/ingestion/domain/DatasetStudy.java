package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetStudy.
 */
@Entity
@Table(name = "dataset_study")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetStudy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_study_id", nullable = false)
    private Long datasetStudyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "study_id", length = 255, nullable = false)
    private String studyId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetStudies")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetStudyId() {
        return datasetStudyId;
    }

    public DatasetStudy datasetStudyId(Long datasetStudyId) {
        this.datasetStudyId = datasetStudyId;
        return this;
    }

    public void setDatasetStudyId(Long datasetStudyId) {
        this.datasetStudyId = datasetStudyId;
    }

    public String getStudyId() {
        return studyId;
    }

    public DatasetStudy studyId(String studyId) {
        this.studyId = studyId;
        return this;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetStudy datasetId(DatasetDetails datasetDetails) {
        this.datasetId = datasetDetails;
        return this;
    }

    public void setDatasetId(DatasetDetails datasetDetails) {
        this.datasetId = datasetDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatasetStudy)) {
            return false;
        }
        return id != null && id.equals(((DatasetStudy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DatasetStudy{" + "id=" + getId() + ", datasetStudyId=" + getDatasetStudyId() + ", studyId='" + getStudyId() + "'" + "}";
    }
}
