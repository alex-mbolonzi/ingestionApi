package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.TechnicalDetails} entity.
 */
public class TechnicalDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long technicalRequestId;

    @Size(max = 1024)
    private String dataLocationPath;

    @Size(max = 255)
    private String dataRefreshFrequency;

    private LocalDate targetIngestionStartDate;

    private LocalDate targetIngestionEndDate;

    @Size(max = 1024)
    private String targetPath;

    @Size(max = 255)
    private String datasetTypeIngestionRef;

    @Size(max = 255)
    private String guestUsersEmail;

    @Size(max = 255)
    private String whitelistIpAddresses;

    @Size(max = 255)
    private String externalStagingContainerName;

    @Size(max = 255)
    private String externalDataSourceLocation;

    @Size(max = 255)
    private String gskAccessSourceLocationRef;

    @Size(max = 255)
    private String externalSourceSecretKeyName;

    @Size(max = 255)
    private String domainRequestId;

    @Size(max = 1024)
    private String existingDataLocationIdentified;

    @NotNull
    @Size(max = 255)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(max = 255)
    private String modifiedBy;

    private Instant modifiedDate;

    private Long ingestionRequestIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechnicalRequestId() {
        return technicalRequestId;
    }

    public void setTechnicalRequestId(Long technicalRequestId) {
        this.technicalRequestId = technicalRequestId;
    }

    public String getDataLocationPath() {
        return dataLocationPath;
    }

    public void setDataLocationPath(String dataLocationPath) {
        this.dataLocationPath = dataLocationPath;
    }

    public String getDataRefreshFrequency() {
        return dataRefreshFrequency;
    }

    public void setDataRefreshFrequency(String dataRefreshFrequency) {
        this.dataRefreshFrequency = dataRefreshFrequency;
    }

    public LocalDate getTargetIngestionStartDate() {
        return targetIngestionStartDate;
    }

    public void setTargetIngestionStartDate(LocalDate targetIngestionStartDate) {
        this.targetIngestionStartDate = targetIngestionStartDate;
    }

    public LocalDate getTargetIngestionEndDate() {
        return targetIngestionEndDate;
    }

    public void setTargetIngestionEndDate(LocalDate targetIngestionEndDate) {
        this.targetIngestionEndDate = targetIngestionEndDate;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getDatasetTypeIngestionRef() {
        return datasetTypeIngestionRef;
    }

    public void setDatasetTypeIngestionRef(String datasetTypeIngestionRef) {
        this.datasetTypeIngestionRef = datasetTypeIngestionRef;
    }

    public String getGuestUsersEmail() {
        return guestUsersEmail;
    }

    public void setGuestUsersEmail(String guestUsersEmail) {
        this.guestUsersEmail = guestUsersEmail;
    }

    public String getWhitelistIpAddresses() {
        return whitelistIpAddresses;
    }

    public void setWhitelistIpAddresses(String whitelistIpAddresses) {
        this.whitelistIpAddresses = whitelistIpAddresses;
    }

    public String getExternalStagingContainerName() {
        return externalStagingContainerName;
    }

    public void setExternalStagingContainerName(String externalStagingContainerName) {
        this.externalStagingContainerName = externalStagingContainerName;
    }

    public String getExternalDataSourceLocation() {
        return externalDataSourceLocation;
    }

    public void setExternalDataSourceLocation(String externalDataSourceLocation) {
        this.externalDataSourceLocation = externalDataSourceLocation;
    }

    public String getGskAccessSourceLocationRef() {
        return gskAccessSourceLocationRef;
    }

    public void setGskAccessSourceLocationRef(String gskAccessSourceLocationRef) {
        this.gskAccessSourceLocationRef = gskAccessSourceLocationRef;
    }

    public String getExternalSourceSecretKeyName() {
        return externalSourceSecretKeyName;
    }

    public void setExternalSourceSecretKeyName(String externalSourceSecretKeyName) {
        this.externalSourceSecretKeyName = externalSourceSecretKeyName;
    }

    public String getDomainRequestId() {
        return domainRequestId;
    }

    public void setDomainRequestId(String domainRequestId) {
        this.domainRequestId = domainRequestId;
    }

    public String getExistingDataLocationIdentified() {
        return existingDataLocationIdentified;
    }

    public void setExistingDataLocationIdentified(String existingDataLocationIdentified) {
        this.existingDataLocationIdentified = existingDataLocationIdentified;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechnicalDetailsDTO technicalDetailsDTO = (TechnicalDetailsDTO) o;
        if (technicalDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), technicalDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "TechnicalDetailsDTO{" +
            "id=" +
            getId() +
            ", technicalRequestId=" +
            getTechnicalRequestId() +
            ", dataLocationPath='" +
            getDataLocationPath() +
            "'" +
            ", dataRefreshFrequency='" +
            getDataRefreshFrequency() +
            "'" +
            ", targetIngestionStartDate='" +
            getTargetIngestionStartDate() +
            "'" +
            ", targetIngestionEndDate='" +
            getTargetIngestionEndDate() +
            "'" +
            ", targetPath='" +
            getTargetPath() +
            "'" +
            ", datasetTypeIngestionRef='" +
            getDatasetTypeIngestionRef() +
            "'" +
            ", guestUsersEmail='" +
            getGuestUsersEmail() +
            "'" +
            ", whitelistIpAddresses='" +
            getWhitelistIpAddresses() +
            "'" +
            ", externalStagingContainerName='" +
            getExternalStagingContainerName() +
            "'" +
            ", externalDataSourceLocation='" +
            getExternalDataSourceLocation() +
            "'" +
            ", gskAccessSourceLocationRef='" +
            getGskAccessSourceLocationRef() +
            "'" +
            ", externalSourceSecretKeyName='" +
            getExternalSourceSecretKeyName() +
            "'" +
            ", domainRequestId='" +
            getDomainRequestId() +
            "'" +
            ", existingDataLocationIdentified='" +
            getExistingDataLocationIdentified() +
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
            "}"
        );
    }
}
