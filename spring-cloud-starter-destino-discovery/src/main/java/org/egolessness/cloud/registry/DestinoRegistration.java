package org.egolessness.cloud.registry;

import org.egolessness.cloud.DestinoDiscoveryContext;
import org.egolessness.destino.client.scheduling.functional.Scheduled;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import javax.annotation.PostConstruct;
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

	private Set<Scheduled<String, String>> jobs;

	public DestinoRegistration(DestinoDiscoveryContext discoveryContext,
							   List<DestinoRegistrationCustomizer> destinoRegistrationCustomizers,
							   ServletWebServerApplicationContext servletWebServerApplicationContext,
							   ReactiveWebServerApplicationContext reactiveWebServerApplicationContext) {
		this.registrationCustomizers = destinoRegistrationCustomizers;
		this.discoveryContext = discoveryContext;
		this.servletWebServerApplicationContext = servletWebServerApplicationContext;
		this.reactiveWebServerApplicationContext = reactiveWebServerApplicationContext;
	}

	@PostConstruct
	public void init() {
		customize(registrationCustomizers);
	}

	private void customize(List<DestinoRegistrationCustomizer> registrationCustomizers) {
		if (registrationCustomizers != null) {
			for (DestinoRegistrationCustomizer customizer : registrationCustomizers) {
				customizer.accept(this);
			}
		}
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

	public Set<Scheduled<String, String>> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Scheduled<String, String>> jobs) {
		this.jobs = jobs;
	}


	@Override
	public String toString() {
		return "DestinoRegistration{" +
				"discoveryProperties=" + discoveryContext.getDiscoveryProperties() +
				", jobs=" + jobs +
				'}';
	}
}
