package dev.ingestion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DatasetRoleDetails.
 */
@Entity
@Table(name = "dataset_role_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatasetRoleDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dataset_role_details_id", nullable = false)
    private Long datasetRoleDetailsId;

    @NotNull
    @Size(max = 45)
    @Column(name = "role", length = 45, nullable = false)
    private String role;

    @NotNull
    @Size(max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "mudid", length = 255, nullable = false)
    private String mudid;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("datasetRoleDetails")
    private DatasetDetails datasetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetRoleDetailsId() {
        return datasetRoleDetailsId;
    }

    public DatasetRoleDetails datasetRoleDetailsId(Long datasetRoleDetailsId) {
        this.datasetRoleDetailsId = datasetRoleDetailsId;
        return this;
    }

    public void setDatasetRoleDetailsId(Long datasetRoleDetailsId) {
        this.datasetRoleDetailsId = datasetRoleDetailsId;
    }

    public String getRole() {
        return role;
    }

    public DatasetRoleDetails role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public DatasetRoleDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMudid() {
        return mudid;
    }

    public DatasetRoleDetails mudid(String mudid) {
        this.mudid = mudid;
        return this;
    }

    public void setMudid(String mudid) {
        this.mudid = mudid;
    }

    public String getName() {
        return name;
    }

    public DatasetRoleDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatasetDetails getDatasetId() {
        return datasetId;
    }

    public DatasetRoleDetails datasetId(DatasetDetails datasetDetails) {
        this.datasetId = datasetDetails;
        return this;
    }

    public void setDatasetId(DatasetDetails datasetDetails) {
        this.datasetId = datasetDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatasetRoleDetails)) {
            return false;
        }
        return id != null && id.equals(((DatasetRoleDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "DatasetRoleDetails{" +
            "id=" +
            getId() +
            ", datasetRoleDetailsId=" +
            getDatasetRoleDetailsId() +
            ", role='" +
            getRole() +
            "'" +
            ", email='" +
            getEmail() +
            "'" +
            ", mudid='" +
            getMudid() +
            "'" +
            ", name='" +
            getName() +
            "'" +
            "}"
        );
    }
}
