package io.ehdev.timetracker.core.company
import io.ehdev.timetracker.core.permissions.UserCompanyPermissions
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotAuthorizedToWriteException
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyInteractorTest {

    UserImpl adminUser
    UserImpl writeUser
    UserImpl readUser
    CompanyImpl company
    CompanyInteractor companyInteractor

    @BeforeMethod
    public void setup(){
        adminUser = UserBuilder.createNewUser()
        writeUser = UserBuilder.createNewUser()
        readUser = UserBuilder.createNewUser()

        company = CompanyInteractor.createNewCompany(adminUser, 'some name')
        company.permissions <<
                new UserCompanyPermissions(company: company, refUser: writeUser, writeAccess: true)
        company.permissions <<
                new UserCompanyPermissions(company: company, refUser: readUser, readAccess: true)
    }

    @Test
    public void testCreatingANewCompany() throws Exception {
        company.preformAdmin(adminUser) {}   //should not throw
    }

    @Test
    public void testSettingName_asWriteUser() throws Exception {
        companyInteractor = new CompanyInteractor(writeUser, company)
        companyInteractor.setName('name')
    }

    @Test(expectedExceptions = UserNotAuthorizedToWriteException.class)
    public void testSettingName_asReadUser() throws Exception {
        companyInteractor = new CompanyInteractor(readUser, company)
        companyInteractor.setName('name')
    }

    @Test
    public void testSetUsersAsAdmin() throws Exception {
        def newUser = UserBuilder.createNewUser()
        companyInteractor = new CompanyInteractor(adminUser, company)
        companyInteractor.addSetUserAsAdmin(writeUser, readUser, newUser)
        [adminUser, writeUser, readUser, newUser].each {
            assertThat(companyInteractor.findUserPermissions(it).canUserAdmin()).isTrue()
        }
    }

    @Test
    public void testSetUsersAsWrite() throws Exception {
        def newUser = UserBuilder.createNewUser()
        companyInteractor = new CompanyInteractor(adminUser, company)
        companyInteractor.addSetUserAsWrite(writeUser, readUser, newUser)
        [writeUser, readUser, newUser].each {
            assertThat(companyInteractor.findUserPermissions(it).canUserWrite()).isTrue()
        }
    }

    @Test
    public void testSetUsersAsRead() throws Exception {
        def newUser = UserBuilder.createNewUser()
        companyInteractor = new CompanyInteractor(adminUser, company)
        companyInteractor.addSetUserAsRead(writeUser, readUser, newUser)
        [writeUser, readUser, newUser].each {
            assertThat(companyInteractor.findUserPermissions(it).canUserAdmin()).isFalse()
            assertThat(companyInteractor.findUserPermissions(it).canUserWrite()).isFalse()
            assertThat(companyInteractor.findUserPermissions(it).canUserRead()).isTrue()
        }
    }
}
