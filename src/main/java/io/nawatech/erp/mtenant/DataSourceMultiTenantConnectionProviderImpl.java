package io.nawatech.erp.mtenant;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import javax.sql.DataSource;
import java.util.Map;

@RequiredArgsConstructor
public class DataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private final Map<String, DataSource> tenantDataSources;

    @Override
    protected DataSource selectAnyDataSource() {
        return tenantDataSources.get("default");
    }

    @Override
    protected DataSource selectDataSource(Object tenantIdentifier) {
        return tenantDataSources.getOrDefault(tenantIdentifier, tenantDataSources.get("default"));
    }
}
