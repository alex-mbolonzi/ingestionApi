package dev.ingestion.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmailTemplate.
 */
@Entity
@Table(name = "email_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @NotNull
    @Size(max = 255)
    @Column(name = "template_code", length = 255, nullable = false, unique = true)
    private String templateCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "subject", length = 255, nullable = false)
    private String subject;

    @NotNull
    @Size(max = 1024)
    @Column(name = "body", length = 1024, nullable = false)
    private String body;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public EmailTemplate templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public EmailTemplate templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSubject() {
        return subject;
    }

    public EmailTemplate subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public EmailTemplate body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTemplate)) {
            return false;
        }
        return id != null && id.equals(((EmailTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "EmailTemplate{" +
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
