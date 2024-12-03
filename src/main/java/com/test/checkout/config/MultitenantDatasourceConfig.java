package com.test.checkout.config;

import com.test.checkout.datasource.MultitenantDataSourceRouting;
import com.test.checkout.datasource.TenantDatasourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultitenantDatasourceConfig {
    @Value("${tenants.default_market}")
    private String DEFAULT_TENANT;

    @Autowired
    private TenantDatasourceProperties tenantDatasourceProperties;

    @Bean
    public DataSource dataSource() {
         Map<Object, Object> resolvedDataSources = new HashMap<>();

        tenantDatasourceProperties.getDatasources().forEach((tenantId, properties) -> {
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
            dataSourceBuilder.username(properties.getUsername());
            dataSourceBuilder.password(properties.getPassword());
            dataSourceBuilder.url(properties.getUrl());

            DataSource dataSource = dataSourceBuilder.build();

            if (dataSource == null) {
                throw new RuntimeException("DataSource is null for tenant: " + tenantId);
            }

            resolvedDataSources.put(tenantId, dataSource);
        });

        AbstractRoutingDataSource dataSource = new MultitenantDataSourceRouting();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(DEFAULT_TENANT));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

}