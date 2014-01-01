package io.ehdev.timetracker.storage.company

import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.storage.InMemoryBaseDao

class InMemoryCompanyDao extends InMemoryBaseDao<CompanyImpl> implements CompanyDao {
}
