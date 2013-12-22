package io.ehdev.timetracker.config

import org.hibernate.SessionFactory
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

import javax.annotation.Resource

import static org.fest.assertions.Assertions.assertThat

@ActiveProfiles("test")
@ContextConfiguration(classes = [PropertyFileLoader.class, HibernateConfig.class])
class SpringTest extends AbstractTestNGSpringContextTests {

    @Resource
    SessionFactory sessionFactory

    @Test
    public void testCanRetrieveSessionFactory() throws Exception {
        assertThat(sessionFactory).isNotNull()
    }
}
