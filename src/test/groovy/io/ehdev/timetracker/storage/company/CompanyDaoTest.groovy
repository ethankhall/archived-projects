package io.ehdev.timetracker.storage.company

import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.storage.DaoTestBase
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class CompanyDaoTest extends DaoTestBase {

    @DataProvider(name = "DP")
    public static Object[][] params(){
        InMemoryCompanyDao inMemoryDao = new InMemoryCompanyDao()
        inMemoryDao.storage.clear()

        CompanyDaoImpl dao = new CompanyDaoImpl()
        dao.setSessionFactory(getSessionFactory("CompanyDaoTest"))

        return [[inMemoryDao], [dao]];
    }

    @Test(dataProvider = "DP")
    public void testSaveObject(CompanyDao dao) throws Exception {
        def company1 = CompanyInteractor.createNewCompany(UserBuilder.createNewUser(), 'bla1')
        def company2 = CompanyInteractor.createNewCompany(UserBuilder.createNewUser(), 'bla2')
        company1.permissions.parentPermissions = new ExtendedPermissions()
        def ids = dao.save(company1, company2)
        assertThat(dao.getByUuid(company1.getUuid())).isEqualTo(company1)
        assertThat(dao.getByUuid(company2.getUuid())).isEqualTo(company2)
    }
}
