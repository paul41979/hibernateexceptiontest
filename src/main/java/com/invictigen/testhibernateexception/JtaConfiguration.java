package com.invictigen.testhibernateexception;

import org.objectweb.jotm.Current;
import org.objectweb.jotm.Jotm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.SystemException;

@Configuration
@EnableTransactionManagement
public class JtaConfiguration {
    @Bean(destroyMethod = "stop")
    public Jotm jotm()
            throws SystemException, NamingException {
      Jotm jotm = new Jotm(true, false);
      return jotm;
    }

    @Bean
    public JtaTransactionManager transactionManager(
            Jotm jotm) {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setUserTransaction(Current.getCurrent());
        return transactionManager;
    }


    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.invictigen.testhibernate");
        factory.setJtaDataSource(dataSource());

        factory.afterPropertiesSet();


        return factory.getObject();
    }
}