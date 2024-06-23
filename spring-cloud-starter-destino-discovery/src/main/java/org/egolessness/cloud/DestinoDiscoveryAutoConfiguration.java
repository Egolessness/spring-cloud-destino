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

package org.egolessness.cloud;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.cloud.context.DestinoAutoConfiguration;
import org.egolessness.cloud.discovery.DestinoServiceDiscovery;
import org.egolessness.cloud.discovery.UtilIPv6AutoConfiguration;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.egolessness.cloud.properties.DestinoDiscoveryPropertiesCompleter;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for destino discovery.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoDiscoveryEnabled
@EnableConfigurationProperties(DestinoDiscoveryProperties.class)
@AutoConfigureAfter({DestinoAutoConfiguration.class, UtilIPv6AutoConfiguration.class})
public class DestinoDiscoveryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DestinoServiceDiscovery destinoServiceDiscovery(DestinoDiscoveryProperties discoveryProperties,
                                                           DestinoConfiguration destinoConfiguration) {
        return new DestinoServiceDiscovery(discoveryProperties, destinoConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean
    public DestinoDiscoveryContext destinoDiscoveryContext(DestinoDiscoveryProperties discoveryProperties,
                                                           ApplicationContext context, InetUtils inetUtils,
                                                           InetIPv6Utils inetIPv6Utils) {
        return new DestinoDiscoveryContext(discoveryProperties, context, inetUtils, inetIPv6Utils);
    }

    @Bean
    @ConditionalOnMissingBean
    public DestinoDiscoveryPropertiesCompleter destinoDiscoveryPropertiesCompleter(DestinoDiscoveryProperties discoveryProperties) {
        return new DestinoDiscoveryPropertiesCompleter(discoveryProperties);
    }

}
