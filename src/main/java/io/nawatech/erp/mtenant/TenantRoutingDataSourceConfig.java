/*package io.nawatech.erp.mtenant;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TenantRoutingDataSourceConfig {

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Bean
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        // Create your tenant DataSources
        DataSource defaultDataSource = buildDataSource("default_db");
        DataSource tenant1DataSource = buildDataSource("tenant1_db");
        DataSource tenant2DataSource = buildDataSource("tenant2_db");

        targetDataSources.put("default", defaultDataSource);
        targetDataSources.put("tenant1", tenant1DataSource);
        targetDataSources.put("tenant2", tenant2DataSource);

        // Setup routing data source
        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(defaultDataSource);
        routingDataSource.afterPropertiesSet(); // <=== REQUIRED

        return routingDataSource;
    }

    private DataSource buildDataSource(String dbName) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true");
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);
        ds.setDriverClassName(dbDriverClassName);
        ds.setMaximumPoolSize(5);
        ds.setMinimumIdle(1);
        return ds;
    }
}*/
