package org.egolessness.cloud.context;

import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.common.exception.DestinoException;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * Start failed listener .
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoShutdownApplicationListener implements ApplicationListener<ApplicationEvent> {

	private final DestinoConfiguration destinoConfiguration;

	public DestinoShutdownApplicationListener(DestinoConfiguration destinoConfiguration) {
		this.destinoConfiguration = destinoConfiguration;
	}

	@Override
	public void onApplicationEvent(@NonNull ApplicationEvent event) {
		if (event instanceof ApplicationFailedEvent) {
			try {
				destinoConfiguration.shutdown();
			} catch (DestinoException ignored) {
			}
		}
	}
}
