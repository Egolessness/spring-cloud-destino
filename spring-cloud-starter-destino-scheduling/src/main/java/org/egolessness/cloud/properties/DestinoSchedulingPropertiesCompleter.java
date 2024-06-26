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

import org.egolessness.destino.client.properties.DestinoProperties;
import org.egolessness.destino.client.properties.LoggingProperties;
import org.egolessness.destino.client.properties.SchedulingProperties;
import org.egolessness.cloud.context.properties.DestinoPropertiesCompleter;
import org.egolessness.destino.common.utils.PredicateUtils;

/**
 * Completer for Destino scheduling properties.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingPropertiesCompleter implements DestinoPropertiesCompleter {

    private final DestinoSchedulingProperties destinoSchedulingProperties;

    public DestinoSchedulingPropertiesCompleter(DestinoSchedulingProperties destinoSchedulingProperties) {
        this.destinoSchedulingProperties = destinoSchedulingProperties;
    }

    @Override
    public void complete(DestinoProperties properties) {
        SchedulingProperties schedulingProperties = new SchedulingProperties();
        schedulingProperties.setEnabled(destinoSchedulingProperties.isEnabled());
        schedulingProperties.setExecuteThreadCount(destinoSchedulingProperties.getExecuteThreadCount());
        schedulingProperties.setFeedbackBatchSize(destinoSchedulingProperties.getFeedbackBatchSize());
        schedulingProperties.setFeedbackThreadCount(destinoSchedulingProperties.getFeedbackThreadCount());
        properties.setSchedulingProperties(schedulingProperties);
        if (PredicateUtils.isNotBlank(destinoSchedulingProperties.getLogLevel())) {
            LoggingProperties loggingProperties = properties.getLoggingProperties();
            if (null == loggingProperties) {
                loggingProperties = new LoggingProperties();
                properties.setLoggingProperties(loggingProperties);
            }
            loggingProperties.setRegistrationLogLevel(destinoSchedulingProperties.getLogLevel());
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
