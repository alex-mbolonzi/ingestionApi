package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IngestionRequestDetails.
 */
@Entity
@Table(name = "ingestion_request_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IngestionRequestDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ingestion_request_id", nullable = false)
    private Long ingestionRequestId;

    @NotNull
    @Size(max = 255)
    @Column(name = "requester_name", length = 255, nullable = false)
    private String requesterName;

    @NotNull
    @Size(max = 255)
    @Column(name = "requester_mudid", length = 255, nullable = false)
    private String requesterMudid;

    @NotNull
    @Size(max = 255)
    @Column(name = "requester_email", length = 255, nullable = false)
    private String requesterEmail;

    @Size(max = 255)
    @Column(name = "request_rationale_reason", length = 255)
    private String requestRationaleReason;

    @NotNull
    @Size(max = 255)
    @Column(name = "requested_by_name", length = 255, nullable = false)
    private String requestedByName;

    @NotNull
    @Size(max = 255)
    @Column(name = "requested_by_mudid", length = 255, nullable = false)
    private String requestedByMudid;

    @NotNull
    @Size(max = 255)
    @Column(name = "requested_by_email", length = 255, nullable = false)
    private String requestedByEmail;

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

    @Size(max = 255)
    @Column(name = "modified_reason", length = 255)
    private String modifiedReason;

    @OneToOne(mappedBy = "ingestionRequestId")
    @JsonIgnore
    private DatasetDetails datasetDetails;

    @OneToOne(mappedBy = "ingestionRequestId")
    @JsonIgnore
    private TechnicalDetails technicalDetails;

    @OneToMany(mappedBy = "ingestionRequestId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RequestStatusDetails> requestStatusDetails = new HashSet<>();

    @OneToMany(mappedBy = "ingestionRequestId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ValidationNotes> validationNotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIngestionRequestId() {
        return ingestionRequestId;
    }

    public IngestionRequestDetails ingestionRequestId(Long ingestionRequestId) {
        this.ingestionRequestId = ingestionRequestId;
        return this;
    }

    public void setIngestionRequestId(Long ingestionRequestId) {
        this.ingestionRequestId = ingestionRequestId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public IngestionRequestDetails requesterName(String requesterName) {
        this.requesterName = requesterName;
        return this;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterMudid() {
        return requesterMudid;
    }

    public IngestionRequestDetails requesterMudid(String requesterMudid) {
        this.requesterMudid = requesterMudid;
        return this;
    }

    public void setRequesterMudid(String requesterMudid) {
        this.requesterMudid = requesterMudid;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public IngestionRequestDetails requesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
        return this;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequestRationaleReason() {
        return requestRationaleReason;
    }

    public IngestionRequestDetails requestRationaleReason(String requestRationaleReason) {
        this.requestRationaleReason = requestRationaleReason;
        return this;
    }

    public void setRequestRationaleReason(String requestRationaleReason) {
        this.requestRationaleReason = requestRationaleReason;
    }

    public String getRequestedByName() {
        return requestedByName;
    }

    public IngestionRequestDetails requestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
        return this;
    }

    public void setRequestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
    }

    public String getRequestedByMudid() {
        return requestedByMudid;
    }

    public IngestionRequestDetails requestedByMudid(String requestedByMudid) {
        this.requestedByMudid = requestedByMudid;
        return this;
    }

    public void setRequestedByMudid(String requestedByMudid) {
        this.requestedByMudid = requestedByMudid;
    }

    public String getRequestedByEmail() {
        return requestedByEmail;
    }

    public IngestionRequestDetails requestedByEmail(String requestedByEmail) {
        this.requestedByEmail = requestedByEmail;
        return this;
    }

    public void setRequestedByEmail(String requestedByEmail) {
        this.requestedByEmail = requestedByEmail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public IngestionRequestDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public IngestionRequestDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public IngestionRequestDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public IngestionRequestDetails modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedReason() {
        return modifiedReason;
    }

    public IngestionRequestDetails modifiedReason(String modifiedReason) {
        this.modifiedReason = modifiedReason;
        return this;
    }

    public void setModifiedReason(String modifiedReason) {
        this.modifiedReason = modifiedReason;
    }

    public DatasetDetails getDatasetDetails() {
        return datasetDetails;
    }

    public IngestionRequestDetails datasetDetails(DatasetDetails datasetDetails) {
        this.datasetDetails = datasetDetails;
        return this;
    }

    public void setDatasetDetails(DatasetDetails datasetDetails) {
        this.datasetDetails = datasetDetails;
    }

    public TechnicalDetails getTechnicalDetails() {
        return technicalDetails;
    }

    public IngestionRequestDetails technicalDetails(TechnicalDetails technicalDetails) {
        this.technicalDetails = technicalDetails;
        return this;
    }

    public void setTechnicalDetails(TechnicalDetails technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public Set<RequestStatusDetails> getRequestStatusDetails() {
        return requestStatusDetails;
    }

    public IngestionRequestDetails requestStatusDetails(Set<RequestStatusDetails> requestStatusDetails) {
        this.requestStatusDetails = requestStatusDetails;
        return this;
    }

    public IngestionRequestDetails addRequestStatusDetails(RequestStatusDetails requestStatusDetails) {
        this.requestStatusDetails.add(requestStatusDetails);
        requestStatusDetails.setIngestionRequestId(this);
        return this;
    }

    public IngestionRequestDetails removeRequestStatusDetails(RequestStatusDetails requestStatusDetails) {
        this.requestStatusDetails.remove(requestStatusDetails);
        requestStatusDetails.setIngestionRequestId(null);
        return this;
    }

    public void setRequestStatusDetails(Set<RequestStatusDetails> requestStatusDetails) {
        this.requestStatusDetails = requestStatusDetails;
    }

    public Set<ValidationNotes> getValidationNotes() {
        return validationNotes;
    }

    public IngestionRequestDetails validationNotes(Set<ValidationNotes> validationNotes) {
        this.validationNotes = validationNotes;
        return this;
    }

    public IngestionRequestDetails addValidationNotes(ValidationNotes validationNotes) {
        this.validationNotes.add(validationNotes);
        validationNotes.setIngestionRequestId(this);
        return this;
    }

    public IngestionRequestDetails removeValidationNotes(ValidationNotes validationNotes) {
        this.validationNotes.remove(validationNotes);
        validationNotes.setIngestionRequestId(null);
        return this;
    }

    public void setValidationNotes(Set<ValidationNotes> validationNotes) {
        this.validationNotes = validationNotes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngestionRequestDetails)) {
            return false;
        }
        return id != null && id.equals(((IngestionRequestDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "IngestionRequestDetails{" +
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
