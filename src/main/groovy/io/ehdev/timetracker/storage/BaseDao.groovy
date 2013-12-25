package io.ehdev.timetracker.storage
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.transaction.Transactional

abstract class BaseDao<T> implements Dao<T> {

    @Autowired
    SessionFactory sessionFactory

    @Transactional
    List<T> query(Closure query){
        def criteria = sessionFactory.openSession().createCriteria(getBaseType())
        query.call(criteria)
        return (List<T>) criteria.list()
    }

    @Transactional
    public void save(T object){
        sessionFactory.openSession().save(object)
    }

    public T getById(Integer id){
        return (T)sessionFactory.openSession().get(getBaseType(), id)
    }

    abstract Class<T> getBaseType();
}
