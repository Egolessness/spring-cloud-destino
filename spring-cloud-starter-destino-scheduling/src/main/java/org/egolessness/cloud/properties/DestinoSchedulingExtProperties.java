/*
 * Copyright (c) 2024 by Kang Wang. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.egolessness.cloud.properties;

import org.egolessness.destino.common.enumeration.RegisterMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egolessness.destino.common.constant.DefaultConstants.*;

/**
 * EProperties for Destino scheduling ext.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@ConfigurationProperties("spring.cloud.destino.scheduling")
public class DestinoSchedulingExtProperties extends DestinoSchedulingProperties {

	/**
	 * server address.
	 */
	private List<String> address;

	/**
	 * read server addresses from provider url.
	 */
	private String addressProvider;

	/**
	 * authentication username.
	 */
	private String username;

	/**
	 * authentication password.
	 */
	private String password;

	/**
	 * authentication encrypted password.
	 */
	private String encryptedPassword;

	/**
	 * authentication access token.
	 */
	private String accessToken;

	/**
	 * namespace.
	 */
	@Value("${spring.cloud.destino.scheduling.namespace:${spring.cloud.destino.namespace:public}}")
	private String namespace = REGISTRATION_NAMESPACE;

	/**
	 * group.
	 */
	private String group = REGISTRATION_GROUP;

	/**
	 * service name.
	 */
	@Value("${spring.cloud.destino.scheduling.service:${spring.application.name:}}")
	private String service;

	/**
	 * cluster name.
	 */
	private String clusterName = REGISTRATION_CLUSTER;

	/**
	 * service ip
	 */
	@Value("${spring.cloud.destino.scheduling.ip:${spring.cloud.client.ip-address:}}")
	private String ip;

	/**
	 * IPv4 or IPv6.
	 */
	private String ipType;

	/**
	 * network interface.
	 */
	private String networkInterface;

	/**
	 * service port.
	 */
	private int port = -1;

	/**
	 * safety register.
	 */
	private boolean safety;

	/**
	 * register mode.
	 */
	private RegisterMode registerMode;

	/**
	 * weight for service instance, the larger the value, the larger the weight.
	 */
	private float weight = 1;

	/**
	 * enable to permit other service‘s request.
	 */
	private boolean instanceEnabled = true;

	/**
	 * heartbeat interval (millisecond).
	 */
	private Integer heartbeatInterval;

	/**
	 * heartbeat timeout (millisecond).
	 */
	private Integer heartbeatTimeout;

	/**
	 * death timeout (millisecond).
	 */
	private Integer deathTimeout;

	/**
	 * Customize health check url to override default.
	 */
	private String healthCheckUrl;

	/**
	 * metadata for register.
	 */
	private Map<String, String> metadata = new HashMap<>();

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public String getAddressProvider() {
		return addressProvider;
	}

	public void setAddressProvider(String addressProvider) {
		this.addressProvider = addressProvider;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getNetworkInterface() {
		return networkInterface;
	}

	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSafety() {
		return safety;
	}

	public void setSafety(boolean safety) {
		this.safety = safety;
	}

	public RegisterMode getRegisterMode() {
		return registerMode;
	}

	public void setRegisterMode(RegisterMode registerMode) {
		this.registerMode = registerMode;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public boolean isInstanceEnabled() {
		return instanceEnabled;
	}

	public void setInstanceEnabled(boolean instanceEnabled) {
		this.instanceEnabled = instanceEnabled;
	}

	public Integer getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public void setHeartbeatInterval(Integer heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}

	public Integer getHeartbeatTimeout() {
		return heartbeatTimeout;
	}

	public void setHeartbeatTimeout(Integer heartbeatTimeout) {
		this.heartbeatTimeout = heartbeatTimeout;
	}

	public Integer getDeathTimeout() {
		return deathTimeout;
	}

	public void setDeathTimeout(Integer deathTimeout) {
		this.deathTimeout = deathTimeout;
	}

	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}

	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}


	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "DestinoSchedulingProperties{" +
				"address=" + address +
				", addressProvider='" + addressProvider + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", encryptedPassword='" + encryptedPassword + '\'' +
				", accessToken='" + accessToken + '\'' +
				", namespace='" + namespace + '\'' +
				", group='" + group + '\'' +
				", service='" + service + '\'' +
				", clusterName='" + clusterName + '\'' +
				", ip='" + ip + '\'' +
				", port=" + port +
				", safety=" + safety +
				", registerMode=" + registerMode +
				", weight=" + weight +
				", instanceEnabled=" + instanceEnabled +
				", heartbeatInterval=" + heartbeatInterval +
				", heartbeatTimeout=" + heartbeatTimeout +
				", deathTimeout=" + deathTimeout +
				", healthCheckUrl='" + healthCheckUrl + '\'' +
				", metadata=" + metadata +
				'}';
	}
}
