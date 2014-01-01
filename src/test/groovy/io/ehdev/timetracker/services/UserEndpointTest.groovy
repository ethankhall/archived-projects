package io.ehdev.timetracker.services

import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.doa.InMemoryUserDao
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class UserEndpointTest {

    InMemoryUserDao userDao
    UserEndpoint endpoint

    @BeforeMethod
    public void setup(){
        userDao = new InMemoryUserDao()
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
