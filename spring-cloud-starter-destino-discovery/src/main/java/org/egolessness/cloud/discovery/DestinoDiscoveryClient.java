package org.egolessness.cloud.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Collections;
import java.util.List;

/**
 * Spring cloud discovery client with Destino.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoDiscoveryClient implements DiscoveryClient {

	private static final Logger log = LoggerFactory.getLogger(DestinoDiscoveryClient.class);

	private final DestinoServiceDiscovery serviceDiscovery;

	public DestinoDiscoveryClient(DestinoServiceDiscovery destinoServiceDiscovery) {
		this.serviceDiscovery = destinoServiceDiscovery;
	}

	@Override
	public String description() {
		return "Spring Cloud Discovery Client @Destino";
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) {
		try {
			return serviceDiscovery.getInstances(serviceId);
		} catch (Exception e) {
			log.error("Unable to read instances of serviceId: {} from destino server.", serviceId, e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<String> getServices() {
		try {
			return serviceDiscovery.getServices();
		} catch (Exception e) {
			log.error("Unable to read services from destino server.", e);
			return Collections.emptyList();
		}
	}

}
