package io.ehdev.timetracker.storage.project

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.project.ProjectImpl
import io.ehdev.timetracker.storage.BaseDao
import org.springframework.stereotype.Repository

@Repository
class ProjectDaoImpl extends BaseDao<ProjectImpl> implements ProjectDao {

    @Override
    Class<ProjectImpl> getBaseType() {
        ProjectImpl.class
    }

    @Override
    List<ProjectImpl> findProjectsForCompany(CompanyImpl company) {
        def query = getSession().createQuery("from ProjectImpl as project where project.company = :id")
        query.setInteger("id", company.id)
        return query.list()
    }
}
