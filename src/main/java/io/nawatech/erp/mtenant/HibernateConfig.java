package io.nawatech.erp.mtenant;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "io.nawatech.erp")
public class HibernateConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSourceMultiTenantConnectionProviderImpl multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver tenantResolver) {

        Map<String, Object> hibernateProps = new HashMap<>();
        hibernateProps.put("hibernate.multiTenancy", "DATABASE");
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
        hibernateProps.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        hibernateProps.put(Environment.HBM2DDL_AUTO, "update");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("io.nawatech.erp");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(hibernateProps);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

