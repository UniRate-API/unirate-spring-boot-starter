package com.unirateapi.spring;

import com.unirateapi.client.UniRateClient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Context-wiring tests for {@link UniRateAutoConfiguration}. All run fully
 * offline via {@link ApplicationContextRunner} — the {@link UniRateClient}
 * constructor only stores configuration, it never performs a request.
 */
class UniRateAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(UniRateAutoConfiguration.class));

    @Test
    void noClientBeanWithoutApiKey() {
        runner.run(context -> assertThat(context).doesNotHaveBean(UniRateClient.class));
    }

    @Test
    void clientBeanCreatedWhenApiKeyPresent() {
        runner.withPropertyValues("unirate.api-key=test-key")
                .run(context -> assertThat(context).hasSingleBean(UniRateClient.class));
    }

    @Test
    void propertiesBindWithDefaults() {
        runner.withPropertyValues("unirate.api-key=test-key")
                .run(context -> {
                    assertThat(context).hasSingleBean(UniRateProperties.class);
                    UniRateProperties props = context.getBean(UniRateProperties.class);
                    assertThat(props.getApiKey()).isEqualTo("test-key");
                    assertThat(props.getBaseUrl()).isEqualTo("https://api.unirateapi.com");
                    assertThat(props.getTimeout()).isEqualTo(Duration.ofSeconds(30));
                });
    }

    @Test
    void propertiesBindCustomValues() {
        runner.withPropertyValues(
                        "unirate.api-key=test-key",
                        "unirate.base-url=https://mock.example.com",
                        "unirate.timeout=5s")
                .run(context -> {
                    UniRateProperties props = context.getBean(UniRateProperties.class);
                    assertThat(props.getBaseUrl()).isEqualTo("https://mock.example.com");
                    assertThat(props.getTimeout()).isEqualTo(Duration.ofSeconds(5));
                });
    }

    @Test
    void userDefinedClientBeanTakesPrecedence() {
        runner.withPropertyValues("unirate.api-key=test-key")
                .withUserConfiguration(CustomClientConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(UniRateClient.class);
                    assertThat(context.getBean(UniRateClient.class))
                            .isSameAs(context.getBean("customClient"));
                });
    }

    @Configuration(proxyBeanMethods = false)
    static class CustomClientConfig {
        @Bean
        UniRateClient customClient() {
            return new UniRateClient("user-supplied-key");
        }
    }
}
