package org.egolessness.cloud.registry;

import org.egolessness.cloud.ConditionalOnDestinoSchedulingEnabled;
import org.egolessness.cloud.scheduling.DestinoRegistrationSchedulingAutoConfiguration;
import org.egolessness.cloud.scheduling.DestinoRegistrationSchedulingCustomizer;
import org.egolessness.cloud.scheduling.UtilIPv6AutoConfiguration;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.egolessness.cloud.properties.DestinoSchedulingExtProperties;
import org.egolessness.cloud.scheduling.DestinoSchedulingJobScanner;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Destino scheduling registry.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
@ConditionalOnMissingBean({DestinoRegistrationSchedulingCustomizer.class})
@AutoConfigureAfter({UtilIPv6AutoConfiguration.class, DestinoRegistrationSchedulingAutoConfiguration.class})
@EnableConfigurationProperties(DestinoSchedulingExtProperties.class)
public class DestinoSchedulingRegistryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingRegistration destinoSchedulingRegistration(DestinoSchedulingExtProperties extProperties,
																	   ApplicationContext context, InetUtils inetUtils,
																	   InetIPv6Utils inetIPv6Utils,
																	   DestinoSchedulingJobScanner jobScanner,
																	   ObjectProvider<ServletWebServerApplicationContext> servletContextProvider,
																	   ObjectProvider<ReactiveWebServerApplicationContext> reactiveContextProvider) {
		return new DestinoSchedulingRegistration(extProperties, context, inetUtils, inetIPv6Utils, jobScanner,
				servletContextProvider, reactiveContextProvider);
	}

	@Bean
	@ConditionalOnMissingBean
	public DestinoSchedulingServiceRegistry schedulingServiceRegistry(DestinoSchedulingRegistration schedulingRegistration,
																	  DestinoConfiguration destinoConfiguration) {
		return new DestinoSchedulingServiceRegistry(schedulingRegistration, destinoConfiguration);
	}

}
