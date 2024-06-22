package org.egolessness.cloud.registry;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.registration.ConsultationService;
import org.egolessness.destino.client.registration.RegistrationService;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.destino.client.registration.selector.InstanceSelector;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.egolessness.destino.common.exception.DestinoException;
import org.egolessness.destino.common.model.ServiceInstance;
import org.egolessness.destino.common.enumeration.RegisterMode;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * Destino service registry.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoServiceRegistry implements ServiceRegistry<DestinoRegistration> {

	private static final Logger log = LoggerFactory.getLogger(DestinoServiceRegistry.class);

	private final DestinoDiscoveryProperties discoveryProperties;

	private final DestinoConfiguration destinoConfiguration;

	DestinoServiceRegistry(DestinoConfiguration destinoConfiguration,
						   DestinoDiscoveryProperties discoveryProperties) {
		this.discoveryProperties = discoveryProperties;
		this.destinoConfiguration = destinoConfiguration;
	}

	@Override
	public void register(DestinoRegistration registration) {

		if (!StringUtils.hasLength(registration.getServiceId())) {
			log.warn("No service register to destino server.");
			return;
		}

		RegistrationService registrationService = destinoConfiguration.getRegistrationService();
		String namespace = discoveryProperties.getNamespace();
		String group = discoveryProperties.getGroup();
		String serviceId = registration.getServiceId();
		RegistrationInfo registrationInfo = buildRegistrationInfo(registration);

		try {
			registrationService.register(namespace, group, serviceId, registrationInfo);
			log.info("Destino registry, {} {} {} {}:{} register finished.", namespace, group, serviceId,
					registrationInfo.getIp(), registrationInfo.getPort());
		} catch (Exception e) {
			if (discoveryProperties.isFailFast()) {
				log.error("Destino registry, {} register failed...{},", serviceId, registration, e);
				rethrowRuntimeException(e);
			}
			log.warn("Destino registry fail-fast is false. {} register failed...{},", serviceId, registration, e);
		}
	}

	@Override
	public void deregister(DestinoRegistration registration) {
		log.info("De-registering from destino server...");
		if (PredicateUtils.isEmpty(registration.getServiceId())) {
			log.warn("No dom to deregister from destino server...");
			return;
		}

		RegistrationService registrationService = destinoConfiguration.getRegistrationService();
		String namespace = discoveryProperties.getNamespace();
		String serviceId = registration.getServiceId();
		String group = discoveryProperties.getGroup();
		String clusterName = discoveryProperties.getClusterName();

		try {
			registrationService.deregister(namespace, group, serviceId, registration.getHost(),
					registration.getPort(), clusterName);
		} catch (Exception e) {
			log.error("ERR_DESTINY_DEREGISTER, deregister failed...{},", registration, e);
		}

		log.info("De-registration finished.");
	}

	@Override
	public void setStatus(DestinoRegistration registration, String status) {
		if (!Status.UP.getCode().equalsIgnoreCase(status)
				&& !Status.DOWN.getCode().equalsIgnoreCase(status)) {
			log.warn("Unsupported status {}, please choose UP or DOWN", status);
			return;
		}

		String namespace = discoveryProperties.getNamespace();
		String serviceId = registration.getServiceId();
		String group = discoveryProperties.getGroup();
		RegistrationInfo registrationInfo = buildRegistrationInfo(registration);
		registrationInfo.setEnabled(!Status.DOWN.getCode().equalsIgnoreCase(status));

		try {
			RegistrationService registrationService = destinoConfiguration.getRegistrationService();
			registrationService.update(namespace, group, serviceId, registrationInfo);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update destino instance status.", e);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public String getStatus(DestinoRegistration registration) {

		String namespace = discoveryProperties.getNamespace();
		String serviceId = registration.getServiceId();
		String group = discoveryProperties.getGroup();
		try {
			ConsultationService consultationService = destinoConfiguration.getConsultationService();
			InstanceSelector instanceSelector = consultationService.subscribeService(namespace, group, serviceId,
					new String[0]);
			List<ServiceInstance> instances = instanceSelector.getAllInstances();
			for (ServiceInstance instance : instances) {
				if (instance.getIp().equalsIgnoreCase(discoveryProperties.getIp())
						&& instance.getPort() == discoveryProperties.getPort()) {
					return instance.isEnabled() ? Status.UP.getCode() : Status.DOWN.getCode();
				}
			}
		} catch (Exception e) {
			log.error("Failed to read instances of {}.", serviceId, e);
		}
		return null;
	}

	@Override
	public void close() {
		try {
			destinoConfiguration.shutdown();
		} catch (DestinoException e) {
			log.error("Destino configuration shutdown failed.", e);
		}
	}

	private RegistrationInfo buildRegistrationInfo(DestinoRegistration registration) {
		RegistrationInfo registrationInfo = new RegistrationInfo();
		registrationInfo.setIp(registration.getHost());
		registrationInfo.setPort(registration.getPort());
		registrationInfo.setWeight(discoveryProperties.getWeight());
		registrationInfo.setEnabled(discoveryProperties.isInstanceEnabled());
		registrationInfo.setCluster(discoveryProperties.getClusterName());
		registrationInfo.setMetadata(registration.getMetadata());

		if (null != discoveryProperties.getRegisterMode()) {
			registrationInfo.setMode(discoveryProperties.getRegisterMode());
		} else if (discoveryProperties.isSafety()) {
			registrationInfo.setMode(RegisterMode.SAFETY);
		} else {
			registrationInfo.setMode(RegisterMode.QUICKLY);
		}

		registration.customize(registrationInfo);
		return registrationInfo;
	}

}
