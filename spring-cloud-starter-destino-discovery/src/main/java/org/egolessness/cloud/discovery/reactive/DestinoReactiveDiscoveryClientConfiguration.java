package org.egolessness.cloud.discovery.reactive;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.cloud.DestinoDiscoveryAutoConfiguration;
import org.egolessness.cloud.discovery.DestinoServiceDiscovery;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino reactive discovery client.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnReactiveDiscoveryEnabled
@ConditionalOnDestinoDiscoveryEnabled
@AutoConfigureAfter({ DestinoDiscoveryAutoConfiguration.class,
		ReactiveCompositeDiscoveryClientAutoConfiguration.class })
@AutoConfigureBefore({ ReactiveCommonsClientAutoConfiguration.class })
public class DestinoReactiveDiscoveryClientConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DestinoReactiveDiscoveryClient reactiveDiscoveryClient(DestinoServiceDiscovery destinoServiceDiscovery) {
		return new DestinoReactiveDiscoveryClient(destinoServiceDiscovery);
	}

}
