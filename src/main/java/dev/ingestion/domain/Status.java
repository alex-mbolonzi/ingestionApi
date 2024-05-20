package dev.ingestion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @NotNull
    @Size(max = 255)
    @Column(name = "status_code", length = 255, nullable = false, unique = true)
    private String statusCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "status_name", length = 255, nullable = false)
    private String statusName;

    @OneToMany(mappedBy = "statusId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RequestStatusDetails> requestStatusDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusId() {
        return statusId;
    }

    public Status statusId(Long statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Status statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public Status statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<RequestStatusDetails> getRequestStatusDetails() {
        return requestStatusDetails;
    }

    public Status requestStatusDetails(Set<RequestStatusDetails> requestStatusDetails) {
        this.requestStatusDetails = requestStatusDetails;
        return this;
    }

    public Status addRequestStatusDetails(RequestStatusDetails requestStatusDetails) {
        this.requestStatusDetails.add(requestStatusDetails);
        requestStatusDetails.setStatusId(this);
        return this;
    }

    public Status removeRequestStatusDetails(RequestStatusDetails requestStatusDetails) {
        this.requestStatusDetails.remove(requestStatusDetails);
        requestStatusDetails.setStatusId(null);
        return this;
    }

    public void setRequestStatusDetails(Set<RequestStatusDetails> requestStatusDetails) {
        this.requestStatusDetails = requestStatusDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "Status{" +
            "id=" +
            getId() +
            ", statusId=" +
            getStatusId() +
            ", statusCode='" +
            getStatusCode() +
            "'" +
            ", statusName='" +
            getStatusName() +
            "'" +
            "}"
        );
    }
}
