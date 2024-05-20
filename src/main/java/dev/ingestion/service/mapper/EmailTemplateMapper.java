package dev.ingestion.service.mapper;

import dev.ingestion.domain.*;
import dev.ingestion.service.dto.EmailTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailTemplate} and its DTO {@link EmailTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailTemplateMapper extends EntityMapper<EmailTemplateDTO, EmailTemplate> {
    default EmailTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(id);
        return emailTemplate;
    }
}
