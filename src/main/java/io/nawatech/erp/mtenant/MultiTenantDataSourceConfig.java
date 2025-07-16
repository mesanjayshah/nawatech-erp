package io.nawatech.erp.mtenant;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MultiTenantDataSourceConfig {

    // Master DataSource connecting to tenant metadata DB
    @Bean
    @Primary
    public DataSource masterDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/db_master");
        ds.setUsername("root");
        ds.setPassword("sanjuSP31@");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    // Tenant routing DataSource dynamically configured from tenant metadata DB
    @Bean
    public DataSource dataSource(DataSource masterDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);

        List<TenantInfo> tenants = jdbcTemplate.query(
                "SELECT tenant_id, db_url, db_username, db_password FROM tenant_table",
                (rs, rowNum) -> new TenantInfo(
                        rs.getString("tenant_id"),
                        rs.getString("db_url"),
                        rs.getString("db_username"),
                        rs.getString("db_password"))
        );

        Map<Object, Object> targetDataSources = new HashMap<>();
        for (TenantInfo tenant : tenants) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(tenant.getDbUrl());
            ds.setUsername(tenant.getDbUsername());
            ds.setPassword(tenant.getDbPassword());
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            targetDataSources.put(tenant.getTenantId(), ds);
        }

        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(targetDataSources.values().iterator().next());
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    // Simple POJO to hold tenant info
    public static class TenantInfo {
        private final String tenantId;
        private final String dbUrl;
        private final String dbUsername;
        private final String dbPassword;

        public TenantInfo(String tenantId, String dbUrl, String dbUsername, String dbPassword) {
            this.tenantId = tenantId;
            this.dbUrl = dbUrl;
            this.dbUsername = dbUsername;
            this.dbPassword = dbPassword;
        }


        public String getTenantId() { return tenantId; }
        public String getDbUrl() { return dbUrl; }
        public String getDbUsername() { return dbUsername; }
        public String getDbPassword() { return dbPassword; }
    }
}

