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

package org.egolessness.cloud.instance;

import org.egolessness.cloud.context.DestinoMetadataKey;
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
        metadata.put(DestinoMetadataKey.WEIGHT,  Double.toString(instance.getWeight()));
        metadata.put(DestinoMetadataKey.HEALTHY, Boolean.toString(instance.isHealthy()));
        if (PredicateUtils.isNotBlank(instance.getCluster())) {
            metadata.put(DestinoMetadataKey.CLUSTER, instance.getCluster());
        }
        if (null != instance.getMode()) {
            metadata.put(DestinoMetadataKey.REGISTER_MODE, instance.getMode().name());
        }
        if (null != instance.getMetadata()) {
            metadata.putAll(instance.getMetadata());
        }
        destinoServiceInstance.setMetadata(metadata);

        String secureString = metadata.get(DestinoMetadataKey.SECURE);
        if (PredicateUtils.isNotBlank(secureString)) {
            boolean secure = Boolean.parseBoolean(secureString);
            destinoServiceInstance.setSecure(secure);
        }
        return destinoServiceInstance;
    }

}
