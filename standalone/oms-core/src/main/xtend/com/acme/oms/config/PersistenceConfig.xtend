package com.acme.oms.config

import java.sql.Driver
import java.util.HashMap
import javax.persistence.spi.PersistenceUnitTransactionType
import org.axonframework.common.Assert
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
import org.springframework.util.ClassUtils

import static com.acme.oms.config.DataSourceLabel.*
import static java.lang.String.format
import static javax.persistence.spi.PersistenceUnitTransactionType.RESOURCE_LOCAL
import static org.hibernate.cfg.AvailableSettings.*
import static org.hibernate.jpa.AvailableSettings.NAMING_STRATEGY

@Configuration
@EnableTransactionManagement
@PropertySource(#["classpath:META-INF/persistence.properties"])
class PersistenceConfig implements TransactionManagementConfigurer {
    
    public static val TRANSACTION_MANAGER_BEAN_NAME = "transactionManager"
    public static val PACKAGES_TO_SCAN = #["com.acme.oms"]
    public static val PERSISTENCE_UNIT_NAME = "tutorial"
    public static val JPA_GENERATE_DDL_LABEL = "jpa.generateDdl"
    public static val PU_TRANSACTION_TYPE_LABEL = "pu.transaction.type"
    public static val MANAGED_CLASS_NAMES_LABEL = "managed.class.names"

    static val LIST_SEP = ","
    static val LOADED_JDBC_DRIVER = "Loaded JDBC driver: {}"
    static val DRIVER_CLASS_NAME_MUST_BE_PROVIDED = "A driver class name must be provided."
    static val COULD_NOT_LOAD_JDBC_DRIVER_CLASS = "Could not load JDBC driver class [%s]"

    protected val logger = LoggerFactory.getLogger(getClass)

    @Autowired extension Environment env

    def private getProperty(DataSourceLabel label) {
        label.label.property 
    }

    @Bean
    def dataSource() {
        new SimpleDriverDataSource => [
            driverClass = driverClassName.property.driverType
            url = url.property
            username = username.property
            password = password.property
        ]
    }
    
    def private getDriverType(String typeName) {
        Assert.notNull(typeName, DRIVER_CLASS_NAME_MUST_BE_PROVIDED)
        val driverClassNameToUse = typeName.trim
        try {
            val type = Class.forName(driverClassNameToUse, true, ClassUtils.defaultClassLoader)
            logger.info(LOADED_JDBC_DRIVER, type)
            if (!Driver.isAssignableFrom(type)) {
                throw new IllegalStateException(format(COULD_NOT_LOAD_JDBC_DRIVER_CLASS, driverClassNameToUse))
            }
            type as Class<Driver>
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(format(COULD_NOT_LOAD_JDBC_DRIVER_CLASS, driverClassNameToUse), ex)
        }
    }
    
    @Bean(name=TRANSACTION_MANAGER_BEAN_NAME)
    override annotationDrivenTransactionManager() {
        new JpaTransactionManager(entityManagerFactory)
    }

    @Bean
    def entityManagerFactory() {
        val em = new LocalContainerEntityManagerFactoryBean => [
            dataSource = dataSource()
            persistenceUnitName = PERSISTENCE_UNIT_NAME
            packagesToScan = PACKAGES_TO_SCAN
            persistenceUnitPostProcessors = [
                val putt = com.acme.oms.config.PersistenceConfig.PU_TRANSACTION_TYPE_LABEL.property?:RESOURCE_LOCAL.name
                val tt = PersistenceUnitTransactionType.valueOf(putt.toUpperCase)
                transactionType = tt
                val classNames = (MANAGED_CLASS_NAMES_LABEL.property?:"").split(LIST_SEP)
                for(className:classNames) {
                    addManagedClassName(className)
                }
            ]
            jpaVendorAdapter = jpaVendorAdaper
            jpaPropertyMap = additionalProperties
            afterPropertiesSet
        ]
        em.object
    }

    @Bean
    def jpaVendorAdaper() {
        new HibernateJpaVendorAdapter => [
            database = DIALECT.getProperty(Database)
            showSql = SHOW_SQL.getProperty(Boolean, false)
            generateDdl = JPA_GENERATE_DDL_LABEL.getProperty(Boolean, false)
        ]
    }

    def private additionalProperties() {
        new HashMap<String, Object> => [
            put("hibernate.validator.apply_to_ddl", "hibernate.validator.apply_to_ddl".getProperty(Boolean, false))
            put("hibernate.validator.autoregister_listeners", "hibernate.validator.autoregister_listeners".getProperty(Boolean, false))
            put(NAMING_STRATEGY, ImprovedNamingStrategy.name)
            put(HBM2DDL_AUTO, HBM2DDL_AUTO.getProperty(String, ""))
            put(GENERATE_STATISTICS, GENERATE_STATISTICS.getProperty(Boolean, false))
        ]
    }

     
}