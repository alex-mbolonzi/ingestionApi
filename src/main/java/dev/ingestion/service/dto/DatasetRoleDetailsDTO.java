package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.DatasetRoleDetails} entity.
 */
public class DatasetRoleDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long datasetRoleDetailsId;

    @NotNull
    @Size(max = 45)
    private String role;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String mudid;

    @NotNull
    @Size(max = 255)
    private String name;

    private Long datasetIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetRoleDetailsId() {
        return datasetRoleDetailsId;
    }

    public void setDatasetRoleDetailsId(Long datasetRoleDetailsId) {
        this.datasetRoleDetailsId = datasetRoleDetailsId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMudid() {
        return mudid;
    }

    public void setMudid(String mudid) {
        this.mudid = mudid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDatasetIdId() {
        return datasetIdId;
    }

    public void setDatasetIdId(Long datasetDetailsId) {
        this.datasetIdId = datasetDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatasetRoleDetailsDTO datasetRoleDetailsDTO = (DatasetRoleDetailsDTO) o;
        if (datasetRoleDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datasetRoleDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "DatasetRoleDetailsDTO{" +
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
            ", datasetId=" +
            getDatasetIdId() +
            "}"
        );
    }
}
