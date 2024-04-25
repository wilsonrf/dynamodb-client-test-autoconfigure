package com.wilsonfranca.test.autoconfigure.dynamodb;

import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbAutoconfiguration;
import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbConnectionDetails;
import com.wilsonfranca.autoconfigure.dynamodb.DynamoDbProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@AutoConfigureBefore(DynamoDbAutoconfiguration.class)
public class DynamoDbTestAutoconfiguration {

    @Bean
    public DynamoDbConnectionDetails propertiesDynamodbTestConnectionDetails(DynamoDbProperties dynamoDbProperties) {
        return new PropertiesDynamodbTestConnectionDetails(dynamoDbProperties);
    }
}
