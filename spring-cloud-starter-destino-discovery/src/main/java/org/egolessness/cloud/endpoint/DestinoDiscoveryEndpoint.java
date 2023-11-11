package org.egolessness.cloud.endpoint;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.registration.ConsultationService;
import org.egolessness.destino.client.registration.collector.Service;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Endpoint for destino discovery, read discovery properties and subscribed services.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Endpoint(id = "destinodiscovery")
public class DestinoDiscoveryEndpoint {

	private final DestinoConfiguration destinoConfiguration;

	private final DestinoDiscoveryProperties discoveryProperties;

	public DestinoDiscoveryEndpoint(DestinoConfiguration destinoConfiguration,
									DestinoDiscoveryProperties discoveryProperties) {
		this.destinoConfiguration = destinoConfiguration;
		this.discoveryProperties = discoveryProperties;
	}

	/**
	 * @return destino discovery endpoint
	 */
	@ReadOperation
	public Map<String, Object> destinoDiscovery() {
		Map<String, Object> result = new HashMap<>();
		result.put("discovery-properties", discoveryProperties);

		ConsultationService consultationService = destinoConfiguration.getConsultationService();
		List<Service> subscribed = consultationService.getSubscribeServices();

		result.put("subscribed-services", subscribed);
		return result;
	}

}
