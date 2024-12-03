package com.challenge.checkout.exception.tenant;

import com.challenge.checkout.exception.NotFoundException;

public class TenantNotFoundException extends NotFoundException {
    public TenantNotFoundException(String tenantName) {
        super("Tenant: " + tenantName + " not exists");
    }
}
