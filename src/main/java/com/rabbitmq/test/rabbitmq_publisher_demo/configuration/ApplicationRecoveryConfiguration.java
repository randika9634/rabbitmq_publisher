package com.rabbitmq.test.rabbitmq_publisher_demo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.rabbitmq.test.rabbitmq_publisher_demo",
        entityManagerFactoryRef = "EntityManager",
        transactionManagerRef = "TransactionManager"
)
public class ApplicationRecoveryConfiguration {

    private final Environment environment;

    @Autowired
    public ApplicationRecoveryConfiguration(Environment environment) {
        this.environment = environment;
    }


    @Bean(name = "EntityManager")
    public LocalContainerEntityManagerFactoryBean healthEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(recoveryDataSource());
        em.setPackagesToScan(
                new String[]{"com.rabbitmq.test.rabbitmq_publisher_demo.model"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                "none");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.MySQL5Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(destroyMethod = "")
    @Qualifier("affinitiDataSource")
    public DataSource recoveryDataSource() {
        HikariDataSource hikari = new HikariDataSource();
        hikari.setDriverClassName(environment.getProperty("recovery.datasource.driver-class-name"));
        hikari.setJdbcUrl(environment.getProperty("recovery.datasource.url"));
        hikari.setUsername(environment.getProperty("recovery.datasource.username"));
        hikari.setPassword(environment.getProperty("recovery.datasource.password"));
        hikari.addDataSourceProperty("autoReconnect", true);
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikari.addDataSourceProperty("useServerPrepStmts", true);
        hikari.addDataSourceProperty("cacheResultSetMetadata", true);
        hikari.setMaximumPoolSize(10);
        hikari.setConnectionTimeout(30000);
        return hikari;
    }

    @Bean
    public PlatformTransactionManager recoveryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(healthEntityManager().getObject());
        return transactionManager;
    }


}
