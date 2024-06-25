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

package org.egolessness.cloud.discovery;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.registration.selector.InstanceSelector;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.egolessness.cloud.instance.DestinoInstanceConverter;
import org.egolessness.destino.common.exception.DestinoException;
import org.egolessness.destino.common.model.Page;
import org.egolessness.destino.common.model.PageParam;
import org.egolessness.destino.common.model.Pageable;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service discovery for Destino client.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoServiceDiscovery {

	private final DestinoDiscoveryProperties discoveryProperties;

	private final DestinoConfiguration configuration;

	public DestinoServiceDiscovery(DestinoDiscoveryProperties discoveryProperties,
								   DestinoConfiguration destinoConfiguration) {
		this.discoveryProperties = discoveryProperties;
		this.configuration = destinoConfiguration;
	}

	/**
	 * get all health instances with serviceId
	 */
	public List<ServiceInstance> getInstances(String serviceId) throws DestinoException {
		String namespace = discoveryProperties.getNamespace();
		String group = discoveryProperties.getGroup();
		InstanceSelector instancesSelector = configuration.getConsultationService().subscribeService(namespace, group,
				serviceId);
		return instancesSelector.getHealthyInstances().stream()
				.filter(instance -> instance != null && instance.isEnabled() && instance.isHealthy())
				.map(instance -> DestinoInstanceConverter.INSTANCE.apply(instance, serviceId))
				.collect(Collectors.toList());
	}

	/**
	 * get all services.
	 */
	public List<String> getServices() throws DestinoException {
		PageParam pageParam = new PageParam(0, Integer.MAX_VALUE);
		Page<String> page = pageQueryServices(pageParam);
		List<String> services = page.getRecords();
		if (null != services) {
			return services;
		}
		return Collections.emptyList();
	}

	public Page<String> pageQueryServices(Pageable pageable) throws DestinoException {
		String namespace = discoveryProperties.getNamespace();
		String group = discoveryProperties.getGroup();
		return configuration.getConsultationService().queryServices(namespace, group, pageable);
	}

}
