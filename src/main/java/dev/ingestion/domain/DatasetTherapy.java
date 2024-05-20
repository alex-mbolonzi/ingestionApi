package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetTherapy.
 */
@Entity
@Table(name = "dataset_therapy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetTherapy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_theraphy_id", nullable = false)
    private Long datasetTheraphyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "therapy", length = 255, nullable = false)
    private String therapy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetTherapies")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetTheraphyId() {
        return datasetTheraphyId;
    }

    public DatasetTherapy datasetTheraphyId(Long datasetTheraphyId) {
        this.datasetTheraphyId = datasetTheraphyId;
        return this;
    }

    public void setDatasetTheraphyId(Long datasetTheraphyId) {
        this.datasetTheraphyId = datasetTheraphyId;
    }

    public String getTherapy() {
        return therapy;
    }

    public DatasetTherapy therapy(String therapy) {
        this.therapy = therapy;
        return this;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetTherapy datasetId(DatasetDetails datasetDetails) {
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
        if (!(o instanceof DatasetTherapy)) {
            return false;
        }
        return id != null && id.equals(((DatasetTherapy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetTherapy{" + "id=" + getId() + ", datasetTheraphyId=" + getDatasetTheraphyId() + ", therapy='" + getTherapy() + "'" + "}"
        );
    }
}
