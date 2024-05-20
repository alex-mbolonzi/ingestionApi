package dev.ingestion.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetDetails.
 */
@Entity
@Table(name = "dataset_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_id", nullable = false)
    private Long datasetId;

    @Size(max = 255)
    @Column(name = "dataset_name", length = 255)
    private String datasetName;

    @Size(max = 255)
    @Column(name = "dataset_origin_source", length = 255)
    private String datasetOriginSource;

    @Size(max = 255)
    @Column(name = "current_data_location_ref", length = 255)
    private String currentDataLocationRef;

    @Column(name = "meteor_space_domino_usage_flag")
    private Boolean meteorSpaceDominoUsageFlag;

    @Column(name = "ihd_flag")
    private Boolean ihdFlag;

    @Size(max = 255)
    @Column(name = "dataset_required_for_ref", length = 255)
    private String datasetRequiredForRef;

    @Size(max = 255)
    @Column(name = "estimated_data_volume_ref", length = 255)
    private String estimatedDataVolumeRef;

    @Column(name = "analysis_init_dt")
    private LocalDate analysisInitDt;

    @Column(name = "analysis_end_dt")
    private LocalDate analysisEndDt;

    @Column(name = "dta_contract_complete_flag")
    private Boolean dtaContractCompleteFlag;

    @Column(name = "dta_expected_completion_date")
    private LocalDate dtaExpectedCompletionDate;

    @Size(max = 255)
    @Column(name = "dataset_type_ref", length = 255)
    private String datasetTypeRef;

    @Size(max = 255)
    @Column(name = "information_classification_type_ref", length = 255)
    private String informationClassificationTypeRef;

    @Size(max = 255)
    @Column(name = "pii_type_ref", length = 255)
    private String piiTypeRef;

    @Size(max = 255)
    @Column(name = "retention_rules", length = 255)
    private String retentionRules;

    @Column(name = "retention_rules_contract_date")
    private LocalDate retentionRulesContractDate;

    @Size(max = 255)
    @Column(name = "contract_partner", length = 255)
    private String contractPartner;

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

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetDataCategory> datasetDataCategories = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetIndication> datasetIndications = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetRoleDetails> datasetRoleDetails = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetStudy> datasetStudies = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetTechAndAssay> datasetTechAndAssays = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetTherapy> datasetTherapies = new HashSet<>();

    @OneToMany(mappedBy = "datasetId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasetUserUsageRestriction> datasetUserUsageRestrictions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public DatasetDetails datasetId(Long datasetId) {
        this.datasetId = datasetId;
        return this;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public DatasetDetails datasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDatasetOriginSource() {
        return datasetOriginSource;
    }

    public DatasetDetails datasetOriginSource(String datasetOriginSource) {
        this.datasetOriginSource = datasetOriginSource;
        return this;
    }

    public void setDatasetOriginSource(String datasetOriginSource) {
        this.datasetOriginSource = datasetOriginSource;
    }

    public String getCurrentDataLocationRef() {
        return currentDataLocationRef;
    }

    public DatasetDetails currentDataLocationRef(String currentDataLocationRef) {
        this.currentDataLocationRef = currentDataLocationRef;
        return this;
    }

    public void setCurrentDataLocationRef(String currentDataLocationRef) {
        this.currentDataLocationRef = currentDataLocationRef;
    }

    public Boolean isMeteorSpaceDominoUsageFlag() {
        return meteorSpaceDominoUsageFlag;
    }

    public DatasetDetails meteorSpaceDominoUsageFlag(Boolean meteorSpaceDominoUsageFlag) {
        this.meteorSpaceDominoUsageFlag = meteorSpaceDominoUsageFlag;
        return this;
    }

    public void setMeteorSpaceDominoUsageFlag(Boolean meteorSpaceDominoUsageFlag) {
        this.meteorSpaceDominoUsageFlag = meteorSpaceDominoUsageFlag;
    }

    public Boolean isIhdFlag() {
        return ihdFlag;
    }

    public DatasetDetails ihdFlag(Boolean ihdFlag) {
        this.ihdFlag = ihdFlag;
        return this;
    }

    public void setIhdFlag(Boolean ihdFlag) {
        this.ihdFlag = ihdFlag;
    }

    public String getDatasetRequiredForRef() {
        return datasetRequiredForRef;
    }

    public DatasetDetails datasetRequiredForRef(String datasetRequiredForRef) {
        this.datasetRequiredForRef = datasetRequiredForRef;
        return this;
    }

    public void setDatasetRequiredForRef(String datasetRequiredForRef) {
        this.datasetRequiredForRef = datasetRequiredForRef;
    }

    public String getEstimatedDataVolumeRef() {
        return estimatedDataVolumeRef;
    }

    public DatasetDetails estimatedDataVolumeRef(String estimatedDataVolumeRef) {
        this.estimatedDataVolumeRef = estimatedDataVolumeRef;
        return this;
    }

    public void setEstimatedDataVolumeRef(String estimatedDataVolumeRef) {
        this.estimatedDataVolumeRef = estimatedDataVolumeRef;
    }

    public LocalDate getAnalysisInitDt() {
        return analysisInitDt;
    }

    public DatasetDetails analysisInitDt(LocalDate analysisInitDt) {
        this.analysisInitDt = analysisInitDt;
        return this;
    }

    public void setAnalysisInitDt(LocalDate analysisInitDt) {
        this.analysisInitDt = analysisInitDt;
    }

    public LocalDate getAnalysisEndDt() {
        return analysisEndDt;
    }

    public DatasetDetails analysisEndDt(LocalDate analysisEndDt) {
        this.analysisEndDt = analysisEndDt;
        return this;
    }

    public void setAnalysisEndDt(LocalDate analysisEndDt) {
        this.analysisEndDt = analysisEndDt;
    }

    public Boolean isDtaContractCompleteFlag() {
        return dtaContractCompleteFlag;
    }

    public DatasetDetails dtaContractCompleteFlag(Boolean dtaContractCompleteFlag) {
        this.dtaContractCompleteFlag = dtaContractCompleteFlag;
        return this;
    }

    public void setDtaContractCompleteFlag(Boolean dtaContractCompleteFlag) {
        this.dtaContractCompleteFlag = dtaContractCompleteFlag;
    }

    public LocalDate getDtaExpectedCompletionDate() {
        return dtaExpectedCompletionDate;
    }

    public DatasetDetails dtaExpectedCompletionDate(LocalDate dtaExpectedCompletionDate) {
        this.dtaExpectedCompletionDate = dtaExpectedCompletionDate;
        return this;
    }

    public void setDtaExpectedCompletionDate(LocalDate dtaExpectedCompletionDate) {
        this.dtaExpectedCompletionDate = dtaExpectedCompletionDate;
    }

    public String getDatasetTypeRef() {
        return datasetTypeRef;
    }

    public DatasetDetails datasetTypeRef(String datasetTypeRef) {
        this.datasetTypeRef = datasetTypeRef;
        return this;
    }

    public void setDatasetTypeRef(String datasetTypeRef) {
        this.datasetTypeRef = datasetTypeRef;
    }

    public String getInformationClassificationTypeRef() {
        return informationClassificationTypeRef;
    }

    public DatasetDetails informationClassificationTypeRef(String informationClassificationTypeRef) {
        this.informationClassificationTypeRef = informationClassificationTypeRef;
        return this;
    }

    public void setInformationClassificationTypeRef(String informationClassificationTypeRef) {
        this.informationClassificationTypeRef = informationClassificationTypeRef;
    }

    public String getPiiTypeRef() {
        return piiTypeRef;
    }

    public DatasetDetails piiTypeRef(String piiTypeRef) {
        this.piiTypeRef = piiTypeRef;
        return this;
    }

    public void setPiiTypeRef(String piiTypeRef) {
        this.piiTypeRef = piiTypeRef;
    }

    public String getRetentionRules() {
        return retentionRules;
    }

    public DatasetDetails retentionRules(String retentionRules) {
        this.retentionRules = retentionRules;
        return this;
    }

    public void setRetentionRules(String retentionRules) {
        this.retentionRules = retentionRules;
    }

    public LocalDate getRetentionRulesContractDate() {
        return retentionRulesContractDate;
    }

    public DatasetDetails retentionRulesContractDate(LocalDate retentionRulesContractDate) {
        this.retentionRulesContractDate = retentionRulesContractDate;
        return this;
    }

    public void setRetentionRulesContractDate(LocalDate retentionRulesContractDate) {
        this.retentionRulesContractDate = retentionRulesContractDate;
    }

    public String getContractPartner() {
        return contractPartner;
    }

    public DatasetDetails contractPartner(String contractPartner) {
        this.contractPartner = contractPartner;
        return this;
    }

    public void setContractPartner(String contractPartner) {
        this.contractPartner = contractPartner;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DatasetDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DatasetDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DatasetDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public DatasetDetails modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public IngestionRequestDetails getIngestionRequestId() {
        return ingestionRequestId;
    }

    public DatasetDetails ingestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
        return this;
    }

    public void setIngestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
    }

    public Set<DatasetDataCategory> getDatasetDataCategories() {
        return datasetDataCategories;
    }

    public DatasetDetails datasetDataCategories(Set<DatasetDataCategory> datasetDataCategories) {
        this.datasetDataCategories = datasetDataCategories;
        return this;
    }

    public DatasetDetails addDatasetDataCategory(DatasetDataCategory datasetDataCategory) {
        this.datasetDataCategories.add(datasetDataCategory);
        datasetDataCategory.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetDataCategory(DatasetDataCategory datasetDataCategory) {
        this.datasetDataCategories.remove(datasetDataCategory);
        datasetDataCategory.setDatasetId(null);
        return this;
    }

    public void setDatasetDataCategories(Set<DatasetDataCategory> datasetDataCategories) {
        this.datasetDataCategories = datasetDataCategories;
    }

    public Set<DatasetIndication> getDatasetIndications() {
        return datasetIndications;
    }

    public DatasetDetails datasetIndications(Set<DatasetIndication> datasetIndications) {
        this.datasetIndications = datasetIndications;
        return this;
    }

    public DatasetDetails addDatasetIndication(DatasetIndication datasetIndication) {
        this.datasetIndications.add(datasetIndication);
        datasetIndication.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetIndication(DatasetIndication datasetIndication) {
        this.datasetIndications.remove(datasetIndication);
        datasetIndication.setDatasetId(null);
        return this;
    }

    public void setDatasetIndications(Set<DatasetIndication> datasetIndications) {
        this.datasetIndications = datasetIndications;
    }

    public Set<DatasetRoleDetails> getDatasetRoleDetails() {
        return datasetRoleDetails;
    }

    public DatasetDetails datasetRoleDetails(Set<DatasetRoleDetails> datasetRoleDetails) {
        this.datasetRoleDetails = datasetRoleDetails;
        return this;
    }

    public DatasetDetails addDatasetRoleDetails(DatasetRoleDetails datasetRoleDetails) {
        this.datasetRoleDetails.add(datasetRoleDetails);
        datasetRoleDetails.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetRoleDetails(DatasetRoleDetails datasetRoleDetails) {
        this.datasetRoleDetails.remove(datasetRoleDetails);
        datasetRoleDetails.setDatasetId(null);
        return this;
    }

    public void setDatasetRoleDetails(Set<DatasetRoleDetails> datasetRoleDetails) {
        this.datasetRoleDetails = datasetRoleDetails;
    }

    public Set<DatasetStudy> getDatasetStudies() {
        return datasetStudies;
    }

    public DatasetDetails datasetStudies(Set<DatasetStudy> datasetStudies) {
        this.datasetStudies = datasetStudies;
        return this;
    }

    public DatasetDetails addDatasetStudy(DatasetStudy datasetStudy) {
        this.datasetStudies.add(datasetStudy);
        datasetStudy.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetStudy(DatasetStudy datasetStudy) {
        this.datasetStudies.remove(datasetStudy);
        datasetStudy.setDatasetId(null);
        return this;
    }

    public void setDatasetStudies(Set<DatasetStudy> datasetStudies) {
        this.datasetStudies = datasetStudies;
    }

    public Set<DatasetTechAndAssay> getDatasetTechAndAssays() {
        return datasetTechAndAssays;
    }

    public DatasetDetails datasetTechAndAssays(Set<DatasetTechAndAssay> datasetTechAndAssays) {
        this.datasetTechAndAssays = datasetTechAndAssays;
        return this;
    }

    public DatasetDetails addDatasetTechAndAssay(DatasetTechAndAssay datasetTechAndAssay) {
        this.datasetTechAndAssays.add(datasetTechAndAssay);
        datasetTechAndAssay.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetTechAndAssay(DatasetTechAndAssay datasetTechAndAssay) {
        this.datasetTechAndAssays.remove(datasetTechAndAssay);
        datasetTechAndAssay.setDatasetId(null);
        return this;
    }

    public void setDatasetTechAndAssays(Set<DatasetTechAndAssay> datasetTechAndAssays) {
        this.datasetTechAndAssays = datasetTechAndAssays;
    }

    public Set<DatasetTherapy> getDatasetTherapies() {
        return datasetTherapies;
    }

    public DatasetDetails datasetTherapies(Set<DatasetTherapy> datasetTherapies) {
        this.datasetTherapies = datasetTherapies;
        return this;
    }

    public DatasetDetails addDatasetTherapy(DatasetTherapy datasetTherapy) {
        this.datasetTherapies.add(datasetTherapy);
        datasetTherapy.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetTherapy(DatasetTherapy datasetTherapy) {
        this.datasetTherapies.remove(datasetTherapy);
        datasetTherapy.setDatasetId(null);
        return this;
    }

    public void setDatasetTherapies(Set<DatasetTherapy> datasetTherapies) {
        this.datasetTherapies = datasetTherapies;
    }

    public Set<DatasetUserUsageRestriction> getDatasetUserUsageRestrictions() {
        return datasetUserUsageRestrictions;
    }

    public DatasetDetails datasetUserUsageRestrictions(Set<DatasetUserUsageRestriction> datasetUserUsageRestrictions) {
        this.datasetUserUsageRestrictions = datasetUserUsageRestrictions;
        return this;
    }

    public DatasetDetails addDatasetUserUsageRestriction(DatasetUserUsageRestriction datasetUserUsageRestriction) {
        this.datasetUserUsageRestrictions.add(datasetUserUsageRestriction);
        datasetUserUsageRestriction.setDatasetId(this);
        return this;
    }

    public DatasetDetails removeDatasetUserUsageRestriction(DatasetUserUsageRestriction datasetUserUsageRestriction) {
        this.datasetUserUsageRestrictions.remove(datasetUserUsageRestriction);
        datasetUserUsageRestriction.setDatasetId(null);
        return this;
    }

    public void setDatasetUserUsageRestrictions(Set<DatasetUserUsageRestriction> datasetUserUsageRestrictions) {
        this.datasetUserUsageRestrictions = datasetUserUsageRestrictions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatasetDetails)) {
            return false;
        }
        return id != null && id.equals(((DatasetDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetDetails{" +
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
            "}"
        );
    }
}
