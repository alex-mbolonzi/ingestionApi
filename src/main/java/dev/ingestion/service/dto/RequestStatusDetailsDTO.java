package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.RequestStatusDetails} entity.
 */
public class RequestStatusDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long requestStatusId;

    @NotNull
    @Size(max = 255)
    private String decisionByName;

    @NotNull
    @Size(max = 255)
    private String decisionByMudid;

    @NotNull
    @Size(max = 255)
    private String decisionByEmail;

    @NotNull
    private Instant decisionDate;

    @Size(max = 255)
    private String decisionComments;

    @Size(max = 255)
    private String rejectionReason;

    @NotNull
    private Boolean activeFlag;

    @NotNull
    @Size(max = 255)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDate;

    private Long ingestionRequestIdId;

    private Long statusIdId;

    private String statusIdStatusIdRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public String getDecisionByName() {
        return decisionByName;
    }

    public void setDecisionByName(String decisionByName) {
        this.decisionByName = decisionByName;
    }

    public String getDecisionByMudid() {
        return decisionByMudid;
    }

    public void setDecisionByMudid(String decisionByMudid) {
        this.decisionByMudid = decisionByMudid;
    }

    public String getDecisionByEmail() {
        return decisionByEmail;
    }

    public void setDecisionByEmail(String decisionByEmail) {
        this.decisionByEmail = decisionByEmail;
    }

    public Instant getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(Instant decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getDecisionComments() {
        return decisionComments;
    }

    public void setDecisionComments(String decisionComments) {
        this.decisionComments = decisionComments;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
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

    public Long getIngestionRequestIdId() {
        return ingestionRequestIdId;
    }

    public void setIngestionRequestIdId(Long ingestionRequestDetailsId) {
        this.ingestionRequestIdId = ingestionRequestDetailsId;
    }

    public Long getStatusIdId() {
        return statusIdId;
    }

    public void setStatusIdId(Long statusId) {
        this.statusIdId = statusId;
    }

    public String getStatusIdStatusIdRef() {
        return statusIdStatusIdRef;
    }

    public void setStatusIdStatusIdRef(String statusStatusIdRef) {
        this.statusIdStatusIdRef = statusStatusIdRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestStatusDetailsDTO requestStatusDetailsDTO = (RequestStatusDetailsDTO) o;
        if (requestStatusDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestStatusDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "RequestStatusDetailsDTO{" +
            "id=" +
            getId() +
            ", requestStatusId=" +
            getRequestStatusId() +
            ", decisionByName='" +
            getDecisionByName() +
            "'" +
            ", decisionByMudid='" +
            getDecisionByMudid() +
            "'" +
            ", decisionByEmail='" +
            getDecisionByEmail() +
            "'" +
            ", decisionDate='" +
            getDecisionDate() +
            "'" +
            ", decisionComments='" +
            getDecisionComments() +
            "'" +
            ", rejectionReason='" +
            getRejectionReason() +
            "'" +
            ", activeFlag='" +
            isActiveFlag() +
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
            ", ingestionRequestId=" +
            getIngestionRequestIdId() +
            ", statusId=" +
            getStatusIdId() +
            ", statusId='" +
            getStatusIdStatusIdRef() +
            "'" +
            "}"
        );
    }
}
