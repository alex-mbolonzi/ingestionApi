package dev.ingestion.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationReferenceTable.
 */
@Entity
@Table(name = "application_reference_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationReferenceTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @NotNull
    @Size(max = 255)
    @Column(name = "reference_data", length = 255, nullable = false)
    private String referenceData;

    @NotNull
    @Size(max = 255)
    @Column(name = "reference_data_type", length = 255, nullable = false)
    private String referenceDataType;

    @NotNull
    @Size(max = 255)
    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 255)
    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @NotNull
    @Column(name = "reference_order", nullable = false)
    private Long referenceOrder;

    @Size(max = 255)
    @Column(name = "reference_group_type", length = 255)
    private String referenceGroupType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public ApplicationReferenceTable referenceId(Long referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceData() {
        return referenceData;
    }

    public ApplicationReferenceTable referenceData(String referenceData) {
        this.referenceData = referenceData;
        return this;
    }

    public void setReferenceData(String referenceData) {
        this.referenceData = referenceData;
    }

    public String getReferenceDataType() {
        return referenceDataType;
    }

    public ApplicationReferenceTable referenceDataType(String referenceDataType) {
        this.referenceDataType = referenceDataType;
        return this;
    }

    public void setReferenceDataType(String referenceDataType) {
        this.referenceDataType = referenceDataType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ApplicationReferenceTable createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ApplicationReferenceTable createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ApplicationReferenceTable modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public ApplicationReferenceTable modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getReferenceOrder() {
        return referenceOrder;
    }

    public ApplicationReferenceTable referenceOrder(Long referenceOrder) {
        this.referenceOrder = referenceOrder;
        return this;
    }

    public void setReferenceOrder(Long referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public String getReferenceGroupType() {
        return referenceGroupType;
    }

    public ApplicationReferenceTable referenceGroupType(String referenceGroupType) {
        this.referenceGroupType = referenceGroupType;
        return this;
    }

    public void setReferenceGroupType(String referenceGroupType) {
        this.referenceGroupType = referenceGroupType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationReferenceTable)) {
            return false;
        }
        return id != null && id.equals(((ApplicationReferenceTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "ApplicationReferenceTable{" +
            "id=" +
            getId() +
            ", referenceId=" +
            getReferenceId() +
            ", referenceData='" +
            getReferenceData() +
            "'" +
            ", referenceDataType='" +
            getReferenceDataType() +
            "'" +
            ", createdBy='" +
            getCreatedBy() +
            "'" +
            ", createdDate='" +
            getCreatedDate() +
            "'" +
            ", modifiedBy='" +
            getModifiedBy() +
            "'" +
            ", modifiedDate='" +
            getModifiedDate() +
            "'" +
            ", referenceOrder=" +
            getReferenceOrder() +
            ", referenceGroupType='" +
            getReferenceGroupType() +
            "'" +
            "}"
        );
    }
}
