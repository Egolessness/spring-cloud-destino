package org.egolessness.cloud.registry;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface DestinoRegistrationCustomizer {

	void accept(@Nonnull DestinoRegistration registration);

}
