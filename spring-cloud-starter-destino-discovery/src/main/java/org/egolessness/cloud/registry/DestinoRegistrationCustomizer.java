package org.egolessness.cloud.registry;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface DestinoRegistrationCustomizer {

	void accept(@NonNull DestinoRegistration registration);

}
