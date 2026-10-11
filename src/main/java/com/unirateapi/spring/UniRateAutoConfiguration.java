package com.unirateapi.spring;

import com.unirateapi.client.UniRateClient;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration that exposes a ready-to-use {@link UniRateClient} bean.
 *
 * <p>The bean is created only when {@code unirate.api-key} is present, and only
 * when the application has not already defined its own {@link UniRateClient}
 * bean. This makes the starter safe to drop in: no key, no bean; custom bean,
 * yours wins.</p>
 *
 * @see UniRateProperties
 */
@AutoConfiguration
@EnableConfigurationProperties(UniRateProperties.class)
@ConditionalOnClass(UniRateClient.class)
public class UniRateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "unirate", name = "api-key")
    public UniRateClient uniRateClient(UniRateProperties properties) {
        return new UniRateClient(
                properties.getApiKey(),
                properties.getBaseUrl(),
                properties.getTimeout(),
                null);
    }
}
