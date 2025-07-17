package io.nawatech.erp.master.multitenant.config;

import com.zaxxer.hikari.HikariDataSource;
import io.nawatech.erp.master.entity.MasterTenant;
import io.nawatech.erp.master.multitenant.TenantRoutingDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MultiTenantDataSourceConfig {

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/db_master");
        ds.setUsername("root");
        ds.setPassword("sanjuSP31@");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    @Bean
    public DataSource dataSource(DataSource masterDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource());

        List<MasterTenant> tenants = jdbcTemplate.query(
                "SELECT tenant_id, db_url, db_username, db_password FROM master_tenant",
                (rs, rowNum) -> new MasterTenant(
                        rs.getString("tenant_id"),
                        rs.getString("db_url"),
                        rs.getString("db_username"),
                        rs.getString("db_password")
                )
        );

        Map<Object, Object> targetDataSources = new HashMap<>();
        for (MasterTenant tenant : tenants) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(tenant.getDbUrl());
            ds.setUsername(tenant.getDbUsername());
            ds.setPassword(tenant.getDbPassword());
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            targetDataSources.put(tenant.getTenantId(), ds);
        }

        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);

        if (targetDataSources.isEmpty()) {
            log.warn("⚠️  No tenants found — using masterDataSource as default target.");
            routingDataSource.setDefaultTargetDataSource(masterDataSource);
        } else {
            routingDataSource.setDefaultTargetDataSource(targetDataSources.values().iterator().next());
        }

        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }

    @Bean
    public TenantRoutingDataSource tenantRoutingDataSource() {
        // Optionally initialize with target data sources if you want here
        return new TenantRoutingDataSource();
    }

}
