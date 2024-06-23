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
import org.egolessness.destino.common.balancer.WeightRandomBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import java.util.List;

/**
 * Destino weight random load balancer.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoWeightRandomLoadBalancer extends AbstractDestinoLoadBalancer {

	public DestinoWeightRandomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> instanceListSupplierProvider,
										   String serviceId, DestinoDiscoveryContext discoveryContext) {
		super(instanceListSupplierProvider, serviceId, discoveryContext);
	}

	@Override
	protected ServiceInstance chooseInstance(List<ServiceInstance> instances) {
		WeightRandomBalancer<ServiceInstance> balancer = new WeightRandomBalancer<>(instances, this::getWeight);
		return balancer.next();
	}

}
