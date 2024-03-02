
package com.example.lucy;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@org.springframework.stereotype.Component
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "app.some-config")
public final class ConfProp{
    public String test;
}
