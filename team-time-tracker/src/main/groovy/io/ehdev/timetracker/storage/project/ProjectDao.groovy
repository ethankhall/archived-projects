package io.ehdev.timetracker.storage.project

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.project.ProjectImpl
import io.ehdev.timetracker.storage.Dao

public interface ProjectDao extends Dao<ProjectImpl>{

    List<ProjectImpl> findProjectsForCompany(CompanyImpl company)

}