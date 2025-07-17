package io.nawatech.erp.master.multitenant;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantProvisioningService {

    private final JdbcTemplate masterJdbcTemplate;
    private final TenantRoutingDataSource routingDataSource;

    public void provisionTenant(String email) {
        // 1. Generate tenantId & DB details
        String tenantId = generateTenantId(email); // e.g. tenant_sanjay
        String dbName = tenantId + "_db";
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "sanjuSP31@";

        // 2. Create the tenant database if it does not exist
        masterJdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");

        // 3. Run Flyway migration on the new tenant DB
        runFlywayMigration(dbUrl, dbUsername, dbPassword);

        // 4. Insert tenant metadata into master DB (tenant_table)
        masterJdbcTemplate.update("""
            INSERT INTO master_tenant (tenant_id, db_url, db_username, db_password)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE db_url=VALUES(db_url), db_username=VALUES(db_username), db_password=VALUES(db_password)
            """, tenantId, dbUrl, dbUsername, dbPassword);

        // 5. Add tenant's DataSource to routing datasource dynamically
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        routingDataSource.addTargetDataSource(tenantId, ds);
    }

    private String generateTenantId(String email) {
        return "tenant_" + email.split("@")[0].replaceAll("[^a-zA-Z0-9]", "");
    }

    private void runFlywayMigration(String url, String username, String password) {
        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .locations("classpath:db/migration") // your migration scripts location
                .baselineOnMigrate(true)              // handles existing schema gracefully
                .load();
        flyway.migrate();
    }
}