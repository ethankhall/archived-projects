package io.ehdev.timetracker.services
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

@Slf4j
@ActiveProfiles("test")
@ContextConfiguration(classes = [PropertyFileLoader.class, HibernateConfig.class])
class UserEndpointTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    UserDaoImpl userDao
    UserEndpoint endpoint

    @BeforeMethod
    public void setup(){
        userDao = new UserDaoImpl(sessionFactory: sessionFactory)
        endpoint = new UserEndpoint(userDao: userDao)
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testCreateUser_whenUserNotFound() throws Exception {
        def token = TokenBuilder.build("123", "john doe", 'john@doe.com')
        endpoint.show(token)
    }
    @Test
    public void testCreateUser_whenUserExists() throws Exception {
        def user = UserBuilder.createNewUser("john doe", "john@doe.com")
        userDao.save(user)
        def token = TokenBuilder.build(user)
        def userInfo = endpoint.show(token)
        assertThat(userInfo).isEqualTo(new ExternalUser(name: user.name, email:user.email, uuid: user.uuid))
    }

}
