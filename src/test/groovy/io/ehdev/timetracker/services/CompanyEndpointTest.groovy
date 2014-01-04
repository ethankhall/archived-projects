package io.ehdev.timetracker.services
import groovy.mock.interceptor.MockFor
import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.services.external.company.ExternalCompany
import io.ehdev.timetracker.services.external.company.ExternalCompanyBuilder
import io.ehdev.timetracker.storage.company.CompanyDaoImpl
import io.ehdev.timetracker.storage.permission.UserCompanyPermissionsDaoImpl
import io.ehdev.timetracker.storage.user.UserDaoImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyEndpointTest {
    private CompanyEndpoint companyEndpoint

    def userDaoMock
    def companyDaoMock
    def userCompanyPermissionsDaoMock

    def userDao
    def companyDao
    def userCompanyPermissionsDao

    private UserImpl authorizedUser
    private UserImpl otherUser

    @BeforeMethod
    public void setup(){
        userDaoMock = new MockFor(UserDaoImpl)
        companyDaoMock = new MockFor(CompanyDaoImpl)
        userCompanyPermissionsDaoMock = new MockFor(UserCompanyPermissionsDaoImpl)

        authorizedUser = UserBuilder.createNewUser()
        otherUser = UserBuilder.createNewUser()
    }

    @Test
    public void testCreatingACompany() throws Exception {
        companyDaoMock.demand.save { null }
        userDaoMock.demand.getUserFromToken { authorizedUser }
        setupService()

        def company = companyEndpoint.createNewCompany(
                ExternalCompanyBuilder.createCompany([name: 'something']),
                TokenBuilder.build(authorizedUser))

        assertThat(company.getUuid()).isNotNull()
        assertThat(company.getName()).isEqualTo('something')
        assertThat(company.admin).hasSize(1)
        assertThat(company.admin).containsOnly(authorizedUser.uuid)

        companyDaoMock.verify companyDao
    }

    @Test
    public void testGetCompanyForUser_whereExists() throws Exception {
        def company = CompanyInteractor.createNewCompany(authorizedUser, "something")

        userDaoMock.demand.getUserFromToken { authorizedUser }
        companyDaoMock.demand.getByUuid(company.uuid) { company }
        setupService()

        def retrievedCompany = companyEndpoint.getCompany(company.uuid, null)
        assertThat(retrievedCompany).isEqualTo(new ExternalCompany(company))
    }

    @Test
    public void testGettingCompaniesForUser_whereThereWereNoneForUser() throws Exception {
        userDaoMock.demand.getUserFromToken { authorizedUser }
        userCompanyPermissionsDaoMock.demand.getCompaniesAvailableToUser { [] }
        setupService()

        assertThat(companyEndpoint.getAllCompaniesForUser(null)).isEmpty()
    }

    @Test
    public void testGettingCompaniesForUser() throws Exception {
        def company1 = CompanyInteractor.createNewCompany(authorizedUser, "something")
        def company2 = CompanyInteractor.createNewCompany(authorizedUser, "something2")

        userDaoMock.demand.getUserFromToken { authorizedUser }
        userCompanyPermissionsDaoMock.demand
                .getCompaniesAvailableToUser {  [company1, company2] }
        setupService()

        def companies = companyEndpoint.getAllCompaniesForUser(null)
        assertThat(companies)
                .containsOnly(new ExternalCompany(company1), new ExternalCompany(company2))
    }

    @Test
    public void testUpdateCompany_nameShouldUpdate() throws Exception {
        def company1 = CompanyInteractor.createNewCompany(authorizedUser, "something")
        companyDaoMock.demand.with {
            getByUuid(company1.uuid) { company1 }
            save { null }
        }
        userDaoMock.demand.getUserFromToken { authorizedUser }
        setupService()

        companyEndpoint.updateCompany(company1.uuid, new ExternalCompany(name: 'new name'), null)
        assertThat(company1.name).isEqualTo('new name')

        companyDaoMock.verify companyDao
    }

    @Test
    public void testUpdateCompany_permissionsShouldUpdate() throws Exception {
        def user1 = UserBuilder.createNewUser()
        def user2 = UserBuilder.createNewUser()
        def company1 = CompanyInteractor.createNewCompany(authorizedUser, "something")

        userDaoMock.demand.with {
            getUserFromToken { authorizedUser }
            getUserByUUID { user1 }
            getUserByUUID { user2 }
        }
        companyDaoMock.demand.with {
            getByUuid(company1.uuid) { company1 }
            save { null }
        }
        setupService()

        def externalCompany = new ExternalCompany(
                name: 'new name',
                write: [user1.uuid],
                read: [user2.uuid])
        companyEndpoint.updateCompany(company1.uuid, externalCompany, null)
        assertThat(company1.permissions).hasSize(3)
        assertThat(company1.permissions.refUser.uuid)
                .containsOnly(authorizedUser.uuid, user1.uuid, user2.uuid)

        companyDaoMock.verify companyDao
    }

    private void setupService() {

        userDao = userDaoMock.proxyInstance()
        companyDao = companyDaoMock.proxyInstance()
        userCompanyPermissionsDao = userCompanyPermissionsDaoMock.proxyInstance()

        companyEndpoint = new CompanyEndpoint(
                userDao: userDao,
                companyDao: companyDao,
                userCompanyPermissionsDao: userCompanyPermissionsDao
        )
    }
}