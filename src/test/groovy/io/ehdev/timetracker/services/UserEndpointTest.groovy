package io.ehdev.timetracker.services
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.InMemoryUserDao
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class UserEndpointTest {

    InMemoryUserDao userDao

    @BeforeMethod
    public void setup(){
        userDao = new InMemoryUserDao()
    }

    @Test
    public void testCreateUser() throws Exception {
        def externalUser = new ExternalUser(email: "some_email@domain.com", name: 'John Doe')
        def convertedUser = externalUser.convertToUser()
        def id = userDao.save(convertedUser)
        assertThat(convertedUser).isEqualTo(userDao.getById(id))
        assertThat(externalUser).isEqualTo(ExternalUser.convertUser(userDao.getById(id)))
    }
}
