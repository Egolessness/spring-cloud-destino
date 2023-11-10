package org.egolessness.cloud.properties;

import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.cloud.context.properties.DestinoPropertiesCompleter;
import org.egolessness.destino.common.utils.PredicateUtils;

/**
 * Completer for Destino scheduling ext properties.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingExtPropertiesCompleter implements DestinoPropertiesCompleter {

    private final DestinoSchedulingExtProperties schedulingExtProperties;

    public DestinoSchedulingExtPropertiesCompleter(DestinoSchedulingExtProperties schedulingExtProperties) {
        this.schedulingExtProperties = schedulingExtProperties;
    }

    @Override
    public void complete(DestinoProperties properties) {
        if (PredicateUtils.isNotEmpty(schedulingExtProperties.getAddress())) {
            for (String address : schedulingExtProperties.getAddress()) {
                properties.addAddress(address);
            }
        }
        if (PredicateUtils.isBlank(properties.getAddressesProviderUrl())) {
            properties.setAddressesProviderUrl(properties.getAddressesProviderUrl());
        }
        if (PredicateUtils.isEmpty(properties.getUsername())) {
            properties.setUsername(schedulingExtProperties.getUsername());
        }
        if (PredicateUtils.isEmpty(properties.getPassword())) {
            properties.setPassword(schedulingExtProperties.getPassword());
        }
        if (PredicateUtils.isEmpty(properties.getEncryptedPassword())) {
            properties.setEncryptedPassword(schedulingExtProperties.getEncryptedPassword());
        }
        if (PredicateUtils.isEmpty(properties.getAccessToken())) {
            properties.setAccessToken(schedulingExtProperties.getAccessToken());
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
