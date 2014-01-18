package io.ehdev.timetracker.services
import io.ehdev.timetracker.core.project.Project
import io.ehdev.timetracker.core.project.ProjectImpl
import io.ehdev.timetracker.storage.project.ProjectDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/project")
class ProjectEndpoint {

    @Autowired
    ProjectDao projectDao

    @RequestMapping(method = RequestMethod.POST)
    public Project createNewProject(@RequestBody ProjectImpl newProject,
                                    OpenIDAuthenticationToken authentication) {
        projectDao.save(newProject)
        return newProject;
    }

}
