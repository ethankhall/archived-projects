package io.ehdev.timetracker.core.company
import io.ehdev.timetracker.core.user.UserBuilder
import org.testng.annotations.Test

class CompanyInteractorTest {

    @Test
    public void testCreatingANewCompany() throws Exception {
        def user = UserBuilder.createNewUser()
        def company = CompanyInteractor.createNewCompany(user, 'some name')
        company.preformAdmin(user) {}   //should not throw
    }
}
