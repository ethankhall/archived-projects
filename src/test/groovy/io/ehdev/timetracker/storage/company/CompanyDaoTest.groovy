package io.ehdev.timetracker.storage.company
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
class CompanyDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    CompanyDao dao

    @BeforeMethod
    public void setup(){
        dao = new CompanyDaoImpl(sessionFactory: sessionFactory)
    }

    @Test
    public void testSaveObject() throws Exception {
        def company1 = CompanyInteractor.createNewCompany(UserBuilder.createNewUser(), 'bla1')
        dao.save(company1)

        def retrievedCompany = dao.getByUuid(company1.getUuid())
        assertThat(company1.id).isEqualTo(retrievedCompany.id)
        assertThat(company1.name).isEqualTo(retrievedCompany.name)
        assertThat(company1.uuid).isEqualTo(retrievedCompany.uuid)
        assertThat(company1.permissions.id).isEqualTo(retrievedCompany.permissions.id)
    }
}
