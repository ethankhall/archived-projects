package io.ehdev.timetracker.storage.user
import com.google.common.base.Optional
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import org.h2.jdbcx.JdbcDataSource
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class UserDaoTest {

    @DataProvider(name = "DP")
    public static Object[][] params(){
        InMemoryUserDao inMemoryDao = new InMemoryUserDao()
        inMemoryDao.storage.clear()

        UserDaoImpl dbUserDao = new UserDaoImpl()
        dbUserDao.setSessionFactory(getSessionFactory())

        return [[inMemoryDao], [dbUserDao]];
    }

    private static SessionFactory getSessionFactory(){
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:UserDaoTest;DB_CLOSE_DELAY=-1");

        def builder = new LocalSessionFactoryBuilder(ds)
        builder.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        builder.setProperty("hibernate.show_sql", "true")
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        builder.scanPackages("io.ehdev.timetracker")
        return builder.buildSessionFactory()
    }

    @Test(dataProvider = "DP")
    public void testGettingByUUID(UserDao userDao) throws Exception {
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

    @Test(dataProvider = "DP")
    public void testGetUserFromToken(UserDao userDao) throws Exception {
        ArrayList<UserImpl> userList = createUserEntries(userDao)

        assertThat(userDao.getUserFromToken(userList[1].authToken)).isEqualTo(Optional.of(userList[1]))
        assertThat(userDao.getUserFromToken("1").isPresent()).isFalse()


        def mockedValues = mock(OpenIDAuthenticationToken.class)
        when(mockedValues.getIdentityUrl()).thenReturn( userList[1].authToken, "1" )
        assertThat(userDao.getUserFromToken(mockedValues)).isEqualTo(Optional.of(userList[1]))
        assertThat(userDao.getUserFromToken(mockedValues).isPresent()).isFalse()
    }

    @Test(dataProvider = "DP")
    public void testGetById(UserDao userDao) throws Exception {
        ArrayList<UserImpl> userList = createUserEntries(userDao)
        assertThat(userDao.getById(userList[0].id)).isEqualTo(userList[0])
    }
}
