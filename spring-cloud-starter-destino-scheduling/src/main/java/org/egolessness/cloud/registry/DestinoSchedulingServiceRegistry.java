package org.egolessness.cloud.registry;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.registration.RegistrationService;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.destino.common.exception.DestinoException;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * Destino scheduling service registry.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingServiceRegistry implements SmartInitializingSingleton, DisposableBean {

	private static final Logger log = LoggerFactory.getLogger(DestinoSchedulingServiceRegistry.class);

	private final DestinoSchedulingRegistration schedulingRegistration;

	private final DestinoConfiguration destinoConfiguration;

	DestinoSchedulingServiceRegistry(DestinoSchedulingRegistration schedulingRegistration,
									 DestinoConfiguration destinoConfiguration) {
		this.schedulingRegistration = schedulingRegistration;
		this.destinoConfiguration = destinoConfiguration;
	}

	public void register() {
		String service = schedulingRegistration.getService();
		if (PredicateUtils.isEmpty(service)) {
			log.warn("Unable to register to destino scheduling, current service name is empty.");
			return;
		}

		RegistrationService registrationService = destinoConfiguration.getRegistrationService();
		String namespace = schedulingRegistration.getNamespace();
		String group = schedulingRegistration.getGroup();
		RegistrationInfo registrationInfo = schedulingRegistration.buildRegistrationInfo();

		try {
			registrationService.register(namespace, group, service, registrationInfo);
			log.info("Destino scheduling registry, {} {} {} {}:{} register finished.", namespace, group, service,
					registrationInfo.getIp(), registrationInfo.getPort());
		} catch (Exception e) {
			log.warn("Destino scheduling registry. {} register failed...{},", service, registrationInfo, e);
		}
	}

	public void deregister() {
		log.info("De-registering from destino scheduling...");
		String service = schedulingRegistration.getService();
		if (PredicateUtils.isEmpty(service)) {
			log.warn("No dom to deregister from destino scheduling...");
			return;
		}

		RegistrationService registrationService = destinoConfiguration.getRegistrationService();
		String namespace = schedulingRegistration.getNamespace();
		String group = schedulingRegistration.getGroup();
		RegistrationInfo registrationInfo = schedulingRegistration.buildRegistrationInfo();

		try {
			registrationService.deregister(namespace, group, service, registrationInfo.getIp(),
					registrationInfo.getPort(), registrationInfo.getCluster());
		} catch (Exception e) {
			log.error("Failed to deregister from destino scheduling...{},", registrationInfo, e);
		}

		log.info("Destino scheduling, De-registration finished.");
	}

	@Override
	public void afterSingletonsInstantiated() {
		register();
	}

	@Override
	public void destroy() throws DestinoException {
		deregister();
		destinoConfiguration.shutdown();
	}

}
