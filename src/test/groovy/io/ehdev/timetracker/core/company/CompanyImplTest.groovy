package io.ehdev.timetracker.core.company

import io.ehdev.timetracker.core.user.UserImpl
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyImplTest {

    @Test
    public void testToString() throws Exception {
        def user1 = new UserImpl(1, '1', "name", 'email', 'auth')
        def user2 = new UserImpl(1, '1', "name", 'email', 'auth')
        def company1 = CompanyInteractor.createNewCompany(user1, 'a')
        def company2 = CompanyInteractor.createNewCompany(user2, 'a')
        company1.uuid = company2.uuid

        assertThat(company2).isEqualTo(company1)
        assertThat(company2.hashCode()).isEqualTo(company1.hashCode())
    }
}
