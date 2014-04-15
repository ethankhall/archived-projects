package io.ehdev.timetracker.services
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.project.Project
import io.ehdev.timetracker.core.project.ProjectInteractor
import io.ehdev.timetracker.services.external.ExternalProject
import io.ehdev.timetracker.storage.project.ProjectDao
import io.ehdev.timetracker.storage.user.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/project")
@Slf4j
class ProjectEndpoint {

    @Autowired
    ProjectDao projectDao

    @Autowired
    UserDao userDao

    @RequestMapping(method = RequestMethod.POST)
    public Project createNewProject(@RequestBody ExternalProject receivedProject,
                                    OpenIDAuthenticationToken authentication) {

        def user = userDao.getUserFromToken(authentication)
        log.debug("User ${user.id} is creating a new project")

        if(receivedProject.company) {
            ProjectInteractor.createNewProject(user, receivedProject.rate, receivedProject.name)
        } else {
            ProjectInteractor.createNewProject(user, receivedProject.rate, receivedProject.name)
        }


        projectDao.save(receivedProject)

        return receivedProject;
    }

}
