package org.egolessness.cloud.scheduling;

import org.egolessness.cloud.ConditionalOnDestinoSchedulingEnabled;
import org.egolessness.cloud.context.ConditionalOnDestinoEnabled;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for Ipv6 util.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDestinoEnabled
@ConditionalOnDestinoSchedulingEnabled
public class UtilIPv6AutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public InetIPv6Utils inetIPv6Utils(InetUtilsProperties properties) {
		return new InetIPv6Utils(properties);
	}

}
