package org.egolessness.cloud.endpoint;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Endpoints autoconfiguration for destino discovery.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Endpoint.class)
@ConditionalOnDestinoDiscoveryEnabled
public class DestinoDiscoveryEndpointAutoConfiguration {

	@Bean
	@ConditionalOnEnabledHealthIndicator("destino-discovery")
	public DestinoHealthIndicator destinoHealthIndicator(DestinoConfiguration destinoConfiguration) {
		return new DestinoHealthIndicator(destinoConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnAvailableEndpoint
	public DestinoDiscoveryEndpoint destinoDiscoveryEndpoint(DestinoConfiguration destinoConfiguration,
														   DestinoDiscoveryProperties discoveryProperties) {
		return new DestinoDiscoveryEndpoint(destinoConfiguration, discoveryProperties);
	}

}
