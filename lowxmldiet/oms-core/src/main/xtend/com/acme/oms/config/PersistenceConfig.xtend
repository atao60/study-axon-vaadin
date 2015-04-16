package com.acme.oms.config

import java.sql.Driver
import java.util.HashMap
import org.hibernate.cfg.ImprovedNamingStrategy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.util.Assert
import org.springframework.util.ClassUtils

import static com.acme.oms.config.DataSourceLabel.*
import static java.lang.String.format
import static org.hibernate.cfg.AvailableSettings.*
import static org.hibernate.jpa.AvailableSettings.NAMING_STRATEGY

@Configuration
@EnableTransactionManagement
@PropertySource(#["classpath:META-INF/persistence.properties"])
class PersistenceConfig implements TransactionManagementConfigurer {
    
    static val PACKAGES_TO_SCAN = #["com.acme.oms"]
    static val PERSISTENCE_UNIT_NAME = "tutorial"
    static val LOADED_JDBC_DRIVER = "Loaded JDBC driver: {}"
    static val DRIVER_CLASS_NAME_MUST_BE_PROVIDED = "A driver class name must be provided."
    static val COULD_NOT_LOAD_JDBC_DRIVER_CLASS = "Could not load JDBC driver class [%s]"

    protected val logger = LoggerFactory.getLogger(getClass)

    @Autowired Environment env

    def private getProperty(DataSourceLabel label) {
        return env.getProperty(label.label) 
    }
    
    def private <T> T getProperty(String label, Class<T> type) {
        return env.getProperty(label, type)
    }
    
    def private <T> T getProperty(String label, Class<T> type, T defaultValue) {
        return env.getProperty(label, type, defaultValue)
    }
    @Bean
    def dataSource() {
        val source = new SimpleDriverDataSource
        
        source.driverClass = getDriverType(getProperty(driverClassName))
        source.url = getProperty(url)
        source.username = getProperty(username)
        source.password = getProperty(password)
        
        return source;
    }
    
    def private getDriverType(String typeName) {
        Assert.hasText(typeName, DRIVER_CLASS_NAME_MUST_BE_PROVIDED)
        val driverClassNameToUse = typeName.trim
        try {
            val type = Class.forName(driverClassNameToUse, true, ClassUtils.defaultClassLoader)
            logger.info(LOADED_JDBC_DRIVER, type)
            if (!Driver.isAssignableFrom(type)) {
                throw new IllegalStateException(format(COULD_NOT_LOAD_JDBC_DRIVER_CLASS, driverClassNameToUse))
            }
            val driverType = type as Class<Driver>
            return driverType;
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(format(COULD_NOT_LOAD_JDBC_DRIVER_CLASS, driverClassNameToUse), ex)
        }
    }
    
    @Bean(name="transactionManager")
    override annotationDrivenTransactionManager() {
        new JpaTransactionManager(entityManagerFactory)
    }
/*
    @Bean
    def transactionTemplate() {
        val transactionTemplate = new TransactionTemplate
        transactionTemplate.transactionManager = annotationDrivenTransactionManager
        return transactionTemplate
    }    
*/
    @Bean
    def entityManagerFactory() {
        val em = new LocalContainerEntityManagerFactoryBean
        em.setDataSource(dataSource)
        em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME)
        em.setPackagesToScan(PACKAGES_TO_SCAN)
        em.setJpaVendorAdapter(jpaVendorAdaper)
        em.setJpaPropertyMap(additionalProperties)
        em.afterPropertiesSet
        return em.object
    }

    @Bean
    def jpaVendorAdaper() {
        val vendorAdapter = new HibernateJpaVendorAdapter
        vendorAdapter.setDatabase(getProperty(DIALECT, Database))
        vendorAdapter.setShowSql(getProperty(SHOW_SQL, Boolean, true))
        vendorAdapter.setGenerateDdl(getProperty("jpa.generateDdl", Boolean, true))
        return vendorAdapter;
    }

    def private additionalProperties() {
        val properties = new HashMap<String, Object>
        properties.put("hibernate.validator.apply_to_ddl", getProperty("hibernate.validator.apply_to_ddl", Boolean, false))
        properties.put("hibernate.validator.autoregister_listeners", getProperty("hibernate.validator.autoregister_listeners", Boolean, false))
        properties.put(NAMING_STRATEGY, ImprovedNamingStrategy.name)
        properties.put(HBM2DDL_AUTO, getProperty(HBM2DDL_AUTO, String, ""))
        properties.put(GENERATE_STATISTICS, getProperty(GENERATE_STATISTICS, Boolean, false))
        return properties
    }

     
}