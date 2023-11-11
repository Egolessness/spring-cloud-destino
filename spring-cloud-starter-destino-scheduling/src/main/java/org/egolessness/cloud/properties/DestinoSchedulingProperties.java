package org.egolessness.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for Destino scheduling.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
@ConfigurationProperties("spring.cloud.destino.scheduling")
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