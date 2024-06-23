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

package org.egolessness.cloud.properties;

import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.cloud.context.properties.DestinoPropertiesCompleter;
import org.egolessness.destino.client.properties.LoggingProperties;
import org.egolessness.destino.common.utils.PredicateUtils;

/**
 * Completer for Destino discovery.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoDiscoveryPropertiesCompleter implements DestinoPropertiesCompleter {

    private final DestinoDiscoveryProperties discoveryProperties;

    public DestinoDiscoveryPropertiesCompleter(DestinoDiscoveryProperties discoveryProperties) {
        this.discoveryProperties = discoveryProperties;
    }

    @Override
    public void complete(DestinoProperties properties) {
        if (PredicateUtils.isNotEmpty(discoveryProperties.getAddress())) {
            for (String address : discoveryProperties.getAddress()) {
                properties.addAddress(address);
            }
        }
        if (PredicateUtils.isBlank(properties.getAddressesProviderUrl())) {
            properties.setAddressesProviderUrl(properties.getAddressesProviderUrl());
        }
        if (PredicateUtils.isEmpty(properties.getUsername())) {
            properties.setUsername(discoveryProperties.getUsername());
        }
        if (PredicateUtils.isEmpty(properties.getPassword())) {
            properties.setPassword(discoveryProperties.getPassword());
        }
        if (PredicateUtils.isEmpty(properties.getEncryptedPassword())) {
            properties.setEncryptedPassword(discoveryProperties.getEncryptedPassword());
        }
        if (PredicateUtils.isEmpty(properties.getAccessToken())) {
            properties.setAccessToken(discoveryProperties.getAccessToken());
        }
        if (PredicateUtils.isNotBlank(discoveryProperties.getLogLevel())) {
            LoggingProperties loggingProperties = properties.getLoggingProperties();
            if (null == loggingProperties) {
                loggingProperties = new LoggingProperties();
                properties.setLoggingProperties(loggingProperties);
            }
            loggingProperties.setRegistrationLogLevel(discoveryProperties.getLogLevel());
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
