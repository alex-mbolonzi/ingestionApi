package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetDataCategory.
 */
@Entity
@Table(name = "dataset_data_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetDataCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_data_category_id", nullable = false)
    private Long datasetDataCategoryId;

    @NotNull
    @Size(max = 255)
    @Column(name = "data_category_ref", length = 255, nullable = false)
    private String dataCategoryRef;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetDataCategories")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetDataCategoryId() {
        return datasetDataCategoryId;
    }

    public DatasetDataCategory datasetDataCategoryId(Long datasetDataCategoryId) {
        this.datasetDataCategoryId = datasetDataCategoryId;
        return this;
    }

    public void setDatasetDataCategoryId(Long datasetDataCategoryId) {
        this.datasetDataCategoryId = datasetDataCategoryId;
    }

    public String getDataCategoryRef() {
        return dataCategoryRef;
    }

    public DatasetDataCategory dataCategoryRef(String dataCategoryRef) {
        this.dataCategoryRef = dataCategoryRef;
        return this;
    }

    public void setDataCategoryRef(String dataCategoryRef) {
        this.dataCategoryRef = dataCategoryRef;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetDataCategory datasetId(DatasetDetails datasetDetails) {
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
        if (!(o instanceof DatasetDataCategory)) {
            return false;
        }
        return id != null && id.equals(((DatasetDataCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetDataCategory{" +
            "id=" +
            getId() +
            ", datasetDataCategoryId=" +
            getDatasetDataCategoryId() +
            ", dataCategoryRef='" +
            getDataCategoryRef() +
            "'" +
            "}"
        );
    }
}
