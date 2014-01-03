package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotFoundException
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat
import static org.fest.assertions.Fail.fail
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@Slf4j
@ActiveProfiles("test")
@ContextConfiguration(classes = [PropertyFileLoader.class, HibernateConfig.class])
class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    UserDaoImpl userDao

    @BeforeMethod
    public void setup() {
        userDao = new UserDaoImpl(sessionFactory: sessionFactory)
    }

    @Test
    public void testGettingByUUID() throws Exception {
        ArrayList<UserImpl> userList = createUserEntries(userDao)

        assertThat(userDao.getUserByUUID(userList[1].uuid)).isEqualTo(Optional.of(userList[1]))
        assertThat(userDao.getUserByUUID("1").isPresent()).isFalse()
    }

    public ArrayList<UserImpl> createUserEntries(userDao) {
        def userList = [UserBuilder.createNewUser(), UserBuilder.createNewUser(), UserBuilder.createNewUser()]
        userList.each {
            userDao.save(it)
        }
        userList
    }

    @Test
    public void testGetUserFromToken() throws Exception {
        ArrayList<UserImpl> userList = createUserEntries(userDao)

        def mockedValues = mock(OpenIDAuthenticationToken.class)
        when(mockedValues.getIdentityUrl()).thenReturn( userList[1].authToken)
        assertThat(userDao.getUserFromToken(mockedValues)).isEqualTo(userList[1])
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void testGettingUser_whereNoneExists() throws Exception {
        def mockedValues = mock(OpenIDAuthenticationToken.class)
        when(mockedValues.getIdentityUrl()).thenReturn("1")
        userDao.getUserFromToken(mockedValues)
        fail()
    }

    @Test
    public void testGetById() throws Exception {
        ArrayList<UserImpl> userList = createUserEntries(userDao)
        assertThat(userDao.getById(userList[0].id)).isEqualTo(userList[0])
    }
}
