package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetUserUsageRestriction.
 */
@Entity
@Table(name = "dataset_user_usage_restriction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetUserUsageRestriction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "usage_user_restriction_id", nullable = false)
    private Long usageUserRestrictionId;

    @NotNull
    @Size(max = 255)
    @Column(name = "restriction_ref", length = 255, nullable = false)
    private String restrictionRef;

    @NotNull
    @Size(max = 255)
    @Column(name = "restriction_type_ref", length = 255, nullable = false)
    private String restrictionTypeRef;

    @Size(max = 255)
    @Column(name = "reason_for_other", length = 255)
    private String reasonForOther;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetUserUsageRestrictions")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsageUserRestrictionId() {
        return usageUserRestrictionId;
    }

    public DatasetUserUsageRestriction usageUserRestrictionId(Long usageUserRestrictionId) {
        this.usageUserRestrictionId = usageUserRestrictionId;
        return this;
    }

    public void setUsageUserRestrictionId(Long usageUserRestrictionId) {
        this.usageUserRestrictionId = usageUserRestrictionId;
    }

    public String getRestrictionRef() {
        return restrictionRef;
    }

    public DatasetUserUsageRestriction restrictionRef(String restrictionRef) {
        this.restrictionRef = restrictionRef;
        return this;
    }

    public void setRestrictionRef(String restrictionRef) {
        this.restrictionRef = restrictionRef;
    }

    public String getRestrictionTypeRef() {
        return restrictionTypeRef;
    }

    public DatasetUserUsageRestriction restrictionTypeRef(String restrictionTypeRef) {
        this.restrictionTypeRef = restrictionTypeRef;
        return this;
    }

    public void setRestrictionTypeRef(String restrictionTypeRef) {
        this.restrictionTypeRef = restrictionTypeRef;
    }

    public String getReasonForOther() {
        return reasonForOther;
    }

    public DatasetUserUsageRestriction reasonForOther(String reasonForOther) {
        this.reasonForOther = reasonForOther;
        return this;
    }

    public void setReasonForOther(String reasonForOther) {
        this.reasonForOther = reasonForOther;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DatasetUserUsageRestriction createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DatasetUserUsageRestriction createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DatasetUserUsageRestriction modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public DatasetUserUsageRestriction modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetUserUsageRestriction datasetId(DatasetDetails datasetDetails) {
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
        if (!(o instanceof DatasetUserUsageRestriction)) {
            return false;
        }
        return id != null && id.equals(((DatasetUserUsageRestriction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetUserUsageRestriction{" +
            "id=" +
            getId() +
            ", usageUserRestrictionId=" +
            getUsageUserRestrictionId() +
            ", restrictionRef='" +
            getRestrictionRef() +
            "'" +
            ", restrictionTypeRef='" +
            getRestrictionTypeRef() +
            "'" +
            ", reasonForOther='" +
            getReasonForOther() +
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
            "}"
        );
    }
}
