package io.ehdev.timetracker.services
import groovy.mock.interceptor.MockFor
import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.core.company.CompanyInteractor
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

    private UserImpl authorizedUser
    private UserImpl otherUser

    @BeforeMethod
    public void setup(){
        userDao = new MockFor(UserDaoImpl)
        companyDao = new MockFor(CompanyDaoImpl)
        userCompanyPermissionsDao = new MockFor(UserCompanyPermissionsDaoImpl)

        authorizedUser = UserBuilder.createNewUser()
        otherUser = UserBuilder.createNewUser()
    }

    @Test
    public void testCreatingACompany() throws Exception {
        setupService()

        def company = companyEndpoint.createNewCompany(
                ExternalCompanyBuilder.createCompany([name: 'something']),
                TokenBuilder.build(authorizedUser))

        assertThat(company.getUuid()).isNotNull()
        assertThat(company.getName()).isEqualTo('something')
        assertThat(company.admin).hasSize(1)
        assertThat(company.admin).containsOnly(ExternalUser.convertUser(authorizedUser))
    }

    @Test
    public void testGetCompanyForUser_whereExists() throws Exception {
        def company = CompanyInteractor.createNewCompany(authorizedUser, "something")

        companyDao.demand.getByUuid(company.uuid) { company }
        setupService()

        def retrievedCompany = companyEndpoint.getCompany(company.uuid, null)
        assertThat(retrievedCompany).isEqualTo(new ExternalCompany(company))
    }

    @Test
    public void testGettingCompaniesForUser_whereThereWereNoneForUser() throws Exception {
        setupService()

        assertThat(companyEndpoint.getAllCompaniesForUser(null)).isEmpty()
    }

    @Test
    public void testGettingCompaniesForUser() throws Exception {
        def company1 = CompanyInteractor.createNewCompany(authorizedUser, "something")
        def company2 = CompanyInteractor.createNewCompany(authorizedUser, "something2")

        setupCompanyReturns([company1, company2])
        setupService()

        def companies = companyEndpoint.getAllCompaniesForUser(null)
        assertThat(companies).containsOnly(new ExternalCompany(company1), new ExternalCompany(company2))
    }

    private void setupCompanyReturns( authorizedUser ){
        userCompanyPermissionsDao.demand.getCompaniesAvailableToUser(otherUser) { authorizedUser }
    }

    private void setupService() {
        userDao.demand.getUserFromToken { authorizedUser }
        companyDao.demand.save { null }
        userCompanyPermissionsDao.demand.getCompaniesAvailableToUser(otherUser) { [] }

        companyEndpoint = new CompanyEndpoint(
                userDao: userDao.proxyInstance(),
                companyDao: companyDao.proxyInstance(),
                userCompanyPermissionsDao: userCompanyPermissionsDao.proxyInstance()
        )
    }
}