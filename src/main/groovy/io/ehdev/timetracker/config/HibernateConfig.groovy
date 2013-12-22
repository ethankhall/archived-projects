package io.ehdev.timetracker.config

import com.jolbox.bonecp.BoneCPDataSource
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder

import javax.sql.DataSource

@Configuration
@Import([BaseConfig.class, PropertyFileLoader.class])
class HibernateConfig {

    private static final Logger log = LoggerFactory.getLogger(HibernateConfig.class)

    @Value('${datasource.driver}')
    String driver
    @Value('${datasource.username}')
    String userName
    @Value('${datasource.password}')
    String password
    @Value('${datasource.jdbcUrl}')
    String jdbcUrl
    @Value('${datasource.idleConnectionTestPeriodInSeconds}')
    String idleConnections
    @Value('${datasource.idleMaxAgeInSeconds}')
    String maxIdleAge
    @Value('${datasource.maxConnectionsPerPartition}')
    String maxConnections
    @Value('${datasource.minConnectionsPerPartition}')
    String minConnections
    @Value('${datasource.partitionCount}')
    String partitionCount
    @Value('${datasource.acquireIncrement}')
    String acquireIncrement
    @Value('${datasource.statementsCacheSize}')
    String statementCacheSize

    @Bean
    public DataSource getDataSource(){
        return new LazyConnectionDataSourceProxy(getBoneDataSource());
    }

    @Bean(destroyMethod = "close")
    public BoneCPDataSource getBoneDataSource(){
        def source = new BoneCPDataSource();
        source.setDriverClass(driver)
        source.setUsername(userName)
        source.setPassword(password)
        source.setJdbcUrl(jdbcUrl)
        source.setIdleConnectionTestPeriodInSeconds(Integer.decode(idleConnections))
        source.setIdleMaxAgeInSeconds(Integer.decode(maxIdleAge))
        source.setMaxConnectionsPerPartition(Integer.decode(maxConnections))
        source.setMinConnectionsPerPartition(Integer.decode(minConnections))
        source.setPartitionCount(Integer.decode(partitionCount))
        source.setAcquireIncrement(Integer.decode(acquireIncrement))
        source.setStatementsCacheSize(Integer.decode(statementCacheSize))
        //source.setReleaseHelperThreads(3)
        return source
    }

    @Bean
    public SessionFactory getSessionFactory(){
        return new LocalSessionFactoryBuilder(getDataSource())
                .addPackage("io.ehdev.timetracker")
                .buildSessionFactory();
    }
}
