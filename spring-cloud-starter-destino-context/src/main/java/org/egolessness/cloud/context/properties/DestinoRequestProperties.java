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

package org.egolessness.cloud.context.properties;

import org.egolessness.destino.client.properties.RepeaterProperties;
import org.egolessness.destino.common.enumeration.RequestChannel;
import org.egolessness.destino.common.properties.HttpProperties;
import org.egolessness.destino.common.properties.RequestProperties;
import org.egolessness.destino.common.properties.TlsProperties;
import org.egolessness.destino.common.utils.PredicateUtils;

import java.time.Duration;

/**
 * Properties for destino request .
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoRequestProperties {

    private String channel;

    private long timeoutMillis;

    private long keepaliveMillis;

    private long keepaliveTimeoutMillis;

    private int maxInboundMessageSize;

    private boolean tlsEnabled;

    private long connectTimeoutMillis;

    private long readTimeoutMillis;

    private long connectionRequestTimeoutMillis;

    private boolean contentCompressionEnabled;

    private int maxRedirects;

    private String userAgent;

    private int maxConnTotal;

    private int maxConnPerRoute;

    private int ioThreadCount;

    private long connectionTimeToLiveMillis;

    private int repeaterThreadCount;

    private long repeaterPeriodMillis;

    public DestinoRequestProperties() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getKeepaliveMillis() {
        return keepaliveMillis;
    }

    public void setKeepaliveMillis(long keepaliveMillis) {
        this.keepaliveMillis = keepaliveMillis;
    }

    public long getKeepaliveTimeoutMillis() {
        return keepaliveTimeoutMillis;
    }

    public void setKeepaliveTimeoutMillis(long keepaliveTimeoutMillis) {
        this.keepaliveTimeoutMillis = keepaliveTimeoutMillis;
    }

    public boolean isTlsEnabled() {
        return tlsEnabled;
    }

    public void setTlsEnabled(boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    public int getMaxInboundMessageSize() {
        return maxInboundMessageSize;
    }

    public void setMaxInboundMessageSize(int maxInboundMessageSize) {
        this.maxInboundMessageSize = maxInboundMessageSize;
    }

    public long getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(long connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public long getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public void setReadTimeoutMillis(long readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
    }

    public long getConnectionRequestTimeoutMillis() {
        return connectionRequestTimeoutMillis;
    }

    public void setConnectionRequestTimeoutMillis(long connectionRequestTimeoutMillis) {
        this.connectionRequestTimeoutMillis = connectionRequestTimeoutMillis;
    }

    public boolean isContentCompressionEnabled() {
        return contentCompressionEnabled;
    }

    public void setContentCompressionEnabled(boolean contentCompressionEnabled) {
        this.contentCompressionEnabled = contentCompressionEnabled;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public int getIoThreadCount() {
        return ioThreadCount;
    }

    public void setIoThreadCount(int ioThreadCount) {
        this.ioThreadCount = ioThreadCount;
    }

    public long getConnectionTimeToLiveMillis() {
        return connectionTimeToLiveMillis;
    }

    public void setConnectionTimeToLiveMillis(long connectionTimeToLiveMillis) {
        this.connectionTimeToLiveMillis = connectionTimeToLiveMillis;
    }

    public int getRepeaterThreadCount() {
        return repeaterThreadCount;
    }

    public void setRepeaterThreadCount(int repeaterThreadCount) {
        this.repeaterThreadCount = repeaterThreadCount;
    }

    public long getRepeaterPeriodMillis() {
        return repeaterPeriodMillis;
    }

    public void setRepeaterPeriodMillis(long repeaterPeriodMillis) {
        this.repeaterPeriodMillis = repeaterPeriodMillis;
    }

    public RequestProperties toRequestProperties() {
        RequestProperties requestProperties = new RequestProperties();
        if (PredicateUtils.isNotBlank(channel)) {
            for (RequestChannel requestChannel : RequestChannel.values()) {
                if (requestChannel.name().equalsIgnoreCase(channel)) {
                    requestProperties.setRequestChannel(requestChannel);
                }
            }
        }
        if (timeoutMillis > 0) {
            requestProperties.setRequestTimeout(Duration.ofMillis(timeoutMillis));
        }
        if (keepaliveMillis > 0) {
            requestProperties.setKeepaliveTimeout(Duration.ofMillis(keepaliveMillis));
        }
        if (keepaliveTimeoutMillis > 0) {
            requestProperties.setKeepaliveTimeout(Duration.ofMillis(keepaliveTimeoutMillis));
        }
        if (maxInboundMessageSize > 0) {
            requestProperties.setMaxInboundMessageSize(maxInboundMessageSize);
        }
        TlsProperties tlsProperties = new TlsProperties();
        tlsProperties.setEnabled(tlsEnabled);
        requestProperties.setTlsProperties(tlsProperties);

        HttpProperties httpProperties = new HttpProperties();
        if (connectTimeoutMillis > 0) {
            httpProperties.setConnectTimeout(Duration.ofMillis(connectTimeoutMillis));
        }
        if (readTimeoutMillis > 0) {
            httpProperties.setReadTimeout(Duration.ofMillis(readTimeoutMillis));
        }
        if (connectionRequestTimeoutMillis > 0) {
            httpProperties.setConnectionRequestTimeout(Duration.ofMillis(connectionRequestTimeoutMillis));
        }
        if (connectionTimeToLiveMillis > 0) {
            httpProperties.setConnectionRequestTimeout(Duration.ofMillis(connectionTimeToLiveMillis));
        }
        httpProperties.setContentCompressionEnabled(contentCompressionEnabled);
        httpProperties.setMaxRedirects(maxRedirects);
        httpProperties.setUserAgent(userAgent);
        httpProperties.setMaxConnTotal(maxConnTotal);
        httpProperties.setMaxConnPerRoute(maxConnPerRoute);
        httpProperties.setIoThreadCount(ioThreadCount);
        requestProperties.setHttpProperties(httpProperties);
        return requestProperties;
    }

    public RepeaterProperties toRepeaterProperties() {
        RepeaterProperties repeaterProperties = new RepeaterProperties();
        repeaterProperties.setThreadCount(repeaterThreadCount);
        if (repeaterPeriodMillis > 0) {
            repeaterProperties.setPeriod(Duration.ofMillis(repeaterPeriodMillis));
        }
        return repeaterProperties;
    }

}