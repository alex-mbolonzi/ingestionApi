package dev.ingestion.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, dev.ingestion.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, dev.ingestion.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, dev.ingestion.domain.User.class.getName());
            createCache(cm, dev.ingestion.domain.Authority.class.getName());
            createCache(cm, dev.ingestion.domain.User.class.getName() + ".authorities");
            createCache(cm, dev.ingestion.domain.ApplicationReferenceTable.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetDataCategory.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetDataCategories");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetIndications");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetRoleDetails");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetStudies");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetTechAndAssays");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetTherapies");
            createCache(cm, dev.ingestion.domain.DatasetDetails.class.getName() + ".datasetUserUsageRestrictions");
            createCache(cm, dev.ingestion.domain.DatasetIndication.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetRoleDetails.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetStudy.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetTechAndAssay.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetTherapy.class.getName());
            createCache(cm, dev.ingestion.domain.DatasetUserUsageRestriction.class.getName());
            createCache(cm, dev.ingestion.domain.EmailTemplate.class.getName());
            createCache(cm, dev.ingestion.domain.IngestionRequestDetails.class.getName());
            createCache(cm, dev.ingestion.domain.IngestionRequestDetails.class.getName() + ".requestStatusDetails");
            createCache(cm, dev.ingestion.domain.IngestionRequestDetails.class.getName() + ".validationNotes");
            createCache(cm, dev.ingestion.domain.RequestStatusDetails.class.getName());
            createCache(cm, dev.ingestion.domain.Status.class.getName());
            createCache(cm, dev.ingestion.domain.Status.class.getName() + ".requestStatusDetails");
            createCache(cm, dev.ingestion.domain.TechnicalDetails.class.getName());
            createCache(cm, dev.ingestion.domain.ValidationNotes.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
