package org.egolessness.cloud.context;

import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.springframework.lang.NonNull;

@FunctionalInterface
public interface DestinoRegistrationCustomizer {

	void accept(@NonNull RegistrationInfo registerInfo);

}
