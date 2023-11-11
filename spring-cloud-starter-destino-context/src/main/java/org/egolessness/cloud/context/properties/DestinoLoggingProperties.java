package org.egolessness.cloud.context.properties;

import org.egolessness.destino.client.properties.LoggingProperties;

/**
 * Properties for destino logging.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoLoggingProperties {

    private Boolean defaultConfigEnabled;

    private String configPath;

    private String logPath;

    private Integer maxCount;

    private String fileSize;

    private String defaultLogLevel;

    private String remoteLogLevel;

    private String registrationLogLevel;

    private String schedulingLogLevel;

    public DestinoLoggingProperties() {
    }

    public Boolean getDefaultConfigEnabled() {
        return defaultConfigEnabled;
    }

    public void setDefaultConfigEnabled(Boolean defaultConfigEnabled) {
        this.defaultConfigEnabled = defaultConfigEnabled;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDefaultLogLevel() {
        return defaultLogLevel;
    }

    public void setDefaultLogLevel(String defaultLogLevel) {
        this.defaultLogLevel = defaultLogLevel;
    }

    public String getRemoteLogLevel() {
        return remoteLogLevel;
    }

    public void setRemoteLogLevel(String remoteLogLevel) {
        this.remoteLogLevel = remoteLogLevel;
    }

    public String getRegistrationLogLevel() {
        return registrationLogLevel;
    }

    public void setRegistrationLogLevel(String registrationLogLevel) {
        this.registrationLogLevel = registrationLogLevel;
    }

    public String getSchedulingLogLevel() {
        return schedulingLogLevel;
    }

    public void setSchedulingLogLevel(String schedulingLogLevel) {
        this.schedulingLogLevel = schedulingLogLevel;
    }

    public LoggingProperties toLoggingProperties() {
        LoggingProperties loggingProperties = new LoggingProperties();
        loggingProperties.setDefaultConfigEnabled(defaultConfigEnabled);
        loggingProperties.setConfigPath(configPath);
        loggingProperties.setLogPath(logPath);
        loggingProperties.setFileSize(fileSize);
        loggingProperties.setMaxCount(maxCount);
        loggingProperties.setDefaultLogLevel(defaultLogLevel);
        loggingProperties.setRemoteLogLevel(remoteLogLevel);
        loggingProperties.setRegistrationLogLevel(registrationLogLevel);
        loggingProperties.setSchedulingLogLevel(schedulingLogLevel);
        return loggingProperties;
    }

}
