package org.egolessness.cloud.context.properties;

import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.destino.client.properties.ReceiverProperties;
import org.egolessness.destino.common.exception.DestinoException;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.List;

/**
 * Properties for destino.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@ConfigurationProperties("spring.cloud.destino")
public class DestinoContextProperties {

    /**
     * destino server address.
     */
    private List<String> address;

    /**
     * the domain name of a service, through which the server address can be dynamically
     * obtained.
     */
    private String addressProvider;

    /**
     * namespace.
     */
    private String namespace;

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

    private String snapshotDir;

    private String accessToken;

    private DestinoLoggingProperties logging;

    private DestinoRequestProperties request;

    private ReceiverProperties receiver;

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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public String getSnapshotDir() {
        return snapshotDir;
    }

    public void setSnapshotDir(String snapshotDir) {
        this.snapshotDir = snapshotDir;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public DestinoLoggingProperties getLogging() {
        return logging;
    }

    public void setLogging(DestinoLoggingProperties logging) {
        this.logging = logging;
    }

    public DestinoRequestProperties getRequest() {
        return request;
    }

    public void setRequest(DestinoRequestProperties request) {
        this.request = request;
    }

    public ReceiverProperties getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverProperties receiver) {
        this.receiver = receiver;
    }

    public DestinoProperties toDestinoProperties(List<DestinoPropertiesCompleter> completerList) throws DestinoException {
        DestinoProperties properties = new DestinoProperties();
        properties.setAddresses(address);
        properties.setAddressesProviderUrl(addressProvider);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setEncryptedPassword(encryptedPassword);
        properties.setSnapshotPath(snapshotDir);
        properties.setAccessToken(accessToken);
        properties.setReceiverProperties(receiver);
        if (null != request) {
            properties.setRequestProperties(request.toRequestProperties());
            properties.setRepeaterProperties(request.toRepeaterProperties());
        }
        if (null != logging) {
            properties.setLoggingDefaultConfigEnabled(logging.getDefaultConfigEnabled());
            properties.setLoggingConfigPath(logging.getConfigPath());
        }
        if (PredicateUtils.isNotEmpty(completerList)) {
            completerList.sort(Comparator.comparingInt(Ordered::getOrder));
            for (DestinoPropertiesCompleter completer : completerList) {
                completer.complete(properties);
            }
        }
        return properties;
    }

}
