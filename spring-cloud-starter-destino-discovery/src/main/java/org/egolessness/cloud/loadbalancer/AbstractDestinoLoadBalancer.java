package org.egolessness.cloud.loadbalancer;

import org.egolessness.cloud.DestinoDiscoveryContext;
import org.egolessness.cloud.instance.DestinoInstanceMetaKey;
import org.egolessness.cloud.context.validator.InetAddressValidator;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract load balancer for Destino.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public abstract class AbstractDestinoLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private static final Logger log = LoggerFactory.getLogger(AbstractDestinoLoadBalancer.class);

	protected final String serviceId;

	protected final ObjectProvider<ServiceInstanceListSupplier> instanceListSupplierProvider;

	protected final DestinoDiscoveryContext discoveryContext;

	private boolean supportIpv6 = true;

	public AbstractDestinoLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> instanceListSupplierProvider,
									   String serviceId, DestinoDiscoveryContext discoveryContext) {
		this.serviceId = serviceId;
		this.instanceListSupplierProvider = instanceListSupplierProvider;
		this.discoveryContext = discoveryContext;
	}

	@PostConstruct
	public void init() {
		String iPv6Address = discoveryContext.getInetIPv6Utils().findIPv6Address();
		supportIpv6 = PredicateUtils.isNotEmpty(iPv6Address);
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = instanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next().map(this::convertInstanceResponse);
	}

	private Response<ServiceInstance> convertInstanceResponse(List<ServiceInstance> instances) {
		if (PredicateUtils.isEmpty(instances)) {
			log.warn("No available instances for service: " + this.serviceId);
			return new EmptyResponse();
		}

		try {
			String cluster = discoveryContext.getCluster();
			if (PredicateUtils.isNotBlank(cluster)) {
				Stream<ServiceInstance> sameClusterInstances = instances.stream()
						.filter(instance -> Objects.equals(instance.getMetadata().get(DestinoInstanceMetaKey.CLUSTER), cluster));
				if (sameClusterInstances.findAny().isPresent()) {
					instances = sameClusterInstances.collect(Collectors.toList());
				}
			}
			instances = this.filterIpv6WhenUnsupported(instances);
			if (PredicateUtils.isEmpty(instances)) {
				return new EmptyResponse();
			}

			return new DefaultResponse(chooseInstance(instances));
		} catch (Exception e) {
			log.warn("Failed to choose instance by destino Load balancer.", e);
			return null;
		}
	}

	protected double getWeight(ServiceInstance instance) {
		Map<String, String> metadata = instance.getMetadata();
		String weightValue = metadata.get(DestinoInstanceMetaKey.WEIGHT);
		try {
			if (PredicateUtils.isNotEmpty(weightValue)) {
				return Double.parseDouble(weightValue);
			}
		} catch (Exception ignored) {
		}
		return 1D;
	}

	private List<ServiceInstance> filterIpv6WhenUnsupported(List<ServiceInstance> instances) {
		if (supportIpv6) {
			return instances;
		}

		return instances.stream().filter(instance -> !InetAddressValidator.isValidInet6Address(instance.getHost()))
				.collect(Collectors.toList());
	}

	protected abstract ServiceInstance chooseInstance(List<ServiceInstance> instances);

}
