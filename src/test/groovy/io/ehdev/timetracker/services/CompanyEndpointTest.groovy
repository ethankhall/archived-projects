package io.ehdev.timetracker.services
import io.ehdev.timetracker.TokenBuilder
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.services.external.company.ExternalCompany
import io.ehdev.timetracker.services.external.company.ExternalCompanyBuilder
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.company.InMemoryCompanyDao
import io.ehdev.timetracker.storage.user.InMemoryUserDao
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyEndpointTest {
    private CompanyEndpoint companyEndpoint
    private InMemoryUserDao userDao
    private InMemoryCompanyDao companyDao
    private UserImpl user1
    private UserImpl user2

    @BeforeMethod
    public void setup(){
        userDao = new InMemoryUserDao()
        companyDao = new InMemoryCompanyDao()
        companyEndpoint = CompanyEndpoint.newInstance()
        companyEndpoint.setUserDao(userDao)
        companyEndpoint.setCompanyDao(companyDao)

        user1 = UserBuilder.createNewUser()
        user2 = UserBuilder.createNewUser()

        userDao.save(user1, user2)
    }

    @Test
    public void testCreatingACompany() throws Exception {
        def company = createCompanyWithEndpoint('something', user1)
        assertThat(company.getUuid()).isNotNull()
        assertThat(company.getName()).isEqualTo('something')
        assertThat(company.admin).hasSize(1)
        assertThat(company.admin).containsOnly(ExternalUser.convertUser(user1))
    }

    @Test
    public void testGetCreatedCompany() throws Exception {
        def company = createCompanyWithEndpoint('something', user2)
        def retrievedCompany = companyEndpoint.getCompany(company.uuid, TokenBuilder.build(user2))
        assertThat(retrievedCompany).isInstanceOf(ExternalCompany.class)
    }

    public ExternalCompany createCompanyWithEndpoint(String name, UserImpl user) {
        companyEndpoint.createNewCompany(
                ExternalCompanyBuilder.createCompany([name: name]),
                TokenBuilder.build(user))
    }
}