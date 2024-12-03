package com.test.checkout.datasource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSourceRouting extends AbstractRoutingDataSource {
    @Override
    protected String determineCurrentLookupKey() {
        return TenantDatasourceContextHolder.getTenantDatasource();
    }
}