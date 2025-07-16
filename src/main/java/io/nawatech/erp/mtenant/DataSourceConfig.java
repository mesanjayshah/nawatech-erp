package io.nawatech.erp.mtenant;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSourceMultiTenantConnectionProviderImpl multiTenantConnectionProvider() {
        Map<String, DataSource> dataSources = new HashMap<>();

        // Replace it with dynamic lookup from DB if needed
        dataSources.put("default", buildDataSource("default_db"));
        dataSources.put("tenant1", buildDataSource("tenant1_db"));
        dataSources.put("tenant2", buildDataSource("tenant2_db"));



        return new DataSourceMultiTenantConnectionProviderImpl(dataSources);
    }

    private DataSource buildDataSource(String dbName) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/" + dbName);
        ds.setUsername("root");
        ds.setPassword("sanjuSP31@");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new LoginBasedTenantIdentifierResolver();
    }
}

