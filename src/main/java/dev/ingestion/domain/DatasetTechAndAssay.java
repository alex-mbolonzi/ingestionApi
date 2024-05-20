package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetTechAndAssay.
 */
@Entity
@Table(name = "dataset_tech_and_assay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetTechAndAssay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_tech_and_assay_id", nullable = false)
    private Long datasetTechAndAssayId;

    @NotNull
    @Size(max = 255)
    @Column(name = "technique_and_assay", length = 255, nullable = false)
    private String techniqueAndAssay;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetTechAndAssays")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetTechAndAssayId() {
        return datasetTechAndAssayId;
    }

    public DatasetTechAndAssay datasetTechAndAssayId(Long datasetTechAndAssayId) {
        this.datasetTechAndAssayId = datasetTechAndAssayId;
        return this;
    }

    public void setDatasetTechAndAssayId(Long datasetTechAndAssayId) {
        this.datasetTechAndAssayId = datasetTechAndAssayId;
    }

    public String getTechniqueAndAssay() {
        return techniqueAndAssay;
    }

    public DatasetTechAndAssay techniqueAndAssay(String techniqueAndAssay) {
        this.techniqueAndAssay = techniqueAndAssay;
        return this;
    }

    public void setTechniqueAndAssay(String techniqueAndAssay) {
        this.techniqueAndAssay = techniqueAndAssay;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetTechAndAssay datasetId(DatasetDetails datasetDetails) {
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
        if (!(o instanceof DatasetTechAndAssay)) {
            return false;
        }
        return id != null && id.equals(((DatasetTechAndAssay) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetTechAndAssay{" +
            "id=" +
            getId() +
            ", datasetTechAndAssayId=" +
            getDatasetTechAndAssayId() +
            ", techniqueAndAssay='" +
            getTechniqueAndAssay() +
            "'" +
            "}"
        );
    }
}
