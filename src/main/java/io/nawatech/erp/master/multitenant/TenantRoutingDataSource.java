package io.nawatech.erp.master.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    // Hold tenant DataSources
    private final Map<Object, Object> targetDataSources = new HashMap<>();

    public TenantRoutingDataSource() {
        super();
        // Initially set empty target data sources
        super.setTargetDataSources(targetDataSources);
    }

    /**
     * This method determines the current lookup key (tenant id) for routing.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String tenantId = TenantContext.getTenant();
        System.out.println("🔑 Using tenant: " + tenantId);
        return tenantId;
    }

    /**
     * Add a new tenant DataSource dynamically at runtime.
     */
    public synchronized void addTargetDataSource(String tenantId, DataSource dataSource) {
        // Add new tenant DataSource
        targetDataSources.put(tenantId, dataSource);

        // Update parent's targetDataSources map
        super.setTargetDataSources(targetDataSources);

        // This call refreshes the resolvedDataSources cache internally
        super.afterPropertiesSet();
    }
}