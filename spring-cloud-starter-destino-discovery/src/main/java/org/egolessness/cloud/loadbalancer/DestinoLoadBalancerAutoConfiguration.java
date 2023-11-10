package org.egolessness.cloud.loadbalancer;

import org.egolessness.cloud.ConditionalOnDestinoDiscoveryEnabled;
import org.egolessness.cloud.registry.DestinoAutoServiceRegistration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for destino load balancer.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.loadbalancer.destino.enabled", havingValue = "true")
@AutoConfigureAfter({DestinoAutoServiceRegistration.class})
@LoadBalancerClients(defaultConfiguration = DestinoLoadBalancerClientConfiguration.class)
public class DestinoLoadBalancerAutoConfiguration {

}
