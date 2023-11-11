package org.egolessness.cloud.context;

import org.egolessness.cloud.context.properties.DestinoPropertiesCompleter;
import org.egolessness.destino.client.DestinoConfiguration;
import org.egolessness.destino.client.infrastructure.ScriptFactory;
import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.cloud.context.properties.DestinoContextProperties;
import org.egolessness.destino.common.exception.DestinoException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfiguration for destino.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@EnableConfigurationProperties(DestinoContextProperties.class)
public class DestinoAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DestinoConfiguration destinoConfiguration(DestinoContextProperties contextProperties,
													 ApplicationContext context,
													 List<DestinoPropertiesCompleter> completerList) throws DestinoException {
		DestinoProperties destinoProperties = contextProperties.toDestinoProperties(completerList);
		ScriptFactory scriptFactory = new ScriptFactory();
		scriptFactory.setInstanceFactory(new SpringScriptInstanceFactory(context));
		return new DestinoConfiguration(destinoProperties, scriptFactory);
	}

	@Bean
	public DestinoShutdownApplicationListener destinoShutdownApplicationListener(DestinoConfiguration destinoConfiguration) {
		return new DestinoShutdownApplicationListener(destinoConfiguration);
	}

}