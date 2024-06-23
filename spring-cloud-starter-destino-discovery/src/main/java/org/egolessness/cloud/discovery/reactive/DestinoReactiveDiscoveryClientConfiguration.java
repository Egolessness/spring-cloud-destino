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
	public DestinoReactiveDiscoveryClient destinoReactiveDiscoveryClient(DestinoServiceDiscovery destinoServiceDiscovery) {
		return new DestinoReactiveDiscoveryClient(destinoServiceDiscovery);
	}

}
