package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RequestStatusDetails.
 */
@Entity
@Table(name = "request_status_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RequestStatusDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "request_status_id", nullable = false)
    private Long requestStatusId;

    @NotNull
    @Size(max = 255)
    @Column(name = "decision_by_name", length = 255, nullable = false)
    private String decisionByName;

    @NotNull
    @Size(max = 255)
    @Column(name = "decision_by_mudid", length = 255, nullable = false)
    private String decisionByMudid;

    @NotNull
    @Size(max = 255)
    @Column(name = "decision_by_email", length = 255, nullable = false)
    private String decisionByEmail;

    @NotNull
    @Column(name = "decision_date", nullable = false)
    private Instant decisionDate;

    @Size(max = 255)
    @Column(name = "decision_comments", length = 255)
    private String decisionComments;

    @Size(max = 255)
    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    @NotNull
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

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
    @JsonIgnoreProperties("requestStatusDetails")
    private IngestionRequestDetails ingestionRequestId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("requestStatusDetails")
    private Status statusId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestStatusId() {
        return requestStatusId;
    }

    public RequestStatusDetails requestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
        return this;
    }

    public void setRequestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public String getDecisionByName() {
        return decisionByName;
    }

    public RequestStatusDetails decisionByName(String decisionByName) {
        this.decisionByName = decisionByName;
        return this;
    }

    public void setDecisionByName(String decisionByName) {
        this.decisionByName = decisionByName;
    }

    public String getDecisionByMudid() {
        return decisionByMudid;
    }

    public RequestStatusDetails decisionByMudid(String decisionByMudid) {
        this.decisionByMudid = decisionByMudid;
        return this;
    }

    public void setDecisionByMudid(String decisionByMudid) {
        this.decisionByMudid = decisionByMudid;
    }

    public String getDecisionByEmail() {
        return decisionByEmail;
    }

    public RequestStatusDetails decisionByEmail(String decisionByEmail) {
        this.decisionByEmail = decisionByEmail;
        return this;
    }

    public void setDecisionByEmail(String decisionByEmail) {
        this.decisionByEmail = decisionByEmail;
    }

    public Instant getDecisionDate() {
        return decisionDate;
    }

    public RequestStatusDetails decisionDate(Instant decisionDate) {
        this.decisionDate = decisionDate;
        return this;
    }

    public void setDecisionDate(Instant decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getDecisionComments() {
        return decisionComments;
    }

    public RequestStatusDetails decisionComments(String decisionComments) {
        this.decisionComments = decisionComments;
        return this;
    }

    public void setDecisionComments(String decisionComments) {
        this.decisionComments = decisionComments;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public RequestStatusDetails rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public RequestStatusDetails activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RequestStatusDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public RequestStatusDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public RequestStatusDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public RequestStatusDetails modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public IngestionRequestDetails getIngestionRequestId() {
        return ingestionRequestId;
    }

    public RequestStatusDetails ingestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
        return this;
    }

    public void setIngestionRequestId(IngestionRequestDetails ingestionRequestDetails) {
        this.ingestionRequestId = ingestionRequestDetails;
    }

    public Status getStatusId() {
        return statusId;
    }

    public RequestStatusDetails statusId(Status status) {
        this.statusId = status;
        return this;
    }

    public void setStatusId(Status status) {
        this.statusId = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestStatusDetails)) {
            return false;
        }
        return id != null && id.equals(((RequestStatusDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "RequestStatusDetails{" +
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
            "}"
        );
    }
}
