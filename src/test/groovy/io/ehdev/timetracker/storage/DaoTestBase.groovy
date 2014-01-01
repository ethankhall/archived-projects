package io.ehdev.timetracker.storage

import org.h2.jdbcx.JdbcDataSource
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder

abstract class DaoTestBase {


    static SessionFactory getSessionFactory(String dbName){
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:${dbName};DB_CLOSE_DELAY=-1");

        def builder = new LocalSessionFactoryBuilder(ds)
        builder.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        builder.setProperty("hibernate.show_sql", "true")
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        builder.setProperty("hibernate.current_session_context_class", "org.hibernate.context.ThreadLocalSessionContext")
        builder.scanPackages("io.ehdev.timetracker")
        return builder.buildSessionFactory()
    }
}
