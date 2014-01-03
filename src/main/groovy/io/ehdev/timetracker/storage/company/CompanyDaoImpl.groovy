package io.ehdev.timetracker.storage.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.storage.BaseDao
import io.ehdev.timetracker.storage.UUIDSearcher
import org.springframework.stereotype.Repository

@Repository
class CompanyDaoImpl extends BaseDao<CompanyImpl> implements CompanyDao {

    @Delegate
    UUIDSearcher<CompanyImpl> searcher = new UUIDSearcher<CompanyImpl>(this)

    @Override
    Class<CompanyImpl> getBaseType() {
        return CompanyImpl
    }
}
