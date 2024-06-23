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

package org.egolessness.cloud.discovery.reactive;

import org.egolessness.cloud.discovery.DestinoServiceDiscovery;
import org.egolessness.destino.common.exception.DestinoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;

/**
 * Spring cloud reactive discovery client with Destino.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoReactiveDiscoveryClient implements ReactiveDiscoveryClient {

	private static final Logger log = LoggerFactory.getLogger(DestinoReactiveDiscoveryClient.class);

	private final DestinoServiceDiscovery serviceDiscovery;

	public DestinoReactiveDiscoveryClient(DestinoServiceDiscovery destinoServiceDiscovery) {
		this.serviceDiscovery = destinoServiceDiscovery;
	}

	@Override
	public String description() {
		return "Spring Cloud Reactive Discovery Client @Destino";
	}

	@Override
	public Flux<ServiceInstance> getInstances(String serviceId) {
		return Mono.justOrEmpty(serviceId).flatMapIterable(service -> {
			try {
				return serviceDiscovery.getInstances(service);
			} catch (Exception e) {
				log.error("Unable to read instances of serviceId: {} from destino server.", serviceId, e);
				return Collections.emptyList();
			}
		}).subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Flux<String> getServices() {
		return Flux.defer(() -> {
			try {
				List<String> services = serviceDiscovery.getServices();
				return Flux.fromIterable(services);
			} catch (DestinoException e) {
				log.error("Unable to read services from destino server.", e);
				return Flux.empty();
			}
		}).subscribeOn(Schedulers.boundedElastic());
	}

}
