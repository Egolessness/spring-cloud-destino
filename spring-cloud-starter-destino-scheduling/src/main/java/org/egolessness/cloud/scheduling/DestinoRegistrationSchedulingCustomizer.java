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

package org.egolessness.cloud.scheduling;

import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.destino.client.scheduling.functional.Scheduled;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * Destino registration scheduling customizer.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoRegistrationSchedulingCustomizer implements DestinoRegistrationCustomizer {

    private final DestinoSchedulingJobScanner jobScanner;

    public DestinoRegistrationSchedulingCustomizer(DestinoSchedulingJobScanner jobScanner) {
        this.jobScanner = jobScanner;
    }

    @Override
    public void accept(@NonNull RegistrationInfo registration) {
        Set<Scheduled<String, String>> jobs = jobScanner.loadJobs();
        if (PredicateUtils.isNotEmpty(jobs)) {
            registration.setJobs(jobs);
        }
    }

}
