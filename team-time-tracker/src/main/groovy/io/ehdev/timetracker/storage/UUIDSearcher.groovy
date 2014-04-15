package io.ehdev.timetracker.storage

import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.company.CompanyNotFoundException

class UUIDSearcher<T extends Storable> {

    BaseDao<T> dao

    UUIDSearcher(BaseDao<T> dao){
        this.dao = dao
    }

    public T getByUuid(String uuid){
        def query = dao.getSession().createQuery("from ${dao.getBaseType().getSimpleName()} as obj where obj.uuid = :uuid")
        query.setParameter("uuid", uuid)
        T result = (T)query.uniqueResult()
        if(null != result) {
            return result
        } else {
            throw new CompanyNotFoundException(uuid)
        }
    }

}
