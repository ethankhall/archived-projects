package io.ehdev.timetracker.storage.project
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.config.HibernateConfig
import io.ehdev.timetracker.config.PropertyFileLoader
import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.project.ProjectInteractor
import io.ehdev.timetracker.core.project.rate.FixedBidRate
import io.ehdev.timetracker.core.user.UserBuilder
import io.ehdev.timetracker.storage.company.CompanyDao
import io.ehdev.timetracker.storage.company.CompanyDaoImpl
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
class ProjectDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SessionFactory sessionFactory

    private CompanyDao companyDao
    private ProjectDaoImpl projectDao

    @BeforeMethod
    public void setup(){
        companyDao = new CompanyDaoImpl(sessionFactory: sessionFactory)
        projectDao = new ProjectDaoImpl(sessionFactory: sessionFactory)
    }

    @Test
    public void testFindingByCompany() throws Exception {
        def user1 = UserBuilder.createNewUser()

        def company = CompanyInteractor.createNewCompany(user1, 'company')
        companyDao.save(company)

        def project1 = ProjectInteractor.createNewProject(company, FixedBidRate.ZERO, 'project 1 name')
        def project2 = ProjectInteractor.createNewProject(company, FixedBidRate.ZERO, 'project 2 name')
        projectDao.save(project1, project2)
        assertThat(projectDao.findProjectsForCompany(company)).hasSize(2)
    }
}
