package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetDetails} entity.
 */
public class DatasetDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetId;

    @Size(max = 255)
    private String datasetName;

    @Size(max = 255)
    private String datasetOriginSource;

    @Size(max = 255)
    private String currentDataLocationRef;

    private Boolean meteorSpaceDominoUsageFlag;

    private Boolean ihdFlag;

    @Size(max = 255)
    private String datasetRequiredForRef;

    @Size(max = 255)
    private String estimatedDataVolumeRef;

    private LocalDate analysisInitDt;

    private LocalDate analysisEndDt;

    private Boolean dtaContractCompleteFlag;

    private LocalDate dtaExpectedCompletionDate;

    @Size(max = 255)
    private String datasetTypeRef;

    @Size(max = 255)
    private String informationClassificationTypeRef;

    @Size(max = 255)
    private String piiTypeRef;

    @Size(max = 255)
    private String retentionRules;

    private LocalDate retentionRulesContractDate;

    @Size(max = 255)
    private String contractPartner;

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

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDatasetOriginSource() {
        return datasetOriginSource;
    }

    public void setDatasetOriginSource(String datasetOriginSource) {
        this.datasetOriginSource = datasetOriginSource;
    }

    public String getCurrentDataLocationRef() {
        return currentDataLocationRef;
    }

    public void setCurrentDataLocationRef(String currentDataLocationRef) {
        this.currentDataLocationRef = currentDataLocationRef;
    }

    public Boolean isMeteorSpaceDominoUsageFlag() {
        return meteorSpaceDominoUsageFlag;
    }

    public void setMeteorSpaceDominoUsageFlag(Boolean meteorSpaceDominoUsageFlag) {
        this.meteorSpaceDominoUsageFlag = meteorSpaceDominoUsageFlag;
    }

    public Boolean isIhdFlag() {
        return ihdFlag;
    }

    public void setIhdFlag(Boolean ihdFlag) {
        this.ihdFlag = ihdFlag;
    }

    public String getDatasetRequiredForRef() {
        return datasetRequiredForRef;
    }

    public void setDatasetRequiredForRef(String datasetRequiredForRef) {
        this.datasetRequiredForRef = datasetRequiredForRef;
    }

    public String getEstimatedDataVolumeRef() {
        return estimatedDataVolumeRef;
    }

    public void setEstimatedDataVolumeRef(String estimatedDataVolumeRef) {
        this.estimatedDataVolumeRef = estimatedDataVolumeRef;
    }

    public LocalDate getAnalysisInitDt() {
        return analysisInitDt;
    }

    public void setAnalysisInitDt(LocalDate analysisInitDt) {
        this.analysisInitDt = analysisInitDt;
    }

    public LocalDate getAnalysisEndDt() {
        return analysisEndDt;
    }

    public void setAnalysisEndDt(LocalDate analysisEndDt) {
        this.analysisEndDt = analysisEndDt;
    }

    public Boolean isDtaContractCompleteFlag() {
        return dtaContractCompleteFlag;
    }

    public void setDtaContractCompleteFlag(Boolean dtaContractCompleteFlag) {
        this.dtaContractCompleteFlag = dtaContractCompleteFlag;
    }

    public LocalDate getDtaExpectedCompletionDate() {
        return dtaExpectedCompletionDate;
    }

    public void setDtaExpectedCompletionDate(LocalDate dtaExpectedCompletionDate) {
        this.dtaExpectedCompletionDate = dtaExpectedCompletionDate;
    }

    public String getDatasetTypeRef() {
        return datasetTypeRef;
    }

    public void setDatasetTypeRef(String datasetTypeRef) {
        this.datasetTypeRef = datasetTypeRef;
    }

    public String getInformationClassificationTypeRef() {
        return informationClassificationTypeRef;
    }

    public void setInformationClassificationTypeRef(String informationClassificationTypeRef) {
        this.informationClassificationTypeRef = informationClassificationTypeRef;
    }

    public String getPiiTypeRef() {
        return piiTypeRef;
    }

    public void setPiiTypeRef(String piiTypeRef) {
        this.piiTypeRef = piiTypeRef;
    }

    public String getRetentionRules() {
        return retentionRules;
    }

    public void setRetentionRules(String retentionRules) {
        this.retentionRules = retentionRules;
    }

    public LocalDate getRetentionRulesContractDate() {
        return retentionRulesContractDate;
    }

    public void setRetentionRulesContractDate(LocalDate retentionRulesContractDate) {
        this.retentionRulesContractDate = retentionRulesContractDate;
    }

    public String getContractPartner() {
        return contractPartner;
    }

    public void setContractPartner(String contractPartner) {
        this.contractPartner = contractPartner;
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

        DatasetDetailsDTO datasetDetailsDTO = (DatasetDetailsDTO) o;
        if (datasetDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetDetailsDTO{" +
            "id=" +
            getId() +
            ", datasetId=" +
            getDatasetId() +
            ", datasetName='" +
            getDatasetName() +
            "'" +
            ", datasetOriginSource='" +
            getDatasetOriginSource() +
            "'" +
            ", currentDataLocationRef='" +
            getCurrentDataLocationRef() +
            "'" +
            ", meteorSpaceDominoUsageFlag='" +
            isMeteorSpaceDominoUsageFlag() +
            "'" +
            ", ihdFlag='" +
            isIhdFlag() +
            "'" +
            ", datasetRequiredForRef='" +
            getDatasetRequiredForRef() +
            "'" +
            ", estimatedDataVolumeRef='" +
            getEstimatedDataVolumeRef() +
            "'" +
            ", analysisInitDt='" +
            getAnalysisInitDt() +
            "'" +
            ", analysisEndDt='" +
            getAnalysisEndDt() +
            "'" +
            ", dtaContractCompleteFlag='" +
            isDtaContractCompleteFlag() +
            "'" +
            ", dtaExpectedCompletionDate='" +
            getDtaExpectedCompletionDate() +
            "'" +
            ", datasetTypeRef='" +
            getDatasetTypeRef() +
            "'" +
            ", informationClassificationTypeRef='" +
            getInformationClassificationTypeRef() +
            "'" +
            ", piiTypeRef='" +
            getPiiTypeRef() +
            "'" +
            ", retentionRules='" +
            getRetentionRules() +
            "'" +
            ", retentionRulesContractDate='" +
            getRetentionRulesContractDate() +
            "'" +
            ", contractPartner='" +
            getContractPartner() +
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
