package dev.ingestion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.ingestion.domain.EmailTemplate} entity.
 */
public class EmailTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private Long templateId;

    @NotNull
    @Size(max = 255)
    private String templateCode;

    @NotNull
    @Size(max = 255)
    private String subject;

    @NotNull
    @Size(max = 1024)
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailTemplateDTO emailTemplateDTO = (EmailTemplateDTO) o;
        if (emailTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "EmailTemplateDTO{" +
            "id=" +
            getId() +
            ", templateId=" +
            getTemplateId() +
            ", templateCode='" +
            getTemplateCode() +
            "'" +
            ", subject='" +
            getSubject() +
            "'" +
            ", body='" +
            getBody() +
            "'" +
            "}"
        );
    }
}
