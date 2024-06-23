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

import org.egolessness.cloud.ConditionalOnDestinoSchedulingEnabled;
import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
import org.egolessness.cloud.properties.DestinoSchedulingExtPropertiesCompleter;
import org.egolessness.cloud.scheduling.DestinoRegistrationSchedulingAutoConfiguration;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.egolessness.cloud.properties.DestinoSchedulingExtProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfiguration for Destino scheduling registry.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
@ConditionalOnMissingClass("org.egolessness.cloud.registry.DestinoServiceRegistryAutoConfiguration")
@AutoConfigureAfter(DestinoRegistrationSchedulingAutoConfiguration.class)
public class DestinoSchedulingRegistryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingExtPropertiesCompleter destinoSchedulingExtPropertiesCompleter(
			DestinoSchedulingExtProperties extProperties) {
		return new DestinoSchedulingExtPropertiesCompleter(extProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public InetIPv6Utils inetIPv6Utils(InetUtilsProperties properties) {
		return new InetIPv6Utils(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingRegistration destinoSchedulingRegistration(DestinoSchedulingExtProperties extProperties,
																	   ApplicationContext context, InetUtils inetUtils,
																	   InetIPv6Utils inetIPv6Utils,
																	   List<DestinoRegistrationCustomizer> registrationCustomizers,
																	   ObjectProvider<ServletWebServerApplicationContext> servletContextProvider,
																	   ObjectProvider<ReactiveWebServerApplicationContext> reactiveContextProvider) {
		return new DestinoSchedulingRegistration(extProperties, context, inetUtils, inetIPv6Utils, registrationCustomizers,
				servletContextProvider, reactiveContextProvider);
	}

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingAutoRegister destinoSchedulingAutoRegister(DestinoSchedulingRegistration schedulingRegistration,
																	  DestinoConfiguration destinoConfiguration) {
		return new DestinoSchedulingAutoRegister(schedulingRegistration, destinoConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingAutoDeregister destinoSchedulingAutoDeregister(DestinoSchedulingRegistration schedulingRegistration,
																	   DestinoConfiguration destinoConfiguration) {
		return new DestinoSchedulingAutoDeregister(schedulingRegistration, destinoConfiguration);
	}

}
