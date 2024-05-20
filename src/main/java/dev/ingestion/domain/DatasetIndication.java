package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetIndication.
 */
@Entity
@Table(name = "dataset_indication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetIndication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_indication_id", nullable = false)
    private Long datasetIndicationId;

    @NotNull
    @Size(max = 255)
    @Column(name = "indication", length = 255, nullable = false)
    private String indication;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetIndications")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetIndicationId() {
        return datasetIndicationId;
    }

    public DatasetIndication datasetIndicationId(Long datasetIndicationId) {
        this.datasetIndicationId = datasetIndicationId;
        return this;
    }

    public void setDatasetIndicationId(Long datasetIndicationId) {
        this.datasetIndicationId = datasetIndicationId;
    }

    public String getIndication() {
        return indication;
    }

    public DatasetIndication indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetIndication datasetId(DatasetDetails datasetDetails) {
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
        if (!(o instanceof DatasetIndication)) {
            return false;
        }
        return id != null && id.equals(((DatasetIndication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetIndication{" +
            "id=" +
            getId() +
            ", datasetIndicationId=" +
            getDatasetIndicationId() +
            ", indication='" +
            getIndication() +
            "'" +
            "}"
        );
    }
}
