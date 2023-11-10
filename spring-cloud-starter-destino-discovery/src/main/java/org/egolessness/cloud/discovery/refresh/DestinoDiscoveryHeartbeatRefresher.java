/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.egolessness.cloud.discovery.refresh;

import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Refresher for Destino discovery heartbeat.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoDiscoveryHeartbeatRefresher implements ApplicationEventPublisherAware, SmartLifecycle {

	private static final Logger log = LoggerFactory.getLogger(DestinoDiscoveryHeartbeatRefresher.class);

	private final DestinoDiscoveryProperties discoveryProperties;

	private final ThreadPoolTaskScheduler taskScheduler;

	private final AtomicLong heartbeatIndex = new AtomicLong();

	private final AtomicBoolean started = new AtomicBoolean();

	private ApplicationEventPublisher publisher;

	private ScheduledFuture<?> heartbeatFuture;

	public DestinoDiscoveryHeartbeatRefresher(DestinoDiscoveryProperties discoveryProperties) {
		this.discoveryProperties = discoveryProperties;
		this.taskScheduler = buildTaskScheduler();
	}

	private ThreadPoolTaskScheduler buildTaskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setBeanName("Destino-Heartbeat-Refresher");
		taskScheduler.initialize();
		return taskScheduler;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public boolean isRunning() {
		return this.started.get();
	}

	@Override
	public void start() {
		if (this.started.compareAndSet(false, true)) {
			log.info("Start destino heartbeat task scheduler.");
			this.heartbeatFuture = this.taskScheduler.scheduleWithFixedDelay(
					this::publishHeartbeatEvent, Duration.ofMillis(this.discoveryProperties.getHeartbeatRefreshInterval()));
		}
	}

	@Override
	public void stop() {
		if (this.started.compareAndSet(true, false)) {
			if (this.heartbeatFuture != null) {
				this.taskScheduler.shutdown();
				this.heartbeatFuture.cancel(true);
			}
		}
	}

	@Override
	public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	private void publishHeartbeatEvent() {
		try {
			this.publisher.publishEvent(new HeartbeatEvent(this, heartbeatIndex.getAndIncrement()));
		} catch (Exception e) {
			log.error("Refresh destino service error.", e);
		}
	}
}
