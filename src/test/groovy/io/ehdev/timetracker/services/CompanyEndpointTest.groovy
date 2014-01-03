package io.ehdev.timetracker.services
import groovy.mock.interceptor.MockFor
import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.services.external.company.ExternalCompany
import io.ehdev.timetracker.services.external.company.ExternalCompanyBuilder
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.company.CompanyDaoImpl
import io.ehdev.timetracker.storage.permission.UserCompanyPermissionsDaoImpl
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyEndpointTest {
    private CompanyEndpoint companyEndpoint

    def userDao
    def companyDao
    def userCompanyPermissionsDao

    private UserImpl user1
    private UserImpl user2

    @BeforeMethod
    public void setup(){
        userDao = new MockFor(UserDaoImpl)
        companyDao = new MockFor(CompanyDaoImpl)
        userCompanyPermissionsDao = new MockFor(UserCompanyPermissionsDaoImpl)

        companyEndpoint = CompanyEndpoint.newInstance()

        user1 = UserBuilder.createNewUser()
        user2 = UserBuilder.createNewUser()
    }

    @Test
    public void testCreatingACompany() throws Exception {
        userDao.demand.getUserFromToken { user1 }
        companyEndpoint.setUserDao(userDao.proxyInstance())

        companyDao.demand.save { null }
        companyEndpoint.setCompanyDao(companyDao.proxyInstance())

        def company = createCompanyWithEndpoint('something', user1)

        assertThat(company.getUuid()).isNotNull()
        assertThat(company.getName()).isEqualTo('something')
        assertThat(company.admin).hasSize(1)
        assertThat(company.admin).containsOnly(ExternalUser.convertUser(user1))
    }

    @Test
    public void testGetCreatedCompany() throws Exception {
        def company = createCompany("something", "something", user1)

        userDao.demand.getUserFromToken { user1 }
        companyDao.demand.getByUuid('something') { company }

        companyEndpoint.setUserDao(userDao.proxyInstance())
        companyEndpoint.setCompanyDao(companyDao.proxyInstance())

        def retrievedCompany = companyEndpoint.getCompany("something", null)
        assertThat(retrievedCompany).isEqualTo(new ExternalCompany(company))
    }

    @Test
    public void testGettingCompaniesForUser_whereThereWereNoneForUser() throws Exception {
        userDao.demand.getUserFromToken { user1 }
        userCompanyPermissionsDao.demand.getCompaniesAvailableToUser(user2) { [] }

        companyEndpoint.setUserDao(userDao.proxyInstance())
        companyEndpoint.setUserCompanyPermissionsDao(userCompanyPermissionsDao.proxyInstance())

        assertThat(companyEndpoint.getAllCompaniesForUser(null)).isEmpty()
    }

    @Test
    public void testGettingCompaniesForUser() throws Exception {

        def company1 = createCompany("something", "something", user1)
        def company2 = createCompany("something2", "something2", user1)

        userDao.demand.getUserFromToken { user1 }
        userCompanyPermissionsDao.demand.getCompaniesAvailableToUser(user1) { [company1, company2] }

        companyEndpoint.setUserDao(userDao.proxyInstance())
        companyEndpoint.setUserCompanyPermissionsDao(userCompanyPermissionsDao.proxyInstance())


        def companies = companyEndpoint.getAllCompaniesForUser(null)
        assertThat(companies).containsOnly(new ExternalCompany(company1), new ExternalCompany(company2))
    }

    public CompanyImpl createCompany(String name, String uuid, UserImpl user) {
        def company = new CompanyImpl(
                name: name,
                uuid: uuid)
        def permissions = [ new UserCompanyPermissions(
                refUser: user,
                adminAccess: true,
                company: company) ]
        company.setPermissions(permissions)
        return company
    }

    public ExternalCompany createCompanyWithEndpoint(String name, UserImpl user) {
        companyEndpoint.createNewCompany(
                ExternalCompanyBuilder.createCompany([name: name]),
                TokenBuilder.build(user))
    }
}