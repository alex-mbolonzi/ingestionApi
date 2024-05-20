package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.ApplicationReferenceTable} entity.
 */
public class ApplicationReferenceTableDTO implements Serializable {

    private Long id;

    @NotNull
    private Long referenceId;

    @NotNull
    @Size(max = 255)
    private String referenceData;

    @NotNull
    @Size(max = 255)
    private String referenceDataType;

    @NotNull
    @Size(max = 255)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDate;

    @NotNull
    private Long referenceOrder;

    @Size(max = 255)
    private String referenceGroupType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceData() {
        return referenceData;
    }

    public void setReferenceData(String referenceData) {
        this.referenceData = referenceData;
    }

    public String getReferenceDataType() {
        return referenceDataType;
    }

    public void setReferenceDataType(String referenceDataType) {
        this.referenceDataType = referenceDataType;
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

    public Long getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(Long referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public String getReferenceGroupType() {
        return referenceGroupType;
    }

    public void setReferenceGroupType(String referenceGroupType) {
        this.referenceGroupType = referenceGroupType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationReferenceTableDTO applicationReferenceTableDTO = (ApplicationReferenceTableDTO) o;
        if (applicationReferenceTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationReferenceTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "ApplicationReferenceTableDTO{" +
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
