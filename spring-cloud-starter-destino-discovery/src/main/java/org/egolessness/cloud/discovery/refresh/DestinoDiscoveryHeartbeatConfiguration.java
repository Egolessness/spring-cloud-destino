
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

package org.egolessness.cloud.discovery.refresh;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.cloud.DestinoDiscoveryAutoConfiguration;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino discovery heartbeat.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnDestinoDiscoveryEnabled
@AutoConfigureAfter(value = DestinoDiscoveryAutoConfiguration.class,
		name = "de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration")
public class DestinoDiscoveryHeartbeatConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@Conditional(DestinoDiscoveryHeartBeatCondition.class)
	public DestinoDiscoveryHeartbeatRefresher destinoDiscoveryHeartbeatRefresher(DestinoDiscoveryProperties discoveryProperties) {
		return new DestinoDiscoveryHeartbeatRefresher(discoveryProperties);
	}

	private static class DestinoDiscoveryHeartBeatCondition extends AnyNestedCondition {

		DestinoDiscoveryHeartBeatCondition()  {
			super(ConfigurationPhase.REGISTER_BEAN);
		}

		@ConditionalOnProperty(value = "spring.cloud.gateway.discovery.locator.enabled")
		static class GatewayLocatorHeartBeatEnabled { }

		@ConditionalOnBean(type = "de.codecentric.boot.admin.server.cloud.discovery.InstanceDiscoveryListener")
		static class SpringBootAdminHeartBeatEnabled { }

		@ConditionalOnProperty(value = "spring.cloud.destino.discovery.heartbeat.enabled")
		static class DestinoDiscoveryHeartBeatEnabled { }

	}

}
