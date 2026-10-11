package com.unirateapi.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Configuration for the UniRate Spring Boot starter.
 *
 * <p>Bind under the {@code unirate.*} prefix in {@code application.properties}
 * or {@code application.yml}:</p>
 *
 * <pre>{@code
 * unirate:
 *   api-key: your-api-key      # required; get one free at https://unirateapi.com
 *   base-url: https://api.unirateapi.com
 *   timeout: 30s
 * }</pre>
 */
@ConfigurationProperties(prefix = "unirate")
public class UniRateProperties {

    /** Default base URL for the UniRate API. */
    public static final String DEFAULT_BASE_URL = "https://api.unirateapi.com";

    /** Default request timeout. */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    /**
     * API key for the UniRate API. Required — the auto-configured client bean
     * is only created when this is set. Obtain a free key at
     * <a href="https://unirateapi.com">unirateapi.com</a>.
     */
    private String apiKey;

    /** Base URL of the UniRate API. Rarely changed; useful for mocking/testing. */
    private String baseUrl = DEFAULT_BASE_URL;

    /** Per-request timeout. Accepts ISO-8601 or Spring duration syntax (e.g. {@code 30s}, {@code 10000ms}). */
    private Duration timeout = DEFAULT_TIMEOUT;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}
