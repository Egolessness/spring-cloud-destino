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
import org.egolessness.destino.common.utils.PredicateUtils;

/**
 * Completer for Destino scheduling ext properties.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingExtPropertiesCompleter implements DestinoPropertiesCompleter {

    private final DestinoSchedulingExtProperties schedulingExtProperties;

    public DestinoSchedulingExtPropertiesCompleter(DestinoSchedulingExtProperties schedulingExtProperties) {
        this.schedulingExtProperties = schedulingExtProperties;
    }

    @Override
    public void complete(DestinoProperties properties) {
        if (PredicateUtils.isNotEmpty(schedulingExtProperties.getServers())) {
            for (String server : schedulingExtProperties.getServers()) {
                properties.addServer(server);
            }
        }
        if (PredicateUtils.isBlank(properties.getServersProviderUrl())) {
            properties.setServersProviderUrl(properties.getServersProviderUrl());
        }
        if (PredicateUtils.isEmpty(properties.getUsername())) {
            properties.setUsername(schedulingExtProperties.getUsername());
        }
        if (PredicateUtils.isEmpty(properties.getPassword())) {
            properties.setPassword(schedulingExtProperties.getPassword());
        }
        if (PredicateUtils.isEmpty(properties.getEncryptedPassword())) {
            properties.setEncryptedPassword(schedulingExtProperties.getEncryptedPassword());
        }
        if (PredicateUtils.isEmpty(properties.getAccessToken())) {
            properties.setAccessToken(schedulingExtProperties.getAccessToken());
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
