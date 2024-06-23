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

import org.egolessness.cloud.DestinoDiscoveryContext;
import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.net.*;
import java.util.*;

/**
 * Destino registration.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoRegistration implements Registration {

	private final List<DestinoRegistrationCustomizer> registrationCustomizers;

	private final DestinoDiscoveryContext discoveryContext;

	private final ServletWebServerApplicationContext servletWebServerApplicationContext;

	private final ReactiveWebServerApplicationContext reactiveWebServerApplicationContext;

	public DestinoRegistration(DestinoDiscoveryContext discoveryContext,
							   List<DestinoRegistrationCustomizer> destinoRegistrationCustomizers,
							   ServletWebServerApplicationContext servletWebServerApplicationContext,
							   ReactiveWebServerApplicationContext reactiveWebServerApplicationContext) {
		this.registrationCustomizers = destinoRegistrationCustomizers;
		this.discoveryContext = discoveryContext;
		this.servletWebServerApplicationContext = servletWebServerApplicationContext;
		this.reactiveWebServerApplicationContext = reactiveWebServerApplicationContext;
	}

	@Override
	public String getServiceId() {
		return discoveryContext.getService();
	}

	@Override
	public String getHost() {
		return discoveryContext.getHost();
	}

	@Override
	public int getPort() {
		if (discoveryContext.getPort() > 0) {
			return discoveryContext.getPort();
		}
		if (servletWebServerApplicationContext != null) {
			return servletWebServerApplicationContext.getWebServer().getPort();
		}
		if (reactiveWebServerApplicationContext != null) {
			return reactiveWebServerApplicationContext.getWebServer().getPort();
		}
		return -1;
	}

	public void setPort(int port) {
		this.discoveryContext.setPort(port);
	}

	@Override
	public boolean isSecure() {
		return discoveryContext.isSecure();
	}

	@Override
	public URI getUri() {
		return DefaultServiceInstance.getUri(this);
	}

	@Override
	public Map<String, String> getMetadata() {
		return discoveryContext.getMetadata();
	}

	public boolean isRegisterEnabled() {
		return discoveryContext.isRegisterEnabled();
	}

	public String getCluster() {
		return discoveryContext.getCluster();
	}

	public DestinoDiscoveryProperties getDiscoveryProperties() {
		return discoveryContext.getDiscoveryProperties();
	}

	public void customize(RegistrationInfo registrationInfo) {
		if (registrationCustomizers != null) {
			for (DestinoRegistrationCustomizer customizer : registrationCustomizers) {
				customizer.accept(registrationInfo);
			}
		}
	}

	@Override
	public String toString() {
		return "DestinoRegistration{" +
				"discoveryProperties=" + discoveryContext.getDiscoveryProperties() +
				'}';
	}

}
