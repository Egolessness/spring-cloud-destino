package org.egolessness.cloud;

import org.egolessness.cloud.context.DestinoMetadataKey;
import org.egolessness.cloud.properties.DestinoDiscoveryProperties;
import org.egolessness.cloud.context.util.InetIPv6Utils;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.springframework.cloud.client.discovery.ManagementServerPortUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.*;
import java.util.*;

import static org.egolessness.destino.common.constant.InstanceMetadataKey.*;

/**
 * Context of destino discovery.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoDiscoveryContext {

    private final DestinoDiscoveryProperties discoveryProperties;

    private final ApplicationContext context;

    private final InetUtils inetUtils;

    private final InetIPv6Utils inetIPv6Utils;

    public DestinoDiscoveryContext(DestinoDiscoveryProperties discoveryProperties, ApplicationContext context,
                                   InetUtils inetUtils, InetIPv6Utils inetIPv6Utils) {
        this.discoveryProperties = discoveryProperties;
        this.context = context;
        this.inetUtils = inetUtils;
        this.inetIPv6Utils = inetIPv6Utils;
    }

    @PostConstruct
    public void init() throws Exception  {
        Map<String, String> metadata = discoveryProperties.getMetadata();
        if (null == metadata) {
            metadata = new HashMap<>();
            discoveryProperties.setMetadata(metadata);
        }

        if (PredicateUtils.isNotBlank(discoveryProperties.getHealthCheckUrl())) {
            metadata.put(HEALTH_CHECK_URL, discoveryProperties.getHealthCheckUrl());
        }

        metadata.put(REGISTER_ENVIRONMENT, "SPRING_CLOUD");
        if (discoveryProperties.isSecure()) {
            metadata.put(DestinoMetadataKey.SECURE, "true");
        }

        String ip = discoveryProperties.getIp();
        if (PredicateUtils.isBlank(ip)) {
            ip = findAvailableIp();
            discoveryProperties.setIp(ip);
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

        Integer heartbeatInterval = discoveryProperties.getHeartbeatInterval();
        if (heartbeatInterval != null) {
            metadata.put(HEARTBEAT_INTERVAL, Integer.toString(heartbeatInterval));
        }

        Integer heartbeatTimeout = discoveryProperties.getHeartbeatTimeout();
        if (heartbeatTimeout != null) {
            metadata.put(HEARTBEAT_TIMEOUT, Integer.toString(heartbeatTimeout));
        }

        Integer deathTimeout = discoveryProperties.getDeathTimeout();
        if (deathTimeout != null) {
            metadata.put(DEATH_TIMEOUT, Integer.toString(deathTimeout));
        }
    }

    private String findAvailableIp() throws SocketException {
        String networkInterface = discoveryProperties.getNetworkInterface();
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
            String ipType = discoveryProperties.getIpType();
            if (PredicateUtils.isEmpty(ipType)) {
                String ipv4Address = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
                String ipv6Address = inetIPv6Utils.findIPv6Address();
                if (PredicateUtils.isNotBlank(ipv6Address)) {
                    discoveryProperties.getMetadata().put(DestinoMetadataKey.IPV6, ipv6Address);
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

    public String getService() {
        return discoveryProperties.getService();
    }

    public String getHost() {
        return discoveryProperties.getIp();
    }

    public int getPort() {
        return discoveryProperties.getPort();
    }

    public void setPort(int port) {
        this.discoveryProperties.setPort(port);
    }

    public boolean isSecure() {
        return discoveryProperties.isSecure();
    }

    public Map<String, String> getMetadata() {
        return discoveryProperties.getMetadata();
    }

    public String getNamespace() {
        return discoveryProperties.getNamespace();
    }

    public String getGroup() {
        return discoveryProperties.getGroup();
    }

    public boolean isRegisterEnabled() {
        return discoveryProperties.isRegisterEnabled();
    }

    public String getCluster() {
        return discoveryProperties.getClusterName();
    }

    public float getWeight() {
        return discoveryProperties.getWeight();
    }

    public DestinoDiscoveryProperties getDiscoveryProperties() {
        return discoveryProperties;
    }

    public InetIPv6Utils getInetIPv6Utils() {
        return inetIPv6Utils;
    }

}
