package io.ehdev.timetracker.security
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.testng.collections.Lists.newArrayList

@Slf4j
@ActiveProfiles("test")
@ContextConfiguration(classes = [PropertyFileLoader.class, HibernateConfig.class])
class SecureUserDetailsServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    SecureUserDetailsService service
    UserDaoImpl userDao
    OpenIDAuthenticationToken token

    @BeforeClass
    public void setupToken() {
        token = mock(OpenIDAuthenticationToken.class)
        when(token.getIdentityUrl()).thenReturn("123")
        when(token.getAttributes()).thenReturn(
                newArrayList(new OpenIDAttribute('lastname', 'something', ['doe']),
                        new OpenIDAttribute('email', 'something', ['joe@doe.com'])))
    }

    @BeforeMethod
    public void setup() {
        service = new SecureUserDetailsService()
        userDao = new UserDaoImpl( sessionFactory: sessionFactory)
        service.setUserDao(userDao)
    }

    @Test
    public void testLoadUserDetails_whereNoneIsPresent() throws Exception {
        UserDetails details = service.loadUserDetails(token)
        assertThat(details.getUsername()).isSameAs("123")
    }

    @Test
    public void testLoadUserDetails_whereOneIsPresent() throws Exception {
        userDao.save(new UserImpl(uuid: "SOMETHING", authToken: "123"))
        UserDetails details = service.loadUserDetails(token)
        assertThat(details.getUsername()).isSameAs("123")
    }

    @Test
    public void testCreateUser() throws Exception {
        def user = service.createNewUser(token)
        assertThat(user.uuid).isNotNull()
        assertThat(user.name).isEqualTo('doe')
        assertThat(user.email).isEqualTo('joe@doe.com')
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testGetUser_whereNoneIsPresent() throws Exception {
        service.getUser(token)
    }

    @Test
    public void testGetUser() throws Exception {
        userDao.save(new UserImpl(uuid: "SOMETHING", authToken: "123"))
        service.getUser(token)
    }

    @Test
    public void testPopulateUserFromOpenId() throws Exception {
        def user = UserBuilder.createNewUser()
        service.populateUserFromOpenId(user,
                newArrayList(new OpenIDAttribute('lastname', 'something', ['doe']),
                    new OpenIDAttribute('email', 'something', ['joe@doe.com']),
                    new OpenIDAttribute('firstname', 'something', ['john']),
                    new OpenIDAttribute('fullname', 'something', ['john doe']),
                ))
        assertThat(user.getName()).isEqualTo('john doe')
    }

    @Test
    public void testPopulateUserFromOpenId_whereFullnameIsMissing() throws Exception {
        def user = UserBuilder.createNewUser()
        service.populateUserFromOpenId(user,
                newArrayList(new OpenIDAttribute('lastname', 'something', ['doe']),
                        new OpenIDAttribute('email', 'something', ['joe@doe.com']),
                        new OpenIDAttribute('firstname', 'something', ['john']))
                )
        assertThat(user.getName()).isEqualTo('john doe')
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testLoadUserByUsername_whereNoUserIsPresent() throws Exception {
        service.loadUserByUsername("123")
    }

    @Test
    public void testLoadUserByUsername_whereUserIsPresent() throws Exception {
        userDao.save(new UserImpl(uuid: "SOMETHING", authToken: "123"))
        service.loadUserByUsername("123")
    }
}
