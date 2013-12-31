package io.ehdev.timetracker.storage

import io.ehdev.timetracker.config.BaseConfig
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.context.web.WebAppConfiguration
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = [BaseConfig.class, PropertyFileLoader.class, HibernateConfig.class])
class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDaoImpl userDao

    @Test
    public void testSaveUser() throws Exception {
        def user = UserBuilder.createNewUser()
        userDao.save(user)
        def retrieved = userDao.getById(user.getId())
        assertThat(user).isEqualTo(retrieved)

        retrieved = userDao.getUserByUUID(user.getUuid()).get()
        assertThat(user).isEqualTo(retrieved)
    }

    @Test
    public void testMultipleInserts_onlyOneFoundById() throws Exception {

        for(i in 0..10){
            userDao.save(UserBuilder.createNewUser())
        }
        def user = UserBuilder.createNewUser()
        userDao.save(user)
        for(i in 0..10){
            userDao.save(UserBuilder.createNewUser())
        }

        def retrieved = userDao.getUserByUUID(user.getUuid()).get()
        assertThat(user).isEqualTo(retrieved)

    }
}
