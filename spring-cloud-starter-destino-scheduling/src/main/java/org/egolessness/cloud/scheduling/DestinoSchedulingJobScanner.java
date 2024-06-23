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

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.annotation.DestinoJob;
import org.egolessness.destino.client.annotation.GlobalScheduled;
import org.egolessness.destino.client.scheduling.LocalSchedulingService;
import org.egolessness.destino.client.scheduling.functional.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Destino scheduling job scanner.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingJobScanner {

    private static final Logger log = LoggerFactory.getLogger(DestinoSchedulingJobScanner.class);

    private final LocalSchedulingService localSchedulingService;

    private final ApplicationContext applicationContext;

    public DestinoSchedulingJobScanner(DestinoConfiguration destinoConfiguration, ApplicationContext applicationContext) {
        this.localSchedulingService = destinoConfiguration.getLocalScheduledService();
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanName : beanNames) {
            addJobWithBeanName(beanName);
        }
    }

    private void addJobWithBeanName(String beanName) {
        Lazy lazyBean = applicationContext.findAnnotationOnBean(beanName, Lazy.class);
        if (lazyBean != null){
            log.debug("Destino job is scanning, skip @Lazy Bean: {}.", beanName);
            return;
        }

        Object bean;
        try {
            bean = applicationContext.getBean(beanName);
        } catch (Exception e) {
            return;
        }

        try {
            Scheduled<String, String> job = localSchedulingService.parseJobForInterface(bean);
            addJob(job);
        } catch (Exception e) {
            log.error("Failed to parse scheduled interface impl with bean: {}.", beanName, e);
        }

        try {
            Map<Method, DestinoJob> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<DestinoJob>) method ->
                            AnnotatedElementUtils.findMergedAnnotation(method, DestinoJob.class));

            for (Map.Entry<Method, DestinoJob> destinoJobEntry : annotatedMethods.entrySet()) {
                Method method = destinoJobEntry.getKey();
                DestinoJob destinoJob = destinoJobEntry.getValue();
                Scheduled<String, String> job = localSchedulingService.parseJob(bean, method, destinoJob.value());
                addJob(job);
            }
        } catch (Exception e) {
            log.error("Failed to parse @DestinoJob with bean: {}.", beanName, e);
        }

        try {
            Map<Method, GlobalScheduled> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<GlobalScheduled>) method ->
                            AnnotatedElementUtils.findMergedAnnotation(method, GlobalScheduled.class));

            for (Map.Entry<Method, GlobalScheduled> globalScheduledEntry : annotatedMethods.entrySet()) {
                Method method = globalScheduledEntry.getKey();
                GlobalScheduled globalScheduled = globalScheduledEntry.getValue();
                Scheduled<String, String> job = localSchedulingService.parseJob(bean, method, globalScheduled.value());
                addJob(job);
            }
        } catch (Exception e) {
            log.error("Failed to parse @GlobalScheduled with bean: {}.", beanName, e);
        }
    }

    private void addJob(@Nullable Scheduled<String, String> job) {
        if (null != job) {
            localSchedulingService.addJobs(job);
        }
    }

    public Set<Scheduled<String, String>> loadJobs() {
        return new HashSet<>(localSchedulingService.loadJobs());
    }

}
