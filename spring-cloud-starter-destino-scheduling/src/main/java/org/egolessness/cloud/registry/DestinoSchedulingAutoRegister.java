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
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

public class DestinoSchedulingAutoRegister implements ApplicationListener<WebServerInitializedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DestinoSchedulingAutoRegister.class);

    private final DestinoSchedulingRegistration schedulingRegistration;

    private final DestinoConfiguration destinoConfiguration;

    DestinoSchedulingAutoRegister(DestinoSchedulingRegistration schedulingRegistration,
                                     DestinoConfiguration destinoConfiguration) {
        this.schedulingRegistration = schedulingRegistration;
        this.destinoConfiguration = destinoConfiguration;
    }

    @Override
    public void onApplicationEvent(@NonNull WebServerInitializedEvent webServerInitializedEvent) {
        String service = schedulingRegistration.getService();
        if (PredicateUtils.isEmpty(service)) {
            log.warn("Unable to register to destino scheduling, current service name is empty.");
            return;
        }

        RegistrationService registrationService = destinoConfiguration.getRegistrationService();
        String namespace = schedulingRegistration.getNamespace();
        String group = schedulingRegistration.getGroup();
        RegistrationInfo registrationInfo = schedulingRegistration.buildRegistrationInfo();

        try {
            registrationService.register(namespace, group, service, registrationInfo);
            log.info("Destino scheduling registry, {} {} {} {}:{} register finished.", namespace, group, service,
                    registrationInfo.getIp(), registrationInfo.getPort());
        } catch (Exception e) {
            log.warn("Destino scheduling registry. {} register failed...{},", service, registrationInfo, e);
        }
    }

}
