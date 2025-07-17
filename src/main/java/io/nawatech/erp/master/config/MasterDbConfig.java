package io.nawatech.erp.master.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EntityScan("io.nawatech.erp.mtenant")
@EnableJpaRepositories(
        basePackages = "io.nawatech.erp.master",
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterDbConfig {

    private final DataSource masterDataSource;

    public MasterDbConfig(@Qualifier("masterDataSource") DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource);
        em.setPackagesToScan("io.nawatech.erp.master");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
