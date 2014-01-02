package io.ehdev.timetracker.storage.company

import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.storage.DaoTestBase
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

@Slf4j
class CompanyDaoTest extends DaoTestBase {

    @DataProvider(name = "DP")
    public static Object[][] params(){
        InMemoryCompanyDao inMemoryDao = new InMemoryCompanyDao()
        inMemoryDao.storage.clear()


        def factory = getSessionFactory("CompanyDaoTest")
        CompanyDaoImpl dao = new CompanyDaoImpl()
        dao.setSessionFactory(factory)

        return [[inMemoryDao], [dao]];
    }

    @Test(dataProvider = "DP")
    public void testSaveObject(CompanyDao dao) throws Exception {
        def company1 = CompanyInteractor.createNewCompany(UserBuilder.createNewUser(), 'bla1')
        dao.save(company1)

        def retrievedCompany = dao.getByUuid(company1.getUuid())
        assertThat(company1.id).isEqualTo(retrievedCompany.id)
        assertThat(company1.name).isEqualTo(retrievedCompany.name)
        assertThat(company1.uuid).isEqualTo(retrievedCompany.uuid)
        assertThat(company1.permissions.id).isEqualTo(retrievedCompany.permissions.id)
    }
}
