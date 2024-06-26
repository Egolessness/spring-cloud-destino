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

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.registration.RegistrationService;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.NonNull;

/**
 * Destino service auto deregister.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingAutoDeregister implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DestinoSchedulingAutoRegister.class);

    private final DestinoSchedulingRegistration schedulingRegistration;

    private final DestinoConfiguration destinoConfiguration;

    DestinoSchedulingAutoDeregister(DestinoSchedulingRegistration schedulingRegistration,
                                  DestinoConfiguration destinoConfiguration) {
        this.schedulingRegistration = schedulingRegistration;
        this.destinoConfiguration = destinoConfiguration;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextClosedEvent contextClosedEvent) {
        log.info("De-registering from destino scheduling...");
        String service = schedulingRegistration.getService();
        if (PredicateUtils.isEmpty(service)) {
            log.warn("No dom to deregister from destino scheduling...");
            return;
        }

        RegistrationService registrationService = destinoConfiguration.getRegistrationService();
        String namespace = schedulingRegistration.getNamespace();
        String group = schedulingRegistration.getGroup();
        RegistrationInfo registrationInfo = schedulingRegistration.buildRegistrationInfo();

        try {
            registrationService.deregister(namespace, group, service, registrationInfo.getIp(),
                    registrationInfo.getPort(), registrationInfo.getCluster());
        } catch (Exception e) {
            log.error("Failed to deregister from destino scheduling...{},", registrationInfo, e);
        }

        log.info("Destino scheduling, De-registration finished.");
    }
}
