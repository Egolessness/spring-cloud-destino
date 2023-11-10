package org.egolessness.cloud.context;

import org.egolessness.destino.client.scheduling.script.ScriptInstanceFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * Factory for destino script instance.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class SpringScriptInstanceFactory implements ScriptInstanceFactory {

    private final ApplicationContext context;

    public SpringScriptInstanceFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object createInstance(Class<?> type) {
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        return beanFactory.createBean(type, AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, false);
    }

}
