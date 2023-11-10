package org.egolessness.cloud.instance;

import org.egolessness.destino.common.enumeration.Mark;
import org.egolessness.destino.common.model.ServiceInstance;
import org.egolessness.destino.common.utils.PredicateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Converter for {@link ServiceInstance} -> {@link DestinoServiceInstance}.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public enum DestinoInstanceConverter implements BiFunction<ServiceInstance, String, DestinoServiceInstance> {

    INSTANCE;

    @Override
    public DestinoServiceInstance apply(ServiceInstance instance, String serviceId) {
        DestinoServiceInstance destinoServiceInstance = new DestinoServiceInstance();
        destinoServiceInstance.setServiceId(serviceId);
        destinoServiceInstance.setHost(instance.getIp());
        destinoServiceInstance.setPort(instance.getPort());

        String instanceId = Mark.UNDERLINE.join(instance.getServiceName(), instance.getCluster(),
                instance.getIp(), instance.getPort());
        destinoServiceInstance.setInstanceId(instanceId);

        Map<String, String> metadata = new HashMap<>();
        metadata.put(DestinoInstanceMetaKey.WEIGHT,  Double.toString(instance.getWeight()));
        metadata.put(DestinoInstanceMetaKey.HEALTHY, Boolean.toString(instance.isHealthy()));
        if (PredicateUtils.isNotBlank(instance.getCluster())) {
            metadata.put(DestinoInstanceMetaKey.CLUSTER, instance.getCluster());
        }
        if (null != instance.getMode()) {
            metadata.put(DestinoInstanceMetaKey.REGISTER_MODE, instance.getMode().name());
        }
        if (null != instance.getMetadata()) {
            metadata.putAll(instance.getMetadata());
        }
        destinoServiceInstance.setMetadata(metadata);

        String secureString = metadata.get(DestinoInstanceMetaKey.SECURE);
        if (PredicateUtils.isNotBlank(secureString)) {
            boolean secure = Boolean.parseBoolean(secureString);
            destinoServiceInstance.setSecure(secure);
        }
        return destinoServiceInstance;
    }

}
