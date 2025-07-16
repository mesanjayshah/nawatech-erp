package io.nawatech.erp.mtenant;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantProvisioningService {

    private final JdbcTemplate masterJdbcTemplate;

    public void provisionTenant(String email) {
        String tenantId = generateTenantId(email); // e.g., tenant_sanjay

        String dbName = tenantId + "_db";
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true";
        String dbUsername = "root";
        String dbPassword = "sanjuSP31@";

        // 1. Create DB
        masterJdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");

        // 2. Optionally: Run schema SQL using Flyway or manual JdbcTemplate on new DB
        // example: runFlywayMigration(dbName);

        // 3. Insert into tenant_table
        masterJdbcTemplate.update("""
            INSERT INTO tenant_table (tenant_id, db_url, db_username, db_password)
            VALUES (?, ?, ?, ?)
        """, tenantId, dbUrl, dbUsername, dbPassword);

        // 4. Map user to tenant
        masterJdbcTemplate.update("""
            INSERT INTO user_tenant_mapping (email, tenant_id)
            VALUES (?, ?)
        """, email, tenantId);
    }

    private String generateTenantId(String email) {
        return "tenant_" + email.split("@")[0].replaceAll("[^a-zA-Z0-9]", "");
    }
}

