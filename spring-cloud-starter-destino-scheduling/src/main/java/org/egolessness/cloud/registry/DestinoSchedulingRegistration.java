package org.egolessness.cloud.registry;

import org.egolessness.cloud.context.DestinoMetadataKey;
import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.egolessness.cloud.properties.DestinoSchedulingExtProperties;
import org.egolessness.destino.common.enumeration.RegisterMode;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.discovery.ManagementServerPortUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egolessness.destino.common.constant.InstanceMetadataKey.*;

/**
 * Destino scheduling discovery registration without auto service register.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingRegistration {

    private final DestinoSchedulingExtProperties schedulingExtProperties;

    private final ApplicationContext context;

    private final InetUtils inetUtils;

    private final InetIPv6Utils inetIPv6Utils;

    private final List<DestinoRegistrationCustomizer> registrationCustomizers;

    private final ObjectProvider<ServletWebServerApplicationContext> servletContextProvider;

    private final ObjectProvider<ReactiveWebServerApplicationContext> reactiveContextProvider;

    public DestinoSchedulingRegistration(DestinoSchedulingExtProperties schedulingExtProperties,
                                         ApplicationContext context, InetUtils inetUtils, InetIPv6Utils inetIPv6Utils,
                                         List<DestinoRegistrationCustomizer> registrationCustomizers,
                                         ObjectProvider<ServletWebServerApplicationContext> servletContextProvider,
                                         ObjectProvider<ReactiveWebServerApplicationContext> reactiveContextProvider) {
        this.schedulingExtProperties = schedulingExtProperties;
        this.context = context;
        this.inetUtils = inetUtils;
        this.inetIPv6Utils = inetIPv6Utils;
        this.registrationCustomizers = registrationCustomizers;
        this.servletContextProvider = servletContextProvider;
        this.reactiveContextProvider = reactiveContextProvider;

    }

    @PostConstruct
    public void init() throws Exception  {
        Map<String, String> metadata = schedulingExtProperties.getMetadata();
        if (null == metadata) {
            metadata = new HashMap<>();
            schedulingExtProperties.setMetadata(metadata);
        }

        if (PredicateUtils.isNotBlank(schedulingExtProperties.getHealthCheckUrl())) {
            metadata.put(HEALTH_CHECK_URL, schedulingExtProperties.getHealthCheckUrl());
        }

        metadata.put(REGISTER_ENVIRONMENT, "SPRING_CLOUD");

        String ip = schedulingExtProperties.getIp();
        if (PredicateUtils.isBlank(ip)) {
            ip = findAvailableIp();
            schedulingExtProperties.setIp(ip);
        }

        Environment env = context.getEnvironment();

        String endpointBasePath = env.getProperty(DestinoMetadataKey.MANAGEMENT_ENDPOINT_BASE_PATH);
        if (PredicateUtils.isNotEmpty(endpointBasePath)) {
            metadata.put(DestinoMetadataKey.MANAGEMENT_ENDPOINT_BASE_PATH, endpointBasePath);
        }

        Integer managementPort = ManagementServerPortUtils.getPort(context);
        if (managementPort != null) {
            metadata.put(DestinoMetadataKey.MANAGEMENT_PORT, managementPort.toString());
            String address = env.getProperty("management.server.address");
            if (PredicateUtils.isNotEmpty(address)) {
                metadata.put(DestinoMetadataKey.MANAGEMENT_ADDRESS, address);
            }
            String contextPath = env.getProperty("management.server.servlet.context-path");
            if (PredicateUtils.isNotEmpty(contextPath)) {
                metadata.put(DestinoMetadataKey.MANAGEMENT_CONTEXT_PATH, contextPath);
            }
        }

        Integer heartbeatInterval = schedulingExtProperties.getHeartbeatInterval();
        if (heartbeatInterval != null) {
            metadata.put(HEARTBEAT_INTERVAL, Integer.toString(heartbeatInterval));
        }

        Integer heartbeatTimeout = schedulingExtProperties.getHeartbeatTimeout();
        if (heartbeatTimeout != null) {
            metadata.put(HEARTBEAT_TIMEOUT, Integer.toString(heartbeatTimeout));
        }

        Integer deathTimeout = schedulingExtProperties.getDeathTimeout();
        if (deathTimeout != null) {
            metadata.put(DEATH_TIMEOUT, Integer.toString(deathTimeout));
        }
    }

    private String findAvailableIp() throws SocketException {
        String networkInterface = schedulingExtProperties.getNetworkInterface();
        if (PredicateUtils.isNotEmpty(networkInterface)) {
            NetworkInterface netInterface = NetworkInterface.getByName(networkInterface);
            if (null == netInterface) {
                throw new IllegalArgumentException("No such network interface: " + networkInterface);
            }

            Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses();
            while (inetAddress.hasMoreElements()) {
                InetAddress foundAddress = inetAddress.nextElement();
                if (foundAddress instanceof Inet4Address && !foundAddress.isLoopbackAddress()) {
                    return foundAddress.getHostAddress();
                }
                if (foundAddress instanceof Inet6Address && !foundAddress.isLoopbackAddress()) {
                    return foundAddress.getHostAddress();
                }
            }

            throw new RuntimeException("Unable to find any available ip with network-interface: " + networkInterface);
        } else {
            String ipType = schedulingExtProperties.getIpType();
            if (PredicateUtils.isEmpty(ipType)) {
                String ipv4Address = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
                String ipv6Address = inetIPv6Utils.findIPv6Address();
                if (PredicateUtils.isNotBlank(ipv6Address)) {
                    schedulingExtProperties.getMetadata().put(DestinoMetadataKey.IPV6, ipv6Address);
                }
                return ipv4Address;
            } else if (DestinoMetadataKey.IPV4.equalsIgnoreCase(ipType)) {
                return inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            } else if (DestinoMetadataKey.IPV6.equalsIgnoreCase(ipType)) {
                String ipv6Address = inetIPv6Utils.findIPv6Address();
                if (PredicateUtils.isBlank(ipv6Address)) {
                    return inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
                }
                return ipv6Address;
            } else {
                throw new IllegalArgumentException("Please check destino ip-type: " + ipType);
            }
        }
    }

    public String getNamespace() {
        return schedulingExtProperties.getNamespace();
    }

    public String getGroup() {
        return schedulingExtProperties.getGroup();
    }

    public String getService() {
        return schedulingExtProperties.getService();
    }

    public RegistrationInfo buildRegistrationInfo() {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setIp(schedulingExtProperties.getIp());
        registrationInfo.setWeight(schedulingExtProperties.getWeight());
        registrationInfo.setEnabled(schedulingExtProperties.isInstanceEnabled());
        registrationInfo.setCluster(schedulingExtProperties.getClusterName());
        registrationInfo.setMetadata(schedulingExtProperties.getMetadata());

        if (schedulingExtProperties.getPort() > 0) {
            registrationInfo.setPort(schedulingExtProperties.getPort());
        } else {
            ServletWebServerApplicationContext servletContext = servletContextProvider.getIfAvailable();
            if (servletContext != null) {
                registrationInfo.setPort(servletContext.getWebServer().getPort());
            }
            ReactiveWebServerApplicationContext reactiveContext = reactiveContextProvider.getIfAvailable();
            if (reactiveContext != null) {
                registrationInfo.setPort(reactiveContext.getWebServer().getPort());
            }
        }

        if (null != schedulingExtProperties.getRegisterMode()) {
            registrationInfo.setMode(schedulingExtProperties.getRegisterMode());
        } else if (schedulingExtProperties.isSafety()) {
            registrationInfo.setMode(RegisterMode.SAFETY);
        } else {
            registrationInfo.setMode(RegisterMode.QUICKLY);
        }

        if (registrationCustomizers != null) {
            for (DestinoRegistrationCustomizer registrationCustomizer : registrationCustomizers) {
                registrationCustomizer.accept(registrationInfo);
            }
        }

        return registrationInfo;
    }

}
