package io.ehdev.timetracker.services
import groovy.mock.interceptor.MockFor
import io.ehdev.timetracker.services.external.ExternalProject
import io.ehdev.timetracker.storage.project.ProjectDao
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class ProjectEndpointTest extends GroovyTestCase {

    def mockProjectDao
    private projectDaoProxy

    @BeforeMethod
    public void setup() {
        mockProjectDao = new MockFor(ProjectDao)
    }

    @AfterTest
    public void verifyInteractions() {
        mockProjectDao.verify projectDaoProxy
    }

    @Test
    public void createNewProject() throws Exception {
        mockProjectDao.demand.save { null }

        def projectEndpoint = wireUpProjectEndpoint()

        projectEndpoint.createNewProject(new ExternalProject(), null)
    }

    private ProjectEndpoint wireUpProjectEndpoint() {
        def projectEndpoint = new ProjectEndpoint()
        projectDaoProxy = mockProjectDao.proxyInstance()

        projectEndpoint.setProjectDao(projectDaoProxy)

        return projectEndpoint
    }
}
