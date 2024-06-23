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

package org.egolessness.cloud.loadbalancer;

import org.egolessness.cloud.DestinoDiscoveryContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.env.Environment;

/**
 * Destino load balancer factory.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoLoadBalancerFactory {

    private final Environment environment;

    private final LoadBalancerClientFactory clientFactory;

    private final DestinoDiscoveryContext discoveryContext;

    public DestinoLoadBalancerFactory(Environment environment, LoadBalancerClientFactory clientFactory,
                                      DestinoDiscoveryContext discoveryContext) {
        this.environment = environment;
        this.clientFactory = clientFactory;
        this.discoveryContext = discoveryContext;
    }

    public ReactorLoadBalancer<ServiceInstance> create() {
        String loadbalancerStrategy = environment.resolvePlaceholders("${spring.cloud.destino.discovery.loadbalancer.strategy:" +
                "${spring.cloud.destino.loadbalancer.strategy:default}}");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        ObjectProvider<ServiceInstanceListSupplier> listSupplierProvider = clientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class);
        if ("random".equalsIgnoreCase(loadbalancerStrategy)) {
            return new RandomLoadBalancer(listSupplierProvider, name);
        }
        if ("round robin".equalsIgnoreCase(loadbalancerStrategy) || "round-robin".equalsIgnoreCase(loadbalancerStrategy)
                || "roundrobin".equalsIgnoreCase(loadbalancerStrategy)) {
            return new RoundRobinLoadBalancer(listSupplierProvider, name);
        }
        return new DestinoWeightRandomLoadBalancer(listSupplierProvider, name, discoveryContext);
    }

}
