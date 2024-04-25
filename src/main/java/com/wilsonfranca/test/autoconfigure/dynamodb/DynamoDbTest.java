package com.wilsonfranca.test.autoconfigure.dynamodb;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Inherited
@Documented
@AutoConfigureCache
@AutoConfigureDynamoDb
@ImportAutoConfiguration
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@OverrideAutoConfiguration(enabled = false)
public @interface DynamoDbTest {
}
