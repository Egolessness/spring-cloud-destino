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

/**
 * Properties for Destino scheduling.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoSchedulingProperties {

    private boolean enabled = true;

    private int executeThreadCount;

    private int feedbackBatchSize;

    private int feedbackThreadCount;

    private String logLevel;

    public DestinoSchedulingProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getExecuteThreadCount() {
        return this.executeThreadCount;
    }

    public void setExecuteThreadCount(int executeThreadCount) {
        this.executeThreadCount = executeThreadCount;
    }

    public int getFeedbackBatchSize() {
        return this.feedbackBatchSize;
    }

    public void setFeedbackBatchSize(int feedbackBatchSize) {
        this.feedbackBatchSize = feedbackBatchSize;
    }

    public int getFeedbackThreadCount() {
        return this.feedbackThreadCount;
    }

    public void setFeedbackThreadCount(int feedbackThreadCount) {
        this.feedbackThreadCount = feedbackThreadCount;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

}