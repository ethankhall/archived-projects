package io.ehdev.timetracker.storage

import org.h2.jdbcx.JdbcDataSource
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder

abstract class DaoTestBase {


    static SessionFactory getSessionFactory(String dbName){
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:${dbName};DB_CLOSE_DELAY=-1");

        def builder = new LocalSessionFactoryBuilder(ds)
        builder.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        builder.setProperty("hibernate.show_sql", "true")
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        builder.scanPackages("io.ehdev.timetracker")
        return builder.buildSessionFactory()
    }
}
