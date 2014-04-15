package io.ehdev.timetracker.security
import groovy.mock.interceptor.MockFor
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.openid.OpenIDAttribute
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.testng.collections.Lists.newArrayList

@Slf4j
class SecureUserDetailsServiceTest {

    SecureUserDetailsService service
    MockFor userDao
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
    public void createNewMock() {
        userDao = new MockFor(UserDaoImpl)
    }

    private void setupService() {
        service = new SecureUserDetailsServiceImpl(userDao: userDao.proxyInstance())
    }

    @Test
    public void testLoadUserDetails_whereNoneIsPresent() throws Exception {
        userDao.demand.with {
            getUserFromToken(token) { throw new UserNotFoundException('', '') }
            save { null }
        }
        setupService()

        UserDetails details = service.loadUserDetails(token)
        assertThat(details.getUsername()).isSameAs("123")
    }

    @Test
    public void testLoadUserDetails_whereOneIsPresent() throws Exception {
        givenUser("123")

        UserDetails details = service.loadUserDetails(token)
        assertThat(details.getUsername()).isSameAs("123")
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testGetUser_whereNoneIsPresent() throws Exception {
        givenUserNotFound()

        service.getUser(token)
    }

    @Test
    public void testGetUser() throws Exception {
        userDao.demand.getUserFromToken(token) { null }
        setupService()

        assertThat(service.getUser(token)).isNull()
    }

    @Test
    public void testPopulateUserFromOpenId() throws Exception {
        def user = UserBuilder.createNewUser()
        setupService()
        service.populateUserFromOpenId(user,
                newArrayList(new OpenIDAttribute('lastname', 'something', ['doe']),
                    new OpenIDAttribute('email', 'something', ['joe@doe.com']),
                    new OpenIDAttribute('firstname', 'something', ['john']),
                    new OpenIDAttribute('fullname', 'something', ['john doe']),
                ))
        assertThat(user.getName()).isEqualTo('john doe')
    }

    @Test
    public void testPopulateUserFromOpenId_whereFullNameIsMissing() throws Exception {
        def user = UserBuilder.createNewUser()
        setupService()
        service.populateUserFromOpenId(user,
                newArrayList(new OpenIDAttribute('lastname', 'something', ['doe']),
                        new OpenIDAttribute('email', 'something', ['joe@doe.com']),
                        new OpenIDAttribute('firstname', 'something', ['john']))
                )
        assertThat(user.getName()).isEqualTo('john doe')
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testLoadUserByUsername_whereNoUserIsPresent() throws Exception {
        givenUserNotFound()

        service.loadUserByUsername("123")
    }

    @Test
    public void testLoadUserByUsername_whereUserIsPresent() throws Exception {
        givenUser("123")

        service.loadUserByUsername("123")
    }

    private void givenUserNotFound(){
        userDao.demand.getUserFromToken(token) { throw new UserNotFoundException('', '') }
        setupService()
    }

    public void givenUser(String userToken) {
        userDao.demand.getUserFromToken(token) { return new UserImpl(authToken: userToken) }
        setupService()
    }

}
