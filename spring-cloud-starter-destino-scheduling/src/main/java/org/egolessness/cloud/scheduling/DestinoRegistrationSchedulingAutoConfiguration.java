package org.egolessness.cloud.scheduling;

import org.egolessness.cloud.ConditionalOnDestinoSchedulingEnabled;
import org.egolessness.cloud.DestinoSchedulingAutoConfiguration;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino registration scheduling.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
@AutoConfigureAfter(DestinoSchedulingAutoConfiguration.class)
public class DestinoRegistrationSchedulingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DestinoSchedulingJobScanner destinoSchedulingJobScanner(DestinoConfiguration configuration,
                                                                   ApplicationContext context) {
        return new DestinoSchedulingJobScanner(configuration, context);
    }

    @Bean
    @ConditionalOnMissingBean
    public DestinoRegistrationSchedulingCustomizer destinoRegistrationSchedulingCustomizer(DestinoSchedulingJobScanner scanner) {
        return new DestinoRegistrationSchedulingCustomizer(scanner);
    }

}
