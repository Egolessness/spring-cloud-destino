package org.egolessness.cloud;

import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.cloud.context.DestinoAutoConfiguration;
import org.egolessness.cloud.properties.DestinoSchedulingExtProperties;
import org.egolessness.cloud.properties.DestinoSchedulingProperties;
import org.egolessness.cloud.properties.DestinoSchedulingPropertiesCompleter;
import org.egolessness.cloud.scheduling.DestinoSchedulingJobScanner;
import org.egolessness.destino.client.DestinoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino scheduling.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
@EnableConfigurationProperties(DestinoSchedulingExtProperties.class)
@AutoConfigureAfter(DestinoAutoConfiguration.class)
public class DestinoSchedulingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DestinoSchedulingPropertiesCompleter destinoSchedulingPropertiesCompleter(DestinoSchedulingExtProperties properties) {
        return new DestinoSchedulingPropertiesCompleter(properties);
    }

}
