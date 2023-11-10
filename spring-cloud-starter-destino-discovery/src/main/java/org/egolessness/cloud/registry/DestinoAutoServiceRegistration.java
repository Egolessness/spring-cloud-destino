package org.egolessness.cloud.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.StringUtils;

/**
 * Destino auto service registration.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoAutoServiceRegistration extends AbstractAutoServiceRegistration<DestinoRegistration> {

	private static final Logger log = LoggerFactory.getLogger(DestinoAutoServiceRegistration.class);

	private final DestinoRegistration registration;

	DestinoAutoServiceRegistration(ServiceRegistry<DestinoRegistration> serviceRegistry,
								   AutoServiceRegistrationProperties autoServiceRegistrationProperties,
								   DestinoRegistration registration) {
		super(serviceRegistry, autoServiceRegistrationProperties);
		this.registration = registration;
	}

	@Override
	protected DestinoRegistration getRegistration() {
		if (this.registration.getPort() < 0 && this.getPort().get() > 0) {
			this.registration.setPort(this.getPort().get());
		}
		return this.registration;
	}

	@Override
	protected DestinoRegistration getManagementRegistration() {
		return null;
	}

	@Override
	protected void register() {
		if (!this.registration.isRegisterEnabled()) {
			log.debug("Registration disabled.");
			return;
		}
		if (this.registration.getPort() < 0) {
			this.registration.setPort(getPort().get());
		}
		super.register();
	}

	@Override
	protected void registerManagement() {
		if (!this.registration.isRegisterEnabled()) {
			return;
		}
		super.registerManagement();
	}

	@Override
	protected void deregister() {
		if (!this.registration.isRegisterEnabled()) {
			return;
		}
		super.deregister();
	}

	@Override
	protected void deregisterManagement() {
		if (!this.registration.isRegisterEnabled()) {
			return;
		}
		super.deregisterManagement();
	}

	@Override
	protected Object getConfiguration() {
		return this.registration.getDiscoveryProperties();
	}

	@Override
	protected boolean isEnabled() {
		return this.registration.isRegisterEnabled();
	}

	@Override
	@Deprecated
	protected String getAppName() {
		String appName = this.registration.getServiceId();
		return !StringUtils.hasLength(appName) ? super.getAppName() : appName;
	}

}
