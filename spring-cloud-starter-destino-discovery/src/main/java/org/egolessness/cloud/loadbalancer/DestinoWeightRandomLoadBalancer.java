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
