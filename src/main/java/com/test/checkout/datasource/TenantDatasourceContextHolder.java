package com.test.checkout.datasource;

import com.test.checkout.enums.TenantDatasourceEnum;
import org.springframework.util.Assert;

public class TenantDatasourceContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setTenantDatasource(TenantDatasourceEnum datasource) {
        Assert.notNull(datasource, "Datasource cannot be null");
        CONTEXT.set(datasource.name());
    }

    public static String getTenantDatasource() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}