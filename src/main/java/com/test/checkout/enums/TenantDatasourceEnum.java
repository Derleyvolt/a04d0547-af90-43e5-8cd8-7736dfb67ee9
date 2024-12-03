package com.test.checkout.enums;

import com.test.checkout.exception.NotFoundException;

public enum TenantDatasourceEnum {
    marketA,
    marketB;

    public static TenantDatasourceEnum fromTenantId(String tenantName) {
         return switch (tenantName) {
            case "marketA" -> marketA;
            case "marketB" -> marketB;
            // Adicione outros casos conforme necessário
            default -> throw new NotFoundException("Tenant " + tenantName + " does not exist");
        };
    }
}
