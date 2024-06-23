/*
 * Copyright (c) 2024 by Kang Wang. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.egolessness.cloud.registry;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.cloud.DestinoDiscoveryAutoConfiguration;
import org.egolessness.cloud.DestinoDiscoveryContext;
import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
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
			List<DestinoRegistrationCustomizer> registrationCustomizers,
			ObjectProvider<ServletWebServerApplicationContext> servletWebServerApplicationContextProvider,
			ObjectProvider<ReactiveWebServerApplicationContext> reactiveWebServerApplicationContextProvider,
			DestinoDiscoveryContext discoveryContext
	) {
		ServletWebServerApplicationContext servletApplicationContext = servletWebServerApplicationContextProvider.getIfAvailable();
		ReactiveWebServerApplicationContext reactiveApplicationContext = reactiveWebServerApplicationContextProvider.getIfAvailable();
		return new DestinoRegistration(discoveryContext, registrationCustomizers, servletApplicationContext, reactiveApplicationContext);
	}

	@Bean
	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public DestinoAutoServiceRegistration destinoAutoServiceRegistration(DestinoServiceRegistry registry,
																		 AutoServiceRegistrationProperties autoServiceRegistrationProperties,
																		 DestinoRegistration registration) {
		return new DestinoAutoServiceRegistration(registry, autoServiceRegistrationProperties, registration);
	}

}
