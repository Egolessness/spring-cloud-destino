package org.egolessness.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

/**
 * Condition if destino scheduling is enabled.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ConditionalOnProperty(value = "spring.cloud.destino.scheduling.enabled", matchIfMissing = true)
public @interface ConditionalOnDestinoSchedulingEnabled {

}
