package com.test.checkout.datasource;

import com.test.checkout.enums.TenantDatasourceEnum;
import com.test.checkout.exception.NotFoundException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String clientName = req.getHeader("X-ClientID");

        try {
            TenantDatasourceContextHolder.setTenantDatasource(
                    TenantDatasourceEnum.fromTenantId(clientName)
            );
        } catch (NotFoundException ex) {
            ((HttpServletResponse) response).sendError(404, ex.getMessage());
            return;
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantDatasourceContextHolder.setTenantDatasource(
                    TenantDatasourceEnum.marketA
            );
        }
    }
}