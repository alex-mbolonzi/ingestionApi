package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.IngestionRequestDetails} entity.
 */
public class IngestionRequestDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long ingestionRequestId;

    @NotNull
    @Size(max = 255)
    private String requesterName;

    @NotNull
    @Size(max = 255)
    private String requesterMudid;

    @NotNull
    @Size(max = 255)
    private String requesterEmail;

    @Size(max = 255)
    private String requestRationaleReason;

    @NotNull
    @Size(max = 255)
    private String requestedByName;

    @NotNull
    @Size(max = 255)
    private String requestedByMudid;

    @NotNull
    @Size(max = 255)
    private String requestedByEmail;

    @NotNull
    @Size(max = 255)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDate;

    @Size(max = 255)
    private String modifiedReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIngestionRequestId() {
        return ingestionRequestId;
    }

    public void setIngestionRequestId(Long ingestionRequestId) {
        this.ingestionRequestId = ingestionRequestId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterMudid() {
        return requesterMudid;
    }

    public void setRequesterMudid(String requesterMudid) {
        this.requesterMudid = requesterMudid;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequestRationaleReason() {
        return requestRationaleReason;
    }

    public void setRequestRationaleReason(String requestRationaleReason) {
        this.requestRationaleReason = requestRationaleReason;
    }

    public String getRequestedByName() {
        return requestedByName;
    }

    public void setRequestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
    }

    public String getRequestedByMudid() {
        return requestedByMudid;
    }

    public void setRequestedByMudid(String requestedByMudid) {
        this.requestedByMudid = requestedByMudid;
    }

    public String getRequestedByEmail() {
        return requestedByEmail;
    }

    public void setRequestedByEmail(String requestedByEmail) {
        this.requestedByEmail = requestedByEmail;
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

    public String getModifiedReason() {
        return modifiedReason;
    }

    public void setModifiedReason(String modifiedReason) {
        this.modifiedReason = modifiedReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngestionRequestDetailsDTO ingestionRequestDetailsDTO = (IngestionRequestDetailsDTO) o;
        if (ingestionRequestDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingestionRequestDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "IngestionRequestDetailsDTO{" +
            "id=" +
            getId() +
            ", ingestionRequestId=" +
            getIngestionRequestId() +
            ", requesterName='" +
            getRequesterName() +
            "'" +
            ", requesterMudid='" +
            getRequesterMudid() +
            "'" +
            ", requesterEmail='" +
            getRequesterEmail() +
            "'" +
            ", requestRationaleReason='" +
            getRequestRationaleReason() +
            "'" +
            ", requestedByName='" +
            getRequestedByName() +
            "'" +
            ", requestedByMudid='" +
            getRequestedByMudid() +
            "'" +
            ", requestedByEmail='" +
            getRequestedByEmail() +
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
            ", modifiedReason='" +
            getModifiedReason() +
            "'" +
            "}"
        );
    }
}
