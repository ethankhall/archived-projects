package io.ehdev.timetracker.storage

import io.ehdev.timetracker.core.Storable

class UUIDSearcher<T extends Storable> {

    BaseDao<T> dao

    UUIDSearcher(BaseDao<T> dao){
        this.dao = dao
    }

    public T getByUuid(String uuid){
        def query = dao.getSession().createQuery("from ${dao.getBaseType().getSimpleName()} as obj where obj.uuid = :uuid")
        query.setParameter("uuid", uuid)
        return (T)query.uniqueResult()
    }

}
