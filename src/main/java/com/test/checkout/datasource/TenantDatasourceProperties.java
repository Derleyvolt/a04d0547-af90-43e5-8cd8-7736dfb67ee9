package com.test.checkout.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "tenants")
public class TenantDatasourceProperties {
    private Map<String, DataSourceProperties> datasources;

    @Setter
    @Getter
    public static class DataSourceProperties {
        private String url;
        private String username;
        private String password;
    }
}

