package io.ehdev.timetracker.services
import groovy.mock.interceptor.MockFor
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.UserDao
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

@Slf4j
class UserEndpointTest {

    MockFor userDao
    UserEndpoint endpoint

    @BeforeMethod
    public void setup(){
        userDao = new MockFor(UserDao)
    }

    private void setupService(){
        endpoint = new UserEndpoint(userDao: userDao.proxyInstance())
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testCreateUser_whenUserNotFound() throws Exception {
        userDao.demand.getUserFromToken() { throw new UserNotFoundException('', '') }
        setupService()

        endpoint.getExternalUserForLoggedInUser(null)
    }
    @Test
    public void testCreateUser_whenUserExists() throws Exception {
        def user = UserBuilder.createNewUser("john doe", "john@doe.com")
        userDao.demand.getUserFromToken() { user }
        setupService()

        def userInfo = endpoint.getExternalUserForLoggedInUser(null)
        assertThat(userInfo).isEqualTo(new ExternalUser(name: user.name, email:user.email, uuid: user.uuid))
    }

}
