package com.test.checkout.exception.tenant;

import com.test.checkout.exception.NotFoundException;

public class TenantNotFoundException extends NotFoundException {
    public TenantNotFoundException(String tenantName) {
        super("Tenant: " + tenantName + " not exists");
    }
}
