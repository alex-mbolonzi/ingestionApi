package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetUserUsageRestriction} entity.
 */
public class DatasetUserUsageRestrictionDTO implements Serializable {

    private Long id;

    @NotNull
    private Long usageUserRestrictionId;

    @NotNull
    @Size(max = 255)
    private String restrictionRef;

    @NotNull
    @Size(max = 255)
    private String restrictionTypeRef;

    @Size(max = 255)
    private String reasonForOther;

    @NotNull
    @Size(max = 255)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDate;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsageUserRestrictionId() {
        return usageUserRestrictionId;
    }

    public void setUsageUserRestrictionId(Long usageUserRestrictionId) {
        this.usageUserRestrictionId = usageUserRestrictionId;
    }

    public String getRestrictionRef() {
        return restrictionRef;
    }

    public void setRestrictionRef(String restrictionRef) {
        this.restrictionRef = restrictionRef;
    }

    public String getRestrictionTypeRef() {
        return restrictionTypeRef;
    }

    public void setRestrictionTypeRef(String restrictionTypeRef) {
        this.restrictionTypeRef = restrictionTypeRef;
    }

    public String getReasonForOther() {
        return reasonForOther;
    }

    public void setReasonForOther(String reasonForOther) {
        this.reasonForOther = reasonForOther;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
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

        DatasetUserUsageRestrictionDTO datasetUserUsageRestrictionDTO = (DatasetUserUsageRestrictionDTO) o;
        if (datasetUserUsageRestrictionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetUserUsageRestrictionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetUserUsageRestrictionDTO{" +
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
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
