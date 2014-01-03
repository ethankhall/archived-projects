package io.ehdev.timetracker.storage.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.storage.InMemoryBaseDao
import io.ehdev.timetracker.storage.InMemoryUUIDSearcher

class InMemoryCompanyDao extends InMemoryBaseDao<CompanyImpl> implements CompanyDao {

    HashMap<Integer, CompanyImpl> storage = new HashMap<Integer, CompanyImpl>();

    @Delegate
    InMemoryUUIDSearcher<CompanyImpl> searcher = new InMemoryUUIDSearcher<CompanyImpl>(this)

    @Override
    Map<Integer, CompanyImpl> getStorage() {
        return storage
    }
}
