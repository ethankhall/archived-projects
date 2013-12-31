package io.ehdev.timetracker.services
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.InMemoryUserDao
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

    @Test(enabled = false)
    public void testCreateUser() throws Exception {
        def externalUser = new ExternalUser(email: "some_email@domain.com", name: 'John Doe')
        endpoint.createUser(externalUser)
        def key = userDao.storage.keySet().first()
        assertThat(externalUser).isEqualTo(ExternalUser.convertUser(userDao.getById(key)))
    }
}
