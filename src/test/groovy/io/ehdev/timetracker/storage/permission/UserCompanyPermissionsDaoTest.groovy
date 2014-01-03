package io.ehdev.timetracker.storage.permission
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.user.UserBuilder
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
class UserCompanyPermissionsDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    private UserCompanyPermissionsDaoImpl permissionsDao

    @BeforeMethod
    public void setup(){
        permissionsDao = new UserCompanyPermissionsDaoImpl()
        permissionsDao.setSessionFactory(sessionFactory)
    }

    @Test
    public void testSaveObject() throws Exception {
        def user1 = UserBuilder.createNewUser()
        def company1 = CompanyInteractor.createNewCompany(user1, 'bla1')
        def permission = company1.permissions[0]

        permissionsDao.save(permission)

        def retrievedPermission = permissionsDao.getById(permission.id)
        assertThat(permission).isEqualTo(retrievedPermission)
    }

    @Test
    public void testFindingCompaniesForUser() throws Exception {
        def user1 = UserBuilder.createNewUser()
        def company1 = CompanyInteractor.createNewCompany(user1, 'bla1')
        def company2 = CompanyInteractor.createNewCompany(user1, 'bla2')
        def company3 = CompanyInteractor.createNewCompany(user1, 'bla3')
        def company4 = CompanyInteractor.createNewCompany(user1, 'bla4')

        permissionsDao.save(company1.permissions[0], company2.permissions[0], company3.permissions[0], company4.permissions[0])
        def companies = permissionsDao.getCompaniesAvailableToUser(user1)
        assertThat(companies.uuid).containsOnly(company1.uuid, company2.uuid, company3.uuid, company4.uuid)
    }
}
