package com.wilsonfranca.test.autoconfigure.dynamodb;

import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbConnectionDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.assertj.core.api.Assertions.assertThat;

@DynamoDbTest
public class DynamoDbTestIntegrationTest {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbConnectionDetails dynamoDbConnectionDetails;

    @Test
    public void testDynamoDbWillBeOnTheDefaultUrlOverride() {

        assertThat(dynamoDbConnectionDetails).isInstanceOf(PropertiesDynamodbTestConnectionDetails.class);
        assertThat(dynamoDbConnectionDetails.endpointOverride()).isEqualTo("http://localhost:8000");
    }
}
