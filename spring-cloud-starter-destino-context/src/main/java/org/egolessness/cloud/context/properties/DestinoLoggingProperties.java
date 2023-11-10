package org.egolessness.cloud.context.properties;

/**
 * Properties for destino logging.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoLoggingProperties {

    private Boolean defaultConfigEnabled;

    private String configPath;

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

}
