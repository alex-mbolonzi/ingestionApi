package dev.ingestion.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.ValidationNotes} entity.
 */
public class ValidationNotesDTO implements Serializable {

    private Long id;

    @NotNull
    private Long notesId;

    @NotNull
    @Size(max = 1024)
    private String notes;

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

    public Long getNotesId() {
        return notesId;
    }

    public void setNotesId(Long notesId) {
        this.notesId = notesId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

        ValidationNotesDTO validationNotesDTO = (ValidationNotesDTO) o;
        if (validationNotesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), validationNotesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "ValidationNotesDTO{" +
            "id=" +
            getId() +
            ", notesId=" +
            getNotesId() +
            ", notes='" +
            getNotes() +
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
