package org.egolessness.cloud.endpoint;

import org.egolessness.destino.client.DestinoConfiguration;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.concurrent.TimeoutException;

/**
 * Destino server health indicator {@link AbstractHealthIndicator}.
 *
 * @author wangkang zsmjwk@outlook.com
 */
public class DestinoHealthIndicator extends AbstractHealthIndicator {

	private final DestinoConfiguration destinoConfiguration;

	public DestinoHealthIndicator(DestinoConfiguration destinoConfiguration) {
		this.destinoConfiguration = destinoConfiguration;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		try {
			boolean available = destinoConfiguration.serverCheck();
			if (available) {
				builder.up();
			} else {
				builder.down();
			}
		} catch (TimeoutException e) {
			builder.unknown();
		}
	}

}
