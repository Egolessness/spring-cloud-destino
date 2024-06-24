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

import org.egolessness.cloud.ConditionalOnDestinoSchedulingEnabled;
import org.egolessness.cloud.DestinoSchedulingAutoConfiguration;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.destino.client.DestinoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino registration scheduling.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
@AutoConfigureAfter(DestinoSchedulingAutoConfiguration.class)
public class DestinoRegistrationSchedulingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DestinoSchedulingJobScanner destinoSchedulingJobScanner(DestinoConfiguration configuration,
                                                                   ApplicationContext context) {
        return new DestinoSchedulingJobScanner(configuration, context);
    }

    @Bean
    @ConditionalOnMissingBean
    public DestinoRegistrationSchedulingCustomizer destinoRegistrationSchedulingCustomizer(DestinoSchedulingJobScanner scanner) {
        return new DestinoRegistrationSchedulingCustomizer(scanner);
    }

}
