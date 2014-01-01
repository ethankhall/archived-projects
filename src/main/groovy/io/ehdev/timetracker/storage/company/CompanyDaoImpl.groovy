package io.ehdev.timetracker.storage.company
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.storage.BaseDao
import org.springframework.stereotype.Repository

@Repository
class CompanyDaoImpl extends BaseDao<CompanyImpl> implements CompanyDao {

    @Override
    Class<CompanyImpl> getBaseType() {
        return CompanyImpl
    }
}
