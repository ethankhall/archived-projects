package io.ehdev.timetracker.storage.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.storage.InMemoryBaseDao

class InMemoryCompanyDao extends InMemoryBaseDao<CompanyImpl> implements CompanyDao {

    HashMap<Integer, CompanyImpl> storage = new HashMap<Integer, CompanyImpl>();

    @Override
    Map<Integer, CompanyImpl> getStorage() {
        return storage
    }
}
