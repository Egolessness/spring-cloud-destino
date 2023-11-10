package org.egolessness.cloud.context.properties;

import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.destino.common.exception.DestinoException;
import org.springframework.core.Ordered;

/**
 * interface for destino properties .
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public interface DestinoPropertiesCompleter extends Ordered {

    void complete(DestinoProperties properties) throws DestinoException;

}
