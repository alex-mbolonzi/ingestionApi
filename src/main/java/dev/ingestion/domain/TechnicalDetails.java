package dev.ingestion.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TechnicalDetails.
 */
@Entity
@Table(name = "technical_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechnicalDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "technical_request_id", nullable = false)
    private Long technicalRequestId;

    @Size(max = 1024)
    @Column(name = "data_location_path", length = 1024)
    private String dataLocationPath;

    @Size(max = 255)
    @Column(name = "data_refresh_frequency", length = 255)
    private String dataRefreshFrequency;

    @Column(name = "target_ingestion_start_date")
    private LocalDate targetIngestionStartDate;

    @Column(name = "target_ingestion_end_date")
    private LocalDate targetIngestionEndDate;

    @Size(max = 1024)
    @Column(name = "target_path", length = 1024)
    private String targetPath;

    @Size(max = 255)
    @Column(name = "dataset_type_ingestion_ref", length = 255)
    private String datasetTypeIngestionRef;

    @Size(max = 255)
    @Column(name = "guest_users_email", length = 255)
    private String guestUsersEmail;

    @Size(max = 255)
    @Column(name = "whitelist_ip_addresses", length = 255)
    private String whitelistIpAddresses;

    @Size(max = 255)
    @Column(name = "external_staging_container_name", length = 255)
    private String externalStagingContainerName;

    @Size(max = 255)
    @Column(name = "external_data_source_location", length = 255)
    private String externalDataSourceLocation;

    @Size(max = 255)
    @Column(name = "gsk_access_source_location_ref", length = 255)
    private String gskAccessSourceLocationRef;

    @Size(max = 255)
    @Column(name = "external_source_secret_key_name", length = 255)
    private String externalSourceSecretKeyName;

    @Size(max = 255)
    @Column(name = "domain_request_id", length = 255)
    private String domainRequestId;

    @Size(max = 1024)
    @Column(name = "existing_data_location_identified", length = 1024)
    private String existingDataLocationIdentified;

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

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private IngestionRequestDetails ingestionRequestId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechnicalRequestId() {
        return technicalRequestId;
    }

    public TechnicalDetails technicalRequestId(Long technicalRequestId) {
        this.technicalRequestId = technicalRequestId;
        return this;
    }

    public void setTechnicalRequestId(Long technicalRequestId) {
        this.technicalRequestId = technicalRequestId;
    }

    public String getDataLocationPath() {
        return dataLocationPath;
    }

    public TechnicalDetails dataLocationPath(String dataLocationPath) {
        this.dataLocationPath = dataLocationPath;
        return this;
    }

    public void setDataLocationPath(String dataLocationPath) {
        this.dataLocationPath = dataLocationPath;
    }

    public String getDataRefreshFrequency() {
        return dataRefreshFrequency;
    }

    public TechnicalDetails dataRefreshFrequency(String dataRefreshFrequency) {
        this.dataRefreshFrequency = dataRefreshFrequency;
        return this;
    }

    public void setDataRefreshFrequency(String dataRefreshFrequency) {
        this.dataRefreshFrequency = dataRefreshFrequency;
    }

    public LocalDate getTargetIngestionStartDate() {
        return targetIngestionStartDate;
    }

    public TechnicalDetails targetIngestionStartDate(LocalDate targetIngestionStartDate) {
        this.targetIngestionStartDate = targetIngestionStartDate;
        return this;
    }

    public void setTargetIngestionStartDate(LocalDate targetIngestionStartDate) {
        this.targetIngestionStartDate = targetIngestionStartDate;
    }

    public LocalDate getTargetIngestionEndDate() {
        return targetIngestionEndDate;
    }

    public TechnicalDetails targetIngestionEndDate(LocalDate targetIngestionEndDate) {
        this.targetIngestionEndDate = targetIngestionEndDate;
        return this;
    }

    public void setTargetIngestionEndDate(LocalDate targetIngestionEndDate) {
        this.targetIngestionEndDate = targetIngestionEndDate;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public TechnicalDetails targetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getDatasetTypeIngestionRef() {
        return datasetTypeIngestionRef;
    }

    public TechnicalDetails datasetTypeIngestionRef(String datasetTypeIngestionRef) {
        this.datasetTypeIngestionRef = datasetTypeIngestionRef;
        return this;
    }

    public void setDatasetTypeIngestionRef(String datasetTypeIngestionRef) {
        this.datasetTypeIngestionRef = datasetTypeIngestionRef;
    }

    public String getGuestUsersEmail() {
        return guestUsersEmail;
    }

    public TechnicalDetails guestUsersEmail(String guestUsersEmail) {
        this.guestUsersEmail = guestUsersEmail;
        return this;
    }

    public void setGuestUsersEmail(String guestUsersEmail) {
        this.guestUsersEmail = guestUsersEmail;
    }

    public String getWhitelistIpAddresses() {
        return whitelistIpAddresses;
    }

    public TechnicalDetails whitelistIpAddresses(String whitelistIpAddresses) {
        this.whitelistIpAddresses = whitelistIpAddresses;
        return this;
    }

    public void setWhitelistIpAddresses(String whitelistIpAddresses) {
        this.whitelistIpAddresses = whitelistIpAddresses;
    }

    public String getExternalStagingContainerName() {
        return externalStagingContainerName;
    }

    public TechnicalDetails externalStagingContainerName(String externalStagingContainerName) {
        this.externalStagingContainerName = externalStagingContainerName;
        return this;
    }

    public void setExternalStagingContainerName(String externalStagingContainerName) {
        this.externalStagingContainerName = externalStagingContainerName;
    }

    public String getExternalDataSourceLocation() {
        return externalDataSourceLocation;
    }

    public TechnicalDetails externalDataSourceLocation(String externalDataSourceLocation) {
        this.externalDataSourceLocation = externalDataSourceLocation;
        return this;
    }

    public void setExternalDataSourceLocation(String externalDataSourceLocation) {
        this.externalDataSourceLocation = externalDataSourceLocation;
    }

    public String getGskAccessSourceLocationRef() {
        return gskAccessSourceLocationRef;
    }

    public TechnicalDetails gskAccessSourceLocationRef(String gskAccessSourceLocationRef) {
        this.gskAccessSourceLocationRef = gskAccessSourceLocationRef;
        return this;
    }

    public void setGskAccessSourceLocationRef(String gskAccessSourceLocationRef) {
        this.gskAccessSourceLocationRef = gskAccessSourceLocationRef;
    }

    public String getExternalSourceSecretKeyName() {
        return externalSourceSecretKeyName;
    }

    public TechnicalDetails externalSourceSecretKeyName(String externalSourceSecretKeyName) {
        this.externalSourceSecretKeyName = externalSourceSecretKeyName;
        return this;
    }

    public void setExternalSourceSecretKeyName(String externalSourceSecretKeyName) {
        this.externalSourceSecretKeyName = externalSourceSecretKeyName;
    }

    public String getDomainRequestId() {
        return domainRequestId;
    }

    public TechnicalDetails domainRequestId(String domainRequestId) {
        this.domainRequestId = domainRequestId;
        return this;
    }

    public void setDomainRequestId(String domainRequestId) {
        this.domainRequestId = domainRequestId;
    }

    public String getExistingDataLocationIdentified() {
        return existingDataLocationIdentified;
    }

    public TechnicalDetails existingDataLocationIdentified(String existingDataLocationIdentified) {
        this.existingDataLocationIdentified = existingDataLocationIdentified;
        return this;
    }

    public void setExistingDataLocationIdentified(String existingDataLocationIdentified) {
        this.existingDataLocationIdentified = existingDataLocationIdentified;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TechnicalDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TechnicalDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public TechnicalDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public TechnicalDetails modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public IngestionRequestDetails getIngestionRequestId() {
        return ingestionRequestId;
    }

    public TechnicalDetails ingestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
        return this;
    }

    public void setIngestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnicalDetails)) {
            return false;
        }
        return id != null && id.equals(((TechnicalDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "TechnicalDetails{" +
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
            "}"
        );
    }
}
