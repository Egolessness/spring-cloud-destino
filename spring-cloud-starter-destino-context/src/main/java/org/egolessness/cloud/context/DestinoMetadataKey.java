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

package org.egolessness.cloud.context;

/**
 * Destino service instance metadata common key.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoMetadataKey {

    public static final String CLUSTER = "instance.cluster";

    public static final String WEIGHT = "instance.weight";

    public static final String HEALTHY = "instance.healthy";

    public static final String REGISTER_MODE = "instance.register-mode";

    public static final String SECURE = "secure";

    public static final String IPV6 = "IPv6";

    public static final String IPV4 = "IPv4";

    public static final String MANAGEMENT_PORT = "management.port";

    public static final String MANAGEMENT_CONTEXT_PATH = "management.context-path";

    public static final String MANAGEMENT_ADDRESS = "management.address";

    public static final String MANAGEMENT_ENDPOINT_BASE_PATH = "management.endpoints.web.base-path";

}
