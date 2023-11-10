package org.egolessness.cloud.context;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Inherited
@ConditionalOnProperty(value = "spring.cloud.destino.enabled", matchIfMissing = true)
public @interface ConditionalOnDestinoEnabled {

}