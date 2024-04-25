/*
 * Copyright 2024 Wilson da Rocha França
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wilsonfranca.test.autoconfigure.dynamodb;

import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbConnectionDetails;
import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbProperties;

/**
 * Adapts {@link DynamoDbProperties} to a {@link DynamoDbConnectionDetails} for tests.
 */
public class PropertiesDynamodbTestConnectionDetails implements DynamoDbConnectionDetails {

    /**
     * The default value for dynamodb endpoint-override.
     */
    private static final String DEFAULT_ENDPOINT_OVERRIDE = "http://localhost:8000";

    /**
     * The {@link DynamoDbProperties} to be adapted.
     */
    private final DynamoDbProperties dynamoDbProperties;

    public PropertiesDynamodbTestConnectionDetails(DynamoDbProperties dynamoDbProperties) {
        this.dynamoDbProperties = dynamoDbProperties;
    }

    @Override
    public String endpointOverride() {
        if (this.dynamoDbProperties.getEndpointOverride() != null) {
            return this.dynamoDbProperties.getEndpointOverride();
        }
        return DEFAULT_ENDPOINT_OVERRIDE;
    }
}
