package org.egolessness.cloud.registry;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.cloud.DestinoDiscoveryAutoConfiguration;
import org.egolessness.cloud.DestinoDiscoveryContext;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.serviceregistry.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfiguration for destino service registry.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
@AutoConfigureBefore(ServiceRegistryAutoConfiguration.class)
@AutoConfigureAfter({DestinoDiscoveryAutoConfiguration.class, AutoServiceRegistrationAutoConfiguration.class})
public class DestinoServiceRegistryAutoConfiguration {

	@Bean
	public DestinoServiceRegistry destinoServiceRegistry(DestinoConfiguration destinoConfiguration,
														 DestinoDiscoveryProperties discoveryProperties) {
		return new DestinoServiceRegistry(destinoConfiguration, discoveryProperties);
	}

	@Bean
	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public DestinoRegistration destinoRegistration(
			ObjectProvider<List<DestinoRegistrationCustomizer>> registrationCustomizersProvider,
			ObjectProvider<ServletWebServerApplicationContext> servletWebServerApplicationContextProvider,
			ObjectProvider<ReactiveWebServerApplicationContext> reactiveWebServerApplicationContextProvider,
			DestinoDiscoveryContext discoveryContext
	) {
		List<DestinoRegistrationCustomizer> customizers = registrationCustomizersProvider.getIfAvailable();
		ServletWebServerApplicationContext servletApplicationContext = servletWebServerApplicationContextProvider.getIfAvailable();
		ReactiveWebServerApplicationContext reactiveApplicationContext = reactiveWebServerApplicationContextProvider.getIfAvailable();
		return new DestinoRegistration(discoveryContext, customizers, servletApplicationContext, reactiveApplicationContext);
	}

	@Bean
	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public DestinoAutoServiceRegistration destinoAutoServiceRegistration(
			DestinoServiceRegistry registry,
			AutoServiceRegistrationProperties autoServiceRegistrationProperties,
			DestinoRegistration registration) {
		return new DestinoAutoServiceRegistration(registry,
				autoServiceRegistrationProperties, registration);
	}

}
